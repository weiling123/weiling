package com.beijing.beixin.ui.shoppingcart;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.ViewHolder;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.BookMoreDetailBean;
import com.beijing.beixin.pojo.ProductDetail;
import com.beijing.beixin.pojo.ProductSepcBean;
import com.beijing.beixin.pojo.detailBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.MainActivity;
import com.beijing.beixin.ui.PageTabActivity;
import com.beijing.beixin.ui.activity.yindaoye.MyViewPager;
import com.beijing.beixin.ui.base.BaseFragment;
import com.beijing.beixin.ui.myself.login.LoginActivity;
import com.beijing.beixin.ui.shoppingcart.bookdetail.CommentsFragement;
import com.beijing.beixin.ui.shoppingcart.bookdetail.GoodsFragment;
import com.beijing.beixin.ui.shoppingcart.bookdetail.MoreInfoFragment;
import com.beijing.beixin.utils.MResource;
import com.beijing.beixin.utils.NetWorkUtils;
import com.beijing.beixin.utils.ScreenUtil;
import com.beijing.beixin.utils.ToastUtil;
import com.beijing.beixin.utils.Utils;
import com.beijing.beixin.utils.sqlite.FootInfo;
import com.beijing.beixin.widget.ScrollViewContainer.PageChange;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class BookDetailActivity extends PageTabActivity implements OnClickListener, PageChange {
	/**
	 * 加入购物车
	 */
	private TextView button_to_pay;
	/**
	 * 侧滑 抽屉
	 */
	private DrawerLayout drawerLayout;
	/**
	 * 浏览足迹
	 */
	private LinearLayout ll_foot_list;
	/**
	 * 浏览足迹数据展示
	 */
	private ListView mListView;
	/**
	 * 收藏的标识
	 */
	private ImageView imageview_collection_pro;
	/**
	 * 收藏的文字
	 */
	private TextView textview_collection_pro;
	/**
	 * 购物车的数量
	 */
	private TextView imagebutton_overlay;

	public String mProductId;

	public ProductDetail mProductDetail;

	public List<detailBean> mDetailBeans;

	private View mTitleIconMore;

	private static final int TAB_NUM = 3;

	private View load_faile;

	private View content_view;

	public int mShopSize;

	public String mPluCode;

	public MoreInfoFragment mMoreInfoFragment;

	private ViewFlipper viewFlipper;

	public List<BookMoreDetailBean> mDetailMore = new ArrayList<BookMoreDetailBean>();

	public List<BookMoreDetailBean> mDetailMorePage = new ArrayList<BookMoreDetailBean>();
	public String[] specName;
	public String[][] specDetail;
	public boolean mIScanAddCart;
	public List<ProductSepcBean> listSpec;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_detail);
		Intent intent = getIntent();
		mProductId = intent.getStringExtra("ProductId");
		// mProductId = "137778";
		mPluCode = intent.getStringExtra("pluCode");
		mShopSize = intent.getIntExtra("shopSize", 0);
		mIScanAddCart = intent.getBooleanExtra("canAddCart", true);

		load_faile = findViewById(R.id.load_faile);
		content_view = findViewById(R.id.content_view);
		findViewById(R.id.load_faile_button).setOnClickListener(this);

		TextView[] tabsTView = new TextView[TAB_NUM];
		for (int i = 0; i < TAB_NUM; i++) {
			tabsTView[i] = (TextView) findViewById(MResource.getIdByName(this, "tv_tab_" + i));
		}

		init();
		
		init(tabsTView, R.id.cursor);

		initData();

		((MyViewPager) mPager).setNoScroll(false);
	}

	@Override
	public void doDefaultCursor(View[] views, int cursorId) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		
		TextView tabTView = (TextView) views[0];

		int quarterWidth = (int) (tabTView.getPaint().measureText("商品") + 40);

		mCursor = findViewById(cursorId);
		mCursor.getLayoutParams().width = quarterWidth;
		
		int rightImwidth = (int) (displayMetrics.density * 35*2 +10+0.5);
		
		int screenWidth = ScreenUtil.getScreenWidth(this);
		

		int viewWidth = (screenWidth - rightImwidth * 2) / 3;

		for (int i = 0; i < views.length; i++) {
			views[i].setOnClickListener(new MyOnClickListener(i));
			mPostions[i] = viewWidth * i;
		}

		mCursor.setX((viewWidth - quarterWidth) / 2);
	}

	/**
	 * 顶部的三个按钮的监听
	 */
	private void listenTitle() {
		// 返回按钮的监听
		findViewById(R.id.left_back).setOnClickListener(this);
		// 浏览足迹的监听
		findViewById(R.id.right_foot).setOnClickListener(this);
		// 更多的监听
		mTitleIconMore = findViewById(R.id.iv_more);
		mTitleIconMore.setOnClickListener(this);
	}

	private void init() {
		listenTitle();

		imagebutton_overlay = (TextView) findViewById(R.id.imagebutton_overlay);
		imageview_collection_pro = (ImageView) findViewById(R.id.imageview_collection_pro);
		textview_collection_pro = (TextView) findViewById(R.id.textview_collection_pro);
		button_to_pay = (TextView) findViewById(R.id.button_to_pay);
		if (!mIScanAddCart) {
			button_to_pay.setEnabled(false);
		}
		// 咨询的监听
		findViewById(R.id.layout_ask).setOnClickListener(this);
		// 店铺的监听
		findViewById(R.id.layout_shop).setOnClickListener(this);
		// 加入购物车
		button_to_pay.setOnClickListener(this);
		// 跳转购物车的监听
		findViewById(R.id.ll_addcart).setOnClickListener(this);
		// 收藏的监听
		findViewById(R.id.layout_collection_pro).setOnClickListener(this);
		drawerLayout = (DrawerLayout) findViewById(R.id.dl_foot_list);
		drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		ll_foot_list = (LinearLayout) findViewById(R.id.ll_foot_list);
		mListView = (ListView) findViewById(R.id.foot_listview);

		viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
		viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.view_push_up_in));
		viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.view_push_up_out));
	}

	@Override
	protected void onResume() {
		if (MyApplication.mapp.getCookieStore() != null) {
			getCartNum();
		} else {
			int num = MyApplication.mapp.getShopNum();
			setShopNum(num);
		}
		super.onResume();
	}

	private void setUIResult(boolean isSuccess) {
		if (isSuccess) {
			content_view.setVisibility(View.VISIBLE);
			load_faile.setVisibility(View.GONE);
		} else {
			content_view.setVisibility(View.GONE);
			load_faile.setVisibility(View.VISIBLE);
		}
	}

	private void setShopNum(int num) {
		if (num > 0) {
			imagebutton_overlay.setVisibility(View.VISIBLE);
			if (num >= 100) {
				imagebutton_overlay.setText("99+");
			} else {
				imagebutton_overlay.setText(num + "");
			}
		} else {
			imagebutton_overlay.setVisibility(View.INVISIBLE);
		}
	}

	private void initData() {
		if (TextUtils.isEmpty(mProductId)) {
			getProductIdByPlucode(mPluCode);
		} else {
			sendhttp();
		}
	}

	private void getProductIdByPlucode(String pluCode) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("pluCode", pluCode);
		HttpUtils utils = new HttpUtils(15 * 1000);
		utils.send(HttpMethod.POST, SystemConfig.PRODUCTID_BY_PLUCODE, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				setUIResult(false);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					Log.e("test", "网络正常" + arg0.result);
					JSONObject obj = new JSONObject(arg0.result);
					if (obj.getBoolean("success") && !"".equals(obj.getString("result"))) {
						mProductId = obj.getString("result");
						sendhttp();
					} else {
						Intent intent = new Intent(BookDetailActivity.this, MainActivity.class);
						startActivity(intent);
						finish();
					}
				} catch (JSONException e1) {
					Intent intent = new Intent(BookDetailActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
					e1.printStackTrace();
				}
			}
		});
	}

	public void sendhttp() {
		showDialog("正在请求数据，请稍后...");
		BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("networktype", NetWorkUtils.getCurrentNetType(this));
		params.addBodyParameter("productId", mProductId);
		task.askCookieRequest(SystemConfig.PRODUCT_DETAIL, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				setUIResult(true);
				try {
					JSONObject object = new JSONObject(arg0.result);
					mProductDetail = JSON.parseObject(object.getString("result"), ProductDetail.class);
					mDetailBeans = JSON.parseArray(object.getString("similarProducts"), detailBean.class);
					if (mProductDetail != null) {
						mShopSize = Integer.valueOf(mProductDetail.getShopInfProxySize());
						setCanAddCart();
						if ("Y".equals(mProductDetail.getIsCollected())) {
							imageview_collection_pro.setImageResource(R.drawable.followr);
							textview_collection_pro.setText("已收藏");
						} else {
							imageview_collection_pro.setImageResource(R.drawable.follow);
							textview_collection_pro.setText("收藏");
						}
						if (MyApplication.mapp.getCookieStore() != null) {
							addfootinfobook();
							selectfootinfo();
						}
						GoodsFragment goodsFragment = (GoodsFragment) mFragments[0];
						goodsFragment.updateUI();
					} else {
						Intent intent = new Intent(BookDetailActivity.this, MainActivity.class);
						startActivity(intent);
						finish();
					}
					dismissDialog();
				} catch (Exception e) {
					dismissDialog();
					setUIResult(false);
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				setUIResult(false);
			}
		});
	}

	public void setCanAddCart() {
		if (mProductDetail.getSku().getCurrentNum() <= 0 || !mIScanAddCart
				|| "N".equals(mProductDetail.getIsOnSale())) {
			button_to_pay.setEnabled(false);
			button_to_pay.setBackgroundResource(R.color.dark_gray);
		} else {
			button_to_pay.setEnabled(true);
			button_to_pay.setBackgroundResource(R.color.common_red_bg);
		}
	}

	/**
	 * 点击规格获取不同的颜色
	 */
	public void getSkuBySpec(String specvals) {
		BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("productId", mProductId);
		params.addBodyParameter("specvals", specvals);
		task.askCookieRequest(SystemConfig.GET_PRODUCT_SKU, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					Log.e("商品规格", arg0.result.toString());
					JSONObject object = new JSONObject(arg0.result);
					List<ProductSepcBean> list = JSON.parseArray(object.getString("result"), ProductSepcBean.class);

					specName = new String[list.size()];
					specDetail = new String[list.size()][];
					for (int i = 0; i < list.size(); i++) {
						specName[i] = list.get(i).getSpecNm();
						specDetail[i] = new String[list.get(i).getSpecValues().size()];
						for (int j = 0; j < list.get(i).getSpecValues().size(); j++) {
							specDetail[i][j] = list.get(i).getSpecValues().get(j).getSpecValueNm();
						}
					}
					dismissDialog();
				} catch (Exception e) {
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
	 * 添加浏览足迹
	 */
	public void addfootinfobook() {
		FootInfo info = new FootInfo();
		info.setFootinfoid(mProductDetail.getProductId() + "");
		info.setFootinfoname(mProductDetail.getProductNm());
		info.setLoginname(MyApplication.mapp.getCertUserName());
		if (mProductDetail.getImageList() != null && mProductDetail.getImageList().size() != 0) {
			info.setFootinfoimage(mProductDetail.getImageList().get(0).toString());
		}
		info.setFootinfoprice(mProductDetail.getUnitPrice() + "");
		info.setFootinfoshopname(mProductDetail.getShop().getShopNm());
		info.setFootinfooldprice(mProductDetail.getMarketPrice() + "");
		MyApplication.mServer.addFootInfo(info);
	}

	/**
	 * 查询浏览足迹
	 */
	public void selectfootinfo() {
		mListView.setAdapter(new CommonAdapter<FootInfo>(BookDetailActivity.this,
				MyApplication.mServer.findAllFootInfo(MyApplication.mapp.getCertUserName()), R.layout.item_foot_list) {

			@Override
			public void convert(ViewHolder helper, final FootInfo item) {
				BitmapUtils bitmapUtils = new BitmapUtils(BookDetailActivity.this);
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
						Intent intent = new Intent(BookDetailActivity.this, BookDetailActivity.class);
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_to_pay:
			showDialog("正在添加到购物车...");
			int account = getProductNum();
			if (account == 0) {
				dismissDialog();
				ToastUtil.show(BookDetailActivity.this, "请添加商品数量");
				return;
			}
			addTocart();
			break;
		case R.id.ll_addcart:
			Intent intent = new Intent(this, ShopcartActivity.class);
			startActivity(intent);
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
				startActivity(LoginActivity.class);
			}
			break;
		case R.id.left_back:
			onBackPressed();
			break;
		case R.id.iv_more:
			showwindow(mTitleIconMore);
			break;
		case R.id.right_foot:
			if (MyApplication.mapp.getCookieStore() != null) {
				drawerLayout.openDrawer(ll_foot_list);
			} else {
				startActivity(LoginActivity.class);
			}
			break;
		case R.id.layout_ask:
			showAskDialog();
			break;
		case R.id.load_faile_button:
			initData();
			break;
		case R.id.layout_shop:
			Intent inShop = new Intent(BookDetailActivity.this, ShopActivity.class);
			inShop.putExtra("shopId", mProductDetail.getShop().getShopInfId().toString());
			startActivity(inShop);
			break;
		}
	}

	@Override
	public void onBackPressed() {
		if (mFragments == null || mCurrIndex != 0) {
			setPageIndex(0);
			return;
		}
		if (mFragments == null || mMoreInfoFragment == null || mIsFirst) {
			super.onBackPressed();
		} else {
			GoodsFragment cGoodsFragment = (GoodsFragment) mFragments[0];
			cGoodsFragment.goBack();
		}
	}

	public void showAskDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(BookDetailActivity.this);
		builder.setMessage("电话：" + mProductDetail.getShopMobile().toString().trim());
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("呼叫", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent = new Intent(Intent.ACTION_CALL,
						Uri.parse("tel:" + mProductDetail.getShopMobile().toString().trim()));
				startActivity(intent);
			}
		});
		builder.create().show();
	}

	/**
	 * 获取购物车数量
	 */
	public void getCartNum() {
		BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", "normal");
		task.askCookieRequest(SystemConfig.GET_SELECTCART_NUM, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("获取购物车数量", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					if (obj.getBoolean("success")) {
						imagebutton_overlay.setVisibility(View.VISIBLE);
						int num = obj.getInt("cartNum");
						setShopNum(num);
					}
				} catch (JSONException e) {
					Log.e("获取购物车数量", arg0.toString());
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("获取购物车数量", arg0.toString());
			}
		});
	}

	/**
	 * 加入购物车
	 */
	public void addTocart() {
		BaseTask task = new BaseTask(this);
		int num = getProductNum();
		if (num == 0) {
			showToast("商品的数量不能为0");
			return;
		}
		String shopInfId = mProductDetail.getShop().getShopInfId();
		String skuId = mProductDetail.getSku().getSkuId() + "";
		task.addShoppingCard(shopInfId, skuId, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("BookDetailActivity", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					if (obj.getBoolean("success")) {
						imagebutton_overlay.setVisibility(View.VISIBLE);
						if (obj.getInt("cartNum") >= 100) {
							imagebutton_overlay.setText("99+");
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
				showToast("加入购物车失败");
			}
		}, getProductNum());
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
				showToast("收藏商品失败");
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
				showToast("取消收藏商品失败");
			}
		});
	}

	public interface DetailInterface {
		public int getProductNum();
	}

	public int getProductNum() {
		Fragment fragment = getCurrentPage();
		if (fragment instanceof DetailInterface) {
			DetailInterface detailInterface = (DetailInterface) fragment;
			return detailInterface.getProductNum();
		}
		return 0;
	}

	@Override
	public Fragment[] getFragment() {
		BaseFragment[] fragments = new BaseFragment[TAB_NUM];
		fragments[0] = new GoodsFragment();
		fragments[1] = new MoreInfoFragment();
		fragments[2] = new CommentsFragement();
		return fragments;
	}

	@Override
	public int getTabNum() {
		return TAB_NUM;
	}

	@Override
	public void initPageSelected(int currIndex, int nextIndex) {

	}

	private boolean mIsFirst = true;

	@Override
	public void onPageChange(boolean isFirst) {
		if (mIsFirst == isFirst) {
			return;
		}
		if (isFirst) {
			((MyViewPager) mPager).setNoScroll(false);
			viewFlipper.showPrevious();
			mIsCanChange = true;
		} else {
			((MyViewPager) mPager).setNoScroll(true);
			viewFlipper.showNext();
			mIsCanChange = false;
			mMoreInfoFragment.setUserVisibleHint(true);
		}

		mIsFirst = isFirst;

	}

}
