package com.beijing.beixin.utils.loginSDK;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.beijing.beixin.ui.myself.login.LoginActivity;

/**
 * 账号密码登录的task
 * 
 * @author zhangxue
 * @date 2015-12-14
 */
public class LoginTasks extends AsyncTask<String, String, Object> {

	public LoginActivity activity;// LoginActivity

	/**
	 * 构造方法
	 * 
	 * @param activity
	 */
	public LoginTasks(LoginActivity activity) {
		this.activity = activity;
	}

	@Override
	protected Object doInBackground(String... arg0) {
		String result = null;// 返回结果
		String requestUrl = arg0[0];// url
		List<NameValuePair> params = new ArrayList<NameValuePair>();// 参数的集合
		params.add(new BasicNameValuePair("username", arg0[1]));// 用户名
		params.add(new BasicNameValuePair("userpwd", arg0[2]));// 密码
		HttpUtilsHelp httputils = new HttpUtilsHelp();
		try {
			result = httputils.doPost(requestUrl, params);
			if (!TextUtils.isEmpty(result)) {// 是否为空
				result = "111";
			}
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Object result) {
		// activity.loginSuccess((String) result);
		if (!TextUtils.isEmpty((String) result)) {
			// activity.loginSuccess((String) result);// 登录成功 回调
		}
		super.onPostExecute(result);
	}

	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);
	}
}
