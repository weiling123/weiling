package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

public class SendWayBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private DeliveryWay deliveryWay;// 配送方式
	private Double deliveryPrice;// 配送价格
	private DeliveryRule deliveryRule;// 配送规则
	private String descr;// 描述
	private Integer deliveryLogisticsCompanyId;// 配送公司id
	private String deliveryRuleNm;// 配送方式名称
	private String wayRules;// 配送规则

	public String getWayRules() {
		return wayRules;
	}

	public void setWayRules(String wayRules) {
		this.wayRules = wayRules;
	}

	public DeliveryWay getDeliveryWay() {
		return deliveryWay;
	}

	public void setDeliveryWay(DeliveryWay deliveryWay) {
		this.deliveryWay = deliveryWay;
	}

	public Double getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setDeliveryPrice(Double deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}

	public DeliveryRule getDeliveryRule() {
		return deliveryRule;
	}

	public void setDeliveryRule(DeliveryRule deliveryRule) {
		this.deliveryRule = deliveryRule;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Integer getDeliveryLogisticsCompanyId() {
		return deliveryLogisticsCompanyId;
	}

	public void setDeliveryLogisticsCompanyId(Integer deliveryLogisticsCompanyId) {
		this.deliveryLogisticsCompanyId = deliveryLogisticsCompanyId;
	}

	public String getDeliveryRuleNm() {
		return deliveryRuleNm;
	}

	public void setDeliveryRuleNm(String deliveryRuleNm) {
		this.deliveryRuleNm = deliveryRuleNm;
	}

}
