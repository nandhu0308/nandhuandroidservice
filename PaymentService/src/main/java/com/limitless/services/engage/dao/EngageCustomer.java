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
import javax.persistence.Version;

/**
 * EngageCustomer generated by hbm2java
 */
@Entity
@Table(name = "engage_customer", catalog = "llcdb")
public class EngageCustomer implements java.io.Serializable {
	
	@Id @GeneratedValue
	@Column(name="CUSTOMER_ID")
	private Integer customerId;
	@Column(name="CITRUS_SELLER_ID")
	private Integer citrusSellerId;
	@Column(name="CUSTOMER_NAME")
	private String customerName;
	@Column(name="CUSTOMER_EMAIL99")
	private String customerEmail99;
	@Column(name="CUSTOMER_PASSWD99")
	private String customerPasswd99;
	@Column(name="CUSTOMER_COUNTRY_CODE")
	private String customerCountryCode;
	@Column(name="CUSTOMER_MOBILE_NUMBER")
	private String customerMobileNumber;
	@Column(name="CUSTOMER_COUNTRY")
	private String customerCountry;
	@Column(name="CUSTOMER_CITY")
	private String customerCity;
	@Column(name="CUSTOMER_CREATED_TIME")
	private Date customerCreatedTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CUSTOMER_UPDATED_TIME")
	@Version
	private Date customerUpdatedTime;

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
	
}
