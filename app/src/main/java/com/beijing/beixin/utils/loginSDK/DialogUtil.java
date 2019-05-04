package com.beijing.beixin.utils.loginSDK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.beixin.R;

/**
 * 对话框工具类
 * 
 * @author zhangxue
 * @date 2015-12-14
 */
@SuppressLint("InflateParams")
public class DialogUtil {

	/**
	 * 提示信息弹出框
	 * 
	 * @param context
	 * @param msg
	 *            弹出框内容
	 * @param cancel
	 *            是否可取消
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	public static void showDialog(Context context, String msg, boolean cancel) {
		Button yes;
		final Dialog dialog = new Dialog(context, R.style.mask_dialog);// 创建自定义样式dialog
		LinearLayout popView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.please_in_userinfo_tishi,
				null);
		((TextView) popView.findViewById(R.id.tv1)).setText(msg);
		yes = (Button) popView.findViewById(R.id.yes);
		yes.setOnClickListener(new View.OnClickListener() {// 确认的点击事件

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		dialog.setCancelable(cancel);// 不可以用“返回键”取消
		dialog.setContentView(popView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		dialog.setFeatureDrawableAlpha(Window.FEATURE_OPTIONS_PANEL, 0);
		dialog.show();
		WindowManager windowManager = ((Activity) context).getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width = (int) (display.getWidth()) - 200; // 设置宽度
		dialog.getWindow().setAttributes(lp);
	}

	/**
	 * 提示信息弹出框
	 * 
	 * @param context
	 * @param msg
	 *            弹出框内容
	 * @param cancel
	 *            是否可取消
	 */
	@SuppressWarnings("deprecation")
	public static void showDialog(Context context, String msg, boolean cancel, final EditText editText) {
		Button yes;
		final Dialog dialog = new Dialog(context, R.style.mask_dialog);// 创建自定义样式dialog
		LinearLayout popView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.please_in_userinfo_tishi,
				null);
		((TextView) popView.findViewById(R.id.tv1)).setText(msg);
		yes = (Button) popView.findViewById(R.id.yes);
		yes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				editText.setFocusable(true);
				editText.setFocusableInTouchMode(true);
				editText.requestFocus();
			}
		});
		dialog.setCancelable(cancel);// 不可以用“返回键”取消
		dialog.setContentView(popView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		dialog.setFeatureDrawableAlpha(Window.FEATURE_OPTIONS_PANEL, 0);
		dialog.show();
		WindowManager windowManager = ((Activity) context).getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width = (int) (display.getWidth()) - 200; // 设置宽度
		dialog.getWindow().setAttributes(lp);
	}

	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	public static Dialog createLoadingDialog(Context context, String msg) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		ProgressBar spaceshipImage = (ProgressBar) v.findViewById(R.id.loading);// main.xml中的ImageView
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		tipTextView.setText(msg);// 设置加载信息
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		loadingDialog.setCancelable(false);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
		return loadingDialog;
	}

	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
}
