package com.beijing.beixin.ui.pay;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.beijing.beixin.R;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.utils.Utils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 修改支付密码
 * 
 * @author ouyanghao
 * 
 */
public class PayPwdActivity extends BaseActivity implements OnClickListener {

	private EditText mobile;
	private Button getnumber;
	private Button sure_update;
	private EditText inputnumber;
	private EditText old_pwd;
	private EditText new_pwd;
	private int time = 60;
	private Timer timer = new Timer();
	private TimerTask task;
	// /**
	// * 手机号码
	// */
	// private String mMobile;
	/**
	 * 验证码
	 */
	private String validate;
	/**
	 * 旧密码
	 */
	private String oldPwd;
	/**
	 * 新密码
	 */
	private String newPwd;
	private ToggleButton toggleButtonIsLeave;
	private ToggleButton toggleButtonIsLeave2;
	private String mUserName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_pwd);
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		setNavigationTitle("支付密码");
		init();
	}

	public void init() {
		mobile = (EditText) findViewById(R.id.mobile);
		mUserName = ((MyApplication) getApplication()).getUsermobile();
		mobile.setText(mUserName.substring(0, 3) + "****" + mUserName.substring(7, mUserName.length()));
		getnumber = (Button) findViewById(R.id.getnumber);
		sure_update = (Button) findViewById(R.id.sure_update);
		inputnumber = (EditText) findViewById(R.id.inputnumber);
		old_pwd = (EditText) findViewById(R.id.pwd);
		new_pwd = (EditText) findViewById(R.id.pwd2);
		toggleButtonIsLeave = (ToggleButton) findViewById(R.id.toggleButtonIsLeave);
		toggleButtonIsLeave2 = (ToggleButton) findViewById(R.id.toggleButtonIsLeave2);
		toggleButtonIsLeave.setOnClickListener(this);
		toggleButtonIsLeave2.setOnClickListener(this);
		getnumber.setOnClickListener(this);
		sure_update.setOnClickListener(this);
	}

	/**
	 * 发送短信验证码
	 */
	public void register_one() {
		// mMobile = mobile.getText().toString().trim();
		if ("".equals(Utils.checkStr(mUserName))) {
			showToast("手机号码不能为空，请重新输入！");
			return;
		}
		if (!Utils.isMobileNO(mUserName)) {
			showToast("手机号码格式不正确，请重新输入！");
			return;
		}
		sendYzm();
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("mobile", mUserName);
		baseTask.askCookieRequest(SystemConfig.SEND_VALIDATE_CODE, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("sendMesCode", arg0.result.toString());
				try {
					JSONObject object = new JSONObject(arg0.result);
					if (object.getBoolean("success")) {
						showToast("发送短信成功");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("sendMesCode", arg0.toString());
			}
		});
	}

	/**
	 * 验证短信验证码
	 */
	public void register_two() {
		validate = inputnumber.getText().toString().trim();
		oldPwd = old_pwd.getText().toString().trim();
		newPwd = new_pwd.getText().toString().trim();
		if ("".equals(Utils.checkStr(mUserName))) {
			showToast("手机号码不能为空，请重新输入！");
			return;
		}
		if (!Utils.isMobileNO(mUserName)) {
			showToast("手机号码格式不正确，请重新输入！");
			return;
		}
		if ("".equals(Utils.checkStr(validate))) {
			showToast("验证码不能为空，请重新输入！");
			return;
		}
		if ("".equals(Utils.checkStr(oldPwd))) {
			showToast("密码不能为空，请重新输入！");
			return;
		}
		if ("".equals(Utils.checkStr(newPwd))) {
			showToast("密码不能为空，请重新输入！");
			return;
		}
		if (!Utils.checkStr(oldPwd).equals(Utils.checkStr(newPwd))) {
			showToast("两次密码不一致，请重新输入！");
			return;
		}
		if (oldPwd.length() < 6) {
			showToast("密码不能小于6位，请重新输入！");
			return;
		}
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("validateCode", validate);
		baseTask.askCookieRequest(SystemConfig.VERIFY_VALIDATE, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("cartMesCode", arg0.result.toString());
				try {
					JSONObject jsonObject = new JSONObject(arg0.result);
					if (jsonObject.getBoolean("success")) {
						register_four();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				showToast("验证码验证失败，请重新输入");
			}
		});
	}

	public void register_four() {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("newPwd", newPwd);
		params.addBodyParameter("validateCode", validate);
		baseTask.askCookieRequest(SystemConfig.SET_NEWPSW, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("cartMesCode", arg0.result.toString());
				try {
					JSONObject jsonObject = new JSONObject(arg0.result);
					if (jsonObject.getBoolean("success")) {
						showToast("修改成功");
						finish();
					} else if (jsonObject.getString("result") != null
							&& !"null".equals(jsonObject.getString("result"))) {
						showToast(jsonObject.getString("result"));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("setNewPsw异常", arg0.toString());
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.getnumber:
			register_one();
			break;
		case R.id.sure_update:
			register_two();
			break;
		case R.id.toggleButtonIsLeave:
			if (toggleButtonIsLeave.isChecked()) {
				old_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				old_pwd.setSelection(old_pwd.getText().toString().length());
			} else {
				old_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				old_pwd.setSelection(old_pwd.getText().toString().length());
			}
			break;
		case R.id.toggleButtonIsLeave2:
			if (toggleButtonIsLeave2.isChecked()) {
				new_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				new_pwd.setSelection(new_pwd.getText().toString().length());
			} else {
				new_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				new_pwd.setSelection(new_pwd.getText().toString().length());
			}
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
							getnumber.setBackgroundColor(Color.RED);
							getnumber.setEnabled(true);
							getnumber.setText("获取验证码");
							task.cancel();
						} else {
							getnumber.setText(time + " S");
							getnumber.setEnabled(false);
							getnumber.setBackgroundColor(Color.GRAY);
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
