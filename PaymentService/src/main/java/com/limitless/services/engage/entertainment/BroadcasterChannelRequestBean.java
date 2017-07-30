package com.limitless.services.engage.entertainment;

public class BroadcasterChannelRequestBean {
	private String broadcasterName;
	private String channelName;
	private int customerId;
	private boolean isLoggedIn;
	
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

	public String getBroadcasterName() {
		return broadcasterName;
	}

	public void setBroadcasterName(String broadcasterName) {
		this.broadcasterName = broadcasterName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
}
