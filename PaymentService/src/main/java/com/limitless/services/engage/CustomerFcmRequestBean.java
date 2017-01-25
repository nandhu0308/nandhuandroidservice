package com.limitless.services.engage;

public class CustomerFcmRequestBean {
	private String to;
	private String priority;
	//private CustomerNotificationBean notification;
	private CustomerDataBean data;
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
	public CustomerDataBean getData() {
		return data;
	}
	public void setData(CustomerDataBean data) {
		this.data = data;
	}
}
