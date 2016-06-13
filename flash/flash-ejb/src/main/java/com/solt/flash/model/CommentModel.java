package com.solt.flash.model;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import com.solt.flash.entity.Comment;

@Local
public interface CommentModel extends Serializable {

	List<Comment> searchComments(String keyword, String user);
	void deleteComment(Comment comment);
}
