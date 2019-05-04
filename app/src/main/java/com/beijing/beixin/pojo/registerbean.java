package com.beijing.beixin.pojo;

import java.io.Serializable;

/**
 * 2.1.6.用户注册，完善资料
 * 
 * @author ouyanghao
 * 
 */
@SuppressWarnings("serial")
public class registerbean implements Serializable {

	private Integer sysUserId;// 用户主键
	private String loginId;// 登录账号，用于用户登录
	private String userName;// 用户姓名
	private String userMobile;// 手机号码
	private String userTel;// 固定电话
	private String userSexCode;// 性别，‘0’为男性 、‘1’为女性、‘2’未知性别
	private String userEmail;// 用户邮箱
	private String registerDate;// 注册时间，格式为yyyy-MM-dd
								// HH:mm:ss，时区为GMT+8，例如：2015-01-01 12:00:00。
	private String userStatCode;// 用户状态，‘0’为正常状态、‘1’为冻结状态。
	private String userIcon;// 头像图片地址
	private String userSourceCode;// 用户来源编码，记录用户注册来源。‘0’为前台注册、‘1‘为电话注册、‘2’为QQ会员注册、‘3’为微博会员注册、‘4’为淘宝会员注册、‘5’为人人网会员注册、‘6’为豆瓣网会员注册、‘7’为开心网会员注册、‘8’为360会员注册。
	private String userGradeLevelId;// 会员等级

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

	public String getUserSexCode() {
		return userSexCode;
	}

	public void setUserSexCode(String userSexCode) {
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

	public String getUserStatCode() {
		return userStatCode;
	}

	public void setUserStatCode(String userStatCode) {
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
}
