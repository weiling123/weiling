package com.beijing.beixin.ui.myself;

import org.json.JSONException;
import org.json.JSONObject;

import com.beijing.beixin.R;
import com.beijing.beixin.R.layout;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
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
 * 修改昵称
 * 
 * @author ouyanghao
 * 
 */
@SuppressWarnings("unused")
public class UpdateNameActivity extends BaseActivity implements OnClickListener {

	private ImageView left_back;
	private ImageView iv_close;
	private TextView right_save;
	private EditText et_username;
	private String name = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_name);
		init();
	}

	public void init() {
		et_username = (EditText) findViewById(R.id.et_username);
		left_back = (ImageView) findViewById(R.id.left_back);
		iv_close = (ImageView) findViewById(R.id.iv_close);
		right_save = (TextView) findViewById(R.id.right_save);
		left_back.setOnClickListener(this);
		right_save.setOnClickListener(this);
		iv_close.setOnClickListener(this);
	}

	public void updateName() {
		name = et_username.getText().toString().trim();
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		if ("".equals(Utils.checkStr(name))) {
			showToast("名称不能为空!");
			return;
		}
		params.addBodyParameter("userName", name);
		showDialog("正在修改。。");
		baseTask.askCookieRequest(SystemConfig.UPDATEUSERNAME, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject jsonObject = new JSONObject(arg0.result.toString());
					if (jsonObject.getBoolean("result")) {
						showToast("修改成功");
						Intent intent = new Intent();
						Bundle bundle = new Bundle();
						bundle.putString("b_name", name);
						intent.putExtras(bundle);
						setResult(MyAccountActivity.NAME_REAQUET_CODE, intent);
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
				Log.e("修改昵称异常", arg0.toString());
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
			updateName();
			break;
		case R.id.iv_close:
			et_username.setText("");
			break;
		}
	}
}
