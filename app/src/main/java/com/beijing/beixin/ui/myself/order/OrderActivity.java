package com.beijing.beixin.ui.myself.order;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.ListItemClickHelp;
import com.beijing.beixin.adapter.OrderListAdapter;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.ClassifyRightBean;
import com.beijing.beixin.pojo.DataShop;
import com.beijing.beixin.pojo.OrderListBean;
import com.beijing.beixin.pojo.OrderListBean.AppOrderItemVo;
import com.beijing.beixin.pojo.SuccessBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.MainActivity;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.myself.MyAccountActivity;
import com.beijing.beixin.ui.myself.login.LoginActivity;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.ui.shoppingcart.ShopcartActivity;
import com.beijing.beixin.utils.ExitApplication;
import com.beijing.beixin.utils.ToastUtil;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

@SuppressWarnings("unused")
public class OrderActivity extends BaseActivity implements ListItemClickHelp, OnClickListener {

	private static final String ZONE = "0";// 全部订单
	private static final String ONE = "1";// 待付款
	private static final String TWO = "2";// 待发货
	private static final String THREE = "3";// 待收货
	private static final String FOUR = "4";// 待评价
	public static final int PAYING_ORDER = 2001;
	private ListView listview;
	private List<OrderListBean> orderListBeans = new ArrayList<OrderListBean>();
	private OrderListAdapter adapter;
	private String state;
	/**
	 * 全部
	 */
	private TextView tv_comprehensive;
	/**
	 * 待付款
	 */
	private TextView tv_paying;
	/**
	 * 待收货
	 */
	private TextView tv_goodsing;
	/**
	 * 待发货
	 */
	private TextView tv_sinceing;
	/**
	 * 待评价
	 */
	private TextView tv_contexting;
	private int i = 1;
	private int j = 1;
	private int k = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		initView();
		getStateView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if ("success".equals(((MyApplication) getApplication()).getTotalMoney())) {
			getStateView();
		}
	}

	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		if ("balance".equals(getIntent().getStringExtra("from"))) {
			Intent intent = new Intent(OrderActivity.this, MainActivity.class);
			intent.putExtra("cart", "myself");
			startActivity(intent);
		}
		finish();
	}

	/**
	 * 再按一次退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if ("balance".equals(getIntent().getStringExtra("from"))) {
				Intent intent = new Intent(OrderActivity.this, MainActivity.class);
				intent.putExtra("cart", "myself");
				startActivity(intent);
			}
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 订单状态
	 */
	public void getStateView() {
		Intent intent = getIntent();
		state = intent.getStringExtra("state");
		i = 1;
		j = 1;
		k = 1;
		if ("1".equals(state)) {
			j++;
			tv_paying.setTextColor(Color.parseColor("#E5350D"));
			sendhttp(state);
		} else if ("2".equals(state)) {
			tv_sinceing.setTextColor(Color.parseColor("#E5350D"));
			sendhttp(state);
		} else if ("3".equals(state)) {
			k++;
			tv_goodsing.setTextColor(Color.parseColor("#E5350D"));
			sendhttp(state);
		} else if ("4".equals(state)) {
			tv_contexting.setTextColor(Color.parseColor("#E5350D"));
			sendhttp(state);
		} else {
			i++;
			tv_comprehensive.setTextColor(Color.parseColor("#E5350D"));
			sendhttp(ZONE);
		}
	}

	private void initView() {
		setNavigationTitle("我的订单");
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		tv_comprehensive = (TextView) findViewById(R.id.tv_comprehensive);
		tv_paying = (TextView) findViewById(R.id.tv_paying);
		tv_goodsing = (TextView) findViewById(R.id.tv_goodsing);
		tv_sinceing = (TextView) findViewById(R.id.tv_sinceing);
		tv_contexting = (TextView) findViewById(R.id.tv_contexting);
		tv_comprehensive.setOnClickListener(this);
		tv_paying.setOnClickListener(this);
		tv_goodsing.setOnClickListener(this);
		tv_sinceing.setOnClickListener(this);
		tv_contexting.setOnClickListener(this);
		initListView();
	}

	private void initListView() {
		listview = (ListView) findViewById(R.id.listview);
		adapter = new OrderListAdapter(this, this);
		adapter.setData(orderListBeans);
		listview.setAdapter(adapter);
	}

	public void sendhttp(String orderType) {
		sendhttp(orderType, false);
	}

	/**
	 * 查询订单状态
	 * 
	 * @param orderType
	 */
	public void sendhttp(String orderType, final boolean isDismis) {
		showDialog("加载数据中。。。");
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("orderType", orderType);
		params.addBodyParameter("pageNumber", "1");
		baseTask.askCookieRequest(SystemConfig.ORDER_LIST, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("OrderActivity", arg0.result.toString());
				try {
					JSONObject jsonObject = new JSONObject(arg0.result);
					JSONArray array = jsonObject.getJSONArray("result");
					orderListBeans = JSON.parseArray(array.toString(), OrderListBean.class);
					if (orderListBeans.size() != 0) {
						adapter.setData(orderListBeans);
						adapter.notifyDataSetChanged();
					} else {
						adapter.setData(orderListBeans);
						adapter.notifyDataSetChanged();
					}
					dismissDialog();
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
			}
		});
	}

	/**
	 * 用户取消订单
	 */
	public void cancelOrder(String id) {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("orderId", id);
		baseTask.askCookieRequest(SystemConfig.CANCEL_ORDER, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("取消订单", arg0.result.toString());
				try {
					JSONObject jsonObject = new JSONObject(arg0.result.toString());
					if (jsonObject.getBoolean("success")) {
						if (j % 2 == 0) {
							sendhttp(ONE);
						}
						if (i % 2 == 0) {
							sendhttp(ZONE);
						}
						showToast("订单取消成功");
						setResult(1004);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("取消订单异常", arg0.toString());
			}
		});
	}

	/**
	 * 2.1.42.用户确认收货
	 */
	public void BuyerSigned(String id) {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("orderId", id);
		baseTask.askCookieRequest(SystemConfig.BUYERSIGNED, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("确认收货", arg0.result.toString());
				try {
					JSONObject jsonObject = new JSONObject(arg0.result.toString());
					if (jsonObject.getBoolean("success")) {
						if (k % 2 == 0) {
							sendhttp(THREE);
						}
						if (i % 2 == 0) {
							sendhttp(ZONE);
						}
						showToast("确认收货成功");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("确认收货异常", arg0.toString());
				showToast("确认收货失败");
			}
		});
	}

	@Override
	public void onItemClick(View item, View widget, int position, int which) {
		final String orderid = orderListBeans.get(position).getOrderId().toString();
		switch (which) {
		case R.id.tv_return:
			// 取消订单
			AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
			builder.setTitle("提示");
			builder.setMessage("是否取消订单？");
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					cancelOrder(orderid);
				}
			});
			builder.create().show();
			break;
		case R.id.tv_gopay:
			// 去付款
			Intent intent = new Intent(OrderActivity.this, GoPayOrderActivity.class);
			intent.putExtra("orderId", orderid);
			startActivityForResult(intent, PAYING_ORDER);
			break;
		case R.id.tv_suried:
			// 确认收货
			AlertDialog.Builder builder1 = new AlertDialog.Builder(OrderActivity.this);
			builder1.setTitle("提示");
			builder1.setMessage("是否确认收货？");
			builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					BuyerSigned(orderid);
				}
			});
			builder1.create().show();
			break;
		case R.id.tv_single:
			// 晒单评价
			intent = new Intent(this, EvaluationSingleActivity.class);
			intent.putExtra("OrderListBean", orderListBeans.get(position));
			startActivityForResult(intent, 1000);
			break;
		case R.id.tv_buify:
			syncdShpcart(orderListBeans.get(position));
			break;
		}
	}

	private void syncdShpcart(OrderListBean orderListBean) {
		final BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", "normal");
		params.addBodyParameter("handler", "sku");
		StringBuffer stringBuffer = new StringBuffer();
		List<AppOrderItemVo> appOrderItemVos = orderListBean.getOrderItemList();
		for (AppOrderItemVo appOrderItemVo : appOrderItemVos) {
			if (!"true".equals(appOrderItemVo.getIsPresent()) && "Y".equals(appOrderItemVo.getIsOnSale())) {
				stringBuffer.append(appOrderItemVo.getSkuId());
				stringBuffer.append(",");
				stringBuffer.append("1");
				stringBuffer.append(",");
				stringBuffer.append("Y");
				stringBuffer.append(";");
			}
		}
		if (stringBuffer.length() == 0) {
			showToast("此商品已下架");
			return;
		}
		showDialog("加入购物车。。。");
		stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
		params.addBodyParameter("object", stringBuffer.toString());
		task.askNormalRequest(SystemConfig.BUY_AGAIN, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				dismissDialog();
				Log.e("saveRemark", arg0.result.toString());
				try {
					JSONObject obj = new JSONObject(arg0.result.toString());
					if (obj.has("success") && obj.getBoolean("success")) {
						Intent intent = new Intent(OrderActivity.this, ShopcartActivity.class);
						startActivity(intent);
					} else {
						showToast("加入购物车失败");
					}
				} catch (JSONException e) {
					e.printStackTrace();
					showToast("加入购物车失败");
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				showToast("加入购物车失败");
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1000 && resultCode == RESULT_OK) {
			sendhttp(FOUR, true);
		}
		if (requestCode == PAYING_ORDER && resultCode == RESULT_OK) {
			if (j % 2 == 0) {
				sendhttp(ONE);
			}
			if (i % 2 == 0) {
				sendhttp(ZONE);
			}
		}
	};

	/**
	 * 清空选中颜色
	 */
	public void setColorText() {
		tv_comprehensive.setTextColor(Color.parseColor("#A0A0A0"));
		tv_paying.setTextColor(Color.parseColor("#A0A0A0"));
		tv_goodsing.setTextColor(Color.parseColor("#A0A0A0"));
		tv_sinceing.setTextColor(Color.parseColor("#A0A0A0"));
		tv_contexting.setTextColor(Color.parseColor("#A0A0A0"));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("state", state);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState != null) {
			state = savedInstanceState.getString("state");
		}
	}

	@Override
	public void onClick(View v) {
		i = 1;
		j = 1;
		k = 1;
		setColorText();
		switch (v.getId()) {
		case R.id.tv_comprehensive:
			i++;
			tv_comprehensive.setTextColor(Color.parseColor("#E5350D"));
			state = ZONE;
			sendhttp(ZONE);
			break;
		case R.id.tv_paying:
			j++;
			tv_paying.setTextColor(Color.parseColor("#E5350D"));
			state = ONE;
			sendhttp(ONE);
			break;
		case R.id.tv_goodsing:
			k++;
			tv_goodsing.setTextColor(Color.parseColor("#E5350D"));
			state = THREE;
			sendhttp(THREE);
			break;
		case R.id.tv_sinceing:
			tv_sinceing.setTextColor(Color.parseColor("#E5350D"));
			state = TWO;
			sendhttp(TWO);
			break;
		case R.id.tv_contexting:
			tv_contexting.setTextColor(Color.parseColor("#E5350D"));
			state = FOUR;
			sendhttp(FOUR);
			break;
		}
	}
}
