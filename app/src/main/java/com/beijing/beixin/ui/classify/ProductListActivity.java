package com.beijing.beixin.ui.classify;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.HomeSpecialListAdapter;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.NewProductBean;
import com.beijing.beixin.pojo.NewProductBean.product;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.utils.LogUtil;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class ProductListActivity extends BaseActivity {

	private String mTitle;
	private TextView mShowNoDateTView;
	private ListView mListView;
	private HomeSpecialListAdapter myArrayAdapter = null;
	private String mTitleType;
	private String skuId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list);
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		mShowNoDateTView = (TextView) findViewById(R.id.tv_main_no_data2);
		mListView = (ListView) findViewById(R.id.listview);
		myArrayAdapter = new HomeSpecialListAdapter(this, R.layout.item_new_book_list, null, false);
		mListView.setAdapter(myArrayAdapter);
		mListView.setOnItemClickListener(myArrayAdapter);
		Intent intent = getIntent();
		mTitle = intent.getStringExtra("title");
		skuId = intent.getStringExtra("skuId");
		mTitleType = intent.getStringExtra("titleType");
		setNavigationTitle(mTitle);
		sendhttpHOT();
	}

	private void sendhttpHOT() {
		showDialog("正在加载数据中...");
		BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", "2");
		params.addBodyParameter("titleType", mTitleType);
		params.addBodyParameter("skuId", skuId);
		params.addBodyParameter("moduleTitle", "bf_feature_center_links");
		params.addBodyParameter("productLeftNm", "bf_feature_center_recommend_tab");
		task.askNormalRequest(SystemConfig.INDEX_PRODUCTS, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				LogUtil.e("ResponseInfo", arg0.result.toString());
				try {
					JSONObject jsonObject = new JSONObject(arg0.result.toString());
					ArrayList<NewProductBean> newProductBeans = (ArrayList<NewProductBean>) JSON
							.parseArray(jsonObject.getString("hots_products").toString(), NewProductBean.class);
					if (newProductBeans == null || newProductBeans.size() == 0
							|| newProductBeans.get(0).getProducts() == null
							|| newProductBeans.get(0).getProducts().size() == 0) {
						mShowNoDateTView.setText("该类商品暂无数据");
						mShowNoDateTView.setVisibility(View.VISIBLE);
						mListView.setVisibility(View.GONE);
						return;
					}
					mShowNoDateTView.setVisibility(View.GONE);
					mListView.setVisibility(View.VISIBLE);
					NewProductBean newProductBean = newProductBeans.get(0);
					List<product> arrayList = newProductBean.getProducts();
					product[] products = new product[arrayList.size()];
					products = arrayList.toArray(products);
					myArrayAdapter.OnRefresh(products);
					dismissDialog();
				} catch (JSONException e) {
					dismissDialog();
					mShowNoDateTView.setText("获取失败");
					mShowNoDateTView.setVisibility(View.VISIBLE);
					mListView.setVisibility(View.GONE);
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				mShowNoDateTView.setText("获取失败");
				mShowNoDateTView.setVisibility(View.VISIBLE);
				mListView.setVisibility(View.GONE);
			}
		});
	}
}
