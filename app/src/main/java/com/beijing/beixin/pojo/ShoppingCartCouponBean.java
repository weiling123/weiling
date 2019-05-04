package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

public class ShoppingCartCouponBean implements Serializable {

	private List<address> receiverAddr;
	private String allCartNum;
	private String allDiscount;
	private String allObtainTotalIntegral;
	private String allOrderTotalAmount;
	private String allProductTotalAmount;
	private String selectCartNum;
	private String freightTotalAmount;
	private String isSelectAll;
	private List<shop> shoppingCartVos;
	private List<discount> discountList;

	public List<address> getReceiverAddr() {
		return receiverAddr;
	}

	public void setReceiverAddr(List<address> receiverAddr) {
		this.receiverAddr = receiverAddr;
	}

	public String getAllCartNum() {
		return allCartNum;
	}

	public void setAllCartNum(String allCartNum) {
		this.allCartNum = allCartNum;
	}

	public String getAllDiscount() {
		return allDiscount;
	}

	public void setAllDiscount(String allDiscount) {
		this.allDiscount = allDiscount;
	}

	public String getAllObtainTotalIntegral() {
		return allObtainTotalIntegral;
	}

	public void setAllObtainTotalIntegral(String allObtainTotalIntegral) {
		this.allObtainTotalIntegral = allObtainTotalIntegral;
	}

	public String getAllOrderTotalAmount() {
		return allOrderTotalAmount;
	}

	public void setAllOrderTotalAmount(String allOrderTotalAmount) {
		this.allOrderTotalAmount = allOrderTotalAmount;
	}

	public String getAllProductTotalAmount() {
		return allProductTotalAmount;
	}

	public void setAllProductTotalAmount(String allProductTotalAmount) {
		this.allProductTotalAmount = allProductTotalAmount;
	}

	public String getSelectCartNum() {
		return selectCartNum;
	}

	public void setSelectCartNum(String selectCartNum) {
		this.selectCartNum = selectCartNum;
	}

	public String getFreightTotalAmount() {
		return freightTotalAmount;
	}

	public void setFreightTotalAmount(String freightTotalAmount) {
		this.freightTotalAmount = freightTotalAmount;
	}

	public String getIsSelectAll() {
		return isSelectAll;
	}

	public void setIsSelectAll(String isSelectAll) {
		this.isSelectAll = isSelectAll;
	}

	public List<shop> getShoppingCartVos() {
		return shoppingCartVos;
	}

	public void setShoppingCartVos(List<shop> shoppingCartVos) {
		this.shoppingCartVos = shoppingCartVos;
	}

	public List<discount> getDiscountList() {
		return discountList;
	}

	public void setDiscountList(List<discount> discountList) {
		this.discountList = discountList;
	}

	public static class discount implements Serializable, Cloneable {

		private String businessRuleId;
		private String ruleName;
		private String discount;

		public String getBusinessRuleId() {
			return businessRuleId;
		}

		public void setBusinessRuleId(String businessRuleId) {
			this.businessRuleId = businessRuleId;
		}

		public String getRuleName() {
			return ruleName;
		}

		public void setRuleName(String ruleName) {
			this.ruleName = ruleName;
		}

		public String getDiscount() {
			return discount;
		}

		public void setDiscount(String discount) {
			this.discount = discount;
		}
	}

	public static class address implements Serializable {

		private String zoneNm;
		private String addressPath;
		private String name;
		private String sysUserId;
		private int receiveAddrId;
		private String zoneId;
		private String addr;
		private String zipcode;
		private String mobile;
		private String tel;
		private String isDefault;

		public String getZoneNm() {
			return zoneNm;
		}

		public void setZoneNm(String zoneNm) {
			this.zoneNm = zoneNm;
		}

		public String getAddressPath() {
			return addressPath;
		}

		public void setAddressPath(String addressPath) {
			this.addressPath = addressPath;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSysUserId() {
			return sysUserId;
		}

		public void setSysUserId(String sysUserId) {
			this.sysUserId = sysUserId;
		}

		public int getReceiveAddrId() {
			return receiveAddrId;
		}

		public void setReceiveAddrId(int receiveAddrId) {
			this.receiveAddrId = receiveAddrId;
		}

		public String getZoneId() {
			return zoneId;
		}

		public void setZoneId(String zoneId) {
			this.zoneId = zoneId;
		}

		public String getAddr() {
			return addr;
		}

		public void setAddr(String addr) {
			this.addr = addr;
		}

		public String getZipcode() {
			return zipcode;
		}

		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
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

		public String getIsDefault() {
			return isDefault;
		}

		public void setIsDefault(String isDefault) {
			this.isDefault = isDefault;
		}
	}

	public static class shop implements Serializable {

		private String shopInfId;
		private int orderTotalAmount;
		private String orgId;
		private List<item> items;
		private String productTotalAmount;
		private String shopNm;
		private String freightAmount;
		private String obtainTotalIntegral;
		private String isSelectAll;
		private String useTotalIntegral;
		private String useUnitIntegral;
		private String presentQuantity;
		private String totalDiscountAmount;
		private String selectedCartItemNum;

		public int getOrderTotalAmount() {
			return orderTotalAmount;
		}

		public void setOrderTotalAmount(int orderTotalAmount) {
			this.orderTotalAmount = orderTotalAmount;
		}

		public String getShopInfId() {
			return shopInfId;
		}

		public void setShopInfId(String shopInfId) {
			this.shopInfId = shopInfId;
		}

		public String getOrgId() {
			return orgId;
		}

		public void setOrgId(String orgId) {
			this.orgId = orgId;
		}

		public List<item> getItems() {
			return items;
		}

		public void setItems(List<item> items) {
			this.items = items;
		}

		public String getProductTotalAmount() {
			return productTotalAmount;
		}

		public void setProductTotalAmount(String productTotalAmount) {
			this.productTotalAmount = productTotalAmount;
		}

		public String getShopNm() {
			return shopNm;
		}

		public void setShopNm(String shopNm) {
			this.shopNm = shopNm;
		}

		public String getFreightAmount() {
			return freightAmount;
		}

		public void setFreightAmount(String freightAmount) {
			this.freightAmount = freightAmount;
		}

		public String getObtainTotalIntegral() {
			return obtainTotalIntegral;
		}

		public void setObtainTotalIntegral(String obtainTotalIntegral) {
			this.obtainTotalIntegral = obtainTotalIntegral;
		}

		public String getIsSelectAll() {
			return isSelectAll;
		}

		public void setIsSelectAll(String isSelectAll) {
			this.isSelectAll = isSelectAll;
		}

		public String getUseTotalIntegral() {
			return useTotalIntegral;
		}

		public void setUseTotalIntegral(String useTotalIntegral) {
			this.useTotalIntegral = useTotalIntegral;
		}

		public String getUseUnitIntegral() {
			return useUnitIntegral;
		}

		public void setUseUnitIntegral(String useUnitIntegral) {
			this.useUnitIntegral = useUnitIntegral;
		}

		public String getPresentQuantity() {
			return presentQuantity;
		}

		public void setPresentQuantity(String presentQuantity) {
			this.presentQuantity = presentQuantity;
		}

		public String getTotalDiscountAmount() {
			return totalDiscountAmount;
		}

		public void setTotalDiscountAmount(String totalDiscountAmount) {
			this.totalDiscountAmount = totalDiscountAmount;
		}

		public String getSelectedCartItemNum() {
			return selectedCartItemNum;
		}

		public void setSelectedCartItemNum(String selectedCartItemNum) {
			this.selectedCartItemNum = selectedCartItemNum;
		}
	}

	public static class item implements Serializable {

		private String name;
		private String specName;
		private String productId;
		private String itemKey;
		private String skuId;
		private String quantity;
		private String productTotalAmount;
		private String promotionType;
		private String marketPrice;
		private String obtainIntegral;
		private String itemSelected;
		private String supportDelivery;
		private String isGroupBuy;
		private String productUnitPrice;
		private String image;
		private String totalCostAmount;
		private List<String> combos;
		private String isPresent;
		private String useTotalIntegral;
		private String useUnitIntegral;
		private String unitCostAmount;
		private String isRedemption;
		private String isIntegral;
		private List<String> presents;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSpecName() {
			return specName;
		}

		public void setSpecName(String specName) {
			this.specName = specName;
		}

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public String getItemKey() {
			return itemKey;
		}

		public void setItemKey(String itemKey) {
			this.itemKey = itemKey;
		}

		public String getSkuId() {
			return skuId;
		}

		public void setSkuId(String skuId) {
			this.skuId = skuId;
		}

		public String getQuantity() {
			return quantity;
		}

		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}

		public String getProductTotalAmount() {
			return productTotalAmount;
		}

		public void setProductTotalAmount(String productTotalAmount) {
			this.productTotalAmount = productTotalAmount;
		}

		public String getPromotionType() {
			return promotionType;
		}

		public void setPromotionType(String promotionType) {
			this.promotionType = promotionType;
		}

		public String getMarketPrice() {
			return marketPrice;
		}

		public void setMarketPrice(String marketPrice) {
			this.marketPrice = marketPrice;
		}

		public String getObtainIntegral() {
			return obtainIntegral;
		}

		public void setObtainIntegral(String obtainIntegral) {
			this.obtainIntegral = obtainIntegral;
		}

		public String getItemSelected() {
			return itemSelected;
		}

		public void setItemSelected(String itemSelected) {
			this.itemSelected = itemSelected;
		}

		public String getSupportDelivery() {
			return supportDelivery;
		}

		public void setSupportDelivery(String supportDelivery) {
			this.supportDelivery = supportDelivery;
		}

		public String getIsGroupBuy() {
			return isGroupBuy;
		}

		public void setIsGroupBuy(String isGroupBuy) {
			this.isGroupBuy = isGroupBuy;
		}

		public String getProductUnitPrice() {
			return productUnitPrice;
		}

		public void setProductUnitPrice(String productUnitPrice) {
			this.productUnitPrice = productUnitPrice;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public String getTotalCostAmount() {
			return totalCostAmount;
		}

		public void setTotalCostAmount(String totalCostAmount) {
			this.totalCostAmount = totalCostAmount;
		}

		public List<String> getCombos() {
			return combos;
		}

		public void setCombos(List<String> combos) {
			this.combos = combos;
		}

		public String getIsPresent() {
			return isPresent;
		}

		public void setIsPresent(String isPresent) {
			this.isPresent = isPresent;
		}

		public String getUseTotalIntegral() {
			return useTotalIntegral;
		}

		public void setUseTotalIntegral(String useTotalIntegral) {
			this.useTotalIntegral = useTotalIntegral;
		}

		public String getUseUnitIntegral() {
			return useUnitIntegral;
		}

		public void setUseUnitIntegral(String useUnitIntegral) {
			this.useUnitIntegral = useUnitIntegral;
		}

		public String getUnitCostAmount() {
			return unitCostAmount;
		}

		public void setUnitCostAmount(String unitCostAmount) {
			this.unitCostAmount = unitCostAmount;
		}

		public String getIsRedemption() {
			return isRedemption;
		}

		public void setIsRedemption(String isRedemption) {
			this.isRedemption = isRedemption;
		}

		public String getIsIntegral() {
			return isIntegral;
		}

		public void setIsIntegral(String isIntegral) {
			this.isIntegral = isIntegral;
		}

		public List<String> getPresents() {
			return presents;
		}

		public void setPresents(List<String> presents) {
			this.presents = presents;
		}

		public Object clone() throws CloneNotSupportedException {
			return super.clone();
		}
	}
}
