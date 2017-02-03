package com.limitless.services.engage.restaurants.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="restaurant_item_category", catalog="llcdb")
public class RestaurantCategory {
	@Id
	@GeneratedValue
	@Column(name="CATEGORY_ID")
	private Integer categoryId;
	@Column(name="CATEGORY_NAME")
	private String categoryName;
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
