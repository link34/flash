package com.solt.flash.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;

@Entity
public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	private Blog blog;

	@ManyToOne
	private User user;

	private String comment;

	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable
	@MapKeyColumn(name="login")
	private Map<String, String> rate;

	@Embedded
	private SecurityInfo security;
	
	public Comment() {
		security = new SecurityInfo();
		rate = new HashMap<>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		security.setCreateUser(user.getLoginId());
		security.setModUser(user.getLoginId());
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Map<String, String> getRate() {
		return rate;
	}

	public void setRate(Map<String, String> rate) {
		this.rate = rate;
	}

	public SecurityInfo getSecurity() {
		return security;
	}

	public void setSecurity(SecurityInfo security) {
		this.security = security;
	}

}