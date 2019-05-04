package com.beijing.beixin.utils;

/**
 * 处理重复点击的通用类
 * 
 * @author wenjian
 * 
 */
public class ViewUtil {

	private static long lastClickTime;

	/**
	 * 防止按钮啥的重复点击 多次 造成多次操作
	 */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		// 2秒内重复点击不作处理
		if (time - lastClickTime < 1000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
}
