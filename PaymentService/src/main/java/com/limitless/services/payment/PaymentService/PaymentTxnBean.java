package com.limitless.services.payment.PaymentService;

/*
 * @author veejay.developer@gmail.com
 * ©www.limitlesscircle.com 
 */

public class PaymentTxnBean {
	
	private int txnId;
	private int engageCustomerId;
	private int sellerId;
	private int citrusSellerId;
	private String sellerName;
	private String sellerDeviceId;
	private float txnAmount;
	private int citrusMpTxnId;
	private int splitId;
	public enum TxnStatus {
		PAYMENT_INITIATED, PAYMENT_SUCCESSFUL, PAYMENT_FAILED, FUND_RELEASED, REFUND_INITIATED, REFUND_PROCESSED;
	}
	private TxnStatus txnStatus;
	private String txnNotes;
	private int orderId;
	private String txnType;
	private int promoCodeId;
	
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
	public int getCitrusSellerId() {
		return citrusSellerId;
	}
	public void setCitrusSellerId(int citrusSellerId) {
		this.citrusSellerId = citrusSellerId;
	}
	public String getTxnNotes() {
		return txnNotes;
	}
	public void setTxnNotes(String txnNotes) {
		this.txnNotes = txnNotes;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public int getPromoCodeId() {
		return promoCodeId;
	}
	public void setPromoCodeId(int promoCodeId) {
		this.promoCodeId = promoCodeId;
	}
	
}
