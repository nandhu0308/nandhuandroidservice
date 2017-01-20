package com.limitless.services.engage;

import java.util.List;

public class CustomerAddressListBean {
	private String message;
	private int customerId;
	private List<AddressListBean> addressList;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public List<AddressListBean> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<AddressListBean> addressList) {
		this.addressList = addressList;
	}
}
