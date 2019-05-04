package com.beijing.beixin.ui.shoppingcart;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.ListItemClickHelp;
import com.beijing.beixin.adapter.ShopAdater;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.ShopBean;
import com.beijing.beixin.pojo.ShopInfoBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.classify.ClassifyProductActivity;
import com.beijing.beixin.ui.myself.login.LoginActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.ToastUtil;
import com.beijing.beixin.utils.Utils;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshListView;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase.Mode;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase.OnRefreshListener2;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 店铺
 * 
 * @author ouyanghao
 * 
 */
@SuppressWarnings("unused")
public class ShopActivity extends BaseActivity implements OnClickListener, ListItemClickHelp {

	/**
	 * 店铺icon
	 */
	private ImageView shop_icon;
	/**
	 * 店铺名称
	 */
	private TextView shop_name;
	/**
	 * 关注人数
	 */
	private TextView shop_people_count;
	/**
	 * 全部商品
	 */
	private TextView shop_all_count;
	/**
	 * 上新
	 */
	private TextView shop_new;
	/**
	 * 促销
	 */
	private TextView shop_hot;
	private TextView tv_main_no_data2;
	/**
	 * 收藏
	 */
	private ImageView collection;
	private String collectionstr;
	/**
	 * 分享
	 */
	private LinearLayout ll_shop_share;
	/**
	 * 返回
	 */
	private ImageView iv_back;
	/**
	 * 展示数据
	 */
	private String shopId;
	private List<ShopBean.shopproduct> list = new ArrayList<ShopBean.shopproduct>();
	private ShopBean msBean = new ShopBean();
	private ShopInfoBean shopinfoBean = null;
	private ShopAdater mAdater = null;
	/**
	 * 搜索框值
	 */
	private String search = "";
	int page = 1;
	int pagesize = 10;
	int Onlysize = 5;
	/**
	 * 刷新
	 */
	private PullToRefreshListView mPullRefreshListView;
	private ListView actualListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);
		init();
		showDialog("正在加载中。。。");
		sendhttpinfo();
	}

	public void init() {
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		Intent intent = getIntent();
		shopId = intent.getStringExtra("shopId");
		shop_icon = (ImageView) findViewById(R.id.shop_icon);
		shop_name = (TextView) findViewById(R.id.shop_name);
		shop_people_count = (TextView) findViewById(R.id.shop_people_count);
		shop_all_count = (TextView) findViewById(R.id.shop_all_count);
		shop_new = (TextView) findViewById(R.id.shop_new);
		shop_hot = (TextView) findViewById(R.id.shop_hot);
		tv_main_no_data2 = (TextView) findViewById(R.id.tv_main_no_data2);
		ll_shop_share = (LinearLayout) findViewById(R.id.ll_shop_share);
		collection = (ImageView) findViewById(R.id.collection);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		collection.setOnClickListener(this);
		iv_back.setOnClickListener(this);
		initPullRefreshListView();
	}

	/**
	 * 初始化主界面的listview
	 * 
	 * @param view
	 */
	private void initPullRefreshListView() {
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.shop_listview);
		actualListView = mPullRefreshListView.getRefreshableView();
		registerForContextMenu(actualListView);
		mPullRefreshListView.setMode(Mode.BOTH);
		mAdater = new ShopAdater(this, this);
		mAdater.setData(list);
		actualListView.setAdapter(mAdater);
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				showDialog("正在加载中。。。");
				pagesize = 10;
				getShopProduct(pagesize + "", search);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				try {
					showDialog("正在加载中。。。");
					pagesize += Onlysize;
					getShopProduct(pagesize + "", search);
					android.util.Log.i("onPullDownToRefresh", refreshView.toString());
				} catch (Exception e) {
					mPullRefreshListView.onRefreshComplete();
				}
			}
		});
		actualListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(ShopActivity.this, BookDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("ProductId", list.get(position - 1).getProductId());
				bundle.putString("Productimage", list.get(position - 1).getProductImgUrl());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	/**
	 * 店铺商品列表
	 */
	public void getShopProduct(String pagesize, String search) {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("shopId", shopId);
		params.addBodyParameter("page", "1");
		params.addBodyParameter("pageSize", pagesize);
		params.addBodyParameter("keyword ", search);
		params.addBodyParameter("shopCategoryId ", "");
		params.addBodyParameter("order ", "");
		params.addBodyParameter("imgSpec ", "");

		baseTask.askCookieRequest(SystemConfig.SHOPPRODUCTLIST, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("getShopProduct", arg0.result.toString());
				try {
					JSONObject object = new JSONObject(arg0.result.toString());
					msBean = JSON.parseObject(object.getString("result"), ShopBean.class);
					list = msBean.getResult();
					if (list.size() != 0) {
						mPullRefreshListView.setVisibility(View.VISIBLE);
						mAdater.setData(list);
						mAdater.notifyDataSetChanged();
						mPullRefreshListView.onRefreshComplete();
					} else {
						mPullRefreshListView.setVisibility(View.GONE);
						tv_main_no_data2.setVisibility(View.VISIBLE);
						tv_main_no_data2.setText("暂无数据");
					}
					dismissDialog();
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				mPullRefreshListView.setVisibility(View.GONE);
				tv_main_no_data2.setVisibility(View.VISIBLE);
				tv_main_no_data2.setText("获取异常");
				Log.e("店铺商品列表异常", arg0.toString());
				dismissDialog();
			}
		});
	}

	/**
	 * 收藏店铺或者取消收藏
	 */
	public void sendhttpYorN() {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("shopInfId", shopId);
		if (!shopinfoBean.isShopCollect()) {
			params.addBodyParameter("action", "add");
			showDialog("正在收藏...");
		} else {
			params.addBodyParameter("action", "del");
			showDialog("正在取消收藏...");
		}
		baseTask.askCookieRequest(SystemConfig.ADDDELSHOP, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("collectionShop", arg0.result.toString());
				try {
					JSONObject object = new JSONObject(arg0.result.toString());
					if (object.getBoolean("success")) {
						sendhttpinfoReset();
					}
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("收藏或取消异常", arg0.toString());
			}
		});
	}

	/**
	 * 店铺信息
	 */
	public void sendhttpinfo() {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("shopInfId", shopId);
		params.addBodyParameter("imgSpec", "100X100");
		baseTask.askCookieRequest(SystemConfig.SHOPINF, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				shopinfoBean = new ShopInfoBean();
				try {
					JSONObject object = new JSONObject(arg0.result);
					shopinfoBean = JSON.parseObject(object.getString("result"), ShopInfoBean.class);
					if (shopinfoBean != null) {
						setNavigationTitle(shopinfoBean.getShopNm());
						BitmapUtil bitmapUtil = new BitmapUtil();
						bitmapUtil.displayImage(ShopActivity.this, shop_icon, shopinfoBean.getLogoUrl());
						shop_name.setText(shopinfoBean.getShopNm());
						if ("".equals(Utils.checkStr(shopinfoBean.getProductCount()))) {
							shop_all_count.setText("0");
						} else {
							shop_all_count.setText(shopinfoBean.getProductCount());
						}
						if ("".equals(Utils.checkStr(shopinfoBean.getNewProductCount()))) {
							shop_new.setText("0");
						} else {
							shop_new.setText(shopinfoBean.getNewProductCount());
						}
						if ("".equals(Utils.checkStr(shopinfoBean.getPromotionProductCount()))) {
							shop_hot.setText("0");
						} else {
							shop_hot.setText(shopinfoBean.getPromotionProductCount());
						}
						if ("".equals(Utils.checkStr(shopinfoBean.getAttentionShopCount()))) {
							shop_people_count.setText("0人收藏");
						} else {
							shop_people_count.setText(shopinfoBean.getAttentionShopCount() + "人收藏");
						}
						if (shopinfoBean.isShopCollect()) {
							collection.setImageResource(R.drawable.favoritebutton_selected);
						} else {
							collection.setImageResource(R.drawable.favoritebutton_unselected);
						}
						getShopProduct(pagesize + "", search);
					}
					dismissDialog();
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				showToast("获取店铺商品信息失败");
				Log.e("店铺商品列表异常", arg0.toString());
			}
		});
	}

	public void sendhttpinfoReset() {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("shopInfId", shopId);
		params.addBodyParameter("imgSpec", "100X100");
		baseTask.askCookieRequest(SystemConfig.SHOPINF, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				shopinfoBean = new ShopInfoBean();
				try {
					JSONObject object = new JSONObject(arg0.result);
					shopinfoBean = JSON.parseObject(object.getString("result"), ShopInfoBean.class);
					if (shopinfoBean != null) {
						setNavigationTitle(shopinfoBean.getShopNm());
						BitmapUtil bitmapUtil = new BitmapUtil();
						bitmapUtil.displayImage(ShopActivity.this, shop_icon, shopinfoBean.getLogoUrl());
						shop_name.setText(shopinfoBean.getShopNm());
						if ("".equals(Utils.checkStr(shopinfoBean.getProductCount()))) {
							shop_all_count.setText("0");
						} else {
							shop_all_count.setText(shopinfoBean.getProductCount());
						}
						if ("".equals(Utils.checkStr(shopinfoBean.getNewProductCount()))) {
							shop_new.setText("0");
						} else {
							shop_new.setText(shopinfoBean.getNewProductCount());
						}
						if ("".equals(Utils.checkStr(shopinfoBean.getPromotionProductCount()))) {
							shop_hot.setText("0");
						} else {
							shop_hot.setText(shopinfoBean.getPromotionProductCount());
						}
						if ("".equals(Utils.checkStr(shopinfoBean.getAttentionShopCount()))) {
							shop_people_count.setText("0人收藏");
						} else {
							shop_people_count.setText(shopinfoBean.getAttentionShopCount() + "人收藏");
						}
						if (shopinfoBean.isShopCollect()) {
							collection.setImageResource(R.drawable.favoritebutton_selected);
						} else {
							collection.setImageResource(R.drawable.favoritebutton_unselected);
						}
					}
					dismissDialog();
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				showToast("获取店铺商品信息失败");
				Log.e("店铺商品列表异常", arg0.toString());
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.collection:
			if (MyApplication.mapp.getCookieStore() != null) {
				if (shopId == null) {
					return;
				}
				if (shopinfoBean == null) {
					return;
				}
				sendhttpYorN();
			} else {
				Intent intent = new Intent(ShopActivity.this, LoginActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("bookdetail", "histroy");
				intent.putExtras(bundle);
				startActivity(intent);
			}
			break;
		case R.id.iv_back:
			ShopActivity.this.finish();
			break;
		}
	}

	@Override
	public void onItemClick(View item, View widget, int position, int which) {
		switch (which) {
		case R.id.iv_histroy:
			break;
		}
	}
}
