package com.limitless.services.engage;

public class SellerLoginRequestBean {
	
	private String emailId;
	private String passwd;
	private String sellerDeviceId;
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getSellerDeviceId() {
		return sellerDeviceId;
	}
	public void setSellerDeviceId(String sellerDeviceId) {
		this.sellerDeviceId = sellerDeviceId;
	}

}
