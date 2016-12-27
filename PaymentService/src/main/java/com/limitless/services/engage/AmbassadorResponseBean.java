package com.limitless.services.engage;

public class AmbassadorResponseBean {
	private String ambassadorMobileNumber;
	private int merchantOnboardCount;
	private String message;
	public String getAmbassadorMobileNumber() {
		return ambassadorMobileNumber;
	}
	public void setAmbassadorMobileNumber(String ambassadorMobileNumber) {
		this.ambassadorMobileNumber = ambassadorMobileNumber;
	}
	public int getMerchantOnboardCount() {
		return merchantOnboardCount;
	}
	public void setMerchantOnboardCount(int merchantOnboardCount) {
		this.merchantOnboardCount = merchantOnboardCount;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
