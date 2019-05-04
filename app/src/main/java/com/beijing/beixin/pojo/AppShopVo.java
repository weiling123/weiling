package com.beijing.beixin.pojo;

/**
 * Created by imall-mac on 15-6-9.
 */
public class AppShopVo {

	private Integer shopInfId;

	private String shopNm;

	private String shopLogo;

	private double productDescrSame;// 描述相符

	private double sellerServiceAttitude;// 服务态度

	private double sellerSendOutSpeed;// 发货速度

	private String csadInf;// 客服QQ

	public String getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}

	public Integer getShopInfId() {
		return shopInfId;
	}

	public void setShopInfId(Integer shopInfId) {
		this.shopInfId = shopInfId;
	}

	public String getShopNm() {
		return shopNm;
	}

	public void setShopNm(String shopNm) {
		this.shopNm = shopNm;
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

	public String getCsadInf() {
		return csadInf;
	}

	public void setCsadInf(String csadInf) {
		this.csadInf = csadInf;
	}
}
