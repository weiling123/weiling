package com.beijing.beixin.ui.shoppingcart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.ViewHolder;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.AddressBean;
import com.beijing.beixin.pojo.SubmitOrderAddressBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.myself.address.AddAddressActivity;
import com.beijing.beixin.ui.myself.address.UpdateAddressActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.ToastUtil;
import com.beijing.beixin.utils.Utils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 收货地址
 * 
 * @author ouyanghao
 * 
 */
public class SubmitOrderAddressActivity extends BaseActivity {

	/**
	 * 收货地址显示
	 */
	private ListView mListView;
	/**
	 * 适配器
	 */
	private CommonAdapter<AddressBean> mAdapter;
	/**
	 * 地址集合
	 */
	private List<AddressBean> addresslist = new ArrayList<AddressBean>();
	private List<SubmitOrderAddressBean> sendWaylist = new ArrayList<SubmitOrderAddressBean>();
	private BitmapUtil bitmapUtil = null;
	/**
	 * 新建地址
	 */
	private Button addaddress;
	/**
	 * 默认地址
	 */
	private TextView default_address;
	private AddressBean address;
	private int maddressSize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address);
		init();
		showDialog("正在获取地址，请稍后...");
	}

	@Override
	protected void onResume() {
		super.onResume();
		getaddesslist();
	}

	/**
	 * 初始化
	 */
	public void init() {
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		setNavigationTitle("收货地址管理");
		setNavigationRightBtnText("新增");
		mListView = (ListView) findViewById(R.id.address_list);
		addaddress = (Button) findViewById(R.id.bt_add_address);
		bitmapUtil = new BitmapUtil();
	}

	@Override
	public void setLeftNavigatoinOnClick() {
		super.setLeftNavigatoinOnClick();
		finish();
	}

	@Override
	public void rightButtonOnClick() {
		super.rightButtonOnClick();
		if (addresslist.size() == 10) {
			showToast("用户地址不允许多于10个");
			return;
		}
		if (addresslist.size() != 0) {
			maddressSize++;
		} else {
			maddressSize = 0;
		}
		Intent intent = new Intent(SubmitOrderAddressActivity.this, AddAddressActivity.class);
		intent.putExtra("maddressSize", maddressSize + "");
		startActivity(intent);
	}

	/**
	 * 获取收货地址列表
	 */
	public void getaddesslist() {
		BaseTask baseTask = new BaseTask(this);
		baseTask.askCookieRequest(SystemConfig.RECEIVE_ADDRESS, null, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("获取地址信息", arg0.result.toString());
				try {
					JSONObject object = new JSONObject(arg0.result);
					JSONArray array = object.getJSONArray("result");
					addresslist = JSON.parseArray(array.toString(), AddressBean.class);
					if (addresslist.size() != 0) {
						getdata();
						dismissDialog();
						// addaddress.setOnClickListener(new AddAddress());
					} else {
						getdata();
						dismissDialog();
						ToastUtil.show(SubmitOrderAddressActivity.this, "暂无地址");
						// addaddress.setOnClickListener(new AddAddress());
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(SubmitOrderAddressActivity.this, "获取地址失败");
			}
		});
	}

	/**
	 * 保存收货地址
	 */
	public void updateReceiver(String receiveAddrId) {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", "normal");
		params.addBodyParameter("receiveAddrId", receiveAddrId);
		baseTask.askCookieRequest(SystemConfig.UPDATE_RECEIVER, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("选择订单地址", arg0.result.toString());
				try {
					JSONObject object = new JSONObject(arg0.result);
					if (object.getBoolean("success")) {
						showToast("保存成功");
						org.json.JSONArray array = object.getJSONArray("result");
						sendWaylist = JSON.parseArray(array.toString(), SubmitOrderAddressBean.class);
						Intent intent = new Intent();
						Bundle bundle = new Bundle();
						bundle.putSerializable("addressBean", address);
						bundle.putSerializable("sendWayList", (Serializable) sendWaylist);
						intent.putExtras(bundle);
						setResult(1, intent);
						finish();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(SubmitOrderAddressActivity.this, "获取地址失败");
			}
		});
	}

	/**
	 * 填充适配器
	 */
	public void getdata() {
		mListView
				.setAdapter(mAdapter = new CommonAdapter<AddressBean>(this, addresslist, R.layout.item_submit_address) {

					@Override
					public void convert(final ViewHolder helper, final AddressBean item) {
						if (!"".equals(Utils.checkStr(item.getName()))) {
							helper.setText(R.id.address_name, item.getName());
						}
						if (!"".equals(Utils.checkStr(item.getMobile()))) {
							helper.setText(R.id.address_tel, item.getMobile().substring(0, 3) + "****"
									+ item.getMobile().substring(7, item.getMobile().length()));
						}
						if (!"".equals(Utils.checkStr(item.getAddr()))) {
							helper.setText(R.id.address, item.getAddressPath().replace("-", " ") + item.getAddr());
						}
						if ("Y".equals(item.getIsDefault())) {
							helper.getView(R.id.default_address).setVisibility(View.VISIBLE);
							helper.setImageResource(R.id.iv_default_address, R.drawable.select_r);
						} else {
							helper.getView(R.id.default_address).setVisibility(View.GONE);
							helper.setImageResource(R.id.iv_default_address, R.drawable.select_g);
						}
						helper.getView(R.id.tv_update).setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								Intent intent = new Intent(SubmitOrderAddressActivity.this,
										UpdateAddressActivity.class);
								intent.putExtra("name", item.getName());
								intent.putExtra("mobile", item.getMobile());
								intent.putExtra("address", item.getAddr());
								intent.putExtra("area", item.getAddressPath());
								intent.putExtra("addid", item.getReceiveAddrId() + "");
								intent.putExtra("isDefaults", item.getIsDefault());
								intent.putExtra("zoneid", item.getZoneId() + "");
								startActivity(intent);
							}
						});
					}
				});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				address = addresslist.get(position);
				updateReceiver(addresslist.get(position).getReceiveAddrId() + "");
			}
		});
	}
}
