package com.solt.flash.image;

import java.io.Serializable;

import javax.ejb.Local;
import javax.servlet.http.Part;

@Local
public interface FlashImageService extends Serializable {
	
	String saveImage(String loginId, Part file);

}
