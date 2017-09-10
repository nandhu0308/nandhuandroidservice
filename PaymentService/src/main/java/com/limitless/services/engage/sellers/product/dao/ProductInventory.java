package com.limitless.services.engage.sellers.product.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="product_inventory")
public class ProductInventory {
	@Id
	@Column(name="PRODUCT_ID")
	private Integer productId;
	@Column(name="PRODUCT_STOCK")
	private Integer productStock;
	@Column(name="PRODUCT_SOLD")
	private Integer productSold;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="INVENTORY_UPDATE_TIME")
	@Version
	private Date inventoryUpdateTime;
	public Date getInventoryUpdateTime() {
		return inventoryUpdateTime;
	}
	public void setInventoryUpdateTime(Date inventoryUpdateTime) {
		this.inventoryUpdateTime = inventoryUpdateTime;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getProductStock() {
		return productStock;
	}
	public void setProductStock(Integer productStock) {
		this.productStock = productStock;
	}
	public Integer getProductSold() {
		return productSold;
	}
	public void setProductSold(Integer productSold) {
		this.productSold = productSold;
	}
}
