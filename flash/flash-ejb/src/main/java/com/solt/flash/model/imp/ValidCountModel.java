package com.solt.flash.model.imp;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.solt.flash.entity.User.Status;
import com.solt.flash.model.CountModel;

public class ValidCountModel implements CountModel {

	@Inject
	private EntityManager em;

	@Named("validUserCount")
	@Produces
	@Override
	public int getUserCont() {
		String sql = "select count(u) from User u where u.status = :status";
		TypedQuery<Long> q = em.createQuery(sql, Long.class);
		q.setParameter("status", Status.Valid);
		return q.getSingleResult().intValue();
	}

	@Named("validCommentCount")
	@Produces
	@Override
	public int getCommentCount() {
		String sql = "select count(u) from Comment u where u.user.status = :status";
		TypedQuery<Long> q = em.createQuery(sql, Long.class);
		q.setParameter("status", Status.Valid);
		return q.getSingleResult().intValue();
	}

	@Named("validBlogCount")
	@Produces
	@Override
	public int getBlogCount() {
		String sql = "select count(u) from Blog u where u.user.status = :status";
		TypedQuery<Long> q = em.createQuery(sql, Long.class);
		q.setParameter("status", Status.Valid);
		return q.getSingleResult().intValue();
	}

}
