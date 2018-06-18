package com.iteye.wwwcomy.authservice.util;

import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;

public class SaltUtil {
	private static SecureRandom secureRandom = new SecureRandom();
	private static final int SALT_LENGTH = 32;

	public static String generateSaltString() {
		byte[] salt = new byte[SALT_LENGTH];
		secureRandom.nextBytes(salt);
		return Base64.encodeBase64String(salt);
	}
}
