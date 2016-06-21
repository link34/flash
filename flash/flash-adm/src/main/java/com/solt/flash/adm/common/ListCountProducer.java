package com.solt.flash.adm.common;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ApplicationScoped
public class ListCountProducer {

	@Produces
	@ListCount(ListType.Blogs)
	private int blogListCount;
	
	@Produces
	@ListCount(ListType.Comments)
	private int commentListCount;
	
	@Produces
	@ListCount(ListType.Users)
	private int userListCount;
	
	@PostConstruct
	private void init() {
		ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
		blogListCount = Integer.parseInt(ctx.getInitParameter("flash.bloglist.size"));
		commentListCount = Integer.parseInt(ctx.getInitParameter("flash.commentlist.size"));
		userListCount = Integer.parseInt(ctx.getInitParameter("flash.userlist.size"));
	}
	
	
}
