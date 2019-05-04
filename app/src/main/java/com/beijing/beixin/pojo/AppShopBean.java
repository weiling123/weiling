package com.beijing.beixin.pojo;

import java.io.Serializable;

import android.R.integer;

public class AppShopBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String attentionShopCount;
	private Integer newProductCount;
	private Integer promotionProductCount;
	private avgvo shopRatingAvgVo;

	public Integer getNewProductCount() {
		return newProductCount;
	}

	public void setNewProductCount(Integer newProductCount) {
		this.newProductCount = newProductCount;
	}

	public Integer getPromotionProductCount() {
		return promotionProductCount;
	}

	public void setPromotionProductCount(Integer promotionProductCount) {
		this.promotionProductCount = promotionProductCount;
	}

	public String getAttentionShopCount() {
		return attentionShopCount;
	}

	public void setAttentionShopCount(String attentionShopCount) {
		this.attentionShopCount = attentionShopCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public avgvo getShopRatingAvgVo() {
		return shopRatingAvgVo;
	}

	public void setShopRatingAvgVo(avgvo shopRatingAvgVo) {
		this.shopRatingAvgVo = shopRatingAvgVo;
	}

	public static class avgvo implements Serializable {

		private String productDescrSame;
		private String sellerServiceAttitude;
		private String sellerSendOutSpeed;
		private String sellerAverage;

		public String getProductDescrSame() {
			return productDescrSame;
		}

		public void setProductDescrSame(String productDescrSame) {
			this.productDescrSame = productDescrSame;
		}

		public String getSellerServiceAttitude() {
			return sellerServiceAttitude;
		}

		public void setSellerServiceAttitude(String sellerServiceAttitude) {
			this.sellerServiceAttitude = sellerServiceAttitude;
		}

		public String getSellerSendOutSpeed() {
			return sellerSendOutSpeed;
		}

		public void setSellerSendOutSpeed(String sellerSendOutSpeed) {
			this.sellerSendOutSpeed = sellerSendOutSpeed;
		}

		public String getSellerAverage() {
			return sellerAverage;
		}

		public void setSellerAverage(String sellerAverage) {
			this.sellerAverage = sellerAverage;
		}
	}

	/**
	 * 店铺ID
	 */
	private Integer shopInfId;
	/**
	 * 发货速度
	 */
	private Double sellerSendOutSpeed;
	/**
	 * 描述相符
	 */
	private Double productDescrSame;
	/**
	 * 服务态度
	 */
	private Double sellerServiceAttitude;
	/**
	 * 店铺名称
	 */
	private String shopNm;
	/**
	 * 客服QQ
	 */
	private String csadInf;
	/**
	 * 店铺logo
	 */
	private String shopLogo;
	/**
	 * 店铺logo
	 */
	private String logoUrl;

	public Integer getShopInfId() {
		return shopInfId;
	}

	public void setShopInfId(Integer shopInfId) {
		this.shopInfId = shopInfId;
	}

	public Double getSellerSendOutSpeed() {
		return sellerSendOutSpeed;
	}

	public void setSellerSendOutSpeed(Double sellerSendOutSpeed) {
		this.sellerSendOutSpeed = sellerSendOutSpeed;
	}

	public Double getProductDescrSame() {
		return productDescrSame;
	}

	public void setProductDescrSame(Double productDescrSame) {
		this.productDescrSame = productDescrSame;
	}

	public Double getSellerServiceAttitude() {
		return sellerServiceAttitude;
	}

	public void setSellerServiceAttitude(Double sellerServiceAttitude) {
		this.sellerServiceAttitude = sellerServiceAttitude;
	}

	public String getShopNm() {
		return shopNm;
	}

	public void setShopNm(String shopNm) {
		this.shopNm = shopNm;
	}

	public String getCsadInf() {
		return csadInf;
	}

	public void setCsadInf(String csadInf) {
		this.csadInf = csadInf;
	}

	public String getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
}
