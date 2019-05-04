package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

public class ClassifyRightBean implements Serializable {

	/**
	 * 父节点
	 */
	private Integer sysObjectId;
	/**
	 * 分类名称
	 */
	private String categoryNm;
	/**
	 * 分类Id
	 */
	private Integer categoryId;
	/**
	 * 子节点名称集合
	 */
	private String subCateStr;
	/**
	 * 子节点
	 */
	private List<AppCateGoryVo> subCategory;

	public Integer getSysObjectId() {
		return sysObjectId;
	}

	public void setSysObjectId(Integer sysObjectId) {
		this.sysObjectId = sysObjectId;
	}

	public String getCategoryNm() {
		return categoryNm;
	}

	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getSubCateStr() {
		return subCateStr;
	}

	public void setSubCateStr(String subCateStr) {
		this.subCateStr = subCateStr;
	}

	public List<AppCateGoryVo> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(List<AppCateGoryVo> subCategory) {
		this.subCategory = subCategory;
	}

	public static class AppCateGoryVo implements Serializable {

		/**
		 * 父节点
		 */
		private Integer sysObjectId;
		/**
		 * 分类名称
		 */
		private String categoryNm;
		/**
		 * 分类Id
		 */
		private Integer categoryId;
		/**
		 * 子节点名称集合
		 */
		private String subCateStr;
		/**
		 * 子节点
		 */
		private String subCategory;

		public Integer getSysObjectId() {
			return sysObjectId;
		}

		public void setSysObjectId(Integer sysObjectId) {
			this.sysObjectId = sysObjectId;
		}

		public String getCategoryNm() {
			return categoryNm;
		}

		public void setCategoryNm(String categoryNm) {
			this.categoryNm = categoryNm;
		}

		public Integer getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(Integer categoryId) {
			this.categoryId = categoryId;
		}

		public String getSubCateStr() {
			return subCateStr;
		}

		public void setSubCateStr(String subCateStr) {
			this.subCateStr = subCateStr;
		}

		public String getSubCategory() {
			return subCategory;
		}

		public void setSubCategory(String subCategory) {
			this.subCategory = subCategory;
		}
	}

}
