package com.solt.flash.adm.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.solt.flash.adm.common.ListCount;
import com.solt.flash.adm.common.ListType;
import com.solt.flash.entity.User;
import com.solt.flash.entity.User.Status;
import com.solt.flash.model.UserModel;

@Named
@ViewScoped
public class UserBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private List<User> users;
	private String name;
	private Status status;
	
	private List<Status> statusList;
	
	@Inject
	private UserModel model;
	
	@ListCount(ListType.Users)
	@Inject
	private int limit;
	
	@PostConstruct
	private void init() {
		status = Status.Valid;
		statusList = Arrays.asList(Status.values());
		users = new ArrayList<>();
		search();
	}
	
	public void switchStatus(User user) {
		user.setStatus((user.getStatus().equals(Status.Valid)) ? Status.UnValid : Status.Valid);
		model.editUser(user);
		users.clear();
		search();
	}
	
	public void allowUser(User user) {
		user.setStatus(Status.Valid);
		model.editUser(user);
		users.clear();
		search();
	}
	
	public void formSearch() {
		users.clear();
		search();
	}
	
	public void search() {
		
		long total = model.findCount(name, status);
		
		if(total > users.size()) {
			users.addAll(model.find(name, status, users.size(), limit));
		}
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Status> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Status> statusList) {
		this.statusList = statusList;
	}
	
}
