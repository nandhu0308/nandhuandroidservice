package com.limitless.services.engage.bills;

public class BillRequestBean {
	private String clientInvoiceId;
	private int customerId;
	private int sellerId;
	private String billDate;
	private String billDueDate;
	private float billAmount;
	private float billAdjustment;
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getBillDueDate() {
		return billDueDate;
	}
	public void setBillDueDate(String billDueDate) {
		this.billDueDate = billDueDate;
	}
	public float getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(float billAmount) {
		this.billAmount = billAmount;
	}
	public float getBillAdjustment() {
		return billAdjustment;
	}
	public void setBillAdjustment(float billAdjustment) {
		this.billAdjustment = billAdjustment;
	}
	public String getClientInvoiceId() {
		return clientInvoiceId;
	}
	public void setClientInvoiceId(String clientInvoiceId) {
		this.clientInvoiceId = clientInvoiceId;
	}
}
