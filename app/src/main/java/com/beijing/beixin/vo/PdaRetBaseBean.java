package com.beijing.beixin.vo;

import java.io.Serializable;

/**
 * 通用 移动终端接口 请求结果返回参数
 * 
 * @author liu jinbang
 * 
 */
@SuppressWarnings("serial")
public class PdaRetBaseBean implements Serializable {

	private String status; // 1 成功 0 失败
	private String statusStr;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public PdaRetBaseBean(String status, String statusStr) {
		super();
		this.status = status;
		this.statusStr = statusStr;
	}

	public PdaRetBaseBean() {
		super();
		// TODO Auto-generated constructor stub
	}
}
