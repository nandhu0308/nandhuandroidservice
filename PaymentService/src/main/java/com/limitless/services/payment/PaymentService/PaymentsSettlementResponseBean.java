package com.limitless.services.payment.PaymentService;

public class PaymentsSettlementResponseBean {
	private int psId;
	private int settlementId;
	private int releasefundRefId;
	private String message;
	private double settlementAmount;
	private String errorIdSettle;
	private String errorDescriptionSettle;
	private String errorIdRelease;
	private String errorDescriptionRelease;
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
	public double getSettlementAmount() {
		return settlementAmount;
	}
	public void setSettlementAmount(double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	public String getErrorIdSettle() {
		return errorIdSettle;
	}
	public void setErrorIdSettle(String errorIdSettle) {
		this.errorIdSettle = errorIdSettle;
	}
	public String getErrorDescriptionSettle() {
		return errorDescriptionSettle;
	}
	public void setErrorDescriptionSettle(String errorDescriptionSettle) {
		this.errorDescriptionSettle = errorDescriptionSettle;
	}
	public String getErrorIdRelease() {
		return errorIdRelease;
	}
	public void setErrorIdRelease(String errorIdRelease) {
		this.errorIdRelease = errorIdRelease;
	}
	public String getErrorDescriptionRelease() {
		return errorDescriptionRelease;
	}
	public void setErrorDescriptionRelease(String errorDescriptionRelease) {
		this.errorDescriptionRelease = errorDescriptionRelease;
	}
}
