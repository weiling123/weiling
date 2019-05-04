package com.beijing.beixin.pojo;

import java.io.Serializable;

public class ShopInfoBean implements Serializable {

	private Integer shopId;
	private String logoUrl;
	private String shopNm;
	private String mobile;
	private String csadInf;
	private String companyQqUrl;
	private String levelNm;
	private String startDateString;
	private String newProductCount;
	private String promotionProductCount;
	private String attentionShopCount;
	private String productCount;
	private double productDescrSame;
	private double sellerServiceAttitude;
	private double sellerSendOutSpeed;
	private boolean isShopCollect;

	public String getProductCount() {
		return productCount;
	}

	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}

	public String getNewProductCount() {
		return newProductCount;
	}

	public void setNewProductCount(String newProductCount) {
		this.newProductCount = newProductCount;
	}

	public String getPromotionProductCount() {
		return promotionProductCount;
	}

	public void setPromotionProductCount(String promotionProductCount) {
		this.promotionProductCount = promotionProductCount;
	}

	public String getAttentionShopCount() {
		return attentionShopCount;
	}

	public void setAttentionShopCount(String attentionShopCount) {
		this.attentionShopCount = attentionShopCount;
	}

	public boolean isShopCollect() {
		return isShopCollect;
	}

	public void setShopCollect(boolean isShopCollect) {
		this.isShopCollect = isShopCollect;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getShopNm() {
		return shopNm;
	}

	public void setShopNm(String shopNm) {
		this.shopNm = shopNm;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCsadInf() {
		return csadInf;
	}

	public void setCsadInf(String csadInf) {
		this.csadInf = csadInf;
	}

	public String getCompanyQqUrl() {
		return companyQqUrl;
	}

	public void setCompanyQqUrl(String companyQqUrl) {
		this.companyQqUrl = companyQqUrl;
	}

	public String getLevelNm() {
		return levelNm;
	}

	public void setLevelNm(String levelNm) {
		this.levelNm = levelNm;
	}

	public String getStartDateString() {
		return startDateString;
	}

	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}

	public double getProductDescrSame() {
		return productDescrSame;
	}

	public void setProductDescrSame(double productDescrSame) {
		this.productDescrSame = productDescrSame;
	}

	public double getSellerServiceAttitude() {
		return sellerServiceAttitude;
	}

	public void setSellerServiceAttitude(double sellerServiceAttitude) {
		this.sellerServiceAttitude = sellerServiceAttitude;
	}

	public double getSellerSendOutSpeed() {
		return sellerSendOutSpeed;
	}

	public void setSellerSendOutSpeed(double sellerSendOutSpeed) {
		this.sellerSendOutSpeed = sellerSendOutSpeed;
	}
}
