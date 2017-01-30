package com.limitless.services.engage;

import java.util.List;

public class SellerContactsRequestBean {
	private int sellerId;
	private List<PhoneNumber> phoneNumberList;
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public List<PhoneNumber> getPhoneNumberList() {
		return phoneNumberList;
	}
	public void setPhoneNumberList(List<PhoneNumber> phoneNumberList) {
		this.phoneNumberList = phoneNumberList;
	}
}
