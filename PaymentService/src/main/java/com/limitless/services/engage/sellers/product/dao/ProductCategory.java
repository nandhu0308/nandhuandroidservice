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
	@Column(name="ID")
	private int productCategoryId;
	@Column(name="PRODUCT_CATEGORY_NAME")
	private String productCategoryName;
	@Column(name="SELLER_ID")
	private Integer sellerId;
	@Column(name="PRODUCT_CATEGORY_IMAGE")
	private String prodcuctCategoryImage;
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
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public String getProdcuctCategoryImage() {
		return prodcuctCategoryImage;
	}
	public void setProdcuctCategoryImage(String prodcuctCategoryImage) {
		this.prodcuctCategoryImage = prodcuctCategoryImage;
	}
}
