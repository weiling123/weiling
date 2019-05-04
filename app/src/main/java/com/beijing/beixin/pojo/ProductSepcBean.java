package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 收货地址列表
 * 
 * @author ouyanghao
 *
 */
@SuppressWarnings("serial")
public class ProductSepcBean implements Serializable {
	private List<ProductSpecDetailBean> specValues;
	private String productUserSpecs;
	private String descr;
	private String specNm;
	private Integer specId;
	private String specTypeCode;

	public List<ProductSpecDetailBean> getSpecValues() {
		return specValues;
	}

	public void setSpecValues(List<ProductSpecDetailBean> specValues) {
		this.specValues = specValues;
	}

	public String getProductUserSpecs() {
		return productUserSpecs;
	}

	public void setProductUserSpecs(String productUserSpecs) {
		this.productUserSpecs = productUserSpecs;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getSpecNm() {
		return specNm;
	}

	public void setSpecNm(String specNm) {
		this.specNm = specNm;
	}

	public Integer getSpecId() {
		return specId;
	}

	public void setSpecId(Integer specId) {
		this.specId = specId;
	}

	public String getSpecTypeCode() {
		return specTypeCode;
	}

	public void setSpecTypeCode(String specTypeCode) {
		this.specTypeCode = specTypeCode;
	}

	public class ProductSpecDetailBean implements Serializable {
		private Boolean available;
		private Boolean checked;
		private int specId;
		private int specValueId;
		private String specValueNm;

		public Boolean getAvailable() {
			return available;
		}

		public void setAvailable(Boolean available) {
			this.available = available;
		}

		public Boolean getChecked() {
			return checked;
		}

		public void setChecked(Boolean checked) {
			this.checked = checked;
		}

		public int getSpecId() {
			return specId;
		}

		public void setSpecId(int specId) {
			this.specId = specId;
		}

		public int getSpecValueId() {
			return specValueId;
		}

		public void setSpecValueId(int specValueId) {
			this.specValueId = specValueId;
		}

		public String getSpecValueNm() {
			return specValueNm;
		}

		public void setSpecValueNm(String specValueNm) {
			this.specValueNm = specValueNm;
		}

	}
}
