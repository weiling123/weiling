package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

public class detailBean implements Serializable {
	private String productId;
	private String isDelete;
	private String productNm;
	// private List<E> skuList;
	private String salesVolume;
	private String isOnSale;
	private String unitPrice;
	private String marketPrice;
	private String sellingPoint;
	private String standardProductId;
	private String image;
	// private List<E> shopInfProxyList;
	// private com.beijing.beixin.pojo.ShoppingCartCouponBean.shop shop;
	// private List<E> promotionList;
	private String shopInfProxySize;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getProductNm() {
		return productNm;
	}

	public void setProductNm(String productNm) {
		this.productNm = productNm;
	}

	public String getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(String salesVolume) {
		this.salesVolume = salesVolume;
	}

	public String getIsOnSale() {
		return isOnSale;
	}

	public void setIsOnSale(String isOnSale) {
		this.isOnSale = isOnSale;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getSellingPoint() {
		return sellingPoint;
	}

	public void setSellingPoint(String sellingPoint) {
		this.sellingPoint = sellingPoint;
	}

	public String getStandardProductId() {
		return standardProductId;
	}

	public void setStandardProductId(String standardProductId) {
		this.standardProductId = standardProductId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getShopInfProxySize() {
		return shopInfProxySize;
	}

	public void setShopInfProxySize(String shopInfProxySize) {
		this.shopInfProxySize = shopInfProxySize;
	}
}
