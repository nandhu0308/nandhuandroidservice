package com.limitless.services.engage;

public class GuestLoginRequestBean {
	private String deviceMac;
	private String utmSource;
	private String utmMedium;
	private String utmCampign;
	private String utmTerm;
	private String utmContent;

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

	public String getUtmSource() {
		return utmSource;
	}

	public void setUtmSource(String utmSource) {
		this.utmSource = utmSource;
	}

	public String getUtmMedium() {
		return utmMedium;
	}

	public void setUtmMedium(String utmMedium) {
		this.utmMedium = utmMedium;
	}

	public String getUtmCampign() {
		return utmCampign;
	}

	public void setUtmCampign(String utmCampign) {
		this.utmCampign = utmCampign;
	}

	public String getUtmTerm() {
		return utmTerm;
	}

	public void setUtmTerm(String utmTerm) {
		this.utmTerm = utmTerm;
	}

	public String getUtmContent() {
		return utmContent;
	}

	public void setUtmContent(String utmContent) {
		this.utmContent = utmContent;
	}
}
