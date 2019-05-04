package com.beijing.beixin.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

/**
 * 处理程序退出的共用类
 * 
 * @author wenjian
 * 
 */
public class ExitApplication extends Application {

	private List<Activity> activityList = new ArrayList<Activity>();
	private List<Activity> activityAllList = new ArrayList<Activity>();
	private static ExitApplication instance;

	private ExitApplication() {
	}

	/** 单例模式中获取唯一的ExitApplication实例 */
	public static ExitApplication getInstance() {
		if (null == instance) {
			instance = new ExitApplication();
		}
		return instance;
	}

	/** 添加Activity到容器中 */
	public void addActivity(Activity activity) {
		if (activityList.contains(activity)) {
		} else {
			activityList.add(activity);
		}
	}

	/** 遍历所有Activity并finish */
	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
	}

	/**
	 * 每个Activity都必须加入的list
	 * 
	 * @param activity
	 */
	public void addAllActivity(Activity activity) {
		if (activityAllList.contains(activity)) {
		} else {
			activityAllList.add(activity);
		}
	}

	public boolean containActivity(Activity activity) {
		if (activityAllList.contains(activity)) {
			return true;
		} else {
			return false;
		}
	}

	/** 完整地遍历所有Activity并finish */
	public void exitAll() {
		for (Activity activity : activityAllList) {
			activity.finish();
		}
	}
}
