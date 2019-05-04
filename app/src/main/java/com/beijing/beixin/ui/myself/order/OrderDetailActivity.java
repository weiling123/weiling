package com.beijing.beixin.ui.myself.order;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.ViewHolder;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.LogisticsBean;
import com.beijing.beixin.pojo.OrderDetailBean;
import com.beijing.beixin.pojo.OrderListBean;
import com.beijing.beixin.pojo.OrderListBean.AppOrderItemVo;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.utils.ExpandListView;
import com.beijing.beixin.utils.Utils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 订单详情
 * 
 * @author ouyanghao
 * 
 */
public class OrderDetailActivity extends BaseActivity {

	/**
	 * 快递logo
	 */
	private ImageView iv_express_logo;
	/**
	 * 快递名称
	 */
	private TextView tv_express_name;
	/**
	 * 运单编号
	 */
	private TextView tv_waybill_number;
	/**
	 * 物流状态
	 */
	private TextView tv_logistics_status;
	/**
	 * 订单号
	 */
	private TextView tv_order_number;
	/**
	 * 商品信息
	 */
	private LinearLayout ll_product_info;
	/**
	 * 商品logo
	 */
	private ImageView iv_product_logo;
	/**
	 * 商品名称
	 */
	private TextView tv_product_name;
	/**
	 * 商品数量
	 */
	private TextView tv_product_sum;
	/**
	 * 商品价格
	 */
	private TextView tv_product_price;
	/**
	 * 会员头像
	 */
	private ImageView iv_vip_header;
	/**
	 * 收货人名称
	 */
	private TextView tv_vip_name;
	/**
	 * 收货人tel
	 */
	private TextView tv_vip_tel;
	/**
	 * 收货地址
	 */
	private TextView tv_vip_addess;
	private TextView tv_sum;
	private TextView tv_pay;
	/**
	 * 支付方式
	 */
	private TextView tv_method_pay;
	/**
	 * 发票信息
	 */
	private TextView tv_invoice_info;
	/**
	 * 备注
	 */
	private TextView textview_remark;
	/**
	 * 优惠券
	 */
	private TextView tv_coupons;
	private TextView tv_express_time;
	private TextView tv_express_datetime;
	/**
	 * 积分
	 */
	private TextView tv_integral;
	/**
	 * 商品金额
	 */
	private TextView tv_price;
	/**
	 * 商品总额
	 */
	private TextView tv_ordercount;
	/**
	 * 运费
	 */
	private TextView tv_freight;
	private String orderid;
	private ExpandListView mListView;
	private OrderDetailBean detailBean = null;
	private LogisticsBean logisticsBeans = null;
	private List<AppOrderItemVo> orderItemVos = new ArrayList<OrderListBean.AppOrderItemVo>();
	private CommonAdapter<AppOrderItemVo> mAdapter;
	BitmapUtils bitmapUtils = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);
		init();
		showDialog("正在请求数据，请稍后...");
		queryLogistics();
		sendhttp();
	}

	public void init() {
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		setNavigationTitle("订单详情");
		Intent intent = getIntent();
		orderid = intent.getStringExtra("orderid");
		bitmapUtils = new BitmapUtils(OrderDetailActivity.this);
		mListView = (ExpandListView) findViewById(R.id.order_deatil);
		tv_express_name = (TextView) findViewById(R.id.tv_express_name);
		tv_waybill_number = (TextView) findViewById(R.id.tv_waybill_number);
		tv_express_datetime = (TextView) findViewById(R.id.tv_express_datetime);
		tv_express_time = (TextView) findViewById(R.id.tv_express_time);
		tv_sum = (TextView) findViewById(R.id.tv_sum);
		tv_pay = (TextView) findViewById(R.id.tv_pay);
		ll_product_info = (LinearLayout) findViewById(R.id.ll_product_info);
		iv_product_logo = (ImageView) findViewById(R.id.iv_product_logo);
		tv_product_sum = (TextView) findViewById(R.id.tv_product_sum);
		tv_product_price = (TextView) findViewById(R.id.tv_product_price);
		iv_vip_header = (ImageView) findViewById(R.id.iv_vip_header);
		tv_vip_name = (TextView) findViewById(R.id.tv_vip_name);
		tv_vip_addess = (TextView) findViewById(R.id.tv_vip_addess);
		tv_method_pay = (TextView) findViewById(R.id.tv_method_pay);
		tv_invoice_info = (TextView) findViewById(R.id.tv_invoice_info);
		textview_remark = (TextView) findViewById(R.id.textview_remark);
		tv_coupons = (TextView) findViewById(R.id.tv_coupons);
		tv_integral = (TextView) findViewById(R.id.tv_integral);
		tv_freight = (TextView) findViewById(R.id.tv_freight);
		tv_order_number = (TextView) findViewById(R.id.tv_order_number);
		tv_vip_tel = (TextView) findViewById(R.id.tv_vip_tel);
		tv_ordercount = (TextView) findViewById(R.id.tv_ordercount);
	}

	public void setAdapterData(List<AppOrderItemVo> orderItemVos) {
		mListView.setAdapter(mAdapter = new CommonAdapter<OrderListBean.AppOrderItemVo>(this, orderItemVos,
				R.layout.item_orderdetail) {

			@Override
			public void convert(ViewHolder helper, AppOrderItemVo item) {
				bitmapUtils.display(helper.getView(R.id.iv_product_logo), item.getImage());
				helper.setText(R.id.tv_product_name, item.getProductNm());
				helper.setText(R.id.tv_product_sum, "×" + item.getQuantity());
				helper.setText(R.id.tv_product_price, "¥" + Utils.parseDecimalDouble2(item.getUnitPrice()));
				helper.getConvertView().setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
					}
				});
			}
		});
	}

	/**
	 * 物流信息
	 */
	public void queryLogistics() {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("orderId", orderid);
		baseTask.askCookieRequest(SystemConfig.QUERYLOGISTICS, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("物流信息", arg0.toString());
				logisticsBeans = new LogisticsBean();
				JSONObject object;
				try {
					object = new JSONObject(arg0.result);
					logisticsBeans = JSON.parseObject(object.getString("result"), LogisticsBean.class);
					tv_express_name.setText("[" + logisticsBeans.getLogisticsCompany() + "]");
					tv_waybill_number.setText(logisticsBeans.getLogisticsNum());
					tv_order_number.setText(logisticsBeans.getOrderNum());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("物流信息异常", arg0.toString());
			}
		});
	}

	/**
	 * 订单详情
	 */
	public void sendhttp() {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("orderId", orderid);
		baseTask.askCookieRequest(SystemConfig.ORDER_DETAIL, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("订单详情", arg0.result.toString());
				detailBean = new OrderDetailBean();
				JSONObject object;
				try {
					object = new JSONObject(arg0.result);
					detailBean = JSON.parseObject(object.getString("result").toString(), OrderDetailBean.class);
					orderItemVos = detailBean.getOrderItemList();
					setAdapterData(orderItemVos);
					tv_vip_name.setText(detailBean.getReceiverNm());
					tv_vip_addess.setText(detailBean.getReceiverAddr());
					tv_vip_tel.setText(detailBean.getReceiverMobile());
					tv_method_pay.setText(detailBean.getPayWay());
					tv_coupons
							.setText("-¥" + Utils.parseDecimalDouble2(detailBean.getDisTotalAmount()).replace("-", ""));
					tv_integral.setText(detailBean.getObtainTotalIntegral() + "分");
					tv_freight.setText("¥" + Utils.parseDecimalDouble2(detailBean.getFreightAmount()));
					if ("".equals(Utils.checkStr(detailBean.getInvoiceTitle()))) {
						tv_invoice_info.setText("未填写");
					} else {
						tv_invoice_info.setText(detailBean.getInvoiceTitle());
					}
					if ("".equals(Utils.checkStr(detailBean.getRemark()))) {
						textview_remark.setText("无");
					} else {
						textview_remark.setText(detailBean.getRemark());
					}
					tv_ordercount.setText("¥" + Utils.parseDecimalDouble2(detailBean.getOrderTotalAmount()));
					tv_sum.setText("¥" + Utils.parseDecimalDouble2(detailBean.getProductTotalAmount()) + "+"
							+ tv_freight.getText().toString().trim() + "运费"
							+ tv_coupons.getText().toString().trim().replace("优惠", "") + "优惠");
					tv_pay.setText("实付款：" + tv_ordercount.getText().toString().trim());
					tv_express_datetime.setText(detailBean.getOrderCreateTime());
					dismissDialog();
				} catch (JSONException e) {
					e.printStackTrace();
					dismissDialog();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("订单详情异常", arg0.toString());
				dismissDialog();
			}
		});
	}
}
