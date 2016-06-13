package com.solt.flash.adm.view;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.solt.flash.entity.Category;
import com.solt.flash.model.CategoryModel;

@Model
public class CategoryBean {
	
	private String categoryName;
	
	@Inject
	private CategoryModel model;
	
	@Inject
	private Event<Category> createEvent;
	
	public void addCategory() {
		Category c = new Category();
		c.setCategory(categoryName);
		model.create(c);
		categoryName = "";
		createEvent.fire(c);
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}
