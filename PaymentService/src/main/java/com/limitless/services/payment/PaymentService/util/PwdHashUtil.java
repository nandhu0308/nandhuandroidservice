package com.limitless.services.payment.PaymentService.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class PwdHashUtil {
	
	public static void main(String[] args) {
		
		/*MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
		}
		messageDigest.update(stringToEncrypt.getBytes());
		String encryptedString = new String(messageDigest.digest());
		
		System.out.println(encryptedString);*/
		
		System.out.println(DigestUtils.sha256Hex("anandh$123"));
		String stringToEncrypt = "100000:8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918";
		
		byte[]   bytesEncoded = Base64.encodeBase64(stringToEncrypt.getBytes());
		System.out.println("ecncoded value is " + new String(bytesEncoded ));

		// Decode data on other side, by processing encoded data
		byte[] valueDecoded= Base64.decodeBase64(bytesEncoded );
		System.out.println("Decoded value is " + new String(valueDecoded));
	}

}
