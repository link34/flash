package com.solt.flash.view;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import com.solt.flash.entity.Blog;
import com.solt.flash.entity.Blog.Status;
import com.solt.flash.entity.User;
import com.solt.flash.image.FlashImageService;
import com.solt.flash.interceptor.ErrorHandler;
import com.solt.flash.model.BlogModel;
import com.solt.flash.producers.LoginUser;

@Named
@ViewScoped
public class NewBlogBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Blog blog;

    @Inject
    @LoginUser
    private User loginUser;

    @Inject
    private BlogModel model;
    
    private boolean publish;
    private String tags;
    
    private Part file;
    
    @Inject
    private FlashImageService imgService;
    
    @PostConstruct
    private void init() {
    	blog = new Blog();
    	blog.setUser(loginUser);
    }

    @ErrorHandler
    public String save() {
    	if(null != tags) {
    		Set<String> set = new HashSet<>(Arrays.asList(tags.split(",")));
        	blog.setTags(set);
    	}
    	
    	blog.setStatus((publish) ? Status.Published : Status.Edit);
		model.createBlog(blog);
		return "/blog?faces-redirect=true&id=" + blog.getId();
    }
    
    public void uploadImage() {
    	blog.setImage(imgService.saveImage(loginUser.getLoginId(), file));
    }

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public boolean isPublish() {
		return publish;
	}

	public void setPublish(boolean publish) {
		this.publish = publish;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}
	
}