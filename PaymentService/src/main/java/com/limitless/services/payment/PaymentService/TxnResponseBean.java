package com.limitless.services.payment.PaymentService;

/*
 * @author veejay.developer@gmail.com
 * ©www.limitlesscircle.com 
 */

public class TxnResponseBean {
	
	private int txnId;
	private String message = "Failure";
	
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

	
}
