package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 商品列表
 * 
 * @author ouyanghao
 * 
 */
@SuppressWarnings("serial")
public class ProductlistBean implements Serializable {

	/**
	 * 商品是否删除.”Y”:已删除,”N”:未删除
	 */
	private String isDelete;
	/**
	 * 商品id
	 */
	private Integer productId;
	/**
	 * 商品名称
	 */
	private String productNm;
	/**
	 * 是否在售,”Y”:在售,”N”:下架
	 */
	private String isOnSale;
	/**
	 * 销量
	 */
	private Integer salesVolume;
	/**
	 * 售价
	 */
	private double unitPrice;
	/**
	 * 卖点
	 */
	private String sellingPoint;
	/**
	 * 是否收藏
	 */
	private String isAttention;
	private List appProductBusinessRule;

	public List getAppProductBusinessRule() {
		return appProductBusinessRule;
	}

	public void setAppProductBusinessRule(List appProductBusinessRule) {
		this.appProductBusinessRule = appProductBusinessRule;
	}

	public String getIsAttention() {
		return isAttention;
	}

	public void setIsAttention(String isAttention) {
		this.isAttention = isAttention;
	}

	/**
	 * 超市售价
	 */
	private double marketPrice;
	/**
	 * 图片地址
	 */
	private String image;
	/**
	 * 店铺信息子节点
	 */
	private shop shop;
	private List<shopInfProxy> shopInfProxyList;
	private int shopInfProxySize;
	private List<skuListBean> skuList;
	private skuListBean sku;

	public skuListBean getSku() {
		return sku;
	}

	public void setSku(skuListBean sku) {
		this.sku = sku;
	}

	// public List<skuListBean> getSkuList() {
	// return skuList;
	// }

	public void setSkuList(List<skuListBean> skuList) {
		this.skuList = skuList;
	}

	public int getShopInfProxySize() {
		return shopInfProxySize;
	}

	public void setShopInfProxySize(int shopInfProxySize) {
		this.shopInfProxySize = shopInfProxySize;
	}

	public List<shopInfProxy> getShopInfProxyList() {
		return shopInfProxyList;
	}

	public void setShopInfProxyList(List<shopInfProxy> shopInfProxyList) {
		this.shopInfProxyList = shopInfProxyList;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}

	public ProductlistBean() {
		super();
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductNm() {
		return productNm;
	}

	public void setProductNm(String productNm) {
		this.productNm = productNm;
	}

	public String getIsOnSale() {
		return isOnSale;
	}

	public void setIsOnSale(String isOnSale) {
		this.isOnSale = isOnSale;
	}

	public int getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(int salesVolume) {
		this.salesVolume = salesVolume;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getSellingPoint() {
		return sellingPoint;
	}

	public void setSellingPoint(String sellingPoint) {
		this.sellingPoint = sellingPoint;
	}

	public double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public shop getShop() {
		return shop;
	}

	public void setShop(shop shop) {
		this.shop = shop;
	}

	public static class shopInfProxy implements Serializable {

		private List<sku> skuList;

		public List<sku> getSkuList() {
			return skuList;
		}

		public void setSkuList(List<sku> skuList) {
			this.skuList = skuList;
		}

		public static class sku implements Serializable {

			private String productId;
			private String sysOrgId;
			private String skuId;
			private String currentNum;
			private String safetyStock;
			private String specNm;
			private String stockNo;
			private String weight;
			private String volume;
			private String costPrice;
			private String marketPrice;
			private String salePrice;
			private String maximumOrder;
			private String unit;
			private String gbSixNineNo;
			private String marketingChannel;

			public String getProductId() {
				return productId;
			}

			public void setProductId(String productId) {
				this.productId = productId;
			}

			public String getSysOrgId() {
				return sysOrgId;
			}

			public void setSysOrgId(String sysOrgId) {
				this.sysOrgId = sysOrgId;
			}

			public String getSkuId() {
				return skuId;
			}

			public void setSkuId(String skuId) {
				this.skuId = skuId;
			}

			public String getCurrentNum() {
				return currentNum;
			}

			public void setCurrentNum(String currentNum) {
				this.currentNum = currentNum;
			}

			public String getSafetyStock() {
				return safetyStock;
			}

			public void setSafetyStock(String safetyStock) {
				this.safetyStock = safetyStock;
			}

			public String getSpecNm() {
				return specNm;
			}

			public void setSpecNm(String specNm) {
				this.specNm = specNm;
			}

			public String getStockNo() {
				return stockNo;
			}

			public void setStockNo(String stockNo) {
				this.stockNo = stockNo;
			}

			public String getWeight() {
				return weight;
			}

			public void setWeight(String weight) {
				this.weight = weight;
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

			public String getMarketPrice() {
				return marketPrice;
			}

			public void setMarketPrice(String marketPrice) {
				this.marketPrice = marketPrice;
			}

			public String getSalePrice() {
				return salePrice;
			}

			public void setSalePrice(String salePrice) {
				this.salePrice = salePrice;
			}

			public String getMaximumOrder() {
				return maximumOrder;
			}

			public void setMaximumOrder(String maximumOrder) {
				this.maximumOrder = maximumOrder;
			}

			public String getUnit() {
				return unit;
			}

			public void setUnit(String unit) {
				this.unit = unit;
			}

			public String getGbSixNineNo() {
				return gbSixNineNo;
			}

			public void setGbSixNineNo(String gbSixNineNo) {
				this.gbSixNineNo = gbSixNineNo;
			}

			public String getMarketingChannel() {
				return marketingChannel;
			}

			public void setMarketingChannel(String marketingChannel) {
				this.marketingChannel = marketingChannel;
			}
		}
	}

	/**
	 * 店铺
	 * 
	 * @author ouyanghao
	 * 
	 */
	public static class shop implements Serializable {

		/**
		 * 店铺ID
		 */
		private Integer shopInfId;
		/**
		 * 发货速度
		 */
		private Double sellerSendOutSpeed;
		/**
		 * 商品描述相似度
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
		 * 联系电话
		 */
		private String csadInf;
		/**
		 * 店铺logo地址
		 */
		private String shopLogo;

		public int getShopInfId() {
			return shopInfId;
		}

		public void setShopInfId(int shopInfId) {
			this.shopInfId = shopInfId;
		}

		public double getSellerSendOutSpeed() {
			return sellerSendOutSpeed;
		}

		public void setSellerSendOutSpeed(double sellerSendOutSpeed) {
			this.sellerSendOutSpeed = sellerSendOutSpeed;
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
	}
}
