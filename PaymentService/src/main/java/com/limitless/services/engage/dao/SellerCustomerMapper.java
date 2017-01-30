package com.limitless.services.engage.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="seller_customer_mapper", catalog="llcdb")
public class SellerCustomerMapper {
	@Id
	@GeneratedValue
	@Column(name="SCM_ID")
	private Integer scmId;
	@Column(name="SELLER_ID")
	private Integer sellerId;
	@Column(name="CUSTOMER_ID")
	private Integer customerId;
	public Integer getScmId() {
		return scmId;
	}
	public void setScmId(Integer scmId) {
		this.scmId = scmId;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
}
