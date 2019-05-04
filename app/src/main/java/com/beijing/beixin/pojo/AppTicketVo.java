package com.beijing.beixin.pojo;

public class AppTicketVo {

	public static final String TICKET_KEY = "BXTicketKey123!@#";
	private Integer ticketId;
	private Integer activityId;
	private String ticketCodeNm;
	private Integer ticketStatus;// 0/**< 未出票 */ 1/**< 已领票 */ 2/**< 已使用 */ 3/**<
									// 已失效 */ null（状态未知）
	private String getMobile;
	private Integer getWay;
	private String validTimeFromString;
	private String validTimeToStrig;
	private String getTicketTimeString;
	private String useTicketTimeString;
	private String isUse;
	private String activityName;
	private String activityUrl;

	public String getActivityUrl() {
		return activityUrl;
	}

	public void setActivityUrl(String activityUrl) {
		this.activityUrl = activityUrl;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Integer getTicketId() {
		return ticketId;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getTicketCodeNm() {
		return ticketCodeNm;
	}

	public void setTicketCodeNm(String ticketCodeNm) {
		this.ticketCodeNm = ticketCodeNm;
	}

	public Integer getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(Integer ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public String getGetMobile() {
		return getMobile;
	}

	public void setGetMobile(String getMobile) {
		this.getMobile = getMobile;
	}

	public Integer getGetWay() {
		return getWay;
	}

	public void setGetWay(Integer getWay) {
		this.getWay = getWay;
	}

	public String getValidTimeFromString() {
		return validTimeFromString;
	}

	public void setValidTimeFromString(String validTimeFromString) {
		this.validTimeFromString = validTimeFromString;
	}

	public String getValidTimeToStrig() {
		return validTimeToStrig;
	}

	public void setValidTimeToStrig(String validTimeToStrig) {
		this.validTimeToStrig = validTimeToStrig;
	}

	public String getGetTicketTimeString() {
		return getTicketTimeString;
	}

	public void setGetTicketTimeString(String getTicketTimeString) {
		this.getTicketTimeString = getTicketTimeString;
	}

	public String getUseTicketTimeString() {
		return useTicketTimeString;
	}

	public void setUseTicketTimeString(String useTicketTimeString) {
		this.useTicketTimeString = useTicketTimeString;
	}

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
}
