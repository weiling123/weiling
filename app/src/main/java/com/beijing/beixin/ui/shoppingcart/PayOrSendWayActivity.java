package com.beijing.beixin.ui.shoppingcart;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.SendWayAdapter;
import com.beijing.beixin.adapter.ViewHolder;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.PayWayBean;
import com.beijing.beixin.pojo.SendWayBean;
import com.beijing.beixin.pojo.ShoppingCartBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.utils.MyGridView;
import com.beijing.beixin.utils.ToastUtil;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class PayOrSendWayActivity extends BaseActivity implements OnClickListener {

	/**
	 * 支付方式的gridview
	 */
	private MyGridView gridView_pay_way;
	/**
	 * 支付方式
	 */
	private String payWay = "";
	/**
	 * 配送方式
	 */
	private String sendWay = "";
	private String way = "";
	private SendWayAdapter sendWayAdapter;
	private String orgId = "";
	private TextView textview_name;
	private TextView textview_sendrule;
	private TextView textview_watch_rule;
	private int position;

	private boolean mIsGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payorsend);
		initView();
		initData();
		setListener();
	}

	private void initView() {
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		textview_name = (TextView) findViewById(R.id.textview_name);
		textview_sendrule = (TextView) findViewById(R.id.textview_sendrule);
		textview_watch_rule = (TextView) findViewById(R.id.textview_watch_rule);
		textview_watch_rule.setOnClickListener(this);
		gridView_pay_way = (MyGridView) findViewById(R.id.gridView_send_way);
		showDialog("正在请求数据，请稍后...");
	}

	private void initData() {
		way = getIntent().getStringExtra("way");
		orgId = getIntent().getStringExtra("orgId");
		position = getIntent().getIntExtra("position", 0);
		mIsGroup = getIntent().getBooleanExtra("isGroup", false);
		if ("pay".equals(way)) {
			setNavigationTitle("支付方式");
			textview_name.setText("支付方式");
			getPayType(orgId);
			textview_watch_rule.setVisibility(View.INVISIBLE);
		} else {
			setNavigationTitle("配送方式");
			textview_name.setText("配送方式");
			getSendType(orgId);
			textview_watch_rule.setVisibility(View.VISIBLE);
		}
	}

	private void setListener() {
		gridView_pay_way.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView textview = (TextView) view.findViewById(R.id.textview_pay_way);
				String payOrSendId = textview.getTag() + "";
				if ("pay".equals(way)) {
					payWay = textview.getText().toString();
					savePayWay(orgId, payOrSendId);
				} else {
					sendWay = textview.getText().toString();
					saveSendWay(orgId, payOrSendId);
				}
			}
		});
	}

	/**
	 * 支付方式
	 */
	public void getPayType(String orgId) {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", "normal");
		params.addBodyParameter("orgId", orgId + "");
		baseTask.askCookieRequest(SystemConfig.PAYWAY_LIST, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				dismissDialog();
				Log.e("payWay", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					List<PayWayBean> list = JSON.parseArray(obj.get("result").toString(), PayWayBean.class);
					CommonAdapter<PayWayBean> adapter = new CommonAdapter<PayWayBean>(PayOrSendWayActivity.this, list,
							R.layout.item_send_way_gridview) {

						@Override
						public void convert(ViewHolder helper, PayWayBean item) {
							helper.setText(R.id.textview_pay_way, item.getPayWayName());
							helper.setTag(R.id.textview_pay_way, item.getPayWayTypeCode());
						}
					};
					gridView_pay_way.setAdapter(adapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(PayOrSendWayActivity.this, "获取支付方式失败");
			}
		});
	}

	/**
	 * 配送方式
	 */
	public void getSendType(String orgId) {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		if (mIsGroup) {
			params.addBodyParameter("handler", "groupBuy");
			params.addBodyParameter("type", "groupBuy");
			params.addBodyParameter("isCod", "N");
			params.addBodyParameter("orgId", orgId + "");
		} else {
			params.addBodyParameter("type", "normal");
			params.addBodyParameter("orgId", orgId + "");
		}
		baseTask.askCookieRequest(SystemConfig.SEND_LIST, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				dismissDialog();
				Log.e("sendWay", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					List<SendWayBean> list = JSON.parseArray(obj.get("result").toString(), SendWayBean.class);
					String rule = "";
					for (int i = 0; i < list.size(); i++) {
						String wayRule = list.get(i).getWayRules().replace("\\n", "\n");
						rule = rule + wayRule + "\n\n";
					}
					textview_sendrule.setText(rule);
					CommonAdapter<SendWayBean> adapter = new CommonAdapter<SendWayBean>(PayOrSendWayActivity.this, list,
							R.layout.item_send_way_gridview) {

						@Override
						public void convert(ViewHolder helper, SendWayBean item) {
							helper.setText(R.id.textview_pay_way, item.getDeliveryRuleNm());
							helper.setTag(R.id.textview_pay_way, item.getDeliveryRule().getDeliveryRuleId() + "");
						}
					};
					gridView_pay_way.setAdapter(adapter);
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(PayOrSendWayActivity.this, "获取配送方式失败");
			}
		});
	}

	/**
	 * 支付方式选择
	 */
	public void savePayWay(String orgId, String payWayTypeCode) {
		showDialog("选择支付方式。。。");
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", "normal");
		params.addBodyParameter("orgId", orgId + "");
		params.addBodyParameter("payWayTypeCode", payWayTypeCode);
		baseTask.askCookieRequest(SystemConfig.SAVE_PAY_WAY, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("submitOrder", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					if (obj.getBoolean("success")) {
						dismissDialog();
						Intent intent = new Intent();
						Bundle bundle = new Bundle();
						bundle.putString("payway", payWay);
						bundle.putInt("position", position);
						intent.putExtras(bundle);
						setResult(1, intent);
						finish();
					}
				} catch (JSONException e) {
					e.printStackTrace();
					dismissDialog();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(PayOrSendWayActivity.this, "支付方式选择失败");
			}
		});
	}

	/**
	 * 配送方式选择
	 */
	public void saveSendWay(String orgId, String deliveryRuleId) {
		showDialog("选择配送方式。。。");
		final BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		if (mIsGroup) {
			params.addBodyParameter("type", "groupBuy");
		} else {
			params.addBodyParameter("type", "normal");
		}
		params.addBodyParameter("orgId", orgId + "");
		params.addBodyParameter("deliveryRuleId", deliveryRuleId + "");
		baseTask.askCookieRequest(SystemConfig.SAVE_SEND_WAY, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("submitOrder", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					ShoppingCartBean bean = JSON.parseObject(obj.get("result").toString(), ShoppingCartBean.class);
					if (bean != null && bean.getShoppingCartVos() != null
							&& bean.getShoppingCartVos().get(0).getItems() != null) {
						dismissDialog();
						Intent intent = new Intent();
						Bundle bundle = new Bundle();
						bundle.putString("sendway", sendWay);
						bundle.putInt("position", position);
						bundle.putSerializable("shoppingCartBean", bean);
						intent.putExtras(bundle);
						setResult(1, intent);
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
				ToastUtil.show(PayOrSendWayActivity.this, "支付方式选择失败");
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_sure:
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("sendway", sendWayAdapter.sendWay);
			intent.putExtras(bundle);
			setResult(1, intent);
			finish();
			break;
		case R.id.textview_watch_rule:
			if ("查看运费规则".equals(textview_watch_rule.getText().toString())) {
				textview_watch_rule.setText("收起运费规则");
				textview_sendrule.setVisibility(View.VISIBLE);
			} else {
				textview_watch_rule.setText("查看运费规则");
				textview_sendrule.setVisibility(View.INVISIBLE);
			}
			break;
		default:
			break;
		}
	}
}
