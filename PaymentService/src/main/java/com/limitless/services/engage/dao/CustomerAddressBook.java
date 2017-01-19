package com.limitless.services.engage.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customer_address_book", catalog="llcdb")
public class CustomerAddressBook {
	@Id
	@GeneratedValue
	@Column(name="CAB_ID")
	private Integer cabId;
	@Column(name="CUSTOMER_ID")
	private Integer customerId;
	@Column(name="RECEIVER_NAME")
	private String receiverName;
	@Column(name="CUSTOMER_ADDRESS1")
	private String customerAddress1;
	@Column(name="CUSTOMER_ADDRESS2")
	private String customerAddress2;
	@Column(name="CUSTOMER_CITY")
	private String customerCity;
	@Column(name="CUSTOMER_STATE")
	private String customerState;
	@Column(name="CUSTOMER_ZIP")
	private String customerZip;
	@Column(name="CUSTOMER_LANDMARK")
	private String customerLandmark;
	@Column(name="CUSTOMER_DELIVERY_MOBILE")
	private String customerDeliveryMobile;
	public Integer getCabId() {
		return cabId;
	}
	public void setCabId(Integer cabId) {
		this.cabId = cabId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCustomerAddress1() {
		return customerAddress1;
	}
	public void setCustomerAddress1(String customerAddress1) {
		this.customerAddress1 = customerAddress1;
	}
	public String getCustomerAddress2() {
		return customerAddress2;
	}
	public void setCustomerAddress2(String customerAddress2) {
		this.customerAddress2 = customerAddress2;
	}
	public String getCustomerCity() {
		return customerCity;
	}
	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}
	public String getCustomerState() {
		return customerState;
	}
	public void setCustomerState(String customerState) {
		this.customerState = customerState;
	}
	public String getCustomerZip() {
		return customerZip;
	}
	public void setCustomerZip(String customerZip) {
		this.customerZip = customerZip;
	}
	public String getCustomerLandmark() {
		return customerLandmark;
	}
	public void setCustomerLandmark(String customerLandmark) {
		this.customerLandmark = customerLandmark;
	}
	public String getCustomerDeliveryMobile() {
		return customerDeliveryMobile;
	}
	public void setCustomerDeliveryMobile(String customerDeliveryMobile) {
		this.customerDeliveryMobile = customerDeliveryMobile;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
}
