package com.beijing.beixin.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

/** 工具类 */
public class Utils {

	private static Activity activity;
	static final String algorithmStr = "AES/ECB/PKCS5Padding";

	public static int getWidth(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		return windowManager.getDefaultDisplay().getWidth();
	}

	// 字符串的非空
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input) || "null".equals(input))
			return true;
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	// 判断网络是否连接
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	// 验证手机号
	public static boolean isMobileNO(String mobiles) {
		// Pattern pattern = Pattern
		// .compile("^((13[0-9]{1})|159|153|189|182)+\\d{8}$");
		Pattern pattern = Pattern
				.compile("^(((13[0-9]{1})|(15[0-9]{1})|(14[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\\d{8})$");
		Matcher m = pattern.matcher(mobiles);
		return m.matches();
	}

	// 验证身份证
	public static boolean doAuthentication(String shenfen) {
		Pattern pattern = Pattern
				.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
		Matcher m = pattern.matcher(shenfen);
		return m.matches();
	}

	// 验证只能输入数字和字母
	public static boolean InputFigureLetter(String input) {
		Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
		Matcher m = pattern.matcher(input);
		return m.matches();
	}

	/**
	 * 字符串转日期(yyyy-MM-dd)
	 */
	public static Date StrToDate(String str) {
		return StrToDate(str, "yyyy-MM-dd");
	}

	/**
	 * 字符串转日期()
	 */
	public static Date StrToDate2(String str) {
		return StrToDate(str, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到几天前的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	// 判断email格式是否正确
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 得到几天后的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 得到当前时间之后的几个小时时间
	 * 
	 * @param differhour
	 * @return
	 */
	public static String getCurrentHourAfter(int differhour) {
		long currenttime = new Date().getTime();
		Date dat = new Date(currenttime + 1000 * 60 * 60 * differhour);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(dat);
	}

	/**
	 * 得到当前时间之前的几个小时时间
	 * 
	 * @param differhour
	 * @return
	 */
	public static String getCurrentHourBefor(int differhour) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		long currenttime = new Date().getTime();
		Date dat = new Date(currenttime - 1000 * 60 * 60 * 2);
		// format.parse(format.format(dat))
		return format.format(dat);
	}

	/**
	 * 字符串转日期
	 */
	public static Date StrToDate(String str, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期转换成Java字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = format.format(date);
		return str;
	}

	/**
	 * 时间转化
	 * 
	 * @param time
	 * @param type类型
	 * @return
	 */
	public static String DateToStr(Date time, String type) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(type);
		String date = dateFormat.format(time);
		return date;
	}

	/**
	 * 获取当前时间，格式为 :yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(now);
		return date;
	}

	/**
	 * 获取当前时间，格式为 :yyyy-MM-dd
	 * 
	 * @return
	 */
	public static Integer getCurrentDate_MM() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
		String date = dateFormat.format(now);
		return isInteger(date);
	}

	/**
	 * 获取当前时间，格式为 :yyyy-MM-dd
	 * 
	 * @return
	 */
	public static Integer getCurrentDate_dd() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
		String date = dateFormat.format(now);
		return isInteger(date);
	}

	/**
	 * 获取当前时间，格式为 :yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurrentDate2() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = dateFormat.format(now);
		return date;
	}

	/**
	 * 获取当前时间，格式为 :yyyy-MM-dd HH:mm
	 * 
	 * @return
	 */
	public static String getCurrentDate3() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = dateFormat.format(now);
		return date;
	}

	/**
	 * 获取明天零点时间，格式为 :yyyy-MM-dd HH:mm：ss
	 * 
	 * @return
	 */
	public static String getTomorrowDateAtZeroAM() {
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString.substring(0, dateString.length() - 8) + "00:00:00";
	}

	/**
	 * string -> int
	 * 
	 * @param s
	 * @return
	 */
	public static int strToInt(String s) {
		int i = 0;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 比较两个 yyyy-MM-dd 格式的日期字符串时间前后
	 * 
	 * @param date1
	 * @param date2
	 * @return true:"date1在date2后" , false:"date1在date2前"
	 */
	public static boolean dateComparator(String date1, String date2) {
		return dateComparator(date1, date2, "yyyy-MM-dd");
	}

	/**
	 * 比较两个 yyyy-MM-dd HH:mm:ss 格式的日期字符串时间前后
	 * 
	 * @param date1
	 * @param date2
	 * @return true:"date1在date2后" , false:"date1在date2前"
	 */
	public static boolean dateComparator2(String date1, String date2) {
		return dateComparator(date1, date2, "yyyy-MM-dd HH:mm:ss");
	}

	public static boolean dateComparator(String date1, String date2, String str) {
		DateFormat df = new SimpleDateFormat(str);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				return false;
			} else if (dt1.getTime() < dt2.getTime()) {
				return true;
			} else {
				return true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	public static boolean dateIsequel(String date1, String date2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() == dt2.getTime()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取两个日期的差 yyyy-MM-dd
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long dateDifference1(String date1, String date2) {
		return dateDifference(date1, date2, "yyyy-MM-dd");
	}

	/**
	 * 获取两个日期的差 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long dateDifference2(String date1, String date2) {
		return dateDifference(date1, date2, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取两个日期的差
	 * 
	 * @param date1
	 * @param date2
	 * @param str
	 * @return
	 */
	public static long dateDifference(String date1, String date2, String str) {
		DateFormat df = new SimpleDateFormat(str);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			long temp = dt2.getTime() - dt1.getTime();
			long result = temp / (1000 * 60);
			return result;
		} catch (Exception exception) {
			exception.printStackTrace();
			return 0;
		}
	}

	/**
	 * 得到两个日期的差
	 * 
	 * @param fDate
	 * @param oDate
	 * @return 天数
	 */
	public static int daysOfTwo(String fDate, String oDate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt1;
		Date dt2;
		try {
			dt1 = df.parse(fDate);
			dt2 = df.parse(oDate);
			Calendar aCalendar = Calendar.getInstance();
			aCalendar.setTime(dt1);
			int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
			int year1 = aCalendar.get(Calendar.YEAR);
			aCalendar.setTime(dt2);
			int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
			int year2 = aCalendar.get(Calendar.YEAR);
			return (day2 - day1) + (year2 - year1) * 365;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 比较两个数的大小
	 * 
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static boolean numComparator(String num1, String num2) {
		int int1;
		int int2;
		try {
			int1 = Integer.parseInt(num1.trim());
			int2 = Integer.parseInt(num2.trim());
			return int1 > int2;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取当前日期是星期几<br>
	 * 
	 * @param time
	 *            需要获取的日期
	 * @return 当前日期是星期几，(从0开始，周日、周一.....)
	 */
	public static int getWeekOfDate(String time) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dt;
		int week = 0;
		try {
			dt = df.parse(time);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			week = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if (week < 0)
				week = 0;
		} catch (ParseException e) {
		}
		return week;
	}

	/**
	 * 判断是否为double类型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDoubleNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) { // 不是数字
			return false;
		}
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) { // 不是数字
			return false;
		}
	}

	/**
	 * 关于EditText的判断方法
	 * 
	 * @param editText
	 * @param yajin
	 *            限额大小
	 * @param c
	 */
	public static void setPricePoint(final EditText editText, final double yajin, final Context c) {
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (isDoubleNumeric(s.toString())) {
					if (s.toString().contains(".")) {
						if (s.length() - 1 - s.toString().indexOf(".") > 2) {
							s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
							editText.setText(s);
							editText.setSelection(s.length());
						}
					}
					if (s.toString().trim().substring(0).equals(".")) {
						s = "0" + s;
						editText.setText(s);
						editText.setSelection(2);
					}
					if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
						if (!s.toString().substring(1, 2).equals(".")) {
							editText.setText(Utils.isInteger(s.subSequence(0, s.length()).toString()) + "");
							// editText.setSelection(s.length()+1);
							return;
						}
					}
				} else {
					return;
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().equals("")) {
					editText.setText("0");
					return;
				}
				if (!isDoubleNumeric(s.toString())) {
					editText.setText("0");
					Toast.makeText(c, "请输入正确价格", Toast.LENGTH_SHORT).show();
					return;
				}
				double strcount = Double.parseDouble(s.toString());
				double count = yajin;
				if (strcount > count) {
					editText.setText("0");
					Toast.makeText(c, "超出限额", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	/** 时间秒数转换为时间 */
	public static String getDatesft(Long dates) {
		// long sstime = dates.toString();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dates);
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(date);
	}

	/** 时间秒数转换为时间 */
	public static String getFullDate(Long dates) {
		// long sstime = dates.toString();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dates);
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/** 时间秒数转换为时间 */
	public static String getDate(Long dates) {
		// long sstime = dates.toString();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dates);
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/** 时间秒数转换为时间 */
	public static String getDate2(Long dates) {
		// long sstime = dates.toString();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dates);
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static String checkInt(String num) {
		return (num == null || !Utils.isNumeric(num)) ? "0" : num;
	}

	public static String checkDouble(String num) {
		return (num == null || "NaN".equals(num) || !Utils.isDoubleNumeric(num) || num == "null") ? "0" : num;
	}

	public static String checkStr(String str) {
		return Utils.isEmpty(str) ? "" : str;
	}

	/** 获取前3天时间 */
	public static String FrontThreeDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -3); // 得到前三天
		Date date = calendar.getTime();
		String dates = DateToStrtimeminute(date);
		return dates;
	}

	/** 获取前15天时间 */
	public static String FroutFifteenFDays() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -15); // 得到前十五天
		Date date = calendar.getTime();
		String dates = DateToStrtimeminute(date);
		return dates;
	}

	/** 获取前30天时间 */
	public static String FroutthirtyDays() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -30); // 得到前三十天
		Date date = calendar.getTime();
		String dates = DateToStrtimeminute(date);
		return dates;
	}

	/**
	 * 几分钟以后的时间
	 * 
	 * @param after
	 * @return
	 */
	public static String MinueLaterTime(int after) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, after); // 得到前三十天
		Date date = calendar.getTime();
		String dates = DateToStrtimeminute(date);
		return dates;
	}

	/** 日期转换字符串 */
	public static String DateToStrtimeminute(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;
	}

	/** 检查Integer数据 */
	public static Integer isInteger(Integer num) {
		return isInteger(num + "");
	}

	/** 检查Double数据 */
	public static Double isDouble(Double num) {
		return isDouble(num + "");
	}

	/** String检查Integer数据 */
	public static Integer isInteger(String num) {
		return Integer.parseInt(checkInt(num));
	}

	/** String检查Double数据 */
	public static Double isDouble(String num) {
		return Double.parseDouble(checkDouble(num));
	}

	/**
	 * 获取小时和分钟的字符串
	 * 
	 * @param orderDate
	 * @return
	 */
	public static String getShorDate(Date mDate) {
		if (mDate == null) {
			return "00:00";
		}
		String hoursStr = "";
		String minutesStr = "";
		int hours = mDate.getHours();
		int minutes = mDate.getMinutes();
		if (hours < 10) {
			hoursStr = "0" + hours;
		} else {
			hoursStr = "" + hours;
		}
		if (minutes < 10) {
			minutesStr = "0" + minutes;
		} else {
			minutesStr = "" + minutes;
		}
		return hoursStr + ":" + minutesStr;
	}

	/**
	 * 两个double相减 返回保留2位小数的字符串
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static String getDoubleMin(double a, double b) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(a - b);
	}

	/**
	 * 两个double相减 返回保留2位小数的字符串
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double getAddDouble(double x, double y) {
		BigDecimal add1 = new BigDecimal(Double.toString(x));
		BigDecimal add2 = new BigDecimal(y + "");
		return add1.add(add2).doubleValue();
	}

	/**
	 * 得到小数点后两位
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static String parseDecimalDouble2(double x) {
		DecimalFormat df = new DecimalFormat("0.00");
		df.setMaximumFractionDigits(2);
		df.setGroupingSize(0);
		df.setRoundingMode(RoundingMode.FLOOR);
		String a = df.format(isDouble(x));
		return a;
	}

	/**
	 * 得到小数点后1位
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	// public static String parseDecimalDouble1(String x) {
	// DecimalFormat df = new DecimalFormat("0.0");
	// df.setMaximumFractionDigits(1);
	// df.setGroupingSize(0);
	// df.setRoundingMode(RoundingMode.FLOOR);
	// String a = df.format(isDouble(x));
	// return a;
	// }

	/**
	 * 得到小数点后两位
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static String parseDecimalDouble2(String str) {
		DecimalFormat df = new DecimalFormat("0.00");
		df.setMaximumFractionDigits(2);
		df.setGroupingSize(0);
		df.setRoundingMode(RoundingMode.FLOOR);
		String a = df.format(isDouble(str));
		return a;
	}

	/**
	 * 得到小数点后两位
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static String parseDecimalDouble3(String str) {
		DecimalFormat df = new DecimalFormat("0.0");
		df.setMaximumFractionDigits(1);
		df.setGroupingSize(0);
		df.setRoundingMode(RoundingMode.FLOOR);
		String a = df.format(isDouble(str));
		return a;
	}

	/**
	 * 两个double相减 返回保留2位小数的字符串
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static String getDoubleMin(String a, String b) {
		double x = isDouble(a);
		double y = isDouble(b);
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(x - y);
	}

	/**
	 * 两个double相加 返回保留2位小数的字符串
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static String getDoubleAdd(double a, double b) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(a + b);
	}

	/**
	 * 两个double相加 返回保留2位小数的字符串
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static String getDoubleAdd(String a, String b) {
		double x = isDouble(a);
		double y = isDouble(b);
		return parseDecimalDouble2(x + y);
	}

	/**
	 * double取两位
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static String formatDoubleReturnString(double a) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(a);
	}

	/**
	 * 判断app是否安装
	 * 
	 * @author gaof
	 * @return boolean
	 */
	public static boolean isAppInstalled(Context context, String packageName) {
		final PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
		List<String> pName = new ArrayList<String>();
		if (pinfo != null) {
			for (int i = 0; i < pinfo.size(); i++) {
				String pn = pinfo.get(i).packageName;
				pName.add(pn);
				if (pn.contains(packageName)) {
					String version = pinfo.get(i).versionName;// 获取版本号
					int versionNum = Utils.isInteger(version.replace(".", "").trim());
				}
			}
		}
		return pName.contains(packageName);
	}

	/**
	 * 得到当前应用版本号
	 * 
	 * @author gaof
	 * @return boolean
	 */
	public static String getAppVersion(Context context) {
		String version = "1.0.0";
		final PackageManager packageManager = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = packageManager.getPackageInfo(context.getPackageName(), 0);
			version = info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return version;
		}
		// info.versionCode
		// info.packageName;
		// info.signatures;
		return version;
	}

	/**
	 * 防止重复点击
	 */
	private static long lastClickTime;

	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 800) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/**
	 * 获取银联插件版本号
	 * 
	 * @param context
	 * @return 插件版本号
	 */
	public static String getYinlianPluginVersion(Context context) {
		String version = "1.0.0";
		PackageInfo info = null;
		PackageManager packageManager = context.getPackageManager();
		try {
			info = packageManager.getPackageInfo("com.chinaums.mpospluginpad", 0);
			if (info != null) {
				version = info.versionName;
			} else {
				return version;
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}

	/**
	 * double取两位
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double formatDoubleReturnDouble(double a) {
		DecimalFormat df = new DecimalFormat("0.00");
		return isDouble(df.format(a));
	}

	/**
	 * 把电话号码替换成带星号的 例如：182****6742 假如不是电话号码的就不进行替换
	 * 
	 * @param phone
	 * @return
	 */
	public static String replacePhoneWithAsterisk(String phone) {
		String newphone = phone;
		if (isMobileNO(newphone)) {
			newphone = phone.substring(0, 3) + "****" + phone.substring(7);
		}
		return newphone;
	}

	/**
	 * 获取屏幕分辨率的高度
	 * 
	 * @param activity
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getScreenHeight(Context context) {
		Display mDisplay = ((Activity) context).getWindowManager().getDefaultDisplay();
		return mDisplay.getHeight();
	}

	/**
	 * 获取屏幕分辨率的宽度
	 * 
	 * @param activity
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getScreenWidth(Activity activity) {
		Display mDisplay = activity.getWindowManager().getDefaultDisplay();
		return mDisplay.getWidth();
	}

	/**
	 * 通过backtype的种类进行确认字段1.还车结算界面,2.违章结算界面,3.维修结算界面,4.取车租金支付,5.取车先付结算界面
	 * 6.预授权列表界面 0：不关联结算单 1：一次结算 2：二次结算 取车 支付 0 还车支付 1 违章预授权列表 2
	 * 
	 * @author gaof
	 */
	public static String ConfirmSettlementTimesVaule(String backtype) {
		String SettlementTimes = "";
		switch (Utils.isInteger(backtype)) {
		case 1:
			SettlementTimes = "1";
			break;
		case 2:
			SettlementTimes = "1";
			break;
		case 3:
			SettlementTimes = "1";
			break;
		case 4:
			SettlementTimes = "0";
			break;
		case 5:
			SettlementTimes = "0";
			break;
		case 6:
			SettlementTimes = "2";
			break;
		}
		return SettlementTimes;
	}

	/**
	 * @author gaof
	 * @param 银行卡号
	 *            将银行卡号处理为前四后6的格式
	 */
	public static String ChangeToBankCard(String bankcard) {
		String bankcardS = "";
		bankcardS = bankcard.substring(0, 6) + "*******" + bankcard.substring(bankcard.length() - 4, bankcard.length());
		return bankcardS;
	}

	/**
	 * @author gaof
	 * @param billsMID
	 *            billsTID 商户编号,商户终端号
	 */
	public static boolean billsMidOrTid(String billsMID, String billsTID) {
		if (Utils.isEmpty(billsMID) || Utils.isEmpty(billsTID)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 验证邮政编码
	 * 
	 * @param post
	 *            邮编
	 * @return
	 */
	public static boolean checkPost(String post) {
		if (post.matches("[0-9]\\d{5}(?!\\d)")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * double 乘法
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double mul(double d1, double d2) {
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.multiply(bd2).doubleValue();
	}

	/**
	 * 获取apk的版本号 currentVersionCode
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getAPPVersionCodeFromAPP(Context ctx) {
		int currentVersionCode = 0;
		String appVersionName = "";
		PackageManager manager = ctx.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
			appVersionName = info.versionName; // 版本名
			currentVersionCode = info.versionCode; // 版本号
			System.out.println(currentVersionCode + " " + appVersionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch blockd
			e.printStackTrace();
		}
		return appVersionName;
	}

	/**
	 * 加密
	 */
	public static String encode(String content, String keyBytes) {
		// 加密之后的字节数组,转成16进制的字符串形式输出
		return parseByte2HexStr(encrypt(content, changeTo16(keyBytes)));
	}

	private static byte[] encrypt(String content, String password) {
		try {
			byte[] keyStr = getKey(password);
			SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
			Cipher cipher = Cipher.getInstance(algorithmStr);// algorithmStr
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// ʼ
			byte[] result = cipher.doFinal(byteContent);
			return result; //
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	private static byte[] getKey(String password) {
		byte[] rByte = null;
		if (password != null) {
			rByte = password.getBytes();
		} else {
			rByte = new byte[24];
		}
		return rByte;
	}

	private static String changeTo16(String key) {
		if (key != null && key.length() != 16) {
			if (key.length() > 16) {
				key = key.substring(0, 15);
			} else {
				int length = 16 - key.length();
				for (int i = 0; i < length; i++) {
					key += "1";
				}
			}
		}
		return key;
	}

	/***
	 * 银联订单查询新接口失败状态判断0 未支付 1 支付成功，2 支付失败，3 支付中，4 确认中，5 已经确认 6 已经冲正，7 账单已经提交，8
	 * 冲正失败，9 账单生成中 10 冲正中，11 交易超时无应答，12 交易超时无应答 13 确认失败，14 撤销中，15 已撤销，16 撤销失败
	 * 17 退货中，18 已退货，19 退货失败，21 锁票中 22 锁票成功，23 锁票失败，26 取消锁票中，27 取消锁票成功 28
	 * 取消锁票失败，29 签名中，30 签名成功，31 签名失败 32 支付准备中，33 支付准备失败，34 支付准备成功,35 预授权中 36
	 * 预授权成功,37 预授权失败,38 预授权撤销中,39 预授权撤销成功 40 预授权撤销失败,41 预授权完成中,42 预授权完成成功,43
	 * 预授权完成失败 44 预授权完成撤销中,45 预授权完成撤销成功,46 预授权完成撤销失败
	 */
	public static String returnStringForQuery(String orderState) {
		String failMessage = "";
		switch (Utils.isInteger(orderState)) {
		case 2:
			failMessage = "支付失败";
			break;
		case 3:
			failMessage = "正在支付中";
			break;
		case 4:
			failMessage = "确认中...";
			break;
		case 5:
			failMessage = "已经确认";
			break;
		case 6:
			failMessage = "已经冲正";
			break;
		case 7:
			failMessage = "账单已经提交";
			break;
		case 8:
			failMessage = "冲正失败";
			break;
		case 9:
			failMessage = "账单生成中";
			break;
		case 10:
			failMessage = "冲正中";
			break;
		case 11:
			failMessage = "交易超时无应答";
			break;
		case 12:
			failMessage = "交易超时无应答";
			break;
		case 13:
			failMessage = "确认失败";
			break;
		case 14:
			failMessage = "撤销中";
			break;
		case 15:
			failMessage = "已撤销";
			break;
		case 16:
			failMessage = "撤销失败";
			break;
		case 17:
			failMessage = "退货中";
			break;
		case 18:
			failMessage = "已退货";
			break;
		case 19:
			failMessage = "退货失败";
			break;
		case 31:
			failMessage = "签名失败";
			break;
		case 35:
			failMessage = "预授权中";
			break;
		case 36:
			failMessage = "预授权成功";
			break;
		case 37:
			failMessage = "预授权失败";
			break;
		case 38:
			failMessage = "预授权撤销中";
			break;
		case 39:
			failMessage = "预授权撤销成功";
			break;
		case 40:
			failMessage = "预授权撤销失败";
			break;
		case 41:
			failMessage = "预授权完成中";
			break;
		case 42:
			failMessage = "预授权完成成功";
			break;
		case 43:
			failMessage = "预授权完成失败";
			break;
		case 44:
			failMessage = "预授权完成撤销中";
			break;
		case 45:
			failMessage = "预授权完成撤销成功";
			break;
		case 46:
			failMessage = "预授权完成撤销失败";
			break;
		case 0:
			failMessage = "此订单为新订单";
			break;
		default:
			failMessage = "支付失败";
			break;
		}
		return failMessage;
	}
}
