package com.beijing.beixin.ui.myself.login;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

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
import com.beijing.beixin.utils.loginSDK.CheckUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 注册
 * 
 * @author liangshibin
 * @date 2015-01-14 , OnTouchListener, TextWatcher
 */
public class RegisterActivity extends BaseActivity implements OnClickListener {

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myself_register);
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
	 * 2.1.4.用户注册，发送短信验证码
	 */
	public void register_one() {
		username = register_et_username.getText().toString().trim();
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("mobilePhoneNo", username);
		baseTask.askNormalRequest(SystemConfig.REGISTER_SENDSMSVALIDATECODE, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("sendMesCode", arg0.result.toString());
				try {
					JSONObject object = new JSONObject(arg0.result);
					if ("1".equals(object.getString("resultCode"))) {
						showToast("发送短信成功");
						messagesCodeBatch = object.getString("messagesCodeBatch");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				ToastUtil.show(RegisterActivity.this, arg0.toString());
			}
		});
	}

	/**
	 * 2.1.5.验证短信验证码
	 */
	public void register_two() {
		username = register_et_username.getText().toString().trim();
		mobilecode = ed_mobileValidateCode.getText().toString().trim();
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("mobilePhoneNo", username);
		params.addBodyParameter("messagesCode", mobilecode);
		Date date = new Date();
		try {
			date.setTime(Long.parseLong(messagesCodeBatch));
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// date.setHours(0);
		// date.setMinutes(0);
		// date.setSeconds(0);
		String s = sdf.format(date);
		params.addBodyParameter("messagesCodeBatch", s);
		baseTask.askNormalRequest(SystemConfig.REGISTER_VERIFYVALIDATE, params, new RequestCallBack<String>() {

			@SuppressWarnings("null")
			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("cartMesCode", arg0.result.toString());
				try {
					JSONObject jsonObject = new JSONObject(arg0.result);
					if ("1".equals(jsonObject.getString("resultCode"))) {
						showToast("验证验证码成功");
						register_three();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				ToastUtil.show(RegisterActivity.this, arg0.toString());
			}
		});
	}

	/**
	 * 2.1.6.用户注册，完善资料
	 */
	public void register_three() {
		username = register_et_username.getText().toString().trim();
		passwords = register_et_password.getText().toString().trim();
		mobilecode = ed_mobileValidateCode.getText().toString().trim();
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		// if (CheckUtils.checkPhone(this, username, register_et_username)) {
		params.addBodyParameter("mobilePhoneNo", username);
		// }
		// if (CheckUtils.checkPwd(this, passwords, register_et_password)) {
		params.addBodyParameter("password", passwords);
		// }
		params.addBodyParameter("offlineCardNo", "");
		params.addBodyParameter("operationType", "A");
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
		baseTask.askNormalRequest(SystemConfig.REGISTER, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("register", arg0.result.toString());
				try {
					JSONObject object = new JSONObject(arg0.result);
					if ("1".equals(object.getString("resultCode"))) {
						showToast(object.getString("resultMessage"));
						finish();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("register", "fail" + arg0.toString());
			}
		});
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

}
