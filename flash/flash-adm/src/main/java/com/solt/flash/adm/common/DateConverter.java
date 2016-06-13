package com.solt.flash.adm.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.solt.flash.common.ApplicationException;

@Named
@Dependent
public class DateConverter implements Converter{
	
	private DateFormat df;
	
	@PostConstruct
	private void init() {
		df = new SimpleDateFormat("yyyy-MM-dd");
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(null != value) {
			try {
				return df.parse(value);
			} catch (ParseException e) {
				throw new ApplicationException(e);
			}
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
