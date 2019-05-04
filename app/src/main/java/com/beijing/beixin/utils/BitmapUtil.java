package com.beijing.beixin.utils;

import android.content.Context;
import android.widget.ImageView;

import com.beijing.beixin.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 调用xutils的bitmapUtils的方法
 *
 * @author liangshibin 2015-12-29
 */
public class BitmapUtil {

	public void displayImage(Context mContext, ImageView imageView01, String url) {
		BitmapUtils util = new BitmapUtils(mContext);
		util.configDefaultLoadingImage(R.drawable.icon_bg);
		util.configDefaultLoadFailedImage(R.drawable.icon_bg);
		util.configDefaultConnectTimeout(10 * 1000);
		util.configDefaultReadTimeout(10 * 1000);
		util.display(imageView01, url);
	}
}
