package com.beijing.beixin.ui.myself;

import org.json.JSONException;
import org.json.JSONObject;

import com.beijing.beixin.R;
import com.beijing.beixin.R.layout;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.myself.address.AddAddressActivity;
import com.beijing.beixin.utils.ToastUtil;
import com.beijing.beixin.utils.Utils;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 修改手机验证
 * 
 * @author ouyanghao
 * 
 */
public class UpdateMobileActivity extends BaseActivity implements OnClickListener {

	private ImageView left_back;
	private ImageView iv_close;
	private TextView right_save;
	private EditText et_mobile;
	private String mobile = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_mobile);
		init();
	}

	public void init() {
		et_mobile = (EditText) findViewById(R.id.et_mobile);
		left_back = (ImageView) findViewById(R.id.left_back);
		iv_close = (ImageView) findViewById(R.id.iv_close);
		right_save = (TextView) findViewById(R.id.right_save);
		left_back.setOnClickListener(this);
		right_save.setOnClickListener(this);
		iv_close.setOnClickListener(this);
	}

	public void updateMpbile() {
		mobile = et_mobile.getText().toString().trim();
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		if ("".equals(Utils.checkStr(mobile))) {
			showToast("手机不能为空!");
			return;
		}
		if (!Utils.isMobileNO((mobile))) {
			showToast("手机号码不符合！");
			return;
		}
		params.addBodyParameter("userMobile", mobile);
		// http://b2cmob.bxmedia.net/app/user/updateUserMobile.json?userMobile=18762608660
		showDialog("正在修改。。");
		baseTask.askCookieRequest(SystemConfig.UPDATEUSERMOVILE, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject jsonObject = new JSONObject(arg0.result.toString());
					if (mobile.equals(MyApplication.mapp.getUserInfoBean().getUserMobile())) {
						dismissDialog();
						showToast(jsonObject.getString("result"));
						return;
					}
					if (jsonObject.getBoolean("success")) {
						showToast("修改成功");
						MyApplication.mapp.getUserInfoBean().setUserMobile(mobile);
						finish();
					} else {
						showToast("修改失败");
					}
					dismissDialog();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("修改手机验证异常", arg0.toString());
				showToast("修改失败");
				dismissDialog();
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.left_back:
			finish();
			break;
		case R.id.right_save:
			updateMpbile();
			break;
		case R.id.iv_close:
			et_mobile.setText("");
			break;
		}
	}
}
