package com.beijing.beixin.utils.loginSDK;

/**
 * 用户信息实体类
 * 
 * @author zhangxue
 * @date 2015-12-14
 */
public class UserInfo {

	/**
	 * login:用户已登录 exit:用户已退出登录 register:用户注册但未维护个人信息
	 */
	public static String loginStatus;
	/** 用户UUID,单人登录使用 */
	public static String uuid;
	/** 用户ID */
	public static String userId;
	/** 用户姓名 */
	public static String userName;
	/** 用户手机号码 */
	public static String userTel;
	/** 用户邮箱 */
	public static String userMail;
	/** 用户地址 */
	public static String userAddr;
	/** 用户账户 */
	public static String userLogin;
	/** 密码 */
	public static String userPassWord;
}
