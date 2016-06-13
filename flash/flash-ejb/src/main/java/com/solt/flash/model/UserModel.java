package com.solt.flash.model;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import com.solt.flash.entity.User;
import com.solt.flash.entity.User.Status;

@Local
public interface UserModel extends Serializable {
	
	public User getUser(String loginId);

    public void checkLoginId(String loginId);

    public void createUser(User user);

    public void editUser(User user);

    public void changePass(String loginId, String oldPass, String newPass, String confPass);
    
    public List<User> find(String name, Status status);

}