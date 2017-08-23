package com.limitless.services.socialentity;

public class SocialEntityRatingRequestBean {
	private int entityId;
	private int customerId;
	private int rating;
	private String entityType, review;
	public int getEntityId() {
		return entityId;
	}
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
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
	
	

}
