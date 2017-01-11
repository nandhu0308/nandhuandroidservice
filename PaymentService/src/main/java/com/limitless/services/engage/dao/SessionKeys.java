package com.limitless.services.engage.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sessions", catalog="llcdb")
public class SessionKeys {
	@Id
	@GeneratedValue
	@Column(name="SESSION_ID")
	private Integer sessionId;
	@Column(name="USER_ID")
	private Integer userId;
	@Column(name="CREATED_ON", insertable = false, updatable = false)
	private Date createdOn;
	@Column(name="SESSION_KEY")
	private String sessionKey;
	@Column(name="KEY_ALIVE")
	private Integer keyAlive;
	
	public Integer getSessionId() {
		return sessionId;
	}
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	public Integer getKeyAlive() {
		return keyAlive;
	}
	public void setKeyAlive(Integer keyAlive) {
		this.keyAlive = keyAlive;
	}
}
