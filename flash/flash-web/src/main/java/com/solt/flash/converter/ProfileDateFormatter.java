package com.solt.flash.converter;

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
public class ProfileDateFormatter implements Converter{
	
	private DateFormat df;
	
	@PostConstruct
	private void init() {
		df = new SimpleDateFormat("d MMMM, yyyy");
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
		if(null != value && value instanceof Date) {
			Date d = (Date) value;
			return df.format(d);
		}
		return null;
	}

}
