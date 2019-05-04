package com.beijing.beixin.pojo;

import java.io.Serializable;

public class DeliveryRule implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer deliveryWayId;// 配送方式id
	private String isSupportCod;// 是否支持货到付款
	private Integer deliveryRuleId;// 配送规则id
	private String descr;// 配送规则描述
	private Double firstWeight;// 首重
	private Double continueWeight;// 续重
	private Double firstWeightPrice;// 首重价格
	private Double continueWeightPrice;// 每增加续重增加的价格

	public Integer getDeliveryWayId() {
		return deliveryWayId;
	}

	public void setDeliveryWayId(Integer deliveryWayId) {
		this.deliveryWayId = deliveryWayId;
	}

	public String getIsSupportCod() {
		return isSupportCod;
	}

	public void setIsSupportCod(String isSupportCod) {
		this.isSupportCod = isSupportCod;
	}

	public Integer getDeliveryRuleId() {
		return deliveryRuleId;
	}

	public void setDeliveryRuleId(Integer deliveryRuleId) {
		this.deliveryRuleId = deliveryRuleId;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Double getFirstWeight() {
		return firstWeight;
	}

	public void setFirstWeight(Double firstWeight) {
		this.firstWeight = firstWeight;
	}

	public Double getContinueWeight() {
		return continueWeight;
	}

	public void setContinueWeight(Double continueWeight) {
		this.continueWeight = continueWeight;
	}

	public Double getFirstWeightPrice() {
		return firstWeightPrice;
	}

	public void setFirstWeightPrice(Double firstWeightPrice) {
		this.firstWeightPrice = firstWeightPrice;
	}

	public Double getContinueWeightPrice() {
		return continueWeightPrice;
	}

	public void setContinueWeightPrice(Double continueWeightPrice) {
		this.continueWeightPrice = continueWeightPrice;
	}

}
