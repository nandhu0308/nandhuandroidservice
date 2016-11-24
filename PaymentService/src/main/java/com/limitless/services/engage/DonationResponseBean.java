package com.limitless.services.engage;

import java.util.List;

public class DonationResponseBean {
	private String message;
	private List<DonationsBean> donationsList;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<DonationsBean> getDonationsList() {
		return donationsList;
	}
	public void setDonationsList(List<DonationsBean> donationsList) {
		this.donationsList = donationsList;
	}
}
