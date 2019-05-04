package com.beijing.beixin.pojo;

import java.util.ArrayList;
import java.util.List;

import com.beijing.beixin.pojo.MoreShopBean.shopInfProxy.sku;

/**
 * Created by imall-mac on 15-6-8.
 */
public class AppProductBaseVo {

	private Integer productId;

	private String image;// 列表商品图片

	private String productNm;

	private String sellingPoint;

	private String isOnSale;// 商品是否在售

	private String isDelete;// 商品是否已删除

	private Double marketPrice;// 市场价

	private Double unitPrice;// 单价

	private AppShopVo shop;// 店铺信息

	private Integer salesVolume;// 销量

	private List<Promotion> promotionList = new ArrayList<Promotion>();// 促销信息
																		// 免邮、赠品、折扣

	private Integer standardProductId; // 标准商品ID

	private List<StandardProductProxy> shopInfProxyList = new ArrayList<StandardProductProxy>();

	private Integer shopInfProxySize;

	private List<sku> skuList;

	public List<sku> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<sku> skuList) {
		this.skuList = skuList;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getProductNm() {
		return productNm;
	}

	public void setProductNm(String productNm) {
		this.productNm = productNm;
	}

	public String getSellingPoint() {
		return sellingPoint;
	}

	public void setSellingPoint(String sellingPoint) {
		this.sellingPoint = sellingPoint;
	}

	public Integer getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public AppShopVo getShop() {
		return shop;
	}

	public void setShop(AppShopVo shop) {
		this.shop = shop;
	}

	public List<Promotion> getPromotionList() {
		return promotionList;
	}

	public void setPromotionList(List<Promotion> promotionList) {
		this.promotionList = promotionList;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getIsOnSale() {
		return isOnSale;
	}

	public void setIsOnSale(String isOnSale) {
		this.isOnSale = isOnSale;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public static class Promotion {
		public static final String ZP = "zp";// 赠品
		public static final String ZK = "zk";// 折扣
		public static final String MY = "my";// 免邮
		public static final String BLUE = "#68c5f0";
		public static final String ORANGE = "#e9a438";
		public static final String GREEN = "#23dc9d";

		private String type;
		private String name;
		private String color;

		public static Promotion genZP() {
			Promotion p = new Promotion();
			p.setName("赠品");
			p.setType(ZP);
			p.setColor(GREEN);
			return p;
		}

		public static Promotion genZK() {
			Promotion p = new Promotion();
			p.setName("折扣");
			p.setType(ZK);
			p.setColor(BLUE);
			return p;
		}

		public static Promotion genMY() {
			Promotion p = new Promotion();
			p.setName("免邮");
			p.setType(MY);
			p.setColor(ORANGE);
			return p;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}
	}

	public Integer getStandardProductId() {
		return standardProductId;
	}

	public void setStandardProductId(Integer standardProductId) {
		this.standardProductId = standardProductId;
	}

	public List<StandardProductProxy> getShopInfProxyList() {
		return shopInfProxyList;
	}

	public void setShopInfProxyList(List<StandardProductProxy> shopInfProxyList) {
		this.shopInfProxyList = shopInfProxyList;
	}

	public Integer getShopInfProxySize() {
		return shopInfProxySize;
	}

	public void setShopInfProxySize(Integer shopInfProxySize) {
		this.shopInfProxySize = shopInfProxySize;
	}

}
