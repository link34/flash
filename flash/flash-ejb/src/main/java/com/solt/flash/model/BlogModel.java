package com.solt.flash.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.solt.flash.entity.Blog;
import com.solt.flash.entity.Comment;
import com.solt.flash.entity.User;

@Local
public interface BlogModel extends Serializable {

    public Blog findBlogById(long id);

    public List<Blog> searchBlog(Map<SearchParam, Object> params);

    public List<Blog> searchBlog(Map<SearchParam, Object> params, int start, int offset);
    public long searchBlogCount(Map<SearchParam, Object> params);

    public void deleteBlog(Blog blog);

    public void createBlog(Blog blog);

    public void saveBlog(Blog blog);
    
    List<Comment> getUserComments(User user);
    
    void saveComment(Comment comment);

    public enum SearchParam {
        Category,
        Tag,
        DateFrom,
        DateTo,
        Keyword,
        Status,
        User
    }

}