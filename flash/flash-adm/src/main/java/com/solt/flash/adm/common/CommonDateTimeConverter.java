package com.solt.flash.adm.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

@Named
@RequestScoped
public class CommonDateTimeConverter implements Converter {

	private DateFormat df;
	
	@PostConstruct
	private void init() {
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			if(null != value) {
				return df.parse(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(null != value) {
			Date d = (Date) value;
			return df.format(d);
		}
		return null;
	}

}
