package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 商品详情
 * 
 * @author ouyanghao
 * 
 */
@SuppressWarnings("serial")
public class ProductDetail implements Serializable {

	// private String standardProductId;
	private List shopInfProxyList;
	private List appSpecVoList;
	private String companyQq;
	private String isCanBuy;
	private List promotionList;
	private String shopInfProxySize;
	private String attentionShopCount;
	private String productCount;
	private String shopDynamic;
	private String shopMobile;
	private String pluCode;
	private String videoUrl;
	private String standardAttrGroup;
	private List<attrGroupProxy> attrGroupProxyList;
	private List<PromotionBean> appProductBusinessRule;

	public List<PromotionBean> getAppProductBusinessRule() {
		return appProductBusinessRule;
	}

	public void setAppProductBusinessRule(List<PromotionBean> appProductBusinessRule) {
		this.appProductBusinessRule = appProductBusinessRule;
	}

	public String getStandardAttrGroup() {
		return standardAttrGroup;
	}

	public void setStandardAttrGroup(String standardAttrGroup) {
		this.standardAttrGroup = standardAttrGroup;
	}

	public String getPluCode() {
		return pluCode;
	}

	public void setPluCode(String pluCode) {
		this.pluCode = pluCode;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public List<attrGroupProxy> getAttrGroupProxyList() {
		return attrGroupProxyList;
	}

	public void setAttrGroupProxyList(List<attrGroupProxy> attrGroupProxyList) {
		this.attrGroupProxyList = attrGroupProxyList;
	}

	public static class attrGroupProxy implements Serializable {

		private String description;
		private String position;
		private String attrGroupId;
		private String attrGroupNm;
		private List<dic> dicValues;
		private String dicValueMap;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = position;
		}

		public String getAttrGroupId() {
			return attrGroupId;
		}

		public void setAttrGroupId(String attrGroupId) {
			this.attrGroupId = attrGroupId;
		}

		public String getAttrGroupNm() {
			return attrGroupNm;
		}

		public void setAttrGroupNm(String attrGroupNm) {
			this.attrGroupNm = attrGroupNm;
		}

		public List<dic> getDicValues() {
			return dicValues;
		}

		public void setDicValues(List<dic> dicValues) {
			this.dicValues = dicValues;
		}

		public String getDicValueMap() {
			return dicValueMap;
		}

		public void setDicValueMap(String dicValueMap) {
			this.dicValueMap = dicValueMap;
		}

		public static class dic implements Serializable {

			private String name;
			private String description;
			private String valueString;
			private String position;
			private List values;
			private String innerCode;
			private String dicId;

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

			public List getValues() {
				return values;
			}

			public void setValues(List values) {
				this.values = values;
			}

			public String getInnerCode() {
				return innerCode;
			}

			public void setInnerCode(String innerCode) {
				this.innerCode = innerCode;
			}

			public String getDicId() {
				return dicId;
			}

			public void setDicId(String dicId) {
				this.dicId = dicId;
			}
		}
	}

	public String getShopMobile() {
		return shopMobile;
	}

	public void setShopMobile(String shopMobile) {
		this.shopMobile = shopMobile;
	}

	public String getAttentionShopCount() {
		return attentionShopCount;
	}

	public void setAttentionShopCount(String attentionShopCount) {
		this.attentionShopCount = attentionShopCount;
	}

	public String getProductCount() {
		return productCount;
	}

	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}

	public String getShopDynamic() {
		return shopDynamic;
	}

	public void setShopDynamic(String shopDynamic) {
		this.shopDynamic = shopDynamic;
	}

	private List<detail> detailList;

	public List<detail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<detail> detailList) {
		this.detailList = detailList;
	}

	public static class detail implements Serializable {

		private String name;
		private String description;
		private String valueString;
		private String position;
		private List<String> values;
		private String innerCode;
		private String dicId;

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

		public List<String> getValues() {
			return values;
		}

		public void setValues(List<String> values) {
			this.values = values;
		}

		public String getInnerCode() {
			return innerCode;
		}

		public void setInnerCode(String innerCode) {
			this.innerCode = innerCode;
		}

		public String getDicId() {
			return dicId;
		}

		public void setDicId(String dicId) {
			this.dicId = dicId;
		}
	}

	// public String getStandardProductId() {
	// return standardProductId;
	// }
	//
	// public void setStandardProductId(String standardProductId) {
	// this.standardProductId = standardProductId;
	// }

	public List getShopInfProxyList() {
		return shopInfProxyList;
	}

	public void setShopInfProxyList(List shopInfProxyList) {
		this.shopInfProxyList = shopInfProxyList;
	}

	public List getAppSpecVoList() {
		return appSpecVoList;
	}

	public void setAppSpecVoList(List appSpecVoList) {
		this.appSpecVoList = appSpecVoList;
	}

	public String getCompanyQq() {
		return companyQq;
	}

	public void setCompanyQq(String companyQq) {
		this.companyQq = companyQq;
	}

	public String getIsCanBuy() {
		return isCanBuy;
	}

	public void setIsCanBuy(String isCanBuy) {
		this.isCanBuy = isCanBuy;
	}

	public List getPromotionList() {
		return promotionList;
	}

	public void setPromotionList(List promotionList) {
		this.promotionList = promotionList;
	}

	public String getShopInfProxySize() {
		return shopInfProxySize;
	}

	public void setShopInfProxySize(String shopInfProxySize) {
		this.shopInfProxySize = shopInfProxySize;
	}

	/**
	 * 商品名称
	 */
	private String productNm;
	/**
	 * 商品id
	 */
	private Integer productId;
	/**
	 * 销量
	 */
	private Integer salesVolume;
	/**
	 * 会员价
	 */
	private Double unitPrice;
	/**
	 * 卖点
	 */
	private String sellingPoint;
	/**
	 * 市场价
	 */
	private Double marketPrice;
	/**
	 * 评论条数
	 */
	private Integer commentTotalCount;
	/**
	 * 是否开启多规格boolean
	 */
	private String isMultiSpecEnabled;
	/**
	 * 图片列表
	 */
	private List imageList;
	/**
	 * 默认的规格
	 */
	private com.beijing.beixin.bean.Sku sku;
	/**
	 * 好评数量
	 */
	private Integer goodCommentTotalCount;
	/**
	 * 中评数量
	 */
	private Integer normalCommentTotalCount;
	/**
	 * 差评数量
	 */
	private Integer badCommentTotalCount;
	/**
	 * 好评率
	 */
	private Integer goodRate;
	/**
	 * 是否被收藏，”N”不被收藏，”Y”已被收藏
	 */
	private String isCollected;
	/**
	 * 是否在售，”Y”表示在售，”N”不在售
	 */
	private String isOnSale;
	// /**
	// * 商品图片
	// */
	// private String image;
	/**
	 * 商家信息
	 */
	private shop shop;

	// public String getImage() {
	// return image;
	// }
	//
	//
	// public void setImage(String image) {
	// this.image = image;
	// }
	public String getProductNm() {
		return productNm;
	}

	public void setProductNm(String productNm) {
		this.productNm = productNm;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getSellingPoint() {
		return sellingPoint;
	}

	public void setSellingPoint(String sellingPoint) {
		this.sellingPoint = sellingPoint;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Integer getCommentTotalCount() {
		return commentTotalCount;
	}

	public void setCommentTotalCount(Integer commentTotalCount) {
		this.commentTotalCount = commentTotalCount;
	}

	public String getIsMultiSpecEnabled() {
		return isMultiSpecEnabled;
	}

	public void setIsMultiSpecEnabled(String isMultiSpecEnabled) {
		this.isMultiSpecEnabled = isMultiSpecEnabled;
	}

	public List getImageList() {
		return imageList;
	}

	public void setImageList(List imageList) {
		this.imageList = imageList;
	}

	public com.beijing.beixin.bean.Sku getSku() {
		return sku;
	}

	public void setSku(com.beijing.beixin.bean.Sku sku) {
		this.sku = sku;
	}

	public Integer getGoodCommentTotalCount() {
		return goodCommentTotalCount;
	}

	public void setGoodCommentTotalCount(Integer goodCommentTotalCount) {
		this.goodCommentTotalCount = goodCommentTotalCount;
	}

	public Integer getNormalCommentTotalCount() {
		return normalCommentTotalCount;
	}

	public void setNormalCommentTotalCount(Integer normalCommentTotalCount) {
		this.normalCommentTotalCount = normalCommentTotalCount;
	}

	public Integer getBadCommentTotalCount() {
		return badCommentTotalCount;
	}

	public void setBadCommentTotalCount(Integer badCommentTotalCount) {
		this.badCommentTotalCount = badCommentTotalCount;
	}

	public Integer getGoodRate() {
		return goodRate;
	}

	public void setGoodRate(Integer goodRate) {
		this.goodRate = goodRate;
	}

	public String getIsCollected() {
		return isCollected;
	}

	public void setIsCollected(String isCollected) {
		this.isCollected = isCollected;
	}

	public String getIsOnSale() {
		return isOnSale;
	}

	public void setIsOnSale(String isOnSale) {
		this.isOnSale = isOnSale;
	}

	public shop getShop() {
		return shop;
	}

	public void setShop(shop shop) {
		this.shop = shop;
	}

	public static class shop implements Serializable {

		/**
		 * 店铺ID
		 */
		private String shopInfId;
		/**
		 * 发货速度
		 */
		private String sellerSendOutSpeed;
		/**
		 * 商品描述相似度
		 */
		private String productDescrSame;
		/**
		 * 服务态度
		 */
		private String sellerServiceAttitude;
		/**
		 * 店铺名称
		 */
		private String shopNm;
		/**
		 * 联系电话
		 */
		private String csadInf;
		/**
		 * 店铺logo地址
		 */
		private String shopLogo;

		public String getShopInfId() {
			return shopInfId;
		}

		public void setShopInfId(String shopInfId) {
			this.shopInfId = shopInfId;
		}

		public String getSellerSendOutSpeed() {
			return sellerSendOutSpeed;
		}

		public void setSellerSendOutSpeed(String sellerSendOutSpeed) {
			this.sellerSendOutSpeed = sellerSendOutSpeed;
		}

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

		public String getCsadInf() {
			return csadInf;
		}

		public void setCsadInf(String csadInf) {
			this.csadInf = csadInf;
		}

		public String getShopNm() {
			return shopNm;
		}

		public void setShopNm(String shopNm) {
			this.shopNm = shopNm;
		}

		public String getShopLogo() {
			return shopLogo;
		}

		public void setShopLogo(String shopLogo) {
			this.shopLogo = shopLogo;
		}
	}
}
