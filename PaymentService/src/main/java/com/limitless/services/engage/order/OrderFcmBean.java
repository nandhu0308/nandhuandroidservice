package com.limitless.services.engage.order;

public class OrderFcmBean {
	private String to;
	private String priority;
	private OrderNotificationDataBean data;
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
	public OrderNotificationDataBean getData() {
		return data;
	}
	public void setData(OrderNotificationDataBean data) {
		this.data = data;
	}
}
