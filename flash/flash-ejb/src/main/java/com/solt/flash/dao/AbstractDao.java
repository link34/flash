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
	public T findById(Object id) {
		return em.find(type, id);
	}

}