package com.limitless.services.engage.bills;

public class BillBean {
	private int billId;
	private String clientInvoiceId;
	private int customerId;
	private String customerName;
	private int sellerId;
	private String sellerName;
	private int citrusSellerId;
	private String billDate;
	private String billDueDate;
	private String billPaidDate;
	private float billAmount;
	private float adjustmentAmount;
	private float payableAmount;
	private String billStatus;
	
	public int getCitrusSellerId() {
		return citrusSellerId;
	}
	public void setCitrusSellerId(int citrusSellerId) {
		this.citrusSellerId = citrusSellerId;
	}
	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
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
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
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
	public String getBillPaidDate() {
		return billPaidDate;
	}
	public void setBillPaidDate(String billPaidDate) {
		this.billPaidDate = billPaidDate;
	}
	public float getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(float billAmount) {
		this.billAmount = billAmount;
	}
	public float getAdjustmentAmount() {
		return adjustmentAmount;
	}
	public void setAdjustmentAmount(float adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}
	public String getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	public float getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(float payableAmount) {
		this.payableAmount = payableAmount;
	}
	public String getClientInvoiceId() {
		return clientInvoiceId;
	}
	public void setClientInvoiceId(String clientInvoiceId) {
		this.clientInvoiceId = clientInvoiceId;
	}
}
