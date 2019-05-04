package com.beijing.beixin.ui.myself.address;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.R.drawable;
import com.beijing.beixin.R.id;
import com.beijing.beixin.R.layout;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.AllProvinceList;
import com.beijing.beixin.pojo.SuccessBean;
import com.beijing.beixin.pojo.ZoneBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.utils.StringUtils;
import com.beijing.beixin.utils.ToastUtil;
import com.beijing.beixin.utils.Utils;
import com.beijing.beixin.utils.loginSDK.CheckUtils;
import com.beijing.beixin.widget.AreaDialog;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 新建收货地址
 * 
 * @author ouyanghao
 * 
 */
@SuppressWarnings("unused")
public class AddAddressActivity extends BaseActivity implements OnClickListener {

	/**
	 * 收货人姓名
	 */
	private EditText address_name;
	/**
	 * 收货人电话
	 */
	private EditText tel;
	/**
	 * 联动地区
	 */
	private LinearLayout ll_area;
	/**
	 * 省
	 */
	private TextView address;
	/**
	 * 市
	 */
	private TextView city;
	/**
	 * 街道
	 */
	private TextView street;
	/**
	 * 收货人详情地址
	 */
	private EditText address_info;
	/**
	 * 保存
	 */
	private Button bt_save_address;
	/**
	 * 选择联系人
	 */
	private TextView select_people;
	/**
	 * 地名ID
	 */
	private int pid;
	private int cid;
	private int sid;
	/**
	 * Zone实体集合
	 */
	private List<ZoneBean> zonelist = new ArrayList<ZoneBean>();
	private AreaDialog areaDialog;
	private List<AllProvinceList> list = new ArrayList<AllProvinceList>();
	private String maddressSize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_address);
		init();
		city();
	}

	/**
	 * 初始化
	 */
	public void init() {
		maddressSize = getIntent().getStringExtra("maddressSize");
		address_name = (EditText) findViewById(R.id.address_name);
		tel = (EditText) findViewById(R.id.tel);
		ll_area = (LinearLayout) findViewById(R.id.ll_area);
		address_info = (EditText) findViewById(R.id.address_info);
		bt_save_address = (Button) findViewById(R.id.bt_save_address);
		select_people = (TextView) findViewById(R.id.select_people);
		address = (TextView) findViewById(R.id.address);
		city = (TextView) findViewById(R.id.city);
		street = (TextView) findViewById(R.id.jie);
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		setNavigationTitle("新建地址");
		setNavigationRightBtnText("保存");
		select_people.setOnClickListener(this);
		ll_area.setOnClickListener(this);
		bt_save_address.setOnClickListener(this);
	}

	/**
	 * 新增收货地址
	 */
	public void saveOrUpdateAddess(boolean isDefault) {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		// 收货人
		if ("".equals(Utils.checkStr(address_name.getText().toString().trim()))) {
			ToastUtil.show(AddAddressActivity.this, "请填写收货人！");
			return;
		}
		// 手机号码
		if ("".equals(Utils.checkStr(tel.getText().toString().trim()))) {
			ToastUtil.show(AddAddressActivity.this, "请填写手机号码！");
			return;
		}
		if (!Utils.isMobileNO((tel.getText().toString().trim()))) {
			ToastUtil.show(AddAddressActivity.this, "手机号码不符合！");
			return;
		}
		// 所在区域
		if ("".equals(Utils.checkStr(address.getText().toString().trim()))) {
			ToastUtil.show(AddAddressActivity.this, "请选择所在省！");
			return;
		}
		if ("".equals(Utils.checkStr(city.getText().toString().trim()))) {
			ToastUtil.show(AddAddressActivity.this, "请选择所在市！");
			return;
		}
		if ("".equals(Utils.checkStr(street.getText().toString().trim()))) {
			ToastUtil.show(AddAddressActivity.this, "请选择所在区！");
			return;
		}
		// 详细地址
		if ("".equals(Utils.checkStr(address_info.getText().toString().trim()))) {
			ToastUtil.show(AddAddressActivity.this, "请填写详细地址！");
			return;
		}
		params.addBodyParameter("name", address_name.getText().toString().trim());
		params.addBodyParameter("mobile", tel.getText().toString().trim());
		params.addBodyParameter("zoneId", sid + "");
		params.addBodyParameter("addr", address_info.getText().toString().trim());
		// 是否为默认地址
		params.addBodyParameter("isDefault", isDefault + "");
		showDialog("正在保存。。。");
		baseTask.askCookieRequest(SystemConfig.ADDRESS_SAVEORUPDATE, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject jsonObject = new JSONObject(arg0.result);
					SuccessBean bean = JSON.parseObject(jsonObject.toString(), SuccessBean.class);
					if (bean.isSuccess()) {
						dismissDialog();
						showToast("新建成功");
						finish();
					} else {
						ToastUtil.show(AddAddressActivity.this, "新建失败");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(AddAddressActivity.this, "新建失败");
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case Activity.RESULT_OK:
			try {
				if (data == null) {
					return;
				}
				String phoneNumber = null;
				Uri contactData = data.getData();
				if (contactData == null) {
					return;
				}
				@SuppressWarnings("deprecation")
				Cursor cursor = managedQuery(contactData, null, null, null, null);
				if (cursor == null) {
					return;
				}
				if (cursor.getCount() == 0) {
					showToast("您已设置禁止访问联系人，请更改设置");
					return;
				}
				if (cursor.moveToFirst()) {
					String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					String hasPhone = cursor
							.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
					String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
					if (hasPhone.equalsIgnoreCase("1")) {
						hasPhone = "true";
					} else {
						hasPhone = "false";
					}
					if (Boolean.parseBoolean(hasPhone)) {
						Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
								null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
						while (phones.moveToNext()) {
							phoneNumber = phones
									.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							address_name.setText(name);
							String mobile = phoneNumber.toString().trim().replace(" ", "");
							tel.setText(mobile);
						}
						phones.close();
					}
				}
			} catch (Exception e) {
				return;
			}
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_save_address:
			break;
		case R.id.select_people:
			Intent intent2 = new Intent(Intent.ACTION_PICK, android.provider.ContactsContract.Contacts.CONTENT_URI);
			startActivityForResult(intent2, 0);
			break;
		case R.id.ll_area:
			initAreaDialog();
			break;
		}
	}

	@Override
	public void rightButtonOnClick() {
		if ("0".equals(maddressSize)) {
			saveOrUpdateAddess(true);
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage("是否设为默认地址？");
			builder.setNegativeButton("否", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					saveOrUpdateAddess(false);
				}
			});
			builder.setPositiveButton("是", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					saveOrUpdateAddess(true);
				}
			});
			builder.create().show();
		}
	}

	@Override
	public void leftButtonOnClick() {
		finish();
	}

	/**
	 * 2.1.21.地址AJAX联动查询
	 */
	public void city() {
		showDialog("正在加载中...");
		BaseTask baseTask = new BaseTask(this);
		baseTask.askCookieRequest(SystemConfig.ZONE_LIST, null, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject jsonObject = new JSONObject(arg0.result);
					JSONArray array = jsonObject.getJSONArray("result");
					list = JSON.parseArray(array.toString(), AllProvinceList.class);
					dismissDialog();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
			}
		});
	}

	private void initAreaDialog() {
		areaDialog = new AreaDialog(AddAddressActivity.this, list);
		areaDialog.show();
		areaDialog.getOkButton().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				areaDialog.dismiss();
				address.setText(areaDialog.getProvinceName());
				city.setText(areaDialog.getmCityName());
				street.setText(areaDialog.getmDistrictName());
				sid = areaDialog.getmDistrictId();
				// showToast(areaDialog.getmDistrictName() + sid);
			}
		});
	}
}
