package com.smf.platform.dao.api;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

/**
 * 通用的DAO操作接口
 */
@SuppressWarnings("unchecked")
public interface GenericDao {
	
	public EntityManager getEm();

	/**
	 * 根据id获得一个对象
	 * 
	 * @param clazz
	 *            对象类型
	 * @param id
	 *            对象id
	 * @return 对象
	 */
	public Object get(Class clazz, Serializable id);

	/**
	 * 根据查询语句获取相关对象列表
	 * 
	 * @param queryStr
	 *            查询语句
	 * @return
	 */
	public List query(String queryStr);

	/**
	 * 根据查询语句获取相关对象列表
	 * 
	 * @param queryStr
	 *            查询语句
	 * @return
	 */
	public List query(String queryStr, boolean isCache);

	/**
	 * 根据查询语句及其参数列表获取相关对象集
	 * 
	 * @param queryStr
	 *            查询语句
	 * @param params
	 *            查询语句中的参数值列表
	 * @return
	 */
	public List query(String queryStr, Object[] params);

	/**
	 * 分页查询，根据对象类型、起始页、页大小获取相关对象集，并进行排序
	 * 
	 * @param clazz
	 *            对象类型
	 * @param orderBy
	 *            排序属性
	 * @param asc
	 *            是否升序
	 * @param page
	 *            起始页
	 * @param span
	 *            页大小
	 * @return
	 */
	public List queryByPage(final Class clazz, final String orderBy,
			final boolean asc, final int page, final int span);

	/**
	 * 分页查询，根据查询语句及其参数列表、起始页、页大小获取相关对象集
	 * 
	 * @param queryStr
	 *            查询语句
	 * @param params
	 *            查询语句中的参数值列表
	 * @param page
	 *            起始页
	 * @param span
	 *            页大小
	 * @return
	 */
	public List queryByPage(final String queryStr, final Object params[],
			final int page, final int span);

	/**
	 * 根据对象类型获取所有相关对象，并进行排序
	 * 
	 * @param clazz
	 *            对象类型
	 * @param orderBy
	 *            排序属性
	 * @param asc
	 *            是否升序
	 * @return
	 */
	public List queryAll(final Class clazz, final String orderBy,
			final boolean asc);

	/**
	 * 根据对象类型获取所有相关对象，并进行排序
	 * 
	 * @param clazz
	 *            对象类型
	 * @param orderBy
	 *            排序属性
	 * @param asc
	 *            是否升序
	 * @param asc
	 *            是否缓存
	 * @return
	 */
	public List queryAll(final Class clazz, final String orderBy,
			final boolean asc, final boolean isCache);

	/**
	 * 根据对象类型获取所有相关对象
	 * 
	 * @param clazz
	 *            对象类型
	 * @return
	 */
	public List queryAll(final Class clazz);

	/**
	 * 根据对象类型、属性获取相关对象列表
	 * 
	 * @param clazz
	 *            对象类型
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            属性值
	 * @return
	 */
	public List queryByProperty(final Class clazz, final String propertyName,
			final Object value);

	/**
	 * 根据对象类型、属性获取相关对象列表
	 * 
	 * @param clazz
	 *            对象类型
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            属性值
	 * @param isCache
	 *            是否缓存
	 * @return
	 */
	public List queryByProperty(final Class clazz, final String propertyName,
			final Object value, final boolean isCache);

	/**
	 * 根据对象类型、属性模糊查询相应的对象列表
	 * 
	 * @param clazz
	 *            对象类型
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            属性值
	 * @return
	 */
	public List queryByPropertyLike(final Class clazz,
			final String propertyName, final Object value);

	/**
	 * 根据对象类型、属性获取单个对象
	 * 
	 * @param clazz
	 *            对象类型
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            属性值
	 * @return
	 */
	public Object queryFirstByProperty(final Class clazz,
			final String propertyName, final Object value);

	/**
	 * 根据对象类型、属性获取相关对象列表，并进行排序
	 * 
	 * @param clazz
	 *            对象类型
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            属性值
	 * @param orderBy
	 *            排序属性
	 * @param asc
	 *            是否升序
	 * @return
	 */
	public List queryByProperty(final Class clazz, final String propertyName,
			final Object value, final String orderBy, final boolean asc);

	/**
	 * 根据对象类型、属性获取相关对象列表，并进行排序
	 * 
	 * @param clazz
	 *            对象类型
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            属性值
	 * @param orderBy
	 *            排序属性
	 * @param asc
	 *            是否升序
	 * @param isCache
	 *            是否缓存
	 * @return
	 */
	public List queryByProperty(final Class clazz, final String propertyName,
			final Object value, final String orderBy, final boolean asc,
			final boolean isCache);

	/**
	 * 根据对象类型、属性模糊查询相应的对象列表,并进行排序
	 * 
	 * @param clazz
	 *            对象类型
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            属性值
	 * @param orderBy
	 *            排序属性
	 * @param asc
	 *            是否升序
	 * @return
	 */
	public List queryByPropertyLike(final Class clazz,
			final String propertyName, final Object value,
			final String orderBy, final boolean asc);

	/**
	 * 根据对象id删除一个对象
	 * 
	 * @param clazz
	 *            对象类型
	 * @param id
	 *            对象id
	 */
	public void remove(Class clazz, Serializable id);

	/**
	 * 删除一个数据集合
	 * 
	 * @param object
	 */
	public void removeAll(List list);

	/**
	 * 删除一个对象
	 * 
	 * @param obj
	 */
	public void remove(Object obj);

	/**
	 * 保存一个对象
	 * 
	 * @param object
	 */
	public void save(Object object);

	/**
	 * 保存一个数据集合
	 * 
	 * @param object
	 */
	public void saveAll(List list);

	/**
	 * 更新一个对象
	 * 
	 * @param object
	 */
	public void update(Object object);

	/**
	 * 更新一个数据集合
	 * 
	 * @param object
	 */
	public void updateAll(List list);

	/**
	 * 根据jpql执行批量更新或删除等非查询类操作
	 * 
	 * @param jpql
	 *            非查询类操作语句
	 * @param params
	 *            参数值列表
	 * @return 受影响的对象数
	 */
	public int executeUpdate(final String jpql, final Object params[]);

	/**
	 * 根据原始sql查询
	 * 
	 * @param sql
	 * @return
	 */
	public List executeNativeQuery(final String sql);

	/**
	 * 根据原始sql执行非查询类操作
	 * 
	 * @param sql
	 * @return 受影响的记录数
	 */
	public int executeNativeUpdate(final String sql);

	public void executeNativeUpdate(List<String> sqls);

	/**
	 * 根据namedQuery查询
	 * 
	 * @param namedQuery
	 * @return
	 */
	public List queryByNamedQuery(String namedQuery);

	/**
	 * 根据namedQuery及其参数列表查询
	 * 
	 * @param namedQuery
	 * @param params
	 * @return
	 */
	public List queryByNamedQuery(String namedQuery, Object[] params);

	/**
	 * 根据namedQuery分页查询
	 * 
	 * @param namedQuery
	 * @param params
	 * @param page
	 * @param span
	 * @return
	 */
	public List queryPageByNamedQuery(final String namedQuery,
			final Object params[], final int page, final int span);

	/**
	 * 根据namedQuery获取第一个对象
	 * 
	 * @param queryStr
	 *            查询语句
	 * @return
	 */

	public Object queryFirstByNamedQuery(String namedQuery);

	/**
	 * 根据namedQuery及其参数列表获取第一个对象
	 * 
	 * @param queryStr
	 *            查询语句
	 * @param params
	 *            查询语句中的参数值列表
	 * @return
	 */
	public Object queryFirstByNamedQuery(String namedQuery, Object[] params);

	/**
	 * 根据查询语句获取第一个对象
	 * 
	 * @param queryStr
	 *            查询语句
	 * @return
	 */
	public Object queryFirst(String queryStr);

	/**
	 * 根据查询语句及其参数列表获取第一个对象
	 * 
	 * @param queryStr
	 *            查询语句
	 * @param params
	 *            查询语句中的参数值列表
	 * @return
	 */
	public Object queryFirst(String queryStr, Object[] params);

	public Object merge(Object object);

}
