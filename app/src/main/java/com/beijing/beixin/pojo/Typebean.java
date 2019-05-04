package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 分类
 * 
 * @author ouyanghao
 * 
 */
public class Typebean implements Serializable {

	private String sysObjectId;
	private String categoryNm;
	private String categoryId;
	private String subCateStr;
	private List<String> subCategory;

	public String getSysObjectId() {
		return sysObjectId;
	}

	public void setSysObjectId(String sysObjectId) {
		this.sysObjectId = sysObjectId;
	}

	public String getCategoryNm() {
		return categoryNm;
	}

	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getSubCateStr() {
		return subCateStr;
	}

	public void setSubCateStr(String subCateStr) {
		this.subCateStr = subCateStr;
	}

	public List<String> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(List<String> subCategory) {
		this.subCategory = subCategory;
	}
}
