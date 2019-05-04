package com.beijing.beixin.pojo;

import java.io.Serializable;

import android.R.integer;

/**
 * 用户信息
 * 
 * @author ouyanghao
 * 
 */
public class UserInfoBean implements Serializable {

	/**
	 * 用户主键
	 */
	private int sysUserId;
	/**
	 * 登录账号，用于用户登录
	 */
	private String loginId;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 手机号码
	 */
	private String userMobile;
	/**
	 * 固定电话
	 */
	private String userTel;
	/**
	 * 性别，‘0’为男性 、‘1’为女性、‘2’未知性别
	 */
	private int userSexCode;
	/**
	 * 用户邮箱
	 */
	private String userEmail;
	/**
	 * 注册时间，格式为yyyy-MM-dd HH:mm:ss，时区为GMT+8，例如：2015-01-01 12:00:00。
	 */
	private String registerDate;
	/**
	 * 用户状态，‘0’为正常状态、‘1’为冻结状态。
	 */
	private int userStatCode;
	/**
	 * 头像图片地址
	 */
	private String userIcon;
	/**
	 * 用户来源编码，记录用户注册来源。‘0’为前台注册、‘1‘为电话注册、‘2’为QQ会员注册、‘3’为微博会员注册、‘4’为淘宝会员注册、‘5’
	 * 为人人网会员注册、‘6’为豆瓣网会员注册、‘7’为开心网会员注册、‘8’为360会员注册。
	 */
	private String userSourceCode;
	/**
	 * 会员等级
	 */
	private String userGradeLevelId;
	/**
	 * 错误返回
	 */
	private errorObject errorObject;
	/**
	 * 密码
	 * 
	 * @return
	 */
	private String userPsw;

	public String getUserPsw() {
		return userPsw;
	}

	public void setUserPsw(String userPsw) {
		this.userPsw = userPsw;
	}

	public errorObject getErrorObject() {
		return errorObject;
	}

	public void setErrorObject(errorObject errorObject) {
		this.errorObject = errorObject;
	}

	public UserInfoBean() {
		super();
	}

	public UserInfoBean(int sysUserId, String loginId, String userName, String userMobile, String userTel,
			int userSexCode, String userEmail, String registerDate, int userStatCode, String userIcon,
			String userSourceCode, String userGradeLevelId) {
		super();
		this.sysUserId = sysUserId;
		this.loginId = loginId;
		this.userName = userName;
		this.userMobile = userMobile;
		this.userTel = userTel;
		this.userSexCode = userSexCode;
		this.userEmail = userEmail;
		this.registerDate = registerDate;
		this.userStatCode = userStatCode;
		this.userIcon = userIcon;
		this.userSourceCode = userSourceCode;
		this.userGradeLevelId = userGradeLevelId;
	}

	public int getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(int sysUserId) {
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

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public int getUserSexCode() {
		return userSexCode;
	}

	public void setUserSexCode(int userSexCode) {
		this.userSexCode = userSexCode;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public int getUserStatCode() {
		return userStatCode;
	}

	public void setUserStatCode(int userStatCode) {
		this.userStatCode = userStatCode;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getUserSourceCode() {
		return userSourceCode;
	}

	public void setUserSourceCode(String userSourceCode) {
		this.userSourceCode = userSourceCode;
	}

	public String getUserGradeLevelId() {
		return userGradeLevelId;
	}

	public void setUserGradeLevelId(String userGradeLevelId) {
		this.userGradeLevelId = userGradeLevelId;
	}

	public static class errorObject implements Serializable {
		private String errorData;
		private String errorCode;
		private String errorText;

		public String getErrorData() {
			return errorData;
		}

		public void setErrorData(String errorData) {
			this.errorData = errorData;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public String getErrorText() {
			return errorText;
		}

		public void setErrorText(String errorText) {
			this.errorText = errorText;
		}
	}
}
