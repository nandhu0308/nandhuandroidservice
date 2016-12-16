package com.limitless.services.payment.PaymentService;

public class AuthTokenBean {
	private String message;
	private String authToken;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
}
