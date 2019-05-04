package com.beijing.beixin.ui.shoppingcart;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beijing.beixin.R;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.ViewHolder;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.MainActivity;
import com.beijing.beixin.ui.PageTabActivity;
import com.beijing.beixin.ui.myself.login.LoginActivity;
import com.beijing.beixin.ui.shoppingcart.detail.BookDetailFragment;
import com.beijing.beixin.ui.shoppingcart.detail.PackingListFragment;
import com.beijing.beixin.utils.Utils;
import com.beijing.beixin.utils.sqlite.FootInfo;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 产品详情
 */
public class BookDetailMoreActivity extends PageTabActivity implements OnClickListener {

	private TextView[] mTabsTView;
	private Resources mResources;
	private static final int TAB_NUM = 1;
	private static final int TAB_NONUM = 2;
	public String mProductId;
	public String mSkuId;
	public String mShopInfId;
	public String mobile;
	public String cartNum = "";
	public String isCollect;
	public String pluCode;
	public String videoUrl;
	/**
	 * 关注的标识
	 */
	private ImageView imageview_collection_pro;
	/**
	 * 关注的文字
	 */
	private TextView textview_collection_pro;
	/**
	 * 关注的点击区域
	 */
	private LinearLayout layout_collection_pro;
	/**
	 * 店铺
	 */
	private LinearLayout layout_shop;
	/**
	 * 咨询
	 */
	private LinearLayout layout_ask;
	private LinearLayout ll_addcart;
	private LinearLayout linearLayout1_no;
	private LinearLayout linearLayout1;
	private TextView imagebutton_overlay;
	private TextView button_to_pay;
	private String attrGroupNm;
	/**
	 * 侧滑
	 */
	private DrawerLayout drawerLayout;
	/**
	 * 浏览足迹
	 */
	private LinearLayout ll_foot_list;
	private ListView mListView;
	/**
	 * 适配器
	 */
	private CommonAdapter<FootInfo> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_detail_more);
		mResources = getResources();
		mProductId = getIntent().getStringExtra("productId");
		mSkuId = getIntent().getStringExtra("skuId");
		mShopInfId = getIntent().getStringExtra("ShopInfId");
		mobile = getIntent().getStringExtra("mobile");
		cartNum = getIntent().getStringExtra("cartNum");
		isCollect = getIntent().getStringExtra("IsCollect");
		pluCode = getIntent().getStringExtra("pluCode");
		videoUrl = getIntent().getStringExtra("videoUrl");
		attrGroupNm = getIntent().getStringExtra("attrGroupNm");
		linearLayout1_no = (LinearLayout) findViewById(R.id.linearLayout1_no);
		linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
		drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		ll_foot_list = (LinearLayout) findViewById(R.id.ll_foot_list);
		mListView = (ListView) findViewById(R.id.foot_listview);
		View cursor = findViewById(R.id.cursor);
		if (("Y").equals(attrGroupNm)) {
			mTabsTView = new TextView[TAB_NONUM];
			mTabsTView[0] = (TextView) findViewById(R.id.tv_notab_0);
			mTabsTView[1] = (TextView) findViewById(R.id.tv_notab_1);
			linearLayout1.setVisibility(View.GONE);
			linearLayout1_no.setVisibility(View.VISIBLE);
			cursor.setVisibility(View.VISIBLE);
			init(mTabsTView, R.id.cursor);
		} else {
			mTabsTView = new TextView[TAB_NUM];
			mTabsTView[0] = (TextView) findViewById(R.id.tv_tab_0);
			// mTabsTView[1] = (TextView) findViewById(R.id.tv_tab_1);
			// mTabsTView[2] = (TextView) findViewById(R.id.tv_tab_2);
			linearLayout1.setVisibility(View.VISIBLE);
			linearLayout1_no.setVisibility(View.GONE);
			cursor.setVisibility(View.GONE);
			init(mTabsTView, R.id.cursor);
		}
		initView();
		setListener();
		selectfootinfo();
	}

	private void initView() {
		layout_shop = (LinearLayout) findViewById(R.id.layout_shop);
		layout_ask = (LinearLayout) findViewById(R.id.layout_ask);
		ll_addcart = (LinearLayout) findViewById(R.id.ll_addcart);
		layout_collection_pro = (LinearLayout) findViewById(R.id.layout_collection_pro);
		button_to_pay = (TextView) findViewById(R.id.button_to_pay);
		textview_collection_pro = (TextView) findViewById(R.id.textview_collection_pro);
		imageview_collection_pro = (ImageView) findViewById(R.id.imageview_collection_pro);
		imagebutton_overlay = (TextView) findViewById(R.id.imagebutton_overlay);
		if ("Y".equals(isCollect)) {
			imageview_collection_pro.setImageResource(R.drawable.followr);
			textview_collection_pro.setText("已收藏");
		} else {
			imageview_collection_pro.setImageResource(R.drawable.follow);
			textview_collection_pro.setText("收藏");
		}
		if (!"".equals(cartNum) && cartNum != null) {
			imagebutton_overlay.setVisibility(View.VISIBLE);
			imagebutton_overlay.setText(cartNum);
		}
	}

	private void setListener() {
		layout_shop.setOnClickListener(this);
		layout_ask.setOnClickListener(this);
		ll_addcart.setOnClickListener(this);
		layout_collection_pro.setOnClickListener(this);
		button_to_pay.setOnClickListener(this);
	}

	/**
	 * 点击返回导航的操作
	 */
	public void onBack(View view) {
		onBackPressed();
	}

	/**
	 * 点击刷新导航的操作
	 */
	public void onRefresh(View view) {
		if (MyApplication.mapp.getCookieStore() != null) {
			drawerLayout.openDrawer(ll_foot_list);
		} else {
			startActivity(LoginActivity.class);
		}
	}

	/**
	 * 点击分享导航的操作
	 */
	public void onShare(View view) {
		showwindow(view);
	}

	@Override
	public Fragment[] getFragment() {
		if (("Y").equals(attrGroupNm)) {
			Fragment[] fragments = new Fragment[TAB_NONUM];
			fragments[0] = new BookDetailFragment();
			fragments[1] = new PackingListFragment();
			// fragments[2] = new AfterSaleServiceFragment();
			return fragments;
		} else {
			Fragment[] fragments = new Fragment[TAB_NUM];
			fragments[0] = new BookDetailFragment();
			// fragments[1] = new PackingListFragment();
			// fragments[2] = new AfterSaleServiceFragment();
			return fragments;
		}
	}

	/**
	 * 查询浏览足迹
	 */
	public void selectfootinfo() {
		mListView.setAdapter(mAdapter = new CommonAdapter<FootInfo>(BookDetailMoreActivity.this,
				MyApplication.mServer.findAllFootInfo(MyApplication.mapp.getCertUserName()), R.layout.item_foot_list) {

			@Override
			public void convert(ViewHolder helper, final FootInfo item) {
				BitmapUtils bitmapUtils = new BitmapUtils(BookDetailMoreActivity.this);
				if (item.getFootinfoimage() != null && !"".equals(item.getFootinfoimage())) {
					bitmapUtils.display(helper.getView(R.id.foot_bookicon), item.getFootinfoimage());
				} else {
					helper.setImageResource(R.id.foot_bookicon, R.drawable.icon_bg);
				}
				helper.setText(R.id.foot_bookname, item.getFootinfoname());
				helper.setText(R.id.foot_bookprice, "¥" + Utils.parseDecimalDouble2(item.getFootinfoprice()));
				helper.getConvertView().setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(BookDetailMoreActivity.this, BookDetailActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("ProductId", item.getFootinfoid());
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});
			}
		});
	}

	@Override
	public int getTabNum() {
		if (("Y").equals(attrGroupNm)) {
			return TAB_NONUM;
		} else {
			return TAB_NUM;
		}
	}

	public void initPageSelected(int currIndex, int nextIndex) {
		mTabsTView[currIndex].setTextColor(mResources.getColor(R.color.tv_gray_prodetail));
		mTabsTView[nextIndex].setTextColor(mResources.getColor(R.color.tv_tips_prodetail));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_to_pay:
			showDialog("正在添加到购物车...");
			addTocart();
			break;
		case R.id.ll_addcart:
			Intent intent = new Intent(BookDetailMoreActivity.this, MainActivity.class);
			intent.putExtra("cart", "productinfo");
			startActivity(intent);
			finish();
			break;
		case R.id.layout_collection_pro:
			if (MyApplication.mapp.getCookieStore() != null) {
				String collectionText = textview_collection_pro.getText().toString().trim();
				if ("收藏".equals(collectionText)) {
					showDialog("正在收藏该商品...");
					collectionProduct();
				} else if ("已收藏".equals(collectionText)) {
					showDialog("正在取消收藏该商品...");
					cancelCollectionProduct();
				}
			} else {
				showToast("请登录！！");
			}
			break;
		case R.id.layout_shop:
			Intent intentShop = new Intent(BookDetailMoreActivity.this, ShopActivity.class);
			intentShop.putExtra("shopId", mShopInfId);
			startActivity(intentShop);
			break;
		case R.id.layout_ask:
			showAskDialog();
			break;
		default:
			break;
		}
	}

	/**
	 * 加入购物车
	 */
	public void addTocart() {
		BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", "normal");
		params.addBodyParameter("handler", "sku");
		params.addBodyParameter("objectId", mSkuId);
		params.addBodyParameter("quantity", "1");
		task.addShoppingCard(mShopInfId, mSkuId, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("BookDetailActivity", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					if (obj.getBoolean("success")) {
						imagebutton_overlay.setVisibility(View.VISIBLE);
						if (obj.getInt("cartNum") >= 100) {
							imagebutton_overlay.setText(obj.getInt("99+"));
						} else {
							imagebutton_overlay.setText(obj.getInt("cartNum") + "");
						}
						dismissDialog();
						showToast("加入购物车成功");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				showToast("加入购物车失败" + arg0.toString());
			}
		});
	}

	/**
	 * 收藏商品
	 */
	public void collectionProduct() {
		BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("productId", mProductId);
		task.askCookieRequest(SystemConfig.COLLECTION_PRODUCT, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("collectionProduct", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					if (obj.getBoolean("success")) {
						imageview_collection_pro.setImageResource(R.drawable.followr);
						textview_collection_pro.setText("已收藏");
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
				showToast("收藏商品失败" + arg0.toString());
			}
		});
	}

	/**
	 * 取消收藏商品
	 */
	public void cancelCollectionProduct() {
		BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("productId", mProductId);
		task.askCookieRequest(SystemConfig.CANCEL_COLLECTION_PRODUCT, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("cancelcollectionProduct", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					if (obj.getBoolean("success")) {
						imageview_collection_pro.setImageResource(R.drawable.follow);
						textview_collection_pro.setText("收藏");
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
				showToast("收藏商品失败" + arg0.toString());
			}
		});
	}

	private void showAskDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(BookDetailMoreActivity.this);
		builder.setMessage("电话：" + mobile);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("呼叫", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));
				startActivity(intent);
			}
		});
		builder.create().show();
	}
}
