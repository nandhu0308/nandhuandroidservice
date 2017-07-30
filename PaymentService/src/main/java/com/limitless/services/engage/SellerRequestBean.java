package com.limitless.services.engage;

public class SellerRequestBean {
private int customerId;
private String searchString;
private boolean isLoggedIn;

public int getCustomerId() {
	return customerId;
}
public void setCustomerid(int customerid) {
	this.customerId = customerid;
}
public String getSearchString() {
	return searchString;
}
public void setSearchString(String searchString) {
	this.searchString = searchString;
}
public boolean getIsLoggedIn() {
	return isLoggedIn;
}

public void setIsLoggedIn(boolean isLoggedIn) {
	this.isLoggedIn = isLoggedIn;
}


}
