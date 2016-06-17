package com.solt.flash.adm.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@SessionScoped
public class LogoutBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/admin/users.xhtml?faces-redirect=true";
	}
}
