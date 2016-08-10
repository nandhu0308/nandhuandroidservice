package com.limitless.services.payment.PaymentService;

public class PaymentTxnBean {
	
	private int txnId;
	private int sellerId;
	private String sellerName;
	private float txnAmount;
	public int getTxnId() {
		return txnId;
	}
	public void setTxnId(int txnId) {
		this.txnId = txnId;
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
	public float getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(float txnAmount) {
		this.txnAmount = txnAmount;
	}
	
}
