package base.service;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import base.orm.HibernateDao;
import base.orm.support.Page;
import base.orm.support.PropertyFilter;
import base.util.ReflectionUtil;


/**
 * 领域对象的业务管理类基类.
 * 
 * 提供了领域对象的简单CRUD方法.
 *
 * @param <T> 领域对象类型
 */
@Transactional
public abstract class EntityManager<T> {

	protected Logger logger = Logger.getLogger(getClass());

	protected Class<T> entityClass;

	/**
	 * 通过子类的范型定义取得领域对象类型Class.
	 */
	@SuppressWarnings("unchecked")
	public EntityManager() {
		this.entityClass = ReflectionUtil.getSuperClassGenricType(getClass());
	}

	/**
	 * 在子类实现的回调函数,为EntityManager提供默认CRUD操作所需的DAO.
	 */
	protected abstract HibernateDao<T> getEntityDao();

	// CRUD函数 //
	@Transactional(readOnly=true)
	public T load(Serializable id) {
		return getEntityDao().get(id);
	}
	
	@Transactional(readOnly=true)
	public Page<T> loadAll(Page<T> page) {
		return getEntityDao().getAll(page);
	}

	@Transactional(readOnly=true)
	public List<T> loadAll() {
		return getEntityDao().getAll();
	}

	@Transactional(readOnly=true)
	public Page<T> search(Page<T> page, PropertyFilter... filters) {
		return getEntityDao().findByFilters(page, filters);
	}
	@Transactional(readOnly=true)
	public List<T> search(PropertyFilter... filters) {
		return getEntityDao().findByFilters(filters);
	}

	public void saveOrUpdate(T entity) {
		getEntityDao().saveOrUpdate(entity);
	}

	public void remove(Serializable id) {
		getEntityDao().delete(id);
	}
	
	public void remove(T entity) {
		getEntityDao().delete(entity);
	}
	
}
