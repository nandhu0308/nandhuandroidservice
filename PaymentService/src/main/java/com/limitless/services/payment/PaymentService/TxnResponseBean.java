package com.limitless.services.payment.PaymentService;

/*
 * @author veejay.developer@gmail.com
 * ©www.limitlesscircle.com 
 */

public class TxnResponseBean {
	
	private int txnId;
	private String message = "Failure";
	private double amount;
	private String name;
	private String date;
	private String sellerDeviceId;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getTxnId() {
		return txnId;
	}

	public void setTxnId(int txnId) {
		this.txnId = txnId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSellerDeviceId() {
		return sellerDeviceId;
	}

	public void setSellerDeviceId(String sellerDeviceId) {
		this.sellerDeviceId = sellerDeviceId;
	}
	
}
