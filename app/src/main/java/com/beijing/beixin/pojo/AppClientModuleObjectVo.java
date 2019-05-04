package com.beijing.beixin.pojo;

/**
 * Created by lzw on 29/6/15.
 */
public class AppClientModuleObjectVo {

	private Integer appModuleObjectId;
	private String actionType;
	private Integer appModuleId;
	private String picUrl;
	private String openUrl;
	private String title;
	private Integer targetObjectId;
	private AppProductBaseVo product;

	public Integer getAppModuleObjectId() {
		return appModuleObjectId;
	}

	public void setAppModuleObjectId(Integer appModuleObjectId) {
		this.appModuleObjectId = appModuleObjectId;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public Integer getAppModuleId() {
		return appModuleId;
	}

	public void setAppModuleId(Integer appModuleId) {
		this.appModuleId = appModuleId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public AppProductBaseVo getProduct() {
		return product;
	}

	public void setProduct(AppProductBaseVo product) {
		this.product = product;
	}

	public Integer getTargetObjectId() {
		return targetObjectId;
	}

	public void setTargetObjectId(Integer targetObjectId) {
		this.targetObjectId = targetObjectId;
	}

	public String getOpenUrl() {
		return openUrl;
	}

	public void setOpenUrl(String openUrl) {
		this.openUrl = openUrl;
	}
}
