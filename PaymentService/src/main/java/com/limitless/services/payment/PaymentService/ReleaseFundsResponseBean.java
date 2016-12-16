package com.limitless.services.payment.PaymentService;

public class ReleaseFundsResponseBean {
	private int releaseFundsRefId;
	private double settlementAmount;
	private String errorId;
	private String errorDescription;
	private String message;
	public int getReleaseFundsRefId() {
		return releaseFundsRefId;
	}
	public void setReleaseFundsRefId(int releaseFundsRefId) {
		this.releaseFundsRefId = releaseFundsRefId;
	}
	public double getSettlementAmount() {
		return settlementAmount;
	}
	public void setSettlementAmount(double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	public String getErrorId() {
		return errorId;
	}
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
