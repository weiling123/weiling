package com.beijing.beixin.utils.loginSDK;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * 状态工具类
 * 
 * @author zhangxue
 * @date 2015-12-14
 */
public class StatusUtil {

	public static boolean Login(JSONObject object, Context context) {
		try {
			String userId = object.getString("userId");
			String uuid = object.getString("uuid");
			String userName = object.getString("userName");
			String userLogin = object.getString("userLogin");
			String userPassWord = object.getString("userPassword");
			UserInfo.userId = userId;
			UserInfo.uuid = uuid;
			UserInfo.userPassWord = userPassWord;
			UserInfo.userName = userName;
			UserInfo.userLogin = userLogin;
			Editor editor = PreferenceUtil.getInstance(context).sharedPreference.edit();
			editor.putString("userId", userId);
			editor.putString("uuid", uuid);
			editor.putString("userName", userName);
			editor.putString("userLogin", userLogin);
			editor.putString("userPassword", userPassWord);
			editor.commit();
		} catch (JSONException e) {
			return false;
		}
		return true;
	}

	public static void exit(Context context) {
		Editor editor = PreferenceUtil.getInstance(context).sharedPreference.edit();
		editor.putString("userRole", "");
		editor.putString("userId", "");
		editor.putString("uuid", "");
		editor.putString("imgUrl", "");
		editor.putString("userName", "");
		editor.putString("userMobile", "");
		editor.putString("userLogin", "");
		editor.putString("loginStatus", "exit");
		editor.commit();
		UserInfo.userId = null;
		UserInfo.uuid = null;
		UserInfo.userName = null;
		UserInfo.userTel = null;
		UserInfo.userLogin = null;
		UserInfo.loginStatus = "exit";
	}
}
