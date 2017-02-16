package com.limitless.services.engage;

public class Guest2CustomerLoginRequestBean {
	private int sessionId;
	private int guestId;
	private String customerMobileNumber;
	private String customerPasswd;
	public int getGuestId() {
		return guestId;
	}
	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}
	public String getCustomerMobileNumber() {
		return customerMobileNumber;
	}
	public void setCustomerMobileNumber(String customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}
	public String getCustomerPasswd() {
		return customerPasswd;
	}
	public void setCustomerPasswd(String customerPasswd) {
		this.customerPasswd = customerPasswd;
	}
	public int getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
}
