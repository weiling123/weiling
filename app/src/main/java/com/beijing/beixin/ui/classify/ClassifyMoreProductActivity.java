package com.beijing.beixin.ui.classify;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.ListItemClickHelp;
import com.beijing.beixin.adapter.MoreShopAdater;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.MoreShopBean;
import com.beijing.beixin.pojo.ProductMoreBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.myself.login.LoginActivity;
import com.beijing.beixin.utils.ToastUtil;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase.Mode;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase.OnRefreshListener2;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshListView;
import com.beijing.beixin.vo.ProductBean;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 更多卖家页面
 * 
 * @author ouyanghao
 * 
 */
public class ClassifyMoreProductActivity extends BaseActivity implements ListItemClickHelp {

	/**
	 * 接收传递过来的书名变量
	 */
	private String bookname;
	private String pid;
	private String more;
	/**
	 * 适配器
	 */
	private CommonAdapter<ProductBean> mAdapter;
	/**
	 * 刷新
	 */
	private PullToRefreshListView mPullRefreshListView;
	private ListView actualListView;
	/**
	 * Handler对象
	 */
	private Handler mHandler = new Handler() {
	};
	List<ProductMoreBean> list = new ArrayList<ProductMoreBean>();
	private MoreShopAdater mShopAdater;
	int page = 1;
	int pagesize = 10;
	int Onlysize = 5;
	private String resultString;
	private TextView tv_main_no_data2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classify_more_product);
		init();
		showDialog("正在加载中...");
	}

	/**
	 * 绑定控件ID
	 */
	public void init() {
		tv_main_no_data2 = (TextView) findViewById(R.id.tv_main_no_data2);
		Intent intent = getIntent();
		bookname = intent.getStringExtra("bookname");
		pid = intent.getStringExtra("pid");
		resultString = intent.getStringExtra("resultString");
		more = intent.getStringExtra("More");
		setNavigationTitle("更多卖家");
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		initPullRefreshListView();
		if ("More".equals(more)) {
			sendhttp();
		} else {
			getmoreproduct();
		}
	}

	/**
	 * 初始化主界面的listview
	 * 
	 * @param view
	 */
	private void initPullRefreshListView() {
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.moreshop_listview);
		actualListView = mPullRefreshListView.getRefreshableView();
		registerForContextMenu(actualListView);
		mPullRefreshListView.setMode(Mode.DISABLED);
		mShopAdater = new MoreShopAdater(this, this);
		mShopAdater.setData(new ArrayList<ProductMoreBean>());
		// mShopAdater.setMoreData(mShopBeans);
		actualListView.setAdapter(mShopAdater);
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				pagesize = 10;
				sendhttp();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				try {
					pagesize += Onlysize;
					sendhttp();
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

	public void getmoreproduct() {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("productNum", resultString);
		baseTask.askNormalRequest(SystemConfig.FINDPRODUCT, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					android.util.Log.e("更多卖家", arg0.result);
					JSONObject jsonObject = new JSONObject(arg0.result.toString());
					list = JSON.parseArray(jsonObject.getString("result"), ProductMoreBean.class);
					if (list.size() != 0) {
						mPullRefreshListView.setVisibility(View.VISIBLE);
						mShopAdater.setData(list);
						mShopAdater.notifyDataSetChanged();
						mPullRefreshListView.onRefreshComplete();
					} else {
						mPullRefreshListView.setVisibility(View.GONE);
						tv_main_no_data2.setVisibility(View.VISIBLE);
					}
					dismissDialog();
					dismissDialog();
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(ClassifyMoreProductActivity.this, "获取更多卖家失败");
			}
		});
	}
	public String code;
	public String message;
	public List<String> result;
	/**
	 * 更多卖家
	 */
	public void sendhttp() {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("productId", pid);
		baseTask.askNormalRequest(SystemConfig.MORE_SHOP, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					android.util.Log.e("更多卖家", arg0.result);
					JSONObject jsonObject = new JSONObject(arg0.result.toString());
					list = JSON.parseArray(jsonObject.getString("result"), ProductMoreBean.class);
					if (list.size() != 0) {
						mPullRefreshListView.setVisibility(View.VISIBLE);
						mShopAdater.setData(list);
						mShopAdater.notifyDataSetChanged();
						mPullRefreshListView.onRefreshComplete();
					} else {
						mPullRefreshListView.setVisibility(View.GONE);
						tv_main_no_data2.setVisibility(View.VISIBLE);
					}
					dismissDialog();
					dismissDialog();
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(ClassifyMoreProductActivity.this, "获取更多卖家失败");
			}
		});
	}

	@Override
	public void onItemClick(View item, View widget, int position, int which) {
		switch (which) {
		case R.id.iv_histroy:
			if (MyApplication.mapp.getCookieStore() == null) {
				startActivity(LoginActivity.class);
				return;
			}
			if ("N".equals(list.get(position).getIsAttention())) {
				collectionProduct(list.get(position).getProductId(), position);
			} else {
				cancelCollectionProduct(list.get(position).getProductId(), position);
			}
			break;
		case R.id.iv_shopping:
			addTocart(list.get(position).getShop().getShopInfId(), list.get(position).getSku().getSkuId() + "");
			break;
		}
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
						list.get(position).setIsAttention("Y");
						mShopAdater.notifyDataSetChanged();
						showToast("收藏商品成功");
						dismissDialog();
					}
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
						list.get(position).setIsAttention("N");
						mShopAdater.notifyDataSetChanged();
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
				ToastUtil.show(ClassifyMoreProductActivity.this, "加入购物车失败" + arg0.toString());
			}
		});
	}
}
