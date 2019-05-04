package com.beijing.beixin.ui.classify;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.NewProductBean;
import com.beijing.beixin.pojo.NewProductBean.product;
import com.beijing.beixin.pojo.HotProduct;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.activity.yindaoye.MainFragment_Adapter;
import com.beijing.beixin.ui.activity.yindaoye.MyViewPager;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.base.BaseFragment;
import com.beijing.beixin.ui.classify.newproduct.HotProductFragment;
import com.beijing.beixin.ui.classify.newproduct.NewProductFragment;
import com.beijing.beixin.utils.LogUtil;
import com.beijing.beixin.utils.ScreenUtil;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class HotProductActivity extends BaseActivity {

	private String mType;
	private TextView mShowNoDateTView;
	private View mContLayout;
	private TabHost mTabHost;
	private int mTabCurrentIndex;
	private MyViewPager mViewPager;
	private BaseFragment[] mFragments;
	private HorizontalScrollView mHorizontalScrollView;
	private int mTabWidth;
	private boolean misMan;
	private int mScreenWidth;
	private boolean mIsScroll;
	private int mPageIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot_product_list);
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		mScreenWidth = ScreenUtil.getScreenWidth(this);
		mTabWidth = mScreenWidth / 3;
		mShowNoDateTView = (TextView) findViewById(R.id.tv_main_no_data2);
		mContLayout = findViewById(R.id.content_layout);
		mTabHost = (TabHost) findViewById(R.id.tabhost);
		Intent intent = getIntent();
		mType = intent.getStringExtra("type");
		mViewPager = (MyViewPager) findViewById(R.id.viewpager);
		setNavigationTitle("畅销排行榜");
		mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);

		Parcelable[] parcelables = getIntent().getParcelableArrayExtra("sellWells");
		HotProduct[] sellWells = new HotProduct[parcelables.length];
		System.arraycopy(parcelables, 0, sellWells, 0, parcelables.length);

		mPageIndex = getIntent().getIntExtra("sellWellIndex", 0);
		intViewPage(sellWells);

		initTabs(sellWells);
		mViewPager.setCurrentItem(mPageIndex);
		mTabHost.setCurrentTab(mPageIndex);
		mHorizontalScrollView.post(new Runnable() {
			@Override
			public void run() {
				mHorizontalScrollView.scrollTo(mTabWidth * (mPageIndex - 1), 0);
			}
		});
	}

	protected void onResume() {
		super.onResume();
	}

	List<Fragment> fragmentsList = new ArrayList<Fragment>();;

	private void intViewPage(HotProduct[] sellWells) {
		mFragments = new BaseFragment[sellWells.length];
		for (int i = 0; i < sellWells.length; i++) {
			mFragments[i] = HotProductFragment.instance(sellWells[i].getPageModuleId());
			fragmentsList.add(mFragments[i]);
		}
		mViewPager.setOffscreenPageLimit(sellWells.length);
		MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(myFragmentPagerAdapter);
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

	private void initTabs(HotProduct[] sellWells) {
		mTabHost.setup();
		for (int i = 0; i < sellWells.length; i++) {
			View layout = getLayoutInflater().inflate(R.layout.tab_product_custom, null);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mTabWidth,
					LayoutParams.WRAP_CONTENT);
			layout.setLayoutParams(layoutParams);
			TextView nameTView = (TextView) layout.findViewById(R.id.tv);
			nameTView.setText(sellWells[i].getTitle());
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

	private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
		FragmentManager fm;

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
			this.fm = fm;
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
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
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
