package com.solt.flash.producers;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.solt.flash.entity.Blog;
import com.solt.flash.model.TopRankingModel;

@RequestScoped
public class TopRankingProducer {
	
	@Inject
	private TopRankingModel model;
	
	@Named
	@Produces
	public List<Blog> getTopRankingBlogs() {
		String value = FacesContext.getCurrentInstance()
				.getExternalContext().getInitParameter("flash.topblog.size");
		return model.getTopRanking(Integer.parseInt(value));
	}

}
