package com.beijing.beixin.ui.shoppingcart;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.beijing.beixin.R;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.utils.DialogUtil;
import com.beijing.beixin.utils.alipay.AliPayInterface;
import com.beijing.beixin.utils.alipay.AliPayUtils;
import com.beijing.beixin.utils.uupay.UPPayUtils;

public class PayActivity extends BaseActivity implements Callback, OnClickListener {
	private Context mContext;
	/**
	 * 银联TN号（即交易流水号，需从服务器获取）
	 */
	private String tn = null;
	/**
	 * Handler
	 */
	private Handler mHandler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		mContext = this;
		init();
	}

	private void init() {
		Button pay = (Button) findViewById(R.id.pay);
		Button uupay = (Button) findViewById(R.id.uupay);
		Button weixinpay = (Button) findViewById(R.id.weixinpay);
		mHandler = new Handler(this);
		pay.setOnClickListener(this);
		uupay.setOnClickListener(this);
		weixinpay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 微信支付
				DialogUtil.showToast(mContext, "需要配置商户号等信息才能调起支付");
				// 配置对应的商户号信息后直接调用WXPay()
				// WXPay();
			}
		});
	}

	/*
	 * 发送请求，获取服务器返回的TN号（此URL是官网地址）
	 */
	class Runing implements Runnable {

		@Override
		public void run() {
			InputStream is;
			try {
				String url = "http://101.231.204.84:8091/sim/getacptn";
				URL myURL = new URL(url);
				URLConnection ucon = myURL.openConnection();
				ucon.setConnectTimeout(120000);
				is = ucon.getInputStream();
				int i = -1;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ((i = is.read()) != -1) {
					baos.write(i);
				}
				tn = baos.toString();
				is.close();
				baos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Message msg = mHandler.obtainMessage();
			msg.obj = tn;
			mHandler.sendMessage(msg);
		}
	}

	/*
	 * 处理TN号是否获取成功，成功则启动银联支付控件
	 */
	@Override
	public boolean handleMessage(Message msg) {
		if (msg.obj == null || ((String) msg.obj).length() == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			builder.setTitle("错误提示");
			builder.setMessage("网络连接失败,请重试!");
			builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.create().show();
		} else {
			UPPayUtils.doStartUnionPayPluginapk(PayActivity.this, tn, UPPayUtils.mMode);
		}
		return false;
	}

	/*
	 * 银联支付结果回调(必须重写此方法来接收银联返回结果)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// 银联支付回调结果（1成功，2取消支付,3失败）
		if (UPPayUtils.getUPPayResltDatas(mContext, data) == UPPayUtils.UPPAY_RESULT_SUCC) {
			// 支付成功
			Toast.makeText(mContext, "支付成功", 0).show();
		} else if (UPPayUtils.getUPPayResltDatas(mContext, data) == UPPayUtils.UPPAY_RESULT_FAIL) {
			// 支付失败
			Toast.makeText(mContext, "支付失败", 0).show();
		} else if (UPPayUtils.getUPPayResltDatas(mContext, data) == UPPayUtils.UPPAY_RESULT_CLOSE) {
			// 取消支付
			Toast.makeText(mContext, "取消支付", 0).show();
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.pay:
			AliPayUtils payUtils = new AliPayUtils(PayActivity.this);
			payUtils.GoPay("商品", "商品详情", "0.01");
			payUtils.setPayInterface(new AliPayInterface() {

				@Override
				public void paySuccess() {
					// TODO Auto-generated method stub
				}

				@Override
				public void payProgress() {
					// TODO Auto-generated method stub
				}

				@Override
				public void payError() {
					// TODO Auto-generated method stub
				}

				@Override
				public void payCancel() {
					// TODO Auto-generated method stub

				}
			});
			break;
		case R.id.uupay:
			Runing runing = new Runing();
			Thread t = new Thread(runing);
			t.start();
			break;
		}
	}

	/*
	 * 调微信支付
	 */
	// private void WXPay() {
	// // 微信 "XXXX"换成在商户平台设置 微信回调(后台给的接口)
	// WXPayMain wxPayMain = new WXPayMain(this,
	// "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android");
	// // orderId每次支付都需不同---拼接的格式要与后台一致
	// String order = wxPayMain.random();// 此方法的调用可从后台得到
	// StringBuffer orderId = new StringBuffer(order);
	// orderId.append(("_0_" + (int) (0.01f * 100)));// 拼接的支付金额
	// orderId.append("_00");// 拼接的支付类型
	// // 给附加字段赋值
	// wxPayMain.setOut_trade_no(orderId.toString());
	// wxPayMain.setBody("微信支付");// 商品或支付单简要描述
	// wxPayMain.setAttach(orderId.toString());
	// // 直接调(参数是支付的总金额)
	// wxPayMain.pay((int) (0.01f * 100));
	// }

}
