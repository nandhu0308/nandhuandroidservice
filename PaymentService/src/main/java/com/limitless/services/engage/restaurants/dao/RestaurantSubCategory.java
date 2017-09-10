package com.limitless.services.engage.restaurants.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="restaurant_item_subcategory")
public class RestaurantSubCategory {
	@Id
	@GeneratedValue
	@Column(name="SUB_CATEGORY_ID")
	private Integer subCategoryId;
	@Column(name="SUBCATEGORY_NAME")
	private String subcategoryName;
	@Column(name="CATEGORY_ID")
	private Integer categoryId;
	public Integer getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	public String getSubcategoryName() {
		return subcategoryName;
	}
	public void setSubcategoryName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
}
