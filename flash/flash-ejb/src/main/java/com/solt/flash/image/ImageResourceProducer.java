package com.solt.flash.image;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@ApplicationScoped
public class ImageResourceProducer {

	@ImageStorage
	@Produces
	private String imageStorage;
	
	@ImageBaseUrl
	@Named
	@Produces
	private String imageBaseUrl;
	
	@Named
	@DefaultProfileImage
	@Produces
	private String defaultProfile;
	
	@Named
	@DefaultCoverImage
	@Produces
	private String defaultCover;
	
	@PostConstruct
	private void init() {
		imageStorage = System.getProperty("app.flash.image.storage");
		imageBaseUrl = System.getProperty("app.flash.image.baseUrl");
		
		defaultProfile = "user.png";
		defaultCover = "cover.png";
	}
}
