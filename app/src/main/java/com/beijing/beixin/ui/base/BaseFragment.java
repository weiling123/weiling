package com.beijing.beixin.ui.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.beixin.R;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.ui.MainActivity;
import com.beijing.beixin.ui.homepage.SearchActivity;
import com.beijing.beixin.ui.myself.BrowseTheFootprintActivity;
import com.beijing.beixin.ui.myself.MyCollectionActivity;
import com.beijing.beixin.ui.myself.login.LoginActivity;
import com.beijing.beixin.ui.shoppingcart.ShopcartActivity;
import com.beijing.beixin.utils.DensityUtil;
import com.beijing.beixin.utils.Utils;

public class BaseFragment extends Fragment {

	private static final int POP_WIDTH = 260;
	// log
	private String TAG = "log";
	// 加载项
	private ProgressDialog progressDialog;
	// 加载项
	private Dialog dialog;
	private PopupWindow popupWindow = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/** 加载中 Dialog */
	public void showProgressDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);
			progressDialog.setTitle("提示");
			progressDialog.setMessage("正在请求数据，请稍后...");
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setCancelable(false);
		}
		if (!progressDialog.isShowing()) {
			progressDialog.show();
		}
	}

	/*** 关闭ProgressDialog */
	public void mDismissProgressDialog() {
		if (progressDialog != null) {
			if (progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
		}
	}

	public void showwindow(View p) {
		int width = Integer.parseInt(DensityUtil.dp2px(getActivity(), 130) + "");
		int y = Integer.parseInt(DensityUtil.dp2px(getActivity(), 50) + "");
		int x = Integer.parseInt(DensityUtil.dp2px(getActivity(), 5) + "");
		if (popupWindow == null) {
			View v = LayoutInflater.from(getActivity()).inflate(R.layout.pop_more, null);
			LinearLayout ll_home = (LinearLayout) v.findViewById(R.id.ll_home);
			LinearLayout ll_search = (LinearLayout) v.findViewById(R.id.ll_search);
			LinearLayout ll_history = (LinearLayout) v.findViewById(R.id.ll_history);
			LinearLayout ll_foot = (LinearLayout) v.findViewById(R.id.ll_foot);
			popupWindow = new PopupWindow(v, width, LayoutParams.WRAP_CONTENT);
			// 首页
			ll_home.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (getActivity() instanceof ShopcartActivity) {
						startActivity(MainActivity.class);
						return;
					}
					((MainActivity) getActivity()).setCurrent(0);
					if (popupWindow != null) {
						popupWindow.dismiss();
					}
				}
			});
			// 搜索
			ll_search.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					startActivity(SearchActivity.class);
					if (popupWindow != null) {
						popupWindow.dismiss();
					}
				}
			});
			// 收藏
			ll_history.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (MyApplication.mapp.getCookieStore() != null) {
						Intent intent4 = new Intent(getActivity(), MyCollectionActivity.class);
						intent4.putExtra("flag", "pro");
						startActivity(intent4);
						if (popupWindow != null) {
							popupWindow.dismiss();
						}
					} else {
						startActivity(LoginActivity.class);
						popupWindow.dismiss();
					}
				}
			});
			// 足迹
			ll_foot.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (MyApplication.mapp.getCookieStore() != null) {
						startActivity(BrowseTheFootprintActivity.class);
						if (popupWindow != null) {
							popupWindow.dismiss();
						}
					} else {
						startActivity(LoginActivity.class);
						popupWindow.dismiss();
					}
				}
			});
		}
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAtLocation(p, Gravity.RIGHT | Gravity.TOP, x, y);
	}

	/**
	 * 公共类加载数据效果
	 * 
	 * @param message
	 */
	@SuppressLint("InflateParams")
	public void showDialog(String message) {
		dialog = new Dialog(getActivity(), R.style.dialog_progress);
		View view = getActivity().getLayoutInflater().inflate(R.layout.base_load_dialog, null);
		TextView textView = (TextView) view.findViewById(R.id.progressbar_message);
		textView.setText(message);
		dialog.setContentView(view);
		dialog.setCancelable(false);
		dialog.show();
	}

	/*** 关闭Dialog */
	public void dismissDialog() {
		if (dialog != null) {
			// if (dialog.isShowing()) {
			dialog.dismiss();
			dialog = null;
			// }
		}
	}

	/**
	 * 提示框
	 * 
	 * @param text
	 */
	public void showToast(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 
	 * @param msg
	 */
	public void i(String msg) {
		Log.i(TAG, msg);
	}

	/**
	 * 跳转公共方法1
	 * 
	 * @param cls
	 */
	public void startActivity(Class<?> cls) {
		Intent intent = new Intent(getActivity(), cls);
		startActivity(intent);
	}
}
