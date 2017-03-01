package com.limitless.services.engage;

public class LoginResponseBean {
	
	private int loginStatus = -1;
	
	private String message = "Failure - Check Login Credentials";
	
	private int customerId;
	private String customerName;
	private String customerEmail;
	private String customerCity;
	private String customerCountry;
	private String customerZip;
	private String customerCountryCode;
	private String customerCountryIsoCode;
	private String sessionKey;
	private int sessionId;
	private String deviceId;
	
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

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getCustomerCity() {
		return customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public String getCustomerCountry() {
		return customerCountry;
	}

	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
	}

	public String getCustomerZip() {
		return customerZip;
	}

	public void setCustomerZip(String customerZip) {
		this.customerZip = customerZip;
	}

	public String getCustomerCountryCode() {
		return customerCountryCode;
	}

	public void setCustomerCountryCode(String customerCountryCode) {
		this.customerCountryCode = customerCountryCode;
	}

	public String getCustomerCountryIsoCode() {
		return customerCountryIsoCode;
	}

	public void setCustomerCountryIsoCode(String customerCountryIsoCode) {
		this.customerCountryIsoCode = customerCountryIsoCode;
	}

}
