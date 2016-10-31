package base.orm;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import base.util.AddScalar;

/**
 * 封装Hibernate原生API的CRUD范型基类.
 * 可在Service层直接使用,也可以扩展范型DAO子类使用.
 * 
 */
@SuppressWarnings("unchecked")
public class SimpleHibernateDao<T> {

	// 日志记录
	protected Logger logger = Logger.getLogger(getClass());

	// 对sessionFactory进行初始化
	protected SessionFactory sessionFactory;

	protected Class<T> entityClass;

	/**
	 * 用于扩展的DAO子类使用的构造函数.
	 * 
	 * 通过子类的范型定义取得对象类型Class.
	 * 如 public class UserDao extends SimpleHibernateDao<User>
	 */
	public SimpleHibernateDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * 用于Service层直接使用SimpleHibernateDAO的构造函数.
	 */
	public SimpleHibernateDao(SessionFactory sessionFactory, Class<T> entityClass) {
		this.sessionFactory = sessionFactory;
		this.entityClass = entityClass;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 保存新增或修改的对象.
	 */
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
		logger.debug("save entity: "+entity);
	}

	/**
	 * 删除对象.
	 * @param entity 对象必须是session中的对象或含id属性的transient对象.
	 */
	public void delete(T entity) {
		getSession().delete(entity);
		logger.debug("delete entity: "+entity);
	}

	/**
	 * 按id删除对象.
	 */
	public void delete(Serializable id) {
		delete(get(id));
		logger.debug("delete entity "+entityClass.getSimpleName()+",id is {}"+id);
	}
	
	/**
	 * 按id获取对象.
	 */
	public T get(Serializable id) {
		return (T) getSession().load(entityClass, id);
	}

	/**
	 *	获取全部对象. 
	 */
	public List<T> getAll() {
		return findByCriteria();
	}

	/**
	 * 按属性查找对象列表,匹配方式为相等.
	 */
	public List<T> findByProperty(String propertyName, Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return findByCriteria(criterion);
	}

	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 */
	public T findUniqueByProperty(String propertyName, Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values 数量可变的参数
	 */
	public List<T> find(String hql, Object... values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询唯一对象.
	 */
	public Object findUnique(String hql, Object... values) {
		return createQuery(hql, values).uniqueResult();
	}
	
	/**
	 * 按HQL查询唯一对象数组.
	 */
	public List<String> findUniqueArray(String hql, Object... values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询Integer类型结果. 
	 */
	public Integer findInt(String hql, Object... values) {
		return (Integer) findUnique(hql, values);
	}

	/**
	 * 按HQL查询Long类型结果. 
	 */
	public Long findLong(String hql, Object... values) {
		return (Long) findUnique(hql, values);
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * 返回对象类型不是Entity时可用此函数灵活查询.
	 * 
	 * @param values 数量可变的参数
	 */
	public Query createQuery(String queryString, Object... values) {
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}
	
	public Query createSQLQuery(String queryString, Object... values) {
		Query query = getSession().createSQLQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	public List<T> findByCriteria(Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	/**
	 * 根据Criterion条件创建Criteria.
	 * 
	 * 返回对象类型不是Entity时可用此函数灵活查询.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	public Criteria createCriteria(Criterion... criterions) {
		// 属性对象导航时，要修改此方法
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	public boolean isPropertyUnique(String propertyName, Object newValue, Object orgValue) {
		if (newValue == null || newValue.equals(orgValue))
			return true;
		Object object = findUniqueByProperty(propertyName, newValue);
		return (object == null);
	}

	/**
	 * 取得对象的主键名.
	 */
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}
	
	public int executeSql(String sql) {
		Query query = getSession().createSQLQuery(sql);
		return query.executeUpdate();
	}
	
	/**
	 * 使用sql语句进行分页查询
	 * 
	 * @param sql
	 *            sql语句
	 * @param values
	 *            参数
	 * @param offSet
	 *            第一条记录序号 >-1
	 * @param pageSize
	 *            每页要显示的记录数 >0
	 * @param beanClass
	 *            将查询结果转换为<tt>T</tt>对象
	 * @param fieldList
	 *            查询Bean的成员变量名称
	 */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<T> list(final String sql, final Object[] values, final int offSet, final int pageSize,
			final Class<T> beanClass, final List<String> fieldList) {
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);

		// 添加要查询字段的标量
		AddScalar.addSclar(sqlQuery, beanClass, fieldList);

		Query query = sqlQuery;

		// 转换查询结果为T
		if (beanClass != null) {
			query.setResultTransformer(Transformers.aliasToBean(beanClass));
		}

		if ((values != null) && values.length > 0) {
			int i = 0;
			for (Object obj : values) {
				query.setParameter(i++, obj);
			}
		}

		if (offSet > -1) {
			query.setFirstResult(offSet);
		}

		if (pageSize > 0) {
			query.setMaxResults(pageSize);
		}
		return query.list();
	}
}