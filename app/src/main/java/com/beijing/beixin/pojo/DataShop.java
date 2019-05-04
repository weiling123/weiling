package com.beijing.beixin.pojo;

public class DataShop {

	private String shopInfId;
	private String skuId;
	private int number = 1;
	/**
	 * 1是选中0是没选中
	 */
	private int checkState = 1;

	public DataShop() {
	}

	public DataShop(String shopInfId, String skuId, int number, int checkState) {
		this.shopInfId = shopInfId;
		this.skuId = skuId;
		this.number = number;
		this.checkState = checkState;
	}

	public DataShop(String shopInfId, String skuId) {
		this.shopInfId = shopInfId;
		this.skuId = skuId;
	}

	public DataShop(String shopInfId, String skuId, int number) {
		this.shopInfId = shopInfId;
		this.skuId = skuId;
		this.number = number;
	}

	public String getShopInfId() {
		return shopInfId;
	}

	public void setShopInfId(String shopInfId) {
		this.shopInfId = shopInfId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void addNumber(int num) {
		number += num;
	}

	public int getCheckState() {
		return checkState;
	}

	public void setCheckState(int checkState) {
		this.checkState = checkState;
	}

	public boolean equals(DataShop dataShop) {
		return shopInfId.equals(dataShop.shopInfId) && skuId.equals(dataShop.skuId);
	}
}
