package com.solt.flash.converter;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.solt.flash.entity.Category;

@Named
@RequestScoped
public class CategoryConverter implements Converter  {
	
	@Inject
	private List<Category> allCategory;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(null != value) {
			for (Category category : allCategory) {
				if(category.getCategory().equals(value)) {
					return category;
				}
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(null != value) {
			Category c = (Category) value;
			return c.getCategory();
		}
		return null;
	}


}
