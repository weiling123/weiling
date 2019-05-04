package com.beijing.beixin.ui.homepage.groupby;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;
import android.widget.TextView;

import com.beijing.beixin.R;
import com.beijing.beixin.ui.base.BaseFragment;
import com.beijing.beixin.utils.PullListTask;
import com.beijing.beixin.utils.ScreenUtil;
import com.beijing.beixin.utils.ViewUtility;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase;

import com.beijing.beixin.widget.HorizontalListView;
import com.beijing.beixin.widget.HorizontalListView.OnScrollChangedListener;
import com.beijing.beixin.widget.HorizontalListView.OnScrollStateChangedListener;

public class GroupSoonFragment extends BaseFragment {

	private String mType;

	private static final int GROUP_NUM = 24;

	private static final int INDEX_NUM = 11;

	private Bean[][] mBooks = new Bean[GROUP_NUM][5];

	private int mSize;

	private MyHArrayAdapter mMyHArrayAdapter;

	private HorizontalListView mHorizontalListView;

	private int mCellWidth;

	private int mCellWidthDivder;

	private HorizontalScrollView mHorizontalScrollView;

	private int mTabWidth;

	private TabHost mTabHost;

	private int mTabCurrentIndex;

	/**
	 * 防止循环调用
	 */
	private boolean mIsManOprate = true;

	private MyPullListTask mMyPullListTask;

	public static GroupSoonFragment instance(String type) {
		GroupSoonFragment groupFragment = new GroupSoonFragment();
		Bundle bundle = new Bundle();
		bundle.putString("type", type);
		groupFragment.setArguments(bundle);
		return groupFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mType = getArguments().getString("type");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_group_soon, container, false);
		mCellWidth = (int) (getResources().getDimension(R.dimen.group_book_item_width)
				+ getResources().getDimension(R.dimen.group_book_pad) * 2);
		mCellWidthDivder = (int) (getResources().getDimension(R.dimen.group_book_divider_width)
				+ getResources().getDimension(R.dimen.group_book_divider_pad));
		mTabWidth = (int) (getResources().getDimension(R.dimen.group_tab_width));

		mSize = getIndex(GROUP_NUM);

		initBean();
		initHListview(rootView);
		initTabs(rootView);
		initPullList(rootView);
		return rootView;
	}

	private void initPullList(View rootView) {

		View loadingView = rootView.findViewById(R.id.progressBar);
		View loadFaileView = rootView.findViewById(R.id.load_faile);
		loadingView.setVisibility(View.GONE);
		loadFaileView.setVisibility(View.GONE);

		mMyPullListTask = new MyPullListTask(rootView);
		/*
		 * rootView.findViewById(R.id.load_faile_button).setOnClickListener(new
		 * OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { mMyPullListTask.startNet(); }
		 * });
		 */
		mMyPullListTask.mArrayList = new ArrayList<BookBean>();
		BookBean[] bookBeans = new BookBean[10];
		mMyPullListTask.setArraList(bookBeans, R.layout.item_frag_comment_list);

	}

	private void initTabs(View rootView) {
		mTabHost = (TabHost) rootView.findViewById(R.id.tabhost);
		// 如果没有继承TabActivity时，通过该种方法加载启动tabHost
		mTabHost.setup();

		for (int i = 0; i < GROUP_NUM; i++) {
			String name = i < 10 ? "0" + i + ":00" : i + ":00";
			View layout = getActivity().getLayoutInflater().inflate(R.layout.tab_group_custom, null);

			View cursor = layout.findViewById(R.id.cursor);
			View buying = layout.findViewById(R.id.buying);

			if (i == INDEX_NUM) {
				cursor.setVisibility(View.GONE);
				buying.setVisibility(View.VISIBLE);
			} else {
				buying.setVisibility(View.GONE);
				cursor.setVisibility(View.VISIBLE);
			}

			TextView nameTView = (TextView) layout.findViewById(R.id.tv);
			nameTView.setText(name);
			mTabHost.addTab(mTabHost.newTabSpec("" + i).setIndicator(layout).setContent(R.id.view_test));
		}

		mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				mTabCurrentIndex = Integer.parseInt(tabId);
				if (mHorizontalListView != null && mIsManOprate) {
					int index = getIndex(mTabCurrentIndex);
					int width = index * mCellWidth;
					for (int i = 0; i < index; i++) {
						if (getBean(i).mIsDivider) {
							width += mCellWidthDivder;
						}
					}

					mHorizontalListView.setSelection(index);
					mHorizontalListView.scrollTo(width);
				}
			}

		});
		mHorizontalScrollView = (HorizontalScrollView) rootView.findViewById(R.id.horizontalScrollView);
		mTabHost.setCurrentTab(INDEX_NUM);
		final int width = ScreenUtil.getScreenWidth(getActivity()) - mTabWidth >> 1;
		mHorizontalScrollView.post(new Runnable() {
			@Override
			public void run() {
				mHorizontalScrollView.scrollTo(INDEX_NUM * mTabWidth - width, 0);
			}
		});
	}

	private void setTab(int index) {
		mTabHost.setCurrentTab(index);
		final int width = ScreenUtil.getScreenWidth(getActivity()) - mTabWidth >> 1;
		mHorizontalScrollView.scrollTo(index * mTabWidth - width, 0);
	}

	private void initBean() {
		for (int i = 0; i < GROUP_NUM; i++) {
			boolean isPass = i < INDEX_NUM;
			boolean isNotYet = i > INDEX_NUM;
			for (int j = 0; j < mBooks[i].length; j++) {
				mBooks[i][j] = new Bean(isPass, isNotYet, j == mBooks[i].length - 1 && i != GROUP_NUM - 1);
			}
		}
	}

	private int getIndex(int size) {
		int index = 0;
		for (int i = 0; i < size; i++) {
			index += mBooks[i].length;
		}
		return index;
	}

	private void initHListview(View rootView) {
		mHorizontalListView = (HorizontalListView) rootView.findViewById(R.id.horizontallistview);

		View convertView = getActivity().getLayoutInflater().inflate(R.layout.item_group_soon_book, null);
		mHorizontalListView.getLayoutParams().height = mHorizontalListView.getPaddingBottom()
				+ mHorizontalListView.getPaddingTop() + ViewUtility.getViewHeight(convertView);

		mMyHArrayAdapter = new MyHArrayAdapter(getActivity(), R.layout.item_group_soon_book);
		mHorizontalListView.setAdapter(mMyHArrayAdapter);
		mHorizontalListView.setOnScrollChangedListener(new OnScrollChangedListener() {

			@Override
			public void onScrollChanged() {
				mIsManOprate = false;
				if (mTabHost != null) {
					int tabIndex = getTabIndex();
					if (tabIndex != mTabCurrentIndex) {
						setTab(tabIndex);
						tabIndex = mTabCurrentIndex;
					}
				}
			}
		});

		mHorizontalListView.setOnScrollStateChangedListener(new OnScrollStateChangedListener() {

			@Override
			public void onScrollStateChanged(ScrollState scrollState) {
				if (scrollState == ScrollState.SCROLL_STATE_IDLE) {
					mIsManOprate = true;
				}
			}
		});
	}

	private class Bean {
		public Bean(boolean isPass, boolean isNotYet, boolean isDivider) {
			mIsPass = isPass;
			mIsNotYet = isNotYet;
			mIsDivider = isDivider;
		}

		private boolean mIsPass;
		private boolean mIsNotYet;
		private boolean mIsDivider;
	}

	private class MyHArrayAdapter extends ArrayAdapter<Bean> {

		public MyHArrayAdapter(Context context, int resource) {
			super(context, resource);
		}

		@Override
		public int getCount() {
			return mSize;
		}

		@Override
		public Bean getItem(int position) {
			return getBean(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHHolder viewHHolder;
			if (convertView == null || convertView.getTag() == null) {
				viewHHolder = new ViewHHolder();
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_group_soon_book, null);
				viewHHolder.rootView = convertView.findViewById(R.id.rootView);
				viewHHolder.dividerView = convertView.findViewById(R.id.divider);
				viewHHolder.nameTView = (TextView) convertView.findViewById(R.id.tv_name);

			} else {
				viewHHolder = (ViewHHolder) convertView.getTag();
			}

			Bean bean = getItem(position);

			if (bean.mIsPass) {
				viewHHolder.rootView.setBackgroundColor(Color.parseColor("#9F9B9A"));
			} else if (bean.mIsNotYet) {
				viewHHolder.rootView.setBackgroundColor(Color.parseColor("#B5B4B2"));
			} else {
				viewHHolder.rootView.setBackgroundColor(Color.parseColor("#FFFFFF"));
			}

			if (bean.mIsDivider) {
				viewHHolder.dividerView.setVisibility(View.VISIBLE);
			} else {
				viewHHolder.dividerView.setVisibility(View.GONE);
			}

			/*
			 * viewHHolder.nameTView.setText((position / 5) + ":" + (position %
			 * 5 + 1));
			 */

			return convertView;
		}

	}

	private Bean getBean(int index) {
		for (int i = 0; i < mBooks.length; i++) {
			if (index < mBooks[i].length) {
				return mBooks[i][index];
			} else {
				index -= mBooks[i].length;
			}
		}
		return null;
	}

	private int getTabIndex() {
		int position = mHorizontalListView.getLastVisiblePosition();
		for (int i = 0; i < mBooks.length; i++) {
			if (position < mBooks[i].length) {
				return i;
			} else {
				position -= mBooks[i].length;
			}
		}
		return 0;
	}

	private static class ViewHHolder {
		View rootView;

		View dividerView;

		TextView nameTView;

	}

	private class BookBean {

	}

	private class MyPullListTask extends PullListTask<BookBean> {

		public MyPullListTask(View rooView) {
			super(getActivity(), rooView, PullToRefreshBase.Mode.PULL_FROM_START, false, false);
		}

		@Override
		public View getPullListView(BookBean t, int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_group_buy_list, null);
			}
			return convertView;
		}
	}

}
