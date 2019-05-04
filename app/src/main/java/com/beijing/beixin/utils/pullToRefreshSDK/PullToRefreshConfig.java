package com.beijing.beixin.utils.pullToRefreshSDK;

import com.beijing.beixin.R;

/**
 * 类名称:PullToRefreshConfig 类描述:下拉刷新配置 作者:张静 最后更新时间:2015年12月16日 上午11:45:54
 * 版本:v1.0
 */
public class PullToRefreshConfig {

	public static boolean pullRefreshEnable = true;// 设置是否可以下拉刷新,true为允许，false为不允许
	public static boolean pullLoadEnable = true;// 设置是否可以上拉加载,true为允许，false为不允许
	public static String strDateFormat = "yyyy-MM-dd  HH:mm";// 下拉刷新时顶部的时间显示格式

	/** 下拉刷新的箭头图片 */
	public static int mIconUpdate = R.drawable.pulltorefresh_icon_update;

	/** 刷新和上拉加载的圈圈图片 */
	public static int mIconLoading = R.drawable.pulltorefresh_icon_loading;
}
