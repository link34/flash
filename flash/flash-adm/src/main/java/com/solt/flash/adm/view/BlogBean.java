package com.solt.flash.adm.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.solt.flash.adm.common.CategoryProducer;
import com.solt.flash.adm.common.ParamsHelper;
import com.solt.flash.entity.Blog;
import com.solt.flash.entity.Category;
import com.solt.flash.entity.User;
import com.solt.flash.model.BlogModel;
import com.solt.flash.model.UserModel;
import com.solt.flash.model.BlogModel.SearchParam;

@Named
@ViewScoped
public class BlogBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String keyword;
	private Category category;
	private User user;
	
	private List<Blog> blogList;

	@Inject
	private BlogModel model;
	@Inject
	private CategoryProducer catProducer;
	@Inject
	private UserModel userModel;
	
	@PostConstruct
	private void init() {
		// category id
		String strCatId = ParamsHelper.getParam("cat");
		// user id
		String userId = ParamsHelper.getParam("user");
		
		if(null != strCatId) {
			category = catProducer.findById(Integer.parseInt(strCatId));
		}
		
		if(null != userId) {
			user = userModel.getUser(userId);
		}
		
		search();
	}
	
	public void search() {
		
		Map<SearchParam, Object> searchParams = new HashMap<>();
		searchParams.put(SearchParam.Category, category);
		searchParams.put(SearchParam.User, user);
		searchParams.put(SearchParam.Keyword, keyword);
		
		blogList = model.searchBlog(searchParams);
	}
	
	public void delete(Blog blog) {
		model.deleteBlog(blog);
		search();
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Blog> getBlogList() {
		return blogList;
	}

	public void setBlogList(List<Blog> blogList) {
		this.blogList = blogList;
	}

	public BlogModel getModel() {
		return model;
	}

	public void setModel(BlogModel model) {
		this.model = model;
	}
	
}
