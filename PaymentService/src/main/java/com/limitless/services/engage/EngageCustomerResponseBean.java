package com.limitless.services.engage;

public class EngageCustomerResponseBean {
	
	private int status;
	private int customerId;
	private String message = "Failure";
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
	
}
