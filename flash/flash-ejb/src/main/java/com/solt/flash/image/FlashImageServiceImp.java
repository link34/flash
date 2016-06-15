package com.solt.flash.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.Part;

import com.solt.flash.common.ApplicationException;

@Stateless
@Local
public class FlashImageServiceImp implements FlashImageService {

	private static final long serialVersionUID = 1L;
	
	@ImageStorage
	@Inject
	private String imageStorage;
	
	@Override
	public String saveImage(String loginId, Part file) {
		
		try {
			String suffix = getSuffix(file);
			String fileName = getFileName(suffix);
			
			File userImageDir = new File(imageStorage, loginId);
			if(!userImageDir.exists()) {
				userImageDir.mkdir();
			}
			
			
			File outFile = new File(userImageDir, fileName);
			BufferedImage inImage = ImageIO.read(file.getInputStream());
			
			ImageIO.write(inImage, suffix, outFile);
			
			return fileName;
		} catch (IOException e) {
			throw new ApplicationException(e);
		}
	}
	
	private String getFileName(String suffix) {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")).concat(".").concat(suffix);
	}

	private String getSuffix(Part file) {
		String fileName = file.getSubmittedFileName();
		String [] array = fileName.split("\\.");
		return array[array.length - 1];
	}
	
}
