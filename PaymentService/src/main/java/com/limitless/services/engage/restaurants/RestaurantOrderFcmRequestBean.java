package com.limitless.services.engage.restaurants;

public class RestaurantOrderFcmRequestBean {
	private String to;
	private String priority;
	private RestaurantOrderDataBean data;
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public RestaurantOrderDataBean getData() {
		return data;
	}
	public void setData(RestaurantOrderDataBean data) {
		this.data = data;
	}
}
