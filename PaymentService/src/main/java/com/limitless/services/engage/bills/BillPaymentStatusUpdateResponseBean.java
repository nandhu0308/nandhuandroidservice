package com.limitless.services.engage.bills;

public class BillPaymentStatusUpdateResponseBean {
	private String message;
	private int billId;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
}
