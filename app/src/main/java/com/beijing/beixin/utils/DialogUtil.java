package com.beijing.beixin.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.beixin.R;

/**
 * 各种弹出框的工具类
 * 
 * @author 张鑫
 *
 */
public class DialogUtil {
	public static void showDialog(Context context, int titleid, int msgid, int leftbtnid, int rightbtnid,
			OnClickListener LeftOnClickListener, OnClickListener RightOnClickListener, boolean cancelable) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context).setCancelable(false);
		builder.setTitle(titleid);
		builder.setMessage(msgid).setNegativeButton(leftbtnid, LeftOnClickListener)
				.setPositiveButton(rightbtnid, RightOnClickListener).create().show();
	}

	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void showDialog(Context context, String title, String msg, String leftbtn, String rightbtn,
			OnClickListener LeftOnClickListener, OnClickListener RightOnClickListener, boolean cancelable) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context).setCancelable(cancelable);
		builder.setTitle(title).setMessage(msg).setNegativeButton(leftbtn, LeftOnClickListener)
				.setPositiveButton(rightbtn, RightOnClickListener).create().show();
	}

	public static void showNoTitleDialog(Context context, int msgid, int leftbtnid, int rightbtnid,
			OnClickListener LeftOnClickListener, OnClickListener RightOnClickListener, boolean cancelable) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context).setCancelable(cancelable);
		builder.setMessage(msgid).setNegativeButton(leftbtnid, LeftOnClickListener)
				.setPositiveButton(rightbtnid, RightOnClickListener).create().show();
	}

	public static void showNoTitleDialog(Context context, String msg, String leftbtn, String rightbtn,
			OnClickListener LeftOnClickListener, OnClickListener RightOnClickListener, boolean cancelable) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context).setCancelable(cancelable);
		builder.setMessage(msg).setNegativeButton(leftbtn, LeftOnClickListener)
				.setPositiveButton(rightbtn, RightOnClickListener).create().show();
	}

	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	@SuppressLint("InflateParams")
	@SuppressWarnings("unused")
	public static Dialog createLoadingDialog(Context context, String msg) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		ProgressBar spaceshipImage = (ProgressBar) v.findViewById(R.id.loading);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		if (msg != null && !"".equals(msg)) {
			tipTextView.setText(msg);
		}
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		loadingDialog.setCancelable(true);// 可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		return loadingDialog;

	}

}
