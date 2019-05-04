package com.beijing.beixin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	private static final int ONE_MIN = 1000 * 60;
	private static final long ONE_HOUR = 3600 * 1000;
	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public static String getTxtTime(long modify_last_time) {
		if (modify_last_time == 0) {
			return "刚刚";
		}
		long time = System.currentTimeMillis() - modify_last_time;
		if (time < ONE_MIN) {
			return "刚刚";
		}

		int mins = (int) (time / ONE_MIN);
		if (mins < 60) {
			return mins + "分钟前";
		}
		int hour = (int) (time / ONE_HOUR);
		if (hour < 24) {
			return hour + "小时以前";
		} else if (hour < 48) {
			return "1天以前";
		} else if (hour < 72) {
			return "2天以前";
		}
		Date date = new Date(modify_last_time);
		return TIME_FORMAT.format(date);
	}

	public static String getTxtTime(Date date) {
		if (date == null) {
			return "刚刚";
		}
		long modify_last_time = date.getTime();
		return getTxtTime(modify_last_time);
	}

	public static String getDateFormatDate(long time) {
		return TIME_FORMAT.format(new Date(time));
	}
}
