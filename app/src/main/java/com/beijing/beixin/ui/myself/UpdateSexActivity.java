package com.beijing.beixin.ui.myself;

import org.json.JSONException;
import org.json.JSONObject;

import com.beijing.beixin.R;
import com.beijing.beixin.R.layout;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.utils.ToastUtil;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 修改性别
 * 
 * @author ouyanghao
 * 
 */
@SuppressWarnings("unused")
public class UpdateSexActivity extends BaseActivity implements OnClickListener {

	/**
	 * 男
	 */
	private ImageView boy;
	/**
	 * 女
	 */
	private ImageView girl;
	/**
	 * 保密
	 */
	private ImageView secret;
	/**
	 * 返回
	 */
	private ImageView left_back;
	/**
	 * 保存
	 */
	private TextView right_save;
	private String mType = null;
	private String sex = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_sex);
		init();
	}

	public void init() {
		sex = getIntent().getStringExtra("sex");
		left_back = (ImageView) findViewById(R.id.left_back);
		right_save = (TextView) findViewById(R.id.right_save);
		boy = (ImageView) findViewById(R.id.select_boy);
		girl = (ImageView) findViewById(R.id.select_girl);
		secret = (ImageView) findViewById(R.id.select_secret);
		left_back.setOnClickListener(this);
		right_save.setOnClickListener(this);
		boy.setOnClickListener(this);
		girl.setOnClickListener(this);
		secret.setOnClickListener(this);
		setImageState();
		if ("0".equals(sex)) {
			boy.setImageResource(R.drawable.select_r);
			mType = "0";
		} else if ("1".equals(sex)) {
			girl.setImageResource(R.drawable.select_r);
			mType = "1";
		} else {
			secret.setImageResource(R.drawable.select_r);
			mType = "2";
		}
	}

	public void setImageState() {
		boy.setImageResource(R.drawable.select_g);
		girl.setImageResource(R.drawable.select_g);
		secret.setImageResource(R.drawable.select_g);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.select_boy:
			setImageState();
			boy.setImageResource(R.drawable.select_r);
			mType = "0";
			break;
		case R.id.select_girl:
			setImageState();
			girl.setImageResource(R.drawable.select_r);
			mType = "1";
			break;
		case R.id.select_secret:
			setImageState();
			secret.setImageResource(R.drawable.select_r);
			mType = "2";
			break;
		case R.id.left_back:
			setResult(RESULT_OK);
			finish();
			break;
		case R.id.right_save:
			updateSex(mType);
			break;
		}
	}

	/**
	 * 修改性别 1女，2保密，0男
	 * 
	 * @param typesex
	 */
	public void updateSex(String typesex) {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("userSexCode", typesex);
		showDialog("正在修改。。。");
		baseTask.askCookieRequest(SystemConfig.UPDATEUSERSEXCODE, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject = new JSONObject(arg0.result.toString());
					if (jsonObject.getBoolean("result")) {
						MyApplication.mapp.setUserSexCode(Integer.parseInt(mType));
						setImageState();
						if ("0".equals(mType)) {
							boy.setImageResource(R.drawable.select_r);
						} else if ("1".equals(mType)) {
							girl.setImageResource(R.drawable.select_r);
						} else {
							secret.setImageResource(R.drawable.select_r);
						}
						setResult(RESULT_OK);
						finish();
					}
					dismissDialog();
				} catch (JSONException e) {
					dismissDialog();
					Log.e("修改异常", arg0.toString());
					showToast("修改失败");
					e.printStackTrace();
				} catch (Exception e) {
					dismissDialog();
					Log.e("修改异常", arg0.toString());
					showToast("修改失败");
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("修改失败", arg0.toString());
				dismissDialog();
				showToast("修改失败");
			}
		});
	}

	@Override
	public void onBackPressed() {
		setResult(RESULT_OK);
		super.onBackPressed();
	}
}
