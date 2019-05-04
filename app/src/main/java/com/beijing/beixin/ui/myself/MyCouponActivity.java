package com.beijing.beixin.ui.myself;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.MyCouponAdater;
import com.beijing.beixin.adapter.MyCouponStateAdater;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.CouponCountStateBean;
import com.beijing.beixin.pojo.MyCouponBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase.Mode;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase.OnRefreshListener2;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 优惠券
 * 
 * @author ouyanghao
 * 
 */
public class MyCouponActivity extends BaseActivity implements OnClickListener {

	/**
	 * 未使用
	 */
	private TextView unused;
	/**
	 * 已过期
	 */
	private TextView expired;
	/**
	 * 已使用
	 */
	private TextView has_been_used;
	/**
	 * 优惠券展示列表
	 */
	private TextView tv_main_no_data2;
	private List<MyCouponBean.coupon> list = new ArrayList<MyCouponBean.coupon>();
	private MyCouponBean myCouponBean = new MyCouponBean();
	private MyCouponStateAdater mAdater = null;
	/**
	 * 刷新
	 */
	private PullToRefreshListView mPullRefreshListView;
	private ListView actualListView;
	int page = 1;
	int pagesize = 10000;
	int Onlysize = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_coupon);
		init();
		getcouponcount();
		sendhttp("N", pagesize + "");
	}

	/**
	 * 初始化界面
	 */
	public void init() {
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		setNavigationTitle("优惠券");
		unused = (TextView) findViewById(R.id.unused);
		expired = (TextView) findViewById(R.id.expired);
		has_been_used = (TextView) findViewById(R.id.has_been_used);
		tv_main_no_data2 = (TextView) findViewById(R.id.tv_main_no_data2);
		unused.setTextColor(Color.parseColor("#E5350D"));
		unused.setOnClickListener(this);
		expired.setOnClickListener(this);
		has_been_used.setOnClickListener(this);
		initPullRefreshListView();
	}

	/**
	 * 初始化主界面的listview
	 * 
	 * @param view
	 */
	private void initPullRefreshListView() {
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.coupon_listview);
		actualListView = mPullRefreshListView.getRefreshableView();
		registerForContextMenu(actualListView);
		mPullRefreshListView.setMode(Mode.BOTH);
		mAdater = new MyCouponStateAdater(this);
		mAdater.setData(list);
		actualListView.setAdapter(mAdater);
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// pagesize = 10;
				sendhttp("N", pagesize + "");
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				try {
					// pagesize += Onlysize;
					sendhttp("N", pagesize + "");
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
	 * 获取优惠券count
	 */
	public void getcouponcount() {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId", MyApplication.mapp.getUserInfoBean().getSysUserId() + "");
		baseTask.askCookieRequest(SystemConfig.COUPONCOUNT, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("优惠券状态数量", arg0.result.toString());
				try {
					JSONObject jsonObject = new JSONObject(arg0.result.toString());
					CouponCountStateBean countStateBean = JSON.parseObject(jsonObject.getString("result").toString(),
							CouponCountStateBean.class);
					if (countStateBean != null) {
						unused.setText("未使用(" + (countStateBean.getN()) + ")");
						expired.setText("已过期(" + (countStateBean.getP()) + ")");
						has_been_used.setText("已使用(" + (countStateBean.getY()) + ")");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("优惠券状态数量异常", arg0.toString());
			}
		});
	}

	/**
	 * 获取优惠券列表
	 */
	public void sendhttp(String is, String pagesize) {
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("page", "1");
		params.addBodyParameter("limit", pagesize);
		params.addBodyParameter("is", is);
		params.addBodyParameter("userId", MyApplication.mapp.getUserInfoBean().getSysUserId() + "");
		showDialog("正在加载");
		baseTask.askCookieRequest(SystemConfig.GETPASTCOUPONPAGE, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("我的优惠券", arg0.result.toString());
				try {
					JSONObject jsonObject = new JSONObject(arg0.result.toString());
					myCouponBean = JSON.parseObject(jsonObject.getString("result"), MyCouponBean.class);
					if (myCouponBean != null) {
						list = myCouponBean.getResult();
						mPullRefreshListView.setVisibility(View.VISIBLE);
						mAdater.setData(list);
						mAdater.notifyDataSetChanged();
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
				// TODO Auto-generated method stub
				Log.e("我的优惠券异常", arg0.toString());
				dismissDialog();
			}
		});
	}

	public void SetTextColor() {
		unused.setTextColor(Color.parseColor("#A0A0A0"));
		expired.setTextColor(Color.parseColor("#A0A0A0"));
		has_been_used.setTextColor(Color.parseColor("#A0A0A0"));
	}

	@Override
	public void onClick(View v) {
		SetTextColor();
		switch (v.getId()) {
		case R.id.unused:
			unused.setTextColor(Color.parseColor("#E5350D"));
			sendhttp("N", pagesize + "");
			break;
		case R.id.expired:
			expired.setTextColor(Color.parseColor("#E5350D"));
			sendhttp("", pagesize + "");
			break;
		case R.id.has_been_used:
			has_been_used.setTextColor(Color.parseColor("#E5350D"));
			sendhttp("Y", pagesize + "");
			break;
		}
	}
}
