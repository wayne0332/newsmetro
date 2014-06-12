package com.newsmetro.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class HibernateDaoSupport<T> implements HibernateBaseDao<T>{
	private Class<T> entityClass;
	@Autowired
	protected SessionFactory sessionFactory;
	
	public Session getSession() {  
        //事务必须是开启的，否则获取不到  
        return sessionFactory.getCurrentSession();  
    }  
	public HibernateDaoSupport(){
		Type genType = getClass().getGenericSuperclass();
		Type[]	params = ((ParameterizedType)genType).getActualTypeArguments();
		entityClass = (Class<T>) params[0];
	}

	@Override
	public void save(Object entity) {
		// TODO Auto-generated method stub
		getSession().save(entity);
	}
	
	@Override
	public T merge(T entity) {
		// TODO Auto-generated method stub
		return (T) getSession().merge(entity);
	}
	
	@Override
	public void update(Object entity) {
		// TODO Auto-generated method stub
		getSession().update(entity);
	}

	@Override
	public void delete(Object entity) {
		// TODO Auto-generated method stub
		getSession().delete(entity);
	}

	@Override
	public T get(Serializable id) {
		// TODO Auto-generated method stub
		return (T) getSession().get(entityClass, id);
	}

	@Override
	public List<T> loadAll() {
		// TODO Auto-generated method stub
		return getSession().createQuery("from"+entityClass.getSimpleName()).list();
	}

	@Override
	public List find(String hql) {
		// TODO Auto-generated method stub
		return getSession().createQuery(hql).list();
	}

	@Override
	public List find(String hql, Object... params) {
		// TODO Auto-generated method stub
		
		Query query = getSession().createQuery(hql);
		for(int i=0;i<params.length;i++){
			query.setParameter(i,params[i]);
		}
		return query.list();
	}

	@Override
	public void persist(Object object) {
		// TODO Auto-generated method stub
		getSession().persist(object);
	}

	

	@Override
	public void saveOrUpdate(Object entity) {
		// TODO Auto-generated method stub
		getSession().saveOrUpdate(entity);
	}

	@Override
	public T load(Serializable id) {
		// TODO Auto-generated method stub
		return (T) getSession().load(entityClass, id);
	}

	@Override
	public void refresh(Object entity) {
		// TODO Auto-generated method stub
		getSession().refresh(entity);
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		getSession().flush();
	}
	
}
