package com.beijing.beixin.ui.pay;

import org.json.JSONException;
import org.json.JSONObject;

import com.beijing.beixin.R;
import com.beijing.beixin.R.layout;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.myself.order.OrderActivity;
import com.beijing.beixin.ui.shoppingcart.SubmitOrderActivity;
import com.beijing.beixin.utils.ExitApplication;
import com.beijing.beixin.utils.MD5;
import com.beijing.beixin.utils.ToastUtil;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.R.integer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 余额支付
 * 
 * @author ouyanghao
 * 
 */
@SuppressWarnings("unused")
public class BalancePayActivity extends BaseActivity implements OnClickListener {

	private TextView product_money;
	private TextView balance_pay_money;
	private EditText pwd;
	private EditText pwd2;
	private String mymoney;
	private String productmoney;
	private String orderId;
	private String prestorePayAmount;
	private Button sure_pay;
	public long exitTime;// 储存点击退出时间
	private String pm;
	private String mm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balance_pay);
		init();
	}

	public void init() {
		setNavigationTitle("余额支付");
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		Intent intent = getIntent();
		mymoney = intent.getStringExtra("mymoney");
		productmoney = intent.getStringExtra("productmoney");
		orderId = intent.getStringExtra("orderId");
		product_money = (TextView) findViewById(R.id.product_money);
		balance_pay_money = (TextView) findViewById(R.id.balance_pay_money);
		pwd = (EditText) findViewById(R.id.pwd);
		pwd2 = (EditText) findViewById(R.id.pwd2);
		sure_pay = (Button) findViewById(R.id.sure_pay);
		sure_pay.setOnClickListener(this);
		setText();
	}

	public void setText() {
		pm = productmoney.replace("¥", "");
		mm = mymoney.replace("¥", "");
		if (Double.parseDouble(pm) <= Double.parseDouble(mm)) {
			balance_pay_money.setText("¥" + pm);
		} else {
			balance_pay_money.setText("¥" + mm);
		}
		product_money.setText("¥" + pm);
	}

	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		Intent intent1 = new Intent(BalancePayActivity.this, OrderActivity.class);
		intent1.putExtra("from", "balance");
		intent1.putExtra("state", "1");
		startActivity(intent1);
		finish();
	}

	/**
	 * 查询收银台支付信息
	 */
	public void getCharge(String orderIds) {
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
					// dismissDialog();
					obj = new JSONObject(arg0.result.toString());
					if (obj.get("result") != null) {
						prestorePayAmount = new JSONObject(obj.get("result").toString()).getString("cashPayTotal");
						getPaymentDocumentId(orderId);
					}
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(BalancePayActivity.this, "获取失败");
			}
		});
	}

	/**
	 * 创建流水账单,0 支付宝，1积分，2货到付款，3银联,5预存款
	 */
	public void getPaymentDocumentId(String orderIds) {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("orderIds", orderIds);
		params.addBodyParameter("clearingType", "0");
		params.addBodyParameter("paymentTypeCode", "5");
		baseTask.askCookieRequest(SystemConfig.GET_PAYMENT, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("getPaymentDocumentId", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					if (obj.get("result") != null) {
						prestorePay(obj.get("result").toString());
					}
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(BalancePayActivity.this, "获取失败");
			}
		});
	}

	/**
	 * 调用接口，使用预存款
	 */
	public void prestorePay(String paymentDocumentId) {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("paymentDocumentId", paymentDocumentId);
		params.addBodyParameter("prestorePayAmount", prestorePayAmount);
		String MD5PWD = MD5.MD5Encode(pwd.getText().toString());
		params.addBodyParameter("payPsw", MD5PWD);
		baseTask.askCookieRequest(SystemConfig.PRESTORE_PAY, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("prestorePay", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					if (obj.get("result") != null) {
						String paymentDocumentId = new JSONObject(obj.get("result").toString())
								.getString("paymentDocumentId");
						if (Double.parseDouble(pm) <= Double.parseDouble(mm)) {
							prestoreFullPay(paymentDocumentId);
						} else {
							Intent intent = new Intent(BalancePayActivity.this, CheckstandActivity.class);
							intent.putExtra("productmoney", (Double.parseDouble(pm) - Double.parseDouble(mm)) + "");
							intent.putExtra("paymentDocumentId", paymentDocumentId);
							startActivity(intent);
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
				ToastUtil.show(BalancePayActivity.this, "用预存款失败");
			}
		});
	}

	/**
	 * 预存款全额支付
	 */
	public void prestoreFullPay(String paymentDocumentId) {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("paymentDocumentId", paymentDocumentId);
		baseTask.askCookieRequest(SystemConfig.PRESTOREFULLPAY, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("prestoreFullPay", arg0.result.toString());
				JSONObject obj;
				try {
					dismissDialog();
					obj = new JSONObject(arg0.result.toString());
					if (obj != null) {
						showToast("支付成功");
						dismissDialog();
						Intent intent = new Intent(BalancePayActivity.this, PayOrderSuccessActivity.class);
						intent.putExtra("money", product_money.getText().toString());
						startActivity(intent);
						finish();
					}
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(BalancePayActivity.this, "获取失败");
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sure_pay:
			if ("".equals(pwd.getText().toString())) {
				showToast("请输入支付密码！");
				return;
			}
			showDialog("正在请求数据，请稍后...");
			getCharge(orderId);
			break;
		}
	}

	/**
	 * 再按一次退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent intent1 = new Intent(BalancePayActivity.this, OrderActivity.class);
			intent1.putExtra("state", "1");
			intent1.putExtra("from", "balance");
			startActivity(intent1);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
