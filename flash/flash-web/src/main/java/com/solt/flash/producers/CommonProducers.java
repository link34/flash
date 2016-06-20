package com.solt.flash.producers;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.solt.flash.entity.User.Gender;

@ApplicationScoped
public class CommonProducers {

	@Named
	@Produces
	private List<Gender> genders = Arrays.asList(Gender.values());
	
	@BlogListCount
	@Produces
	private int getBlogListCount() {
		String blogListCount = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("flash.bloglist.size");
		return Integer.parseInt(blogListCount);
	}

}
