package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.beijing.beixin.pojo.ShoppingCartCouponBean.discount;

public class ShoppingCartBean implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private Integer allCartNum;// 购物车项目数量
	private Integer selectCartNum;// 选择的项目数量
	private Double allProductTotalAmount;// 商品总金额
	private Double allOrderTotalAmount;// 订单总金额
	private Integer allObtainTotalIntegral;// 订单获得积分
	private Double allDiscount;// 订单优惠金额
	private Double freightTotalAmount;// 订单运费
	private Boolean isSelectAll;// 是否全选
	private List<ShoppingCartShopBean> shoppingCartVos;// 店铺Cart对象列表
	private AddressBean receiverAddr;
	private List<discount> discountList;

	public List<discount> getDiscountList() {
		return discountList;
	}

	public void setDiscountList(List<discount> discountList) {
		this.discountList = discountList;
	}

	public Integer getAllCartNum() {
		return allCartNum;
	}

	public void setAllCartNum(Integer allCartNum) {
		this.allCartNum = allCartNum;
	}

	public Double getAllProductTotalAmount() {
		return allProductTotalAmount;
	}

	public void setAllProductTotalAmount(Double allProductTotalAmount) {
		this.allProductTotalAmount = allProductTotalAmount;
	}

	public Double getAllOrderTotalAmount() {
		return allOrderTotalAmount;
	}

	public void setAllOrderTotalAmount(Double allOrderTotalAmount) {
		this.allOrderTotalAmount = allOrderTotalAmount;
	}

	public Integer getAllObtainTotalIntegral() {
		return allObtainTotalIntegral;
	}

	public void setAllObtainTotalIntegral(Integer allObtainTotalIntegral) {
		this.allObtainTotalIntegral = allObtainTotalIntegral;
	}

	public Double getAllDiscount() {
		return allDiscount;
	}

	public void setAllDiscount(Double allDiscount) {
		this.allDiscount = allDiscount;
	}

	public Double getFreightTotalAmount() {
		return freightTotalAmount;
	}

	public void setFreightTotalAmount(Double freightTotalAmount) {
		this.freightTotalAmount = freightTotalAmount;
	}

	public Boolean getIsSelectAll() {
		return isSelectAll;
	}

	public void setIsSelectAll(Boolean isSelectAll) {
		this.isSelectAll = isSelectAll;
	}

	public List<ShoppingCartShopBean> getShoppingCartVos() {
		return shoppingCartVos;
	}

	public void setShoppingCartVos(List<ShoppingCartShopBean> shoppingCartVos) {
		this.shoppingCartVos = shoppingCartVos;
	}

	public Integer getSelectCartNum() {
		return selectCartNum;
	}

	public void setSelectCartNum(Integer selectCartNum) {
		this.selectCartNum = selectCartNum;
	}

	public AddressBean getReceiverAddr() {
		return receiverAddr;
	}

	public void setReceiverAddr(AddressBean receiverAddr) {
		this.receiverAddr = receiverAddr;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		ShoppingCartBean shoppingCartBean = (ShoppingCartBean) super.clone();
		if (shoppingCartVos != null) {
			shoppingCartBean.shoppingCartVos = new ArrayList();
			for (int i = 0; i < shoppingCartVos.size(); i++) {
				shoppingCartBean.shoppingCartVos.add((ShoppingCartShopBean) shoppingCartVos.get(i).clone());
			}
		}

		return shoppingCartBean;
	}

}
