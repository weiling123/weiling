package com.beijing.beixin.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * 网络状态公共类
 * 
 * @author wenjian
 * 
 */
public class NetWorkUtils {

	public static final int NETWORK_TYPE_EHRPD = 14; // Level 11
	public static final int NETWORK_TYPE_EVDO_B = 12; // Level 9
	public static final int NETWORK_TYPE_HSPAP = 15; // Level 13
	public static final int NETWORK_TYPE_IDEN = 11; // Level 8
	public static final int NETWORK_TYPE_LTE = 13; // Level 11

	/**
	 * 检测网络是否存在
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 得到当前的手机网络类型
	 * 
	 * @param context
	 * @return
	 */
	public static String getCurrentNetType(Context context) {
		String type = "";
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info == null) {
			type = null;
		} else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
			type = "wifi";
		} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
			int subType = info.getSubtype();
			if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
					|| subType == TelephonyManager.NETWORK_TYPE_EDGE) {
				type = "2G";
			} else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
					|| subType == TelephonyManager.NETWORK_TYPE_EVDO_A
					|| subType == TelephonyManager.NETWORK_TYPE_EVDO_0
					|| subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
				type = "3G";
			} else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
				type = "4G";
			}
		}
		return type;
	}

	/**
	 * 检测wifi网络状态是否良好
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkGood(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		if (wifiInfo != null) {
			// 得到的值是一个0到-100的区间值
			if (-50 < wifiInfo.getRssi() && wifiInfo.getRssi() < 0) {
				// 0到-50表示信号最好
				return true;
			} else if (-70 < wifiInfo.getRssi() && wifiInfo.getRssi() <= -50) {
				// -50到-70表示信号偏差
				return false;
			} else if (wifiInfo.getRssi() <= -70) {
				// 小于-70表示最差
				return false;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 获取wifi网络信息
	 * 
	 * @param context
	 * @return
	 */
	public static WifiInfo isNetworkInfo(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		if (wifiInfo.getBSSID() != null) {
			// wifi名称
			String ssid = wifiInfo.getSSID();
			// wifi信号强度
			int signalLevel = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 5);
			// wifi速度
			int speed = wifiInfo.getLinkSpeed();
			// wifi速度单位
			String units = WifiInfo.LINK_SPEED_UNITS;
			System.out.println("ssid=" + ssid + ",signalLevel=" + signalLevel + ",speed=" + speed + ",units=" + units);
		}
		return wifiInfo;
	}

	/**
	 * 判断MOBILE网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断wifi网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 获取当前网络连接的类型信息 是mobile 0 还是wifi 1 还是无网络 -1
	 * 
	 * @param context
	 * @return
	 */
	public static int getConnectedType(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
				if (mNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE
						&& mNetworkInfo.getType() != ConnectivityManager.TYPE_WIFI) {
					return ConnectivityManager.TYPE_MOBILE;
				} else if (mNetworkInfo.getType() != ConnectivityManager.TYPE_MOBILE
						&& mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
					return ConnectivityManager.TYPE_WIFI;
				} else if (mNetworkInfo.getType() != ConnectivityManager.TYPE_MOBILE
						&& mNetworkInfo.getType() != ConnectivityManager.TYPE_WIFI) {
					return -1;
				}
			}
		}
		return -1;
	}

	/**
	 * 检测wifi网络状态值
	 * 
	 * 得到的值是一个0到-100的区间值 0到-50表示信号最好 -50到-70表示信号偏差 小于-70表示最差
	 * 
	 * @param context
	 * @return
	 */
	public static Integer isNetworkGoodDetail(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		if (wifiInfo != null) {
			return wifiInfo.getRssi();
		} else {
			return -200;
		}
	}

	/**
	 * 获取网络连接速度 通用 联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EGDE，电信的2G为CDMA，电信 的3G为EVDO
	 * 
	 * @param type
	 *            wifi -1 mobile-0
	 * @param subType
	 * @return
	 */
	public static boolean isConnectionFast(Context context) {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		// 有网络
		if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
			int type = mNetworkInfo.getType();
			int subType = mNetworkInfo.getSubtype();
			if (type == ConnectivityManager.TYPE_WIFI) {
				System.out.println("CONNECTED VIA WIFI");
				return true;
			} else if (type == ConnectivityManager.TYPE_MOBILE) {
				switch (subType) {
				case TelephonyManager.NETWORK_TYPE_1xRTT:
					return false; // ~ 50-100 kbps
				case TelephonyManager.NETWORK_TYPE_CDMA:// 电信的2G
					return false; // ~ 14-64 kbps
				case TelephonyManager.NETWORK_TYPE_EDGE:// 移动和联通的2G
					return false; // ~ 50-100 kbps
				case TelephonyManager.NETWORK_TYPE_EVDO_0:// 电信的3G
					return true; // ~ 400-1000 kbps
				case TelephonyManager.NETWORK_TYPE_EVDO_A:// 电信的3G
					return true; // ~ 600-1400 kbps
				case TelephonyManager.NETWORK_TYPE_GPRS:// 移动和联通的2G
					return false; // ~ 100 kbps
				case TelephonyManager.NETWORK_TYPE_HSDPA:// 联通的3G
					return true; // ~ 2-14 Mbps
				case TelephonyManager.NETWORK_TYPE_HSPA:
					return true; // ~ 700-1700 kbps
				case TelephonyManager.NETWORK_TYPE_HSUPA:
					return true; // ~ 1-23 Mbps
				case TelephonyManager.NETWORK_TYPE_UMTS:// 联通的3G
					return true; // ~ 400-7000 kbps
				// NOT AVAILABLE YET IN API LEVEL 7
				case NETWORK_TYPE_EHRPD:
					return true; // ~ 1-2 Mbps
				case NETWORK_TYPE_EVDO_B:
					return true; // ~ 5 Mbps
				case NETWORK_TYPE_HSPAP:
					return true; // ~ 10-20 Mbps
				case NETWORK_TYPE_IDEN:
					return false; // ~25 kbps
				case NETWORK_TYPE_LTE:
					return true; // ~ 10+ Mbps
				// Unknown
				case TelephonyManager.NETWORK_TYPE_UNKNOWN:
					return false;
				default:
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 获取网络连接速度的带宽描述
	 * 
	 * @param type
	 *            wifi -1 mobile-0
	 * @param subType
	 * @return
	 */
	public static String isConnectionFastDetail(Context context) {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		// 有网络
		if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
			int type = mNetworkInfo.getType();
			int subType = mNetworkInfo.getSubtype();
			if (type == ConnectivityManager.TYPE_WIFI) {
				System.out.println("CONNECTED VIA WIFI");
				return "wifi";
			} else if (type == ConnectivityManager.TYPE_MOBILE) {
				switch (subType) {
				case TelephonyManager.NETWORK_TYPE_1xRTT:
					return "50-100 kbps慢"; // ~ 50-100 kbps
				case TelephonyManager.NETWORK_TYPE_CDMA:// 电信的2G
					return "14-64 kbps慢"; // ~ 14-64 kbps
				case TelephonyManager.NETWORK_TYPE_EDGE:// 移动和联通的2G
					return "50-100 kbps慢"; // ~ 50-100 kbps
				case TelephonyManager.NETWORK_TYPE_EVDO_0:// 电信的3G
					return "400-1000 kbps快"; // ~ 400-1000 kbps
				case TelephonyManager.NETWORK_TYPE_EVDO_A:// 电信的3G
					return "600-1400 kbps快"; // ~ 600-1400 kbps
				case TelephonyManager.NETWORK_TYPE_GPRS:// 移动和联通的2G
					return "100 kbps慢"; // ~ 100 kbps
				case TelephonyManager.NETWORK_TYPE_HSDPA:// 联通的3G
					return "2-14 Mbps快"; // ~ 2-14 Mbps
				case TelephonyManager.NETWORK_TYPE_HSPA:
					return "700-1700 kbps快"; // ~ 700-1700 kbps
				case TelephonyManager.NETWORK_TYPE_HSUPA:
					return "1-23 Mbps快"; // ~ 1-23 Mbps
				case TelephonyManager.NETWORK_TYPE_UMTS:// 联通的3G
					return "400-7000 kbps快"; // ~ 400-7000 kbps
				// NOT AVAILABLE YET IN API LEVEL 7
				case NETWORK_TYPE_EHRPD:
					return "1-2 Mbps快"; // ~ 1-2 Mbps
				case NETWORK_TYPE_EVDO_B:
					return "5 Mbps快"; // ~ 5 Mbps
				case NETWORK_TYPE_HSPAP:
					return "10-20 Mbps快"; // ~ 10-20 Mbps
				case NETWORK_TYPE_IDEN:
					return "25 kbps慢"; // ~25 kbps
				case NETWORK_TYPE_LTE:
					return "10+ Mbps"; // ~ 10+ Mbps
				// Unknown
				case TelephonyManager.NETWORK_TYPE_UNKNOWN:
					return "未知网络";
				default:
					return "默认慢";
				}
			} else {
				return "未知网络类型";
			}
		} else {
			return "网络不存在";
		}
	}
}
