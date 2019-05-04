package com.beijing.beixin.pojo;

import java.io.Serializable;

public class appSkuVos implements Serializable {

	private static final long serialVersionUID = 1L;
	private int skuId;
	private double unitPrice;
	private Object specValueIds;

	private int currentNum;

	private String stockNo;

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

	public int getSkuId() {
		return skuId;
	}

	public void setSkuId(int skuId) {
		this.skuId = skuId;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Object getSpecValueIds() {
		return specValueIds;
	}

	public void setSpecValueIds(Object specValueIds) {
		this.specValueIds = specValueIds;
	}

}
