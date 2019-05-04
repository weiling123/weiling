package com.beijing.beixin.ui.classify;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.NewProductBean;
import com.beijing.beixin.pojo.NewProductBean.product;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.base.BaseFragment;
import com.beijing.beixin.ui.classify.newproduct.NewProductFragment;
import com.beijing.beixin.utils.LogUtil;
import com.beijing.beixin.utils.ScreenUtil;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class NewProductActivity extends BaseActivity {

	private String mType;
	private String mTitle;
	private TextView mShowNoDateTView;
	private View mContLayout;
	private TabHost mTabHost;
	private NewProductBean[] mNewProductBeans;
	private int mTabCurrentIndex;
	private ViewPager mViewPager;
	private BaseFragment[] mFragments;
	private HorizontalScrollView mHorizontalScrollView;
	private int mTabWidth;
	private boolean misMan;
	private int mScreenWidth;
	private boolean mIsScroll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_product);
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		mScreenWidth = ScreenUtil.getScreenWidth(this);
		mTabWidth = mScreenWidth / 3;
		mShowNoDateTView = (TextView) findViewById(R.id.tv_main_no_data2);
		mContLayout = findViewById(R.id.content_layout);
		mTabHost = (TabHost) findViewById(R.id.tabhost);
		Intent intent = getIntent();
		mType = intent.getStringExtra("type");
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mTitle = intent.getStringExtra("title");
		if ("1".equals(mType)) {
			setNavigationTitle("新品上架");
		} else if ("2".equals(mType)) {
			if ("hot".equals(mTitle)) {
				setNavigationTitle("精品热销");
			} else {
				setNavigationTitle("限时秒杀");
			}
		}
		mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
		sendhttpHOT();
	}

	public List<product> getProduct(int index) {
		return mNewProductBeans[index].getProducts();
	}

	private void intViewPage() {
		mFragments = new BaseFragment[mNewProductBeans.length];
		for (int i = 0; i < mNewProductBeans.length; i++) {
			mFragments[i] = NewProductFragment.instance(i);
		}
		mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int index) {
				if (misMan) {
					int bX = mHorizontalScrollView.getScrollX();
					int endX = bX + mScreenWidth;
					int showXB = index * mTabWidth;
					int showXE = showXB + mTabWidth;
					if (showXE > endX || showXB < bX) {
						if (index > mTabCurrentIndex) {
							mHorizontalScrollView.scrollTo(mTabWidth * index, 0);
						} else {
							mHorizontalScrollView.scrollTo(mTabWidth * (index - 2), 0);
						}
					}
					mTabHost.setCurrentTab(index);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			// arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。
			@Override
			public void onPageScrollStateChanged(int arg0) {
				if (arg0 == 1) {
					mIsScroll = true;
				} else if (arg0 == 2) {
					if (mIsScroll = true) {
						misMan = true;
					}
				} else {
					mIsScroll = false;
					misMan = false;
				}
			}
		});
	}

	private void initTabs() {
		mTabHost.setup();
		for (int i = 0; i < mNewProductBeans.length; i++) {
			View layout = getLayoutInflater().inflate(R.layout.tab_product_custom, null);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mTabWidth,
					LayoutParams.WRAP_CONTENT);
			layout.setLayoutParams(layoutParams);
			TextView nameTView = (TextView) layout.findViewById(R.id.tv);
			nameTView.setText(mNewProductBeans[i].getTitle());
			mTabHost.addTab(mTabHost.newTabSpec("" + i).setIndicator(layout).setContent(R.id.view_test));
		}
		mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				mTabCurrentIndex = Integer.parseInt(tabId);
				mViewPager.setCurrentItem(mTabCurrentIndex);
			}
		});
	}

	private void sendhttpHOT() {
		BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", mType);
		if ("2".equals(mType)) {
			params.addBodyParameter("moduleTitle", "bf_feature_center_links");
			params.addBodyParameter("productLeftNm", "bf_feature_center_recommend_tab");
		} else {
			params.addBodyParameter("moduleTitle", "bf_new_book_center_tabs");
			params.addBodyParameter("productLeftNm", "bf_new_book_tab");
			params.addBodyParameter("productRightNm", "_left_recommend");
		}
		showDialog("正在加载中。。。");
		task.askNormalRequest(SystemConfig.INDEX_ALL_PRODUCTS, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				LogUtil.e("ResponseInfo", arg0.result.toString());
				try {
					JSONObject jsonObject = new JSONObject(arg0.result.toString());
					ArrayList<NewProductBean> newProductBean = (ArrayList<NewProductBean>) JSON
							.parseArray(jsonObject.getString("hots_products").toString(), NewProductBean.class);
					if (newProductBean == null || newProductBean.size() == 0) {
						mShowNoDateTView.setText("该类商品暂无数据");
						mShowNoDateTView.setVisibility(View.VISIBLE);
						mContLayout.setVisibility(View.GONE);
						return;
					}
					mNewProductBeans = new NewProductBean[newProductBean.size()];
					mNewProductBeans = newProductBean.toArray(mNewProductBeans);
					initTabs();
					intViewPage();
					dismissDialog();
				} catch (JSONException e) {
					dismissDialog();
					mShowNoDateTView.setText("获取异常");
					mShowNoDateTView.setVisibility(View.VISIBLE);
					mContLayout.setVisibility(View.GONE);
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				mShowNoDateTView.setText("获取失败");
				mShowNoDateTView.setVisibility(View.VISIBLE);
				mContLayout.setVisibility(View.GONE);
			}
		});
	}

	private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

		FragmentManager fm;

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return mFragments[arg0];
		}

		@Override
		public int getCount() {
			return mFragments.length;
		}

		@Override
		public Object instantiateItem(View container, int position) {
			// TODO Auto-generated method stub
			Fragment fragment = (Fragment) super.instantiateItem(container, position);
			String fragmentTag = fragment.getTag();
			FragmentTransaction ft = fm.beginTransaction();
			// 移除旧的fragment
			ft.remove(fragment);
			// 换成新的fragment
			fragment = mFragments[position];
			// 添加新fragment时必须用前面获得的tag，这点很重要
			ft.add(container.getId(), fragment, fragmentTag);
			ft.attach(fragment);
			ft.commit();
			return fragment;
		}
	}
}
