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
@Table(name = "entity_rating")
public class EntityRating {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "RATING")
	private Integer rating;
	
	@Column(name = "CUSTOMER_ID")
	private Integer customerId;
	
	@Column(name = "ENTITY_ID")
	private Integer entityId;
	
	@Column(name = "ENTITY_TYPE")
	private String entityType;
	
	@Column(name = "REVIEW")
	private String review;
	
	@Column(name = "CREATED_ON", insertable = false, updatable = false)
	private Date createdOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_ON")
	@Version
	private Date updatedOn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	
}
