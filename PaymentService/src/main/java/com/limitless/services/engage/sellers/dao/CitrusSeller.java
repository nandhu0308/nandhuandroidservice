package com.limitless.services.engage.sellers.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="citrus_seller", catalog="llcdb")
public class CitrusSeller {
	@Id
	@Column(name="CITRUS_ID")
	private Integer citrusId;
	@Column(name="NAME")
	private String sellerName;
	@Column(name="ADDRESS1")
	private String sellerAddress1;
	@Column(name="ADDRESS2")
	private String sellerAddress2;
	@Column(name="CITY")
	private String sellerCity;
	@Column(name="STATE")
	private String sellerState;
	@Column(name="COUNTRY")
	private String sellerCountry;
	@Column(name="ZIP")
	private String sellerZip;
	@Column(name="BURL")
	private String sellerBurl;
	@Column(name="MAIL")
	private String sellerMail;
	@Column(name="IFSC")
	private String sellerIfsc;
	@Column(name="ACC_NUMBER")
	private String sellerAccNumber;
	@Column(name="PAYOUTMODE")
	private String sellerPayoutmode;
	@Column(name="ACCOUNT_ID")
	private Integer sellerAccountId;
	@Column(name="ACTIVE")
	private Integer sellerActive;
	
	public Integer getCitrusId() {
		return citrusId;
	}
	public void setCitrusId(Integer citrusId) {
		this.citrusId = citrusId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getSellerAddress1() {
		return sellerAddress1;
	}
	public void setSellerAddress1(String sellerAddress1) {
		this.sellerAddress1 = sellerAddress1;
	}
	public String getSellerAddress2() {
		return sellerAddress2;
	}
	public void setSellerAddress2(String sellerAddress2) {
		this.sellerAddress2 = sellerAddress2;
	}
	public String getSellerCity() {
		return sellerCity;
	}
	public void setSellerCity(String sellerCity) {
		this.sellerCity = sellerCity;
	}
	public String getSellerState() {
		return sellerState;
	}
	public void setSellerState(String sellerState) {
		this.sellerState = sellerState;
	}
	public String getSellerCountry() {
		return sellerCountry;
	}
	public void setSellerCountry(String sellerCountry) {
		this.sellerCountry = sellerCountry;
	}
	public String getSellerZip() {
		return sellerZip;
	}
	public void setSellerZip(String sellerZip) {
		this.sellerZip = sellerZip;
	}
	public String getSellerBurl() {
		return sellerBurl;
	}
	public void setSellerBurl(String sellerBurl) {
		this.sellerBurl = sellerBurl;
	}
	public String getSellerMail() {
		return sellerMail;
	}
	public void setSellerMail(String sellerMail) {
		this.sellerMail = sellerMail;
	}
	public String getSellerIfsc() {
		return sellerIfsc;
	}
	public void setSellerIfsc(String sellerIfsc) {
		this.sellerIfsc = sellerIfsc;
	}
	public String getSellerAccNumber() {
		return sellerAccNumber;
	}
	public void setSellerAccNumber(String sellerAccNumber) {
		this.sellerAccNumber = sellerAccNumber;
	}
	public String getSellerPayoutmode() {
		return sellerPayoutmode;
	}
	public void setSellerPayoutmode(String sellerPayoutmode) {
		this.sellerPayoutmode = sellerPayoutmode;
	}
	public Integer getSellerAccountId() {
		return sellerAccountId;
	}
	public void setSellerAccountId(Integer sellerAccountId) {
		this.sellerAccountId = sellerAccountId;
	}
	public Integer getSellerActive() {
		return sellerActive;
	}
	public void setSellerActive(Integer sellerActive) {
		this.sellerActive = sellerActive;
	}
}
