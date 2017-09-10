package com.limitless.services.payment.PaymentService.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="citrus_auth_token")
public class CitrusAuthToken {
	@Id
	@GeneratedValue
	@Column(name="AUTH_ID")
	private Integer authId;
	@Column(name="AUTH_TOKEN")
	private String authToken;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TOKEN_UPDATED_TIME")
	@Version
	private Date updatedTime;
	public Integer getAuthId() {
		return authId;
	}
	public void setAuthId(Integer authId) {
		this.authId = authId;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
}
