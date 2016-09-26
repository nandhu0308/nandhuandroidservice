package com.limitless.services.payment.PaymentService.util;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.codec.binary.Base64;

import com.limitless.services.engage.dao.EngageCustomerManager;

public class AuthenticationUtil {
	
	private static AuthenticationUtil authntil = null;
	
	private AuthenticationUtil(){
		
	}
	
	public static AuthenticationUtil getInstance(){
		if(authntil == null){
			authntil = new AuthenticationUtil();
		} 
		return authntil;
	}
	
	public boolean validateCredentials(String authnCredentials, boolean isAdmin){
		boolean isValidCredentials = false;
		
		if (null == authnCredentials){
			return isValidCredentials;
		}

		final String encodedUserPassword = authnCredentials.replaceFirst("Basic" + " ", "");
		String userIdAndPasswd = null;
		try {
			byte[] decodedBytes = Base64.decodeBase64(encodedUserPassword);
			userIdAndPasswd = new String(decodedBytes, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		final StringTokenizer tokenizer = new StringTokenizer(userIdAndPasswd, ":");
		final int userId = Integer.parseInt(tokenizer.nextToken());
		final String password = tokenizer.nextToken();
		
		System.out.println(userId + " : " + password);
		
		//tbd - pass it as environment variable
		if(isAdmin){
			//Admin ID - only if the admin ID
			if(userId == 100000){
				EngageCustomerManager manager = new EngageCustomerManager();
				isValidCredentials = manager.validateCredentials(userId, password);
			}
		} else {
			EngageCustomerManager manager = new EngageCustomerManager();
			isValidCredentials = manager.validateCredentials(userId, password);
		}
		
		
		return isValidCredentials;
	}
}
