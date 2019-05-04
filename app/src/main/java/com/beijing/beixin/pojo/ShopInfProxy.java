package com.beijing.beixin.pojo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: myj Date: 12-11-21 Time: 上午11:30
 */
public class ShopInfProxy {

	/* 店铺ID */
	private Integer shopInfId;

	/* 店铺名称 */
	private String shopNm;

	/* 店铺模板目录 */
	private String shopTemplateCatalog;

	/* 店铺联系人姓名 */
	private String name;

	/* 商家店址 */
	private String shopAddr;

	/* 联系人电话 */
	private String tel;

	/* 联系人手机 */
	private String mobile;

	/* 经营开始日期 */
	private String startDateString;

	/* 经营结束日期 */
	private String endDateString;

	/* 店铺商品总数 */
	private Integer shopProductTotal;

	/* 客服信息 */
	private List<String> csadInfList;

	/* 店铺评价 */
	private ShopRatingAvgVo shopRatingAvgVo;

	/* 店铺评价等级 */
	private ShopLevelProxy shopLevel;

	// 店铺logo ${shopInfProxy.images["100X100"]}
	private List<BaseImageProxy> images = new ArrayList<BaseImageProxy>();

	// 取掌柜loginID
	private String mainUserLoginId;

	// 商品详细页 统一模块
	private String unifiedModuleStr;

	private String isFreeze;

	private Integer shopGradeLevelId;

	private Integer sysOrgId;

	// 客服在线时间
	private String csadOnlineDescr;

	// 二级域名
	private String subDomain;

	// 店铺QQ
	private String companyQqUrl;

	// 百度地图X坐标
	private String baiduMapXAxis;

	// 百度地图Y坐标
	private String baiduMapYAxis;

	public String getBaiduMapXAxis() {
		return baiduMapXAxis;
	}

	public void setBaiduMapXAxis(String baiduMapXAxis) {
		this.baiduMapXAxis = baiduMapXAxis;
	}

	public String getBaiduMapYAxis() {
		return baiduMapYAxis;
	}

	public void setBaiduMapYAxis(String baiduMapYAxis) {
		this.baiduMapYAxis = baiduMapYAxis;
	}

	public Integer getShopInfId() {
		return shopInfId;
	}

	public void setShopInfId(Integer shopInfId) {
		this.shopInfId = shopInfId;
	}

	public Integer getShopGradeLevelId() {
		return shopGradeLevelId;
	}

	public void setShopGradeLevelId(Integer shopGradeLevelId) {
		this.shopGradeLevelId = shopGradeLevelId;
	}

	public String getShopNm() {
		return shopNm;
	}

	public void setShopNm(String shopNm) {
		this.shopNm = shopNm;
	}

	public String getShopAddr() {
		return shopAddr;
	}

	public void setShopAddr(String shopAddr) {
		this.shopAddr = shopAddr;
	}

	public String getShopTemplateCatalog() {
		return shopTemplateCatalog;
	}

	public void setShopTemplateCatalog(String shopTemplateCatalog) {
		this.shopTemplateCatalog = shopTemplateCatalog;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStartDateString() {
		return startDateString;
	}

	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}

	public String getEndDateString() {
		return endDateString;
	}

	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}

	public String getUnifiedModuleStr() {
		return unifiedModuleStr;
	}

	public void setUnifiedModuleStr(String unifiedModuleStr) {
		this.unifiedModuleStr = unifiedModuleStr;
	}

	public void setShopProductTotal(Integer shopProductTotal) {
		this.shopProductTotal = shopProductTotal;
	}

	public List<String> getCsadInfList() {
		return csadInfList;
	}

	public void setCsadInfList(List<String> csadInfList) {
		this.csadInfList = csadInfList;
	}

	public void setShopRatingAvgVo(ShopRatingAvgVo shopRatingAvgVo) {
		this.shopRatingAvgVo = shopRatingAvgVo;
	}

	public void setShopLevel(ShopLevelProxy shopLevel) {
		this.shopLevel = shopLevel;
	}

	public List<BaseImageProxy> getImages() {
		return images;
	}

	public void setImages(List<BaseImageProxy> images) {
		this.images = images;
	}

	public BaseImageProxy getDefaultImage() {
		if (images != null && images.size() > 0) {
			return images.get(0);
		} else {
			return new BaseImageProxy("");
		}
	}

	public void setMainUserLoginId(String mainUserLoginId) {
		this.mainUserLoginId = mainUserLoginId;
	}

	public String getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(String freeze) {
		isFreeze = freeze;
	}

	public String getCsadOnlineDescr() {
		return csadOnlineDescr;
	}

	public void setCsadOnlineDescr(String csadOnlineDescr) {
		this.csadOnlineDescr = csadOnlineDescr;
	}

	public Integer getSysOrgId() {
		return sysOrgId;
	}

	public void setSysOrgId(Integer sysOrgId) {
		this.sysOrgId = sysOrgId;
	}

	public String getSubDomain() {
		return this.subDomain;
	}

	public void setSubDomain(String value) {
		this.subDomain = value;
	}

	public String getCompanyQqUrl() {
		return this.companyQqUrl;
	}

	public void setCompanyQqUrl(String companyQQUrl) {
		this.companyQqUrl = companyQQUrl;
	}

}
