package com.beijing.beixin.pojo;

import java.util.List;

/**
 * Created by ADMIN on 2015/12/21.
 */
public class StandardProductProxy {

	private Integer productId;

	private List<Sku> skuList;

	private Double minPrice;

	private boolean isCanBuy;

	private ShopInfProxy shopProxy;

	private Integer salesVolume;

	public Integer getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public List<Sku> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<Sku> skuList) {
		this.skuList = skuList;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public ShopInfProxy getShopProxy() {
		return shopProxy;
	}

	public void setShopProxy(ShopInfProxy shopProxy) {
		this.shopProxy = shopProxy;
	}

	public boolean getIsCanBuy() {
		return isCanBuy;
	}

	public void setIsCanBuy(boolean isCanBuy) {
		this.isCanBuy = isCanBuy;
	}
}
