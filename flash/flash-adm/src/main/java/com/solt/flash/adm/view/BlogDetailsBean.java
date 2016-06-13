package com.solt.flash.adm.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.solt.flash.adm.common.ParamsHelper;
import com.solt.flash.entity.Blog;
import com.solt.flash.entity.Comment;
import com.solt.flash.model.BlogModel;

@Named
@ViewScoped
public class BlogDetailsBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Blog blog;
	@Inject
	private BlogModel model;
	
	@PostConstruct
	private void init() {
		String strId = ParamsHelper.getParam("id");
		if(null != strId) {
			blog = model.findBlogById(Long.parseLong(strId));
		}
	}
	
	public String delete(Blog blog) {
		model.deleteBlog(blog);
		return "/admin/blogs?faces-redirect=true";
	}

	public void deleteComment(Comment comment) {
		blog.removeComment(comment);
		model.saveBlog(blog);
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}
}
