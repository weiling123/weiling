package com.beijing.beixin.ui.pay;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.beixin.R;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.myself.order.OrderActivity;
import com.beijing.beixin.utils.ToastUtil;
import com.beijing.beixin.utils.alipay.AliPayInterface;
import com.beijing.beixin.utils.alipay.AliPayUtils;
import com.beijing.beixin.utils.uupay.UPPayUtils;
import com.beijing.beixin.wxapi.WXPayEntryActivity;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 收银台
 * 
 * @author ouyanghao
 * 
 */
@SuppressWarnings("unused")
public class CheckstandActivity extends BaseActivity implements OnClickListener, Callback {

	private ImageView left_back;
	private TextView right_save;
	/**
	 * 支付金额
	 */
	private TextView pay_money;
	/**
	 * 微信支付
	 */
	private LinearLayout ll_weixin;
	/**
	 * 银行卡
	 */
	private LinearLayout ll_unionpay;
	/**
	 * 支付宝
	 */
	private LinearLayout ll_alipay;
	/**
	 * 其它支付方式
	 */
	private LinearLayout ll_other;
	/**
	 * 银联TN号（即交易流水号，需从服务器获取）
	 */
	private String tn = null;
	/**
	 * Handler
	 */
	private Handler mHandler = null;
	/**
	 * 应付金额
	 */
	private String orderId;
	private String orders;
	private String productmoney;
	private String paymentDocumentId = "";
	/**
	 * 微信支付相关内容
	 */
	private PayReq req;
	private IWXAPI msgApi;
	// 微信回调
	private String WX_RESPONSE = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkstand);
		msgApi = WXAPIFactory.createWXAPI(this, "wxd2a403b6bee8850e");
		msgApi.registerApp("wxd2a403b6bee8850e");
		req = new PayReq();
		init();
	}

	/**
	 * 绑定控件ID及监听
	 */
	public void init() {
		Intent intent = getIntent();
		productmoney = intent.getStringExtra("productmoney");
		if (intent.getStringExtra("paymentDocumentId") != null) {
			paymentDocumentId = intent.getStringExtra("paymentDocumentId");
		}
		pay_money = (TextView) findViewById(R.id.pay_money);
		pay_money.setText(productmoney);
		ll_weixin = (LinearLayout) findViewById(R.id.ll_weixin);
		ll_unionpay = (LinearLayout) findViewById(R.id.ll_unionpay);
		ll_alipay = (LinearLayout) findViewById(R.id.ll_alipay);
		ll_other = (LinearLayout) findViewById(R.id.ll_other);
		left_back = (ImageView) findViewById(R.id.left_back);
		right_save = (TextView) findViewById(R.id.right_save);
		ll_weixin.setOnClickListener(this);
		ll_unionpay.setOnClickListener(this);
		ll_alipay.setOnClickListener(this);
		ll_other.setOnClickListener(this);
		left_back.setOnClickListener(this);
		right_save.setOnClickListener(this);
		mHandler = new Handler(this);
		orderId = getIntent().getStringExtra("orderId");
		if (orderId != null) {
			getCharge(orderId);
		}
	}

	/**
	 * 支付类点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_weixin:
			boolean sIsWXAppInstalledAndSupported = msgApi.isWXAppInstalled() && msgApi.isWXAppSupportAPI();
			if (sIsWXAppInstalledAndSupported == false) {
				showToast("您还未安装微信，请安装");
				return;
			}
			showDialog("正在调用微信支付");
			if ("".equals(paymentDocumentId)) {
				getPaymentDocumentId(orderId, "26");
			} else {
				weixinPay(paymentDocumentId);
			}
			break;
		case R.id.ll_unionpay:
			showToast("当前版本暂不支持");
			// Runing runing = new Runing();
			// Thread t = new Thread(runing);
			// t.start();
			break;
		case R.id.ll_alipay:
			// Alipay();
			if ("".equals(paymentDocumentId)) {
				getPaymentDocumentId(orderId, "25");
			} else {
				aliPay(paymentDocumentId);
			}
			break;
		case R.id.ll_other:
			showToast("当前版本暂不支持");
			break;
		case R.id.left_back:
			finish();
			break;
		case R.id.right_save:
			startActivity(OrderActivity.class);
			break;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		dismissDialog();
	}

	/**
	 * 查询收银台支付信息
	 */
	public void getCharge(String orderIds) {
		showDialog("正在查询收银台支付信息");
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("orderIds", orderIds);
		params.addBodyParameter("clearingType", "0");
		params.addBodyParameter("paymentTypeCode", "");
		baseTask.askCookieRequest(SystemConfig.GET_CHARGE, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("getCharge", arg0.result.toString());
				JSONObject obj;
				try {
					dismissDialog();
					obj = new JSONObject(arg0.result.toString());
					if (obj.get("result") != null) {
						dismissDialog();
					}
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(CheckstandActivity.this, "获取失败");
			}
		});
	}

	/**
	 * 创建流水账单,0 支付宝，1积分，2货到付款，3银联,5预存款,25支付宝支付，26微信支付
	 */
	public void getPaymentDocumentId(String orderIds, final String paymentTypeCode) {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("orderIds", orderIds);
		params.addBodyParameter("clearingType", "0");
		params.addBodyParameter("paymentTypeCode", paymentTypeCode);
		baseTask.askCookieRequest(SystemConfig.GET_PAYMENT, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("getPaymentDocumentId", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					if (obj.get("result") != null) {
						if ("25".equals(paymentTypeCode)) {
							aliPay(obj.get("result").toString());
						} else if ("26".equals(paymentTypeCode)) {
							weixinPay(obj.get("result").toString());
						}
					}
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(CheckstandActivity.this, "获取失败");
			}
		});
	}

	/**
	 * 调用接口，使用微信支付
	 */
	public void weixinPay(String paymentDocumentId) {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("paymentDocumentId", paymentDocumentId);
		baseTask.askCookieRequest(SystemConfig.WEIXIN_PAY, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("weixin", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					if (obj.get("sign") != null) {
						req.sign = obj.getString("sign");
					}
					if (obj.get("appid") != null) {
						req.appId = obj.getString("appid").toString();
					}
					if (obj.get("partnerid") != null) {
						req.partnerId = obj.getString("partnerid").toString();
					}
					if (obj.get("prepayid") != null) {
						req.prepayId = obj.getString("prepayid").toString();
					}
					if (obj.get("package") != null) {
						req.packageValue = obj.getString("package").toString();
					}
					if (obj.get("noncestr") != null) {
						req.nonceStr = obj.getString("noncestr").toString();
					}
					if (obj.get("timestamp") != null) {
						req.timeStamp = obj.getString("timestamp").toString();
					}
					msgApi.sendReq(req);
					finish();
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(CheckstandActivity.this, "用预存款失败");
			}
		});
	}

	/**
	 * 调用接口，使用支付宝支付
	 */
	public void aliPay(String order_no) {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("order_no", order_no);
		baseTask.askCookieRequest(SystemConfig.ALIPAY, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("alipay", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					String sign = obj.getJSONObject("result").getString("sign");
					String orderInfo = obj.getJSONObject("result").getString("orderInfo");
					AliPayUtils payUtils = new AliPayUtils(CheckstandActivity.this);
					payUtils.GoPay(sign, orderInfo);
					payUtils.setPayInterface(new AliPayInterface() {

						@Override
						public void paySuccess() {
							String money = ((MyApplication) getApplication()).getTotalMoney();
							Intent intent = new Intent(CheckstandActivity.this, PayOrderSuccessActivity.class);
							intent.putExtra("money", money);
							startActivity(intent);
							finish();
						}

						@Override
						public void payProgress() {
						}

						@Override
						public void payCancel() {
						}

						@Override
						public void payError() {
							String money = ((MyApplication) getApplication()).getTotalMoney();
							Intent intent = new Intent(CheckstandActivity.this, PayOrderFailureActivity.class);
							intent.putExtra("money", money);
							startActivity(intent);
							finish();
						}
					});
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("alipay", "fail");
				dismissDialog();
				ToastUtil.show(CheckstandActivity.this, "支付宝支付失败");
			}
		});
	}

	/**
	 * 支付宝
	 */
	// public void Alipay() {
	// AliPayUtils payUtils = new AliPayUtils(CheckstandActivity.this);
	// payUtils.GoPay("商品", "商品详情", "0.01");
	// payUtils.setPayInterface(new AliPayInterface() {
	//
	// @Override
	// public void paySuccess() {
	// // TODO Auto-generated method stub
	// }
	//
	// @Override
	// public void payProgress() {
	// // TODO Auto-generated method stub
	// }
	//
	// @Override
	// public void payError() {
	// // TODO Auto-generated method stub
	// startActivity(PayOrderSuccessActivity.class);
	// }
	// });
	// }

	/**
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

	/**
	 * 启动银联控件
	 */
	@Override
	public boolean handleMessage(Message msg) {
		if (msg.obj == null || ((String) msg.obj).length() == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(CheckstandActivity.this);
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
			UPPayUtils.doStartUnionPayPluginapk(CheckstandActivity.this, tn, UPPayUtils.mMode);
		}
		return false;
	}

	/**
	 * 银联支付结果回调(必须重写此方法来接收银联返回结果)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// 银联支付回调结果（1成功，2取消支付,3失败）
		if (UPPayUtils.getUPPayResltDatas(CheckstandActivity.this, data) == UPPayUtils.UPPAY_RESULT_SUCC) {
			// 支付成功
			Toast.makeText(CheckstandActivity.this, "支付成功", 0).show();
		} else if (UPPayUtils.getUPPayResltDatas(CheckstandActivity.this, data) == UPPayUtils.UPPAY_RESULT_FAIL) {
			// 支付失败
			Toast.makeText(CheckstandActivity.this, "支付失败", 0).show();
		} else if (UPPayUtils.getUPPayResltDatas(CheckstandActivity.this, data) == UPPayUtils.UPPAY_RESULT_CLOSE) {
			// 取消支付
			// Toast.makeText(CheckstandActivity.this, "取消支付", 0).show();
			startActivity(PayOrderSuccessActivity.class);
		}
	}
}
