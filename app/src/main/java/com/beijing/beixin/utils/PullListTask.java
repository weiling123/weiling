package com.beijing.beixin.utils;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import com.beijing.beixin.R;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshListView;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public abstract class PullListTask<T> implements PullToRefreshBase.OnRefreshListener2<ListView> {

	private Date mTime;
	public ArrayAdapter mMyArrayAdapter;
	private Handler mHander = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				mPullList.onRefreshComplete();
				mTime = new Date();
				break;
			case 1:
				break;
			}
		}
	};
	public PullToRefreshListView mPullList;
	public ArrayList<T> mArrayList;
	private Activity mActivity;
	public int mPageIndex = 1;
	private View mLoadingView;
	private View mLoadFaileView;
	/**
	 * 是否是反转 反转下面刷新，上面加载
	 */
	private boolean mIsReverse;
	private boolean mIsCookie;
	private MyRequestCallBack mMyRequestCallBack;

	public PullListTask(Activity activity, PullToRefreshBase.Mode mode, boolean isNet, boolean isisReverse,
			boolean isCookie) {
		mIsReverse = isisReverse;
		mActivity = activity;
		mPullList = (PullToRefreshListView) activity.findViewById(R.id.pullList);
		mIsCookie = isCookie;
		init(mode, isNet);
	}

	public PullListTask(Activity activity, PullToRefreshBase.Mode mode, boolean isNet, boolean isCookie) {
		this(activity, mode, isNet, false, isCookie);
	}

	public PullListTask(Activity activity, View view, PullToRefreshBase.Mode mode, boolean isNet, boolean isCookie) {
		mActivity = activity;
		mPullList = (PullToRefreshListView) view.findViewById(R.id.pullList);
		mIsCookie = isCookie;
		init(mode, isNet);
	}

	public void refresh() {
		mMyArrayAdapter.notifyDataSetChanged();
	}

	private void init(PullToRefreshBase.Mode mode, boolean isNet) {
		if (Integer.parseInt(Build.VERSION.SDK) >= 9) {
			mPullList.getRefreshableView().setOverScrollMode(View.OVER_SCROLL_NEVER);
		}
		if (isNet) {
			mPullList.setOnRefreshListener(this);
		}
		mPullList.setMode(mode);
	}

	public void setArraList(T[] arraList, int id) {
		mArrayList = new ArrayList<T>();
		for (T t : arraList) {
			mArrayList.add(t);
		}
		mMyArrayAdapter = new MyArrayAdapter(id);
		mPullList.setAdapter(mMyArrayAdapter);
	}

	public void addItem(T t) {
		if (t == null || mArrayList == null) {
			return;
		}
		mArrayList.add(0, t);
		mMyArrayAdapter.notifyDataSetChanged();
		mPullList.getRefreshableView().setSelection(0);
	}

	public void addItemLast(T t) {
		if (t == null || mArrayList == null) {
			return;
		}
		mArrayList.add(t);
		mMyArrayAdapter.notifyDataSetChanged();
		mPullList.getRefreshableView().setSelection(mArrayList.size() - 1);
	}

	public void setArraList(T[] arraList, int id, View loadingView, View loadFaileView) {
		setArraList(arraList, id);
		mLoadingView = loadingView;
		mLoadFaileView = loadFaileView;
		if (loadingView != null) {
			loadingView.setVisibility(View.VISIBLE);
		}
		if (mLoadFaileView != null) {
			mLoadFaileView.setVisibility(View.GONE);
		}
		mMyRequestCallBack = new MyRequestCallBack();
		T[] arrayList = getDatebaseContent();
		updateDatabaseList(arrayList);
	}

	private void updateDatabaseList(T[] arrayList) {
		if (arrayList != null && arrayList.length > 0) {
			resetArrayList(arrayList);
			mMyArrayAdapter.notifyDataSetChanged();
			if (mLoadFaileView != null && mLoadFaileView.getVisibility() != View.GONE) {
				mLoadFaileView.setVisibility(View.GONE);
			}
		}
		startNet();
	}

	private void resetArrayList(T[] arrayList) {
		mArrayList.clear();
		addArrayList(arrayList);
	}

	private void addArrayList(T[] arrayList) {
		for (T t : arrayList) {
			if (mIsReverse) {
				mArrayList.add(0, t);
			} else {
				mArrayList.add(t);
			}
		}
	}

	public void startNet() {
		BaseTask task = new BaseTask(mActivity);
		RequestParams params = getRequestParams();
		params.addBodyParameter("pageNumber", "" + mPageIndex);
		String url = getPageUrl();
		if (mIsCookie) {
			task.askCookieRequest(url, params, mMyRequestCallBack);
		} else {
			task.askNormalRequest(url, params, mMyRequestCallBack);
		}
	}

	private class MyArrayAdapter extends ArrayAdapter {

		public MyArrayAdapter(int resource) {
			super(mActivity, resource, mArrayList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getPullListView(getItem(position), position, convertView, parent);
		}

		@Override
		public int getCount() {
			return getArrayAdapterCount();
		}

		@Override
		public T getItem(int position) {
			return getArrayAdapterItem(position);
		}
	}

	public T getArrayAdapterItem(int position) {
		return mArrayList.get(position);
	}

	public int getArrayAdapterCount() {
		if (mArrayList == null) {
			return 0;
		}
		return mArrayList.size();
	}

	public abstract View getPullListView(T t, int position, View convertView, ViewGroup parent);

	@Override
	public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
		if (!mIsReverse) {
			mPageIndex = 1;
		}
		pullToToRefresh(refreshView);
	}

	/**
	 * onPullUpToRefresh will be called only when the user has Pulled from the
	 * end, and released.
	 */
	public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView) {
		if (mIsReverse) {
			mPageIndex = 1;
		}
		pullToToRefresh(refreshView);
	}

	private void pullToToRefresh(PullToRefreshBase<ListView> refreshView) {
		startNet();
		String label = mTime == null ? ""
				: DateUtils.formatDateTime(mActivity, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
	}

	public class MyRequestCallBack extends RequestCallBack<String> {

		@Override
		public void onSuccess(ResponseInfo<String> arg0) {
			if (arg0 == null) {
				onFaulure();
				return;
			}
			T[] arryList = getList(arg0.result);
			if (arryList == null) {
				onFaulure();
			} else {
				onSuccess(arryList);
			}
		}

		@Override
		public void onFailure(HttpException arg0, String arg1) {
			onFaulure();
		}

		private void onSuccess(T[] arryList) {
			mTime = new Date();
			initDate(arryList);
			mHander.sendEmptyMessage(1);
			mPageIndex++;
			onRefresh();
			mPullList.onRefreshComplete();
			if (arryList.length < 10) {
				setModeStart();
			} else {
				mPullList.setMode(PullToRefreshBase.Mode.BOTH);
			}
			if (mIsReverse) {
				mPullList.getRefreshableView().setSelection(arryList.length - 1);
			}
			hideNetView();
		}

		private void onFaulure() {
			if (mPageIndex == 1 && mLoadFaileView != null && (mArrayList == null || mArrayList.size() == 0)) {
				mLoadFaileView.setVisibility(View.VISIBLE);
				mLoadingView.setVisibility(View.GONE);
				return;
			}
			mPullList.onRefreshComplete();
			if (mPageIndex == 1) {
				setModeStart();
			}
			hideNetView();
		}

		public void initDate(T[] arryList) {
			if (mPageIndex == 1) {
				resetArrayList(arryList);
			} else {
				addArrayList(arryList);
			}
		}
	}

	private void setModeStart() {
		if (mIsReverse) {
			mPullList.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
		} else {
			mPullList.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
		}
	}

	private void hideNetView() {
		if (mLoadingView != null && mLoadingView.getVisibility() != View.GONE) {
			mLoadingView.setVisibility(View.GONE);
		}
		if (mLoadFaileView != null && mLoadFaileView.getVisibility() != View.GONE) {
			mLoadFaileView.setVisibility(View.GONE);
		}
	}

	/***************************************************
	 * 一般需要在子类中实现的方法
	 ***************************************************/
	public T[] getDatebaseContent() {
		return null;
	}

	public String getPageUrl() {
		return null;
	}

	public RequestParams getRequestParams() {
		return new RequestParams();
	}

	public T[] getList(String response) {
		return null;
	}

	public void onRefresh() {
		mMyArrayAdapter.notifyDataSetChanged();
	}
}