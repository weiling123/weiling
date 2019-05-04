package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

public class SaveSendWayBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String shopName;
	private Integer orgId;// 店铺机构ID
	private List<String> proImageList;
	private List<SendWayBean> sendWayList;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public List<String> getProImageList() {
		return proImageList;
	}

	public void setProImageList(List<String> proImageList) {
		this.proImageList = proImageList;
	}

	public List<SendWayBean> getSendWayList() {
		return sendWayList;
	}

	public void setSendWayList(List<SendWayBean> sendWayList) {
		this.sendWayList = sendWayList;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

}
