package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartShopBean implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private Integer shopInfId;// 店铺ID
	private Double orderTotalAmount;// 订单总金额
	private Double productTotalAmount;// 商品总金额
	private Integer allObtainTotalIntegral;// 订单获得积分
	private String shopNm;// 店铺名称
	private Integer orgId;// 店铺机构ID
	private Double freightAmount;// 运费
	private Boolean isSelectAll;// 是否全选
	private int selectedCartItemNum;
	private String totalDiscountAmount;
	private List<ShoppingCartProBean> items;// 购物项对象

	private String remark;

	private String payWay = "请选择";

	private String sendWay = "请选择";

	private String couPon;

	private String couponId = "";

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getSendWay() {
		return sendWay;
	}

	public void setSendWay(String sendWay) {
		this.sendWay = sendWay;
	}

	public String getCouPon() {
		return couPon;
	}

	public void setCouPon(String couPon) {
		this.couPon = couPon;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public void setTotalDiscountAmount(String totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

	public int getSelectedCartItemNum() {
		return selectedCartItemNum;
	}

	public void setSelectedCartItemNum(int selectedCartItemNum) {
		this.selectedCartItemNum = selectedCartItemNum;
	}

	public Integer getShopInfId() {
		return shopInfId;
	}

	public void setShopInfId(Integer shopInfId) {
		this.shopInfId = shopInfId;
	}

	public Double getOrderTotalAmount() {
		return orderTotalAmount;
	}

	public void setOrderTotalAmount(Double orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}

	public Double getProductTotalAmount() {
		return productTotalAmount;
	}

	public void setProductTotalAmount(Double productTotalAmount) {
		this.productTotalAmount = productTotalAmount;
	}

	public Integer getAllObtainTotalIntegral() {
		return allObtainTotalIntegral;
	}

	public void setAllObtainTotalIntegral(Integer allObtainTotalIntegral) {
		this.allObtainTotalIntegral = allObtainTotalIntegral;
	}

	public String getShopNm() {
		return shopNm;
	}

	public void setShopNm(String shopNm) {
		this.shopNm = shopNm;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Double getFreightAmount() {
		return freightAmount;
	}

	public void setFreightAmount(Double freightAmount) {
		this.freightAmount = freightAmount;
	}

	public Boolean getIsSelectAll() {
		return isSelectAll;
	}

	public void setIsSelectAll(Boolean isSelectAll) {
		this.isSelectAll = isSelectAll;
	}

	public List<ShoppingCartProBean> getItems() {
		return items;
	}

	public void setItems(List<ShoppingCartProBean> items) {
		this.items = items;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {

		ShoppingCartShopBean ShopBean = (ShoppingCartShopBean) super.clone();
		if (items != null) {
			ShopBean.items = new ArrayList();
			for (int i = 0; i < items.size(); i++) {
				ShopBean.items.add((ShoppingCartProBean) items.get(i).clone());
			}
		}

		return ShopBean;

	}
}
