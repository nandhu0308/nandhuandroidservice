package com.limitless.services.engage.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="guest_user", catalog="llcdb")
public class GuestUser {
	@Id
	@GeneratedValue
	@Column(name="GUEST_ID")
	private Integer guestId;
	@Column(name="DEVICE_MAC")
	private String deviceMac;
	@Column(name="GUEST_TIMESTAMP", insertable = false, updatable = false)
	private Date guestTimestamp;
	@Column(name="UTM_SOURCE")
	private String utmSource;
	@Column(name="UTM_MEDIUM")
	private String utmMedium;
	@Column(name="UTM_CAMPIGN")
	private String utmCampign;
	@Column(name="UTM_TERM")
	private String utmTerm;
	@Column(name="UTM_CONTENT")
	private String utmContent;
	
	public Integer getGuestId() {
		return guestId;
	}
	public void setGuestId(Integer guestId) {
		this.guestId = guestId;
	}
	public String getDeviceMac() {
		return deviceMac;
	}
	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}
	public Date getGuestTimestamp() {
		return guestTimestamp;
	}
	public void setGuestTimestamp(Date guestTimestamp) {
		this.guestTimestamp = guestTimestamp;
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
