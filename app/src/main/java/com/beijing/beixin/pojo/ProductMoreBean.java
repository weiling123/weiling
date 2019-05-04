package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.beijing.beixin.pojo.ProductMoreBean.shopInfProxy.sku;

import android.R.integer;

@SuppressWarnings("unused")
public class ProductMoreBean implements Serializable {

	private String productId;
	private String isDelete;
	private String isOnSale;
	private String unitPrice;
	private String productNm;
	private String marketPrice;
	private String sellingPoint;
	private String standardProductId;
	private String salesVolume;
	private String image;
	private List<shopInfProxy> shopInfProxyList;
	private shop shop;
	private List<String> promotionList;
	private String shopInfProxySize;
	private String isAttention;
	private sku sku;
	private List<sku> skuList;
	private List appProductBusinessRule;

	public sku getSku() {
		return sku;
	}

	public void setSku(sku sku) {
		this.sku = sku;
	}

	public List getAppProductBusinessRule() {
		return appProductBusinessRule;
	}

	public void setAppProductBusinessRule(List appProductBusinessRule) {
		this.appProductBusinessRule = appProductBusinessRule;
	}

	public shop getShop() {
		return shop;
	}

	public void setShop(shop shop) {
		this.shop = shop;
	}

	public String getShopInfProxySize() {
		return shopInfProxySize;
	}

	public void setShopInfProxySize(String shopInfProxySize) {
		this.shopInfProxySize = shopInfProxySize;
	}

	public String getIsAttention() {
		return isAttention;
	}

	public void setIsAttention(String isAttention) {
		this.isAttention = isAttention;
	}

	public List<sku> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<sku> skuList) {
		this.skuList = skuList;
	}

	public String getStandardProductId() {
		return standardProductId;
	}

	public void setStandardProductId(String standardProductId) {
		this.standardProductId = standardProductId;
	}

	public List<shopInfProxy> getShopInfProxyList() {
		return shopInfProxyList;
	}

	public void setShopInfProxyList(List<shopInfProxy> shopInfProxyList) {
		this.shopInfProxyList = shopInfProxyList;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getIsOnSale() {
		return isOnSale;
	}

	public void setIsOnSale(String isOnSale) {
		this.isOnSale = isOnSale;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getProductNm() {
		return productNm;
	}

	public void setProductNm(String productNm) {
		this.productNm = productNm;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getSellingPoint() {
		return sellingPoint;
	}

	public void setSellingPoint(String sellingPoint) {
		this.sellingPoint = sellingPoint;
	}

	public String getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(String salesVolume) {
		this.salesVolume = salesVolume;
	}

	public List<String> getPromotionList() {
		return promotionList;
	}

	public void setPromotionList(List<String> promotionList) {
		this.promotionList = promotionList;
	}

	public static class shopInfProxy implements Serializable {

		private List<sku> skuList;
		private String productId;
		private shopProxy shopProxy;
		private String isCanBuy;
		private String minPrice;
		private String unitPrice;
		private String salesVolume;
		private String image;

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public String getUnitPrice() {
			return unitPrice;
		}

		public void setUnitPrice(String unitPrice) {
			this.unitPrice = unitPrice;
		}

		public List<sku> getSkuList() {
			return skuList;
		}

		public void setSkuList(List<sku> skuList) {
			this.skuList = skuList;
		}

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public shopProxy getShopProxy() {
			return shopProxy;
		}

		public void setShopProxy(shopProxy shopProxy) {
			this.shopProxy = shopProxy;
		}

		public String getIsCanBuy() {
			return isCanBuy;
		}

		public void setIsCanBuy(String isCanBuy) {
			this.isCanBuy = isCanBuy;
		}

		public String getMinPrice() {
			return minPrice;
		}

		public void setMinPrice(String minPrice) {
			this.minPrice = minPrice;
		}

		public String getSalesVolume() {
			return salesVolume;
		}

		public void setSalesVolume(String salesVolume) {
			this.salesVolume = salesVolume;
		}

		public static class shopProxy implements Serializable {

			private String name;
			private String unifiedModuleStr;
			private String csadOnlineDescr;
			private List images;
			private String shopAddr;
			private String shopDescrStr;
			// private List shopAttesation;
			private String shopProductTotal;
			private String productTotalCount;
			// private List defaultImage;
			private String baiduMapXAxis;
			private String mainUserLoginId;
			private shopRatingAvgVo shopRatingAvgVo;
			// private List csadInfList;
			private String shopNm;
			private String shopTemplateCatalog;
			private String shopInfId;
			private String subDomain;
			private String mobile;
			private String tel;
			private String sysOrgId;
			private String isFreeze;
			private String endDateString;
			private shopLevel shopLevel;
			private String orderTotalCount;
			private String shopGradeLevelId;
			private String companyQqUrl;
			private String startDateString;

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getUnifiedModuleStr() {
				return unifiedModuleStr;
			}

			public void setUnifiedModuleStr(String unifiedModuleStr) {
				this.unifiedModuleStr = unifiedModuleStr;
			}

			public String getCsadOnlineDescr() {
				return csadOnlineDescr;
			}

			public void setCsadOnlineDescr(String csadOnlineDescr) {
				this.csadOnlineDescr = csadOnlineDescr;
			}

			public List getImages() {
				return images;
			}

			public void setImages(List images) {
				this.images = images;
			}

			public String getShopAddr() {
				return shopAddr;
			}

			public void setShopAddr(String shopAddr) {
				this.shopAddr = shopAddr;
			}

			public String getShopDescrStr() {
				return shopDescrStr;
			}

			public void setShopDescrStr(String shopDescrStr) {
				this.shopDescrStr = shopDescrStr;
			}

			public String getShopProductTotal() {
				return shopProductTotal;
			}

			public void setShopProductTotal(String shopProductTotal) {
				this.shopProductTotal = shopProductTotal;
			}

			public String getProductTotalCount() {
				return productTotalCount;
			}

			public void setProductTotalCount(String productTotalCount) {
				this.productTotalCount = productTotalCount;
			}

			public String getBaiduMapXAxis() {
				return baiduMapXAxis;
			}

			public void setBaiduMapXAxis(String baiduMapXAxis) {
				this.baiduMapXAxis = baiduMapXAxis;
			}

			public String getMainUserLoginId() {
				return mainUserLoginId;
			}

			public void setMainUserLoginId(String mainUserLoginId) {
				this.mainUserLoginId = mainUserLoginId;
			}

			public shopRatingAvgVo getShopRatingAvgVo() {
				return shopRatingAvgVo;
			}

			public void setShopRatingAvgVo(shopRatingAvgVo shopRatingAvgVo) {
				this.shopRatingAvgVo = shopRatingAvgVo;
			}

			public String getShopNm() {
				return shopNm;
			}

			public void setShopNm(String shopNm) {
				this.shopNm = shopNm;
			}

			public String getShopTemplateCatalog() {
				return shopTemplateCatalog;
			}

			public void setShopTemplateCatalog(String shopTemplateCatalog) {
				this.shopTemplateCatalog = shopTemplateCatalog;
			}

			public String getShopInfId() {
				return shopInfId;
			}

			public void setShopInfId(String shopInfId) {
				this.shopInfId = shopInfId;
			}

			public String getSubDomain() {
				return subDomain;
			}

			public void setSubDomain(String subDomain) {
				this.subDomain = subDomain;
			}

			public String getMobile() {
				return mobile;
			}

			public void setMobile(String mobile) {
				this.mobile = mobile;
			}

			public String getTel() {
				return tel;
			}

			public void setTel(String tel) {
				this.tel = tel;
			}

			public String getSysOrgId() {
				return sysOrgId;
			}

			public void setSysOrgId(String sysOrgId) {
				this.sysOrgId = sysOrgId;
			}

			public String getIsFreeze() {
				return isFreeze;
			}

			public void setIsFreeze(String isFreeze) {
				this.isFreeze = isFreeze;
			}

			public String getEndDateString() {
				return endDateString;
			}

			public void setEndDateString(String endDateString) {
				this.endDateString = endDateString;
			}

			public shopLevel getShopLevel() {
				return shopLevel;
			}

			public void setShopLevel(shopLevel shopLevel) {
				this.shopLevel = shopLevel;
			}

			public String getOrderTotalCount() {
				return orderTotalCount;
			}

			public void setOrderTotalCount(String orderTotalCount) {
				this.orderTotalCount = orderTotalCount;
			}

			public String getShopGradeLevelId() {
				return shopGradeLevelId;
			}

			public void setShopGradeLevelId(String shopGradeLevelId) {
				this.shopGradeLevelId = shopGradeLevelId;
			}

			public String getCompanyQqUrl() {
				return companyQqUrl;
			}

			public void setCompanyQqUrl(String companyQqUrl) {
				this.companyQqUrl = companyQqUrl;
			}

			public String getStartDateString() {
				return startDateString;
			}

			public void setStartDateString(String startDateString) {
				this.startDateString = startDateString;
			}

			public static class shopLevel implements Serializable {

				private String levelNm;
				private String levelGrade;
				private String levelId;
				private String levelIcon;
			}

			public static class shopRatingAvgVo implements Serializable {

				private String sellerAverage;
				private String productDescrSame;
				private String sellerSendOutSpeed;
				private String sellerServiceAttitude;
			}
		}

		public static class sku implements Serializable {

			private String gbSixNineNo;
			private String maximumOrder;
			private String salePrice;
			private String stockNo;
			private String specNm;
			private String sysOrgId;
			private String productId;
			private String unit;
			private String volume;
			private String costPrice;
			private String skuId;
			private String weight;
			private String marketPrice;
			private String safetyStock;
			private String currentNum;

			public String getGbSixNineNo() {
				return gbSixNineNo;
			}

			public void setGbSixNineNo(String gbSixNineNo) {
				this.gbSixNineNo = gbSixNineNo;
			}

			public String getMaximumOrder() {
				return maximumOrder;
			}

			public void setMaximumOrder(String maximumOrder) {
				this.maximumOrder = maximumOrder;
			}

			public String getSalePrice() {
				return salePrice;
			}

			public void setSalePrice(String salePrice) {
				this.salePrice = salePrice;
			}

			public String getStockNo() {
				return stockNo;
			}

			public void setStockNo(String stockNo) {
				this.stockNo = stockNo;
			}

			public String getSpecNm() {
				return specNm;
			}

			public void setSpecNm(String specNm) {
				this.specNm = specNm;
			}

			public String getSysOrgId() {
				return sysOrgId;
			}

			public void setSysOrgId(String sysOrgId) {
				this.sysOrgId = sysOrgId;
			}

			public String getProductId() {
				return productId;
			}

			public void setProductId(String productId) {
				this.productId = productId;
			}

			public String getUnit() {
				return unit;
			}

			public void setUnit(String unit) {
				this.unit = unit;
			}

			public String getVolume() {
				return volume;
			}

			public void setVolume(String volume) {
				this.volume = volume;
			}

			public String getCostPrice() {
				return costPrice;
			}

			public void setCostPrice(String costPrice) {
				this.costPrice = costPrice;
			}

			public String getSkuId() {
				return skuId;
			}

			public void setSkuId(String skuId) {
				this.skuId = skuId;
			}

			public String getWeight() {
				return weight;
			}

			public void setWeight(String weight) {
				this.weight = weight;
			}

			public String getMarketPrice() {
				return marketPrice;
			}

			public void setMarketPrice(String marketPrice) {
				this.marketPrice = marketPrice;
			}

			public String getSafetyStock() {
				return safetyStock;
			}

			public void setSafetyStock(String safetyStock) {
				this.safetyStock = safetyStock;
			}

			public String getCurrentNum() {
				return currentNum;
			}

			public void setCurrentNum(String currentNum) {
				this.currentNum = currentNum;
			}
		}
	}

	public static class appSkuVos implements Serializable {

		private String unitPrice;
		private String specValueIds;
		private String skuId;
		private String stock;
	}

	public static class shop implements Serializable {

		private String csadInf;
		private String shopNm;
		private String shopInfId;
		private String shopLogo;
		private String productDescrSame;
		private String sellerSendOutSpeed;
		private String sellerServiceAttitude;

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

		public String getShopInfId() {
			return shopInfId;
		}

		public void setShopInfId(String shopInfId) {
			this.shopInfId = shopInfId;
		}

		public String getShopLogo() {
			return shopLogo;
		}

		public void setShopLogo(String shopLogo) {
			this.shopLogo = shopLogo;
		}

		public String getProductDescrSame() {
			return productDescrSame;
		}

		public void setProductDescrSame(String productDescrSame) {
			this.productDescrSame = productDescrSame;
		}

		public String getSellerSendOutSpeed() {
			return sellerSendOutSpeed;
		}

		public void setSellerSendOutSpeed(String sellerSendOutSpeed) {
			this.sellerSendOutSpeed = sellerSendOutSpeed;
		}

		public String getSellerServiceAttitude() {
			return sellerServiceAttitude;
		}

		public void setSellerServiceAttitude(String sellerServiceAttitude) {
			this.sellerServiceAttitude = sellerServiceAttitude;
		}
	}
}
