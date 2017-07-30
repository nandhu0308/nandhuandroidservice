package com.limitless.services.engage.entertainment;

public class CategoryRequestBean {
	private int customerId;
	private boolean isLoggedIn;
	private int categoryId;
	

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public boolean getIsLoggedIn() {
		return isLoggedIn;
	}

	public void setIsLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

}
