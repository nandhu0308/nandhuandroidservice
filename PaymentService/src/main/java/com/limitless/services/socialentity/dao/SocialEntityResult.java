package com.limitless.services.socialentity.dao;

import javax.persistence.Column;
import javax.persistence.Entity;


public class SocialEntityResult {
//	 @Column(name="CUSTOMERSUCCESS")
private int customerSuccess;
//	 @Column(name="TOTALSUCCESS")
private int totalSuccess;
	 
	 
public int getCustomerSuccess() {
	return customerSuccess;
}
public void setCustomerSuccess(int customerSuccess) {
	this.customerSuccess = customerSuccess;
}
public int getTotalSuccess() {
	return totalSuccess;
}
public void setTotalSuccess(int totalSuccess) {
	this.totalSuccess = totalSuccess;
}

}
