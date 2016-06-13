package com.solt.flash.dao.imp;

import com.solt.flash.dao.AbstractDao;
import com.solt.flash.entity.Comment;

public class CommentDao extends AbstractDao<Comment> {

	private static final long serialVersionUID = 1L;

	public CommentDao() {
    	super(Comment.class);
    }
}