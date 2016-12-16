package com.limitless.services.payment.PaymentService;

public class CreditBean {
	
	private int creditId;
	private int txnId;
	private int sellerId;
	private int customerId;
	private float creditAmount;
	private float debitAmount;
	private int merchantId;
	private float creditTemp;
	private float debitTemp;
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}
	public int getCreditId() {
		return creditId;
	}
	public void setCreditId(int creditId) {
		this.creditId = creditId;
	}
	public int getTxnId() {
		return txnId;
	}
	public void setTxnId(int txnId) {
		this.txnId = txnId;
	}
	public float getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(float creditAmount) {
		this.creditAmount = creditAmount;
	}
	public float getDebitAmount() {
		return debitAmount;
	}
	public void setDebitAmount(float debitAmount) {
		this.debitAmount = debitAmount;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public float getCreditTemp() {
		return creditTemp;
	}
	public void setCreditTemp(float creditTemp) {
		this.creditTemp = creditTemp;
	}
	public float getDebitTemp() {
		return debitTemp;
	}
	public void setDebitTemp(float debitTemp) {
		this.debitTemp = debitTemp;
	}
}
