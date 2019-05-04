package com.beijing.beixin.pojo;

import java.io.Serializable;

/**
 * 收货地址列表
 * 
 * @author ouyanghao
 *
 */
@SuppressWarnings("serial")
public class BookMoreDetailBean implements Serializable {
	private String name;
	private String description;
	private String valueString;
	private String position;
	private String values;
	private String innerCode;
	private String PRINTDATE;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValueString() {
		return valueString;
	}

	public void setValueString(String valueString) {
		this.valueString = valueString;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	public String getPRINTDATE() {
		return PRINTDATE;
	}

	public void setPRINTDATE(String pRINTDATE) {
		PRINTDATE = pRINTDATE;
	}

}
