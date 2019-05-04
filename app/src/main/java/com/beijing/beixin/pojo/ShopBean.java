package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ShopBean implements Serializable {

	private List<shopproduct> result;
	private String totalCount;
	private String firstResult;
	private String pageSize;
	private String thisPageNumber;
	private String lastPageNumber;
	private String firstPage;
	private String lastPage;
	private String hasNextPage;
	private String hasPreviousPage;
	private String thisPageFirstElementNumber;
	private String thisPageLastElementNumber;
	private String nextPageNumber;
	private String previousPageNumber;
	private List<String> linkPageNumbers;

	public List<shopproduct> getResult() {
		return result;
	}

	public void setResult(List<shopproduct> result) {
		this.result = result;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(String firstResult) {
		this.firstResult = firstResult;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getThisPageNumber() {
		return thisPageNumber;
	}

	public void setThisPageNumber(String thisPageNumber) {
		this.thisPageNumber = thisPageNumber;
	}

	public String getLastPageNumber() {
		return lastPageNumber;
	}

	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}

	public String getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(String firstPage) {
		this.firstPage = firstPage;
	}

	public String getLastPage() {
		return lastPage;
	}

	public void setLastPage(String lastPage) {
		this.lastPage = lastPage;
	}

	public String getHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(String hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public String getHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(String hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public String getThisPageFirstElementNumber() {
		return thisPageFirstElementNumber;
	}

	public void setThisPageFirstElementNumber(String thisPageFirstElementNumber) {
		this.thisPageFirstElementNumber = thisPageFirstElementNumber;
	}

	public String getThisPageLastElementNumber() {
		return thisPageLastElementNumber;
	}

	public void setThisPageLastElementNumber(String thisPageLastElementNumber) {
		this.thisPageLastElementNumber = thisPageLastElementNumber;
	}

	public String getNextPageNumber() {
		return nextPageNumber;
	}

	public void setNextPageNumber(String nextPageNumber) {
		this.nextPageNumber = nextPageNumber;
	}

	public String getPreviousPageNumber() {
		return previousPageNumber;
	}

	public void setPreviousPageNumber(String previousPageNumber) {
		this.previousPageNumber = previousPageNumber;
	}

	public List<String> getLinkPageNumbers() {
		return linkPageNumbers;
	}

	public void setLinkPageNumbers(List<String> linkPageNumbers) {
		this.linkPageNumbers = linkPageNumbers;
	}

	public static class shopproduct implements Serializable {

		private String productId;
		private String unitPrice;
		private String productNm;
		private String sellingPoint;
		private String productImgUrl;
		private List<String> ruleNmSet;
		private List appProductBusinessRule;

		public List getAppProductBusinessRule() {
			return appProductBusinessRule;
		}

		public void setAppProductBusinessRule(List appProductBusinessRule) {
			this.appProductBusinessRule = appProductBusinessRule;
		}

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public String getUnitPrice() {
			return unitPrice;
		}

		public void setUnitPrice(String unitPrice) {
			this.unitPrice = unitPrice;
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

		public String getProductImgUrl() {
			return productImgUrl;
		}

		public void setProductImgUrl(String productImgUrl) {
			this.productImgUrl = productImgUrl;
		}

		public List<String> getRuleNmSet() {
			return ruleNmSet;
		}

		public void setRuleNmSet(List<String> ruleNmSet) {
			this.ruleNmSet = ruleNmSet;
		}
	}
	// /**
	// * 类别名称
	// */
	// private Integer name;
	// /**
	// * 类别子类
	// */
	// private String children;
	// /**
	// * 店铺ID
	// */
	// private Integer shopId;
	// /**
	// * 类别ID
	// */
	// private Integer categoryId;
	//
	// public Integer getName() {
	// return name;
	// }
	//
	// public void setName(Integer name) {
	// this.name = name;
	// }
	//
	// public String getChildren() {
	// return children;
	// }
	//
	// public void setChildren(String children) {
	// this.children = children;
	// }
	//
	// public Integer getShopId() {
	// return shopId;
	// }
	//
	// public void setShopId(Integer shopId) {
	// this.shopId = shopId;
	// }
	//
	// public Integer getCategoryId() {
	// return categoryId;
	// }
	//
	// public void setCategoryId(Integer categoryId) {
	// this.categoryId = categoryId;
	// }
}
