package com.beijing.beixin.ui.shoppingcart;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.MyCouponAdater;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.MyCouponBean;
import com.beijing.beixin.pojo.ShoppingCartBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.utils.ToastUtil;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class CouponActivity extends BaseActivity {

	/**
	 * 传过来的整个实体
	 */
	private List<MyCouponBean.coupon> list = new ArrayList<MyCouponBean.coupon>();
	/**
	 * 优惠券列表
	 */
	private ListView listview_counpon;
	private List<MyCouponBean.coupon> myCouponBean = new ArrayList<MyCouponBean.coupon>();
	private MyCouponAdater mAdater = null;
	private String orgId = "";
	private int position;
	private String mCouponId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counpon);
		initView();
		initData();
		setListener();
	}

	private void initView() {
		setNavigationTitle("优惠券");
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		initListView();
	}

	private void initListView() {
		listview_counpon = (ListView) findViewById(R.id.listview_counpon);
		mAdater = new MyCouponAdater(this);
		// MyCouponBean.coupon coupon = new MyCouponBean.coupon();
		// coupon.setBatchNm("不使用优惠券");
		// list.add(coupon);
		mAdater.setData(list, mCouponId);
		listview_counpon.setAdapter(mAdater);
	}

	private void initData() {
		orgId = getIntent().getStringExtra("orgId");
		position = getIntent().getIntExtra("position", 0);
		getCounponList(orgId);
	}

	private void setListener() {
		listview_counpon.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (!"".equals(mCouponId)) {
					cancelUserCoupon(orgId);
				} else {
					userCoupon(orgId, myCouponBean.get(position).getCouponId());
				}
			}
		});
	}

	/**
	 * 优惠券
	 */
	public void getCounponList(String orgId) {
		showDialog("正在请求优惠券列表数据，请稍后...");
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", "normal");
		params.addBodyParameter("orgId", orgId + "");
		baseTask.askCookieRequest(SystemConfig.WEB_COUPON_LIST, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("counpon", arg0.result.toString());
				JSONObject obj;
				try {
					dismissDialog();
					obj = new JSONObject(arg0.result.toString());
					if (obj.get("useCouponId") != null && !"null".equals(obj.getString("useCouponId"))) {
						mCouponId = obj.getString("useCouponId");
					}
					myCouponBean = JSON.parseArray(obj.getString("result"), MyCouponBean.coupon.class);
					list.addAll(myCouponBean);
					if (myCouponBean != null) {
						mAdater.setData(list, mCouponId);
						mAdater.notifyDataSetChanged();
					}
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(CouponActivity.this, "获取可用优惠券失败");
			}
		});
	}

	/**
	 * 使用优惠券
	 */
	public void userCoupon(String orgId, final String couponId) {
		showDialog("正在使用优惠券，请稍后...");
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", "normal");
		params.addBodyParameter("orgId", orgId + "");
		params.addBodyParameter("couponId", couponId + "");
		baseTask.askCookieRequest(SystemConfig.USER_COUPON, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("counpon", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					ShoppingCartBean bean = JSON.parseObject(obj.get("result").toString(), ShoppingCartBean.class);
					if (obj.getBoolean("success")) {
						if (bean != null && bean.getShoppingCartVos() != null
								&& bean.getShoppingCartVos().get(0).getItems() != null) {
							showToast("使用优惠券成功");
							Intent intent = new Intent();
							Bundle bundle = new Bundle();
							bundle.putInt("position", position);
							bundle.putString("couponId", couponId);
							bundle.putSerializable("ShoppingCartBean", bean);
							intent.putExtras(bundle);
							setResult(1, intent);
							finish();
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(CouponActivity.this, "使用优惠券失败");
			}
		});
	}

	/**
	 * 取消使用优惠券
	 * 
	 * @param orgId
	 * @param couponId
	 */
	public void cancelUserCoupon(String orgId) {
		if ("".equals(mCouponId)) {
			showToast("您还未使用优惠券");
			return;
		}
		showDialog("正在取消使用优惠券，请稍后...");
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", "normal");
		params.addBodyParameter("orgId", orgId + "");
		params.addBodyParameter("couponId", mCouponId + "");
		baseTask.askCookieRequest(SystemConfig.CANCEL_USER_COUPON, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("counpon", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					ShoppingCartBean bean = JSON.parseObject(obj.get("result").toString(), ShoppingCartBean.class);
					if (obj.getBoolean("success")) {
						if (bean != null && bean.getShoppingCartVos() != null
								&& bean.getShoppingCartVos().get(0).getItems() != null) {
							showToast("取消使用优惠券成功");
							Intent intent = new Intent();
							Bundle bundle = new Bundle();
							bundle.putInt("position", position);
							bundle.putString("couponId", "");
							bundle.putSerializable("ShoppingCartBean", bean);
							intent.putExtras(bundle);
							setResult(1, intent);
							finish();
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(CouponActivity.this, "取消使用优惠券失败");
			}
		});
	}
}
