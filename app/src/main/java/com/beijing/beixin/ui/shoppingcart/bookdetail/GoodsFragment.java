package com.beijing.beixin.ui.shoppingcart.bookdetail;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.ConnectProAdapter;
import com.beijing.beixin.adapter.ViewHolder;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.dialog.DialogBookDetail;
import com.beijing.beixin.dialog.DialogBookDetail.GridViewListener;
import com.beijing.beixin.pojo.ProductDetail;
import com.beijing.beixin.pojo.PromotionBean;
import com.beijing.beixin.pojo.detailBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseFragment;
import com.beijing.beixin.ui.classify.ClassifyMoreProductActivity;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity.DetailInterface;
import com.beijing.beixin.ui.shoppingcart.ShopActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.ExpandListView;
import com.beijing.beixin.utils.Utils;
import com.beijing.beixin.widget.HorizontalListView;
import com.beijing.beixin.widget.ScrollViewContainer;
import com.beijing.beixin.widget.ViewPageIgnore;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.beijing.beixin.bean.ProductSpec;
import com.beijing.beixin.bean.Sku;

public class GoodsFragment extends BaseFragment implements OnClickListener, DetailInterface {

	private BookDetailActivity mBookDetailActivity;

	private BitmapUtil mBitmapUtil = new BitmapUtil();
	/**
	 * 商品名称
	 */
	private TextView textview_product_name;
	/**
	 * 商家logo
	 */
	private ImageView imageview_shop;
	/**
	 * 服务
	 */
	private TextView textview_shop_service_point;
	/**
	 * 商品描述
	 */
	private TextView textview_shop_point;
	/**
	 * 时效
	 */
	private TextView textview_shop_time_point;
	/**
	 * 更多卖家
	 */
	private LinearLayout layout_more_shop;
	/**
	 * 已买的个数
	 */
	private TextView book_count;
	/**
	 * 库存个数
	 */
	private TextView bookstore;
	/**
	 * 其他商铺
	 */
	private TextView textview_other_shop;
	/**
	 * 作者和出本社信息 layout
	 */
	private RelativeLayout rl_writer;
	/**
	 * 作者
	 */
	private TextView textview_writer_name;
	/**
	 * 出本社信息
	 */
	private TextView textview_press_name;
	/**
	 * 收藏的人数
	 */
	private TextView textview_shop_account;
	private TextView textview_shop_count;
	/**
	 * 服务的个数
	 */
	private TextView textview_shop_service_account;
	private TextView textview_service_count;
	/**
	 * 时效 数目
	 */
	private TextView textview_shop_time_account;
	private TextView textview_time_count;
	/**
	 * 当没有相关商品时进行的展示“无数据”
	 */
	private TextView nodata;
	/**
	 * 服务的信息 “有XXX发货并提供售后服务”
	 */
	private TextView ttextview_service_name;
	/**
	 * 评论的内容
	 */
	private TextView tv_commcount;
	/**
	 * 会员价
	 */
	private TextView textview_now_price;
	/**
	 * 店铺的名字
	 */
	private TextView textview_shop;
	/**
	 * 市场价
	 */
	private TextView old_price;

	private HorizontalListView hlv;

	private ConnectProAdapter hlva;

	private ExpandListView listview_sale_promotion;
	private ListView listview_sale_promotion2;
	private TextView textview_sale_promotion;
	private ImageView imageview_sale_promotion;

	private ScrollViewContainer mScrollViewContainer;
	private Boolean listShowOrHide = true;
	private CommonAdapter<PromotionBean> adapter;

	/**
	 * ViewPager
	 */
	private ViewPageIgnore viewPager;
	/**
	 * 装ImageView数组
	 */
	private ImageView[] mImageViews;
	/**
	 * 图片资源id
	 */
	private String[] imgIdArray;
	private List<String> picList = new ArrayList<String>();
	private View root;

	private int mBuyNum;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBookDetailActivity = (BookDetailActivity) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_book_detail_goods, container, false);

		mBookDetailActivity.mMoreInfoFragment = new MoreInfoFragment();
		FragmentTransaction fragmentTransaction = mBookDetailActivity.getSupportFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.fragment_container, mBookDetailActivity.mMoreInfoFragment);
		fragmentTransaction.commit();

		mScrollViewContainer = (ScrollViewContainer) root.findViewById(R.id.scrollViewContainer);
		mScrollViewContainer.setPageChange(mBookDetailActivity);
		mScrollViewContainer.setBottomViewListener(mBookDetailActivity.mMoreInfoFragment);

		// 店铺名称监听
		root.findViewById(R.id.iv_shop).setOnClickListener(this);
		// 进入店铺监听
		root.findViewById(R.id.layout_into_shop).setOnClickListener(this);
		// 联系客服监听
		root.findViewById(R.id.call_tel).setOnClickListener(this);
		// 商品名字的监听
		root.findViewById(R.id.name_layout).setOnClickListener(this);
		textview_product_name = (TextView) root.findViewById(R.id.textview_product_name);

		textview_writer_name = (TextView) root.findViewById(R.id.textview_writer_name);
		textview_press_name = (TextView) root.findViewById(R.id.textview_press_name);
		ttextview_service_name = (TextView) root.findViewById(R.id.ttextview_service_name);
		textview_time_count = (TextView) root.findViewById(R.id.textview_time_count);
		textview_shop_account = (TextView) root.findViewById(R.id.textview_shop_account);
		tv_commcount = (TextView) root.findViewById(R.id.tv_commcount);
		textview_shop_service_account = (TextView) root.findViewById(R.id.textview_shop_service_account);
		textview_shop_time_account = (TextView) root.findViewById(R.id.textview_shop_time_account);

		textview_sale_promotion = (TextView) root.findViewById(R.id.textview_sale_promotion);
		imageview_sale_promotion = (ImageView) root.findViewById(R.id.imageview_sale_promotion);
		textview_sale_promotion.setOnClickListener(this);
		imageview_sale_promotion.setOnClickListener(this);
		listview_sale_promotion = (ExpandListView) root.findViewById(R.id.listview_sale_promotion);
		listview_sale_promotion2 = (ListView) root.findViewById(R.id.listview_sale_promotion2);
		// 评论的监听
		root.findViewById(R.id.good_comment).setOnClickListener(this);
		old_price = (TextView) root.findViewById(R.id.old_price);
		textview_now_price = (TextView) root.findViewById(R.id.textview_now_price);
		imageview_shop = (ImageView) root.findViewById(R.id.imageview_shop);
		textview_shop_point = (TextView) root.findViewById(R.id.textview_shop_point);
		textview_service_count = (TextView) root.findViewById(R.id.textview_service_count);
		nodata = (TextView) root.findViewById(R.id.nodata);

		hlv = (HorizontalListView) root.findViewById(R.id.horizontallistview1);
		hlva = new ConnectProAdapter(mBookDetailActivity);
		hlva.setData(new ArrayList<detailBean>());
		hlv.setAdapter(hlva);
		hlv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), BookDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("ProductId", mBookDetailActivity.mDetailBeans.get(position).getProductId() + "");
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		textview_shop_service_point = (TextView) root.findViewById(R.id.textview_shop_service_point);
		textview_shop_time_point = (TextView) root.findViewById(R.id.textview_shop_time_point);

		textview_shop_count = (TextView) root.findViewById(R.id.textview_shop_count);
		textview_shop = (TextView) root.findViewById(R.id.textview_shop);
		rl_writer = (RelativeLayout) root.findViewById(R.id.rl_writer);
		layout_more_shop = (LinearLayout) root.findViewById(R.id.layout_more_shop);
		layout_more_shop.setOnClickListener(this);
		textview_other_shop = (TextView) root.findViewById(R.id.textview_other_shop);
		setShopLayout();
		book_count = (TextView) root.findViewById(R.id.book_count);
		bookstore = (TextView) root.findViewById(R.id.bookstore);
		root.findViewById(R.id.layout_book_count).setOnClickListener(this);

		return root;
	}

	private void setSkuUI() {
		bookstore.setText(mBookDetailActivity.mProductDetail.getSku().getCurrentNum() + "件");
		textview_product_name.setText(mBookDetailActivity.mProductDetail.getProductNm()+ " "
				+ mBookDetailActivity.mProductDetail.getSku().getSpecNm());
		book_count.setText(mBuyNum + "件"+ " "
				+ mBookDetailActivity.mProductDetail.getSku().getSpecNm());
		textview_now_price
				.setText("¥" + Utils.parseDecimalDouble2(mBookDetailActivity.mProductDetail.getSku().getSalePrice()));
	}

	/**
	 * 初始化首页的大图轮播
	 */
	private void initViewPager() {
		for (int i = 0; i < mBookDetailActivity.mProductDetail.getImageList().size(); i++) {
			picList.add(mBookDetailActivity.mProductDetail.getImageList().get(i).toString());
		}
		// mBookDetailActivity.mProductDetail.getImageList();
		viewPager = (ViewPageIgnore) root.findViewById(R.id.viewPager);
		// 载入图片资源ID
		imgIdArray = new String[picList.size()];
		for (int i = 0; i < picList.size(); i++) {
			imgIdArray[i] = picList.get(i);
		}
		// 将点点加入到ViewGroup中
		mImageViews = new ImageView[imgIdArray.length];
		viewPager.setIsIgnore(imgIdArray.length > 1 );
		
		final TextView pageIndex = (TextView) root.findViewById(R.id.pageIndex); 
		if(imgIdArray.length > 1 ){
			pageIndex.setVisibility(View.VISIBLE);
			setTextView(pageIndex,1,mImageViews.length);
		}else{
			pageIndex.setVisibility(View.GONE);
		}
		for (int i = 0; i < mImageViews.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			mImageViews[i] = imageView;
			String url;
			if (!imgIdArray[i].equals("")) {
				url = imgIdArray[i];
			} else {
				url = "http://asfgsf";
			}
			BitmapUtil util = new BitmapUtil();
			util.displayImage(getActivity(), imageView, url);
		}
		// 设置Adapter
		viewPager.setAdapter(new MyAdapter());
		viewPager.setFocusable(true);
		// 设置监听，主要是设置点点的背景
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				setTextView(pageIndex,position+1,mImageViews.length);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	
	
	private void setTextView(TextView textView,int page,int length){
		String src = page+"/"+length;
		int index = (page+"").length();
		Spannable wordtoSpan = new SpannableString(src);  
		int sizeSamil = (int) mBookDetailActivity.getResources().getDimension(R.dimen.tv_textsize10);
		int sizeBig = (int) mBookDetailActivity.getResources().getDimension(R.dimen.tv_textsize15);
		wordtoSpan.setSpan(new AbsoluteSizeSpan(sizeBig), 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
		wordtoSpan.setSpan(new AbsoluteSizeSpan(sizeSamil), index, src.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
		textView.setText(wordtoSpan);  
	}

	public class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mImageViews.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(mImageViews[position % mImageViews.length]);
		}

		/**
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
		 */
		@Override
		public Object instantiateItem(View container, final int position) {
			try {
				((ViewPager) container).addView(mImageViews[position], 0);
				mImageViews[position].getDrawable();
				mImageViews[position].setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// goToActivity(mPageAppClientModuleVo, position);
					}
				});
			} catch (Exception e) {
				// handler something
			}
			return mImageViews[position];
		}
	}


	public void goBack() {
		mScrollViewContainer.goBack();
	}

	private void setShopLayout() {
		if (mBookDetailActivity.mShopSize > 1) {
			layout_more_shop.setVisibility(View.VISIBLE);
			textview_other_shop.setText("共" + mBookDetailActivity.mShopSize + "家");
		} else {
			layout_more_shop.setVisibility(View.GONE);
		}
	}

	public void updateUI() {
		if ("Y".equals(mBookDetailActivity.mProductDetail.getStandardAttrGroup())) {
			List<ProductDetail.detail> dList = mBookDetailActivity.mProductDetail.getDetailList();
			for (int i = 0; i < dList.size(); i++) {
				if ("AUTHOR".equals(dList.get(i).getInnerCode())) {
					rl_writer.setVisibility(View.VISIBLE);
					textview_writer_name.setText(dList.get(i).getValueString());
				}
				if ("PUBLISHER".equals(dList.get(i).getInnerCode())) {
					textview_press_name.setText(dList.get(i).getValueString());
					rl_writer.setVisibility(View.VISIBLE);
				}
			}
		} else {
			rl_writer.setVisibility(View.GONE);
		}

		String shopName = mBookDetailActivity.mProductDetail.getShop().getShopNm();
		if (TextUtils.isEmpty(shopName)) {
			ttextview_service_name.setText("");
		} else {
			ttextview_service_name.setText("由 " + shopName + " 负责发货，并提供售后服务");
		}

		double speed = src2Double(mBookDetailActivity.mProductDetail.getShop().getSellerSendOutSpeed());
		if (speed <= 2) {
			textview_time_count.setBackgroundColor(Color.parseColor("#288146"));
			textview_time_count.setText("低");
		} else if (speed <= 4) {
			textview_time_count.setBackgroundColor(Color.parseColor("#ff9000"));
			textview_time_count.setText("中");
		} else {
			textview_time_count.setBackgroundColor(Color.parseColor("#e8360f"));
			textview_time_count.setText("高");
		}

		textview_shop_time_point.setText(speed + "");

		double sellerServiceAttitude = src2Double(
				mBookDetailActivity.mProductDetail.getShop().getSellerServiceAttitude());
		if (sellerServiceAttitude <= 2) {
			textview_service_count.setBackgroundColor(Color.parseColor("#288146"));
			textview_service_count.setText("低");
		} else if (sellerServiceAttitude <= 4) {
			textview_service_count.setBackgroundColor(Color.parseColor("#ff9000"));
			textview_service_count.setText("中");
		} else {
			textview_service_count.setBackgroundColor(Color.parseColor("#e8360f"));
			textview_service_count.setText("高");
		}

		textview_shop_service_point.setText(sellerServiceAttitude + "");

		String shopCount = mBookDetailActivity.mProductDetail.getAttentionShopCount();
		if (TextUtils.isEmpty(shopCount)) {
			textview_shop_account.setText("0");
		} else {
			textview_shop_account.setText(shopCount);
		}

		tv_commcount.setText(mBookDetailActivity.mProductDetail.getCommentTotalCount() + "条评论");

		String productCount = mBookDetailActivity.mProductDetail.getProductCount();
		if (TextUtils.isEmpty(productCount)) {
			textview_shop_service_account.setText("0");
		} else {
			textview_shop_service_account.setText(productCount);
		}

		String shopDynamic = mBookDetailActivity.mProductDetail.getShopDynamic();
		if (TextUtils.isEmpty(shopDynamic)) {
			textview_shop_time_account.setText("0");
		} else {
			textview_shop_time_account.setText(shopDynamic);
		}

		old_price.setText("¥" + Utils.parseDecimalDouble2(mBookDetailActivity.mProductDetail.getMarketPrice()));
		old_price.getPaint().setAntiAlias(true);// 抗锯齿
		old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰

		mBitmapUtil.displayImage(mBookDetailActivity, imageview_shop,
				mBookDetailActivity.mProductDetail.getShop().getShopLogo());

		String productDescrSame = mBookDetailActivity.mProductDetail.getShop().getProductDescrSame();
		if (TextUtils.isEmpty(productDescrSame)) {
			textview_shop_point.setText("");
		} else {
			textview_shop_point.setText(Utils.parseDecimalDouble3(productDescrSame));
		}

		double productDescrNum = src2Double(productDescrSame);
		if (productDescrNum <= 2) {
			textview_shop_count.setBackgroundColor(Color.parseColor("#288146"));
			textview_shop_count.setText("低");
		} else if (productDescrNum <= 4) {
			textview_shop_count.setBackgroundColor(Color.parseColor("#ff9000"));
			textview_shop_count.setText("中");
		} else {
			textview_shop_count.setBackgroundColor(Color.parseColor("#e8360f"));
			textview_shop_count.setText("高");
		}

		if (mBookDetailActivity.mDetailBeans.size() != 0) {
			hlv.setVisibility(View.VISIBLE);
			nodata.setVisibility(View.GONE);
			hlva.setData(mBookDetailActivity.mDetailBeans);
			hlva.notifyDataSetChanged();
		} else {
			hlv.setVisibility(View.GONE);
			nodata.setVisibility(View.VISIBLE);
		}

		int storeNum = mBookDetailActivity.mProductDetail.getSku().getCurrentNum();
		mBuyNum = storeNum > 0 ? 1 : 0;
		textview_shop.setText(mBookDetailActivity.mProductDetail.getShop().getShopNm());
		setSkuUI();

		setShopLayout();
		hlva.notifyDataSetChanged();
		adapter = new CommonAdapter<PromotionBean>(getActivity(),
				mBookDetailActivity.mProductDetail.getAppProductBusinessRule(), R.layout.item_promotion) {

			@Override
			public void convert(ViewHolder helper, PromotionBean item) {
				helper.setText(R.id.textview_ruleType, item.getRuleType());
				helper.setText(R.id.textview_ruleNm, item.getBusinessRuleNm());
			}
		};
		listview_sale_promotion.setAdapter(adapter);
		listview_sale_promotion2.setAdapter(adapter);
		initViewPager();
	}

	private double src2Double(String text) {
		if (TextUtils.isEmpty(text)) {
			return 0;
		}
		try {
			return Double.parseDouble(text);
		} catch (Exception e) {
			return 0;
		}

	}

	public class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			if (mBookDetailActivity.mProductDetail == null) {
				return 0;
			}

			if (mBookDetailActivity.mProductDetail.getImageList() == null) {
				return 0;
			}

			return mBookDetailActivity.mProductDetail.getImageList().size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeViewAt(position % mBookDetailActivity.mProductDetail.getImageList().size());
		}

		/**
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
		 */
		@Override
		public Object instantiateItem(View container, int position) {

			ImageView imageView = new ImageView(mBookDetailActivity);
			String url = mBookDetailActivity.mProductDetail.getImageList().get(position).toString();
			if (!TextUtils.isEmpty(url)) {
				mBitmapUtil.displayImage(mBookDetailActivity, imageView, url);
			}
			((ViewPager) container).addView(imageView, 0);
			return imageView;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.textview_sale_promotion:
		case R.id.imageview_sale_promotion:
			if (listShowOrHide) {
				imageview_sale_promotion.setImageResource(R.drawable.red_arrow_up);
				listview_sale_promotion.setAdapter(adapter);
				listview_sale_promotion.setVisibility(View.VISIBLE);
				listview_sale_promotion2.setVisibility(View.GONE);
				listShowOrHide = false;
			} else {
				imageview_sale_promotion.setImageResource(R.drawable.red_arrow_down);
				listview_sale_promotion2.setAdapter(adapter);
				listview_sale_promotion.setVisibility(View.GONE);
				listview_sale_promotion2.setVisibility(View.VISIBLE);
				listShowOrHide = true;
			}
			break;
		case R.id.iv_shop:
		case R.id.layout_into_shop:
			Intent inShop = new Intent(mBookDetailActivity, ShopActivity.class);
			inShop.putExtra("shopId", mBookDetailActivity.mProductDetail.getShop().getShopInfId().toString());
			startActivity(inShop);
			break;
		case R.id.call_tel:
			mBookDetailActivity.showAskDialog();
			break;
		// 跳转到第二页
		case R.id.name_layout:
			mBookDetailActivity.setPageIndex(1);
			break;
		// 跳转到第三页
		case R.id.good_comment:
			if (mBookDetailActivity.mProductDetail.getCommentTotalCount() == 0) {
				showToast("暂无评论");
				return;
			}
			mBookDetailActivity.setPageIndex(2);
			break;
		case R.id.layout_more_shop:
			Intent intentMoreShop = new Intent(mBookDetailActivity, ClassifyMoreProductActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("bookname", mBookDetailActivity.mProductDetail.getProductNm());
			bundle.putString("pid", mBookDetailActivity.mProductId);
			bundle.putString("More", "More");
			intentMoreShop.putExtras(bundle);
			startActivity(intentMoreShop);
			break;
		case R.id.layout_book_count:
			if(System.currentTimeMillis() - mshowTime <1000){
				return;
			}
			mshowTime = System.currentTimeMillis();
			getSpecList(mBookDetailActivity.mProductDetail.getSku().getSkuId() + "");
			break;
		}
	}

	/**
	 * 获取规格列表
	 */
	private void getSpecList(String skuId) {
		BaseTask task = new BaseTask(mBookDetailActivity);
		RequestParams params = new RequestParams();
		params.addBodyParameter("productId", mBookDetailActivity.mProductId);
		params.addBodyParameter("skuId", skuId);
		task.askCookieRequest(SystemConfig.PRODUCT_SPEC, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					Log.e("商品规格", arg0.result.toString());
					ProductSpec productSpec = JSON.parseObject(arg0.result, ProductSpec.class);
					showDialog(productSpec);
				} catch (Exception e) {
					showToast("显示商品规格失败");
					e.printStackTrace();
				}
				dismissDialog();
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				showToast("显示商品规格失败");
				dismissDialog();
			}
		});
	}

	private long mshowTime;
	private synchronized void showDialog(ProductSpec productSpec) {
		
		String produceUrl = "";
		if (mBookDetailActivity.mProductDetail != null && mBookDetailActivity.mProductDetail.getImageList() != null
				&& mBookDetailActivity.mProductDetail.getImageList().size() > 0) {
			produceUrl = mBookDetailActivity.mProductDetail.getImageList().get(0).toString();
		}
		

		new DialogBookDetail(mBookDetailActivity, productSpec, mBookDetailActivity.mProductDetail.getSku(), produceUrl,
				new GridViewListener() {
					@Override
					public void onGridSelect(Sku sku, int buyNum) {
						if (sku == null) {
							if (mBuyNum != buyNum) {
								mBuyNum = buyNum;
							}
							book_count.setText(mBuyNum + "件"+ " "
									+ mBookDetailActivity.mProductDetail.getSku().getSpecNm());
							mBookDetailActivity.setCanAddCart();
						} else {
							mBuyNum = buyNum;
							mBookDetailActivity.mProductDetail.setSku(sku);
							setSkuUI();
							mBookDetailActivity.setCanAddCart();
						}

					}

				});
	}

	@Override
	public int getProductNum() {
		return mBuyNum;
	}

}
