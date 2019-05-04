package com.beijing.beixin.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.text.format.Formatter;

/**
 * 内存
 * 
 * @author ouyanghao
 */
public class SystemMemoryUtils {
	private static final String SystemMemory = "SystemMemory ";

	/**
	 * 获取总内存
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getTotalMemory(Context context) {
		String str1 = "/proc/meminfo";// 系统内存信息文件
		String str2;
		String[] arrayOfString;
		long initial_memory = 0;

		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
			str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

			arrayOfString = str2.split("//s+");
			for (String num : arrayOfString) {
				LogUtil.i(str2, num + "/t");
			}

			initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
			localBufferedReader.close();

		} catch (IOException e) {
			LogUtil.i(SystemMemory, "IOException: " + e);
		}
		return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
	}

	/**
	 * 可用内存 获取当前android可用内存大小
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getAvailMemory(Context context, Activity activity) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(mi);
		return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
	}

}
