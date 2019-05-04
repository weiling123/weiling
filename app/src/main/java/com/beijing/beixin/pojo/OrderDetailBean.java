package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

import com.beijing.beixin.pojo.OrderListBean.AppOrderItemVo;

/**
 * 订单详情
 * 
 * @author ouyanghao
 * 
 */
@SuppressWarnings("serial")
public class OrderDetailBean implements Serializable {

	private String receiverMobile;
	private String receiverAddr;
	private String deliveryWay;
	private String payWay;
	private String logisticsTip;
	private String logisticsTime;
	private String receiverNm;
	private String couponUse;
	private String preStoreUse;
	private String mobile;
	private Integer shopId;
	private String orderNum;
	private Double orderTotalAmount;
	private Integer orderId;
	private String orderStat;
	private String isCod;
	private String isPayed;
	private String shopNm;
	private Integer productTotalCount;
	private List<AppOrderItemVo> orderItemList;
	private boolean isReturnable;
	private String shopLogo;
	private String orderCreateTime;
	private String freightAmount;
	private String productTotalAmount;
	private String obtainTotalIntegral;
	private String remark;
	private String invoiceTitle;
	private Boolean needInvoice;
	private String disTotalAmount;

	public String getDisTotalAmount() {
		return disTotalAmount;
	}

	public void setDisTotalAmount(String disTotalAmount) {
		this.disTotalAmount = disTotalAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public Boolean getNeedInvoice() {
		return needInvoice;
	}

	public void setNeedInvoice(Boolean needInvoice) {
		this.needInvoice = needInvoice;
	}

	public String getFreightAmount() {
		return freightAmount;
	}

	public void setFreightAmount(String freightAmount) {
		this.freightAmount = freightAmount;
	}

	public String getProductTotalAmount() {
		return productTotalAmount;
	}

	public void setProductTotalAmount(String productTotalAmount) {
		this.productTotalAmount = productTotalAmount;
	}

	public String getObtainTotalIntegral() {
		return obtainTotalIntegral;
	}

	public void setObtainTotalIntegral(String obtainTotalIntegral) {
		this.obtainTotalIntegral = obtainTotalIntegral;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getReceiverAddr() {
		return receiverAddr;
	}

	public void setReceiverAddr(String receiverAddr) {
		this.receiverAddr = receiverAddr;
	}

	public String getDeliveryWay() {
		return deliveryWay;
	}

	public void setDeliveryWay(String deliveryWay) {
		this.deliveryWay = deliveryWay;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getLogisticsTip() {
		return logisticsTip;
	}

	public void setLogisticsTip(String logisticsTip) {
		this.logisticsTip = logisticsTip;
	}

	public String getLogisticsTime() {
		return logisticsTime;
	}

	public void setLogisticsTime(String logisticsTime) {
		this.logisticsTime = logisticsTime;
	}

	public String getReceiverNm() {
		return receiverNm;
	}

	public void setReceiverNm(String receiverNm) {
		this.receiverNm = receiverNm;
	}

	public String getCouponUse() {
		return couponUse;
	}

	public void setCouponUse(String couponUse) {
		this.couponUse = couponUse;
	}

	public String getPreStoreUse() {
		return preStoreUse;
	}

	public void setPreStoreUse(String preStoreUse) {
		this.preStoreUse = preStoreUse;
	}

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

	public Integer getProductTotalCount() {
		return productTotalCount;
	}

	public void setProductTotalCount(Integer productTotalCount) {
		this.productTotalCount = productTotalCount;
	}

	public List<AppOrderItemVo> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<AppOrderItemVo> orderItemList) {
		this.orderItemList = orderItemList;
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
}
