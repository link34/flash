package com.solt.flash.adm.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

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
	
	@PostConstruct
	private void init() {
		
		userId = ParamsHelper.getParam("user");
		search();
	}
	
	public void search() {
		comments = model.searchComments(keyword, userId);
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
