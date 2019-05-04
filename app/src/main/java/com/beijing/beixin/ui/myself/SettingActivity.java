package com.beijing.beixin.ui.myself;

import org.json.JSONException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.beixin.R;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.myself.address.AddressActivity;
import com.beijing.beixin.ui.myself.login.TermsActivity;
import com.beijing.beixin.utils.CommonAlertDialog;
import com.beijing.beixin.utils.IgnitedDiagnosticsUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 设置
 * 
 * @author liangshibin 2016-4-9
 */
public class SettingActivity extends BaseActivity implements OnClickListener {

	private ImageView right;
	LinearLayout useraddress_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		setNavigationTitle("更多设置");
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		init();
	}

	private void init() {
		right = (ImageView) findViewById(R.id.navigationRightImageBtn);
		TextView app_version = (TextView) findViewById(R.id.app_version);
		useraddress_info = (LinearLayout) findViewById(R.id.useraddress_info);
		LinearLayout useraccount_info = (LinearLayout) findViewById(R.id.useraccount_info);
		LinearLayout layout_privacy_policy = (LinearLayout) findViewById(R.id.layout_privacy_policy);
		LinearLayout about_beixinwang = (LinearLayout) findViewById(R.id.about_beixinwang);
		ImageView exit = (ImageView) findViewById(R.id.exit);
		// 点击事件监听
		exit.setOnClickListener(this);
		useraccount_info.setOnClickListener(this);
		useraddress_info.setOnClickListener(this);
		layout_privacy_policy.setOnClickListener(this);
		about_beixinwang.setOnClickListener(this);
		IgnitedDiagnosticsUtils util = new IgnitedDiagnosticsUtils();
		@SuppressWarnings("static-access")
		String version = util.getApplicationVersionString(this);
		app_version.setText(version);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.useraddress_info:
			startActivity(AddressActivity.class);
			break;
		case R.id.useraccount_info:
			startActivity(AccountSecurityActivity.class);
			break;
		case R.id.layout_privacy_policy:
			Intent tintent = new Intent(this, TermsActivity.class);
			tintent.putExtra("type", "T");
			startActivity(tintent);
			break;
		case R.id.about_beixinwang:
			Intent aintent = new Intent(this, TermsActivity.class);
			aintent.putExtra("type", "A");
			startActivity(aintent);
			break;
		case R.id.exit:
			exit();
			break;
		}
	}

	public void exit() {
		final CommonAlertDialog dialog = new CommonAlertDialog(this);
		dialog.showYesOrNoDialog("是否退出登录？", new OnClickListener() {
			// dialog.showYesOrNoDialog("您的版本过低\n是否更新到最新版本？", new
			// OnClickListener() {

			@Override
			public void onClick(View v) {
				loginout();
			}
		}, new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	/**
	 * 退出登录
	 */
	public void loginout() {
		BaseTask baseTask = new BaseTask(this);
		baseTask.askCookieRequest(SystemConfig.LOGOUT, null, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("退出成功", arg0.result.toString());
				try {
					org.json.JSONObject jsonObject = new org.json.JSONObject(arg0.result);
					if (jsonObject.getBoolean("success")) {
						MyApplication.mapp.clear();
						showToast("退出成功");
						finish();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("退出成功异常", arg0.toString());
				showToast("退出失败");
			}
		});
	}
}
