package com.limitless.services.engage.restaurants.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="restaurant_item")
public class RestaurantItems {	
	@Id
	@GeneratedValue
	@Column(name="ITEM_ID")
	private Integer itemId;
	@Column(name="RESTAURANT_ID")
	private Integer restaurantId;
	@Column(name="ITEM_NAME")
	private String itemName;
	@Column(name="ITEM_PRICE")
	private float itemPrice;
	@Column(name="ITEM_TYPE")
	private String itemType;
	@Column(name="ITEM_IMAGE")
	private String itemImage;
	@Column(name="AVAILABLE")
	private Integer available;
	@Column(name="CHEF_SPL")
	private Integer chefSpl;
	@Column(name="RECOMMENDED")
	private Integer recommended;
	@Column(name="CATEGORY_ID")
	private Integer categoryId;
	@Column(name="SUBCATEGORY_ID")
	private Integer subcategoryId;
	@Column(name="LUNCH")
	private Integer lunch;
	@Column(name="BREAKFAST")
	private Integer breakfast;
	@Column(name="DINNER")
	private Integer dinner;
	@Column(name="DISCOUNT_RATE")
	private double discountRate;
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public float getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(float itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getItemImage() {
		return itemImage;
	}
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}
	public Integer getAvailable() {
		return available;
	}
	public void setAvailable(Integer available) {
		this.available = available;
	}
	public Integer getChefSpl() {
		return chefSpl;
	}
	public void setChefSpl(Integer chefSpl) {
		this.chefSpl = chefSpl;
	}
	public Integer getRecommended() {
		return recommended;
	}
	public void setRecommended(Integer recommended) {
		this.recommended = recommended;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getSubcategoryId() {
		return subcategoryId;
	}
	public void setSubcategoryId(Integer subcategoryId) {
		this.subcategoryId = subcategoryId;
	}
	public Integer getLunch() {
		return lunch;
	}
	public void setLunch(Integer lunch) {
		this.lunch = lunch;
	}
	public Integer getBreakfast() {
		return breakfast;
	}
	public void setBreakfast(Integer breakfast) {
		this.breakfast = breakfast;
	}
	public Integer getDinner() {
		return dinner;
	}
	public void setDinner(Integer dinner) {
		this.dinner = dinner;
	}
	public double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}
	
}
