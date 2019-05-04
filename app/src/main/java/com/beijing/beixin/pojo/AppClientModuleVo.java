package com.beijing.beixin.pojo;

import java.util.List;

/**
 * Created by liuray on 6/5/14.
 */
public class AppClientModuleVo {
	// 模块 ID
	private Integer appModuleId;
	// 模块编码
	private String innerCode;
	// 模块类型代码
	private String moduleTypeCode;

	private List<AppClientModuleObjectVo> moduleObjects;

	public Integer getAppModuleId() {
		return appModuleId;
	}

	public void setAppModuleId(Integer appModuleId) {
		this.appModuleId = appModuleId;
	}

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	public String getModuleTypeCode() {
		return moduleTypeCode;
	}

	public void setModuleTypeCode(String moduleTypeCode) {
		this.moduleTypeCode = moduleTypeCode;
	}

	public List<AppClientModuleObjectVo> getModuleObjects() {
		return moduleObjects;
	}

	public void setModuleObjects(List<AppClientModuleObjectVo> moduleObjects) {
		this.moduleObjects = moduleObjects;
	}
}
