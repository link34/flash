package com.solt.flash.model;

import javax.ejb.Local;

@Local
public interface CountModel {
	int getUserCont();
	int getCommentCount();
	int getBlogCount();
}
