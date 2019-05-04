package com.beijing.beixin.utils.loginSDK;

import android.content.Context;

/**
 * 接口类
 * 
 * @author zhangxue
 * @date 2015-12-14
 */
public class Urls {

	public static Context context;
	/** 接口IP */
	public static String SERVER_IP = "www.sunrise.network";
	// public static String SERVER_IP="192.168.1.144";
	/** 接口IP */
	// public static String SERVER_IP="192.168.1.79";//"www.sunrise.network";
	/** 接口端口 */
	// public static int SERVER_PORT=8080;
	public static int SERVER_PORT = 15926;
	// public static int SERVER_PORT=8088;//15926;
	/** 接口地址 */
	public static String SERVER_ADDRESS = "http://" + SERVER_IP + ":" + SERVER_PORT + "/parking/app/user/";
	public static String SUFFIX = ".do?";
	public static String SUFFIX1 = ".do";
	/** App更新地址 */
	public static String APK_URL;
	/** 注册接口 */
	public static String REGISTER_ACTION = SERVER_ADDRESS + "register" + SUFFIX;// 参数:
																				// registerName
																				// registerPassword
	/** 登陆接口 */
	// http://www.sunrise.network：15926/parking/app/user/login.do?
	public static String LOGIN_ACTION = SERVER_ADDRESS + "login" + SUFFIX;// 参数:
																			// userAcount
																			// userPassword
																			// uuid
	/** 忘记密码 */
	public static String FORGET_PWD = SERVER_ADDRESS + "sendEmail" + SUFFIX;// 参数:
																			// LoginName
	/** 重置密码 */
	public static String CHANGE_PWD = SERVER_ADDRESS + "UpdatePwd" + SUFFIX;// 参数:
																			// password
																			// userId
}