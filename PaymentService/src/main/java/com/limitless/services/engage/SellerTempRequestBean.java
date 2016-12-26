package com.limitless.services.engage;

public class SellerTempRequestBean {
	private String sellerBankAccountNumber;
	private String sellerIfsc;
	private String sellerKycImage;
	private String sellerBankProof;
	private String status;
	public String getSellerBankAccountNumber() {
		return sellerBankAccountNumber;
	}
	public void setSellerBankAccountNumber(String sellerBankAccountNumber) {
		this.sellerBankAccountNumber = sellerBankAccountNumber;
	}
	public String getSellerIfsc() {
		return sellerIfsc;
	}
	public void setSellerIfsc(String sellerIfsc) {
		this.sellerIfsc = sellerIfsc;
	}
	public String getSellerKycImage() {
		return sellerKycImage;
	}
	public void setSellerKycImage(String sellerKycImage) {
		this.sellerKycImage = sellerKycImage;
	}
	public String getSellerBankProof() {
		return sellerBankProof;
	}
	public void setSellerBankProof(String sellerBankProof) {
		this.sellerBankProof = sellerBankProof;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
