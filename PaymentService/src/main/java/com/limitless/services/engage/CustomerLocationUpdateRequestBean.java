package com.limitless.services.engage;

public class CustomerLocationUpdateRequestBean {
	private int customerId;
	private String customerCity;
	private String customerCountry;
	private String customerZip;
	private String customerCountryIsoCode;
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerCity() {
		return customerCity;
	}
	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}
	public String getCustomerCountry() {
		return customerCountry;
	}
	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
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
}
