package com.limitless.services.payment.PaymentService;

public class TxnSettlementResponseBean {
	private int psId;
	private int txnId;
	private int settleId;
	private int releaseRefId;
	private String message;
	public int getPsId() {
		return psId;
	}
	public void setPsId(int psId) {
		this.psId = psId;
	}
	public int getTxnId() {
		return txnId;
	}
	public void setTxnId(int txnId) {
		this.txnId = txnId;
	}
	public int getSettleId() {
		return settleId;
	}
	public void setSettleId(int settleId) {
		this.settleId = settleId;
	}
	public int getReleaseRefId() {
		return releaseRefId;
	}
	public void setReleaseRefId(int releaseRefId) {
		this.releaseRefId = releaseRefId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
