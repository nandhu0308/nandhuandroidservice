package com.limitless.services.engage.sellers;

public class CitrusSellerBean {
	private String message;
	private int citrusSellerId;
	private String sellerName;
	private String sellerBankAccountNumber;
	private String sellerBankIfsc;
	private int sellerActive;
	public int getCitrusSellerId() {
		return citrusSellerId;
	}
	public void setCitrusSellerId(int citrusSellerId) {
		this.citrusSellerId = citrusSellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getSellerBankAccountNumber() {
		return sellerBankAccountNumber;
	}
	public void setSellerBankAccountNumber(String sellerBankAccountNumber) {
		this.sellerBankAccountNumber = sellerBankAccountNumber;
	}
	public String getSellerBankIfsc() {
		return sellerBankIfsc;
	}
	public void setSellerBankIfsc(String sellerBankIfsc) {
		this.sellerBankIfsc = sellerBankIfsc;
	}
	public int getSellerActive() {
		return sellerActive;
	}
	public void setSellerActive(int sellerActive) {
		this.sellerActive = sellerActive;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
