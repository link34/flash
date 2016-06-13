package com.solt.flash.view;

import java.io.Serializable;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.solt.flash.entity.User;
import com.solt.flash.interceptor.ErrorHandler;
import com.solt.flash.model.UserModel;
import com.solt.flash.producers.LoginUser;

@Named
@ViewScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@LoginUser
	@Inject
    private User user;
	
	private String oldPass;
	private String newPass;
	private String confPass;
    
    @Inject
    private UserModel model;

    @ErrorHandler
    public String editUserInfo() {
    	user.getSecurity().setModification(new Date());
    	user.getSecurity().setModUser(user.getLoginId());
    	model.editUser(user);
    	return "/user/profile?faces-redirect=true";
    }

    @ErrorHandler
    public String changePass() {
    	user.getSecurity().setModification(new Date());
    	user.getSecurity().setModUser(user.getLoginId());
    	model.changePass(user.getLoginId(), oldPass, newPass, confPass);
    	return "/home?faces-redirect=true";
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getConfPass() {
		return confPass;
	}

	public void setConfPass(String confPass) {
		this.confPass = confPass;
	}

}