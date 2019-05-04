package com.beijing.beixin.ui.classify;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.ClassifyProductAdater;
import com.beijing.beixin.adapter.ListItemClickHelp;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.AppClientModuleObjectVo;
import com.beijing.beixin.pojo.AppClientModuleVo;
import com.beijing.beixin.pojo.EightImageBeam;
import com.beijing.beixin.pojo.ProductlistBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.homepage.SearchActivity;
import com.beijing.beixin.ui.homepage.WebViewActivity;
import com.beijing.beixin.ui.myself.login.LoginActivity;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.ui.shoppingcart.ShopActivity;
import com.beijing.beixin.utils.IgnitedDiagnosticsUtils;
import com.beijing.beixin.utils.ToastUtil;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase.Mode;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase.OnRefreshListener2;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshListView;
import com.beijing.beixin.utils.sqlite.ProductSearch;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 搜索页
 * 
 * @author ouyanghao
 * 
 */
@SuppressWarnings("unused")
public class EightAreasActivity extends BaseActivity implements OnClickListener, ListItemClickHelp {

	private ImageView view_icon;
	/**
	 * 刷新
	 */
	private PullToRefreshListView mPullRefreshListView;
	private ListView actualListView;
	/**
	 * 没有更多
	 */
	private TextView tv_main_no_data2;
	private int page = 1;
	private int pagesize = 10;
	private int Onlysize = 5;
	private String categoryId;
	private String name;
	/**
	 * 适配器
	 */
	private ClassifyProductAdater mProductAdater;
	/**
	 * 商品列表集合
	 */
	private List<ProductlistBean> productList = new ArrayList<ProductlistBean>();
	private List<AppClientModuleVo> imageBeams = new ArrayList<AppClientModuleVo>();
	private AppClientModuleVo appClientModuleVo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eight_area);
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		init();
		// ordertype = "sysDefaultOrder,desc";
		sendhttp(categoryId, q, ordertype, pagesize, search);
		getImage(categoryId);
	}

	public void init() {
		Intent intent = getIntent();
		categoryId = intent.getStringExtra("categoryId");
		name = intent.getStringExtra("name");
		setNavigationTitle(name.substring(0, name.indexOf("-")));
		view_icon = (ImageView) findViewById(R.id.view_icon);
		tv_main_no_data2 = (TextView) findViewById(R.id.tv_main_no_data2);
		initPullRefreshListView();
		view_icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goToActivity(appClientModuleVo, 0);
			}
		});
		initView();
		tv_comprehensive.setTextColor(Color.parseColor("#E8360F"));
		iv_default.setImageResource(R.drawable.list_order_down_red);
	}

	/**
	 * 初始化主界面的listview
	 * 
	 * @param view
	 */
	private void initPullRefreshListView() {
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.eight_listview);
		actualListView = mPullRefreshListView.getRefreshableView();
		registerForContextMenu(actualListView);
		mPullRefreshListView.setMode(Mode.BOTH);
		mProductAdater = new ClassifyProductAdater(this, this);
		mProductAdater.setData(productList);
		actualListView.setAdapter(mProductAdater);
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				pagesize = 10;
				sendhttp(categoryId, q, ordertype, pagesize, search);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				try {
					pagesize += Onlysize;
					sendhttp(categoryId, q, ordertype, pagesize, search);
					android.util.Log.i("onPullDownToRefresh", refreshView.toString());
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
	 * title图片获取
	 * 
	 * @param categoryid
	 */
	@SuppressWarnings("static-access")
	public void getImage(String categoryid) {
		BaseTask task = new BaseTask(this);
		String version = new IgnitedDiagnosticsUtils().getApplicationVersionString(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("prdCategoryNM", name);
		params.addBodyParameter("version", version.replace("v", ""));
		params.addBodyParameter("platformDeviceTypeCode", "aos-hand");
		task.askCookieRequest(SystemConfig.FINDMODELBYCHANNELID, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				android.util.Log.e("图片获取成功", arg0.result.toString());
				try {
					JSONObject jsonObject = new JSONObject(arg0.result.toString());
					if (jsonObject.getString("result") != null
							&& !"null".equals(jsonObject.getString("result").toString())) {
						imageBeams = JSON.parseArray(jsonObject.getString("result").toString(),
								AppClientModuleVo.class);
						appClientModuleVo = imageBeams.get(0);
						if (imageBeams != null && imageBeams.get(0).getModuleObjects() != null
								&& imageBeams.get(0).getModuleObjects().size() != 0) {
							BitmapUtils bitmapUtils = new BitmapUtils(EightAreasActivity.this);
							bitmapUtils.display(view_icon, imageBeams.get(0).getModuleObjects().get(0).getPicUrl());
						} else {
							view_icon.setImageResource(R.drawable.icon_bg);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				android.util.Log.e("图片获取异常", arg0.toString());
			}
		});
	}

	/**
	 * 请求商品列表数据
	 */
	public void sendhttp(String categoryId, String q, String order, int pagesize, String search) {
		showDialog("正在请求数据，请稍后...");
		BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("categoryId", categoryId);
		params.addBodyParameter("keyword", search);
		params.addBodyParameter("order", order);
		params.addBodyParameter("page", "1");
		params.addBodyParameter("pageSize", pagesize + "");
		// params.addBodyParameter("startPrice", startPrice);
		// params.addBodyParameter("endPrice", endPrice);
		// params.addBodyParameter("maxPrice", "");
		// params.addBodyParameter("minPrice", "");
		params.addBodyParameter("q", q);
		task.askNormalRequest(SystemConfig.SEARCH_PRODUCT, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					android.util.Log.e("商品列表", arg0.result);
					JSONObject object = new JSONObject(arg0.result);
					JSONArray array = object.getJSONArray("result");
					productList = JSON.parseArray(array.toString(), ProductlistBean.class);
					if (productList.size() != 0) {
						mPullRefreshListView.setVisibility(View.VISIBLE);
						mProductAdater.setData(productList);
						mProductAdater.notifyDataSetChanged();
						mPullRefreshListView.onRefreshComplete();
						tv_main_no_data2.setVisibility(View.GONE);
					} else {
						tv_main_no_data2.setVisibility(View.VISIBLE);
						mPullRefreshListView.setVisibility(View.GONE);
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
				android.util.Log.e("商品列表异常", arg0.toString());
			}
		});
	}

	/**
	 * 加入购物车
	 */
	public void addTocart(String shopInfId, String pid) {
		BaseTask task = new BaseTask(this);
		task.addShoppingCard(shopInfId, pid, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// Log.e("BookDetailActivity", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					if (obj.getBoolean("success")) {
						showToast("加入购物车成功");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				ToastUtil.show(EightAreasActivity.this, "该商品库存不足，加入购物车失败");
			}
		});
	}

	/**
	 * 收藏商品
	 */
	public void collectionProduct(String pid, final int position) {
		BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("productId", pid);
		task.askCookieRequest(SystemConfig.COLLECTION_PRODUCT, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					if (obj.getBoolean("success")) {
						productList.get(position).setIsAttention("Y");
						mProductAdater.notifyDataSetChanged();
						showToast("收藏商品成功");
						dismissDialog();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				showToast("收藏商品失败");
				dismissDialog();
			}
		});
	}

	/**
	 * 取消收藏商品
	 */
	public void cancelCollectionProduct(String pid, final int position) {
		BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("productId", pid);
		task.askCookieRequest(SystemConfig.CANCEL_COLLECTION_PRODUCT, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				android.util.Log.e("cancelcollectionProduct", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					if (obj.getBoolean("success")) {
						productList.get(position).setIsAttention("N");
						mProductAdater.notifyDataSetChanged();
						showToast("取消收藏商品成功");
						dismissDialog();
					}
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				showToast("取消收藏商品失败");
				dismissDialog();
			}
		});
	}

	/**
	 * 收藏，购物车，更多卖家点击事件
	 */
	@Override
	public void onItemClick(View item, View widget, int position, int which) {
		switch (which) {
		case R.id.iv_histroy:
			if (MyApplication.mapp.getCookieStore() == null) {
				startActivity(LoginActivity.class);
				return;
			}
			if ("N".equals(productList.get(position).getIsAttention())) {
				collectionProduct(productList.get(position).getProductId() + "", position);
			} else {
				cancelCollectionProduct(productList.get(position).getProductId() + "", position);
			}
			break;
		case R.id.iv_shopping:
			String shopInfId = productList.get(position).getShop().getShopInfId() + "";
			addTocart(shopInfId, productList.get(position).getSku().getSkuId() + "");
			break;
		case R.id.tv_morebyhome:
			Intent intent = new Intent(EightAreasActivity.this, ClassifyMoreProductActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("bookname", productList.get(position).getProductNm());
			bundle.putString("pid", productList.get(position).getProductId() + "");
			bundle.putString("More", "More");
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		}
	}

	public void goToActivity(AppClientModuleVo appClientModuleVo, int moduleIndex) {
		if (appClientModuleVo == null) {
			return;
		}
		List<AppClientModuleObjectVo> moduleObjects = appClientModuleVo.getModuleObjects();
		if (moduleObjects == null || moduleObjects.size() <= moduleIndex) {
			return;
		}
		AppClientModuleObjectVo objectVo = moduleObjects.get(moduleIndex);
		String action = objectVo.getActionType();
		if (TextUtils.isEmpty(action)) {
			return;
		}
		int code = 0;
		try {
			code = Integer.parseInt(action.trim());
		} catch (Exception e) {
			return;
		}
		switch (code) {
		/*
		 * //0 - 无效果 case 0: Intent intent = new Intent(this,
		 * AdvActivity.class); intent.putExtra("openUrl",
		 * appClientModuleVo.getModuleObjects().get(moduleIndex).getOpenUrl());
		 * startActivity(intent); break;
		 */
		// 1 - 打开商品
		case 1:
			Intent intent = new Intent(this, BookDetailActivity.class);
			intent.putExtra("ProductId", objectVo.getProduct().getProductId() + "");
			startActivity(intent);
			break;
		// 2 - 打开主题
		case 2:
			break;
		// 3 - 打开链接
		case 3:
			intent = new Intent(this, WebViewActivity.class);
			intent.putExtra("openUrl", objectVo.getOpenUrl());
			startActivity(intent);
			break;
		// 4 - 抢购
		case 4:
			break;
		// 5 - 团购
		case 5:
			break;
		// 6 - 订单列表
		case 6:
			break;
		// 7 - 积分商品
		case 7:
			break;
		// 8 - 店铺
		case 8:
			intent = new Intent(this, ShopActivity.class);
			intent.putExtra("shopId", objectVo.getTargetObjectId() + "");
			startActivity(intent);
			break;
		}
	}

	/**
	 * 综合
	 */
	private TextView tv_comprehensive;
	private LinearLayout ll_comprehensive;
	private ImageView iv_default;
	/**
	 * 销量
	 */
	private TextView tv_sales_volume;
	private LinearLayout ll_sales_volume;
	private ImageView iv_sales;
	/**
	 * 价格
	 */
	private TextView tv_price;
	private LinearLayout layout_prices;
	private ImageView iv_up_price;
	private ImageView iv_down_price;
	/**
	 * 筛选
	 */
	private TextView tv_screen;
	private String q = "";
	private String ordertype = "";
	private String search = "";

	public void initView() {
		tv_comprehensive = (TextView) findViewById(R.id.tv_comprehensive);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_sales_volume = (TextView) findViewById(R.id.tv_sales_volume);
		tv_screen = (TextView) findViewById(R.id.tv_screen);
		iv_default = (ImageView) findViewById(R.id.iv_default);
		iv_sales = (ImageView) findViewById(R.id.iv_sales);
		iv_up_price = (ImageView) findViewById(R.id.iv_up_price);
		iv_down_price = (ImageView) findViewById(R.id.iv_down_price);
		tv_comprehensive.setOnClickListener(this);
		tv_price.setOnClickListener(this);
		tv_sales_volume.setOnClickListener(this);
		tv_screen.setOnClickListener(this);
		iv_default.setOnClickListener(this);
		iv_sales.setOnClickListener(this);
		iv_up_price.setOnClickListener(this);
		iv_down_price.setOnClickListener(this);
	}

	/**
	 * 清空选中颜色
	 */
	public void setColorText() {
		tv_comprehensive.setTextColor(Color.parseColor("#A0A0A0"));
		tv_sales_volume.setTextColor(Color.parseColor("#A0A0A0"));
		tv_price.setTextColor(Color.parseColor("#A0A0A0"));
		tv_screen.setTextColor(Color.parseColor("#A0A0A0"));
		iv_default.setImageResource(R.drawable.list_order_down_gray);
		iv_sales.setImageResource(R.drawable.list_order_down_gray);
		iv_up_price.setImageResource(R.drawable.list_order_up_gray);
		iv_down_price.setImageResource(R.drawable.list_order_down_gray);
	}

	int i = 1;

	@Override
	public void onClick(View v) {
		setColorText();
		switch (v.getId()) {
		case R.id.tv_comprehensive:
			tv_comprehensive.setTextColor(Color.parseColor("#E8360F"));
			iv_default.setImageResource(R.drawable.list_order_down_red);
			ordertype = "";
			q = "";
			sendhttp(categoryId, q, ordertype, pagesize, search);
			break;
		case R.id.tv_sales_volume:
			tv_sales_volume.setTextColor(Color.parseColor("#E8360F"));
			iv_sales.setImageResource(R.drawable.list_order_down_red);
			ordertype = "salesVolume,desc";
			sendhttp(categoryId, q, ordertype, pagesize, search);
			break;
		case R.id.tv_price:
			i++;
			tv_price.setTextColor(Color.parseColor("#E8360F"));
			if (i % 2 == 0) {
				ordertype = "minPrice,asc";
				iv_up_price.setImageResource(R.drawable.list_order_up_red);
				sendhttp(categoryId, q, ordertype, pagesize, search);
			} else {
				ordertype = "minPrice,desc";
				iv_down_price.setImageResource(R.drawable.list_order_down_red);
				sendhttp(categoryId, q, ordertype, pagesize, search);
			}
			break;
		case R.id.iv_back:
			finish();
			break;
		case R.id.iv_back_hot:
			finish();
			break;
		case R.id.tv_screen:
			tv_screen.setTextColor(Color.parseColor("#E8360F"));
			// 展开筛选页
			showToast("该版本此功能暂不支持！");
			break;
		case R.id.tv_cancel:
			// 关闭筛选页
			break;
		}
	}
}
