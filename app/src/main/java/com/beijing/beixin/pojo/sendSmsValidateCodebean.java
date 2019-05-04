package com.beijing.beixin.pojo;

import java.io.Serializable;

/**
 * 用户注册，发送短信验证码
 * 
 * @author ouyanghao
 * 
 */
public class sendSmsValidateCodebean implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 注册手机
	 */
	private String mobile;
	/**
	 * 状态，‘0’为验证成功
	 */
	private String statCode;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStatCode() {
		return statCode;
	}

	public void setStatCode(String statCode) {
		this.statCode = statCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@SuppressWarnings("serial")
	public static class errorObject implements Serializable {

		private String errorData;
		private String errorCode;
		private String errorText;

		public String getErrorData() {
			return errorData;
		}

		public void setErrorData(String errorData) {
			this.errorData = errorData;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public String getErrorText() {
			return errorText;
		}

		public void setErrorText(String errorText) {
			this.errorText = errorText;
		}
	}
}
