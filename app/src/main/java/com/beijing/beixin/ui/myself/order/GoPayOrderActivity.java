package com.beijing.beixin.ui.myself.order;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.ViewHolder;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.OrderDetailBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.pay.BalancePayActivity;
import com.beijing.beixin.ui.pay.CheckstandActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.HorizontalListView;
import com.beijing.beixin.utils.Utils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 等待付款
 * 
 * @author ouyanghao
 * 
 */
public class GoPayOrderActivity extends BaseActivity implements OnClickListener {

	/**
	 * 订单号
	 */
	private TextView tv_order_num;
	/**
	 * 账户余额
	 */
	private TextView tv_money;
	/**
	 * 收货人
	 */
	private TextView tv_vip_name;
	/**
	 * 收货人电话
	 */
	private TextView tv_vip_tel;
	/**
	 * 收货人地址
	 */
	private TextView tv_vip_addess;
	/**
	 * 商品列表listview
	 */
	private HorizontalListView listview_images_info;
	/**
	 * 支付方式
	 */
	private TextView pay_way;
	/**
	 * 送货日期
	 */
	private TextView delivery_date;
	/**
	 * 发票信息
	 */
	private TextView invoice_info;
	/**
	 * 商品金额
	 */
	private TextView product_account;
	private RelativeLayout layout_book_list;
	private LinearLayout layout_book_detail;
	private ImageView imageview_book_name;
	private TextView textview_book_name;
	private TextView textview_book_account;
	private TextView textview_book_price;
	private TextView remark_info;
	private TextView return_order;
	/**
	 * 共计多少本
	 */
	private TextView tv_book_account;
	private TextView user_date;
	private TextView textview_yunfei;
	private String orderId;
	private OrderDetailBean detailBean = null;
	private ToggleButton toggleButtonIsLeave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_go_pay_order);
		init();
		sendhttp();
		getProductIdByPlucodes("1");
	}

	public void init() {
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		setNavigationTitle("等待付款");
		Intent intent = getIntent();
		orderId = intent.getStringExtra("orderId");
		TextView button_to_pay = (TextView) findViewById(R.id.button_to_pay);
		button_to_pay.setOnClickListener(this);
		toggleButtonIsLeave = (ToggleButton) findViewById(R.id.toggleButtonIsLeave);
		tv_money = (TextView) findViewById(R.id.tv_money);
		tv_order_num = (TextView) findViewById(R.id.tv_order_num);
		tv_vip_name = (TextView) findViewById(R.id.tv_vip_name);
		return_order = (TextView) findViewById(R.id.return_order);
		return_order.setOnClickListener(this);
		tv_vip_tel = (TextView) findViewById(R.id.tv_vip_tel);
		tv_vip_addess = (TextView) findViewById(R.id.tv_vip_addess);
		remark_info = (TextView) findViewById(R.id.remark_info);
		listview_images_info = (HorizontalListView) findViewById(R.id.listview_images_info);
		layout_book_list = (RelativeLayout) findViewById(R.id.layout_book_list);
		layout_book_detail = (LinearLayout) findViewById(R.id.layout_book_detail);
		imageview_book_name = (ImageView) findViewById(R.id.imageview_book_name);
		textview_book_name = (TextView) findViewById(R.id.textview_book_name);
		textview_book_account = (TextView) findViewById(R.id.textview_book_account);
		textview_book_price = (TextView) findViewById(R.id.textview_book_price);
		pay_way = (TextView) findViewById(R.id.pay_way);
		delivery_date = (TextView) findViewById(R.id.delivery_date);
		invoice_info = (TextView) findViewById(R.id.invoice_info);
		product_account = (TextView) findViewById(R.id.product_account);
		tv_book_account = (TextView) findViewById(R.id.tv_book_account);
		user_date = (TextView) findViewById(R.id.user_date);
		textview_yunfei = (TextView) findViewById(R.id.textview_yunfei);
	}

	public void setdata(OrderDetailBean detailBean) {
		List<String> listImage = new ArrayList<String>();
		for (int i = 0; i < detailBean.getOrderItemList().size(); i++) {
			listImage.add(detailBean.getOrderItemList().get(i).getImage());
		}
		if (listImage.size() > 1) {
			layout_book_detail.setVisibility(View.VISIBLE);
			layout_book_list.setVisibility(View.GONE);
			List<String> listImageStr = new ArrayList<String>();
			if (listImage.size() < 3) {
				for (int i = 0; i < listImage.size(); i++) {
					listImageStr.add(listImage.get(i));
				}
			} else {
				for (int i = 0; i < 3; i++) {
					listImageStr.add(listImage.get(i));
				}
			}
			CommonAdapter<String> adapter = new CommonAdapter<String>(this, listImageStr, R.layout.item_orders) {

				@Override
				public void convert(ViewHolder helper, String item) {
					BitmapUtils util = new BitmapUtils(GoPayOrderActivity.this);
					util.display(helper.getView(R.id.images), item);
				}
			};
			listview_images_info.setAdapter(adapter);
			tv_book_account.setText("共" + listImage.size() + "件");
		} else {
			layout_book_detail.setVisibility(View.GONE);
			layout_book_list.setVisibility(View.VISIBLE);
			BitmapUtil util = new BitmapUtil();
			util.displayImage(this, imageview_book_name, detailBean.getOrderItemList().get(0).getImage());
			textview_book_name.setText(Utils.checkStr(detailBean.getOrderItemList().get(0).getProductNm()));
			textview_book_account
					.setText("×" + Utils.checkStr(detailBean.getOrderItemList().get(0).getQuantity() + ""));
			textview_book_price
					.setText("¥" + Utils.parseDecimalDouble2(detailBean.getOrderItemList().get(0).getUnitPrice()));
		}
	}

	/**
	 * 查询用户账户余额或积分type 0为积分 1为余额
	 * 
	 * @param type
	 */
	private void getProductIdByPlucodes(String type) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", type);
		params.addBodyParameter("userId", MyApplication.mapp.getUserInfoBean().getSysUserId() + "");
		BaseTask task = new BaseTask(this);
		task.askCookieRequest(SystemConfig.GET_USER_PRESTORE, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("test", "积分或余额获取失败");
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					Log.e("积分或余额", arg0.result);
					JSONObject obj = new JSONObject(arg0.result);
					if (obj.getBoolean("success")) {
						tv_money.setText("¥" + Utils.parseDecimalDouble2(obj.getString("result").toString()));
						double money = Double.parseDouble(obj.getString("result").toString());
						if (money == 0) {
							toggleButtonIsLeave.setChecked(false);
						}
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public void sendhttp() {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("orderId", orderId);
		showDialog("正在加载。。。");
		baseTask.askCookieRequest(SystemConfig.ORDER_DETAIL, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("goToPay", arg0.result.toString());
				detailBean = new OrderDetailBean();
				JSONObject object;
				try {
					object = new JSONObject(arg0.result);
					detailBean = JSON.parseObject(object.getString("result").toString(), OrderDetailBean.class);
					tv_order_num.setText(detailBean.getOrderNum());
					tv_vip_name.setText("收货人：" + detailBean.getReceiverNm());
					tv_vip_addess.setText(detailBean.getReceiverAddr());
					tv_vip_tel.setText(detailBean.getReceiverMobile());
					pay_way.setText(detailBean.getPayWay());
					product_account.setText("¥" + Utils.parseDecimalDouble2(detailBean.getProductTotalAmount()));
					delivery_date.setText(detailBean.getDeliveryWay());
					invoice_info.setText(detailBean.getInvoiceTitle());
					user_date.setText(Utils.parseDecimalDouble2(detailBean.getDisTotalAmount()));
					remark_info.setText(detailBean.getRemark() + "");
					textview_yunfei.setText("¥" + Utils.parseDecimalDouble2(detailBean.getFreightAmount()));
					setdata(detailBean);
					dismissDialog();
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				Log.e("订单详情异常", arg0.toString());
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_to_pay:
			((MyApplication) getApplication()).setTotalMoney(detailBean.getOrderTotalAmount() + "");// 将总金额存入sp中
			if (toggleButtonIsLeave.isChecked()) {
				Intent togg = new Intent(GoPayOrderActivity.this, BalancePayActivity.class);
				togg.putExtra("mymoney", tv_money.getText().toString());
				togg.putExtra("productmoney", detailBean.getOrderTotalAmount() + "");
				togg.putExtra("orderId", orderId);
				startActivity(togg);
				finish();
			} else {
				if ("¥0.00".equals(tv_money.getText().toString()) && toggleButtonIsLeave.isChecked()) {
					showToast("余额账户为0，不能使用余额支付");
					return;
				}
				Intent togg = new Intent(GoPayOrderActivity.this, CheckstandActivity.class);
				togg.putExtra("orderId", orderId);
				togg.putExtra("productmoney", detailBean.getOrderTotalAmount() + "");
				startActivity(togg);
				finish();
			}
			break;
		case R.id.return_order:
			AlertDialog.Builder builder = new AlertDialog.Builder(GoPayOrderActivity.this);
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
					cancelOrder(orderId);
				}
			});
			builder.create().show();
			break;
		}
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
						showToast("订单取消成功");
						setResult(RESULT_OK);
						finish();
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
}
