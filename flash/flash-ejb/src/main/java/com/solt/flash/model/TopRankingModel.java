package com.solt.flash.model;

import java.util.List;

import javax.ejb.Local;

import com.solt.flash.entity.Blog;

@Local
public interface TopRankingModel {

	List<Blog> getTopRanking(int limit);
}
