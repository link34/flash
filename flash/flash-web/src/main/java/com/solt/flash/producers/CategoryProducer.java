package com.solt.flash.producers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.solt.flash.entity.Category;
import com.solt.flash.model.CategoryModel;

@ApplicationScoped
public class CategoryProducer {

    private List<Category> allCategory;

    @Inject
    private CategoryModel catModel;

    @PostConstruct
    private void init() {
    	allCategory = catModel.getAll();
    }

    @Named
    @Produces
	public List<Category> getAllCategory() {
		return allCategory;
	}
}