package com.solt.flash.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.solt.flash.entity.Comment;
import com.solt.flash.entity.User;
import com.solt.flash.interceptor.ErrorHandler;
import com.solt.flash.model.BlogModel;
import com.solt.flash.producers.LoginUser;

@Named
@ViewScoped
public class UserCommentsBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Comment> comments;

    @Inject
    private BlogModel model;
    @Inject
    @LoginUser
    private User loginUser;
    
    private Comment selectedComment;
    private String modalScript;
    
    @PostConstruct
    private void init() {
    	comments = model.getUserComments(loginUser);
    }

    @ErrorHandler
    public void edit(Comment comment) {
    	this.selectedComment = comment;
    	modalScript = "$('#editComment').openModal();";
    }
    
    @ErrorHandler
    public String save() {
    	selectedComment.getSecurity().setModification(new Date());
    	selectedComment.getSecurity().setModUser(loginUser.getLoginId());
    	model.saveComment(selectedComment);
    	
    	return "/user/comments?faces-redirect=true";
    }

    @ErrorHandler
    public String delete(Comment comment) {
    	comment.getBlog().removeComment(comment);
    	model.saveBlog(comment.getBlog());
    	return "/user/comments?faces-redirect=true";
    }
    
    public List<Comment> getComments() {
		return comments;
	}
    
    public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Comment getSelectedComment() {
		return selectedComment;
	}

	public void setSelectedComment(Comment selectedComment) {
		this.selectedComment = selectedComment;
	}

	public String getModalScript() {
		return modalScript;
	}

	public void setModalScript(String modalScript) {
		this.modalScript = modalScript;
	}
 
}