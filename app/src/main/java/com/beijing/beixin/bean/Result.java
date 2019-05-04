
package com.beijing.beixin.bean;

import java.util.ArrayList;
import java.util.List;

public class Result {
	private List<SpecValue> specValues = new ArrayList<SpecValue>();
	private String descr;
	private String specNm;
	private int specId;
	private String specTypeCode;

	public List<SpecValue> getSpecValues() {
		return specValues;
	}

	public void setSpecValues(List<SpecValue> specValues) {
		this.specValues = specValues;
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

	public int getSpecId() {
		return specId;
	}

	public void setSpecId(int specId) {
		this.specId = specId;
	}

	public String getSpecTypeCode() {
		return specTypeCode;
	}

	public void setSpecTypeCode(String specTypeCode) {
		this.specTypeCode = specTypeCode;
	}

}
