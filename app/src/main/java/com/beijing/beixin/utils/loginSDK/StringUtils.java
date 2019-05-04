package com.beijing.beixin.utils.loginSDK;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;

/**
 * 字符串工具类
 * 
 * @author zhangxue
 * @date 2015-12-14
 */
@SuppressLint("DefaultLocale")
public class StringUtils {

	private final static Pattern input = Pattern.compile("^[A-Za-z0-9]{5,15}$");
	private final static Pattern phone = Pattern.compile("^(1)\\d{10}$");// 手机号

	/**
	 * 用户名是否为空
	 * 
	 */
	public static boolean isuser(String userString) {
		if (userString == null || userString.trim().length() == 0)
			return false;
		return input.matcher(userString.toLowerCase()).matches();
	}

	/**
	 * 密码是否为空
	 * 
	 */
	public static boolean ispwd(String password) {
		String pa = "^(?=.{8,20}$)(?![0-9]+$)(?!.*(.).*\1)[0-9a-zA-Z]+$";
		if (password == null || password.trim().length() == 0) {
			return false;
		} else {
			if (password.matches(pa)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 判断String是否为空
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isNotNull(String string) {
		if (null != string && !"".equals(string.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	public static String getMap(Map<String, String> map) {
		StringBuffer result = new StringBuffer();
		for (String obj : map.keySet()) {
			result.append(obj + ":" + map.get(obj) + "--");
		}
		return result.toString();
	}

	/**
	 * 判断是不是一个合法的手机号码
	 */
	public static boolean isPhone(String phoneNum) {
		if (phoneNum == null || phoneNum.trim().length() == 0 || !(phoneNum.length() == 11))
			return false;
		return phone.matcher(phoneNum).matches();
	}
}
