package com.beijing.beixin.pojo;

import java.io.Serializable;

/**
 * 查询账户余额
 * 
 * @author ouyanghao
 *
 */
@SuppressWarnings("serial")
public class PrestoreTransactionLogBean implements Serializable {
	/**
	 * 积分描述
	 */
	private String reason;
	/**
	 * 交易时间
	 */
	private String transactionTime;
	/**
	 * 账户余额
	 */
	private Double transactionAmount;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

}
