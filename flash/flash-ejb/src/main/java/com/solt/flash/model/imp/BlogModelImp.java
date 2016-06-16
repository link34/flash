package com.solt.flash.model.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.solt.flash.dao.imp.BlogDao;
import com.solt.flash.dao.imp.CommentDao;
import com.solt.flash.entity.Blog;
import com.solt.flash.entity.Comment;
import com.solt.flash.entity.User;
import com.solt.flash.model.BlogModel;

@Local
@Stateless
public class BlogModelImp implements BlogModel {

	private static final long serialVersionUID = 1L;

	@Inject
	private BlogDao blogDao;
	
	@Inject
	private CommentDao commentDao;

    public Blog findBlogById(long id) {
    	Blog b = blogDao.findById(id);
    	b.getComments().size();
    	b.getTagList();
        return b;
    }

    public void deleteBlog(Blog blog) {
    	blogDao.delete(blog.getId());
    }

    public void createBlog(Blog blog) {
    	blogDao.insert(blog);
    }

    public void saveBlog(Blog blog) {
    	blogDao.update(blog);
    }

	@Override
	public List<Blog> searchBlog(Map<SearchParam, Object> params) {
		
		StringBuffer sb = new StringBuffer();
		Map<String, Object> searchParam = new HashMap<>();

		// User
		Object userValue = params.get(SearchParam.User);
		if(null != userValue) {
			sb.append("t.user = :user ");
			searchParam.put("user", userValue);
		}
		
		// Category
		Object catValue = params.get(SearchParam.Category);
		if(null != catValue) {
			if(searchParam.size() > 0) {
				sb.append("and ");
			}
			
			sb.append("t.category = :cat ");
			searchParam.put("cat", catValue);
		}
		
		// DateFrom
		Object fromValue = params.get(SearchParam.DateFrom);
		if(null != fromValue) {
			if(searchParam.size() > 0) {
				sb.append("and ");
			}
			
			sb.append("t.publishDate >= :fDate ");
			searchParam.put("fDate", fromValue);
		}
		
		// DateTo
		Object toValue = params.get(SearchParam.DateTo);
		if(null != toValue) {
			if(searchParam.size() > 0) {
				sb.append("and ");
			}
			
			sb.append("t.publishDate <= :tDate ");
			searchParam.put("tDate", toValue);
		}
		
		Object statusValue = params.get(SearchParam.Status);
		if(null != statusValue) {
			if(searchParam.size() > 0) {
				sb.append("and ");
			}
			
			sb.append("t.status = :status ");
			searchParam.put("status", statusValue);
		}
		
		// Keyword
		Object keywordValue = params.get(SearchParam.Keyword);
		if(null != keywordValue && !keywordValue.toString().isEmpty()) {
			if(searchParam.size() > 0) {
				sb.append("and ");
			}
			
			sb.append("t.title like :keyword ");
			searchParam.put("keyword", "%" + keywordValue.toString() + "%");
		}
		
		// Tag
		Object tagValue = params.get(SearchParam.Tag);
		if(null != tagValue && !tagValue.toString().isEmpty()) {
			if(searchParam.size() > 0) {
				sb.append("and ");
			}
			
			sb.append(":tag MEMBER OF t.tags");
			searchParam.put("tag", tagValue);
		}
		
		// valid user
		if(searchParam.size() > 0) {
			sb.append("and ");
		}
		sb.append("t.user.status = :userStatus");
		searchParam.put("userStatus", User.Status.Valid);

        return blogDao.select(sb.toString(), searchParam);
	}

	@Override
	public List<Comment> getUserComments(User user) {
		
		if(user.getStatus().equals(User.Status.Valid)) {
			String where = "t.user = :user";
			Map<String, Object> params = new HashMap<>();
			params.put("user", user);
			
			return commentDao.select(where, params);
		}
		return null;
	}

	@Override
	public void saveComment(Comment comment) {
		commentDao.update(comment);
	}

}