package com.beijing.beixin.pojo;

import java.io.Serializable;

/**
 * 用户详细信息
 * 
 * @author ouyanghao
 * 
 */
@SuppressWarnings("serial")
public class UseredInfoBean implements Serializable {

	public static final int SEX_MALE = 0;

	public static final int SEX_FEMALE = 1;

	public static final int SEX_SECRET = 2;
	/**
	 * 用户主键
	 */
	private Integer sysUserId;
	/**
	 * 登录账号，用于用户登录
	 */
	private String loginId;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 性别，‘0’为男性 、‘1’为女性、‘2’未知性别
	 */
	private int userSexCode;
	private String userEmile;

	public String getUserEmile() {
		return userEmile;
	}

	public void setUserEmile(String userEmile) {
		this.userEmile = userEmile;
	}

	public int getUserSexCode() {
		return userSexCode;
	}

	public void setUserSexCode(int userSexCode) {
		this.userSexCode = userSexCode;
	}

	/**
	 * 积分
	 */
	private Integer integral;
	/**
	 * 用户头像地址
	 */
	private String userIcon;
	/**
	 * 订单总数
	 */
	private Integer orderTotalCount;
	/**
	 * 待付款订单数
	 */
	private Integer toPayTotalCount;
	/**
	 * 待发货订单数
	 */
	private Integer toSendTotalCount;
	/**
	 * 待收货订单数
	 */
	private Integer toReceiveTotalCount;
	/**
	 * 已完成订单数
	 */
	private Integer finishedTotalCount;
	/**
	 * 用户消息总数
	 */
	private Integer messageNum;
	/**
	 * 客户热线
	 */
	private String webPhone;
	/**
	 * 会员等级
	 */
	private Integer preStore;
	/**
	 * 用户收藏总数，包括商品和店铺
	 */
	private Integer collectionTotalCount;
	/**
	 * 用户优惠券总数
	 */
	private Integer couponTotalCount;
	/**
	 * 用户收货地址总数
	 */
	private Integer receiverAddrTotalCount;
	/**
	 * 用户收藏的店铺数量
	 */
	private Integer collectionShopCount;
	/**
	 * 卡号
	 */
	private String cardNo;
	/**
	 * 用户等级
	 */
	private String userLevelNm;

	public String getUserLevelNm() {
		return userLevelNm;
	}

	public void setUserLevelNm(String userLevelNm) {
		this.userLevelNm = userLevelNm;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getCollectionShopCount() {
		return collectionShopCount;
	}

	public void setCollectionShopCount(Integer collectionShopCount) {
		this.collectionShopCount = collectionShopCount;
	}

	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public Integer getOrderTotalCount() {
		return orderTotalCount;
	}

	public void setOrderTotalCount(Integer orderTotalCount) {
		this.orderTotalCount = orderTotalCount;
	}

	public Integer getToPayTotalCount() {
		return toPayTotalCount;
	}

	public void setToPayTotalCount(Integer toPayTotalCount) {
		this.toPayTotalCount = toPayTotalCount;
	}

	public Integer getToSendTotalCount() {
		return toSendTotalCount;
	}

	public void setToSendTotalCount(Integer toSendTotalCount) {
		this.toSendTotalCount = toSendTotalCount;
	}

	public Integer getToReceiveTotalCount() {
		return toReceiveTotalCount;
	}

	public void setToReceiveTotalCount(Integer toReceiveTotalCount) {
		this.toReceiveTotalCount = toReceiveTotalCount;
	}

	public Integer getFinishedTotalCount() {
		return finishedTotalCount;
	}

	public void setFinishedTotalCount(Integer finishedTotalCount) {
		this.finishedTotalCount = finishedTotalCount;
	}

	public Integer getMessageNum() {
		return messageNum;
	}

	public void setMessageNum(Integer messageNum) {
		this.messageNum = messageNum;
	}

	public String getWebPhone() {
		return webPhone;
	}

	public void setWebPhone(String webPhone) {
		this.webPhone = webPhone;
	}

	public Integer getPreStore() {
		return preStore;
	}

	public void setPreStore(Integer preStore) {
		this.preStore = preStore;
	}

	public Integer getCollectionTotalCount() {
		return collectionTotalCount;
	}

	public void setCollectionTotalCount(Integer collectionTotalCount) {
		this.collectionTotalCount = collectionTotalCount;
	}

	public Integer getCouponTotalCount() {
		return couponTotalCount;
	}

	public void setCouponTotalCount(Integer couponTotalCount) {
		this.couponTotalCount = couponTotalCount;
	}

	public Integer getReceiverAddrTotalCount() {
		return receiverAddrTotalCount;
	}

	public void setReceiverAddrTotalCount(Integer receiverAddrTotalCount) {
		this.receiverAddrTotalCount = receiverAddrTotalCount;
	}
}
