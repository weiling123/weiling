package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 新品上架/精品热销
 * 
 * @author ouyanghao
 * 
 */
public class NewProductBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String description;
	private String link;
	// private List<String> icon;
	private String title;
	private String newWin;
	private List<product> products;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	// public List<String> getIcon() {
	// return icon;
	// }
	//
	// public void setIcon(List<String> icon) {
	// this.icon = icon;
	// }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNewWin() {
		return newWin;
	}

	public void setNewWin(String newWin) {
		this.newWin = newWin;
	}

	public List<product> getProducts() {
		return products;
	}

	public void setProducts(List<product> products) {
		this.products = products;
	}

	public static class product implements Serializable {

		private String name;
		private String productId;
		private price price;
		private String marketPrice;
		private String categoryId;
		private String salePoint;
		private String imageLinks;
		private String skuId;
		private String shopNm;
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

		private String shopInfId;

		public String getShopInfId() {
			return shopInfId;
		}

		public void setShopInfId(String shopInfId) {
			this.shopInfId = shopInfId;
		}

		public String getShopNm() {
			return shopNm;
		}

		public void setShopNm(String shopNm) {
			this.shopNm = shopNm;
		}

		public String getSkuId() {
			return skuId;
		}

		public void setSkuId(String skuId) {
			this.skuId = skuId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public price getPrice() {
			return price;
		}

		public void setPrice(price price) {
			this.price = price;
		}

		public String getMarketPrice() {
			return marketPrice;
		}

		public void setMarketPrice(String marketPrice) {
			this.marketPrice = marketPrice;
		}

		public String getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(String categoryId) {
			this.categoryId = categoryId;
		}

		public String getSalePoint() {
			return salePoint;
		}

		public void setSalePoint(String salePoint) {
			this.salePoint = salePoint;
		}

		public String getImageLinks() {
			return imageLinks;
		}

		public void setImageLinks(String imageLinks) {
			this.imageLinks = imageLinks;
		}

		public static class price implements Serializable {

			private String startTime;
			private String endTime;
			private String currency;
			private String businessRuleId;
			private String num;
			private String couponId;
			private String obtainIntegral;
			private String unitPrice;
			private String totalAmount;
			private String isSpecialPrice;
			private String discountType;
			private String skuDiscountRuleId;
			private String amountTypeCode;
			private String amountNm;
			private String isNeedPayment;
			private String remainStock;
			private String originalUnitPrice;
			private String needPayment;
			private String installmentFee;
			private String ruleType;
			private String endTimeStr;
			private String limitBuy;
			private String originalStock;

			public String getStartTime() {
				return startTime;
			}

			public void setStartTime(String startTime) {
				this.startTime = startTime;
			}

			public String getEndTime() {
				return endTime;
			}

			public void setEndTime(String endTime) {
				this.endTime = endTime;
			}

			public String getCurrency() {
				return currency;
			}

			public void setCurrency(String currency) {
				this.currency = currency;
			}

			public String getBusinessRuleId() {
				return businessRuleId;
			}

			public void setBusinessRuleId(String businessRuleId) {
				this.businessRuleId = businessRuleId;
			}

			public String getNum() {
				return num;
			}

			public void setNum(String num) {
				this.num = num;
			}

			public String getCouponId() {
				return couponId;
			}

			public void setCouponId(String couponId) {
				this.couponId = couponId;
			}

			public String getObtainIntegral() {
				return obtainIntegral;
			}

			public void setObtainIntegral(String obtainIntegral) {
				this.obtainIntegral = obtainIntegral;
			}

			public String getUnitPrice() {
				return unitPrice;
			}

			public void setUnitPrice(String unitPrice) {
				this.unitPrice = unitPrice;
			}

			public String getTotalAmount() {
				return totalAmount;
			}

			public void setTotalAmount(String totalAmount) {
				this.totalAmount = totalAmount;
			}

			public String getIsSpecialPrice() {
				return isSpecialPrice;
			}

			public void setIsSpecialPrice(String isSpecialPrice) {
				this.isSpecialPrice = isSpecialPrice;
			}

			public String getDiscountType() {
				return discountType;
			}

			public void setDiscountType(String discountType) {
				this.discountType = discountType;
			}

			public String getSkuDiscountRuleId() {
				return skuDiscountRuleId;
			}

			public void setSkuDiscountRuleId(String skuDiscountRuleId) {
				this.skuDiscountRuleId = skuDiscountRuleId;
			}

			public String getAmountTypeCode() {
				return amountTypeCode;
			}

			public void setAmountTypeCode(String amountTypeCode) {
				this.amountTypeCode = amountTypeCode;
			}

			public String getAmountNm() {
				return amountNm;
			}

			public void setAmountNm(String amountNm) {
				this.amountNm = amountNm;
			}

			public String getIsNeedPayment() {
				return isNeedPayment;
			}

			public void setIsNeedPayment(String isNeedPayment) {
				this.isNeedPayment = isNeedPayment;
			}

			public String getRemainStock() {
				return remainStock;
			}

			public void setRemainStock(String remainStock) {
				this.remainStock = remainStock;
			}

			public String getOriginalUnitPrice() {
				return originalUnitPrice;
			}

			public void setOriginalUnitPrice(String originalUnitPrice) {
				this.originalUnitPrice = originalUnitPrice;
			}

			public String getNeedPayment() {
				return needPayment;
			}

			public void setNeedPayment(String needPayment) {
				this.needPayment = needPayment;
			}

			public String getInstallmentFee() {
				return installmentFee;
			}

			public void setInstallmentFee(String installmentFee) {
				this.installmentFee = installmentFee;
			}

			public String getRuleType() {
				return ruleType;
			}

			public void setRuleType(String ruleType) {
				this.ruleType = ruleType;
			}

			public String getEndTimeStr() {
				return endTimeStr;
			}

			public void setEndTimeStr(String endTimeStr) {
				this.endTimeStr = endTimeStr;
			}

			public String getLimitBuy() {
				return limitBuy;
			}

			public void setLimitBuy(String limitBuy) {
				this.limitBuy = limitBuy;
			}

			public String getOriginalStock() {
				return originalStock;
			}

			public void setOriginalStock(String originalStock) {
				this.originalStock = originalStock;
			}
		}
	}
}
