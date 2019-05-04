package com.beijing.beixin.ui.myself;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.beijing.beixin.R;
import com.beijing.beixin.R.layout;
import com.beijing.beixin.adapter.ClassifyProductAdater;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.ViewHolder;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.classify.ClassifyProductActivity;
import com.beijing.beixin.ui.myself.address.AddressActivity;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.utils.Utils;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshListView;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase.Mode;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase.OnRefreshListener2;
import com.beijing.beixin.utils.sqlite.FootInfo;
import com.beijing.beixin.vo.ProductBean;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 浏览足迹
 * 
 * @author ouyanghao
 *   
 */
@SuppressWarnings("unused")
public class BrowseTheFootprintActivity extends BaseActivity {

	/**
	 * 商品图片
	 */
	private ImageView iv_bookimage;
	/**
	 * 商品名称
	 */
	private TextView tv_bookname;
	/**
	 * 商品价格
	 */
	private TextView tv_bookprice;
	/**
	 * 商品市场价
	 */
	private TextView tv_old_price;
	/**
	 * 商品店铺名称
	 */
	private TextView tv_flagshipstore;
	/**
	 * 刷新
	 */
	private PullToRefreshListView mPullRefreshListView;
	private ListView actualListView;
	private CommonAdapter<FootInfo> mAdapter;
	int page = 1;
	int pagesize = 10;
	int Onlysize = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_the_footprint);
		init();
	}

	public void init() {
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		setNavigationTitle("浏览足迹");
		setNavigationRightBtnText("清空");
		initPullRefreshListView();
	}

	/**
	 * 清空足迹
	 */
	@Override
	public void rightButtonOnClick() {
		super.rightButtonOnClick();
		AlertDialog.Builder builder = new AlertDialog.Builder(BrowseTheFootprintActivity.this);
		builder.setTitle("提示");
		builder.setMessage("是否清空足迹？");
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				MyApplication.mapp.mServer.delFootInfo();
				initPullRefreshListView();
			}
		});
		builder.create().show();
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
		actualListView.setAdapter(mAdapter = new CommonAdapter<FootInfo>(BrowseTheFootprintActivity.this,
				MyApplication.mServer.findAllFootInfo(MyApplication.mapp.getCertUserName()),
				R.layout.item_browse_the_footprint) {

			@Override
			public void convert(ViewHolder helper, final FootInfo item) {
				BitmapUtils bitmapUtils = new BitmapUtils(BrowseTheFootprintActivity.this);
				if (item.getFootinfoimage() != null && !"".equals(item.getFootinfoimage())) {
					bitmapUtils.display(helper.getView(R.id.iv_bookimage), item.getFootinfoimage());
				} else {
					helper.setImageResource(R.id.iv_bookimage, R.drawable.icon_bg);
				}
				helper.setText(R.id.tv_bookname, item.getFootinfoname());
				helper.setText(R.id.tv_bookprice, "¥" + Utils.parseDecimalDouble2(item.getFootinfoprice()));
				helper.getConvertView().setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(BrowseTheFootprintActivity.this, BookDetailActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("ProductId", item.getFootinfoid());
						intent.putExtras(bundle);
						startActivity(intent);
						finish();
					}
				});
			}
		});
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				pagesize = 10;
				mPullRefreshListView.onRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				try {
					pagesize += Onlysize;
					android.util.Log.i("onPullDownToRefresh", refreshView.toString());
					mPullRefreshListView.onRefreshComplete();
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
}
