package com.beijing.beixin.db;

import java.io.File;

import android.os.Environment;

/**
 * 获取SDCARD路径
 * 
 * @author wenjian
 *
 */
public class SdCardPathUtil {

	/**
	 * 获取路径
	 * 
	 * @return
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		return sdDir.toString();
	}

}
