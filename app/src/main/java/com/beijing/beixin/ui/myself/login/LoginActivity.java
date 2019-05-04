package com.beijing.beixin.ui.myself.login;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.DataShop;
import com.beijing.beixin.pojo.UserInfoBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.utils.MD5;
import com.beijing.beixin.utils.ToastUtil;
import com.beijing.beixin.utils.Utils;
import com.beijing.beixin.utils.loginSDK.CheckUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 登录
 * 
 * @author liangshibin
 * @date 2015-12-14
 */
@SuppressLint("SimpleDateFormat")
public class LoginActivity extends BaseActivity implements OnClickListener {

	public static final String SETTING_INFOS = "SETTING_Infos";
	/**
	 * 用户名
	 */
	private EditText login_et_username;
	/**
	 * 密码
	 */
	private EditText login_et_password;
	/**
	 * 返回
	 */
	private ImageView left_back;
	/**
	 * 记住密码
	 */
	private CheckBox remember_password;
	/**
	 * 是否记住
	 */
	private boolean isremember;
	/**
	 * 是否自动登录
	 */
	private boolean isautomatic;
	/**
	 * 自动登录
	 */
	private CheckBox automatic_login;
	/**
	 * 登录
	 */
	private Button login_btn_login;
	/**
	 * 点击注册
	 */
	private TextView onclick_register;
	/**
	 * 登录切换按钮
	 */
	private TextView button_login;
	/**
	 * 注册切换按钮
	 */
	private TextView button_register;
	/**
	 * 登录界面
	 */
	private LinearLayout layout_login;
	/**
	 * 注册界面
	 */
	private LinearLayout layout_register;
	/**
	 * 忘记密码
	 */
	private LinearLayout layout_forget_psw;
	/**
	 * 用户名
	 */
	private EditText register_et_username;
	private LinearLayout ll_terms;
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
	private Button btn_register;
	private String username;
	private String passwords;
	private String bookdetail;
	private String mobilecode;
	private String messagesCodeBatch = "";
	private int time = 300;
	private Timer timer = new Timer();
	private TimerTask task;
	private ToggleButton toggleButtonIsLeave;
	private ToggleButton toggleButtonRegisterLeave;
	private SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myself_login);
		Logininit();
		initRegister();
		setListener();
	}

	/**
	 * 初始化控件及监听
	 */
	public void Logininit() {
		login_et_password = (EditText) findViewById(R.id.login_et_password);
		Intent intent = getIntent();
		String exit = intent.getStringExtra("exit");
		bookdetail = intent.getStringExtra("bookdetail");
		login_et_username = (EditText) findViewById(R.id.login_et_username);
		left_back = (ImageView) findViewById(R.id.left_back);
		remember_password = (CheckBox) findViewById(R.id.remember_password);
		automatic_login = (CheckBox) findViewById(R.id.automatic_login);
		login_btn_login = (Button) findViewById(R.id.login_btn_login);
		onclick_register = (TextView) findViewById(R.id.onclick_register);
		button_login = (TextView) findViewById(R.id.button_login);
		button_register = (TextView) findViewById(R.id.button_register);
		layout_login = (LinearLayout) findViewById(R.id.layout_login);
		layout_register = (LinearLayout) findViewById(R.id.layout_register);
		layout_forget_psw = (LinearLayout) findViewById(R.id.layout_forget_psw);
		ll_terms = (LinearLayout) findViewById(R.id.ll_terms);
		toggleButtonIsLeave = (ToggleButton) findViewById(R.id.toggleButtonIsLeave);
		toggleButtonRegisterLeave = (ToggleButton) findViewById(R.id.toggleButtonRegisterLeave);
		remember_password.setOnClickListener(this);
		ll_terms.setOnClickListener(this);
		automatic_login.setOnClickListener(this);
		login_btn_login.setOnClickListener(this);
		onclick_register.setOnClickListener(this);
		button_login.setOnClickListener(this);
		button_register.setOnClickListener(this);
		left_back.setOnClickListener(this);
		toggleButtonIsLeave.setOnClickListener(this);
		toggleButtonRegisterLeave.setOnClickListener(this);
		getPreferences();
		if ("exit".equals(exit)) {
			setPreferences(login_et_username.getText().toString(), login_et_password.getText().toString());
		}
	}

	private void setListener() {
		login_et_password.addTextChangedListener(new TextWatcher() {

			String tmp = "";
			String digits = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				tmp = s.toString();
			}

			@Override
			public void afterTextChanged(Editable s) {
				// Log.d(Sample4Main.TAG, "<><>afterTextChanged<><>" +
				// s.toString());
				String str = s.toString();
				if (str.equals(tmp)) {
					return;
				}
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < str.length(); i++) {
					if (digits.indexOf(str.charAt(i)) >= 0) {
						sb.append(str.charAt(i));
					}
				}
				tmp = sb.toString();
				login_et_password.setText(tmp);
				login_et_password.setSelection(tmp.length());
			}
		});
	}

	/**
	 * 获取SharedPreferences存储的数据
	 */
	public void getPreferences() {
		SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);
		String username = settings.getString("name", "");
		String password = settings.getString("pwd", "");
		login_et_username.setText(username);
		login_et_password.setText(password);
	}

	/**
	 * 存储数据到SharedPreferences
	 * 
	 * @param name
	 * @param pwd
	 */
	public void setPreferences(String name, String pwd) {
		SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("name", name);
		editor.putString("pwd", pwd);
		editor.commit();
	}

	/**
	 * 登录
	 */
	public void login(final String userName, final String userPsw) {
		// final String login_name =
		// login_et_username.getText().toString().trim();
		if (!Utils.isMobileNO(userName)) {
			showToast("手机号码格式不正确，请重新输入！");
			return;
		}
		if ("".equals(Utils.checkStr(userName))) {
			showToast("手机号码不能为空，请重新输入！");
			return;
		}
		if (!CheckUtils.checkPwd(this, userPsw)) {
			return;
		}
		final String MD5PWD = MD5.MD5Encode(userPsw);
		final BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("account", userName);
		params.addBodyParameter("password", MD5PWD);
		showDialog("正在登录。。。");
		task.askNormalRequest(SystemConfig.LOGIN, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				JSONObject obj;
				Log.e("login", arg0.result.toString());
				try {
					obj = new JSONObject(arg0.result);
					UserInfoBean userBean = JSON.parseObject(obj.getString("result"), UserInfoBean.class);
					// http为HttpUtils实例
					DefaultHttpClient httpClient = (DefaultHttpClient) task.utils.getHttpClient();
					// 将用户cookie保存到application中
					MyApplication.mapp.setCookieStore(httpClient.getCookieStore());
					MyApplication.mapp.setUserInfoBean(userBean);
					MyApplication.mapp.setCertUserName(userName);
					MyApplication.mapp.setCertPassword(userPsw);
					((MyApplication) getApplication()).setUsermobile(userName);
					setPreferences(userName, userPsw);
					syncdShpcart();
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(LoginActivity.this, "用户名或密码错误");
			}
		});
	}

	private void finshLogin() {
		dismissDialog();
		if ("bookdetail".equals(bookdetail)) {
			finish();
		} else if ("histroy".equals(bookdetail)) {
			finish();
		} else if ("shopping".equals(bookdetail)) {
			finish();
		} else if ("likeOrcart".equals(bookdetail)) {
			finish();
		} else {
			finish();
			// startActivity(MainActivity.class);
		}
		finish();
	}

	private void syncdShpcart() {
		DataShop[] dataShops = MyApplication.mapp.getDataShop();
		if (dataShops == null || dataShops.length == 0) {
			finshLogin();
			return;
		}
		final BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", "normal");
		params.addBodyParameter("handler", "sku");
		StringBuffer stringBuffer = new StringBuffer();
		for (DataShop dataShop : dataShops) {
			stringBuffer.append(dataShop.getSkuId());
			stringBuffer.append(",");
			stringBuffer.append(dataShop.getNumber());
			stringBuffer.append(",");
			stringBuffer.append(dataShop.getCheckState() == 1 ? "Y" : "N");
			stringBuffer.append(";");
		}
		stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
		params.addBodyParameter("object", stringBuffer.toString());
		task.askNormalRequest(SystemConfig.SYNC_CART_LOCATION, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				finshLogin();
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				finshLogin();
			}
		});
		MyApplication.mapp.emptyDataShop();
	}

	/**
	 * 2.1.4.用户注册，发送短信验证码
	 */
	private void initRegister() {
		register_et_username = (EditText) findViewById(R.id.register_et_username);
		register_et_password = (EditText) findViewById(R.id.register_et_password);
		ed_mobileValidateCode = (EditText) findViewById(R.id.ed_mobileValidateCode);
		send_again = (Button) findViewById(R.id.send_again);
		btn_register = (Button) findViewById(R.id.btn_register);
		send_again.setOnClickListener(this);
		btn_register.setOnClickListener(this);
	}

	/**
	 * 检查用户是否存在
	 */
	public void existUser() {
		username = register_et_username.getText().toString().trim();
		if (!Utils.isMobileNO(username)) {
			showToast("手机号码格式不正确，请重新输入！");
			return;
		}
		if ("".equals(Utils.checkStr(username))) {
			showToast("手机号码不能为空，请重新输入！");
			return;
		}
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("mobile", username);
		baseTask.askNormalRequest(SystemConfig.EXIST_USER, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("sendMesCode", arg0.result.toString());
				try {
					JSONObject object = new JSONObject(arg0.result);
					if ("0".equals(object.getString("result"))) {
						register_one();
					} else {
						showToast("该用户已存在，请重新输入");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {

				Log.e("sendMesCode异常", arg0.toString());
			}
		});
	}

	/**
	 * 2.1.4.用户注册，发送短信验证码
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
				Log.e("sendMesCode异常", arg0.toString());
			}
		});
	}

	/**
	 * 2.1.5.验证短信验证码
	 */
	@SuppressLint("SimpleDateFormat")
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
		if ("".equals(Utils.checkStr(passwords))) {
			showToast("密码不能为空，请重新输入！");
			return;
		}
		if (!CheckUtils.checkPwd(this, passwords)) {
			return;
		}
		showDialog("正在注册，请稍后");
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
		String s = sdf.format(date);
		params.addBodyParameter("messagesCodeBatch", s);
		baseTask.askNormalRequest(SystemConfig.REGISTER_VERIFYVALIDATE, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("cartMesCode", arg0.result.toString());
				try {
					JSONObject jsonObject = new JSONObject(arg0.result);
					if ("1".equals(jsonObject.getString("resultCode"))) {
						register_three();
					} else {
						showToast("验证码验证失败，请重新输入");
						dismissDialog();
					}
				} catch (JSONException e) {
					showToast("验证码验证失败，请重新输入");
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				showToast("验证码验证失败，请重新输入");
				Log.e("cartMesCode异常", arg0.toString());
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
		// if (!"".equals(username)) {
		params.addBodyParameter("mobilePhoneNo", username);
		// }
		// if (!"".equals(passwords)) {
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
				dismissDialog();
				try {
					JSONObject object = new JSONObject(arg0.result);
					if ("1".equals(object.getString("resultCode"))) {
						showToast(object.getString("resultMessage"));
						login(username, passwords);
					} else {
						showToast("注册失败");
					}
				} catch (JSONException e) {
					showToast("注册失败");
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				showToast("注册失败");
				Log.e("register", "fail" + arg0.toString());
				dismissDialog();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn_login:
			String login_name = login_et_username.getText().toString().trim();
			String login_password = login_et_password.getText().toString().trim();
			login(login_name, login_password);
			break;
		case R.id.toggleButtonIsLeave:
			if (toggleButtonIsLeave.isChecked()) {
				login_et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				login_et_password.setSelection(login_et_password.getText().toString().length());
			} else {
				login_et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				login_et_password.setSelection(login_et_password.getText().toString().length());
			}
			break;
		case R.id.toggleButtonRegisterLeave:
			if (toggleButtonRegisterLeave.isChecked()) {
				register_et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				register_et_password.setSelection(register_et_password.getText().toString().length());
			} else {
				register_et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				register_et_password.setSelection(register_et_password.getText().toString().length());
			}
			break;
		case R.id.onclick_register:
			startActivity(ForgetPwdActivity.class);
			break;
		case R.id.button_login:
			button_login.setTextColor(getResources().getColor(R.color.white));
			button_login.setBackgroundResource(R.color.common_dark_red);
			button_register.setTextColor(getResources().getColor(R.color.common_dark_red));
			button_register.setBackgroundResource(R.drawable.white_rounded_shape);
			layout_login.setVisibility(View.VISIBLE);
			layout_register.setVisibility(View.GONE);
			layout_forget_psw.setVisibility(View.VISIBLE);
			break;
		case R.id.button_register:
			button_login.setTextColor(getResources().getColor(R.color.common_dark_red));
			button_login.setBackgroundResource(R.drawable.white_rounded_shape);
			button_register.setTextColor(getResources().getColor(R.color.white));
			button_register.setBackgroundResource(R.color.common_dark_red);
			layout_login.setVisibility(View.GONE);
			layout_register.setVisibility(View.VISIBLE);
			layout_forget_psw.setVisibility(View.INVISIBLE);
			break;
		case R.id.send_again:
			existUser();
			break;
		case R.id.btn_register:
			register_two();
			break;
		case R.id.ll_terms:
			startActivity(TermsActivity.class);
			break;
		case R.id.left_back:
			Clear();
			finish();
			break;
		}
	}

	/**
	 * 监听返回键
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// startActivity(MainActivity.class);
			Clear();
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void Clear() {
		MyApplication.mapp.setCookieStore(null);
		SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("pwd", "");
		editor.commit();
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
							send_again.setBackgroundColor(Color.parseColor("#E5350D"));
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
		time = 300;
		timer.schedule(task, 0, 1000);
	}
}