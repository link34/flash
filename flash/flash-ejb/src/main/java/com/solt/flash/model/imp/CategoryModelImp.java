package com.solt.flash.model.imp;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.solt.flash.dao.imp.CategoryDao;
import com.solt.flash.entity.Category;
import com.solt.flash.model.CategoryModel;

@Local
@Stateless
public class CategoryModelImp implements CategoryModel {

	@Inject
	private CategoryDao catDao;

	@Override
	public List<Category> getAll() {
		return catDao.select(null, null);
	}

	@Override
	public Category findById(int id) {
		return catDao.findById(id);
	}

	@Override
	public void create(Category c) {
		catDao.insert(c);
	}

}