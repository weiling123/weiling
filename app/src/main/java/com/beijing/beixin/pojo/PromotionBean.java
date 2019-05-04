package com.beijing.beixin.pojo;

import java.io.Serializable;

public class PromotionBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String businessRuleNm;
	private String ruleType;
	private String ruleCode;
	private String ruleTypeName;
	private String ruleEnumName;

	public String getBusinessRuleNm() {
		return businessRuleNm;
	}

	public void setBusinessRuleNm(String businessRuleNm) {
		this.businessRuleNm = businessRuleNm;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public String getRuleTypeName() {
		return ruleTypeName;
	}

	public void setRuleTypeName(String ruleTypeName) {
		this.ruleTypeName = ruleTypeName;
	}

	public String getRuleEnumName() {
		return ruleEnumName;
	}

	public void setRuleEnumName(String ruleEnumName) {
		this.ruleEnumName = ruleEnumName;
	}

}
