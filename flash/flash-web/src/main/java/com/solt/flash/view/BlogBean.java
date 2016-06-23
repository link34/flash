package com.solt.flash.view;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import com.solt.flash.entity.Blog;
import com.solt.flash.entity.Blog.Status;
import com.solt.flash.entity.Comment;
import com.solt.flash.entity.User;
import com.solt.flash.image.FlashImageService;
import com.solt.flash.interceptor.ErrorHandler;
import com.solt.flash.model.BlogModel;
import com.solt.flash.model.CommentModel;
import com.solt.flash.producers.LoginUser;

@Named
@ViewScoped
public class BlogBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Blog blog;
    private boolean publish;

    private String newComment;
    private Comment selectedComment;
    private String modalScript;
    
    private Part file;
    
    @LoginUser
    @Inject
    private User loginUser;

    @Inject
    private BlogModel blogModel;
    
    @Inject
    private CommentModel commentModel;
    
    @Inject
    private FlashImageService imageService;

    @PostConstruct
    public void init() {
    	String id = FacesContext.getCurrentInstance()
    				.getExternalContext()
    				.getRequestParameterMap().get("id");
    	if(null != id) {
    		blog = blogModel.findBlogById(Long.parseLong(id));
    		publish = blog.getStatus().equals(Status.Published);
    	}
    }

    @ErrorHandler
    public String save() {
    	blog.setStatus(publish ? Status.Published: Status.Edit);
    	blogModel.saveBlog(blog);
    	return "/blog?faces-redirect=true&id=" + blog.getId();
    }

    @ErrorHandler
    public String addComment() {
    	Comment comment = new Comment();
    	comment.setComment(newComment);
    	comment.setUser(loginUser);
    	blog.addComment(comment);
    	blogModel.saveBlog(blog);
    	return "/blog?faces-redirect=true&id=" + blog.getId();
    }

    @ErrorHandler
    public void editComment(Comment comment) {
    	this.selectedComment = comment;
    	modalScript = "$('#editComment').openModal();";
    }
    
    @ErrorHandler
    public void vote(String value) {
    	if(blog.getRate().containsKey(loginUser.getLoginId())) {
    		blog.getRate().remove(loginUser.getLoginId());
    	} else {
    		blog.getRate().put(loginUser.getLoginId(), value);
    	}
    	
    	blogModel.saveBlog(blog);
    }

    @ErrorHandler
    public String saveComment() {
    	
    	for(Comment cmt : blog.getCommentList()) {
    		if(cmt.getId() == selectedComment.getId()) {
    			cmt.setComment(selectedComment.getComment());
    			cmt.getSecurity().setModification(new Date());
    			cmt.getSecurity().setModUser(loginUser.getLoginId());
    	    	commentModel.saveComment(cmt);
    		}
    	}
    	
    	return "/blog?faces-redirect=true&id=" + blog.getId();
    }

    @ErrorHandler
    public String deleteComment(Comment comment) {
    	blog.removeComment(comment);
    	blogModel.saveBlog(blog);
    	return "/blog?faces-redirect=true&id=" + blog.getId();
    }
    
    public void uploadImage() {
    	blog.setImage(imageService.saveImage(loginUser.getLoginId(), file));
    }

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public String getNewComment() {
		return newComment;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}

	public Comment getSelectedComment() {
		return selectedComment;
	}

	public void setSelectedComment(Comment selectedComment) {
		this.selectedComment = selectedComment;
	}

	public boolean isPublish() {
		return publish;
	}

	public void setPublish(boolean publish) {
		this.publish = publish;
	}

	public String getModalScript() {
		return modalScript;
	}

	public void setModalScript(String modalScript) {
		this.modalScript = modalScript;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

}