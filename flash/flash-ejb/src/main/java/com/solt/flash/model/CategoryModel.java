package com.solt.flash.model;

import java.util.List;

import javax.ejb.Local;

import com.solt.flash.entity.Category;

@Local
public interface CategoryModel {


    List<Category> getAll();
    
    Category findById(int id);

	void create(Category c);

}