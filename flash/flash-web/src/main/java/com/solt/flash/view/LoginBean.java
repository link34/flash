package com.solt.flash.view;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.solt.flash.common.ApplicationException;
import com.solt.flash.common.ApplicationException.ErrorType;
import com.solt.flash.entity.User;
import com.solt.flash.entity.User.Status;
import com.solt.flash.interceptor.ErrorHandler;
import com.solt.flash.model.UserModel;

@Model
public class LoginBean {

    private String name;
    private String loginId;
    private String password;

    @Inject
    private UserModel userModel;
    
    @ErrorHandler
    public String login() {
		internalLogin(loginId, password);
    	return "/home?faces-redirect=true";
    }

    @ErrorHandler
    public String signUp() {
    	
    	userModel.checkLoginId(loginId);
    	
    	User user = new User();
    	user.setName(name);
    	user.setLoginId(loginId);
    	user.setPassword(password);
    	user.getSecurity().setCreateUser(user.getLoginId());
    	user.getSecurity().setModUser(user.getLoginId());
    	userModel.createUser(user);
    	internalLogin(loginId, password);
    	
    	return "/home?faces-redirect=true";
    }

    private void internalLogin(String loginId, String password) {
    	try {
    		
    		User u = userModel.getUser(loginId);
    		if(u.getStatus().equals(Status.Valid)) {
            	HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    			request.login(loginId, password);
    		} else {
    			throw new ApplicationException("You are rejected because you break the rules and regulation of this site.", ErrorType.Warning);
    		}
    		
		} catch (ServletException e) {
			throw new ApplicationException("Please check Login ID and Password!", ErrorType.Warning);
		}
    }

	public String logout() {
    	FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    	return "/home?faces-redirect=true";
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}