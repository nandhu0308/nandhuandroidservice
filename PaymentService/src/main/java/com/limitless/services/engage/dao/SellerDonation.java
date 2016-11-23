package com.limitless.services.engage.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "seller_donation", catalog = "llcdb")
public class SellerDonation implements Serializable {

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
	
	
	
}
