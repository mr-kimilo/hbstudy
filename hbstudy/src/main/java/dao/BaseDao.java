package dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BaseDao<T> {

	/**
	 * save entity
	 * @param entity
	 * @return
	 */
	Object save(Object entity);
	
	/**
	 * delete entity
	 * @param entity
	 */
	void delete(Object entity);
	
	/**
	 * update entity
	 * @param entity
	 */
	void update(Object entity);
	
	/**
	 * save or update object
	 * @param entity
	 */
	void saveOrUpdate(Object entity);
	
	/**
	 * save all object
	 * @param entities
	 */
	void saveAll(Collection<?> entities);
	
	/**
	 * delete all object
	 * @param entities
	 */
	void deleteAll(Collection<?> entities);
	
	/**
	 * update all entities
	 * @param entities
	 */
	void updateAll(Collection<?> entities);
	
	/**
	 * save or update all entities
	 * @param entities
	 */
	void saveOrUpdateAll(Collection<?> entities);
	
	/**
	 * get an object by class type and id
	 * @param entityClass
	 * @param id
	 * @return
	 */
	@SuppressWarnings("hiding")
	<T> T get(Class<T> entityClass,Serializable id);
	
	/**
	 * get object by query SQL and parameters
	 * @param queryString
	 * @param params
	 * @return return the first object if exists many
	 */
	@SuppressWarnings("hiding")
	<T> T get(CharSequence queryString, Map<String,Object> params);
	
	/**
	 * get object by SQL and parameters
	 * @param queryString
	 * @param params
	 * @return return the first object if exists many
	 */
	@SuppressWarnings("hiding")
	<T> T get(CharSequence queryString,Object ...params );
	
	/**
	 * get entity list
	 * @param queryString
	 * @param objects
	 * @return entity list
	 */
	@SuppressWarnings("hiding")
	<T> List<T> findList(CharSequence queryString, Object ...params);
	
	/**
	 * get entity list
	 * @param queryString
	 * @param params
	 * @return  entity list
	 */
	@SuppressWarnings("hiding")
	<T> List<T> findList(CharSequence queryString,Map<String,Object> params); 
	
	/**
	 * find page object by SQL and page index and page size and parameters
	 * @param queryString
	 * @param pageIndex
	 * @param pageSize
	 * @param params
	 * @return
	 */
	@SuppressWarnings("hiding")
	<T> Pagination<T> findPagination(CharSequence queryString,int pageIndex,int pageSize,Object ...params);
	
	/**
	 * find page object by map parameters
	 * @param queryString
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("hiding")
	<T> Pagination<T> findPagination(CharSequence queryString,Map<String,Object> params,int pageIndex,int pageSize);
	
	/**
	 * find page object and define count, adapt to HQL query
	 * @param queryString
	 * @param countString
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("hiding")
	<T> Pagination<T> findPagination(CharSequence queryString,CharSequence countString,Map<String,Object> params,int pageIndex,int pageSize);
	
	/**
	 * return page object by SQL and define count 
	 * @param queryString
	 * @param countString
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("hiding")
	<T> Pagination<T> findSQLPagination(CharSequence queryString,final CharSequence countString,
			final Map<String,Object> params,int pageIndex,int pageSize);
	
	/**
	 * execute DB update
	 * @param hql
	 */
	void excute(String hql);
	
	/**
	 * do update
	 * @param handler
	 */
	void excute(HibernateHandler handler);
	
	/**
	 * execute SQL
	 * @param sql
	 */
	void excuteSQL(String sql);
	
	/**
	 * execute query
	 * @param handler
	 * @return
	 */
	Object executeQuery(HibernateHandler handler);
	
	/**
	 * execute update 
	 * @param sql
	 * @return
	 */
	int excuteSQLUpdate(String sql);
	
	/**
	 * execute update by HQL
	 * @param hql
	 * @return
	 */
	int executeUpdate(String hql);
	
	public T getById(Serializable id);
	
	public T saveEntity(T o);
	
	public T insert(T o);
	
	public void save(List<T> list);
	
	public void insert(List<T> list);
	
	public void delete(List<T> list);
	
	public void update(List<T> list);
	
	public List<T> findByProperty(String name, Object value);
	
	public List<T> findByProperty(Map<String,Object> conditionMap); 
	
}
