package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

public class ProductCollectionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 删除标记，Y是删除，N是没删除
	 */
	private String isDelete;
	/**
	 * 商品ID
	 */
	private Integer productId;
	/**
	 * 商品名称
	 */
	private String productNm;
	/**
	 * 是否在售，Y为是，N为不是
	 */
	private String isOnSale;
	/**
	 * 销量
	 */
	private Integer salesVolume;
	/**
	 * 销售价
	 */
	private Double unitPrice;
	/**
	 * 市场价
	 */
	private Double marketPrice;
	/**
	 * 商品图片
	 */
	private String image;
	/**
	 * 店铺信息
	 */
	private AppShopBean shop;
	/**
	 * 收藏总数
	 */
	private boolean totalCount;
	private List appProductBusinessRule;

	public List getAppProductBusinessRule() {
		return appProductBusinessRule;
	}

	public void setAppProductBusinessRule(List appProductBusinessRule) {
		this.appProductBusinessRule = appProductBusinessRule;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductNm() {
		return productNm;
	}

	public void setProductNm(String productNm) {
		this.productNm = productNm;
	}

	public String getIsOnSale() {
		return isOnSale;
	}

	public void setIsOnSale(String isOnSale) {
		this.isOnSale = isOnSale;
	}

	public Integer getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public AppShopBean getShop() {
		return shop;
	}

	public void setShop(AppShopBean shop) {
		this.shop = shop;
	}

	public boolean isTotalCount() {
		return totalCount;
	}

	public void setTotalCount(boolean totalCount) {
		this.totalCount = totalCount;
	}

}
