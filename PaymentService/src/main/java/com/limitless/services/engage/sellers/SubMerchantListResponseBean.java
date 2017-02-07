package com.limitless.services.engage.sellers;

import java.util.List;

public class SubMerchantListResponseBean {
	private String message;
	private List<SubMerchantBean> subMerchants;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<SubMerchantBean> getSubMerchants() {
		return subMerchants;
	}
	public void setSubMerchants(List<SubMerchantBean> subMerchants) {
		this.subMerchants = subMerchants;
	}
	

}
