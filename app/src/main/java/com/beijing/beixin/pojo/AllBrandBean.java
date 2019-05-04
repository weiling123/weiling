package com.beijing.beixin.pojo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AllBrandBean implements Serializable {

	private String code;
	private String shortName;
	private String brandDescr;
	private String brandId;
	private String brandNm;
	private String homeUrl;
	private String logo;
	private String metaTitle;
	private String metaDescr;
	private String metaKeywords;
	private String auxCode;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getBrandDescr() {
		return brandDescr;
	}

	public void setBrandDescr(String brandDescr) {
		this.brandDescr = brandDescr;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandNm() {
		return brandNm;
	}

	public void setBrandNm(String brandNm) {
		this.brandNm = brandNm;
	}

	public String getHomeUrl() {
		return homeUrl;
	}

	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getMetaTitle() {
		return metaTitle;
	}

	public void setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
	}

	public String getMetaDescr() {
		return metaDescr;
	}

	public void setMetaDescr(String metaDescr) {
		this.metaDescr = metaDescr;
	}

	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	public String getAuxCode() {
		return auxCode;
	}

	public void setAuxCode(String auxCode) {
		this.auxCode = auxCode;
	}
}
