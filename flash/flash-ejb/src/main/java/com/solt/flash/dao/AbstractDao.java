package com.solt.flash.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public abstract class AbstractDao<T> implements Dao<T> {

	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager em;
    private Class<T> type;
    
    private static final String SELECT = "select t from %s t ";
    private static final String COUNT = "select count(t) from %s t ";
    
	public AbstractDao(Class<T> type) {
		super();
		this.type = type;
	}

	@Override
	public void insert(T t) {
		em.persist(t);
	}
	@Override
	public void update(T t) {
		em.merge(t);
	}
	
	@Override
	public void delete(Object id) {
		T t = em.find(type, id);
		em.remove(t);
	}
	
	@Override
	public List<T> select(String where, Map<String, Object> params) {
		SelectHelper helper = new SelectHelper(SELECT, where, params, null);
		TypedQuery<T> query = helper.getQuery();
		return query.getResultList();
	}
	
	@Override
	public List<T> select(String where, Map<String, Object> params, String sortParam) {
		SelectHelper helper = new SelectHelper(SELECT, where, params, sortParam);
		TypedQuery<T> query = helper.getQuery();
		return query.getResultList();
	}
	
	@Override
	public List<T> select(String where, Map<String, Object> params, String sortParam, int cntFrom, int offset) {
		
		SelectHelper helper = new SelectHelper(SELECT, where, params, sortParam);
		TypedQuery<T> query = helper.getQuery();
		query.setFirstResult(cntFrom);
		query.setMaxResults(offset);
		
		return query.getResultList();
	}
	

	@Override
	public long selectCount(String where, Map<String, Object> params) {
		String sql = String.format(COUNT, type.getSimpleName());
		
		if(null != where &&
				null != params &&
				!where.isEmpty() &&
				params.size() > 0) {
			sql = sql.concat("where ").concat(where);
		}
		
		TypedQuery<Long> query = em.createQuery(sql, Long.class);
		
		if(null != where &&
				null != params &&
				!where.isEmpty() &&
				params.size() > 0) {
			for(String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		return query.getSingleResult();
	}
	
	@Override
	public T findById(Object id) {
		return em.find(type, id);
	}
	
	class SelectHelper {

		private String sql;
		TypedQuery<T> query;
		
		SelectHelper(String sql, String where, Map<String, Object> params, String sortParam) {
			this.sql = String.format(sql, type.getSimpleName());
			
			if(null != where &&
					null != params &&
					!where.isEmpty() &&
					params.size() > 0) {
				this.sql = this.sql.concat("where ").concat(where);
			}
			
			if(null != sortParam) {
				this.sql = this.sql.concat(sortParam);
			}
			
			query = em.createQuery(this.sql, type);
			
			if(null != where &&
					null != params &&
					!where.isEmpty() &&
					params.size() > 0) {
				for(String key : params.keySet()) {
					query.setParameter(key, params.get(key));
				}
			}
		}
		
		TypedQuery<T> getQuery() {
			return query;
		}
	}

}