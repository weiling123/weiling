package com.beijing.beixin.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 字符串判断工具类 张鑫
 */
public class StringUtils {

	public static final String MOBILE_REGEXP = "^[1][0-9]{10}$";// 手机号正则表达式
	public static final String VERVIFYCODE_REGEXP = "^[0-9]{4}$";// 验证码正则表达式
	public static final String PASSWORD_REGEXP = "^(?![^a-zA-Z]+$)(?!\\D+$).{6,20}$";// 判断是否为密码

	/**
	 * 判断该字符串是否仅为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str) {
		if (isEmpty(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断是否为手机号码格�?
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) {
		if (isEmpty(str)) {
			return false;
		}
		Pattern p = Pattern.compile(MOBILE_REGEXP);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 判断是否为验证码-短信
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isVerifyCode(String str) {
		if (isEmpty(str)) {
			return false;
		}
		Pattern p = Pattern.compile(VERVIFYCODE_REGEXP);
		Matcher m = p.matcher(str);
		boolean state = m.find();
		return state;
	}

	/**
	 * 判断符合密码规则
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPassword(String str) {
		if (isEmpty(str)) {
			return false;
		}
		Pattern p = Pattern.compile(PASSWORD_REGEXP);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 判断字符串是否为空�?�或者空�?
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.length() <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取唯一码，作为文件名等
	 * 
	 * @return
	 */
	public static String getUUID() {
		return java.util.UUID.randomUUID().toString();
	}

	/**
	 * 以当前时间作为文件名
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDateTimeString() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		return sDateFormat.format(new java.util.Date());
	}

	/**
	 * 
	 * 方法说明：拼接字符串
	 *
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public static String getString(String arg1, String arg2) {
		if (arg1 != null && !"".equals(arg1)) {
			if (arg2 != null && !"".equals(arg2)) {
				StringBuilder sb = new StringBuilder();
				sb.append(arg1);
				sb.append(arg2);
				return sb.toString();
			} else {
				return arg1;
			}
		} else {
			return arg2;
		}
	}

	/**
	 * 方法说明：error格式转换
	 * 
	 * @param info
	 * @return
	 */
	public static Spanned changeErrorInfo(String info) {
		return Html.fromHtml("<font color=#ff0000>" + info + "</font>");
	}

	/**
	 * 方法说明：宠物生日
	 * 
	 * @param time
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDateToString(long time) {
		Date d = new Date(time);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String birthDay = sf.format(d);
		String[] birthNote = birthDay.split("-");
		return new String((Integer.parseInt(birthNote[0]) - 1970) + "年" + (Integer.parseInt(birthNote[1]) - 1) + "月");
	}

	/**
	 * 方法说明：截取数字
	 * 
	 * @param content
	 * @return
	 */
	public static String getNumbers(String content) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	/**
	 * 方法说明：将时间转化为字符串
	 */
	public static Timestamp dateToTimeStamp(Date date) {
		Timestamp ts = null;
		// format的格式可以任意
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String dateStr = sdf.format(date);
			ts = new Timestamp(System.currentTimeMillis());
			ts = Timestamp.valueOf(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;
	}

	/**
	 * 获取时间
	 * 
	 * @param date
	 * @param tag
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static CharSequence getTime(Date date, String tag) {
		SimpleDateFormat format = null;
		if ("age".equals(tag)) {
			format = new SimpleDateFormat("yyyy");
			int selectData = Integer.parseInt(format.format(date));
			String[] currentData = getDate(System.currentTimeMillis());
			return String.valueOf(Integer.parseInt(currentData[0]) - selectData);
		} else if ("year".equals(tag)) {
			format = new SimpleDateFormat("yyyy");
		} else if ("date".equals(tag)) {
			format = new SimpleDateFormat("yyyy-MM-dd");
		} else if ("all".equals(tag)) {
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}
		if (format == null) {
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}
		return format.format(date);
	}

	/**
	 * 方法说明：字符串转化为数组
	 * 
	 * @param timestamp
	 * @return time['year','month','day','hour','minute','second']
	 */
	public static String[] getDate(long timestamp) {
		return getDate(timestamp, "yyyy-MM-dd HH:mm:ss").split("-");
	}

	/**
	 * 方法说明：字符串转化为数组 -聊天-11点声明
	 * 
	 * @param timestamp
	 * @return time['hour','minute','second']
	 */
	public static String[] getTime(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss", Locale.getDefault());
		// Utils.log("查看当前的时间消息为："+sdf.format(new Date()));
		return sdf.format(new Date()).split("-");
	}

	/**
	 * 方法说明： 获取日期
	 * 
	 * @param timestamp
	 * @return time['hour','minute','second']
	 */
	public static String getDate(long timestamp, String txtformat) {
		String format;
		if (TextUtils.isEmpty(txtformat)) {
			format = new SimpleDateFormat("yyyy-MM-dd").format(new Date(timestamp));
		} else {
			format = new SimpleDateFormat(txtformat).format(new Date(timestamp));
		}
		return format;
	}

	public static String String2Birth(String dataStr, String txtformat) {
		SimpleDateFormat df = new SimpleDateFormat(txtformat);
		try {
			Date mData = df.parse(dataStr);
			return dataToAge(mData);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取当前的宠物年龄
	 */
	public static String dataToAge(Date date) {
		StringBuilder sb = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dataTime = format.format(date);
		if (!TextUtils.isEmpty(dataTime)) {
			/// Utils.log("info1+"+dataTime);
			String[] ym = dataTime.split("-");
			if (ym != null && ym.length > 0) {
				// Utils.log("info0:"+ym[0].toString());
				long mCurrentDate = System.currentTimeMillis();
				String[] mCurrentYm = getDate(mCurrentDate);
				sb = new StringBuilder();
				int year = (Integer.valueOf(mCurrentYm[0])) - (Integer.valueOf(ym[0]));
				if (ym.length >= 1 && mCurrentYm.length >= 1) {
					// Utils.log("info3");
					int mYmMonth = Integer.valueOf(ym[1]);
					int mCurrentMonth = Integer.valueOf(mCurrentYm[1]);
					if (mCurrentMonth > mYmMonth) {
						sb.append(String.valueOf(year)).append("年").append(String.valueOf(mCurrentMonth - mYmMonth))
								.append("月");
					} else if (mCurrentMonth < mYmMonth) {
						if (year > 1) {
							sb.append(String.valueOf(year - 1));
							sb.append("年");
						}
						sb.append(String.valueOf(mCurrentMonth + 12 - mYmMonth)).append("月");
					} else {
						sb.append(String.valueOf(year)).append("年");
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 方法说明：将字符串转化为时间戳
	 * 
	 * @param date_str
	 * @param format
	 * @return
	 */
	public static String date2TimeStamp(String date_str, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return String.valueOf(sdf.parse(date_str).getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 方法说明：日期字符串转化为时间
	 * 
	 * @param str
	 * @return
	 */
	public static long date2timer(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}

	/**
	 * 方法说明：获取当前view拥有的数据（限textview/edittext）
	 * 
	 * @param view
	 * @return
	 */
	public static String getViewData(View view) {
		if (view != null) {
			if (view instanceof TextView) {
				return ((TextView) view).getText().toString();
			} else if (view instanceof EditText) {
				return ((EditText) view).getText().toString();
			}
		}
		return "";
	}

	public static String getStrTime(String timestr) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long lcc_time = Long.valueOf(timestr);
		re_StrTime = sdf.format(new Date(lcc_time * 1L));
		return re_StrTime;
	}
}
