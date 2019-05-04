
package com.beijing.beixin.bean;

import java.util.ArrayList;
import java.util.List;

public class ProductSpec {

	private String ctx;
	private int shareCurrentRequestTime;
	private List<Result> result = new ArrayList<Result>();
	private float price;
	private int stock;
	private Sku sku;

	public String getCtx() {
		return ctx;
	}

	public void setCtx(String ctx) {
		this.ctx = ctx;
	}

	public int getShareCurrentRequestTime() {
		return shareCurrentRequestTime;
	}

	public void setShareCurrentRequestTime(int shareCurrentRequestTime) {
		this.shareCurrentRequestTime = shareCurrentRequestTime;
	}

	public List<Result> getResult() {
		return result;
	}

	public void setResult(List<Result> result) {
		this.result = result;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Sku getSku() {
		return sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

}
