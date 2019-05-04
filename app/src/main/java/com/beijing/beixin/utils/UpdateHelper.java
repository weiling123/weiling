package com.beijing.beixin.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.beijing.beixin.R;

public class UpdateHelper {

	private String ui;
	private Context context;

	private static ProgressDialog mypDialog;

	private long length = 0;

	private File apkFile;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int w = msg.what;
			if (w == 124) {
				if (mypDialog != null) {
					mypDialog.dismiss();
				}
				Activity activity = (Activity) context;
				activity.finish();
				installApk();
			} else if (w == 123) {
				long c = (Long) msg.obj;
				mypDialog.setProgress((int) c);

			} else if (w == 122) {
				mypDialog.setMax((int) length);
			}
		}
	};
	private Uri uri;

	public UpdateHelper(Context context) {
		this.context = context;
	}

	public void init(String ui) {
		if (TextUtils.isEmpty(ui)) {
			return;
		}
		this.ui = ui;
		File dir = new File(Environment.getExternalStorageDirectory(), "szyz");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String n = MD5.MD5Encode(ui);
		apkFile = new File(dir, n + ".apk");
		if (apkFile.exists()) {
			apkFile.delete();
		}
		onStart();
	}

	public String parserSize(long size) {
		DecimalFormat df = new DecimalFormat("0.00");
		String s = df.format((double) (size / 1e6));
		return context.getString(R.string.offline_map_size, s);
	}

	public void onStart() {

		uri = Uri.parse(ui);

		mypDialog = new ProgressDialog(context);
		mypDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mypDialog.setTitle(getApplicationName());
		// mypDialog.setProgress(0);
		mypDialog.setCancelable(false);
		mypDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				request();
			}
		}).start();
	}

	private CharSequence getApplicationName() {
		PackageManager packageManager = null;
		ApplicationInfo applicationInfo = null;
		try {
			packageManager = context.getPackageManager();
			applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
		return applicationName;
	}

	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	private void installApk() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	private void request() {
		HttpClient client = new DefaultHttpClient();
		InputStream is = null;
		HttpResponse httpResponse = null;
		ByteArrayOutputStream baos = null;
		FileOutputStream fileOutputStream = null;
		try {
			HttpGet request = new HttpGet(uri.toString());
			request.addHeader("Accept-Encoding", "gzip");
			request.addHeader("X-Online-Host", request.getURI().getHost());
			httpResponse = client.execute(request);
			int code = httpResponse.getStatusLine().getStatusCode();
			if (code == HttpStatus.SC_OK) {
				length = httpResponse.getEntity().getContentLength();
				Message msg_ = handler.obtainMessage();
				msg_.what = 122;
				handler.sendMessage(msg_);

				Header encodeHeader;
				String encode = "";
				if ((encodeHeader = httpResponse.getFirstHeader("Content-Encoding")) != null) {
					encode = encodeHeader.getValue();
				}
				if (encode.equals("gzip")) {
					is = new GZIPInputStream(httpResponse.getEntity().getContent());
				} else {
					is = httpResponse.getEntity().getContent();
				}
				if (is != null) {
					fileOutputStream = new FileOutputStream(apkFile);
					byte[] buf = new byte[1024];
					int ch = -1;
					long count = 0;

					while ((ch = is.read(buf)) != -1) {
						fileOutputStream.write(buf, 0, ch);
						count += ch;
						Message msg = handler.obtainMessage();
						msg.what = 123;
						msg.obj = count;
						handler.sendMessage(msg);
					}
				}
				fileOutputStream.flush();

				handler.sendEmptyMessage(124);
			} else {
				// ZCException ze = new ZCException(code + "");
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (baos != null) {
					baos.close();
				}
				if (client != null) {
					client.getConnectionManager().shutdown();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
