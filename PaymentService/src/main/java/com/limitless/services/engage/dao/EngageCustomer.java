package com.limitless.services.engage.dao;

// Generated Sep 25, 2016 10:49:31 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * EngageCustomer generated by hbm2java
 */
@Entity
@Table(name = "application_users", catalog = "llcdb")
public class EngageCustomer implements java.io.Serializable {
	
	@Id @GeneratedValue
	@Column(name="ID")
	private Integer customerId;
	@Transient
	private Integer citrusSellerId = 0;
	@Column(name="USER_NAME")
	private String customerName;
	@Column(name="EMAIL_ID")
	private String customerEmail99;
	@Column(name="PASSWD")
	private String customerPasswd99;
	@Column(name="DEVICE_ID")
	private String deviceId;
	@Column(name="MOBILE_ISD_CODE")
	private String customerCountryCode;
	@Column(name="MOBILE")
	private String customerMobileNumber;
	@Column(name="COUNTRY")
	private String customerCountry;
	@Column(name="CITY")
	private String customerCity;
	@Column(name="CREATED_ON", insertable = false, updatable = false)
	private Date customerCreatedTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_UPDATED_ON")
	@Version
	private Date customerUpdatedTime;
	@Column(name="UTM_SOURCE")
	private String utmSource;
	@Column(name="UTM_MEDIUM")
	private String utmMedium;
	@Column(name="UTM_CAMPIGN")
	private String utmCampign;
	@Column(name="UTM_TERM")
	private String utmTerm;
	@Column(name="UTM_CONTENT")
	private String utmContent;
	@Column(name="ZIP")
	private String customerZip;
	@Column(name="COUNTRY_ISO_CODE")
	private String customerCountryIsoCode;
	@Column(name="APP_VERSION")
	private String appVersion;

	public EngageCustomer() {
	}

	public EngageCustomer(String customerName, String customerEmail99,
			String customerPasswd99, String customerCountryCode,
			String customerMobileNumber, String customerCountry,
			String customerCity, Date customerCreatedTime,
			Date customerUpdatedTime) {
		this.customerName = customerName;
		this.customerEmail99 = customerEmail99;
		this.customerPasswd99 = customerPasswd99;
		this.customerCountryCode = customerCountryCode;
		this.customerMobileNumber = customerMobileNumber;
		this.customerCountry = customerCountry;
		this.customerCity = customerCity;
		this.customerCreatedTime = customerCreatedTime;
		this.customerUpdatedTime = customerUpdatedTime;
	}

	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail99() {
		return this.customerEmail99;
	}

	public void setCustomerEmail99(String customerEmail99) {
		this.customerEmail99 = customerEmail99;
	}

	public String getCustomerPasswd99() {
		return this.customerPasswd99;
	}

	public void setCustomerPasswd99(String customerPasswd99) {
		this.customerPasswd99 = customerPasswd99;
	}

	public String getCustomerCountryCode() {
		return this.customerCountryCode;
	}

	public void setCustomerCountryCode(String customerCountryCode) {
		this.customerCountryCode = customerCountryCode;
	}

	public String getCustomerMobileNumber() {
		return this.customerMobileNumber;
	}

	public void setCustomerMobileNumber(String customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}

	public String getCustomerCountry() {
		return this.customerCountry;
	}

	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
	}

	public String getCustomerCity() {
		return this.customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public Date getCustomerCreatedTime() {
		return this.customerCreatedTime;
	}

	public void setCustomerCreatedTime(Date customerCreatedTime) {
		this.customerCreatedTime = customerCreatedTime;
	}

	public Date getCustomerUpdatedTime() {
		return this.customerUpdatedTime;
	}

	public void setCustomerUpdatedTime(Date customerUpdatedTime) {
		this.customerUpdatedTime = customerUpdatedTime;
	}

	public Integer getCitrusSellerId() {
		return citrusSellerId;
	}

	public void setCitrusSellerId(Integer citrusSellerId) {
		this.citrusSellerId = citrusSellerId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUtmSource() {
		return utmSource;
	}

	public void setUtmSource(String utmSource) {
		this.utmSource = utmSource;
	}

	public String getUtmMedium() {
		return utmMedium;
	}

	public void setUtmMedium(String utmMedium) {
		this.utmMedium = utmMedium;
	}

	public String getUtmCampign() {
		return utmCampign;
	}

	public void setUtmCampign(String utmCampign) {
		this.utmCampign = utmCampign;
	}

	public String getUtmTerm() {
		return utmTerm;
	}

	public void setUtmTerm(String utmTerm) {
		this.utmTerm = utmTerm;
	}

	public String getUtmContent() {
		return utmContent;
	}

	public void setUtmContent(String utmContent) {
		this.utmContent = utmContent;
	}

	public String getCustomerZip() {
		return customerZip;
	}

	public void setCustomerZip(String customerZip) {
		this.customerZip = customerZip;
	}

	public String getCustomerCountryIsoCode() {
		return customerCountryIsoCode;
	}

	public void setCustomerCountryIsoCode(String customerCountryIsoCode) {
		this.customerCountryIsoCode = customerCountryIsoCode;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	
}
