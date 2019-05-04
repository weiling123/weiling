package com.beijing.beixin.pojo;

import java.io.Serializable;

/**
 * 版本更新
 * 
 * @author ouyanghao
 *
 */
@SuppressWarnings("serial")
public class VersionBean implements Serializable {

	private String versionNumber;
	private String templateInnerCode;
	private String platformDeviceTypeCode;
	private String commentUrl;
	private String appVersionId;
	private String downLoadUrl;
	private String appStoreId;
	private String storeAppleUrl;
	private String store360Url;
	private String storeWandoujiaUrl;
	private String storeMiUrl;
	private String isForcedUpdate;
	private String changeLog;

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getTemplateInnerCode() {
		return templateInnerCode;
	}

	public void setTemplateInnerCode(String templateInnerCode) {
		this.templateInnerCode = templateInnerCode;
	}

	public String getPlatformDeviceTypeCode() {
		return platformDeviceTypeCode;
	}

	public void setPlatformDeviceTypeCode(String platformDeviceTypeCode) {
		this.platformDeviceTypeCode = platformDeviceTypeCode;
	}

	public String getCommentUrl() {
		return commentUrl;
	}

	public void setCommentUrl(String commentUrl) {
		this.commentUrl = commentUrl;
	}

	public String getAppVersionId() {
		return appVersionId;
	}

	public void setAppVersionId(String appVersionId) {
		this.appVersionId = appVersionId;
	}

	public String getDownLoadUrl() {
		return downLoadUrl;
	}

	public void setDownLoadUrl(String downLoadUrl) {
		this.downLoadUrl = downLoadUrl;
	}

	public String getAppStoreId() {
		return appStoreId;
	}

	public void setAppStoreId(String appStoreId) {
		this.appStoreId = appStoreId;
	}

	public String getStoreAppleUrl() {
		return storeAppleUrl;
	}

	public void setStoreAppleUrl(String storeAppleUrl) {
		this.storeAppleUrl = storeAppleUrl;
	}

	public String getStore360Url() {
		return store360Url;
	}

	public void setStore360Url(String store360Url) {
		this.store360Url = store360Url;
	}

	public String getStoreWandoujiaUrl() {
		return storeWandoujiaUrl;
	}

	public void setStoreWandoujiaUrl(String storeWandoujiaUrl) {
		this.storeWandoujiaUrl = storeWandoujiaUrl;
	}

	public String getStoreMiUrl() {
		return storeMiUrl;
	}

	public void setStoreMiUrl(String storeMiUrl) {
		this.storeMiUrl = storeMiUrl;
	}

	public String getIsForcedUpdate() {
		return isForcedUpdate;
	}

	public void setIsForcedUpdate(String isForcedUpdate) {
		this.isForcedUpdate = isForcedUpdate;
	}

	public String getChangeLog() {
		return changeLog;
	}

	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}
}
