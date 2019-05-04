package com.beijing.beixin.ui;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.beijing.beixin.R;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.homepage.SearchActivity;
import com.beijing.beixin.ui.myself.BrowseTheFootprintActivity;
import com.beijing.beixin.ui.myself.MyCollectionActivity;
import com.beijing.beixin.ui.myself.login.LoginActivity;
import com.beijing.beixin.utils.DensityUtil;
import com.beijing.beixin.utils.ScreenUtil;

public abstract class PageTabActivity extends BaseActivity {

	public Fragment[] mFragments;
	public int[] mPostions;
	public ViewPager mPager;
	public View mCursor;
	public int mCurrIndex;
	private PopupWindow popupWindow;

	public boolean mIsCanChange = true;

	public void init(View[] views, int cursorId) {

		mFragments = getFragment();
		mPostions = new int[getTabNum()];
		doDefaultCursor(views, cursorId);

		mPager = (ViewPager) findViewById(R.id.viewpager);
		mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
		mPager.setCurrentItem(0);
		// if( mPager instanceof MyViewPager){
		// ((MyViewPager)mPager).setNoScroll(true);
		// }
		mPager.setOffscreenPageLimit(mFragments.length);
		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int index) {
				Animation animation = null;
				if (mCursor != null) {
					animation = new TranslateAnimation(mPostions[mCurrIndex], mPostions[index], 0, 0);
				}
				initPageSelected(mCurrIndex, index);
				mCurrIndex = index;
				if (animation != null) {
					animation.setFillAfter(true);
					animation.setDuration(300);
					mCursor.startAnimation(animation);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	public void doDefaultCursor(View[] views, int cursorId) {
		int quarterWidth = ScreenUtil.getScreenWidth(this) / getTabNum();
		for (int i = 0; i < mPostions.length; i++) {
			views[i].setOnClickListener(new MyOnClickListener(i));
			mPostions[i] = quarterWidth * i;
		}
		if (cursorId != -1) {
			mCursor = findViewById(cursorId);
			mCursor.getLayoutParams().width = quarterWidth;
		}
	}

	public Fragment getCurrentPage() {
		return mFragments[mCurrIndex];
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

	public class MyOnClickListener implements OnClickListener {

		private int mPageIndex;

		public MyOnClickListener(int pageIndex) {
			mPageIndex = pageIndex;
		}

		@Override
		public void onClick(View v) {
			if (mIsCanChange) {
				mPager.setCurrentItem(mPageIndex);
			}
		}
	}

	public void setPageIndex(int pageIndex) {
		mPager.setCurrentItem(pageIndex);
	}

	public int getPageIndex() {
		return mPager.getCurrentItem();
	}

	public void showwindow(View p) {
		int width = Integer.parseInt(DensityUtil.dp2px(this, 130) + "");
		int y = Integer.parseInt(DensityUtil.dp2px(this, 60) + "");
		int x = Integer.parseInt(DensityUtil.dp2px(this, 5) + "");
		if (popupWindow == null) {
			View v = LayoutInflater.from(this).inflate(R.layout.pop_more, null);
			LinearLayout ll_home = (LinearLayout) v.findViewById(R.id.ll_home);
			LinearLayout ll_search = (LinearLayout) v.findViewById(R.id.ll_search);
			LinearLayout ll_history = (LinearLayout) v.findViewById(R.id.ll_history);
			LinearLayout ll_foot = (LinearLayout) v.findViewById(R.id.ll_foot);
			popupWindow = new PopupWindow(v, width, LayoutParams.WRAP_CONTENT);
			// 首页
			ll_home.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					startActivity(MainActivity.class);
					finish();
					if (popupWindow != null) {
						popupWindow.dismiss();
					}
				}
			});
			// 搜索
			ll_search.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					startActivity(SearchActivity.class);
					if (popupWindow != null) {
						popupWindow.dismiss();
					}
				}
			});
			// 收藏
			ll_history.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (MyApplication.mapp.getCookieStore() != null) {
						Intent intent4 = new Intent(PageTabActivity.this, MyCollectionActivity.class);
						intent4.putExtra("flag", "pro");
						startActivity(intent4);
						if (popupWindow != null) {
							popupWindow.dismiss();
						}
					} else {
						startActivity(LoginActivity.class);
						popupWindow.dismiss();
					}
				}
			});
			// 足迹
			ll_foot.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (MyApplication.mapp.getCookieStore() != null) {
						startActivity(BrowseTheFootprintActivity.class);
						if (popupWindow != null) {
							popupWindow.dismiss();
						}
					} else {
						startActivity(LoginActivity.class);
						popupWindow.dismiss();
					}
				}
			});
		}
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAtLocation(p, Gravity.RIGHT | Gravity.TOP, x, y);
	}

	public abstract Fragment[] getFragment();

	public abstract int getTabNum();

	public abstract void initPageSelected(int currIndex, int nextIndex);
}
