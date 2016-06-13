package com.solt.flash.converter;

import javax.faces.context.FacesContext;

public class ParamsUtils {

	public static String getRequestParam(String paramName) {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(paramName);
	}
}
