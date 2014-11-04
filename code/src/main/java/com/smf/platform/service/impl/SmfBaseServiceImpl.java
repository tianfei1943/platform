package com.smf.platform.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.smf.platform.dao.api.GenericDao;
import com.smf.platform.dao.api.GenericTemplate;
import com.smf.platform.service.api.SmfBaseService;

/**
 * 
 * @author robbie
 * @since 2011-09-01
 */

public class SmfBaseServiceImpl<T extends Serializable> implements
		SmfBaseService<T> {

	@Autowired
	@Qualifier("dao")
	protected GenericDao dao;

	@Autowired
	@Qualifier("template")
	protected GenericTemplate template;
	/**
	 * the target entity class
	 */
	@SuppressWarnings("unchecked")
	private Class entityClass;

	/**
	 * @return the entityClass
	 */
	@SuppressWarnings("unchecked")
	public Class getEntityClass() {
		return entityClass;
	}

	@SuppressWarnings("unchecked")
	public SmfBaseServiceImpl() {
		Type supType = getClass().getGenericSuperclass();
		ParameterizedType paramType = (ParameterizedType) supType;
		entityClass = (Class) paramType.getActualTypeArguments()[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smf.platform.service.api.GenericService#create(java.io.Serializable)
	 */
	public void save(T obj) {
		dao.save(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smf.platform.service.api.GenericService#get(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		return (T) dao.get(entityClass, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smf.platform.service.api.GenericService#listAll(java.lang.String,
	 * boolean)
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryAll(String orderBy, boolean asc) {
		return dao.queryAll(entityClass, orderBy, asc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smf.platform.service.api.GenericService#listAll(java.lang.String,
	 * boolean)
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryAll(String orderBy, boolean asc, boolean isCache) {
		return dao.queryAll(entityClass, orderBy, asc, isCache);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smf.platform.service.api.GenericService#listByPage(java.lang.String,
	 * boolean, int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryByPage(String orderBy, boolean asc, int page, int span) {
		return dao.queryByPage(entityClass, orderBy, asc, page, span);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smf.platform.service.api.GenericService#remove(java.io.Serializable)
	 */
	public void removeById(Serializable id) {
		dao.remove(entityClass, id);
	}

	/**
	 * @return the dao
	 */
	public GenericDao getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(GenericDao dao) {
		this.dao = dao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smf.platform.service.api.SmfBaseService#getByProperty(java.lang.String
	 * , java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryByProperty(final String propertyName, final Object value) {
		return dao.queryByProperty(entityClass, propertyName, value);
	}

	@SuppressWarnings("unchecked")
	public List<T> queryByProperty(final String propertyName,
			final Object value, final boolean isCache) {
		return dao.queryByProperty(entityClass, propertyName, value, isCache);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smf.platform.service.api.SmfBaseService#getByProperty(java.lang.String
	 * , java.lang.Object, java.lang.String, boolean)
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryByProperty(final String propertyName,
			final Object value, final String orderBy, final boolean asc) {
		return dao.queryByProperty(entityClass, propertyName, value, orderBy,
				asc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smf.platform.service.api.SmfBaseService#getByPropertyLike(java.lang
	 * .String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryByPropertyLike(final String propertyName,
			final Object value) {
		return dao.queryByPropertyLike(entityClass, propertyName, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smf.platform.service.api.SmfBaseService#getByPropertyLike(java.lang
	 * .String, java.lang.Object, java.lang.String, boolean)
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryByPropertyLike(String propertyName, Object value,
			String orderBy, boolean asc) {
		return dao.queryByPropertyLike(entityClass, propertyName, value,
				orderBy, asc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smf.platform.service.api.SmfBaseService#getFirstByProperty(java.lang
	 * .String, java.lang.Object)
	 */
	public Object queryFirstByProperty(String propertyName, Object value) {
		return dao.queryFirstByProperty(entityClass, propertyName, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smf.platform.service.api.SmfBaseService#remove(java.io.Serializable)
	 */
	public void remove(T obj) {
		dao.remove(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smf.platform.service.api.SmfBaseService#update(java.io.Serializable)
	 */
	public void update(T obj) {
		dao.update(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.smf.platform.service.api.SmfBaseService#query(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List query(String queryStr) {
		return dao.query(queryStr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.smf.platform.service.api.SmfBaseService#query(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List query(String queryStr, boolean isCache) {
		return dao.query(queryStr, isCache);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.smf.platform.service.api.SmfBaseService#query(java.lang.String,
	 * java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public List query(String queryStr, Object[] params) {
		return dao.query(queryStr, params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smf.platform.service.api.SmfBaseService#queryByPage(java.lang.String,
	 * java.lang.Object[], int, int)
	 */
	@SuppressWarnings("unchecked")
	public List queryByPage(String queryStr, Object[] params, int page, int span) {
		return dao.queryByPage(queryStr, params, page, span);
	}

	public void saveAll(List<T> datas) {
		dao.saveAll(datas);
	}

	public void updateAll(List<T> datas) {
		dao.updateAll(datas);
	}

	public void removeAll(List<T> datas) {
		dao.removeAll(datas);
	}

	public Object mergeOrUpdate(Object obj) {
		if (obj != null) {
			return dao.merge(obj);
		}
		return null;
	}

	public GenericTemplate getTemplate() {
		return template;
	}

	public void setTemplate(GenericTemplate template) {
		this.template = template;
	}

}
