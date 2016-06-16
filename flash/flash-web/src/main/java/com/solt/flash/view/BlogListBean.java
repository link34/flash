package com.solt.flash.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.solt.flash.converter.ParamsUtils;
import com.solt.flash.entity.Blog;
import com.solt.flash.entity.Blog.Status;
import com.solt.flash.entity.Category;
import com.solt.flash.entity.User;
import com.solt.flash.interceptor.ErrorHandler;
import com.solt.flash.model.BlogModel;
import com.solt.flash.model.BlogModel.SearchParam;
import com.solt.flash.producers.LoginUser;
import com.solt.flash.model.CategoryModel;
import com.solt.flash.model.UserModel;

@Named
@ViewScoped
public class BlogListBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<Blog> blogs;
    private Category category;
    private String tag;
    private String keyword;
    private User user;
    
    @Inject
    private BlogModel model;
    @Inject
    private UserModel userModel;
    @Inject
    private CategoryModel catModel;
    
    @LoginUser
    @Inject
    private User loginUser;
    
    @PostConstruct
    private void init() {
    	try {
        	
        	String catId = ParamsUtils.getRequestParam("cat");
        	if(null != catId) {
        		category = catModel.findById(Integer.parseInt(catId));
        	}
        	
        	String loginId = ParamsUtils.getRequestParam("user");
        	
        	if(null != loginId) {
        		user = userModel.getUser(loginId);
        	}
        	
        	search();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @ErrorHandler
    public void delete(Blog blog) {
    	model.deleteBlog(blog);
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

    @ErrorHandler
    public void search() {
    	Map<SearchParam, Object> params = new HashMap<>();
    	params.put(SearchParam.Category, category);
    	params.put(SearchParam.Tag, tag);
    	params.put(SearchParam.Keyword, keyword);
    	params.put(SearchParam.User, user);
    	params.put(SearchParam.Status, Status.Published);
    	
    	blogs = model.searchBlog(params);
    }
    
    public String searchByKeyword() {
    	search();
    	return "/home";
    }
    
	public List<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}