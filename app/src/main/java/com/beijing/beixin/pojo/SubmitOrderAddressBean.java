package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

public class SubmitOrderAddressBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private int orgId;
	private List<SendWayBean> deliveryRuleVoList;

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public List<SendWayBean> getDeliveryRuleVoList() {
		return deliveryRuleVoList;
	}

	public void setDeliveryRuleVoList(List<SendWayBean> deliveryRuleVoList) {
		this.deliveryRuleVoList = deliveryRuleVoList;
	}

}
