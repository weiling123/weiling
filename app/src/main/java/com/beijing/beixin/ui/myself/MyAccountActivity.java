package com.beijing.beixin.ui.myself;

import java.io.File;

import org.json.JSONException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.beijing.beixin.R;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.CommonAlertDialog;
import com.beijing.beixin.utils.RoundImageView;
import com.beijing.beixin.utils.ToastUtil;
import com.beijing.beixin.utils.takeRoundPhotoSDK.TakePhotoConfig;
import com.beijing.beixin.utils.takeRoundPhotoSDK.utils.CameraUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 我的账户
 * 
 * @author ouyanghao
 * 
 */
public class MyAccountActivity extends BaseActivity implements OnClickListener {

	public static final int REAQUET_CODE = 1007;
	public static final int NAME_REAQUET_CODE = 1008;
	/**
	 * 头像栏
	 */
	private LinearLayout userhead_info;
	/**
	 * 头像
	 */
	private RoundImageView iv_personHeader;
	/**
	 * 用户名栏
	 */
	private LinearLayout username_info;
	/**
	 * 真实姓名
	 */
	private LinearLayout ll_username;
	/**
	 * 用户名
	 */
	private TextView user_name;
	private TextView user_level;
	/**
	 * 性别栏
	 */
	private LinearLayout usersex_info;
	/**
	 * 性别
	 */
	private TextView user_sex;
	/**
	 * 出生日期
	 */
	private LinearLayout userdate_info;
	/**
	 * 弹出拍照
	 */
	private PopupWindow mPop;
	/**
	 * 图片路径
	 */
	private Uri imagepath = null;
	private String headimage = null;
	private String name = null;
	private String usernames = null;
	private String sex = null;
	private String userLevelNm = null;
	private TextView username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_account);
		init();
	}

	/**
	 * 绑定控件ID及监听
	 */
	public void init() {
		headimage = getIntent().getStringExtra("headimage");
		name = getIntent().getStringExtra("name");
		usernames = getIntent().getStringExtra("username");
		userLevelNm = getIntent().getStringExtra("userLevelNm");
		sex = getIntent().getStringExtra("sex");
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		setNavigationTitle("我的账户");
		// 绑定控件ID
		username = (TextView) findViewById(R.id.username);
		user_level = (TextView) findViewById(R.id.user_level);
		userhead_info = (LinearLayout) findViewById(R.id.userhead_info);
		username_info = (LinearLayout) findViewById(R.id.username_info);
		usersex_info = (LinearLayout) findViewById(R.id.usersex_info);
		userdate_info = (LinearLayout) findViewById(R.id.userdate_info);
		ll_username = (LinearLayout) findViewById(R.id.ll_username);
		user_name = (TextView) findViewById(R.id.user_name);
		iv_personHeader = (RoundImageView) findViewById(R.id.iv_personHeader);
		user_sex = (TextView) findViewById(R.id.user_sex);
		// 点击事件监听
		userhead_info.setOnClickListener(this);
		ll_username.setOnClickListener(this);
		username_info.setOnClickListener(this);
		usersex_info.setOnClickListener(this);
		userdate_info.setOnClickListener(this);
		BitmapUtil bitmapUtil = new BitmapUtil();
		if (headimage != null) {
			bitmapUtil.displayImage(MyAccountActivity.this, iv_personHeader, headimage);
		} else {
			iv_personHeader.setImageResource(R.drawable.mine_defaulticon);
		}
		if (usernames != null) {
			user_name.setText(usernames);
		}
		if (name != null) {
			if (MyApplication.mapp.getUserInfoBean() != null) {
				username.setText(MyApplication.mapp.getUserInfoBean().getUserName());
			} else {
				username.setText(usernames);
			}
		}
		if (userLevelNm != null) {
			user_level.setText(userLevelNm);
		}
		setSex();
	}

	private void setSex() {
		if ("0".equals(sex)) {
			user_sex.setText("男");
		} else if ("1".equals(sex)) {
			user_sex.setText("女");
		} else {
			user_sex.setText("保密");
		}
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.userhead_info:
			showPopWindow();
			break;
		case R.id.username_info:
			break;
		case R.id.usersex_info:
			Intent intent = new Intent(this, UpdateSexActivity.class);
			intent.putExtra("sex", sex);
			startActivityForResult(intent, REAQUET_CODE);
			break;
		case R.id.userdate_info:
			ToastUtil.show(MyAccountActivity.this, "出生日期");
			break;
		case R.id.ll_username:
			Intent updatename = new Intent(MyAccountActivity.this, UpdateNameActivity.class);
			startActivityForResult(updatename, REAQUET_CODE);
			break;
		}
	}

	private void showPopWindow() {
		View contentView = View.inflate(this, R.layout.take_round_photo_pop_window, null);
		WindowManager manager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		@SuppressWarnings("deprecation")
		int screenWidth = manager.getDefaultDisplay().getWidth();
		int screenHeight = manager.getDefaultDisplay().getHeight();
		mPop = new PopupWindow(contentView, screenWidth, screenHeight, true);
		View view_bg = (View) contentView.findViewById(R.id.view_bg);
		TextView camera = (TextView) contentView.findViewById(R.id.take_round_photo_pop_window_camera);
		TextView albums = (TextView) contentView.findViewById(R.id.take_round_photo_pop_window_albums);
		TextView cancel = (TextView) contentView.findViewById(R.id.take_round_photo_pop_window_cancel);
		camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 打开相机拍照
				imagepath = CameraUtils.takePhoto(MyAccountActivity.this, TakePhotoConfig.PHOTOTAKE);
			}
		});
		albums.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 打开相册
				CameraUtils.openAlbums(MyAccountActivity.this, TakePhotoConfig.PHOTOZOOM);
			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mPop.dismiss();
			}
		});
		view_bg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mPop.dismiss();
			}
		});
		mPop.showAtLocation(iv_personHeader, Gravity.BOTTOM, 0, 0);
	}

	public void sendhttp(String Imagepath) {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("file", new File(Imagepath));
		showDialog("正在修改。。。");
		baseTask.askCookieRequest(SystemConfig.SAVEUSERICON, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					org.json.JSONObject jsonObject = new org.json.JSONObject(arg0.result.toString());
					if (jsonObject.getBoolean("success")) {
						dismissDialog();
						showToast("修改成功");
						Intent data = new Intent();
						Bundle bundle = new Bundle();
						bundle.putString("heade", "heade");
						data.putExtras(bundle);
						setResult(0, data);
					}
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}
			

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				showToast("修改失败");
			}
		});
	}
	

	/**
	 * 退出登录
	 */
	public void loginout() {
		BaseTask baseTask = new BaseTask(this);
		baseTask.askCookieRequest(SystemConfig.LOGOUT, null, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("退出成功", arg0.result.toString());
				try {
					org.json.JSONObject jsonObject = new org.json.JSONObject(arg0.result);
					if (jsonObject.getBoolean("success")) {
						MyApplication.mapp.clear();
						showToast("退出成功");
						finish();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("退出成功异常", arg0.toString());
				showToast("退出失败");
			}
		});
	}

	public void exit() {
		final CommonAlertDialog dialog = new CommonAlertDialog(this);
		dialog.showYesOrNoDialog("", new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loginout();
			}
		}, new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		// AlertDialog.Builder builder = new
		// AlertDialog.Builder(MyAccountActivity.this);
		// builder.setTitle("提示");
		// builder.setMessage("是否退出登录？");
		// builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
		// {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// dialog.dismiss();
		// }
		// });
		// builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
		// {
		//
		// @Override
		// public void onClick(DialogInterface arg0, int arg1) {
		// loginout();
		// }
		// });
		// builder.create().show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REAQUET_CODE) {
			switch (resultCode) {
			case RESULT_OK:
				sex = MyApplication.mapp.getUserSexCode() + "";
				setSex();
				break;
			case NAME_REAQUET_CODE:
				String strname = data.getStringExtra("b_name");
				if (strname != null) {
					username.setText(strname);
					MyApplication.mapp.getUserInfoBean().setUserName(strname);
				}
				break;
			}
		}
		CameraUtils.onActivityResult(this, requestCode, resultCode, data, iv_personHeader, mPop);
		super.onActivityResult(requestCode, resultCode, data);
	}
}
