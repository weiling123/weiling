package com.beijing.beixin.vo;

import java.io.Serializable;

/**
 * sap账号密码登录 返回结果的vo
 * 
 * @author wenjian
 * 
 */
public class LoginReturn implements Serializable {

	private static final long serialVersionUID = 1L;
	private String returnstr;
	private String returnnum;

	public String getReturnstr() {
		return returnstr;
	}

	public void setReturnstr(String returnstr) {
		this.returnstr = returnstr;
	}

	public String getReturnnum() {
		return returnnum;
	}

	public void setReturnnum(String returnnum) {
		this.returnnum = returnnum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public LoginReturn(String returnstr, String returnnum) {
		super();
		this.returnstr = returnstr;
		this.returnnum = returnnum;
	}

	public LoginReturn() {
		super();
	}
}
