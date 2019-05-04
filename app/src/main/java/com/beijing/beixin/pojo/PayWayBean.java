package com.beijing.beixin.pojo;

import java.io.Serializable;

public class PayWayBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String payWayTypeCode;
	private String payWayName;

	public String getPayWayTypeCode() {
		return payWayTypeCode;
	}

	public void setPayWayTypeCode(String payWayTypeCode) {
		this.payWayTypeCode = payWayTypeCode;
	}

	public String getPayWayName() {
		return payWayName;
	}

	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
	}

}
