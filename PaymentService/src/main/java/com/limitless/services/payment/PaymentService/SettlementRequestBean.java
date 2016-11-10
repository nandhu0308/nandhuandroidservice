package com.limitless.services.payment.PaymentService;

import java.util.Date;

public class SettlementRequestBean {
	private int trans_id;
	private String settlement_ref;
	private String trans_source;
	private double settlement_amount;
	private double fee_amount;
	private String settlement_date_time;
	public int getTrans_id() {
		return trans_id;
	}
	public void setTrans_id(int trans_id) {
		this.trans_id = trans_id;
	}
	public String getSettlement_ref() {
		return settlement_ref;
	}
	public void setSettlement_ref(String settlement_ref) {
		this.settlement_ref = settlement_ref;
	}
	public String getTrans_source() {
		return trans_source;
	}
	public void setTrans_source(String trans_source) {
		this.trans_source = trans_source;
	}
	public double getSettlement_amount() {
		return settlement_amount;
	}
	public void setSettlement_amount(double settlement_amount) {
		this.settlement_amount = settlement_amount;
	}
	public double getFee_amount() {
		return fee_amount;
	}
	public void setFee_amount(double fee_amount) {
		this.fee_amount = fee_amount;
	}
	public String getSettlement_date_time() {
		return settlement_date_time;
	}
	public void setSettlement_date_time(String settlement_date_time) {
		this.settlement_date_time = settlement_date_time;
	}
}
