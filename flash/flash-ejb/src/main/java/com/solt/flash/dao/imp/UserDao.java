package com.solt.flash.dao.imp;

import com.solt.flash.dao.AbstractDao;
import com.solt.flash.entity.User;

public class UserDao extends AbstractDao<User> {

	private static final long serialVersionUID = 1L;

	public UserDao() {
    	super(User.class);
    }

}