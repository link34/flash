package com.solt.flash.adm.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.solt.flash.adm.common.ListCount;
import com.solt.flash.adm.common.ListType;
import com.solt.flash.adm.common.ParamsHelper;
import com.solt.flash.entity.Comment;
import com.solt.flash.model.CommentModel;

@Named
@ViewScoped
public class CommentBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Comment> comments;
	private String keyword;
	
	private String userId;
	
	@Inject
	private CommentModel model;
	
	@ListCount(ListType.Comments)
	@Inject
	private int limit;
	
	@PostConstruct
	private void init() {
		
		comments = new ArrayList<>();
		userId = ParamsHelper.getParam("user");
		search();
	}
	
	public void formSearch() {
		comments.clear();
		search();
	}
	
	public void search() {
		
		long total = model.searchCommentCount(keyword, userId);
		
		if(total > comments.size()) {
			comments.addAll(model.searchComments(keyword, userId, comments.size(), limit));
		}
	}
	
	public void delete(Comment comment) {
		model.deleteComment(comment);
		search();
	}
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
