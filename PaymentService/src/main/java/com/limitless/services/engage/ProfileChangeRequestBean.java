package com.limitless.services.engage;

public class ProfileChangeRequestBean {
	
	private int customerId;
	private String customerKey;
	private String customerValue;
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerKey() {
		return customerKey;
	}
	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}
	public String getCustomerValue() {
		return customerValue;
	}
	public void setCustomerValue(String customerValue) {
		this.customerValue = customerValue;
	}
	
}
