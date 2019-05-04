package com.beijing.beixin.pojo;

import java.io.Serializable;

public class ShoppingCartDeleteBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String itemKey;
	private String orgId;

	public String getItemKey() {
		return itemKey;
	}

	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}
