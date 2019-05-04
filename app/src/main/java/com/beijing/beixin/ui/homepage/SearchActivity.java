package com.beijing.beixin.ui.homepage;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.ViewHolder;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.HotSeachBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.classify.ClassifyProductActivity;
import com.beijing.beixin.utils.ExpandListView;
import com.beijing.beixin.utils.MyGridView;
import com.beijing.beixin.utils.Utils;
import com.beijing.beixin.utils.sqlite.ProductSearch;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class SearchActivity extends BaseActivity implements OnClickListener {

	private ExpandListView listview_history_record;
	private MyGridView gridview_history_record;
	private ImageView image_search;
	private EditText et_component_search;
	private TextView tv_clear;
	private ImageView imageview_scancode;
	private ImageView iv_close;
	private List<HotSeachBean> hotlist = new ArrayList<HotSeachBean>();
	private String search = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initView();
		getHotSeach();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// et_component_search.setText("");
		MyApplication.mServer.findAllProduct();
		getsearchlist();
	}

	private void initView() {
		search = getIntent().getStringExtra("search");
		et_component_search = (EditText) findViewById(R.id.et_component_search);
		image_search = (ImageView) findViewById(R.id.image_search);
		imageview_scancode = (ImageView) findViewById(R.id.imageview_scancode);
		iv_close = (ImageView) findViewById(R.id.iv_close);
		tv_clear = (TextView) findViewById(R.id.tv_clear);
		image_search.setOnClickListener(this);
		iv_close.setOnClickListener(this);
		tv_clear.setOnClickListener(this);
		imageview_scancode.setOnClickListener(this);
		gridview_history_record = (MyGridView) findViewById(R.id.gridview_history_record);
		listview_history_record = (ExpandListView) findViewById(R.id.listview_history_record);
		getsearchlist();
		if (search != null) {
			et_component_search.setHint(search);
		}
	}

	public void getsearchlist() {
		CommonAdapter<ProductSearch> adapter = new CommonAdapter<ProductSearch>(this,
				MyApplication.mServer.findAllProduct(), R.layout.item_common_textview) {

			@Override
			public void convert(ViewHolder helper, final ProductSearch item) {
				helper.setText(R.id.textview_item_classify_right, item.getProductname());
				helper.getConvertView().setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(SearchActivity.this, ClassifyProductActivity.class);
						intent.putExtra("search", item.getProductname());
						intent.putExtra("type", "type");
						startActivity(intent);
					}
				});
			}
		};
		listview_history_record.setAdapter(adapter);
	}

	private void gethotadapter(List<HotSeachBean> hotlist) {
		CommonAdapter<HotSeachBean> adapter = new CommonAdapter<HotSeachBean>(this, hotlist, R.layout.item_hotseach) {

			@Override
			public void convert(ViewHolder helper, final HotSeachBean item) {
				helper.setText(R.id.item_name, item.getTitle());
				helper.getConvertView().setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(SearchActivity.this, ClassifyProductActivity.class);
						intent.putExtra("search", item.getTitle());
						intent.putExtra("type", "type");
						startActivity(intent);
					}
				});
			}
		};
		gridview_history_record.setAdapter(adapter);
	}

	/**
	 * 热门搜索
	 */
	public void getHotSeach() {
		BaseTask task = new BaseTask(this);
		task.askCookieRequest(SystemConfig.HOTKEYWORY, null, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("热搜", arg0.result.toString());
				try {
					JSONObject jsonObject = new JSONObject(arg0.result.toString());
					hotlist = JSON.parseArray(jsonObject.getString("result").toString(), HotSeachBean.class);
					if (hotlist != null) {
						gethotadapter(hotlist);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("热搜异常", arg0.toString());
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_search:
			Intent intent = new Intent(SearchActivity.this, ClassifyProductActivity.class);
			intent.putExtra("type", "type");
			if ("".equals(Utils.checkStr(et_component_search.getText().toString()))) {
				intent.putExtra("search", search);
			} else {
				intent.putExtra("search", et_component_search.getText().toString());
			}
			startActivity(intent);
			break;
		case R.id.tv_clear:
			MyApplication.mServer.delProduct();
			getsearchlist();
			break;
		case R.id.imageview_scancode:
			finish();
			break;
		case R.id.iv_close:
			et_component_search.setText("");
			break;
		}
	}
}
