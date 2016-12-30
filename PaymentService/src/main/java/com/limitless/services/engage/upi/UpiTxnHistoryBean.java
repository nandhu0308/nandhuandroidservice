package com.limitless.services.engage.upi;

import com.limitless.services.payment.PaymentService.PaymentsSettlementResponseBean;

public class UpiTxnHistoryBean {
	private int txnId;
	private int customerId;
	private String customerName;
	private int sellerId;
	private String sellerName;
	private double txtAmount;
	private double creditAmount;
	private String txnNotes;
	private String iciciTxnNo;
	private String iciciTxnTime;
	private String txtStatus;
	private String paymentType;
	private String txnTime;
	private PaymentsSettlementResponseBean settlement;
	
	
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
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getTxnNotes() {
		return txnNotes;
	}
	public void setTxnNotes(String txnNotes) {
		this.txnNotes = txnNotes;
	}
	public String getIciciTxnNo() {
		return iciciTxnNo;
	}
	public void setIciciTxnNo(String iciciTxnNo) {
		this.iciciTxnNo = iciciTxnNo;
	}
	public String getIciciTxnTime() {
		return iciciTxnTime;
	}
	public void setIciciTxnTime(String iciciTxnTime) {
		this.iciciTxnTime = iciciTxnTime;
	}
	
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getTxnId() {
		return txnId;
	}
	public void setTxnId(int txnId) {
		this.txnId = txnId;
	}
	public double getTxtAmount() {
		return txtAmount;
	}
	public void setTxtAmount(double txtAmount) {
		this.txtAmount = txtAmount;
	}
	public String getTxtStatus() {
		return txtStatus;
	}
	public void setTxtStatus(String txtStatus) {
		this.txtStatus = txtStatus;
	}
	public String getTxnTime() {
		return txnTime;
	}
	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}
	public PaymentsSettlementResponseBean getSettlement() {
		return settlement;
	}
	public void setSettlement(PaymentsSettlementResponseBean settlement) {
		this.settlement = settlement;
	}
	public double getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}
}
