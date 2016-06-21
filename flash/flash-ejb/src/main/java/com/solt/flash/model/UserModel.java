package com.solt.flash.model;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import com.solt.flash.entity.User;
import com.solt.flash.entity.User.Status;

@Local
public interface UserModel extends Serializable {
	
	User getUser(String loginId);
    void checkLoginId(String loginId);
    void createUser(User user);
    void editUser(User user);
    void changePass(String loginId, String oldPass, String newPass, String confPass);
    List<User> find(String name, Status status);
    List<User> find(String name, Status status, int start, int limit);
    long findCount(String name, Status status);

}