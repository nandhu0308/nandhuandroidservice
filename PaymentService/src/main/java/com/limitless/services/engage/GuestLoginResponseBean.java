package com.limitless.services.engage;

public class GuestLoginResponseBean {
	private int customerId;
	private int guestId;
	private int sessionId;
	private String sessionKey;
	private String message;
	private String guestCity;
	private String guestCountry;
	private String guestZip;
	private String guestCountryIsoCode;
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getGuestId() {
		return guestId;
	}
	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}
	public int getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getGuestCity() {
		return guestCity;
	}
	public void setGuestCity(String guestCity) {
		this.guestCity = guestCity;
	}
	public String getGuestCountry() {
		return guestCountry;
	}
	public void setGuestCountry(String guestCountry) {
		this.guestCountry = guestCountry;
	}
	public String getGuestZip() {
		return guestZip;
	}
	public void setGuestZip(String guestZip) {
		this.guestZip = guestZip;
	}
	public String getGuestCountryIsoCode() {
		return guestCountryIsoCode;
	}
	public void setGuestCountryIsoCode(String guestCountryIsoCode) {
		this.guestCountryIsoCode = guestCountryIsoCode;
	}
}
