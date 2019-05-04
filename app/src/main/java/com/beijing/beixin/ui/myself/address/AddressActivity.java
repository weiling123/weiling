package com.beijing.beixin.ui.myself.address;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.ViewHolder;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.AddressBean;
import com.beijing.beixin.pojo.SuccessBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.myself.order.OrderActivity;
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
public class AddressActivity extends BaseActivity implements OnClickListener {

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
	private BitmapUtil bitmapUtil = null;
	/**
	 * 默认地址
	 */
	private TextView default_address;
	private TextView right_save;
	private ImageView left_back;
	private int maddressSize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address);
		init();
		showDialog("正在获取地址，请稍后...");
		getaddesslist();
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
		mListView = (ListView) findViewById(R.id.address_list);
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		setNavigationTitle("收货地址管理");
		setNavigationRightBtnText("新增");
		// right_save = (TextView) findViewById(R.id.right_save);
		// left_back = (ImageView) findViewById(R.id.left_back);
		// left_back.setOnClickListener(this);
		// right_save.setOnClickListener(this);
		bitmapUtil = new BitmapUtil();
	}

	/**
	 * 获取收货地址列表
	 */
	public void getaddesslist() {
		BaseTask baseTask = new BaseTask(this);
		baseTask.askCookieRequest(SystemConfig.ADDRESS_LIST, null, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject object = new JSONObject(arg0.result);
					JSONArray array = object.getJSONArray("result");
					addresslist = JSON.parseArray(array.toString(), AddressBean.class);
					if (addresslist.size() != 0) {
						getdata();
						dismissDialog();
					} else {
						getdata();
						dismissDialog();
						ToastUtil.show(AddressActivity.this, "暂无地址");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(AddressActivity.this, "获取地址失败");
			}
		});
	}

	/**
	 * 删除地址
	 * 
	 * @param id
	 */
	public void deladdess(String id) {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("id", id);
		showDialog("正在删除。。。");
		baseTask.askCookieRequest(SystemConfig.ADDESS_DEL, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject jsonObject = new JSONObject(arg0.result);
					SuccessBean bean = JSON.parseObject(jsonObject.toString(), SuccessBean.class);
					if (bean.isSuccess()) {
						dismissDialog();
						getaddesslist();
						ToastUtil.show(AddressActivity.this, "删除成功");
					} else {
						ToastUtil.show(AddressActivity.this, "删除失败");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				dismissDialog();
				ToastUtil.show(AddressActivity.this, "删除失败");
			}
		});
	}

	/**
	 * 填充适配器
	 */
	public void getdata() {
		mListView.setAdapter(mAdapter = new CommonAdapter<AddressBean>(this, addresslist, R.layout.item_address) {

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
					helper.setText(R.id.address, item.getAddressPath().replace("-", "").trim() + item.getAddr());
				}
				if ("Y".equals(item.getIsDefault())) {
					helper.getView(R.id.default_address).setVisibility(View.VISIBLE);
					helper.setImageResource(R.id.iv_default_address, R.drawable.select_r);
				} else {
					helper.getView(R.id.default_address).setVisibility(View.GONE);
					helper.setImageResource(R.id.iv_default_address, R.drawable.select_g);
				}
				helper.getView(R.id.ll_edit).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(AddressActivity.this, UpdateAddressActivity.class);
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
				// 删除地址
				helper.getView(R.id.ll_del).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);
						builder.setTitle("提示");
						builder.setMessage("是否删除地址？");
						builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
						builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								deladdess(item.getReceiveAddrId() + "");
								mAdapter.notifyDataSetChanged();
							}
						});
						builder.create().show();
					}
				});
				// 修改默认地址
				helper.getView(R.id.iv_default_address).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if ("Y".equals(item.getIsDefault())) {
							helper.setImageResource(R.id.iv_default_address, R.drawable.select_r);
							UpdateAddress(item.getReceiveAddrId() + "", item.getAddressPath(), item.getName(),
									item.getZoneId() + "", item.getAddr(), item.getMobile(), "false");
						} else {
							helper.setImageResource(R.id.iv_default_address, R.drawable.select_g);
							UpdateAddress(item.getReceiveAddrId() + "", item.getAddressPath(), item.getName(),
									item.getZoneId() + "", item.getAddr(), item.getMobile(), "true");
						}
					}
				});
			}
		});
	}

	/**
	 * 修改默认地址
	 * 
	 * @param addressPath
	 * @param name
	 * @param zoneId
	 * @param addr
	 * @param mobile
	 * @param isDefault
	 */
	public void UpdateAddress(String receiveAddrId, String addressPath, String name, String zoneId, String addr,
			String mobile, String isDefault) {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("receiveAddrId", receiveAddrId);
		params.addBodyParameter("addressPath", addressPath);
		params.addBodyParameter("name", name);
		params.addBodyParameter("zoneId", zoneId);
		params.addBodyParameter("addr", addr);
		params.addBodyParameter("mobile", mobile);
		params.addBodyParameter("isDefault", isDefault);
		showDialog("正在修改...");
		baseTask.askCookieRequest(SystemConfig.ADDRESS_SAVEORUPDATE, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject = new JSONObject(arg0.result);
					if (jsonObject.getBoolean("success")) {
						getaddesslist();
						dismissDialog();
					} else {
						ToastUtil.show(AddressActivity.this, "修改失败");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(AddressActivity.this, "修改失败");
			}
		});
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
		Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
		intent.putExtra("maddressSize", maddressSize + "");
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
