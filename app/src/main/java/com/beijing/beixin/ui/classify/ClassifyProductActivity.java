package com.beijing.beixin.ui.classify;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.ClassifyProductAdater;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.ListItemClickHelp;
import com.beijing.beixin.adapter.ViewHolder;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.AllBrandBean;
import com.beijing.beixin.pojo.CategoryInfoBean;
import com.beijing.beixin.pojo.ProductDetail;
import com.beijing.beixin.pojo.ProductlistBean;
import com.beijing.beixin.pojo.Typebean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.homepage.SearchActivity;
import com.beijing.beixin.ui.myself.login.LoginActivity;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.ToastUtil;
import com.beijing.beixin.utils.Utils;
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
public class ClassifyProductActivity extends BaseActivity implements OnClickListener, ListItemClickHelp {

	/**
	 * 刷新
	 */
	private PullToRefreshListView mPullRefreshListView;
	private ListView actualListView;
	/**
	 * 没有更多
	 */
	private TextView tv_main_no_data;
	/**
	 * 暂无数据
	 */
	private TextView tv_main_no_data2;
	/**
	 * 适配器
	 */
	private ClassifyProductAdater mProductAdater;
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
	/**
	 * 返回
	 */
	private ImageView iv_back, iv_back_hot;
	/**
	 * 侧滑
	 */
	private DrawerLayout drawerLayout;
	/**
	 * 筛选菜单
	 */
	private LinearLayout rightLayout;
	/**
	 * 取消
	 */
	private TextView tv_cancel;
	/**
	 * 确定
	 */
	private TextView tv_ok;
	/**
	 * 筛选价格
	 */
	private LinearLayout ll_price;
	/**
	 * 筛选出版社
	 */
	private LinearLayout ll_press;
	/**
	 * 筛选分类
	 */
	private LinearLayout ll_type;
	/**
	 * 请除选项
	 */
	private LinearLayout ll_remove;
	/**
	 * 搜索框
	 */
	private EditText et_component_search;
	/**
	 * 分类筛选抬头
	 */
	private LinearLayout ll_type_screen;
	/**
	 * 排序筛选
	 */
	private LinearLayout layout_select;
	/**
	 * 分类精品热销抬头
	 */
	private LinearLayout ll_type_hot;
	/**
	 * 八个馆的抬头
	 */
	private View view_icon;
	private TextView types;
	private TextView press;
	private String categryid;
	private String brandid;
	/**
	 * 区分跳转标示
	 */
	private String type;
	/**
	 * 接收传递过来的分类ID
	 */
	private String categoryId;
	private String categoryIds;;
	/**
	 * 商品列表集合
	 */
	private List<ProductlistBean> productList = new ArrayList<ProductlistBean>();
	/**
	 * 图片处理工具
	 */
	private BitmapUtils bitmapUtils;
	private TextView startPrice;
	private TextView endPrice;
	/**
	 * 搜索框值
	 */
	private String search = "";
	private String q = "";
	private String ordertype = "";
	private String startp = "";
	private String endp = "";
	int page = 1;
	int pagesize = 10;
	int Onlysize = 5;
	private GridView poppupwindow_gridView1;
	private CommonAdapter<AllBrandBean> mAdapter;
	private CommonAdapter<Typebean> mAdapter2;
	private List<AllBrandBean> mList = new ArrayList<AllBrandBean>();
	private List<Typebean> typeList = new ArrayList<Typebean>();
	private PopupWindow popupWindow1 = null;
	private PopupWindow popupWindow = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_list);
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		init();
		// ordertype = "sysDefaultOrder,desc";
		sendhttp(categoryId, q, ordertype, pagesize, search, startp, endp);
	}

	/**
	 * 绑定控件及监听
	 */
	public void init() {
		view_icon = (View) findViewById(R.id.view_icon);
		layout_select = (LinearLayout) findViewById(R.id.layout_select);
		ll_type_screen = (LinearLayout) findViewById(R.id.ll_type_screen);
		ll_type_hot = (LinearLayout) findViewById(R.id.ll_type_hot);
		types = (TextView) findViewById(R.id.type);
		press = (TextView) findViewById(R.id.press);
		startPrice = (TextView) findViewById(R.id.startPrice);
		endPrice = (TextView) findViewById(R.id.endPrice);
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		Intent intent = getIntent();
		type = intent.getStringExtra("type");
		if (search != null) {
			search = intent.getStringExtra("search");
		}
		if (!"".equals(search) && search != null) {
			addSearchProduct();
		}
		categoryId = intent.getStringExtra("categoryId");
		categoryIds = intent.getStringExtra("categoryIds");
		if ("type".equals(type)) {
			layout_select.setVisibility(View.VISIBLE);
			ll_type_hot.setVisibility(View.GONE);
			ll_type_screen.setVisibility(View.VISIBLE);
		}
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
		rightLayout = (LinearLayout) findViewById(R.id.right);
		tv_main_no_data2 = (TextView) findViewById(R.id.tv_main_no_data2);
		tv_comprehensive = (TextView) findViewById(R.id.tv_comprehensive);
		tv_sales_volume = (TextView) findViewById(R.id.tv_sales_volume);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_screen = (TextView) findViewById(R.id.tv_screen);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back_hot = (ImageView) findViewById(R.id.iv_back_hot);
		et_component_search = (EditText) findViewById(R.id.et_component_search);
		ll_comprehensive = (LinearLayout) findViewById(R.id.ll_comprehensive);
		iv_default = (ImageView) findViewById(R.id.iv_default);
		ll_sales_volume = (LinearLayout) findViewById(R.id.ll_sales_volume);
		iv_sales = (ImageView) findViewById(R.id.iv_sales);
		layout_prices = (LinearLayout) findViewById(R.id.layout_prices);
		iv_up_price = (ImageView) findViewById(R.id.iv_up_price);
		iv_down_price = (ImageView) findViewById(R.id.iv_down_price);
		et_component_search.setEnabled(true);
		et_component_search.setText(search);
		et_component_search.setOnClickListener(this);
		iv_back.setOnClickListener(this);
		iv_back_hot.setOnClickListener(this);
		tv_comprehensive.setOnClickListener(this);
		tv_sales_volume.setOnClickListener(this);
		tv_price.setOnClickListener(this);
		tv_screen.setOnClickListener(this);
		tv_comprehensive.setTextColor(Color.parseColor("#E8360F"));
		iv_default.setImageResource(R.drawable.list_order_down_red);
		drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		bitmapUtils = new BitmapUtils(this);
		initPullRefreshListView();
		initrightLayout();
	}

	/**
	 * 初始化主界面的listview
	 * 
	 * @param view
	 */
	private void initPullRefreshListView() {
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.product_listview);
		actualListView = mPullRefreshListView.getRefreshableView();
		registerForContextMenu(actualListView);
		mPullRefreshListView.setMode(Mode.BOTH);
		mProductAdater = new ClassifyProductAdater(ClassifyProductActivity.this, ClassifyProductActivity.this);
		mProductAdater.setData(productList);
		actualListView.setAdapter(mProductAdater);
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				pagesize = 10;
				sendhttp(categoryId, q, ordertype, pagesize, search, startp, endp);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				try {
					pagesize += Onlysize;
					sendhttp(categoryId, q, ordertype, pagesize, search, startp, endp);
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
	 * 筛选菜单视图初始化
	 */
	public void initrightLayout() {
		tv_cancel = (TextView) findViewById(R.id.tv_cancel);
		tv_ok = (TextView) findViewById(R.id.tv_ok);
		ll_price = (LinearLayout) findViewById(R.id.ll_price);
		ll_press = (LinearLayout) findViewById(R.id.ll_press);
		ll_type = (LinearLayout) findViewById(R.id.ll_type);
		ll_remove = (LinearLayout) findViewById(R.id.ll_remove);
		tv_cancel.setOnClickListener(this);
		tv_ok.setOnClickListener(this);
		ll_price.setOnClickListener(this);
		ll_press.setOnClickListener(this);
		ll_type.setOnClickListener(this);
		ll_remove.setOnClickListener(this);
	}

	/**
	 * 请求商品列表数据
	 */
	public void sendhttp(String categoryId, String q, String order, int pagesize, String search, String startPrice,
			String endPrice) {
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
	 * 添加搜索记录
	 */
	public void addSearchProduct() {
		ProductSearch mSearch = new ProductSearch();
		mSearch.setProductname(search);
		MyApplication.mServer.addProduct(mSearch);
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

	/**
	 * 点击事件
	 */
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
			sendhttp(categoryId, q, ordertype, pagesize, search, startp, endp);
			break;
		case R.id.tv_sales_volume:
			tv_sales_volume.setTextColor(Color.parseColor("#E8360F"));
			iv_sales.setImageResource(R.drawable.list_order_down_red);
			// ordertype = "sysSalesVolume,desc";
			ordertype = "salesVolume,desc";
			sendhttp(categoryId, q, ordertype, pagesize, search, startp, endp);
			break;
		case R.id.tv_price:
			i++;
			tv_price.setTextColor(Color.parseColor("#E8360F"));
			if (i % 2 == 0) {
				ordertype = "minPrice,asc";
				iv_up_price.setImageResource(R.drawable.list_order_up_red);
				sendhttp(categoryId, q, ordertype, pagesize, search, startp, endp);
			} else {
				ordertype = "minPrice,desc";
				iv_down_price.setImageResource(R.drawable.list_order_down_red);
				sendhttp(categoryId, q, ordertype, pagesize, search, startp, endp);
			}
			break;
		case R.id.iv_back:
			finish();
			break;
		case R.id.iv_back_hot:
			finish();
			break;
		case R.id.tv_screen:
			// 展开筛选页
			// drawerLayout.openDrawer(rightLayout);
			tv_screen.setTextColor(Color.parseColor("#E8360F"));
			showToast("此功能暂不支持");
			// getBrand();
			// getType();
			break;
		case R.id.tv_cancel:
			// 关闭筛选页
			drawerLayout.closeDrawer(rightLayout);
			tv_screen.setTextColor(Color.parseColor("#F37D00"));
			break;
		case R.id.tv_ok:
			startp = startPrice.getText().toString().trim();
			endp = endPrice.getText().toString().trim();
			q = "brandId:" + brandid;
			sendhttp(categryid, q, ordertype, pagesize, search, startp, endp);
			drawerLayout.closeDrawer(rightLayout);
			tv_screen.setTextColor(Color.parseColor("#F37D00"));
			break;
		case R.id.ll_price:
			break;
		case R.id.ll_press:
			showwindow(ll_press);
			break;
		case R.id.ll_type:
			showwindows(ll_type);
			break;
		case R.id.ll_remove:
			startPrice.setText("");
			endPrice.setText("");
			press.setText("");
			types.setText("");
			break;
		case R.id.et_component_search:
			startActivity(SearchActivity.class);
			finish();
			break;
		}
	}

	/**
	 * 出版社
	 * 
	 * @param p
	 */
	public void showwindow(View p) {
		if (popupWindow == null) {
			View v = LayoutInflater.from(this).inflate(R.layout.popupwindow_list, null);
			poppupwindow_gridView1 = (GridView) v.findViewById(R.id.poppupwindow_gridView1);
			if (mList != null) {
				setpopupwindowbrand(mList);
			}
			popupWindow = new PopupWindow(v, rightLayout.getWidth(), LayoutParams.WRAP_CONTENT);
		}
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAsDropDown(p, 0, 30);
	}

	/**
	 * 分类
	 * 
	 * @param p
	 */
	public void showwindows(View p) {
		if (popupWindow1 == null) {
			View v = LayoutInflater.from(this).inflate(R.layout.popupwindow_list, null);
			poppupwindow_gridView1 = (GridView) v.findViewById(R.id.poppupwindow_gridView1);
			if (typeList != null) {
				setpopupwindowtype(typeList);
			}
			popupWindow1 = new PopupWindow(v, rightLayout.getWidth(), LayoutParams.WRAP_CONTENT);
		}
		popupWindow1.setFocusable(true);
		popupWindow1.setOutsideTouchable(true);
		popupWindow1.setBackgroundDrawable(new BitmapDrawable());
		popupWindow1.showAsDropDown(p, 0, 30);
	}

	/**
	 * 出版社
	 * 
	 * @param mList
	 */
	public void setpopupwindowbrand(List<AllBrandBean> mList) {
		poppupwindow_gridView1
				.setAdapter(mAdapter = new CommonAdapter<AllBrandBean>(this, mList, R.layout.item_popupwindow) {

					@Override
					public void convert(ViewHolder helper, final AllBrandBean item) {
						helper.setText(R.id.text_brand, item.getBrandNm());
						helper.getConvertView().setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								press.setText(item.getBrandNm());
								brandid = item.getBrandId();
								if (popupWindow != null) {
									popupWindow.dismiss();
								}
							}
						});
					}
				});
	}

	/**
	 * 分类
	 * 
	 * @param mList
	 */
	public void setpopupwindowtype(List<Typebean> mList) {
		poppupwindow_gridView1
				.setAdapter(mAdapter2 = new CommonAdapter<Typebean>(this, mList, R.layout.item_popupwindow) {

					@Override
					public void convert(ViewHolder helper, final Typebean item) {
						helper.setText(R.id.text_brand, item.getCategoryNm());
						helper.getConvertView().setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								types.setText(item.getCategoryNm());
								categryid = item.getCategoryId();
								if (popupWindow1 != null) {
									popupWindow1.dismiss();
								}
							}
						});
					}
				});
	}

	/**
	 * 获取出版社列表
	 */
	public void getBrand() {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("categoryId", "1");
		baseTask.askCookieRequest(SystemConfig.ALLBRAND, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				android.util.Log.e("出版社列表", arg0.result.toString());
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(arg0.result.toString());
					JSONArray array = jsonObject.getJSONArray("result");
					mList = JSON.parseArray(array.toString(), AllBrandBean.class);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				android.util.Log.e("出版社列表异常", arg0.toString());
			}
		});
	}

	/**
	 * 获取分类
	 */
	public void getType() {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("categoryId", categoryIds);
		baseTask.askCookieRequest(SystemConfig.PRODUCT_TYPE, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				android.util.Log.e("分类列表", arg0.result.toString());
				JSONObject object;
				try {
					object = new JSONObject(arg0.result.toString());
					typeList = JSON.parseArray(object.getString("result").toString(), Typebean.class);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				android.util.Log.e("分类列表异常", arg0.toString());
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
			if (MyApplication.mapp.getCookieStore() != null) {
				if ("N".equals(productList.get(position).getIsAttention())) {
					collectionProduct(productList.get(position).getProductId() + "", position);
				} else {
					cancelCollectionProduct(productList.get(position).getProductId() + "", position);
				}
			} else {
				startActivity(LoginActivity.class);
			}
			break;
		case R.id.iv_shopping:
			String shopInfId = productList.get(position).getShop().getShopInfId() + "";
			addTocart(shopInfId, productList.get(position).getSku().getSkuId() + "");
			break;
		case R.id.tv_morebyhome:
			Intent intent = new Intent(ClassifyProductActivity.this, ClassifyMoreProductActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("bookname", productList.get(position).getProductNm());
			bundle.putString("pid", productList.get(position).getProductId() + "");
			bundle.putString("More", "More");
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		}
	}

	/**
	 * 加入购物车
	 */
	public void addTocart(String shopInfId, String skuid) {
		BaseTask task = new BaseTask();
		task.addShoppingCard(shopInfId, skuid, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				android.util.Log.e("加入购物车", arg0.result.toString());
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
				ToastUtil.show(ClassifyProductActivity.this, "加入购物车失败");
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
				// Log.e("collectionProduct", arg0.result.toString());
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
				dismissDialog();
			}
		});
	}
}
