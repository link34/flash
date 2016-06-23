package com.solt.flash.model.imp;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.solt.flash.entity.Blog;
import com.solt.flash.entity.User;
import com.solt.flash.model.TopRankingModel;

@Local
@Stateless
public class TopRankingModelImp implements TopRankingModel {
	
	@Inject
	private EntityManager em;

	@Override
	public List<Blog> getTopRanking(int limit) {
		String sql = "select b from Blog b where b.user.status = :status order by size(b.rate) desc";
		TypedQuery<Blog> q = em.createQuery(sql, Blog.class);
		q.setParameter("status", User.Status.Valid);
		q.setMaxResults(limit);
		return q.getResultList();
	}

}
