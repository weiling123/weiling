package com.beijing.beixin.ui.myself.address;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.R.layout;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.ViewHolder;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.ZoneBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class AreaActivity extends BaseActivity {

	private ListView mListView;
	private CommonAdapter<ZoneBean> mAdapter;
	private List<ZoneBean> list = new ArrayList<ZoneBean>();
	private String type;
	private String cid;
	private String sid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_area);
		init();
	}

	public void init() {
		mListView = (ListView) findViewById(R.id.arealist);
		Intent intent = getIntent();
		type = intent.getStringExtra("type");
		if ("province".equals(type)) {
			province();
		}
		if ("city".equals(type)) {
			cid = intent.getStringExtra("cid");
			city(cid);
		}
		if ("street".equals(type)) {
			sid = intent.getStringExtra("sid");
			city(sid);
		}
	}

	/**
	 * 2.1.21.地址AJAX联动查询
	 */
	public void province() {
		showDialog("正在加载中...");
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		baseTask.askCookieRequest(SystemConfig.ZONE, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject jsonObject = new JSONObject(arg0.result);
					JSONArray array = jsonObject.getJSONArray("result");
					list = JSON.parseArray(array.toString(), ZoneBean.class);
					getAreadata();
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

	/**
	 * 2.1.21.地址AJAX联动查询
	 */
	public void city(String id) {
		showDialog("正在加载中...");
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("rootId", id);
		baseTask.askCookieRequest(SystemConfig.ZONE, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject jsonObject = new JSONObject(arg0.result);
					JSONArray array = jsonObject.getJSONArray("result");
					list = JSON.parseArray(array.toString(), ZoneBean.class);
					getAreadata();
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

	public void getAreadata() {
		mListView.setAdapter(mAdapter = new CommonAdapter<ZoneBean>(this, list, R.layout.item_area) {

			@Override
			public void convert(ViewHolder helper, final ZoneBean item) {
				helper.setText(R.id.area_name, item.getName());
				helper.getConvertView().setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent();
						intent.putExtra("area_name", item.getName());
						intent.putExtra("area_id", item.getId());
						if ("province".equals(type)) {
							setResult(1, intent);
						}
						if ("city".equals(type)) {
							setResult(2, intent);
						}
						if ("street".equals(type)) {
							setResult(3, intent);
						}
						finish();
					}
				});
			}
		});
	}
}
