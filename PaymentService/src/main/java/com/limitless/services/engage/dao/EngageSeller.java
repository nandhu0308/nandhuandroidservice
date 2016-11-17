package com.limitless.services.engage.dao;

// Generated Oct 14, 2016 12:16:26 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * EngageSeller generated by hbm2java
 */
@Entity
@Table(name = "engage_seller", catalog = "llcdb")
public class EngageSeller implements java.io.Serializable {
	
	@Id @GeneratedValue
	@Column(name="SELLER_ID")
	private int sellerId;
	@Column(name="CITRUS_SELLER_ID")
	private int citrusSellerId;
	@Column(name="SELLER_NAME")
	private String sellerName;
	@Column(name="SELLER_EMAIL99")
	private String sellerEmail99;
	@Column(name="SELLER_PASSWD99")
	private String sellerPasswd99;
	@Column(name="SELLER_COUNTRY_CODE")
	private String sellerCountryCode;
	@Column(name="SELLER_MOBILE_NUMBER")
	private String sellerMobileNumber;
	@Column(name="SELLER_COUNTRY")
	private String sellerCountry;
	@Column(name="SELLER_CITY")
	private String sellerCity;
	@Column(name="SELLER_ADDRESS")
	private String sellerAddress;
	@Column(name="SELLER_DEVICE_ID")
	private String sellerDeviceId;
	@Column(name="SELLER_LOCATION_LATITUDE")
	private Float sellerLocationLatitude;
	@Column(name="SELLER_LOCATION_LONGITUDE")
	private Float sellerLocationLongitude;
	@Column(name="SELLER_SPLIT_PERCENT")
	private float sellerSplitPercent;
	@Column(name="SELLER_KYC_DOC_TYPE")
	private String sellerKycDocType;
	@Column(name="SELLER_KYC_DOC_VALUE")
	private String sellerKycDocValue;
	@Column(name="SELLER_CREATED_TIME")
	private Date sellerCreatedTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SELLER_UPDATED_TIME")
	@Version
	private Date sellerUpdatedTime;
	@Column(name="SELLER_TYPE")
	private String sellerType;
	@Column(name="SELLER_SHOP_NAME")
	private String sellerShopName;

	public EngageSeller() {
	}

	public EngageSeller(int citrusSellerId, String sellerName, String sellerEmail99,
			String sellerPasswd99, String sellerCountryCode,
			String sellerMobileNumber, String sellerCountry, String sellerCity,
			String sellerAddress, float sellerSplitPercent,
			Date sellerCreatedTime, Date sellerUpdatedTime) {
		this.citrusSellerId = citrusSellerId;
		this.sellerName = sellerName;
		this.sellerEmail99 = sellerEmail99;
		this.sellerPasswd99 = sellerPasswd99;
		this.sellerCountryCode = sellerCountryCode;
		this.sellerMobileNumber = sellerMobileNumber;
		this.sellerCountry = sellerCountry;
		this.sellerCity = sellerCity;
		this.sellerAddress = sellerAddress;
		this.sellerSplitPercent = sellerSplitPercent;
		this.sellerCreatedTime = sellerCreatedTime;
		this.sellerUpdatedTime = sellerUpdatedTime;
	}

	public EngageSeller(int citrusSellerId, String sellerName, String sellerEmail99,
			String sellerPasswd99, String sellerCountryCode,
			String sellerMobileNumber, String sellerCountry, String sellerCity,
			String sellerAddress, String sellerDeviceId, Float sellerLocationLatitude,
			Float sellerLocationLongitude, float sellerSplitPercent,
			String sellerKycDocType, String sellerKycDocValue,
			Date sellerCreatedTime, Date sellerUpdatedTime) {
		this.citrusSellerId = citrusSellerId;
		this.sellerName = sellerName;
		this.sellerEmail99 = sellerEmail99;
		this.sellerPasswd99 = sellerPasswd99;
		this.sellerCountryCode = sellerCountryCode;
		this.sellerMobileNumber = sellerMobileNumber;
		this.sellerCountry = sellerCountry;
		this.sellerCity = sellerCity;
		this.sellerAddress = sellerAddress;
		this.sellerDeviceId = sellerDeviceId;
		this.sellerLocationLatitude = sellerLocationLatitude;
		this.sellerLocationLongitude = sellerLocationLongitude;
		this.sellerSplitPercent = sellerSplitPercent;
		this.sellerKycDocType = sellerKycDocType;
		this.sellerKycDocValue = sellerKycDocValue;
		this.sellerCreatedTime = sellerCreatedTime;
		this.sellerUpdatedTime = sellerUpdatedTime;
	}

	public int getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return this.sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerEmail99() {
		return this.sellerEmail99;
	}

	public void setSellerEmail99(String sellerEmail99) {
		this.sellerEmail99 = sellerEmail99;
	}

	public String getSellerPasswd99() {
		return this.sellerPasswd99;
	}

	public void setSellerPasswd99(String sellerPasswd99) {
		this.sellerPasswd99 = sellerPasswd99;
	}

	public String getSellerCountryCode() {
		return this.sellerCountryCode;
	}

	public void setSellerCountryCode(String sellerCountryCode) {
		this.sellerCountryCode = sellerCountryCode;
	}

	public String getSellerMobileNumber() {
		return this.sellerMobileNumber;
	}

	public void setSellerMobileNumber(String sellerMobileNumber) {
		this.sellerMobileNumber = sellerMobileNumber;
	}

	public String getSellerCountry() {
		return this.sellerCountry;
	}

	public void setSellerCountry(String sellerCountry) {
		this.sellerCountry = sellerCountry;
	}

	public String getSellerCity() {
		return this.sellerCity;
	}

	public void setSellerCity(String sellerCity) {
		this.sellerCity = sellerCity;
	}

	public String getSellerAddress() {
		return this.sellerAddress;
	}

	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	public Float getSellerLocationLatitude() {
		return this.sellerLocationLatitude;
	}

	public void setSellerLocationLatitude(Float sellerLocationLatitude) {
		this.sellerLocationLatitude = sellerLocationLatitude;
	}

	public Float getSellerLocationLongitude() {
		return this.sellerLocationLongitude;
	}

	public void setSellerLocationLongitude(Float sellerLocationLongitude) {
		this.sellerLocationLongitude = sellerLocationLongitude;
	}

	public float getSellerSplitPercent() {
		return this.sellerSplitPercent;
	}

	public void setSellerSplitPercent(float sellerSplitPercent) {
		this.sellerSplitPercent = sellerSplitPercent;
	}

	public String getSellerKycDocType() {
		return this.sellerKycDocType;
	}

	public void setSellerKycDocType(String sellerKycDocType) {
		this.sellerKycDocType = sellerKycDocType;
	}

	public String getSellerKycDocValue() {
		return this.sellerKycDocValue;
	}

	public void setSellerKycDocValue(String sellerKycDocValue) {
		this.sellerKycDocValue = sellerKycDocValue;
	}

	public Date getSellerCreatedTime() {
		return this.sellerCreatedTime;
	}

	public void setSellerCreatedTime(Date sellerCreatedTime) {
		this.sellerCreatedTime = sellerCreatedTime;
	}

	public Date getSellerUpdatedTime() {
		return this.sellerUpdatedTime;
	}

	public void setSellerUpdatedTime(Date sellerUpdatedTime) {
		this.sellerUpdatedTime = sellerUpdatedTime;
	}

	public int getCitrusSellerId() {
		return citrusSellerId;
	}

	public void setCitrusSellerId(int citrusSellerId) {
		this.citrusSellerId = citrusSellerId;
	}

	public String getSellerDeviceId() {
		return sellerDeviceId;
	}

	public void setSellerDeviceId(String sellerDeviceId) {
		this.sellerDeviceId = sellerDeviceId;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public String getSellerShopName() {
		return sellerShopName;
	}

	public void setSellerShopName(String sellerShopName) {
		this.sellerShopName = sellerShopName;
	}
}
