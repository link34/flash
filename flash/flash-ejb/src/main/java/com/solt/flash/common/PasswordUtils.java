package com.solt.flash.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordUtils {

	public static String encript(String pass) {
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte [] hash = digest.digest(pass.getBytes());
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			throw new ApplicationException(e);
		}
	}
}
