package com.limitless.services.engage.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="seller_customer_mapper")
public class SellerCustomerMapper {
	@Id
	@GeneratedValue
	@Column(name="SCM_ID")
	private Integer scmId;
	@Column(name="SELLER_ID")
	private Integer sellerId;
	@Column(name="CUSTOMER_ID")
	private Integer customerId;
	@Column(name="FIRST_VISIT_TIME", insertable = false, updatable = false)
	private Date firstVisitTime;
	@Column(name="LAST_VISIT_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	private Date lastVisitTime;
	@Column(name="VISIT_COUNT")
	private Integer visitCount;
	
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
	public Date getFirstVisitTime() {
		return firstVisitTime;
	}
	public void setFirstVisitTime(Date firstVisitTime) {
		this.firstVisitTime = firstVisitTime;
	}
	public Date getLastVisitTime() {
		return lastVisitTime;
	}
	public void setLastVisitTime(Date lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}
	public Integer getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}
}
