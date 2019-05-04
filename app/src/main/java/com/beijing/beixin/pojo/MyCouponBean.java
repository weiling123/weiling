package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 我的优惠券
 * 
 * @author ouyanghao
 * 
 */
@SuppressWarnings("serial")
public class MyCouponBean implements Serializable {

	private List<coupon> result;
	private String totalCount;
	private String firstResult;
	private String lastPageNumber;
	private String thisPageNumber;
	private String pageSize;
	private String firstPage;
	private String lastPage;
	private String hasNextPage;
	private String hasPreviousPage;
	private String thisPageFirstElementNumber;
	private String thisPageLastElementNumber;
	private String nextPageNumber;
	private String previousPageNumber;
	private List<String> linkPageNumbers;

	public List<coupon> getResult() {
		return result;
	}

	public void setResult(List<coupon> result) {
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

	public String getLastPageNumber() {
		return lastPageNumber;
	}

	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}

	public String getThisPageNumber() {
		return thisPageNumber;
	}

	public void setThisPageNumber(String thisPageNumber) {
		this.thisPageNumber = thisPageNumber;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
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

	public static class coupon implements Serializable {

		private String reason;
		private String couponBatchId;
		private String couponPsw;
		private String isBind;
		private String isUse;
		private String amount;
		private String isFreeze;
		private String couponId;
		private String batchNm;
		private String createDate;
		private String bindUserId;
		private String startTimeString;
		private String endTimeString;
		private String couponNum;
		private String useTime;
		private String bindTime;
		private String couponStat;
		private String dateStr;
		private Boolean isSelected;

		public Boolean getIsSelected() {
			return isSelected;
		}

		public void setIsSelected(Boolean isSelected) {
			this.isSelected = isSelected;
		}

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}

		public String getCouponBatchId() {
			return couponBatchId;
		}

		public void setCouponBatchId(String couponBatchId) {
			this.couponBatchId = couponBatchId;
		}

		public String getCouponPsw() {
			return couponPsw;
		}

		public void setCouponPsw(String couponPsw) {
			this.couponPsw = couponPsw;
		}

		public String getIsBind() {
			return isBind;
		}

		public void setIsBind(String isBind) {
			this.isBind = isBind;
		}

		public String getIsUse() {
			return isUse;
		}

		public void setIsUse(String isUse) {
			this.isUse = isUse;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getIsFreeze() {
			return isFreeze;
		}

		public void setIsFreeze(String isFreeze) {
			this.isFreeze = isFreeze;
		}

		public String getCouponId() {
			return couponId;
		}

		public void setCouponId(String couponId) {
			this.couponId = couponId;
		}

		public String getBatchNm() {
			return batchNm;
		}

		public void setBatchNm(String batchNm) {
			this.batchNm = batchNm;
		}

		public String getCreateDate() {
			return createDate;
		}

		public void setCreateDate(String createDate) {
			this.createDate = createDate;
		}

		public String getBindUserId() {
			return bindUserId;
		}

		public void setBindUserId(String bindUserId) {
			this.bindUserId = bindUserId;
		}

		public String getStartTimeString() {
			return startTimeString;
		}

		public void setStartTimeString(String startTimeString) {
			this.startTimeString = startTimeString;
		}

		public String getEndTimeString() {
			return endTimeString;
		}

		public void setEndTimeString(String endTimeString) {
			this.endTimeString = endTimeString;
		}

		public String getCouponNum() {
			return couponNum;
		}

		public void setCouponNum(String couponNum) {
			this.couponNum = couponNum;
		}

		public String getUseTime() {
			return useTime;
		}

		public void setUseTime(String useTime) {
			this.useTime = useTime;
		}

		public String getBindTime() {
			return bindTime;
		}

		public void setBindTime(String bindTime) {
			this.bindTime = bindTime;
		}

		public String getCouponStat() {
			return couponStat;
		}

		public void setCouponStat(String couponStat) {
			this.couponStat = couponStat;
		}

		public String getDateStr() {
			return dateStr;
		}

		public void setDateStr(String dateStr) {
			this.dateStr = dateStr;
		}

	}
}
