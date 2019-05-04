package com.beijing.beixin.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.beixin.R;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.ShoppingCartProBean;
import com.beijing.beixin.pojo.ShoppingCartShopBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.shoppingcart.CouponActivity;
import com.beijing.beixin.ui.shoppingcart.PayOrSendWayActivity;
import com.beijing.beixin.ui.shoppingcart.SubmitOrderActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.ExpandListView;
import com.beijing.beixin.utils.HorizontalListView;
import com.beijing.beixin.utils.ToastUtil;
import com.beijing.beixin.utils.Utils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class SubmitOrderAdapter extends BaseAdapter {

	private Context context;// 上下文
	private List<ShoppingCartShopBean> list;
	private boolean mIsGroup;

	public SubmitOrderAdapter(Context context, boolean isGroup) {
		super();
		this.context = context;
		mIsGroup = isGroup;

	}

	public void setData(List<ShoppingCartShopBean> list) {
		this.list = list;
	}

	public void updateDate(List<ShoppingCartShopBean> listNew) {
		if (list == null || listNew == null) {
			list = listNew;
			return;
		}
		for (int i = 0; i < listNew.size(); i++) {
			for (ShoppingCartShopBean shoppingCartShopBean : list) {
				ShoppingCartShopBean item = listNew.get(i);
				if (item.getShopInfId().intValue() == shoppingCartShopBean.getShopInfId().intValue()) {
					item.setRemark(shoppingCartShopBean.getRemark());
					item.setPayWay(shoppingCartShopBean.getPayWay());
					item.setSendWay(shoppingCartShopBean.getSendWay());
					item.setCouPon(shoppingCartShopBean.getCouPon());
					item.setCouponId(shoppingCartShopBean.getCouponId());
				}
			}
		}
		list = listNew;
	}

	public boolean isAddOrder(SubmitOrderActivity submitOrderActivity) {
		if (list == null) {
			return false;
		}
		for (ShoppingCartShopBean shoppingCartShopBean : list) {
			if ("请选择".equals(shoppingCartShopBean.getPayWay())) {
				submitOrderActivity.showToast("支付方式未选择，请选择支付方式");
				return false;
			}
			if ("请选择".equals(shoppingCartShopBean.getSendWay())) {
				submitOrderActivity.showToast("配送方式未选择，请选择配送方式");
				return false;
			}
		}
		return true;
	}

	public void setPayWay(int position, String way) {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getShopInfId().intValue() == position) {
					list.get(i).setPayWay(way);
				}
			}
		}
	}

	public void setSendWay(int position, String way) {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getShopInfId().intValue() == position) {
					list.get(i).setSendWay(way);
				}
			}
		}
	}

	public void setCoupon(int position, String way, String couponId) {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getShopInfId().intValue() == position) {
					list.get(i).setCouPon(way);
					list.get(i).setCouponId(couponId);
				}
			}
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	private String mLastText;

	@Override
	public View getView(final int position, View convertView, final ViewGroup arg2) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_submit_order_list, null);
			holder.layout_book_list = (RelativeLayout) convertView.findViewById(R.id.layout_book_list);
			holder.layout_coupon = (RelativeLayout) convertView.findViewById(R.id.layout_coupon);
			holder.layout_book_detail = (LinearLayout) convertView.findViewById(R.id.layout_book_detail);
			holder.layout_pay_way = (LinearLayout) convertView.findViewById(R.id.layout_pay_way);
			holder.layout_send_way2 = (LinearLayout) convertView.findViewById(R.id.layout_send_way2);
			holder.layout_send_way = (HorizontalListView) convertView.findViewById(R.id.layout_send_way);
			holder.imageview_book_name = (ImageView) convertView.findViewById(R.id.imageview_book_name);
			holder.textview_book_name = (TextView) convertView.findViewById(R.id.textview_book_name);
			holder.textview_book_price = (TextView) convertView.findViewById(R.id.textview_book_price);
			holder.textview_disccount = (TextView) convertView.findViewById(R.id.textview_disccount);
			holder.textview_yunfei = (TextView) convertView.findViewById(R.id.textview_yunfei);
			holder.textview_total = (TextView) convertView.findViewById(R.id.textview_total);
			holder.textview_book_account = (TextView) convertView.findViewById(R.id.textview_book_account);
			holder.textview_book_account2 = (TextView) convertView.findViewById(R.id.textview_book_account2);
			holder.textview_shopname = (TextView) convertView.findViewById(R.id.textview_shopname);
			holder.textview_send_style = (TextView) convertView.findViewById(R.id.textview_send_style);
			holder.textview_pay_style = (TextView) convertView.findViewById(R.id.textview_pay_style);
			holder.textview_ordersub_vocher_name = (TextView) convertView
					.findViewById(R.id.textview_ordersub_vocher_name);
			holder.edittext_remark = (EditText) convertView.findViewById(R.id.edittext_remark);
			holder.edittext_remark.addTextChangedListener(new MyWatcher(holder.edittext_remark));
			holder.listview_submit_order = (ExpandListView) convertView.findViewById(R.id.listview_submit_order);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.edittext_remark.setTag(position);
		holder.edittext_remark.setText(list.get(position).getRemark());
		String sendWay = list.get(position).getSendWay();
		holder.textview_send_style.setText(sendWay);
		String payWay = list.get(position).getPayWay();
		holder.textview_pay_style.setText(payWay);
		String couPon = list.get(position).getCouPon();
		holder.textview_ordersub_vocher_name.setText(couPon);
		holder.textview_yunfei
				.setText("¥" + Utils.parseDecimalDouble2(list.get(position).getFreightAmount().toString()));
		holder.textview_total
				.setText("¥" + Utils.parseDecimalDouble2(list.get(position).getProductTotalAmount().toString()));
		holder.textview_shopname.setText(list.get(position).getShopNm().toString());
		holder.textview_disccount.setText("¥" + Utils.parseDecimalDouble2(list.get(position).getTotalDiscountAmount()));
		holder.layout_coupon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (((SubmitOrderActivity) context).receiveAddrId != 0) {
					Intent intent = new Intent(context, CouponActivity.class);
					intent.putExtra("orgId", list.get(position).getOrgId() + "");
					intent.putExtra("position", list.get(position).getShopInfId());
					intent.putExtra("couponId", list.get(position).getCouponId());
					((SubmitOrderActivity) context).startActivityForResult(intent, SubmitOrderActivity.TO_COUNPON);
				} else {
					Toast.makeText(context, "请选择收货人与收货地址", Toast.LENGTH_SHORT).show();
				}
			}
		});
		holder.layout_pay_way.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, PayOrSendWayActivity.class);
				intent.putExtra("way", "pay");
				intent.putExtra("isGroup", mIsGroup);
				intent.putExtra("position", list.get(position).getShopInfId());
				intent.putExtra("orgId", list.get(position).getOrgId() + "");
				((SubmitOrderActivity) context).startActivityForResult(intent, SubmitOrderActivity.TO_PAY_WAY);
			}
		});
		holder.layout_send_way2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (((SubmitOrderActivity) context).receiveAddrId != 0) {
					Intent intent = new Intent(context, PayOrSendWayActivity.class);
					intent.putExtra("way", "send");
					intent.putExtra("isGroup", mIsGroup);
					intent.putExtra("position", list.get(position).getShopInfId());
					intent.putExtra("orgId", list.get(position).getOrgId() + "");
					((SubmitOrderActivity) context).startActivityForResult(intent, SubmitOrderActivity.TO_SEND_WAY);
				} else {
					Toast.makeText(context, "请选择收货人与收货地址", Toast.LENGTH_SHORT).show();
				}
			}
		});
		List<ShoppingCartProBean> listCartPro = new ArrayList<ShoppingCartProBean>();
		for (int i = 0; i < list.get(position).getItems().size(); i++) {
			if (list.get(position).getItems().get(i).getItemSelected() == true) {
				listCartPro.add(list.get(position).getItems().get(i));
			}
		}
		CommonAdapter<ShoppingCartProBean> adapter = new CommonAdapter<ShoppingCartProBean>(context, listCartPro,
				R.layout.item_submit_order_product) {

			@Override
			public void convert(com.beijing.beixin.adapter.ViewHolder helper, ShoppingCartProBean item) {
				helper.setText(R.id.textview_book_name, item.getName());
				helper.setText(R.id.textview_book_account, "×" + item.getQuantity());
				helper.setText(R.id.textview_book_price, "¥" + item.getProductUnitPrice());
				if (item.getIsPresent()) {
					helper.setVisiblity(R.id.textview_present, true);
				} else {
					helper.setVisiblity(R.id.textview_present, false);
				}
				if (item.getPresents() != null && item.getPresents().size() != 0) {
					String present = "";
					for (int i = 0; i < item.getPresents().size(); i++) {
						present = present + "赠品:" + item.getPresents().get(i).getName() + " x"
								+ item.getPresents().get(i).getQuantity() + "   ";
					}
					helper.setText(R.id.textview_present_name, present);
				} else {
					helper.setText(R.id.textview_present_name, "");
				}
				BitmapUtil util = new BitmapUtil();
				util.displayImage(context, (ImageView) helper.getView(R.id.imageview_book_name), item.getImage());
			}
		};
		holder.listview_submit_order.setAdapter(adapter);
		return convertView;
	}

	class ViewHolder {

		RelativeLayout layout_book_list;
		RelativeLayout layout_coupon;
		LinearLayout layout_book_detail;
		HorizontalListView layout_send_way;
		ImageView imageview_book_name;
		TextView textview_book_name;
		TextView textview_book_account;
		TextView textview_book_account2;
		TextView textview_book_price;
		TextView textview_disccount;
		TextView textview_yunfei;
		TextView textview_total;
		TextView textview_shopname;
		TextView textview_send_style;
		TextView textview_pay_style;
		TextView textview_ordersub_vocher_name;
		EditText edittext_remark;
		LinearLayout layout_pay_way;
		LinearLayout layout_send_way2;
		ExpandListView listview_submit_order;
	}

	private class MyWatcher implements TextWatcher {

		private View mView = null;

		public MyWatcher(View view) {
			mView = view;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (mView == null) {
				return;
			}
			Object object = mView.getTag();
			int pos2 = -1;
			if (object != null) {
				pos2 = (Integer) mView.getTag();
			}
			String remark = s.toString();
			if (pos2 >= 0 && pos2 < list.size() && !TextUtils.isEmpty(remark)) {
				list.get(pos2).setRemark(remark);
				saveRemark(list.get(pos2).getOrgId(), remark);
			}
		}
	}

	/**
	 * 保存备注
	 */
	public void saveRemark(Integer orgId, String remark) {
		BaseTask baseTask = new BaseTask(context);
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", "normal");
		params.addBodyParameter("orgId", orgId + "");
		params.addBodyParameter("remark", remark);
		baseTask.askCookieRequest(SystemConfig.SAVE_REMARK, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("saveRemark", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					if (obj.getBoolean("success")) {
						Log.e("saveRemark", "备注保存成功");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				ToastUtil.show(context, "备注保存失败");
			}
		});
	}
}
