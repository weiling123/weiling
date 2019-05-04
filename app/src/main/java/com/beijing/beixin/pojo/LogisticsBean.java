package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class LogisticsBean implements Serializable {

	private String orderNum;
	private String orderId;
	private String logisticsCompany;
	private List<LogisticsLog> logisticsLogs;
	private String logisticsNum;

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	public List<LogisticsLog> getLogisticsLogs() {
		return logisticsLogs;
	}

	public void setLogisticsLogs(List<LogisticsLog> logisticsLogs) {
		this.logisticsLogs = logisticsLogs;
	}

	public String getLogisticsNum() {
		return logisticsNum;
	}

	public void setLogisticsNum(String logisticsNum) {
		this.logisticsNum = logisticsNum;
	}

	public static class LogisticsLog implements Serializable {

		private String date;
		private String time;
		private String log;

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getLog() {
			return log;
		}

		public void setLog(String log) {
			this.log = log;
		}
	}
}
