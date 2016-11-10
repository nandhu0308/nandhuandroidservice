package com.limitless.services.payment.PaymentService;

public class PaymentsSettlementResponseBean {
	private int psId;
	private int settlementId;
	private int releasefundRefId;
	private String message;
	public int getPsId() {
		return psId;
	}
	public void setPsId(int psId) {
		this.psId = psId;
	}
	public int getSettlementId() {
		return settlementId;
	}
	public void setSettlementId(int settlementId) {
		this.settlementId = settlementId;
	}
	public int getReleasefundRefId() {
		return releasefundRefId;
	}
	public void setReleasefundRefId(int releasefundRefId) {
		this.releasefundRefId = releasefundRefId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
