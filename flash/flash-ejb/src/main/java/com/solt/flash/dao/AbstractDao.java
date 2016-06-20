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
		String sql = String.format(SELECT, type.getSimpleName());
		
		if(null != where &&
				null != params &&
				!where.isEmpty() &&
				params.size() > 0) {
			sql = sql.concat("where ").concat(where);
		}
		
		TypedQuery<T> query = em.createQuery(sql, type);
		
		if(null != where &&
				null != params &&
				!where.isEmpty() &&
				params.size() > 0) {
			for(String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		
		return query.getResultList();
	}
	
	@Override
	public List<T> select(String where, Map<String, Object> params, String sortParam) {
		String sql = String.format(SELECT, type.getSimpleName());
		
		if(null != where &&
				null != params &&
				!where.isEmpty() &&
				params.size() > 0) {
			sql = sql.concat("where ").concat(where);
		}
		
		if(null != sortParam) {
			sql = sql.concat(sortParam);
		}
		
		TypedQuery<T> query = em.createQuery(sql, type);
		
		if(null != where &&
				null != params &&
				!where.isEmpty() &&
				params.size() > 0) {
			for(String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		
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
	public List<T> select(String where, Map<String, Object> params, String sortParam, int cntFrom, int offset) {
		String sql = String.format(SELECT, type.getSimpleName());
		
		if(null != where &&
				null != params &&
				!where.isEmpty() &&
				params.size() > 0) {
			sql = sql.concat("where ").concat(where);
		}
		
		if(null != sortParam) {
			sql = sql.concat(sortParam);
		}
		
		TypedQuery<T> query = em.createQuery(sql, type);
		
		if(null != where &&
				null != params &&
				!where.isEmpty() &&
				params.size() > 0) {
			for(String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		
		query.setFirstResult(cntFrom);
		query.setMaxResults(offset);
		
		return query.getResultList();
	}
	
	@Override
	public T findById(Object id) {
		return em.find(type, id);
	}

}