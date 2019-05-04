package com.beijing.beixin.wxapi;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.myself.order.OrderActivity;
import com.beijing.beixin.ui.pay.PayOrderFailureActivity;
import com.beijing.beixin.ui.pay.PayOrderSuccessActivity;
import com.beijing.beixin.utils.ExitApplication;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 微信支付的结果工具类
 * 
 * @author 李敏
 * 
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(com.sunrise.epark.R.layout.pay_result);
		api = WXAPIFactory.createWXAPI(this, "wxd2a403b6bee8850e");
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	// 微信发送请求到第三方应用时，会回调到该方法
	@Override
	public void onReq(BaseReq req) {
	}

	// 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
	@SuppressLint("ShowToast")
	@Override
	public void onResp(BaseResp resp) {
		dismissDialog();
		int code = resp.errCode;
		finish();
		if (code == 0) {// 支付成功
			String money = ((MyApplication) getApplication()).getTotalMoney();
			Intent intent = new Intent(WXPayEntryActivity.this, PayOrderSuccessActivity.class);
			intent.putExtra("money", money);
			startActivity(intent);
		} else if (code == -1) {
			// 失败
			String money = ((MyApplication) getApplication()).getTotalMoney();
			Intent intent = new Intent(WXPayEntryActivity.this, PayOrderFailureActivity.class);
			intent.putExtra("money", money);
			startActivity(intent);
		} else if (code == -2) {
			// 用户取消
			Toast.makeText(WXPayEntryActivity.this, "用户取消", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(WXPayEntryActivity.this, OrderActivity.class);
			startActivity(intent);
		}
	}
}