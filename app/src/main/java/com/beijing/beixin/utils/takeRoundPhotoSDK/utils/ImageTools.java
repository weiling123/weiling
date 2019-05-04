package com.beijing.beixin.utils.takeRoundPhotoSDK.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 类名称:ImageTools 类描述:图片工具类 作者:张静 最后更新时间:2015年12月3日 下午3:48:12 版本:v1.0
 */
public final class ImageTools {

	/**
	 * 把bitmap对象保存到sdcard中
	 * 
	 * @param photoBitmap
	 *            (需要保存的bitmap对象)
	 * @param path
	 *            (保存路径)
	 * @author 张静
	 * @Time 2015年12月3日下午2:22:21
	 */
	public static void savePhotoToSDCard(Bitmap photoBitmap, String path) {
		if (checkSDCardAvailable()) {
			File photoFile = new File(path);
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
						fileOutputStream.flush();
					}
				}
			} catch (Exception e) {
				photoFile.delete();
				e.printStackTrace();
			} finally {
				try {
					fileOutputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 检查sdcard是否可用
	 * 
	 * @return
	 * @author 张静
	 * @Time 2015年12月3日下午2:23:14
	 */
	public static boolean checkSDCardAvailable() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * 根据路径获得指定宽高的bitmap对象
	 * 
	 * @param path
	 *            (路径)
	 * @param w
	 *            (宽度)
	 * @param h
	 *            (高度)
	 * @return 处理好的bitmap对象
	 * @author 张静
	 * @Time 2015年12月3日下午2:23:38
	 */
	public static final Bitmap convertToBitmap(String path, int w, int h) {
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
			BitmapFactory.decodeFile(path, opts);
			int width = opts.outWidth;
			int height = opts.outHeight;
			float scaleWidth = 0.f, scaleHeight = 0.f;
			if (width > w || height > h) {
				scaleWidth = ((float) width) / w;
				scaleHeight = ((float) height) / h;
			}
			opts.inJustDecodeBounds = false;
			float scale = Math.max(scaleWidth, scaleHeight);
			opts.inSampleSize = (int) scale;
			WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
			Bitmap bMapRotate = Bitmap.createBitmap(weak.get(), 0, 0, weak.get().getWidth(), weak.get().getHeight(),
					null, true);
			if (bMapRotate != null) {
				return bMapRotate;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
