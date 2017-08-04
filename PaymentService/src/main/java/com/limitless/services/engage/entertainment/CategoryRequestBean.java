package com.limitless.services.engage.entertainment;

public class CategoryRequestBean {
	private int customerId, broadcasterId;
	private boolean isLoggedIn;
	private boolean enablePage;
	private boolean isVerticalPage;
	private int categoryId, verticalIndex, horizontalIndex;
	

	
	public int getVerticalIndex() {
		return verticalIndex;
	}

	public void setVerticalIndex(int verticalIndex) {
		this.verticalIndex = verticalIndex;
	}

	public int getHorizontalIndex() {
		return horizontalIndex;
	}

	public void setHorizontalIndex(int horizontalIndex) {
		this.horizontalIndex = horizontalIndex;
	}

	public boolean getIsVerticalPage() {
		return isVerticalPage;
	}

	public void setIsVerticalPage(boolean isVerticalPage) {
		this.isVerticalPage = isVerticalPage;
	}

	public boolean isEnablePage() {
		return enablePage;
	}

	public void setEnablePage(boolean enablePage) {
		this.enablePage = enablePage;
	}

	public int getBroadcasterId() {
		return broadcasterId;
	}

	public void setBroadcasterId(int broadcasterId) {
		this.broadcasterId = broadcasterId;
	}

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
