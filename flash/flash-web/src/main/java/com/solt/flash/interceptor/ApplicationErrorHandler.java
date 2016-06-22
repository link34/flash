package com.solt.flash.interceptor;

import java.io.Serializable;

import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.solt.flash.common.ApplicationException;

@ErrorHandler
@Interceptor
public class ApplicationErrorHandler implements Serializable{

	private static final long serialVersionUID = 1L;

	@AroundInvoke
	public Object handle(InvocationContext ic) throws Exception {
		
		try {
			return ic.proceed();
		} catch (ApplicationException e) {
			FacesMessage message = new FacesMessage("Application Error", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (EJBException e) {
			FacesMessage message = new FacesMessage("Application Error", 
					(null != e.getCause()) ? e.getCause().getMessage() : e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return "/home";
	}

}
