package com.beijing.beixin.utils.loginSDK;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import org.w3c.dom.Text;

import com.beijing.beixin.R;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.utils.Utils;

/**
 * 判断用户名密码等是否为空、合法等
 * 
 * @author zhangxue
 * @date 2015-12-14
 */
public class CheckUtils {

	/**
	 * 判断用户名是否为空、是否符合规范
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkUsername(Context context, String name, EditText username) {
		if (StringUtils.isNotNull(name)) {// 输入框是否为空
			if (!StringUtils.isuser(name)) {// 用户名是否为空
				DialogUtil.showDialog(context, context.getString(R.string.text_username), true, username);
				return false;
			} else {
				return true;
			}
		} else {
			DialogUtil.showDialog(context, "用户名不能为空", true, username);
			return false;
		}
	}

	/**
	 * 判断手机号是否符合规范
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkPhone(Context context, String phone, EditText etPhone) {
		if (StringUtils.isNotNull(phone)) {// 输入框是否为空
			if (!StringUtils.isPhone(phone)) {// 用户名是否为空
				DialogUtil.showDialog(context, context.getString(R.string.text_phone), true, etPhone);
				return false;
			} else {
				return true;
			}
		} else {
			DialogUtil.showDialog(context, "手机号不能为空", true, etPhone);
			return false;
		}
	}

	/**
	 * 判断密码是否为空、是否符合规范
	 * 
	 * @param pass
	 * @return
	 */
	public static boolean checkPwd(BaseActivity activity, String password) {
		if (TextUtils.isEmpty(password)) {
			activity.showToast("密码不能为空，请重新输入！");
			return false;
		}
		if (password.length() < 6) {
			activity.showToast("密码不能小于6位，请重新输入！");
			return false;
		}

		if (password.length() > 20) {
			activity.showToast("密码不能大于20位，请重新输入！");
			return false;
		}

		return true;

	}

	/**
	 * 检验输入的密码是否合法
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static boolean checkPwd2(Context context, String p1, String p2, EditText pwd1, EditText pwd2) {
		if (StringUtils.isNotNull(p2)) {// 输入框是否为空
			if (!StringUtils.ispwd(p2)) {// 密码是否为空
				DialogUtil.showDialog(context, context.getString(R.string.text_pwd), true, pwd2);
				return false;
			} else {
				if (StringUtils.isNotNull(p1)) {
					if (p1.equals(p2)) {// 两次输入的密码是否一致
						return true;
					} else {
						DialogUtil.showDialog(context, "密码和确认密码不一致", true, pwd2);
						return false;
					}
				} else {
					DialogUtil.showDialog(context, context.getString(R.string.text_pwd), true, pwd1);
					return false;
				}
			}
		} else {
			DialogUtil.showDialog(context, "密码不能为空", true, pwd2);
			return false;
		}
	}
}
