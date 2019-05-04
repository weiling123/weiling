
package com.beijing.beixin.bean;

public class Sku {

	private int productId;
	private int sysOrgId;
	private int skuId;
	private String stockNo;
	private int currentNum;
	private int safetyStock;
	private String specNm;
	private float costPrice;
	private float marketPrice;
	private float salePrice;
	private int maximumOrder;
	private String gbSixNineNo;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getSysOrgId() {
		return sysOrgId;
	}

	public void setSysOrgId(int sysOrgId) {
		this.sysOrgId = sysOrgId;
	}

	public int getSkuId() {
		return skuId;
	}

	public void setSkuId(int skuId) {
		this.skuId = skuId;
	}

	public String getStockNo() {
		return stockNo;
	}

	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}

	public int getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(int currentNum) {
		this.currentNum = currentNum;
	}

	public int getSafetyStock() {
		return safetyStock;
	}

	public void setSafetyStock(int safetyStock) {
		this.safetyStock = safetyStock;
	}

	public String getSpecNm() {
		return specNm;
	}

	public void setSpecNm(String specNm) {
		this.specNm = specNm;
	}

	public float getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(float costPrice) {
		this.costPrice = costPrice;
	}

	public float getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(float marketPrice) {
		this.marketPrice = marketPrice;
	}

	public float getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(float salePrice) {
		this.salePrice = salePrice;
	}

	public int getMaximumOrder() {
		return maximumOrder;
	}

	public void setMaximumOrder(int maximumOrder) {
		this.maximumOrder = maximumOrder;
	}

	public String getGbSixNineNo() {
		return gbSixNineNo;
	}

	public void setGbSixNineNo(String gbSixNineNo) {
		this.gbSixNineNo = gbSixNineNo;
	}
}
