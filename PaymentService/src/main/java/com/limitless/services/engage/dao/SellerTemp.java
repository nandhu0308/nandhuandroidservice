package com.limitless.services.engage.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sellers_temp", catalog="llcdb")
public class SellerTemp {
	@Id
	@GeneratedValue
	@Column(name="ST_ID")
	private Integer stId;
	@Column(name="SELLER_ID")
	private Integer sellerId;
	@Column(name="SELLER_ACCOUNT")
	private String sellerAccount;
	@Column(name="SELLER_IFSC")
	private String sellerIfsc;
	@Column(name="SELLER_KYC_IMG")
	private String sellerKycImage;
	@Column(name="SELLER_BANK_PROOF")
	private String sellerBankProof;
	@Column(name="STATUS")
	private String status;
	public Integer getStId() {
		return stId;
	}
	public void setStId(Integer stId) {
		this.stId = stId;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerAccount() {
		return sellerAccount;
	}
	public void setSellerAccount(String sellerAccount) {
		this.sellerAccount = sellerAccount;
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
