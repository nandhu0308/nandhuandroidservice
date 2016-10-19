package com.limitless.services.payment.PaymentService;

public class CreditBean {
	
	private int creditId;
	private int txnId;
	private float creditAmount;
	private float debitAmount;
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
	
}
