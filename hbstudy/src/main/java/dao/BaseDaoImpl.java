package dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

public class BaseDaoImpl<T> implements BaseDao<T> {

	protected Class<T> entityClazz;
	
	protected SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		//get the T type
		Type type = getClass().getGenericSuperclass();
		if(type instanceof ParameterizedType){
			this.entityClazz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
		}else{
			this.entityClazz =null;
		}
	}
	
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object save(Object entity) {
		return (T)getSession().save(entity);
	}

	@Override
	public void delete(Object entity) {
		getSession().delete(entity);
	}

	@Override
	public void update(Object entity) {
		getSession().update(entity);
	}

	@Override
	public void saveOrUpdate(Object entity) {
		getSession().saveOrUpdate(entity);
	}

	@Override
	public void saveAll(Collection<?> entities) {
		for (@SuppressWarnings("rawtypes")
        Iterator localIterator = entities.iterator(); localIterator.hasNext();) {
            Object entity = localIterator.next();
            getSession().save(entity);
        }
	}
	
	@Override
	public void deleteAll(Collection<?> entities) {

        for (@SuppressWarnings("rawtypes")
        Iterator localIterator = entities.iterator(); localIterator.hasNext();) {
            Object entity = localIterator.next();
            getSession().delete(entity);
        }
    }

	@Override
	public void updateAll(Collection<?> entities) {
		for (@SuppressWarnings("rawtypes")
        Iterator localIterator = entities.iterator(); localIterator.hasNext();) {
            Object entity = localIterator.next();
            getSession().update(entity);
        }
	}

	@Override
	public void saveOrUpdateAll(Collection<?> entities) {
		for (@SuppressWarnings("rawtypes")
        Iterator localIterator = entities.iterator(); localIterator.hasNext();) {
            Object entity = localIterator.next();
            getSession().saveOrUpdate(entity);
        }
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	@Override
	public <T> T get(Class<T> entityClass, Serializable id) {
		 return (T) getSession().get(entityClass, id);
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	@Override
	public <T> T get(CharSequence queryString, Map<String, Object> params) {
		 Query qry = getSession().createQuery(queryString.toString());
		 setParameter(qry, params);
	        @SuppressWarnings("rawtypes")
	        List list = qry.setMaxResults(1).list();
	        if (list.isEmpty())
	            return null;
	      return (T) list.get(0);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
	@Override
	public <T> T get(CharSequence queryString, Object... params) {
		 Query qry = getSession().createQuery(queryString.toString());
	        for (int i = 0; i < params.length; ++i) {
	            qry.setParameter(i, params[i]);
	        }
	        List list = qry.setMaxResults(1).list();
	        if (list.isEmpty())
	            return null;
	        return (T) list.get(0);
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	@Override
	public <T> List<T> findList(CharSequence queryString, Object... params) {
		 Query query = getSession().createQuery(queryString.toString());
	        for (int i = 0; i < params.length; ++i) {
	            query.setParameter(i, params[i]);
	        }
	      return query.list();
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	@Override
	public <T> List<T> findList(CharSequence queryString,
			Map<String, Object> params) {
		 Query query = getSession().createQuery(queryString.toString());
	        setParameter(query, params);
	        return query.list();
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	@Override
	public <T> Pagination<T> findPagination(CharSequence queryString,
			int pageIndex, int pageSize, Object... params) {
		Query query = getSession().createQuery(queryString.toString());

        if ((pageSize > 0) && (pageIndex > 0)) {
            query.setFirstResult((pageIndex < 2) ? 0 : (pageIndex - 1) * pageSize);
            query.setMaxResults(pageSize);
        }

        for (int i = 0; i < params.length; ++i) {
            query.setParameter(i, params[i]);
        }
        @SuppressWarnings("rawtypes")
        List items = query.list();
        long rowsCount = 0L;

        if ((pageSize > 0) && (pageIndex > 0)) {
            String hql = parseSelectCount(queryString.toString());
            rowsCount = ((Long) get(hql, params)).longValue();
        } else {
            rowsCount = items.size();
        }

        @SuppressWarnings("rawtypes")
        Pagination pagination = new Pagination(pageIndex, pageSize, rowsCount);
        pagination.setItems(items);
        return pagination;
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	@Override
	public <T> Pagination<T> findPagination(CharSequence queryString,
			Map<String, Object> params, int pageIndex, int pageSize) {
		Query query =  getSession().createQuery(queryString.toString());
		
		if((pageSize>0)&& (pageIndex>0)){
			query.setFirstResult((pageSize<2)?0 : (pageIndex-1)* pageSize);
			query.setMaxResults(pageSize);
		}
		
		setParameter(query, params);
		
		@SuppressWarnings("rawtypes")
		List items = query.list();
		long rowsCount = 0L;
		
		if((pageSize>0)&&(pageIndex>0)){
			String hql = parseSelectCount(queryString.toString());
			rowsCount = ((Long) get(hql,params)).longValue();
		}else{
			rowsCount = items.size();
		}
		
		@SuppressWarnings("rawtypes")
		Pagination pagination = new Pagination(pageIndex, pageSize, rowsCount);
		pagination.setItems(items);
		
		return pagination;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
	@Override
	public <T> Pagination<T> findPagination(CharSequence queryString,
			CharSequence countString,Map<String,Object> params, int pageIndex, int pageSize) {
		Query query = getSession().createQuery(queryString.toString());
		
		if((pageSize>0) && (pageIndex>0)){
			query.setFirstResult((pageIndex<2? 0: (pageIndex-1)* pageSize));
			query.setMaxResults(pageSize);
		}
		
		setParameter(query, params);
		List items = query.list();
		long rowsCount = 0L;
		
		if((pageSize>0) && (pageIndex>0)){
			rowsCount = ((Long)get(countString,params)).longValue();
		}else{
			rowsCount = items.size();
		}
		
		Pagination pagination = new Pagination(pageIndex,pageSize,rowsCount);
		pagination.setItems(items);
		pagination.setRowsCount(rowsCount);
		
		return pagination;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
	@Override
	public <T> Pagination<T> findSQLPagination(CharSequence queryString,
			final CharSequence countString, final Map<String, Object> params,
			int pageIndex, int pageSize) {
		SQLQuery sqlQuery = getSession().createSQLQuery(queryString.toString());
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		if((pageSize>0) && (pageIndex>0)){
			sqlQuery.setFirstResult((pageIndex<2? 0 : (pageIndex-1) * pageSize));
			sqlQuery.setMaxResults(pageSize);
		}
		
		if((params!= null) && (!(params.isEmpty()))){
			setParameter(sqlQuery, params);
		}
		List items = sqlQuery.list();
		BigInteger rowsCount= BigInteger.valueOf(0);
		
		if((pageSize>0) && (pageIndex>0)){
			rowsCount = (BigInteger) executeQuery(new HibernateHandler(){
				private static final long serialVersionUID = 1L;
				@Override
				public Object doInHibernate(Session session) {
					SQLQuery query = session.createSQLQuery(countString.toString());
					if((params!=null) && !(params.isEmpty())){
						setParameter(query, params);
					}
					return query.uniqueResult();
				}
			});
		}
		
		Pagination pagination  = new Pagination(pageIndex,pageSize,rowsCount.intValue());
		pagination.setItems(items);
		
		return pagination;
	}

	@Override
	public void excute(String hql) {
		executeUpdate(hql);
	}

	@Override
	public void excute(HibernateHandler handler) {
		handler.doInHibernate(getSession());
	}

	@Override
	public void excuteSQL(String sql) {
		excuteSQLUpdate(sql);
	}

	@Override
	public Object executeQuery(HibernateHandler handler) {
		return handler.doInHibernate(getSession());
	}

	@Override
	public int excuteSQLUpdate(String sql) {
		return getSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public int executeUpdate(String hql) {
		
		return getSession().createQuery(hql).executeUpdate();
	}

	@Override
	public T getById(Serializable id) {
		if(id == null)
			return null;
		
		return (T)get(entityClazz,id);
	}

	@Override
	public T saveEntity(T o) {
		saveOrUpdate(o);
		return o;
	}

	@Override
	public T insert(T o) {
		save(o);
		return o;
	}

	@Override
	public void save(List<T> list) {
		saveOrUpdate(list);
	}

	@Override
	public void insert(List<T> list) {
		for(T entity: list){
			save(entity);
		}
	}

	@Override
	public void delete(List<T> list) {
		for(T entity:list){
			delete(entity);
		}
	}

	@Override
	public void update(List<T> list) {
		for(T entity:list){
			update(entity);
		}
	}

	@Override
	public List<T> findByProperty(String name, Object value) {
		String hql = "from "+ entityClazz.getSimpleName()+"where "+name +"=?";
		return findList(hql, value);
	}
	

	@Override
	public List<T> findByProperty(Map<String, Object> conditionMap) {
		StringBuilder builder = new StringBuilder();
		builder.append("from "+entityClazz.getSimpleName());
		if(!conditionMap.isEmpty()){
			Iterator<String> it = conditionMap.keySet().iterator();
			String key = it.next();
			builder.append("where "+ key + "=:" +key);
			while(it.hasNext()){
				key = it.next();
				builder.append(" and " + key + "=:" +key);
			}
		}
		return findList(builder, conditionMap);
	}
	
	protected Query setParameter(Query query, Map<String, Object> parameterMap) {
        for (@SuppressWarnings("rawtypes")
        Iterator iterator = parameterMap.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            query.setParameter(key, parameterMap.get(key));
        }
        return query;
    }
	
	protected boolean followWithWord(String s, String sub, int pos) {
        int i = 0;
        for (; (pos < s.length()) && (i < sub.length()); ++i) {
            if (s.charAt(pos) != sub.charAt(i))
                return false;
            ++pos;
        }

        if (i < sub.length()) {
            return false;
        }

        if (pos >= s.length()) {
            return true;
        }
        return (!(isAlpha(s.charAt(pos))));
    }
	
	protected String parseSelectCount(String queryString) {
        String hql = queryString.toLowerCase();
        int noBlankStart = 0;
        for (int len = hql.length(); noBlankStart < len; ++noBlankStart) {
            if (hql.charAt(noBlankStart) > ' ') {
                break;
            }
        }

        int pair = 0;

        if (!(followWithWord(hql, "select", noBlankStart))) {
            pair = 1;
        }
        int fromPos = -1;
        for (int i = noBlankStart; i < hql.length();) {
            if (followWithWord(hql, "select", i)) {
                ++pair;
                i += "select".length();
            } else if (followWithWord(hql, "from", i)) {
                --pair;
                if (pair == 0) {
                    fromPos = i;
                    break;
                }
                i += "from".length();
            } else {
                ++i;
            }
        }
        if (fromPos == -1) {
            throw new IllegalArgumentException("parse count sql error, check your sql/hql");
        }

        String countHql = "select count(*) " + queryString.substring(fromPos);
        return countHql;
    }
	
	protected boolean isAlpha(char c) {
        return ((c == '_') || (('0' <= c) && (c <= '9')) || (('a' <= c) && (c <= 'z')) || (('A' <= c) && (c <= 'Z')));
    }
}
