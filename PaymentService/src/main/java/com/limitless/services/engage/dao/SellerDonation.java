package com.limitless.services.engage.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "seller_donation", catalog = "llcdb")
public class SellerDonation implements Serializable {
	
	@Id
	@GeneratedValue
	@Column(name="SD_ID")
	private Integer sdId;

	@Column(name="SELLER_ID")
	private Integer sellerId;
	
	@Column(name="DONATION_ID")
	private Integer donationId;

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getDonationId() {
		return donationId;
	}

	public void setDonationId(Integer donationId) {
		this.donationId = donationId;
	}

	public Integer getSdId() {
		return sdId;
	}

	public void setSdId(Integer sdId) {
		this.sdId = sdId;
	}
	
}
