package com.limitless.services.payment.PaymentService;

public class MasterTxnRequestBean {
	private int citrusTxnId;
	private int iciciUorderId;
	private int sellerId;
	private int customerId;
	public int getCitrusTxnId() {
		return citrusTxnId;
	}
	public void setCitrusTxnId(int citrusTxnId) {
		this.citrusTxnId = citrusTxnId;
	}
	public int getIciciUorderId() {
		return iciciUorderId;
	}
	public void setIciciUorderId(int iciciUorderId) {
		this.iciciUorderId = iciciUorderId;
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
}
