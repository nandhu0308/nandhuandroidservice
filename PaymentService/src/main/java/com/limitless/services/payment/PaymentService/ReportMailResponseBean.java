package com.limitless.services.payment.PaymentService;

public class ReportMailResponseBean {
	private String message;
	private int totalMerchants;
	private double totalTxnAmounts;
	private double totalSettlements;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getTotalMerchants() {
		return totalMerchants;
	}
	public void setTotalMerchants(int totalMerchants) {
		this.totalMerchants = totalMerchants;
	}
	public double getTotalTxnAmounts() {
		return totalTxnAmounts;
	}
	public void setTotalTxnAmounts(double totalTxnAmounts) {
		this.totalTxnAmounts = totalTxnAmounts;
	}
	public double getTotalSettlements() {
		return totalSettlements;
	}
	public void setTotalSettlements(double totalSettlements) {
		this.totalSettlements = totalSettlements;
	}
}
