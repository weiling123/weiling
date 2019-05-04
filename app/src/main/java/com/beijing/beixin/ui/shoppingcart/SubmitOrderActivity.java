package com.beijing.beixin.ui.shoppingcart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.SubmitOrderAdapter;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.AddressBean;
import com.beijing.beixin.pojo.ShoppingCartBean;
import com.beijing.beixin.pojo.ShoppingCartShopBean;
import com.beijing.beixin.pojo.SubmitOrderAddressBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.pay.BalancePayActivity;
import com.beijing.beixin.ui.pay.CheckstandActivity;
import com.beijing.beixin.utils.ExpandListView;
import com.beijing.beixin.utils.HorizontalListView;
import com.beijing.beixin.utils.ToastUtil;
import com.beijing.beixin.utils.Utils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class SubmitOrderActivity extends BaseActivity implements OnClickListener {

	/**
	 * 前往支付配送界面
	 */
	public static final int TO_PAY_WAY = 0;
	/**
	 * 前往支付配送界面
	 */
	public static final int TO_SEND_WAY = 1;
	/**
	 * 前往优惠券界面
	 */
	public static final int TO_COUNPON = 2;
	/**
	 * 前往发票界面
	 */
	public static final int TO_INVOICE = 3;
	/**
	 * 前往地址界面
	 */
	public static final int TO_ADDRESS = 4;
	/**
	 * 支付方式
	 */
	private TextView textview_pay_style;
	/**
	 * 配送方式
	 */
	private TextView textview_send_style;
	/**
	 * 收货人
	 */
	private TextView textview_receive_person;
	/**
	 * 电话号码
	 */
	private TextView textview_user_phone;
	/**
	 * 收货地址
	 */
	private TextView textview_address;
	/**
	 * 收货地址
	 */
	private TextView textview_total_address;
	/**
	 * 实付款
	 */
	private TextView textview_allprice;
	/**
	 * 运费
	 */
	private TextView textview_freight_total_amount;
	/**
	 * 运费
	 */
	private TextView button_submit_order;
	/**
	 * 商品价格
	 */
	private TextView textview_product_total_mount;
	/**
	 * 获取的对象
	 */
	private ShoppingCartBean cartBean;
	/**
	 * 优惠券
	 */
	private TextView textview_ordersub_vocher_name;
	/**
	 * 配送方式
	 */
	private HorizontalListView layout_send_way;
	private RelativeLayout layout_book_list;
	private LinearLayout layout_book_detail;
	private TextView tv_money;
	private TextView textview_invoice_unit;
	private String orderId;
	private String prestorePayAmount;
	private ExpandListView listview_submit_order;
	private EditText editText_Remark;
	private SubmitOrderAdapter adapter;
	private ToggleButton toggleButtonIsLeave;
	private List<AddressBean> addresslist = new ArrayList<AddressBean>();
	private RelativeLayout layout_address;
	private RelativeLayout relativelayout_submit;
	private List<SubmitOrderAddressBean> changeSendWaylist = new ArrayList<SubmitOrderAddressBean>();
	public int receiveAddrId = 0;
	private double accountMoney;
	private double totalMoney;
	/**
	 * 修改过后的实体
	 */
	private List<ShoppingCartShopBean> listCommon;

	private boolean mIsGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		Serializable serializable = intent.getSerializableExtra("shoppingCartBean");
		if (serializable == null || !(serializable instanceof ShoppingCartBean)) {
			mIsGroup = false;
		} else {
			mIsGroup = true;
		}

		setContentView(R.layout.activity_submit_order);
		initView();
		setListener();
		getProductIdByPlucodes("1");

		if (mIsGroup) {
			ShoppingCartBean bean = (ShoppingCartBean) serializable;
			updateUI(bean);
		} else {
			initData();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void initView() {
		setNavigationTitle("确认订单");
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		ImageView imageview_ordersub_vocher = (ImageView) findViewById(R.id.imageview_ordersub_vocher);
		imageview_ordersub_vocher.setOnClickListener(this);
		editText_Remark = (EditText) findViewById(R.id.editText_Remark);
		initLayout();
		initTextView();
		initExpandListView();
	}

	private void initExpandListView() {
		listview_submit_order = (ExpandListView) findViewById(R.id.listview_submit_order);
		adapter = new SubmitOrderAdapter(SubmitOrderActivity.this, mIsGroup);
		adapter.setData(new ArrayList<ShoppingCartShopBean>());
		listview_submit_order.setAdapter(adapter);
	}

	private void initLayout() {
		layout_book_list = (RelativeLayout) findViewById(R.id.layout_book_list);
		layout_book_detail = (LinearLayout) findViewById(R.id.layout_book_detail);
		layout_address = (RelativeLayout) findViewById(R.id.layout_address);
		relativelayout_submit = (RelativeLayout) findViewById(R.id.relativelayout_submit);
		FrameLayout framelayout_submit = (FrameLayout) findViewById(R.id.framelayout_submit);
		framelayout_submit.setOnClickListener(this);
		RelativeLayout layout_invoice = (RelativeLayout) findViewById(R.id.layout_invoice);
		layout_invoice.setOnClickListener(this);
		RelativeLayout layout_pay_send = (RelativeLayout) findViewById(R.id.layout_pay_send);
		layout_pay_send.setOnClickListener(this);
	}

	private void initTextView() {
		textview_ordersub_vocher_name = (TextView) findViewById(R.id.textview_ordersub_vocher_name);
		textview_pay_style = (TextView) findViewById(R.id.textview_pay_style);
		textview_send_style = (TextView) findViewById(R.id.textview_send_style);
		textview_receive_person = (TextView) findViewById(R.id.textview_receive_person);
		textview_user_phone = (TextView) findViewById(R.id.textview_user_phone);
		textview_address = (TextView) findViewById(R.id.textview_address);
		textview_total_address = (TextView) findViewById(R.id.textview_total_address);
		textview_allprice = (TextView) findViewById(R.id.textview_allprice);
		textview_product_total_mount = (TextView) findViewById(R.id.textview_product_total_mount);
		textview_freight_total_amount = (TextView) findViewById(R.id.textview_freight_total_amount);
		button_submit_order = (TextView) findViewById(R.id.button_submit_order);
		tv_money = (TextView) findViewById(R.id.tv_money);
		textview_invoice_unit = (TextView) findViewById(R.id.textview_invoice_unit);
		toggleButtonIsLeave = (ToggleButton) findViewById(R.id.toggleButtonIsLeave);
	}

	private void setListener() {
		button_submit_order.setOnClickListener(this);
		textview_ordersub_vocher_name.setOnClickListener(this);
		layout_book_list.setOnClickListener(this);
		layout_book_detail.setOnClickListener(this);
		toggleButtonIsLeave.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (accountMoney < totalMoney) {
					toggleButtonIsLeave.setChecked(false);
				}
			}
		});
	}

	/**
	 * 查询用户账户余额或积分type 0为积分 1为余额
	 * 
	 * @param type
	 */
	private void getProductIdByPlucodes(String type) {
		BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", type);
		params.addBodyParameter("userId", MyApplication.mapp.getUserInfoBean().getSysUserId() + "");
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
						accountMoney = Double.parseDouble(obj.getString("result"));
						if (accountMoney == 0) {
							toggleButtonIsLeave.setChecked(false);
							toggleButtonIsLeave.setEnabled(false);
						}
						tv_money.setText("¥" + Utils.parseDecimalDouble2(obj.getString("result").toString()));
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	/**
	 * 获取订单
	 */
	public void initData() {
		showDialog("正在请求数据，请稍后...");
		final BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", "normal");
		baseTask.askCookieRequest(SystemConfig.CART, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("submitOrder", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result);
					ShoppingCartBean bean = JSON.parseObject(obj.get("result").toString(), ShoppingCartBean.class);
					// 给页面填充数据
					if (bean != null) {
						updateUI(bean);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(SubmitOrderActivity.this, "获取订单失败");
			}
		});
	}

	private void updateUI(ShoppingCartBean bean) {
		cartBean = bean;
		// initListView(cartBean.getShoppingCartVos());
		// 地址
		AddressBean addressBean = bean.getReceiverAddr();
		if (addressBean != null) {
			receiveAddrId = addressBean.getReceiveAddrId();
			textview_receive_person.setText("" + addressBean.getName());
			textview_user_phone.setText("" + addressBean.getMobile());
			textview_address.setText(addressBean.getAddressPath().replace("-", " ") + " " + addressBean.getAddr());
			textview_total_address
					.setText("送至:" + addressBean.getAddressPath().replace("-", " ") + " " + addressBean.getAddr());
		} else {
			layout_address.setVisibility(View.GONE);
			relativelayout_submit.setVisibility(View.VISIBLE);
		}
		totalMoney = bean.getAllOrderTotalAmount();
		textview_allprice.setText("¥" + Utils.parseDecimalDouble2(bean.getAllOrderTotalAmount()));
		textview_freight_total_amount.setText("¥" + Utils.parseDecimalDouble2(bean.getFreightTotalAmount()));
		textview_product_total_mount.setText("¥" + Utils.parseDecimalDouble2(bean.getAllProductTotalAmount()));
		List<ShoppingCartShopBean> list = new ArrayList<ShoppingCartShopBean>();
		for (int i = 0; i < cartBean.getShoppingCartVos().size(); i++) {
			if (cartBean.getShoppingCartVos().get(i).getOrderTotalAmount() != 0) {
				list.add(cartBean.getShoppingCartVos().get(i));
			}
		}
		listCommon = getShoppingList(cartBean);
		adapter.setData(list);
		adapter.notifyDataSetChanged();
		dismissDialog();
	}

	/**
	 * 提交订单
	 */
	public void addOrder() {
		showDialog("正在添加订单，请稍后...");
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		if (mIsGroup) {
			params.addBodyParameter("cartType", "groupBuy");
		} else {
			params.addBodyParameter("cartType", "normal");
		}
		params.addBodyParameter("receiveAddrId", receiveAddrId + "");
		if ("请选择".equals(textview_invoice_unit.getText().toString())) {
			params.addBodyParameter("isCheckInvoice", "N");
		} else {
			params.addBodyParameter("isCheckInvoice", "Y");
			params.addBodyParameter("invoice.invoiceTitle", textview_invoice_unit.getText().toString());
			params.addBodyParameter("invoice.isNeedInvoice", "Y");
		}
		baseTask.askCookieRequest(SystemConfig.ADD_ORDER, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("addOrder", arg0.result.toString());
				JSONObject obj;
				try {
					dismissDialog();
					obj = new JSONObject(arg0.result.toString());
					if (obj.getBoolean("success")) {
						showToast("添加订单成功");
						orderId = obj.getString("orders").toString();
						if (toggleButtonIsLeave.isChecked()) {
							Intent togg = new Intent(SubmitOrderActivity.this, BalancePayActivity.class);
							togg.putExtra("mymoney", tv_money.getText().toString());
							togg.putExtra("productmoney", textview_allprice.getText().toString());
							togg.putExtra("orderId", orderId);
							startActivity(togg);
							finish();
						} else {
							Intent togg = new Intent(SubmitOrderActivity.this, CheckstandActivity.class);
							togg.putExtra("orderId", orderId);
							togg.putExtra("productmoney", textview_allprice.getText().toString());
							startActivity(togg);
							finish();
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
				showToast("新增订单失败");
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.framelayout_submit:
			Intent intent = new Intent(this, SubmitOrderAddressActivity.class);
			startActivityForResult(intent, TO_ADDRESS);
			break;
		case R.id.layout_invoice:
			Intent intent2 = new Intent(this, InvoiceActivity.class);
			startActivityForResult(intent2, TO_INVOICE);
			break;
		case R.id.textview_ordersub_vocher_name:
			break;
		case R.id.imageview_ordersub_vocher:
			Intent intent3 = new Intent(this, CouponActivity.class);
			Bundle bundle2 = new Bundle();
			bundle2.putSerializable("cartBean", cartBean);
			intent3.putExtras(bundle2);
			startActivityForResult(intent3, TO_COUNPON);
			break;
		case R.id.layout_book_list:
		case R.id.layout_book_detail:
			Intent intent4 = new Intent(this, ProductListShopActivity.class);
			Bundle bundle3 = new Bundle();
			bundle3.putSerializable("cartBean", cartBean);
			intent4.putExtras(bundle3);
			startActivity(intent4);
			break;
		case R.id.button_submit_order:
			if ("".equals(textview_address.getText().toString()) || textview_address.getText().toString() == null) {
				ToastUtil.show(this, "请选择默认地址");
				return;
			}
			if ("¥0.00".equals(tv_money.getText().toString()) && toggleButtonIsLeave.isChecked() == true) {
				showToast("余额账户为0，不能使用余额支付");
				return;
			}
			if (adapter.isAddOrder(this)) {
				addOrder();
				((MyApplication) getApplication()).setTotalMoney(textview_allprice.getText().toString());// 将总金额存入sp中
			}
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			int position = data.getExtras().getInt("position");
			if (requestCode == TO_PAY_WAY) {
				String payWay = data.getExtras().get("payway").toString();
				adapter.setPayWay(position, payWay);
				adapter.notifyDataSetChanged();
			} else if (requestCode == TO_ADDRESS) {// 地址界面回调
				AddressBean changeAddressBean = (AddressBean) data.getSerializableExtra("addressBean");
				if (changeAddressBean != null) {
					layout_address.setVisibility(View.VISIBLE);
					relativelayout_submit.setVisibility(View.GONE);
					receiveAddrId = changeAddressBean.getReceiveAddrId();
					textview_receive_person.setText("" + changeAddressBean.getName());
					textview_user_phone.setText("" + changeAddressBean.getMobile());
					textview_address.setText(
							changeAddressBean.getAddressPath().replace("-", " ") + " " + changeAddressBean.getAddr());
					textview_total_address.setText("送至:" + changeAddressBean.getAddressPath().replace("-", " ") + " "
							+ changeAddressBean.getAddr());
				}
				changeSendWaylist = (List<SubmitOrderAddressBean>) data.getSerializableExtra("sendWayList");
				for (int i = 0; i < listCommon.size(); i++) {
					listCommon.get(i).setSendWay("请选择");
				}
				adapter.updateDate(listCommon);
				adapter.notifyDataSetChanged();
			} else if (requestCode == TO_INVOICE) {
				String invoice = data.getExtras().get("invoice").toString();
				if (!"".equals(invoice)) {
					textview_invoice_unit.setText(invoice);
				} else {
					textview_invoice_unit.setText("");
				}
			} else if (requestCode == TO_SEND_WAY) {
				ShoppingCartBean shoppingCartBean = (ShoppingCartBean) data.getSerializableExtra("shoppingCartBean");
				textview_allprice.setText("¥" + Utils.parseDecimalDouble2(shoppingCartBean.getAllOrderTotalAmount()));
				textview_freight_total_amount
						.setText("¥" + Utils.parseDecimalDouble2(shoppingCartBean.getFreightTotalAmount()));
				textview_product_total_mount
						.setText("¥" + Utils.parseDecimalDouble2(shoppingCartBean.getAllProductTotalAmount()));
				String sendWay = data.getExtras().get("sendway").toString();
				listCommon = getShoppingList(shoppingCartBean);
				adapter.updateDate(listCommon);
				adapter.setSendWay(position, sendWay);
				adapter.notifyDataSetChanged();
			} else if (requestCode == TO_COUNPON) {
				ShoppingCartBean shoppingCartBean = (ShoppingCartBean) data.getSerializableExtra("ShoppingCartBean");
				String couponId = data.getExtras().get("couponId").toString();
				textview_allprice.setText("¥" + Utils.parseDecimalDouble2(shoppingCartBean.getAllOrderTotalAmount()));
				textview_freight_total_amount
						.setText("¥" + Utils.parseDecimalDouble2(shoppingCartBean.getFreightTotalAmount()));
				textview_product_total_mount
						.setText("¥" + Utils.parseDecimalDouble2(shoppingCartBean.getAllProductTotalAmount()));
				listCommon = getShoppingList(shoppingCartBean);
				adapter.updateDate(listCommon);
				if ("".equals(couponId)) {
					adapter.setCoupon(position, "请选择", couponId);
				} else {
					adapter.setCoupon(position, shoppingCartBean.getDiscountList().get(0).getRuleName(), couponId);
				}
				adapter.notifyDataSetChanged();
			}
		}
	}

	private List<ShoppingCartShopBean> getShoppingList(ShoppingCartBean shoppingCartBean) {
		List<ShoppingCartShopBean> list = shoppingCartBean.getShoppingCartVos();
		if (list == null) {
			return null;
		}
		List<ShoppingCartShopBean> resultList = new ArrayList<ShoppingCartShopBean>();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getIsSelectAll()) {
				resultList.add(list.get(i));
				continue;
			}
			for (int j = 0; j < list.get(i).getItems().size(); j++) {
				if (!list.get(i).getItems().get(j).getItemSelected()) {
					list.get(i).getItems().remove(j);
					j--;
				}
			}
			if (list.get(i).getItems().size() != 0) {
				resultList.add(list.get(i));
			}

		}
		return resultList;
	}
}
