package com.solt.flash.dao.imp;

import com.solt.flash.dao.AbstractDao;
import com.solt.flash.entity.Category;

public class CategoryDao extends AbstractDao<Category> {

	private static final long serialVersionUID = 1L;

	public CategoryDao() {
    	super(Category.class);
    }

}