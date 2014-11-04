package com.smf.platform.service.api;

import java.io.Serializable;
import java.util.List;

/**
 * 对外提供单个实体基本的CRUD操作
 * 
 * @author robbie
 * @since 2011-08-27
 */
public interface SmfBaseService<T extends Serializable> {

	public void save(T obj);

	public void update(T obj);

	public T get(Serializable id);

	public void removeById(Serializable id);

	public void remove(T obj);

	public List<T> queryByPage(String orderBy, boolean asc, int page, int span);

	public List<T> queryAll(String orderBy, boolean asc);

	public List<T> queryAll(String orderBy, boolean asc, boolean isCache);

	public List<T> queryByProperty(final String propertyName, final Object value);
	
	public List<T> queryByProperty(final String propertyName, final Object value, final boolean isCache);

	public List<T> queryByProperty(final String propertyName, final Object value, final String orderBy,
			final boolean asc);

	public Object queryFirstByProperty(final String propertyName, final Object value);

	public List<T> queryByPropertyLike(final String propertyName, final Object value);

	public List<T> queryByPropertyLike(final String propertyName, final Object value, final String orderBy,
			final boolean asc);

	@SuppressWarnings("unchecked")
	public List query(String queryStr);

	@SuppressWarnings("unchecked")
	public List query(String queryStr, boolean isCache);

	@SuppressWarnings("unchecked")
	public List query(String queryStr, Object[] params);

	public List<T> queryByPage(final String queryStr, final Object params[], final int page, final int span);

	public void saveAll(List<T> datas);

	public void updateAll(List<T> datas);

	public void removeAll(List<T> datas);
	
	/**
	 * 更新或保存一个对象，并且返回新对�?
	 * 
	 * @param object
	 *            对象类型
	 * @return 对象
	 */

	public Object mergeOrUpdate(Object object);
}
