package com.beijing.beixin.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 广播接收工具类
 * 
 * @author 张鑫
 *
 */
public class BroadcastControl {
	public static String FINISH_ACTIVITY = "finish";
	Context context;
	Activity activity;
	MyBroadcastReceiver receiver;

	public BroadcastControl(Context context, Activity activity) {
		this.context = context;
		this.activity = activity;
	}

	public void registBroad(String actionType) {
		receiver = new MyBroadcastReceiver();
		IntentFilter filter = new IntentFilter(actionType);
		context.registerReceiver(receiver, filter);
	}

	public void unregistBroad() {
		context.unregisterReceiver(receiver);
	}

	class MyBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context con, Intent intent) {
			String action = intent.getAction();
			if (action.equals(FINISH_ACTIVITY)) {
				activity.finish();
			}
		}
	}
}
