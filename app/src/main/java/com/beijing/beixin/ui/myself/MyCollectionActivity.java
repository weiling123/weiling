package com.beijing.beixin.ui.myself;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.ClassifyProductAdater;
import com.beijing.beixin.adapter.CollectionAdater;
import com.beijing.beixin.adapter.CollectionShopAdater;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.ListItemClickHelp;
import com.beijing.beixin.adapter.ViewHolder;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.AppShopBean;
import com.beijing.beixin.pojo.ProductCollectionBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.classify.ClassifyProductActivity;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.ui.shoppingcart.ShopActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.Utils;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshListView;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase.Mode;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase.OnRefreshListener2;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class MyCollectionActivity extends BaseActivity implements OnClickListener, ListItemClickHelp {

	/**
	 * 刷新
	 */
	private PullToRefreshListView mPullRefreshListView;
	private ListView actualListView;
	private ListView listview_my_collection;
	private TextView navigation_title;
	private TextView navigation_title2;
	private CollectionAdater mCollectionAdater;
	private CollectionShopAdater mCollectionShopAdater;
	private List<ProductCollectionBean> proList = new ArrayList<ProductCollectionBean>();
	private List<AppShopBean> appshopList = new ArrayList<AppShopBean>();
	private int pagesize = 10;
	private int Onlysize = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_collection);
		initView();
		initData();
	}

	private void initView() {
		ImageView navigationLeftImageBtn = (ImageView) findViewById(R.id.left_back);
		navigationLeftImageBtn.setOnClickListener(this);
		navigation_title = (TextView) findViewById(R.id.navigation_title);
		navigation_title.setOnClickListener(this);
		navigation_title2 = (TextView) findViewById(R.id.navigation_title2);
		navigation_title2.setOnClickListener(this);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.listview_my_collection);
		actualListView = mPullRefreshListView.getRefreshableView();
		registerForContextMenu(actualListView);
		mPullRefreshListView.setMode(Mode.BOTH);
		// initListView();
	}

	/**
	 * 初始化主界面的listview
	 * 
	 * @param view
	 */
	private void initPullRefreshListViewpro() {
		mCollectionAdater = new CollectionAdater(this, this);
		mCollectionAdater.setData(proList);
		actualListView.setAdapter(mCollectionAdater);
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				pagesize = 10;
				productCollect(pagesize);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				try {
					pagesize += Onlysize;
					productCollect(pagesize);
				} catch (Exception e) {
					mPullRefreshListView.onRefreshComplete();
				}
			}
		});
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			}
		});
	}

	/**
	 * 初始化主界面的listview
	 * 
	 * @param view
	 */
	private void initPullRefreshListViewshop() {
		mCollectionShopAdater = new CollectionShopAdater(this, this);
		mCollectionShopAdater.setData(appshopList);
		actualListView.setAdapter(mCollectionShopAdater);
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				pagesize = 10;
				shopCollect(pagesize);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				try {
					pagesize += Onlysize;
					shopCollect(pagesize);
				} catch (Exception e) {
					mPullRefreshListView.onRefreshComplete();
				}
			}
		});
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			}
		});
	}

	private void initListView() {
		listview_my_collection = (ListView) findViewById(R.id.listview_my_collection);
	}

	private void initData() {
		String flag = getIntent().getStringExtra("flag");
		if ("pro".equals(flag)) {
			pagesize = 10;
			navigation_title.setTextColor(getResources().getColor(R.color.common_dark_red));
			navigation_title2.setTextColor(getResources().getColor(R.color.common_tv_black));
			productCollect(pagesize);
			initPullRefreshListViewpro();
		} else {
			pagesize = 10;
			navigation_title.setTextColor(getResources().getColor(R.color.common_tv_black));
			navigation_title2.setTextColor(getResources().getColor(R.color.common_dark_red));
			shopCollect(pagesize);
			initPullRefreshListViewshop();
		}
	}

	/**
	 * 宝贝
	 */
	private void productCollect(int limit) {
		showDialog("正在请求数据，请稍后...");
		BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("page", "1");
		params.addBodyParameter("limit", limit + "");
		task.askCookieRequest(SystemConfig.PRODUCT_COLLECT, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					Log.e("collectionPro", obj.toString());
					proList = JSON.parseArray(obj.get("result").toString(), ProductCollectionBean.class);
					mCollectionAdater.setData(proList);
					mCollectionAdater.notifyDataSetChanged();
					mPullRefreshListView.onRefreshComplete();
					dismissDialog();
				} catch (JSONException e) {
					e.printStackTrace();
					mPullRefreshListView.onRefreshComplete();
					dismissDialog();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("收藏商品列表异常", arg0.toString());
				mPullRefreshListView.onRefreshComplete();
				dismissDialog();
			}
		});
	}

	/**
	 * 店铺
	 */
	private void shopCollect(int limit) {
		showDialog("正在请求数据，请稍后...");
		BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("page", "1");
		params.addBodyParameter("limit", limit + "");
		task.askCookieRequest(SystemConfig.SHOP_COLLECT, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					Log.e("collectionPro", obj.toString());
					appshopList = JSON.parseArray(obj.get("result").toString(), AppShopBean.class);
					mCollectionShopAdater.setData(appshopList);
					mCollectionShopAdater.notifyDataSetChanged();
					mPullRefreshListView.onRefreshComplete();
					dismissDialog();
				} catch (JSONException e) {
					e.printStackTrace();
					mPullRefreshListView.onRefreshComplete();
					dismissDialog();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				showToast("获取收藏店铺列表失败");
				mPullRefreshListView.onRefreshComplete();
				dismissDialog();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_back:
			finish();
			break;
		case R.id.navigation_title:
			productCollect(pagesize);
			navigation_title.setTextColor(getResources().getColor(R.color.common_dark_red));
			navigation_title2.setTextColor(getResources().getColor(R.color.common_tv_black));
			initPullRefreshListViewpro();
			break;
		case R.id.navigation_title2:
			shopCollect(pagesize);
			navigation_title.setTextColor(getResources().getColor(R.color.common_tv_black));
			navigation_title2.setTextColor(getResources().getColor(R.color.common_dark_red));
			initPullRefreshListViewshop();
			break;
		}
	}

	@Override
	public void onItemClick(View item, View widget, int position, int which) {
		// TODO Auto-generated method stub
	}
}
