package com.beijing.beixin.utils.takeRoundPhotoSDK.utils;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.beijing.beixin.ui.myself.MyAccountActivity;
import com.beijing.beixin.utils.ByteUtil;
import com.beijing.beixin.utils.takeRoundPhotoSDK.TakePhotoConfig;
import com.beijing.beixin.utils.takeRoundPhotoSDK.view.ClipActivity;

/**
 * 类名称:CameraUtils 类描述:圆形裁剪拍照Demo的工具类 作者:张静 最后更新时间:2015年12月3日 下午3:47:52 版本:v1.0
 */
public class CameraUtils {

	/**
	 * 调用系统照相机拍照
	 * 
	 * @param activity
	 *            (需要使用的activity)
	 * @param requestCode
	 *            (请求码)
	 * @author 张静
	 * @Time 2015年12月3日下午2:14:10
	 */
	public static Uri takePhoto(Activity activity, int requestCode) {
		// 判断当前的sdcard是否可用，如果不可用，将默认路径改掉
		if (!checkSDCardAvailable()) {
			TakePhotoConfig.photoSavePath = activity.getCacheDir().getPath();
		}
		File imgDir = new File(TakePhotoConfig.photoSavePath);
		// 目录不存在创建文件夹
		if (!imgDir.exists()) {
			imgDir.mkdir();
		}
		Uri imageUri = null;
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		imageUri = Uri.fromFile(new File(TakePhotoConfig.photoSavePath, TakePhotoConfig.photoSaveName));
		openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		activity.startActivityForResult(openCameraIntent, requestCode);
		return imageUri;
	}

	/**
	 * 打开相册
	 * 
	 * @param activity
	 *            (需要使用的activity)
	 * @param requestCode
	 *            (请求码)
	 * @author 张静
	 * @Time 2015年12月3日下午2:15:27
	 */
	public static void openAlbums(Activity activity, int requestCode) {
		Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
		openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		activity.startActivityForResult(openAlbumIntent, requestCode);
	}

	/**
	 * 打开相册后跳转到裁剪activity
	 * 
	 * @param activity
	 *            (需要使用的activity)
	 * @param data
	 *            (Intent对象)
	 * @param requestCode
	 *            (请求码)
	 * @author 张静
	 * @Time 2015年12月3日下午2:16:06
	 */
	public static void afterOpenAlbums(Activity activity, Intent data, int requestCode) {
		if (data == null) {
			return;
		}
		Uri uri = data.getData();
		String[] proj = { MediaStore.Images.Media.DATA };
		@SuppressWarnings("deprecation")
		Cursor cursor = activity.managedQuery(uri, proj, null, null, null);
		if(cursor == null){
			if(uri == null){
				return ;
			}
			Intent intent3 = new Intent(activity, ClipActivity.class);
			intent3.putExtra("path", uri.getPath());
			activity.startActivityForResult(intent3, requestCode);
			return;
		}
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String path = cursor.getString(column_index);// 图片在的路径
		Intent intent3 = new Intent(activity, ClipActivity.class);
		intent3.putExtra("path", path);
		activity.startActivityForResult(intent3, requestCode);
		// activity.finish();
	}

	/**
	 * 打开相机后跳转到裁剪activity
	 * 
	 * @param activity
	 *            (需要使用的activity)
	 * @param requestCode
	 *            (请求码)
	 * @author 张静
	 * @Time 2015年12月3日下午2:17:31
	 */
	public static void afterTakePhoto(Activity activity, int requestCode) {
		String path = TakePhotoConfig.photoSavePath + TakePhotoConfig.photoSaveName;
		Intent intent2 = new Intent(activity, ClipActivity.class);
		intent2.putExtra("path", path);
		activity.startActivityForResult(intent2, requestCode);
		// activity.finish();
	}

	/**
	 * 根据路径获得bitmap
	 * 
	 * @param url
	 *            (路径)
	 * @return
	 * @author 张静
	 * @Time 2015年12月3日下午2:09:42
	 */
	public static Bitmap getLoacalBitmap(String url) {
		Bitmap bitMap = BitmapFactory.decodeFile(url);
		int width = bitMap.getWidth();
		int height = bitMap.getHeight();
		int newWidth = 150;
		int newHeight = 150;
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		bitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);
		return bitMap;
	}

	/**
	 * 处理bitmap对象，给指定的宽和高
	 * 
	 * @param bm
	 *            (需要处理的bitmap)
	 * @param newWidth
	 *            (新的宽度)
	 * @param newHeight
	 *            (新的高度)
	 * @return 处理后的bitmap对象
	 * @author 张静
	 * @Time 2015年12月3日下午2:10:34
	 */
	public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的bitmap
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
		return newbm;
	}

	/**
	 * 检查sdcard
	 * 
	 * @return
	 * @author 张静
	 * @Time 2015年12月3日下午2:09:21
	 */
	public static boolean checkSDCardAvailable() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * 在activity的onActivityResult方法里面要执行的操作
	 * 
	 * @param activity
	 *            (Activity对象)
	 * @param requestCode
	 *            (请求码)
	 * @param resultCode
	 *            (结果码)
	 * @param data
	 *            (Intent对象)
	 * @author 张静
	 * @Time 2015年12月10日下午1:57:36
	 */
	public static void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data,
			ImageView imageview, PopupWindow mPop) {
		if (resultCode != -1) {
			Intent intent = new Intent();
			activity.setResult(-2, intent);
			return;
		}
		switch (requestCode) {
		case TakePhotoConfig.PHOTOZOOM:// 相册
			CameraUtils.afterOpenAlbums(activity, data, TakePhotoConfig.IMAGE_COMPLETE);
			break;
		case TakePhotoConfig.PHOTOTAKE:// 拍照
			CameraUtils.afterTakePhoto(activity, TakePhotoConfig.IMAGE_COMPLETE);
			break;
		case TakePhotoConfig.IMAGE_COMPLETE:
			String url = data.getStringExtra("roundpath");
			Bitmap bitmap = CameraUtils.getLoacalBitmap(url);// 根据路径获得bitmap
			imageview.setImageBitmap(CameraUtils.zoomImg(bitmap, TakePhotoConfig.width, TakePhotoConfig.height));
			mPop.dismiss();
			((MyAccountActivity) activity).sendhttp(url);
			break;
		default:
			break;
		}
	}

}
