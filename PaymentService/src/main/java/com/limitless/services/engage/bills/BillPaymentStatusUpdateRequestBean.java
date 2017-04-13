package com.limitless.services.engage.bills;

public class BillPaymentStatusUpdateRequestBean {
	private int billId;
	private int billStatusCode;
	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public int getBillStatusCode() {
		return billStatusCode;
	}
	public void setBillStatusCode(int billStatusCode) {
		this.billStatusCode = billStatusCode;
	}
}
