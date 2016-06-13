package com.solt.flash.model.imp;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.solt.flash.model.CountModel;

@Local
@Stateless
public class CountModelImp implements CountModel {
	
	@Inject
	private EntityManager em;

	@Named
	@Produces
	@Override
	public int getUserCont() {
		String sql = "select count(u) from User u";
		TypedQuery<Long> q = em.createQuery(sql, Long.class);
		return q.getSingleResult().intValue();
	}

	@Named
	@Produces
	@Override
	public int getCommentCount() {
		String sql = "select count(u) from Comment u";
		TypedQuery<Long> q = em.createQuery(sql, Long.class);
		return q.getSingleResult().intValue();
	}

	@Named
	@Produces
	@Override
	public int getBlogCount() {
		String sql = "select count(u) from Blog u";
		TypedQuery<Long> q = em.createQuery(sql, Long.class);
		return q.getSingleResult().intValue();
	}

}
