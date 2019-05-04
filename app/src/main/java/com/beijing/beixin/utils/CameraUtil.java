package com.beijing.beixin.utils;

import java.io.File;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

import com.beijing.beixin.R;

/**
 * <pre>
 * 业务名:
 * 功能说明: 图片选择
 * 编写日期:	
 * 作者:	 张鑫
 * 要求版本 android4.3以上
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class CameraUtil {

	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	private static AlertDialog mDialog;
	private static Activity mActivity;

	/**
	 * 方法说明：调用打开选择图片方式
	 * 
	 * @param activity
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	public static void openPhotoSelect(Activity activity) {
		mActivity = activity;
		if (activity instanceof Activity && ((Activity) activity).isFinishing()) {
			return;
		}
		View photo = LayoutInflater.from(activity).inflate(R.layout.photo, null);
		View photograph_photos = photo.findViewById(R.id.photograph_photos);
		Button photo_photograph = (Button) photo.findViewById(R.id.photo_photograph);
		View cancel = photo.findViewById(R.id.cancel);

		photo_photograph.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openCamera(mActivity, 1);
				if (mDialog != null && mDialog.isShowing()) {
					mDialog.cancel();
				}
			}
		});
		photograph_photos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openGallery(mActivity, 0, 3);
				if (mDialog != null && mDialog.isShowing()) {
					mDialog.cancel();
				}
			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mDialog != null) {
					mDialog.cancel();
				}
			}
		});

		mDialog = new AlertDialog.Builder(activity).setCancelable(true).create();
		mDialog.show();
		Window window = mDialog.getWindow();
		/**
		 * style 引用了drawable目录下的两个动画类
		 */
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
		wl.alpha = 0.8f;
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

		mDialog.onWindowAttributesChanged(wl);
		mDialog.setCanceledOnTouchOutside(true);

		window.setContentView(photo, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}

	private static void openCamera(Activity activity, int requestCode) {
		Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (hasSdcard()) {
			intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/temp_user_photo")));
		}
		activity.startActivityForResult(intentFromCapture, requestCode);
	}

	private static void openGallery(Activity activity, int intRequestCode, int kitRequestCode) {
		Intent intentFromGallery = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intentFromGallery.setType("image/*");
		intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
		intentFromGallery.addCategory(Intent.CATEGORY_OPENABLE);
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
			activity.startActivityForResult(intentFromGallery, kitRequestCode);
		} else {
			activity.startActivityForResult(intentFromGallery, intRequestCode);
		}
	}

	public static void startPhotoZoom(Activity activity, Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
			String url = getPath(activity, uri);
			intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
		} else {
			intent.setDataAndType(uri, "image/*");
		}

		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", true);
		activity.startActivityForResult(intent, 2);
	}

	// 以下是关键，原本uri返回的是file:///...来着的，android4.4返回的是content:///...
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	private static String getPath(final Context context, final Uri uri) {
		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}

			} else if (isDownloadsDocument(uri)) {
				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			} else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				String selection = "_id=?";
				String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}

		else if ("content".equalsIgnoreCase(uri.getScheme())) {
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		} else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	private static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	private static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	private static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	private static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}

	private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

		Cursor cursor = null;
		String column = "_data";
		String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

}
