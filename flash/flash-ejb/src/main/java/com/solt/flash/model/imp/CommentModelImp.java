package com.solt.flash.model.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.solt.flash.dao.imp.CommentDao;
import com.solt.flash.entity.Comment;
import com.solt.flash.model.CommentModel;

@Local
@Stateless
public class CommentModelImp implements CommentModel {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CommentDao dao;

	@Override
	public List<Comment> searchComments(String keyword, String userId) {
		StringBuffer where = new StringBuffer();
		Map<String, Object> params = new HashMap<>();
		
		if(null != keyword && !keyword.isEmpty()) {
			where.append("t.comment like :comment ");
			params.put("comment", "%" + keyword + "%");
		}
		
		if(null != userId && !userId.isEmpty()) {
			if(params.size() > 0) {
				where.append("and ");
			}
			where.append("t.user.loginId = :userId");
			params.put("userId", userId);
		}
		return dao.select(where.toString(), params);
	}

	@Override
	public void deleteComment(Comment comment) {
		comment = dao.findById(comment.getId());
		comment.getBlog().removeComment(comment);
	}

}
