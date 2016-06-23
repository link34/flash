package com.solt.flash.model.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.solt.flash.dao.imp.BlogDao;
import com.solt.flash.entity.Blog;
import com.solt.flash.entity.User;
import com.solt.flash.model.BlogModel;

@Local
@Stateless
public class BlogModelImp implements BlogModel {

	private static final long serialVersionUID = 1L;

	@Inject
	private BlogDao blogDao;
	
    public Blog findBlogById(long id) {
    	Blog b = blogDao.findById(id);
    	b.getComments().size();
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
		SearchHelper helper = new SearchHelper(params);
        return blogDao.select(helper.getSql(), helper.getParams(), " order by t.security.creation desc");
	}
	

	@Override
	public List<Blog> searchBlog(Map<SearchParam, Object> params, int start, int offset) {
		SearchHelper helper = new SearchHelper(params);
		return blogDao.select(helper.getSql(), helper.getParams(), " order by t.security.creation desc", start, offset);
	}

	@Override
	public long searchBlogCount(Map<SearchParam, Object> params) {
		SearchHelper helper = new SearchHelper(params);
		return blogDao.selectCount(helper.getSql(), helper.getParams());
	}
	
	class SearchHelper {
		
		private StringBuffer sb;
		private Map<String, Object> searchParam;
		
		public SearchHelper(Map<SearchParam, Object> params) {
			sb = new StringBuffer();
			searchParam = new HashMap<>();
			
			// valid user
			sb.append("t.user.status = :userStatus ");
			searchParam.put("userStatus", User.Status.Valid);			

			// User
			Object userValue = params.get(SearchParam.User);
			if(null != userValue) {
				sb.append("and ");
				sb.append("t.user = :user ");
				searchParam.put("user", userValue);
			}
			
			// Category
			Object catValue = params.get(SearchParam.Category);
			if(null != catValue) {
				sb.append("and ");
				sb.append("t.category = :cat ");
				searchParam.put("cat", catValue);
			}
			
			// DateFrom
			Object fromValue = params.get(SearchParam.DateFrom);
			if(null != fromValue) {
				sb.append("and ");
				sb.append("t.publishDate >= :fDate ");
				searchParam.put("fDate", fromValue);
			}
			
			// DateTo
			Object toValue = params.get(SearchParam.DateTo);
			if(null != toValue) {
				sb.append("and ");
				sb.append("t.publishDate <= :tDate ");
				searchParam.put("tDate", toValue);
			}
			
			Object statusValue = params.get(SearchParam.Status);
			if(null != statusValue) {
				sb.append("and ");
				sb.append("t.status = :status ");
				searchParam.put("status", statusValue);
			}
			
			// Keyword
			Object keywordValue = params.get(SearchParam.Keyword);
			if(null != keywordValue && !keywordValue.toString().isEmpty()) {
				sb.append("and ");
				sb.append("t.title like :keyword ");
				searchParam.put("keyword", "%" + keywordValue.toString() + "%");
			}
			
			// Tag
			Object tagValue = params.get(SearchParam.Tag);
			if(null != tagValue && !tagValue.toString().isEmpty()) {
				sb.append("and ");
				sb.append(":tag MEMBER OF t.tags");
				searchParam.put("tag", tagValue);
			}
			
		}
		
		public String getSql() {
			return sb.toString();
		}
		
		public Map<String, Object> getParams() {
			return this.searchParam;
		}
		
	}

}