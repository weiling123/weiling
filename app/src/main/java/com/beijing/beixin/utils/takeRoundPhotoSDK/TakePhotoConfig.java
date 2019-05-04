package com.beijing.beixin.utils.takeRoundPhotoSDK;

import android.os.Environment;

/**
 * 类名称:TakePhotoConfig 类描述:拍照封装配置 作者:张静 最后更新时间:2015年12月16日 上午11:46:18 版本:v1.0
 */
public class TakePhotoConfig {

	/** 请求码 */
	public static final int PHOTOZOOM = 0; // 相册
	public static final int PHOTOTAKE = 1; // 拍照
	public static final int IMAGE_COMPLETE = 2; // 结果

	/** 界面显示的圆形图片的宽度和高度 */
	public static int width = 150;// 显示的bitmap的宽度;
	public static int height = 150;// 显示的bitmap的高度

	/** 照片目录 */
	public static String photoSavePath = Environment.getExternalStorageDirectory() + "/IconDemo/";
	/** 照片名称 */
	public static String photoSaveName = String.valueOf(System.currentTimeMillis()) + ".png";
}
