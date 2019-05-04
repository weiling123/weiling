package com.beijing.beixin.pojo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SuccessBean implements Serializable {

	/**
	 * 状态，true为成功，false为操作失败
	 */
	private boolean success;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
