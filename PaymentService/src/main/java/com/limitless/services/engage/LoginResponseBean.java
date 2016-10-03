package com.limitless.services.engage;

public class LoginResponseBean {
	
	private int loginStatus = -1;
	
	private String message = "Failure";

	public int getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(int loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
