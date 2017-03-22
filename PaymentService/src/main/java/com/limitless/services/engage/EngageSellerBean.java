package com.limitless.services.engage;

import javax.persistence.Column;

public class EngageSellerBean {

	private int citrusSellerId;
	private String sellerName;
	private String sellerShopName;
	private String aboutSeller;
	private String emailId;
	private String passwd;
	private String countryCode;
	private String country;
	private String city;
	private String address;
	private String sellerDeviceId;
	private String mobileNumber;
	private String sellerType;
	private String sellerRole;

	private float latitude;
	private float longitude;

	private String kycDocType;
	private String kycDocValue;
	private String KycDocImage;

	private String ambassadorMobile;
	private int isActive;
	private String sellerBankAccountNumber;
	private String sellerIfsc;
	private float splitPerent;
	private String sellerBankProof;
	private String mobileAlias;

	private double upto3k;
	private double gt3klt10k;
	private double gt10klt1lac;
	private double gt1laclt2lac;
	private double gt2lac;

	private String businessCategory;
	private String mapMarkerName;
	
	private String sellerBusinessType;
	private boolean podAvailable;
	private float deliveryMinAmount;
	private int deliveryRadius;
	private float deliveryFee;
	private boolean convenienceFee;

	public int getCitrusSellerId() {
		return citrusSellerId;
	}

	public void setCitrusSellerId(int citrusSellerId) {
		this.citrusSellerId = citrusSellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getSplitPerent() {
		return splitPerent;
	}

	public void setSplitPerent(float splitPerent) {
		this.splitPerent = splitPerent;
	}

	public String getKycDocType() {
		return kycDocType;
	}

	public void setKycDocType(String kycDocType) {
		this.kycDocType = kycDocType;
	}

	public String getKycDocValue() {
		return kycDocValue;
	}

	public void setKycDocValue(String kycDocValue) {
		this.kycDocValue = kycDocValue;
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

	public String getSellerRole() {
		return sellerRole;
	}

	public void setSellerRole(String sellerRole) {
		this.sellerRole = sellerRole;
	}

	public String getKycDocImage() {
		return KycDocImage;
	}

	public void setKycDocImage(String kycDocImage) {
		KycDocImage = kycDocImage;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

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

	public String getSellerBankProof() {
		return sellerBankProof;
	}

	public void setSellerBankProof(String sellerBankProof) {
		this.sellerBankProof = sellerBankProof;
	}

	public String getAmbassadorMobile() {
		return ambassadorMobile;
	}

	public void setAmbassadorMobile(String ambassadorMobile) {
		this.ambassadorMobile = ambassadorMobile;
	}

	public String getMobileAlias() {
		return mobileAlias;
	}

	public void setMobileAlias(String mobileAlias) {
		this.mobileAlias = mobileAlias;
	}

	public double getUpto3k() {
		return upto3k;
	}

	public void setUpto3k(double upto3k) {
		this.upto3k = upto3k;
	}

	public double getGt3klt10k() {
		return gt3klt10k;
	}

	public void setGt3klt10k(double gt3klt10k) {
		this.gt3klt10k = gt3klt10k;
	}

	public double getGt10klt1lac() {
		return gt10klt1lac;
	}

	public void setGt10klt1lac(double gt10klt1lac) {
		this.gt10klt1lac = gt10klt1lac;
	}

	public double getGt1laclt2lac() {
		return gt1laclt2lac;
	}

	public void setGt1laclt2lac(double gt1laclt2lac) {
		this.gt1laclt2lac = gt1laclt2lac;
	}

	public double getGt2lac() {
		return gt2lac;
	}

	public void setGt2lac(double gt2lac) {
		this.gt2lac = gt2lac;
	}

	public String getBusinessCategory() {
		return businessCategory == null || businessCategory.equalsIgnoreCase("") ? "Shop" : businessCategory;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	public String getMapMarkerName() {
		return mapMarkerName == null || mapMarkerName.equalsIgnoreCase("") ? "default" : mapMarkerName;
	}

	public void setMapMarkerName(String mapMarkerName) {
		this.mapMarkerName = mapMarkerName;
	}

	public String getAboutSeller() {
		return aboutSeller;
	}

	public void setAboutSeller(String aboutSeller) {
		this.aboutSeller = aboutSeller;
	}

	public String getSellerBusinessType() {
		return sellerBusinessType;
	}

	public void setSellerBusinessType(String sellerBusinessType) {
		this.sellerBusinessType = sellerBusinessType;
	}

	public boolean isPodAvailable() {
		return podAvailable;
	}

	public void setPodAvailable(boolean podAvailable) {
		this.podAvailable = podAvailable;
	}

	public float getDeliveryMinAmount() {
		return deliveryMinAmount;
	}

	public void setDeliveryMinAmount(float deliveryMinAmount) {
		this.deliveryMinAmount = deliveryMinAmount;
	}

	public int getDeliveryRadius() {
		return deliveryRadius;
	}

	public void setDeliveryRadius(int deliveryRadius) {
		this.deliveryRadius = deliveryRadius;
	}

	public float getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(float deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public boolean isConvenienceFee() {
		return convenienceFee;
	}

	public void setConvenienceFee(boolean convenienceFee) {
		this.convenienceFee = convenienceFee;
	}
}
