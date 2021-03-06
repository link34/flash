package com.solt.flash.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.solt.flash.entity.Blog;
import com.solt.flash.entity.Blog.Status;
import com.solt.flash.entity.User;
import com.solt.flash.interceptor.ErrorHandler;
import com.solt.flash.model.BlogModel;
import com.solt.flash.model.BlogModel.SearchParam;
import com.solt.flash.producers.BlogListCount;
import com.solt.flash.producers.LoginUser;

@Named
@ViewScoped
public class UserBlogsBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Blog> blogs;

	@Inject
    private BlogModel model;
	@Inject
	@LoginUser
	private User loginUser;

    @BlogListCount
    @Inject
    private int limit;
    
	@ErrorHandler
	@PostConstruct
	private void init() {
		blogs = new ArrayList<>();
		search();
	}
	
	public void search() {
		Map<SearchParam, Object> params = new HashMap<>();
		params.put(SearchParam.User, loginUser);

    	// total count
    	long total = model.searchBlogCount(params);
    	
    	// start count
    	if(total > blogs.size()) {
        	// search
        	blogs.addAll(model.searchBlog(params, blogs.size(), limit));
    	}
    }

    @ErrorHandler
    public void delete(Blog blog) {
    	model.deleteBlog(blog);
    	init();
    }
    
    @ErrorHandler
    public void publish(Blog b) {
    	b.setStatus(Status.Published);
    	b.getSecurity().setModification(new Date());
    	b.getSecurity().setModUser(loginUser.getLoginId());
    	model.saveBlog(b);
    }
    
    @ErrorHandler
    public void unPublish(Blog b) {
    	b.setStatus(Status.Edit);
    	b.getSecurity().setModification(new Date());
    	b.getSecurity().setModUser(loginUser.getLoginId());
    	model.saveBlog(b);
    }

    @ErrorHandler
    public void vote(Blog blog, String value) {
    	if(blog.getRate().containsKey(loginUser.getLoginId())) {
    		blog.getRate().remove(loginUser.getLoginId());
    	} else {
    		blog.getRate().put(loginUser.getLoginId(), value);
    	}
    	
    	model.saveBlog(blog);
    }
    
	public List<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

}