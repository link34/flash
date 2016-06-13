package com.solt.flash.adm.view;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;

@Model
public class LogoutBean {

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/admin/home?faces-redirect=true";
	}
}
