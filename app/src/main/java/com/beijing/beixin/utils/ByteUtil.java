package com.beijing.beixin.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.Image;
import android.util.Xml.Encoding;

/**
 * 把图片等转成byte数组的工具类
 * 
 * @author 张鑫
 * 
 */
public class ByteUtil {

	private static final int MAX_DECODE_PICTURE_SIZE = 1920 * 1440;

	public static byte[] bmpToByteArray(Bitmap bmp, final boolean needRecycle) {
		int i;
		int j;
		if (bmp.getHeight() > bmp.getWidth()) {
			i = bmp.getWidth();
			j = bmp.getWidth();
		} else {
			i = bmp.getHeight();
			j = bmp.getHeight();
		}
		Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
		Canvas localCanvas = new Canvas(localBitmap);
		while (true) {
			localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0, i, j), null);
			if (needRecycle)
				bmp.recycle();
			ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
			localBitmap.compress(Bitmap.CompressFormat.JPEG, 50, localByteArrayOutputStream);
			localBitmap.recycle();
			byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
			try {
				localByteArrayOutputStream.close();
				return arrayOfByte;
			} catch (Exception e) {
			}
			i = bmp.getHeight();
			j = bmp.getHeight();
		}
	}

	public static byte[] getHtmlByteArray(final String url) {
		URL htmlUrl = null;
		InputStream inStream = null;
		try {
			htmlUrl = new URL(url);
			URLConnection connection = htmlUrl.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				inStream = httpConnection.getInputStream();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] data = inputStreamToByte(inStream);
		return data;
	}

	// 将字符串转换成二进制字符串，以空格相隔
	public static String StrToBinstr(String str) {
		byte[] strChar = str.getBytes();
		String result = "";
		for (int i = 0; i < strChar.length; i++) {
			result += Integer.toBinaryString(strChar[i]);
		}
		return result;
	}

	public static byte[] getDrawByteArray(final String url, JSONObject params) {
		URL htmlUrl = null;
		InputStream inStream = null;
		try {
			htmlUrl = new URL(url);
			HttpURLConnection httpConnection = (HttpURLConnection) htmlUrl.openConnection();
			httpConnection.setRequestMethod("POST");
			httpConnection.setConnectTimeout(10000);// 连接超时 单位毫秒
			httpConnection.setReadTimeout(20000);// 读取超时 单位毫秒
			httpConnection.setDoOutput(true);
			httpConnection.setDoInput(true);
			// Post 请求不能使用缓存
			httpConnection.setUseCaches(false);
			// 进行编码
			httpConnection.setInstanceFollowRedirects(true);
			httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpConnection.connect();
			DataOutputStream mOutputStream = new DataOutputStream(httpConnection.getOutputStream());
			mOutputStream.writeBytes(params.toString());
			// 输入参数
			mOutputStream.flush();
			mOutputStream.close();
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				inStream = httpConnection.getInputStream();
			}
			byte[] data = inputStreamToByte(inStream);
			httpConnection.disconnect();
			return data;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] inputStreamToByte(InputStream is) {
		try {
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = is.read()) != -1) {
				bytestream.write(ch);
			}
			byte imgdata[] = bytestream.toByteArray();
			bytestream.close();
			return imgdata;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int appendFile(String text, OutputStream outStream) throws IOException {
		byte[] bs = text.getBytes("UTF-8");
		InputStream in = new ByteArrayInputStream(bs);
		try {
			int byteCount = 0;
			byte[] buffer = new byte[1024];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			outStream.flush();
			return byteCount;
		} finally {
			try {
				in.close();
			} catch (IOException ex) {
			}
		}
	}

	public static byte[] readFromFile(String fileName, int offset, int len) {
		if (fileName == null) {
			return null;
		}
		File file = new File(fileName);
		if (!file.exists()) {
			return null;
		}
		if (len == -1) {
			len = (int) file.length();
		}
		if (offset < 0) {
			return null;
		}
		if (len <= 0) {
			return null;
		}
		if (offset + len > (int) file.length()) {
			return null;
		}
		byte[] b = null;
		try {
			RandomAccessFile in = new RandomAccessFile(fileName, "r");
			b = new byte[len]; // 创建合适文件大小的数组
			in.seek(offset);
			in.readFully(b);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public static Bitmap extractThumbNail(final String path, final int height, final int width, final boolean crop) {
		Assert.assertTrue(path != null && !path.equals("") && height > 0 && width > 0);
		BitmapFactory.Options options = new BitmapFactory.Options();
		try {
			options.inJustDecodeBounds = true;
			Bitmap tmp = BitmapFactory.decodeFile(path, options);
			if (tmp != null) {
				tmp.recycle();
				tmp = null;
			}
			final double beY = options.outHeight * 1.0 / height;
			final double beX = options.outWidth * 1.0 / width;
			options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
			if (options.inSampleSize <= 1) {
				options.inSampleSize = 1;
			}
			while (options.outHeight * options.outWidth / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
				options.inSampleSize++;
			}
			int newHeight = height;
			int newWidth = width;
			if (crop) {
				if (beY > beX) {
					newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
				} else {
					newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
				}
			} else {
				if (beY < beX) {
					newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
				} else {
					newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
				}
			}
			options.inJustDecodeBounds = false;
			Bitmap bm = BitmapFactory.decodeFile(path, options);
			if (bm == null) {
				return null;
			}
			final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
			if (scale != null) {
				bm.recycle();
				bm = scale;
			}
			if (crop) {
				final Bitmap cropped = Bitmap.createBitmap(bm, (bm.getWidth() - width) >> 1,
						(bm.getHeight() - height) >> 1, width, height);
				if (cropped == null) {
					return bm;
				}
				bm.recycle();
				bm = cropped;
			}
			return bm;
		} catch (final OutOfMemoryError e) {
			options = null;
		}
		return null;
	}
}
