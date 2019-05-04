package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

import android.R.integer;

/**
 * 订单列表
 * 
 * @author ouyanghao
 * 
 */
@SuppressWarnings("serial")
public class OrderListBean implements Serializable {

	private String mobile;
	private Integer shopId;
	private String orderNum;
	private Double orderTotalAmount;
	private Integer orderId;
	private String orderStat;
	private String isCod;
	private String isPayed;
	private String shopNm;
	private String productTotalCount;
	private List<AppOrderItemVo> orderItemList;

	public List<AppOrderItemVo> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<AppOrderItemVo> orderItemList) {
		this.orderItemList = orderItemList;
	}

	private boolean isReturnable;
	private String shopLogo;
	private String orderCreateTime;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Double getOrderTotalAmount() {
		return orderTotalAmount;
	}

	public void setOrderTotalAmount(Double orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderStat() {
		return orderStat;
	}

	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}

	public String getIsCod() {
		return isCod;
	}

	public void setIsCod(String isCod) {
		this.isCod = isCod;
	}

	public String getIsPayed() {
		return isPayed;
	}

	public void setIsPayed(String isPayed) {
		this.isPayed = isPayed;
	}

	public String getShopNm() {
		return shopNm;
	}

	public void setShopNm(String shopNm) {
		this.shopNm = shopNm;
	}

	public String getProductTotalCount() {
		return productTotalCount;
	}

	public void setProductTotalCount(String productTotalCount) {
		this.productTotalCount = productTotalCount;
	}

	public boolean isReturnable() {
		return isReturnable;
	}

	public void setReturnable(boolean isReturnable) {
		this.isReturnable = isReturnable;
	}

	public String getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}

	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public static class AppOrderItemVo implements Serializable {

		private Integer productId;
		private Integer quantity;
		private String productNm;
		private Double unitPrice;
		private Integer orderItemId;
		private String exchangeReturnedPurchaseNum;
		private String isExchangeReturnedPurchase;
		private String image;
		private Integer skuId;
		private String spec;
		private String commentCont;
		private int gradeLevel;
		private String isOnSale;
		private String isPresent;

		public String getIsPresent() {
			return isPresent;
		}

		public void setIsPresent(String isPresent) {
			this.isPresent = isPresent;
		}

		public String getIsOnSale() {
			return isOnSale;
		}

		public void setIsOnSale(String isOnSale) {
			this.isOnSale = isOnSale;
		}

		public String getCommentCont() {
			return commentCont;
		}

		public void setCommentCont(String commentCont) {
			this.commentCont = commentCont;
		}

		public int getGradeLevel() {
			return gradeLevel;
		}

		public void setGradeLevel(int gradeLevel) {
			this.gradeLevel = gradeLevel;
		}

		public Integer getProductId() {
			return productId;
		}

		public void setProductId(Integer productId) {
			this.productId = productId;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public String getProductNm() {
			return productNm;
		}

		public void setProductNm(String productNm) {
			this.productNm = productNm;
		}

		public Double getUnitPrice() {
			return unitPrice;
		}

		public void setUnitPrice(Double unitPrice) {
			this.unitPrice = unitPrice;
		}

		public Integer getOrderItemId() {
			return orderItemId;
		}

		public void setOrderItemId(Integer orderItemId) {
			this.orderItemId = orderItemId;
		}

		public String getExchangeReturnedPurchaseNum() {
			return exchangeReturnedPurchaseNum;
		}

		public void setExchangeReturnedPurchaseNum(String exchangeReturnedPurchaseNum) {
			this.exchangeReturnedPurchaseNum = exchangeReturnedPurchaseNum;
		}

		public String getIsExchangeReturnedPurchase() {
			return isExchangeReturnedPurchase;
		}

		public void setIsExchangeReturnedPurchase(String isExchangeReturnedPurchase) {
			this.isExchangeReturnedPurchase = isExchangeReturnedPurchase;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public Integer getSkuId() {
			return skuId;
		}

		public void setSkuId(Integer skuId) {
			this.skuId = skuId;
		}

		public String getSpec() {
			return spec;
		}

		public void setSpec(String spec) {
			this.spec = spec;
		}
	}
}
