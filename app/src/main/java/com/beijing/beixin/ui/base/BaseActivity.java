package com.beijing.beixin.ui.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.beijing.beixin.utils.DensityUtil;
import com.beijing.beixin.utils.ExitApplication;

/**
 * com.camelote.component.ui.BaseActivity
 * 
 * @author 李敏 create at 2015年12月2日 下午5:01:15
 */
public class BaseActivity extends FragmentActivity implements NavigationOnClickListener {
	// log
	private String TAG = "log";
	// 加载项
	private ProgressDialog progressDialog;
	// 加载项
	private Dialog dialog;
	private TextView titleView;
	private String navigationTitle, leftBtnText, rightBtnText;
	private TextView rightBtn;
	private ImageView leftImgBtn, rightImgBtn;
	private int leftImageId;
	private int rightImageId;
	private int centerImageId;
	private PopupWindow popupWindow;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏抬头
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 默认为竖屏
		// 加入全局退出队列
		ExitApplication.getInstance().addAllActivity(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		initCommonView();
	}

	/** 加载中 Dialog */
	public void showProgressDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
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

	/**
	 * 公共类加载数据效果
	 * 
	 * @param message
	 */
	@SuppressLint("InflateParams")
	public void showDialog(String message) {
		dialog = new Dialog(this, R.style.dialog_progress);
		View view = getLayoutInflater().inflate(R.layout.base_load_dialog, null);
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
			// }
		}
	}

	/**
	 * 提示框
	 * 
	 * @param text
	 */
	public void showToast(String text) {
		Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG).show();
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
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}

	public void showwindow(View p) {
		int width = Integer.parseInt(DensityUtil.dp2px(this, 130) + "");
		int y = Integer.parseInt(DensityUtil.dp2px(this, 60) + "");
		int x = Integer.parseInt(DensityUtil.dp2px(this, 5) + "");

		if (popupWindow == null) {
			View v = LayoutInflater.from(this).inflate(R.layout.pop_more, null);
			LinearLayout ll_home = (LinearLayout) v.findViewById(R.id.ll_home);
			LinearLayout ll_search = (LinearLayout) v.findViewById(R.id.ll_search);
			LinearLayout ll_history = (LinearLayout) v.findViewById(R.id.ll_history);
			LinearLayout ll_foot = (LinearLayout) v.findViewById(R.id.ll_foot);
			popupWindow = new PopupWindow(v, width, LayoutParams.WRAP_CONTENT);
			// 首页
			ll_home.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					startActivity(MainActivity.class);
					finish();
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
						Intent intent4 = new Intent(BaseActivity.this, MyCollectionActivity.class);
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
	 * 跳转公共方法2 带参数
	 * 
	 * @param cls
	 */
	public void startActivity(Class<?> cls, String[] keys, String[] values) {
		Intent intent = new Intent(this, cls);
		int size = keys.length;
		for (int i = 0; i < size; i++) {
			intent.putExtra(keys[i], values[i]);
		}
		startActivity(intent);
	}

	/**
	 * app之间跳转
	 * 
	 * @param Package
	 *            包名
	 * @param PackageActivity
	 *            类名
	 * @param keys
	 *            传入的key
	 * @param values
	 *            传入的值
	 */
	public void startActivityapp(String Package, String PackageActivity, String[] keys, String[] values) {
		try {
			ComponentName componetName = new ComponentName(
					// 这个是另外一个应用程序的包名
					Package,
					// 这个参数是要启动的Activity
					PackageActivity);
			Intent intent = new Intent();
			// 我们给他添加一个参数表示从apk1传过去的
			int launchFlags = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED;
			intent.setFlags(launchFlags);
			intent.setAction(Intent.ACTION_VIEW);
			int size = keys.length;
			for (int i = 0; i < size; i++) {
				intent.putExtra(keys[i], values[i]);
			}
			intent.setComponent(componetName);
			startActivity(intent);
		} catch (Exception e) {
			showDialog("请先安装该APP");
		}
	}

	private void initCommonView() {
		leftImgBtn = (ImageView) findViewById(R.id.navigationLeftImageBtn);
		if (leftImgBtn != null) {
			leftImgBtn.setVisibility(View.INVISIBLE);
			leftImgBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					leftButtonOnClick();
				}
			});
		}
		rightImgBtn = (ImageView) findViewById(R.id.navigationRightImageBtn);
		if (rightImgBtn != null) {
			rightImgBtn.setVisibility(View.INVISIBLE);
			rightImgBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					rightImageButtonOnClick();
				}
			});
		}
		titleView = (TextView) findViewById(R.id.navigation_title);
		rightBtn = (Button) findViewById(R.id.navigationRightBtn);
		if (rightBtn != null) {
			rightBtn.setVisibility(View.GONE);
			rightBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					rightButtonOnClick();
				}
			});
		}
		if (null != navigationTitle && null != titleView) {
			titleView.setText(navigationTitle);
		}
		if (leftImageId != 0 && null != leftImgBtn) {
			leftImgBtn.setImageResource(leftImageId);
			leftImgBtn.setVisibility(View.VISIBLE);
		}
		if (rightImageId != 0 && null != rightImgBtn) {
			rightImgBtn.setImageResource(rightImageId);
			rightImgBtn.setVisibility(View.VISIBLE);
			rightBtn.setVisibility(View.GONE);
		}
		if (null != leftBtnText) {
			leftImgBtn.setVisibility(View.VISIBLE);
		}
		if (null != rightBtnText && null != rightBtn) {
			rightBtn.setText(rightBtnText);
			rightImgBtn.setVisibility(View.GONE);
			rightBtn.setVisibility(View.VISIBLE);
		}
		if (null == rightBtnText && null != rightBtn) {
			rightBtn.setText(rightBtnText);
			// rightImgBtn.setVisibility(View.GONE);
			rightBtn.setVisibility(View.GONE);
		}
		if (null != rightBtnText && null != rightBtn && rightImageId != 0 && null != rightImgBtn) {
			rightBtn.setText(rightBtnText);
			rightBtn.setVisibility(View.VISIBLE);
			rightImgBtn.setImageResource(rightImageId);
			rightImgBtn.setVisibility(View.VISIBLE);
		}
	}

	public void setNavigationTitle(String title) {
		navigationTitle = title;
		if (null != titleView) {
			titleView.setText(navigationTitle);
		}
	}

	public void setNavigationRightBtnText(String text) {
		rightBtnText = text;
		if (null != rightBtn) {
			rightBtn.setVisibility(View.VISIBLE);
			rightImgBtn.setVisibility(View.GONE);
			rightBtn.setText(rightBtnText);
		}
	}

	public void setNavigationLeftBtnImage(int imageId) {
		leftImageId = imageId;
		if (null != leftImgBtn) {
			leftImgBtn.setImageResource(leftImageId);
			leftImgBtn.setVisibility(View.VISIBLE);
		}
	}

	public void setNavigationRightBtnImage(int imageId) {
		rightImageId = imageId;
		if (null != rightImgBtn) {
			rightImgBtn.setImageResource(rightImageId);
			rightImgBtn.setVisibility(View.VISIBLE);
			rightBtn.setVisibility(View.GONE);
		}
	}

	public void setLeftNavigatoinOnClick() {
	}

	@Override
	public void leftButtonOnClick() {
		if (leftBtnText != null || leftImageId != 0) {
			finish();
		}
	}

	@Override
	public void rightButtonOnClick() {
	}

	@Override
	public void rightImageButtonOnClick() {
	}

	@Override
	public void centerButtonOnClick() {
	}
}

interface NavigationOnClickListener {

	public void leftButtonOnClick();

	public void rightButtonOnClick();

	public void centerButtonOnClick();

	public void rightImageButtonOnClick();
}
