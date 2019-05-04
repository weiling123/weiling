package com.beijing.beixin.ui.homepage.groupby;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.beijing.beixin.R;
import com.beijing.beixin.ui.base.BaseFragment;
import com.beijing.beixin.ui.homepage.GroupSoonActivity;
import com.beijing.beixin.utils.LogUtil;
import com.beijing.beixin.utils.PullListTask;
import com.beijing.beixin.utils.ViewUtil;
import com.beijing.beixin.utils.ViewUtility;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase;

import com.beijing.beixin.widget.HorizontalListView;

public class GroupBuyFragment extends BaseFragment {

	private String mType;

	private Bean[] mBooks = new Bean[24];

	private MyHArrayAdapter mMyHArrayAdapter;

	private HorizontalListView mHorizontalListView;

	private MyPullListTask mMyPullListTask;

	public static GroupBuyFragment instance(String type) {
		GroupBuyFragment groupFragment = new GroupBuyFragment();
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
		View rootView = inflater.inflate(R.layout.fragment_group_buy, container, false);

		(rootView.findViewById(R.id.group_soon_layout)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), GroupSoonActivity.class);
				getActivity().startActivity(intent);

			}
		});

		initBean();
		initHListview(rootView);
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

	private void initBean() {
		for (int i = 0; i < mBooks.length; i++) {
			mBooks[i] = new Bean();
		}
	}

	private void initHListview(View rootView) {
		mHorizontalListView = (HorizontalListView) rootView.findViewById(R.id.horizontallistview);

		View convertView = getActivity().getLayoutInflater().inflate(R.layout.item_group_buy_book, null);
		mHorizontalListView.getLayoutParams().height = mHorizontalListView.getPaddingBottom()
				+ mHorizontalListView.getPaddingTop() + ViewUtility.getViewHeight(convertView);

		mMyHArrayAdapter = new MyHArrayAdapter(getActivity(), R.layout.item_group_buy_book);
		mHorizontalListView.setAdapter(mMyHArrayAdapter);
	}

	private class Bean {
	}

	private class MyHArrayAdapter extends ArrayAdapter<Bean> {

		public MyHArrayAdapter(Context context, int resource) {
			super(context, resource);
		}

		@Override
		public int getCount() {
			return mBooks.length;
		}

		@Override
		public Bean getItem(int position) {
			return mBooks[position];
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHHolder viewHHolder;
			if (convertView == null || convertView.getTag() == null) {
				viewHHolder = new ViewHHolder();
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_group_buy_book, null);

			} else {
				viewHHolder = (ViewHHolder) convertView.getTag();
			}

			return convertView;
		}

	}

	private static class ViewHHolder {

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
