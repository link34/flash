package com.solt.flash.model.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.solt.flash.dao.imp.CommentDao;
import com.solt.flash.entity.Comment;
import com.solt.flash.entity.User;
import com.solt.flash.model.CommentModel;

@Local
@Stateless
public class CommentModelImp implements CommentModel {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CommentDao dao;

	@Override
	public List<Comment> searchComments(String keyword, String userId) {
		QueryHelper helper = new QueryHelper(keyword, userId);
		return dao.select(helper.getWhere(), helper.getParams());
	}

	@Override
	public void deleteComment(Comment comment) {
		comment = dao.findById(comment.getId());
		comment.getBlog().removeComment(comment);
	}

	@Override
	public List<Comment> searchComments(String keyword, String user, int start, int limit) {
		QueryHelper helper = new QueryHelper(keyword, user);
		return dao.select(helper.getWhere(), helper.getParams(), null, start, limit);
	}

	@Override
	public long searchCommentCount(String keyword, String user) {
		QueryHelper helper = new QueryHelper(keyword, user);
		return dao.selectCount(helper.getWhere(), helper.getParams());
	}
	
	class QueryHelper {
		
		private StringBuffer where;
		private Map<String, Object> params;
		
		QueryHelper(String keyword, String userId) {
			where = new StringBuffer();
			params = new HashMap<>();
			
			where.append("t.user.status = :cmtUsrSts ");
			params.put("cmtUsrSts", User.Status.Valid);

			where.append("and ");
			where.append("t.blog.user.status = :blgUsrSts ");
			params.put("blgUsrSts", User.Status.Valid);

			if(null != keyword && !keyword.isEmpty()) {
				where.append("and ");
				where.append("t.comment like :comment ");
				params.put("comment", "%" + keyword + "%");
			}
			
			if(null != userId && !userId.isEmpty()) {
				where.append("and ");
				where.append("t.user.loginId = :userId ");
				params.put("userId", userId);
			}
		}
		
		String getWhere() {
			return where.toString();
		}
		
		public Map<String, Object> getParams() {
			return params;
		}
	}

	@Override
	public void saveComment(Comment comment) {
		dao.update(comment);
	}

}
