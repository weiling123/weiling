package com.beijing.beixin.ui.myself.login;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.beijing.beixin.R;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.utils.ToastUtil;
import com.beijing.beixin.utils.Utils;
import com.beijing.beixin.utils.loginSDK.CheckUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 找回密码
 * 
 * @author liangshibin
 * @date 2015-02-17 , OnTouchListener, TextWatcher
 */
public class ForgetPwdActivity extends BaseActivity implements OnClickListener {

	/**
	 * 用户名
	 */
	private EditText register_et_username;
	/**
	 * 密码
	 */
	private EditText register_et_password;
	/**
	 * 验证码
	 */
	private EditText ed_mobileValidateCode;
	/**
	 * 重新发送
	 */
	private Button send_again;
	/**
	 * 注册
	 */
	private Button login_btn_login;
	private String username;
	private String passwords;
	private String mobilecode;
	private String messagesCodeBatch = "";
	private int time = 60;
	private Timer timer = new Timer();
	private TimerTask task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myself_register);
		setNavigationTitle("找回密码");
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		init();
	}

	/**
	 * 2.1.4.用户注册，发送短信验证码
	 */
	private void init() {
		register_et_username = (EditText) findViewById(R.id.register_et_username);
		register_et_password = (EditText) findViewById(R.id.register_et_password);
		ed_mobileValidateCode = (EditText) findViewById(R.id.ed_mobileValidateCode);
		send_again = (Button) findViewById(R.id.send_again);
		login_btn_login = (Button) findViewById(R.id.login_btn_login);
		send_again.setOnClickListener(this);
		login_btn_login.setOnClickListener(this);
	}

	/**
	 * 2.1.4.用户忘记密码，发送短信验证码
	 */
	public void register_one() {
		username = register_et_username.getText().toString().trim();
		if (!Utils.isMobileNO(username)) {
			showToast("手机号码格式不正确，请重新输入！");
			return;
		}
		if ("".equals(Utils.checkStr(username))) {
			showToast("手机号码不能为空，请重新输入！");
			return;
		}
		sendYzm();
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("mobilePhoneNo", username);
		baseTask.askNormalRequest(SystemConfig.REGISTER_SENDSMSVALIDATEMESSAGE, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("sendMesCode", arg0.result.toString());
				try {
					JSONObject object = new JSONObject(arg0.result);
					if ("1".equals(object.getString("resultCode"))) {
						showToast("发送短信成功");
						messagesCodeBatch = object.getString("messagesCodeBatch");
					} else if ("2".equals(object.getString("resultCode"))) {
						showToast(object.getString("resultMessage"));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				ToastUtil.show(ForgetPwdActivity.this, arg0.toString());
			}
		});
	}

	/**
	 * 更改密码
	 */
	public void register_two() {
		username = register_et_username.getText().toString().trim();
		passwords = register_et_password.getText().toString().trim();
		mobilecode = ed_mobileValidateCode.getText().toString().trim();
		if (!Utils.isMobileNO(username)) {
			showToast("手机号码格式不正确，请重新输入！");
			return;
		}
		if ("".equals(Utils.checkStr(username))) {
			showToast("手机号码不能为空，请重新输入！");
			return;
		}
		if (!CheckUtils.checkPwd(this, passwords)) {
			return;
		}
		showDialog("正在请求数据，请稍后");
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("mobile", username);
		params.addBodyParameter("newPsw", passwords);
		params.addBodyParameter("messagesCode", mobilecode);
		Date date = new Date();
		try {
			date.setTime(Long.parseLong(messagesCodeBatch));
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = sdf.format(date);
		params.addBodyParameter("messagesCodeBatch", s);
		baseTask.askNormalRequest(SystemConfig.UPDATE_USER_PSW, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("resetpwd", arg0.result.toString());
				JSONObject jsonObject = null;
				try {
					jsonObject = new JSONObject(arg0.result);
					if ((jsonObject.getBoolean("success"))) {
						showToast("找回密码成功");
						finish();
						dismissDialog();
					}
				} catch (JSONException e) {
					dismissDialog();
					String errorMessage;
					try {
						errorMessage = jsonObject.getString("resultMessage");
						showToast(errorMessage);
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(ForgetPwdActivity.this, arg0.toString());
			}
		});
	}

	/**
	 * back
	 */
	@Override
	public void leftButtonOnClick() {
		finish();
		super.leftButtonOnClick();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.send_again:

			register_one();
			break;
		case R.id.login_btn_login:
			register_two();
			break;
		}
	}

	/**
	 * 发送验证码
	 */
	private void sendYzm() {
		task = new TimerTask() {

			@Override
			public void run() {
				runOnUiThread(new Runnable() { // UI thread

					@Override
					public void run() {
						if (time <= 0) {
							send_again.setBackgroundColor(Color.RED);
							send_again.setEnabled(true);
							send_again.setText("获取验证码");
							task.cancel();
						} else {
							send_again.setText(time + " S");
							send_again.setEnabled(false);
							send_again.setBackgroundColor(Color.GRAY);
						}
						time--;
					}
				});
			}
		};
		time = 60;
		timer.schedule(task, 0, 1000);
	}
}
