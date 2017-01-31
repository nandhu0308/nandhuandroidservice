package com.limitless.services.payment.PaymentService.util;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.limitless.services.engage.dao.EngageCustomerManager;
import com.limitless.services.engage.dao.EngageSellerManager;

public class AuthenticationUtil {
	
	final static Logger logger = Logger.getLogger(AuthenticationUtil.class);
	
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
		
		logger.info(userId + " : " + password);
		
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
	
	public boolean authenticateUser(String sessionKey){
		boolean isUserAuthenticated = false;
		if(sessionKey==null){
			return isUserAuthenticated;
		}
		
		byte[] sessionKeyBytes = java.util.Base64.getDecoder().decode(sessionKey);
		String sessionKeyString = new String(sessionKeyBytes);
		logger.info("Token : " +sessionKeyString);
		StringTokenizer tokenizer = new StringTokenizer(sessionKeyString, ".");
		int sessionId = Integer.parseInt(tokenizer.nextToken());
		String sessionToken = tokenizer.nextToken();
		
		JSONObject tokenJson = new JSONObject(sessionToken);
		String role = tokenJson.getString("role");
		
		if(role.equals("customer")){
			EngageCustomerManager manager = new EngageCustomerManager();
			isUserAuthenticated = manager.authenticateCustomer(sessionToken, sessionId);
		}
		
		else if(role.equals("merchant")){
			EngageSellerManager manager = new EngageSellerManager();
			isUserAuthenticated = manager.authenticateMerchant(sessionToken, sessionId);
		}
		
		return isUserAuthenticated;
	}
	
}
