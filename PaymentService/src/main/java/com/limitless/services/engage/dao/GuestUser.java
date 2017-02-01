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
}
