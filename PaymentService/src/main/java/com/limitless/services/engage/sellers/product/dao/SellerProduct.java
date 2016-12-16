package com.limitless.services.engage.sellers.product.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="seller_product", catalog="llcdb")
public class SellerProduct {
	
	@Id
	@Column(name="SELLER_PRODUCT_ID")
	private Integer sellerProductId;
	@Column(name="SELLER_ID")
	private Integer sellerId;
	@Column(name="PRODUCT_ID")
	private Integer productId;

	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
}
