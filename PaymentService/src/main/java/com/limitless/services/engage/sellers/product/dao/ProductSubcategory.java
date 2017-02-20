package com.limitless.services.engage.sellers.product.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product_subcategory", catalog="llcdb")
public class ProductSubcategory {
	@Id
	@GeneratedValue
	@Column(name="PRODUCT_SC_ID")
	private Integer productScId;
	@Column(name="PRODUCT_SC_NAME")
	private String productScName;
	@Column(name="PRODUCT_CATEGORY_ID")
	private Integer productCategoryId;
	public Integer getProductScId() {
		return productScId;
	}
	public void setProductScId(Integer productScId) {
		this.productScId = productScId;
	}
	public String getProductScName() {
		return productScName;
	}
	public void setProductScName(String productScName) {
		this.productScName = productScName;
	}
	public Integer getProductCategoryId() {
		return productCategoryId;
	}
	public void setProductCategoryId(Integer productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
}
