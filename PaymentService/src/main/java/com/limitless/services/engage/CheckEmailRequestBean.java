package com.limitless.services.engage;

public class CheckEmailRequestBean {
	
	private String emailId;
	private String mobileNumber;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	@Override
	public String toString() {
		return "CheckEmailRequestBean [emailId=" + emailId + ", mobileNumber=" + mobileNumber + "]";
	}
}
