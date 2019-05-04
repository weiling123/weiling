/*
 * Web Site: http://www.iloosen.com
 * Since 2010 - 2011
 */

package com.beijing.beixin.pojo;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "prd_sku")
public class Sku {
	private static final long serialVersionUID = 5454155825314635342L;

	// date formats

	// columns START
	private Integer skuId;
	private String specNm;
	private String stockNo;
	private Integer currentNum;
	private Integer safetyStock;
	private String unit;
	private Integer productId;
	private Integer sysOrgId;
	private Double weight;

	private Double volume;
	private Integer maximumOrder;

	private Double salePrice;
	private Double costPrice;

	private Double marketPrice;

	private String gbSixNineNo;
	// columns END

	public Sku() {
	}

	public Sku(Integer skuId) {
		this.skuId = skuId;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public void setSkuId(Integer value) {
		this.skuId = value;
	}

	public Integer getSkuId() {
		return this.skuId;
	}

	public void setSpecNm(String value) {
		this.specNm = value;
	}

	public String getSpecNm() {
		return this.specNm == null ? "" : specNm;
	}

	public void setStockNo(String value) {
		this.stockNo = value;
	}

	public String getStockNo() {
		return this.stockNo;
	}

	public void setCurrentNum(Integer value) {
		this.currentNum = value;
	}

	public Integer getCurrentNum() {
		return this.currentNum;
	}

	public void setSafetyStock(Integer value) {
		this.safetyStock = value;
	}

	public Integer getSafetyStock() {
		return this.safetyStock;
	}

	public void setUnit(String value) {
		this.unit = value;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setProductId(Integer value) {
		this.productId = value;
	}

	public Integer getProductId() {
		return this.productId;
	}

	public void setSysOrgId(Integer value) {
		this.sysOrgId = value;
	}

	public Integer getSysOrgId() {
		return this.sysOrgId;
	}

	public Integer getMaximumOrder() {
		return maximumOrder;
	}

	public void setMaximumOrder(Integer maximumOrder) {
		this.maximumOrder = maximumOrder;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getGbSixNineNo() {
		return gbSixNineNo;
	}

	public void setGbSixNineNo(String gbSixNineNo) {
		this.gbSixNineNo = gbSixNineNo;
	}

}
