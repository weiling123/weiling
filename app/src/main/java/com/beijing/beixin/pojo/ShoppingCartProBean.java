package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

public class ShoppingCartProBean implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private String name;// 商品名称
	private String specName;// 规格名称
	private Integer productId;// 商品ID
	private Boolean itemSelected;// 商品是否选择
	private Integer quantity;// 商品数量
	private Integer skuId;// Sku ID
	private Double productTotalAmount;// 商品总金额
	private String Image;// 商品图片链接
	private String itemKey;// Cart Item 唯一标识
	private String isOnSale;// 是否在售
	private Double productUnitPrice;// 商品单价
	private Boolean isGroupBuy;// 是否是团购
	private Integer stock;// 库存
	private Boolean isPresent;// 是否是赠品
	private List<ShoppingCartProBean> presents;// 是否包含赠品

	public String getIsOnSale() {
		return isOnSale;
	}

	public void setIsOnSale(String isOnSale) {
		this.isOnSale = isOnSale;
	}

	public List<ShoppingCartProBean> getPresents() {
		return presents;
	}

	public void setPresents(List<ShoppingCartProBean> presents) {
		this.presents = presents;
	}

	public Boolean getIsPresent() {
		return isPresent;
	}

	public void setIsPresent(Boolean isPresent) {
		this.isPresent = isPresent;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

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

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Boolean getItemSelected() {
		return itemSelected;
	}

	public void setItemSelected(Boolean itemSelected) {
		this.itemSelected = itemSelected;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public Double getProductTotalAmount() {
		return productTotalAmount;
	}

	public void setProductTotalAmount(Double productTotalAmount) {
		this.productTotalAmount = productTotalAmount;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public String getItemKey() {
		return itemKey;
	}

	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}

	public Double getProductUnitPrice() {
		return productUnitPrice;
	}

	public void setProductUnitPrice(Double productUnitPrice) {
		this.productUnitPrice = productUnitPrice;
	}

	public Boolean getIsGroupBuy() {
		return isGroupBuy;
	}

	public void setIsGroupBuy(Boolean isGroupBuy) {
		this.isGroupBuy = isGroupBuy;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
