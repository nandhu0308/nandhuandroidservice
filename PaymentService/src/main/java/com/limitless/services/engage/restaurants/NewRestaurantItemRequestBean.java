package com.limitless.services.engage.restaurants;

public class NewRestaurantItemRequestBean {
	private int restaurantId;
	private int categoryId;
	private int subcategoryId;
	private String itemName;
	private float itemPrice;
	private String itemType;
	private String itemImage;
	private int itemAvailable;
	private int itemChefSpl;
	private int itemRecommended;
	private int itemLunch;
	private int itemBreakfast;
	private int itemDinner;
	private float itemDiscountRate;
	
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getSubcategoryId() {
		return subcategoryId;
	}
	public void setSubcategoryId(int subcategoryId) {
		this.subcategoryId = subcategoryId;
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
	public int getItemAvailable() {
		return itemAvailable;
	}
	public void setItemAvailable(int itemAvailable) {
		this.itemAvailable = itemAvailable;
	}
	public int getItemChefSpl() {
		return itemChefSpl;
	}
	public void setItemChefSpl(int itemChefSpl) {
		this.itemChefSpl = itemChefSpl;
	}
	public int getItemRecommended() {
		return itemRecommended;
	}
	public void setItemRecommended(int itemRecommended) {
		this.itemRecommended = itemRecommended;
	}
	public int getItemLunch() {
		return itemLunch;
	}
	public void setItemLunch(int itemLunch) {
		this.itemLunch = itemLunch;
	}
	public int getItemBreakfast() {
		return itemBreakfast;
	}
	public void setItemBreakfast(int itemBreakfast) {
		this.itemBreakfast = itemBreakfast;
	}
	public int getItemDinner() {
		return itemDinner;
	}
	public void setItemDinner(int itemDinner) {
		this.itemDinner = itemDinner;
	}
	public float getItemDiscountRate() {
		return itemDiscountRate;
	}
	public void setItemDiscountRate(float itemDiscountRate) {
		this.itemDiscountRate = itemDiscountRate;
	}
}
