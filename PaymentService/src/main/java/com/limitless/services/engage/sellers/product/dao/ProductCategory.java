package com.limitless.services.engage.sellers.product.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product_category", catalog="llcdb")
public class ProductCategory {
	@Id
	@GeneratedValue
	@Column(name="PRODUCT_CATEGORY_ID")
	private int productCategoryId;
	@Column(name="PRODUCT_CATEGORY_NAME")
	private String productCategoryName;
	public int getProductCategoryId() {
		return productCategoryId;
	}
	public void setProductCategoryId(int productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	public String getProductCategoryName() {
		return productCategoryName;
	}
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
}
