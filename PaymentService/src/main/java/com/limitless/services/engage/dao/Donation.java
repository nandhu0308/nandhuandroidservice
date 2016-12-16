package com.limitless.services.engage.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "donation", catalog = "llcdb")
public class Donation implements Serializable {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer dinationId;
	@Column(name="NAME")
	private String donationName;
	@Column(name="CITRUS_SELLER_ID")
	private Integer citrusSellerId;
	public Integer getDinationId() {
		return dinationId;
	}
	public void setDinationId(Integer dinationId) {
		this.dinationId = dinationId;
	}
	public String getDonationName() {
		return donationName;
	}
	public void setDonationName(String donationName) {
		this.donationName = donationName;
	}
	public Integer getCitrusSellerId() {
		return citrusSellerId;
	}
	public void setCitrusSellerId(Integer citrusSellerId) {
		this.citrusSellerId = citrusSellerId;
	}
}
