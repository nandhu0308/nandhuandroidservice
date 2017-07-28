package com.limitless.services.socialentity.dao;

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
@Table(name = "entity_viewers", catalog = "llcdb")
public class EntityViewers {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer Id;
	@Column(name = "CUSTOMER_ID")
	private Integer customerId;
	@Column(name = "CUSTOMER_LOGGED_IN")
	private Boolean customerLoggedIn;
	@Column(name = "ENTITY_ID")
	private Integer entityId;
	@Column(name = "ENTITY_TYPE")
	private String entityType;
	@Column(name = "VIEWING")
	private Boolean viewing;
	@Column(name = "VIEW_DATE")
	private Boolean viewDate;
	@Column(name = "CREATED_TIME", insertable = false, updatable = false)
	private Date createdTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	@Column(name = "UPDATED_TIME")
	private Date updatedTime;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Boolean getCustomerLoggedIn() {
		return customerLoggedIn;
	}

	public void setCustomerLoggedIn(Boolean customerLoggedIn) {
		this.customerLoggedIn = customerLoggedIn;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public Boolean getViewing() {
		return viewing;
	}

	public void setViewing(Boolean viewing) {
		this.viewing = viewing;
	}

	public Boolean getViewDate() {
		return viewDate;
	}

	public void setViewDate(Boolean viewDate) {
		this.viewDate = viewDate;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

}
