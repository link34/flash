package com.solt.flash.common;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.solt.flash.entity.Category;
import com.solt.flash.model.CategoryModel;

@ApplicationScoped
public class CategoryProducer {

	@Named
	@Produces
	private List<Category> allCategories;

	@Inject
	private CategoryModel model;
	
	@PostConstruct
	private void init() {
		allCategories = model.getAll();
	}
	
	public void reload(@Observes Category c) {
		init();
	}
	
	public Category findById(int id) {
		return allCategories.stream()
				.filter(a -> a.getId() == id)
				.findFirst()
				.orElse(null);
	}
	
}