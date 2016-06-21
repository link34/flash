package com.solt.flash.model.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.solt.flash.common.ApplicationException;
import com.solt.flash.common.ApplicationException.ErrorType;
import com.solt.flash.common.PasswordUtils;
import com.solt.flash.dao.imp.UserDao;
import com.solt.flash.entity.User;
import com.solt.flash.entity.User.Role;
import com.solt.flash.entity.User.Status;
import com.solt.flash.model.UserModel;

@Local
@Stateless
public class UserModelImp implements UserModel {

	private static final long serialVersionUID = 1L;

	@Inject
    public UserDao userDao;

    public void checkLoginId(String loginId) {
    	User user = userDao.findById(loginId);
    	if(null != user) {
    		throw new ApplicationException("Your login Id has been used. Please enter another login id.", ErrorType.Warning);
    	}
    }

    public void createUser(User user) {
    	user.setPassword(PasswordUtils.encript(user.getPassword()));
    	userDao.insert(user);
    }

    public void editUser(User user) {
    	userDao.update(user);
    }

	@Override
	public User getUser(String loginId) {
		User user = userDao.findById(loginId);
		user.getCommentCount();
		user.getBlogsCount();
		return user;
	}

	@Override
	public void changePass(String loginId, String oldPass, String newPass, String confPass) {
		
		// check user old password
		User user = userDao.findById(loginId);
		if(!user.getPassword().equals(PasswordUtils.encript(oldPass))) {
			throw new ApplicationException("Please check your old password!", ErrorType.Error);
		}
		
		// check new password
		if(!newPass.equals(confPass)) {
			throw new ApplicationException("Please check your new password and confirm password!", ErrorType.Error);
		}
		
		// set new password
		user.setPassword(PasswordUtils.encript(newPass));
		
		// update user
		userDao.update(user);
	}

	@Override
	public List<User> find(String name, Status status) {
		
		QueryHelper helper = new QueryHelper(name, status);
		List<User> users = userDao.select(helper.getWhere(), helper.getParams());
		users.forEach(a -> {
			a.getCommentCount();
			a.getBlogsCount();
		});
		
		return users;
	}

	@Override
	public List<User> find(String name, Status status, int start, int limit) {
		QueryHelper helper = new QueryHelper(name, status);
		List<User> users = userDao.select(helper.getWhere(), helper.getParams(), null, start, limit);
		users.forEach(a -> {
			a.getCommentCount();
			a.getBlogsCount();
		});
		
		return users;
	}

	@Override
	public long findCount(String name, Status status) {
		QueryHelper helper = new QueryHelper(name, status);
		return userDao.selectCount(helper.getWhere(), helper.getParams());
	}
	
	class QueryHelper {
		
		private StringBuffer sb;
		private Map<String, Object> params;
		
		QueryHelper(String name, Status status) {
			sb = new StringBuffer();
			params = new HashMap<>();
			
			if(null != name) {
				sb.append("t.name like :name ");
				params.put("name" ,name + "%");
			}
			
			if(null != status) {
				if(params.size() > 0) {
					sb.append("and ");
				}
				sb.append("t.status = :status ");
				params.put("status", status);
			}
			
			if(params.size() > 0) {
				sb.append("and ");
			}
			
			sb.append("t.role = :role ");
			params.put("role", Role.Member);
		}
		
		String getWhere() {
			return sb.toString();
		}
		
		public Map<String, Object> getParams() {
			return params;
		}
	}

}