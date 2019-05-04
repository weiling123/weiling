package com.beijing.beixin.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.beijing.beixin.R;
import com.beijing.beixin.ui.activity.yindaoye.MyViewPager;
import com.beijing.beixin.ui.base.BaseFragment;
import com.beijing.beixin.utils.ScreenUtil;

public abstract class PageTabFragment extends BaseFragment {

	public Fragment[] mFragments;
	public int[] mPostions;
	private MyViewPager mPager;
	public View mCursor;
	public int mCurrIndex;

	public void init(View rootView, View[] views, int cursorId) {

		mFragments = getFragment();
		mPostions = new int[getTabNum()];
		doDefaultCursor(rootView, views, cursorId);

		mPager = (MyViewPager) rootView.findViewById(R.id.viewpager);
		mPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager()));
		mPager.setCurrentItem(0);
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

	public void doDefaultCursor(View rootView, View[] views, int cursorId) {
		int quarterWidth = ScreenUtil.getScreenWidth(getActivity()) / getTabNum();
		for (int i = 0; i < mPostions.length; i++) {
			views[i].setOnClickListener(new MyOnClickListener(i));
			mPostions[i] = quarterWidth * i;
		}
		if (cursorId != -1) {
			mCursor = rootView.findViewById(cursorId);
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
			mPager.setCurrentItem(mPageIndex);
		}
	}

	public abstract Fragment[] getFragment();

	public abstract int getTabNum();

	public abstract void initPageSelected(int currIndex, int nextIndex);
}
