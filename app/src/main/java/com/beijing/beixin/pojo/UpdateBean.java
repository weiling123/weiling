package com.beijing.beixin.pojo;

import java.io.Serializable;

public class UpdateBean implements Serializable {
	private String app_version;// 当前版本
	private String app_date;// 版本时间
	private String clear_data;// 清除数据
	private String force_install;// 强制更新
	private String update_content;// 更新内容
	private String min_version;// 最小版本

	public String getApp_version() {
		return app_version;
	}

	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}

	public String getApp_date() {
		return app_date;
	}

	public void setApp_date(String app_date) {
		this.app_date = app_date;
	}

	public String getClear_data() {
		return clear_data;
	}

	public void setClear_data(String clear_data) {
		this.clear_data = clear_data;
	}

	public String getForce_install() {
		return force_install;
	}

	public void setForce_install(String force_install) {
		this.force_install = force_install;
	}

	public String getUpdate_content() {
		return update_content;
	}

	public void setUpdate_content(String update_content) {
		this.update_content = update_content;
	}

	public String getMin_version() {
		return min_version;
	}

	public void setMin_version(String min_version) {
		this.min_version = min_version;
	}

}
