package com.limitless.services.engage.bills;

import java.util.List;

public class CustomerBillsListBean {
	private String message;
	private int customerId;
	private String customerName;
	private String customerMobile;
	private List<BillBean> billList;
	
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
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	public List<BillBean> getBillList() {
		return billList;
	}
	public void setBillList(List<BillBean> billList) {
		this.billList = billList;
	}
}
