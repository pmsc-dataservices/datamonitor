package base.service;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;

import base.orm.HibernateDao;


/**
 * 默认的领域对象业务管理类基类,提供默认的范型DAO成员变量.
 * 
 * @param <T> 领域对象类型
 */
public class DefaultEntityManager<T> extends EntityManager<T> {

	protected HibernateDao<T> entityDao;
	
	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected HibernateDao<T> getEntityDao() {
		return entityDao;
	}

	
	@Resource 		//这个@Resource注解必不可少
	public void setSessionFactory(SessionFactory sessionFactory) {
		//entityClass在调用EntityManager构造器时获得
		entityDao = new HibernateDao<T>(sessionFactory, entityClass);
	}
}
