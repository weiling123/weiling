package com.beijing.beixin.utils.loginSDK;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 记录用户名，密码之类的首选项
 * 
 * @author ASSU_X
 */
public class PreferenceUtil {

	/*** 保存在手机中SharePrefrenc的名称 */
	public static String SP_NANE = "logings";
	private static PreferenceUtil preference = null;
	public SharedPreferences sharedPreference;
	/** 是否是首次登陆 */
	public static final String FIRST = "first";

	/**
	 * 单例
	 * 
	 * @param context
	 * @return
	 */
	public static synchronized PreferenceUtil getInstance(Context context) {
		if (preference == null)
			preference = new PreferenceUtil(context);// 创建
		return preference;
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 */
	public PreferenceUtil(Context context) {
		sharedPreference = context.getSharedPreferences(SP_NANE, Context.MODE_PRIVATE);
	}

	/**
	 * 得到是否是首次登陆
	 * 
	 * @return
	 */
	public boolean getFirst() {
		return sharedPreference.getBoolean(FIRST, true);
	}

	/**
	 * 设置是否是首次登陆
	 * 
	 * @param f
	 */
	public void setFirst(boolean f) {
		Editor edit = sharedPreference.edit();
		edit.putBoolean(FIRST, f);
		edit.commit();
	}

	/**
	 * 得到保存的用户名和密码
	 * 
	 * @param name
	 * @param defValue
	 * @return
	 */
	public String getString(String name, String defValue) {
		String value = sharedPreference.getString(name, defValue);
		return value;
	}

	/**
	 * 设置登陆的用户名和密码
	 * 
	 * @param name
	 * @param value
	 */
	public void setString(String name, String value) {
		Editor edit = sharedPreference.edit();
		edit.putString(name, value);
		edit.commit();
	}

	/**
	 * 得到保存的用户名和密码
	 * 
	 * @param name
	 * @param defValue
	 * @return
	 */
	public int getInt(String name, int defValue) {
		int value = sharedPreference.getInt(name, defValue);
		return value;
	}

	/**
	 * 设置登陆的用户名和密码
	 * 
	 * @param name
	 * @param value
	 */
	public void setInt(String name, int value) {
		Editor edit = sharedPreference.edit();
		edit.putInt(name, value);
		edit.commit();
	}

	/**
	 * 得到保存的用户名和密码
	 * 
	 * @param name
	 * @param defValue
	 * @return
	 */
	public float getFloat(String name, float defValue) {
		float value = sharedPreference.getFloat(name, defValue);
		return value;
	}

	/**
	 * 设置登陆的用户名和密码
	 * 
	 * @param name
	 * @param value
	 */
	public void setFloat(String name, float value) {
		Editor edit = sharedPreference.edit();
		edit.putFloat(name, value);
		edit.commit();
	}

	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 * 
	 * @param context
	 * @param key
	 * @param object
	 */
	public void put(String key, Object object) {
		Editor editor = sharedPreference.edit();
		if (object instanceof String) {
			editor.putString(key, (String) object);
		} else if (object instanceof Integer) {
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean) {
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float) {
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long) {
			editor.putLong(key, (Long) object);
		} else {
			editor.putString(key, object.toString());
		}
		editor.commit();
	}

	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 * 
	 * @param context
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public Object get(String key, Object defaultObject) {
		if (defaultObject instanceof String) {
			return sharedPreference.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer) {
			return sharedPreference.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean) {
			return sharedPreference.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float) {
			return sharedPreference.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long) {
			return sharedPreference.getLong(key, (Long) defaultObject);
		}
		return null;
	}
}
