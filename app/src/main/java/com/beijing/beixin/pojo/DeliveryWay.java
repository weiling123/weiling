package com.beijing.beixin.pojo;

import java.io.Serializable;

public class DeliveryWay implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer position;// 配送方式排序
	private Integer deliveryWayId;// 配送方式id
	private String ruleNm;// 配送方式规则名称
	private String isEnable;// 是否启用
	private String descr;// 配送方式描述
	private Integer deliveryLogisticsCompanyId;// 配送公司id

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getDeliveryWayId() {
		return deliveryWayId;
	}

	public void setDeliveryWayId(Integer deliveryWayId) {
		this.deliveryWayId = deliveryWayId;
	}

	public String getRuleNm() {
		return ruleNm;
	}

	public void setRuleNm(String ruleNm) {
		this.ruleNm = ruleNm;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
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

}
