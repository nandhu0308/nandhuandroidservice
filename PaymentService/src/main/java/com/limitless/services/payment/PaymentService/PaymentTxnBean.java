package com.limitless.services.payment.PaymentService;

/*
 * @author veejay.developer@gmail.com
 * ©www.limitlesscircle.com 
 */

public class PaymentTxnBean {
	
	private int txnId;
	private int engageCustomerId;
	private int sellerId;
	private String sellerName;
	private String sellerDeviceId;
	private float txnAmount;
	private int citrusMpTxnId;
	private int splitId;
	public enum TxnStatus {
		PAYMENT_INITIATED, PAYMENT_SUCCESSFUL, PAYMENT_FAILED, FUND_RELEASED, REFUND_INITIATED, REFUND_PROCESSED;
	}
	private TxnStatus txnStatus;
	
	public TxnStatus getTxnStatus() {
		return txnStatus;
	}
	public void setTxnStatus(TxnStatus txnStatus) {
		this.txnStatus = txnStatus;
	}
	
	public int getCitrusMpTxnId() {
		return citrusMpTxnId;
	}
	public void setCitrusMpTxnId(int citrusMpTxnId) {
		this.citrusMpTxnId = citrusMpTxnId;
	}
	public int getSplitId() {
		return splitId;
	}
	public void setSplitId(int splitId) {
		this.splitId = splitId;
	}
	public int getTxnId() {
		return txnId;
	}
	public void setTxnId(int txnId) {
		this.txnId = txnId;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public float getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(float txnAmount) {
		this.txnAmount = txnAmount;
	}
	public int getEngageCustomerId() {
		return engageCustomerId;
	}
	public void setEngageCustomerId(int engageCustomerId) {
		this.engageCustomerId = engageCustomerId;
	}
	public String getSellerDeviceId() {
		return sellerDeviceId;
	}
	public void setSellerDeviceId(String sellerDeviceId) {
		this.sellerDeviceId = sellerDeviceId;
	}
	
}
