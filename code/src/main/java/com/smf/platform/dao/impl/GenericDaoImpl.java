package com.smf.platform.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.smf.platform.dao.api.GenericDao;

/**
 * 通用的DAO接口实现
 */
@SuppressWarnings("unchecked")
public class GenericDaoImpl implements GenericDao {
	
	@SuppressWarnings("unused")
	// TODO add log
	private Logger log = Logger.getLogger(this.getClass());

	@PersistenceContext(unitName="persistenceUnit")
	private EntityManager em;
	
	public EntityManager getEm(){
		return em;
	}

	private Object getSingleResult(List result) {
		if (result != null && result.size() == 1)
			return result.get(0);
		if (result != null && result.size() > 1)
			throw new IllegalStateException("More than one objects found");
		else
			return null;
	}
	
	public Object get(Class clazz, Serializable id) {
		if (id == null)
			return null;
		else
			return em.find(clazz, id);
	}

	public List query(String queryStr) {
		return queryByPage(queryStr, null, -1, 0);
	}
	
	public List query(String queryStr, boolean isCache) {
		return queryByPage(queryStr, null, -1, 0, isCache);
	}

	public List query(String queryStr, Object[] params) {
		return queryByPage(queryStr, params, -1, 0);
	}

	public List queryByPage(final String queryStr, final Object params[],
			final int page, final int span) {
		return this.queryByPage(queryStr, params, page, span, false);
	}
	
	public List queryByPage(final String queryStr, final Object params[],
			final int page, final int span, final boolean isCache) {
		Query query = em.createQuery(queryStr);
		int parameterIndex = 0;
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				Object obj = params[i];
				query.setParameter(parameterIndex++, obj);
			}
		}
		if (page >= 0 && span > 0) {
			query.setFirstResult(page * span);
			query.setMaxResults(span);
		}
		if (query instanceof org.hibernate.ejb.QueryImpl) {
			((org.hibernate.ejb.QueryImpl)query).getHibernateQuery().setCacheable(isCache);
		}
		return query.getResultList();
	}

	public List queryByPage(final Class clazz, final String orderBy,
			final boolean asc, final int page, final int span) {
		String clazzName = clazz.getName();
		StringBuffer sb = new StringBuffer("select obj from ");
		sb.append(clazzName).append(" obj");
		Query query = null;
		if (orderBy != null && !orderBy.equals(""))
			sb.append(" order by obj.").append(orderBy).append(
					asc ? " asc" : " desc");
		query = em.createQuery(sb.toString());
		if (page >= 0 && span > 0) {
			query.setFirstResult(page * span);
			query.setMaxResults(span);
		}
		return query.getResultList();
	}

	public List queryAll(final Class clazz, final String orderBy,
			final boolean asc) {
		return this.queryAll(clazz, orderBy, asc, false);
	}
	
	public List queryAll(final Class clazz, final String orderBy,
			final boolean asc, final boolean isCache) {
		String clazzName = clazz.getName();
		StringBuffer sb = new StringBuffer("select obj from ");
		sb.append(clazzName).append(" obj");
		Query query = null;
		if (orderBy != null && !orderBy.equals(""))
			sb.append(" order by obj.").append(orderBy).append(
					asc ? " asc" : " desc");
		query = em.createQuery(sb.toString());
		if (query instanceof org.hibernate.ejb.QueryImpl) {
			((org.hibernate.ejb.QueryImpl)query).getHibernateQuery().setCacheable(isCache);
		}
		return query.getResultList();
	}

	public List queryAll(final Class clazz) {
		return queryAll(clazz, null, true);
	}

	public List queryByProperty(final Class clazz, final String propertyName,
			final Object value) {
		return queryByProperty(clazz, propertyName, value, null, true);
	}
	
	public List queryByProperty(final Class clazz, final String propertyName,
			final Object value, final boolean isCache) {
		return queryByProperty(clazz, propertyName, value, null, true, isCache);
	}

	public Object queryFirstByProperty(final Class clazz,
			final String propertyName, final Object value) {
		List result = queryByProperty(clazz, propertyName, value);
		return getSingleResult(result);
	}

	public List queryByProperty(final Class clazz, final String propertyName,
			final Object value, final String orderBy, final boolean asc) {
		return this.queryByProperty(clazz, propertyName, value, orderBy, asc, false);
	}
	
	public List queryByProperty(final Class clazz, final String propertyName,
			final Object value, final String orderBy, final boolean asc, final boolean isCache) {
		if (propertyName == null || "".equals(propertyName))
			throw new IllegalArgumentException("The parameter value is invalid");
		String clazzName = clazz.getName();
		StringBuffer sb = new StringBuffer("select obj from ");
		sb.append(clazzName).append(" obj");
		Query query = null;
		if (propertyName != null) {
			sb.append(" where obj.").append(propertyName).append(" = :value");
			if (orderBy != null && !orderBy.equals(""))
				sb.append(" order by ").append(orderBy).append(
						asc ? " asc" : " desc");
			query = em.createQuery(sb.toString()).setParameter("value", value);
		} else {
			if (orderBy != null && !orderBy.equals(""))
				sb.append(" order by obj.").append(orderBy).append(
						asc ? " asc" : " desc");
			query = em.createQuery(sb.toString());
		}
		if (query instanceof org.hibernate.ejb.QueryImpl) {
			((org.hibernate.ejb.QueryImpl)query).getHibernateQuery().setCacheable(isCache);
		}
		return query.getResultList();
	}

	public void remove(Class clazz, Serializable id) {
		if (id == null)
			throw new IllegalArgumentException("The parameter value is invalid");
		Object object = get(clazz, id);
		if (object != null)
			em.remove(object);
	}

	public void remove(Object obj) {
		if (obj != null) {
			em.remove(obj);
		}
	}

	public void save(Object obj) {
		if (obj != null) {
			em.persist(obj);
		}
	}

	public void saveAll(List list) {
		for(Object obj:list){
			if (obj != null) {
				em.persist(obj);
			}
		}		
	}
	
	public void update(Object obj) {
		if (obj != null) {
			em.merge(obj);
		}
	}

	
	public void updateAll(List list) {
		for (Object obj : list) {
			if (obj != null) {
				em.merge(obj);
			}
		}
	}
	
	public int executeUpdate(final String jpql, final Object params[]) {
		Query query = em.createQuery(jpql);
		int parameterIndex = 1;
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				Object obj = params[i];
				query.setParameter(parameterIndex++, obj);
			}
		}
		return Integer.valueOf(query.executeUpdate()).intValue();
	}

	public List executeNativeQuery(final String sql) {
		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}

	public int executeNativeUpdate(final String sql) {
		Query query = em.createNativeQuery(sql);
		return Integer.valueOf(query.executeUpdate()).intValue();
	}

	public void executeNativeUpdate(List<String> sqls) {
		for (String sql : sqls) {
			Query query = em.createNativeQuery(sql);
			query.executeUpdate();
		} 
	}
	
	public List queryByPropertyLike(Class clazz, String propertyName, Object value) {
		return queryByPropertyLike(clazz, propertyName, value, null, true);
	}

	public List queryByPropertyLike(Class clazz, String propertyName,
			Object value, String orderBy, boolean asc) {
		if (propertyName == null || "".equals(propertyName))
			throw new IllegalArgumentException("The parameter value is invalid");
		String clazzName = clazz.getName();
		StringBuffer sb = new StringBuffer("select obj from ");
		sb.append(clazzName).append(" obj");
		Query query = null;
		if (propertyName != null) {
			sb.append(" where obj.").append(propertyName).append(
					" like '%" + value + "%' ");
			if (orderBy != null && !orderBy.equals(""))
				sb.append(" order by ").append(orderBy).append(
						asc ? " asc" : " desc");
			query = em.createQuery(sb.toString());
		} else {
			if (orderBy != null && !orderBy.equals(""))
				sb.append(" order by obj.").append(orderBy).append(
						asc ? " asc" : " desc");
			query = em.createQuery(sb.toString());
		}
		return query.getResultList();
	}
	
	public List queryByNamedQuery(String namedQuery) {
		return queryPageByNamedQuery(namedQuery, null, -1, 0);
	}

	public List queryByNamedQuery(String namedQuery, Object[] params) {
		return queryPageByNamedQuery(namedQuery, params, -1, 0);
	}

	public List queryPageByNamedQuery(final String namedQuery, final Object params[],
			final int page, final int span) {
		Query query = em.createNamedQuery(namedQuery);
		int parameterIndex = 1;
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				Object obj = params[i];
				query.setParameter(parameterIndex++, obj);
			}
		}
		if (page >= 0 && span > 0) {
			query.setFirstResult(page * span);
			query.setMaxResults(span);
		}
		return query.getResultList();
	} 
	
	public Object queryFirstByNamedQuery(String namedQuery) {
		List result = queryByNamedQuery(namedQuery);
		return getSingleResult(result);
	}

	public Object queryFirstByNamedQuery(String namedQuery, Object[] params) {
		List result = queryByNamedQuery(namedQuery, params);
		return getSingleResult(result);
	}

	public Object queryFirst(String queryStr) {
		List result = query(queryStr);
		return getSingleResult(result);
	}

	public Object queryFirst(String queryStr, Object[] params) {
		List result = query(queryStr, params);
		return getSingleResult(result);
	}

	public void removeAll(List list) {
		for(Object obj:list){
			if (obj != null) {
				em.remove(obj);
			}
		}
	}
	
	public Object merge(Object obj)
	{
		if (obj != null)
		{
			return em.merge(obj);
		}
		return null;
	}
}
