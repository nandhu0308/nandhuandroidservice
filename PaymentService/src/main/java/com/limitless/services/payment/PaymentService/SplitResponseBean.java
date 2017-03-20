package com.limitless.services.payment.PaymentService;

import java.util.List;

/*
 * @author veejay.developer@gmail.com
 * ©www.limitlesscircle.com 
 */

public class SplitResponseBean {
	
	private int splitId;
	private String message = "Failure";
	private double amount;
	private String name;
	private String date;
	private List<String> sellerDeviceIds;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getSplitId() {
		return splitId;
	}

	public void setSplitId(int splitId) {
		this.splitId = splitId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<String> getSellerDeviceIds() {
		return sellerDeviceIds;
	}

	public void setSellerDeviceIds(List<String> sellerDeviceIds) {
		this.sellerDeviceIds = sellerDeviceIds;
	}

}
