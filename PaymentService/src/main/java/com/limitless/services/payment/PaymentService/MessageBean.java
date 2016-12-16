package com.limitless.services.payment.PaymentService;

public class MessageBean {
	private int txnId;
	private int customerId;
	private int sellerCitrusId;
	private int sellerId;
	private double txnAmount;
	private String txnStatus;
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getSellerCitrusId() {
		return sellerCitrusId;
	}
	public void setSellerCitrusId(int sellerCitrusId) {
		this.sellerCitrusId = sellerCitrusId;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public double getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(double txnAmount) {
		this.txnAmount = txnAmount;
	}
	public String getTxnStatus() {
		return txnStatus;
	}
	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}
	public int getTxnId() {
		return txnId;
	}
	public void setTxnId(int txnId) {
		this.txnId = txnId;
	}
}
