package com.limitless.services.engage.restaurants.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="restaurants")
public class Restaurants {
	@Id
	@GeneratedValue
	@Column(name="RESTAURANT_ID")
	private Integer restaurantId;
	@Column(name="RESTAURANT_NAME")
	private String restaurantName;
	@Column(name="RESTAURANT_CITY")
	private String restaurantCity;
	@Column(name="SELLER_ID")
	private Integer sellerId;
	@Column(name="RESTAURANT_PREMIUM")
	private Integer restaurantPremium;
	@Column(name="RESTAURANT_ORDER_STYLE")
	private Integer restaurantOrderStyle;
	@Column(name="CREATED_TIME", insertable = false, updatable = false)
	private Date createdTime;
	@Column(name="RESTAURANT_PHONE")
	private String restaurantPhone;
	
	@Column(nullable = false, name="TAKE_AWAY_POD", columnDefinition = "TINYINT(1)")
	private boolean takeAwayPod;
	@Column(nullable = false, name="EAT_IN_POD", columnDefinition = "TINYINT(1)")
	private boolean eatInPod;
	@Column(nullable = false, name="HOME_DELIVERY_POD", columnDefinition = "TINYINT(1)")
	private boolean homeDeliveryPod;
	
	public Integer getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getRestaurantCity() {
		return restaurantCity;
	}
	public void setRestaurantCity(String restaurantCity) {
		this.restaurantCity = restaurantCity;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getRestaurantPremium() {
		return restaurantPremium;
	}
	public void setRestaurantPremium(Integer restaurantPremium) {
		this.restaurantPremium = restaurantPremium;
	}
	public Integer getRestaurantOrderStyle() {
		return restaurantOrderStyle;
	}
	public void setRestaurantOrderStyle(Integer restaurantOrderStyle) {
		this.restaurantOrderStyle = restaurantOrderStyle;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getRestaurantPhone() {
		return restaurantPhone;
	}
	public void setRestaurantPhone(String restaurantPhone) {
		this.restaurantPhone = restaurantPhone;
	}
	public boolean getTakeAwayPod() {
		return takeAwayPod;
	}
	public void setTakeAwayPod(boolean takeAwayPod) {
		this.takeAwayPod = takeAwayPod;
	}
	public boolean getEatInPod() {
		return eatInPod;
	}
	public void setEatInPod(boolean eatInPod) {
		this.eatInPod = eatInPod;
	}
	public boolean getHomeDeliveryPod() {
		return homeDeliveryPod;
	}
	public void setHomeDeliveryPod(boolean homeDeliveryPod) {
		this.homeDeliveryPod = homeDeliveryPod;
	}
	
	
}
