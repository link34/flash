package com.solt.flash.dao.imp;

import com.solt.flash.dao.AbstractDao;
import com.solt.flash.entity.Blog;

public class BlogDao extends AbstractDao<Blog> {

	private static final long serialVersionUID = 1L;

	public BlogDao() {
		super(Blog.class);
	}

}