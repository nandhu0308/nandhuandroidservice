package com.limitless.services.payment.PaymentService;

public class CustomerCreditResponseBean {

	private int sellerId;
	private String sellerName;
	private String sellerPhone;
	private double totalCredits;

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerPhone() {
		return sellerPhone;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	public double getTotalCredits() {
		return totalCredits;
	}

	public void setTotalCredits(double totalCredits) {
		this.totalCredits = totalCredits;
	}

}
