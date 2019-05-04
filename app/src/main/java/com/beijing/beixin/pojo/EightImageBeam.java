package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

public class EightImageBeam {

	private String innerCode;
	private String appModuleId;
	private String moduleTypeCode;
	private List<module> moduleObjects;

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	public String getAppModuleId() {
		return appModuleId;
	}

	public void setAppModuleId(String appModuleId) {
		this.appModuleId = appModuleId;
	}

	public String getModuleTypeCode() {
		return moduleTypeCode;
	}

	public void setModuleTypeCode(String moduleTypeCode) {
		this.moduleTypeCode = moduleTypeCode;
	}

	public List<module> getModuleObjects() {
		return moduleObjects;
	}

	public void setModuleObjects(List<module> moduleObjects) {
		this.moduleObjects = moduleObjects;
	}

	public static class module implements Serializable {

		private String title;
		// private List product;
		private String targetObjectId;
		private String appModuleId;
		private String appModuleObjectId;
		private String openUrl;
		private String actionType;
		private String picUrl;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getTargetObjectId() {
			return targetObjectId;
		}

		public void setTargetObjectId(String targetObjectId) {
			this.targetObjectId = targetObjectId;
		}

		public String getAppModuleId() {
			return appModuleId;
		}

		public void setAppModuleId(String appModuleId) {
			this.appModuleId = appModuleId;
		}

		public String getAppModuleObjectId() {
			return appModuleObjectId;
		}

		public void setAppModuleObjectId(String appModuleObjectId) {
			this.appModuleObjectId = appModuleObjectId;
		}

		public String getOpenUrl() {
			return openUrl;
		}

		public void setOpenUrl(String openUrl) {
			this.openUrl = openUrl;
		}

		public String getActionType() {
			return actionType;
		}

		public void setActionType(String actionType) {
			this.actionType = actionType;
		}

		public String getPicUrl() {
			return picUrl;
		}

		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}
	}
}
