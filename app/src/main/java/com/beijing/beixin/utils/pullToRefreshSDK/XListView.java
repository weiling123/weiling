package com.beijing.beixin.utils.pullToRefreshSDK;

import com.beijing.beixin.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * 类名称:XListView 类描述:继承ListView的自定义XListView 作者:张静 最后更新时间:2015年12月4日 上午9:13:02
 * 版本:v1.0
 */
public class XListView extends ListView implements OnScrollListener {

	private float mLastY = -1;
	private Scroller mScroller;
	private OnScrollListener mScrollListener;// 滚动监听
	private IXListViewListener mListViewListener;// 接口，处理下拉刷新和上拉加载
	/** header View */
	private XListViewHeader mHeaderView;
	private RelativeLayout mHeaderViewContent;// header view的内容布局
	private TextView mHeaderTimeView;// header view 显示时间
	private int mHeaderViewHeight; // header view的高度
	private boolean mEnablePullRefresh = true;// 是否可以下拉刷新
	private boolean mPullRefreshing = false; // 是否正在刷新 is refreashing.
	/** footer View */
	private XListViewFooter mFooterView;
	private boolean mEnablePullLoad;// 是否可以上拉加载
	private boolean mPullLoading;// 是否正在加载(查看更多)
	private boolean mIsFooterReady = false;// footer是否准备
	// 总共的列表项目，用于检测listview的底部
	private int mTotalItemCount;// 总共列表项目数量
	/** 用来回滚 */
	private int mScrollBack;
	private final static int SCROLLBACK_HEADER = 0;
	private final static int SCROLLBACK_FOOTER = 1;
	private final static int SCROLL_DURATION = 400; // scroll back duration
	private final static int PULL_LOAD_MORE_DELTA = 50; // 下拉距离>=50px,触发"查看更多"
	private final static float OFFSET_RADIO = 1.8f;

	/**
	 * @param context
	 */
	public XListView(Context context) {
		super(context);
		initWithContext(context);
	}

	public XListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWithContext(context);
	}

	public XListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWithContext(context);
	}

	/**
	 * 初始化操作
	 * 
	 * @param context
	 *            (上下文)
	 * @author 张静
	 * @Time 2015年12月4日上午9:27:23
	 */
	private void initWithContext(Context context) {
		mScroller = new Scroller(context, new DecelerateInterpolator());
		super.setOnScrollListener(this);// 添加滚动监听
		// init header view
		mHeaderView = new XListViewHeader(context);
		mHeaderViewContent = (RelativeLayout) mHeaderView
				.findViewById(com.beijing.beixin.R.id.xlistview_header_content);
		mHeaderTimeView = (TextView) mHeaderView.findViewById(R.id.xlistview_header_time);
		addHeaderView(mHeaderView);// 在listview头部添加组件
		// init footer view
		mFooterView = new XListViewFooter(context);
		// 初始化header的高度
		mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				mHeaderViewHeight = mHeaderViewContent.getHeight();
				getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
	}

	/**
	 * 设置适配器
	 */
	@Override
	public void setAdapter(ListAdapter adapter) {
		// 确保footer最后添加并且只添加一次
		if (mIsFooterReady == false) {
			mIsFooterReady = true;
			addFooterView(mFooterView);
		}
		super.setAdapter(adapter);
	}

	/**
	 * 设置下拉刷新是否操作
	 * 
	 * @param enable
	 *            (区分标记)
	 * @author 张静
	 * @Time 2015年12月4日上午10:45:46
	 */
	public void setPullRefreshEnable(boolean enable) {
		mEnablePullRefresh = enable;
		if (!mEnablePullRefresh) { // disable, hide the content
			mHeaderViewContent.setVisibility(View.INVISIBLE);
		} else {
			mHeaderViewContent.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 设置上拉加载"查看更多"是否操作
	 * 
	 * @param enable
	 *            (区分标记)
	 * @author 张静
	 * @Time 2015年12月4日上午10:46:38
	 */
	public void setPullLoadEnable(boolean enable) {
		mEnablePullLoad = enable;
		if (!mEnablePullLoad) {// 如果EnablePullload
			mFooterView.hide();// footerView隐藏
			mFooterView.setOnClickListener(null);
		} else {
			mPullLoading = false;
			mFooterView.show();// 显示底部footer
			mFooterView.setState(XListViewFooter.STATE_NORMAL);
			mFooterView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					startLoadMore();
				}
			});
		}
	}

	/**
	 * 停止刷新，重置header
	 * 
	 * @author 张静
	 * @Time 2015年12月4日上午10:48:06
	 */
	public void stopRefresh() {
		if (mPullRefreshing == true) {
			mPullRefreshing = false;
			resetHeaderHeight();
		}
	}

	/**
	 * 停止上拉加载，重置footer
	 * 
	 * @author 张静
	 * @Time 2015年12月4日上午10:49:22
	 */
	public void stopLoadMore() {
		if (mPullLoading == true) {
			mPullLoading = false;
			mFooterView.setState(XListViewFooter.STATE_NORMAL);
		}
	}

	/**
	 * 设置上次刷新的时间
	 * 
	 * @param time
	 *            (字符串时间)
	 * @author 张静
	 * @Time 2015年12月4日上午10:49:50
	 */
	public void setRefreshTime(String time) {
		mHeaderTimeView.setText(time);
	}

	private void invokeOnScrolling() {
		if (mScrollListener instanceof OnXScrollListener) {
			OnXScrollListener l = (OnXScrollListener) mScrollListener;
			l.onXScrolling(this);
		}
	}

	/**
	 * 更新header的高度
	 * 
	 * @param delta
	 *            (高度变量)
	 * @author 张静
	 * @Time 2015年12月4日上午10:52:14
	 */
	private void updateHeaderHeight(float delta) {
		mHeaderView.setVisiableHeight((int) delta + mHeaderView.getVisiableHeight());
		if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
			if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
				mHeaderView.setState(XListViewHeader.STATE_READY);
			} else {
				mHeaderView.setState(XListViewHeader.STATE_NORMAL);
			}
		}
		setSelection(0); // 滚到最顶部
	}

	/**
	 * 重置header的高度
	 * 
	 * @author 张静
	 * @Time 2015年12月4日上午10:56:12
	 */
	private void resetHeaderHeight() {
		int height = mHeaderView.getVisiableHeight();
		if (height == 0) // 不显示
			return;
		// 正在刷新并且header没有完全显示时，不做操作
		if (mPullRefreshing && height <= mHeaderViewHeight) {
			return;
		}
		// 默认会回滚到header的位置
		int finalHeight = 0;
		// 正在刷新就滚回显示整个header部分
		if (mPullRefreshing && height > mHeaderViewHeight) {
			finalHeight = mHeaderViewHeight;
		}
		mScrollBack = SCROLLBACK_HEADER;
		// 回滚到指定位置
		mScroller.startScroll(0, height, 0, finalHeight - height, SCROLL_DURATION);
		// 触发computeScroll
		invalidate();
	}

	/**
	 * 更新footer的高度
	 * 
	 * @param delta
	 *            (高度变量)
	 * @author 张静
	 * @Time 2015年12月4日上午10:57:50
	 */
	private void updateFooterHeight(float delta) {
		int height = mFooterView.getBottomMargin() + (int) delta;
		if (mEnablePullLoad && !mPullLoading) {
			if (height > PULL_LOAD_MORE_DELTA) {
				mFooterView.setState(XListViewFooter.STATE_READY);
			} else {
				mFooterView.setState(XListViewFooter.STATE_NORMAL);
			}
		}
		mFooterView.setBottomMargin(height);
	}

	/**
	 * 重置footer的高度
	 * 
	 * @author 张静
	 * @Time 2015年12月4日上午10:58:31
	 */
	private void resetFooterHeight() {
		int bottomMargin = mFooterView.getBottomMargin();
		if (bottomMargin > 0) {
			mScrollBack = SCROLLBACK_FOOTER;
			mScroller.startScroll(0, bottomMargin, 0, -bottomMargin, SCROLL_DURATION);
			invalidate();
		}
	}

	/**
	 * 开始加载
	 * 
	 * @author 张静
	 * @Time 2015年12月4日上午10:59:26
	 */
	private void startLoadMore() {
		mPullLoading = true;
		mFooterView.setState(XListViewFooter.STATE_LOADING);
		if (mListViewListener != null) {
			mListViewListener.onLoadMore();
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mLastY == -1) {
			mLastY = ev.getRawY();
		}
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastY = ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float deltaY = ev.getRawY() - mLastY;
			mLastY = ev.getRawY();
			if (getFirstVisiblePosition() == 0 && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
				// 第一个item可见并且头部部分显示或者下拉操作,则表示处于下拉刷新状态
				updateHeaderHeight(deltaY / OFFSET_RADIO);
				invokeOnScrolling();
			} else if (getLastVisiblePosition() == mTotalItemCount - 1
					&& (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
				// 最后一个item,已经拉过或者正要拉
				updateFooterHeight(-deltaY / OFFSET_RADIO);
			}
			break;
		default:
			mLastY = -1;
			if (getFirstVisiblePosition() == 0) {
				// 刷新
				if (mEnablePullRefresh && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
					mPullRefreshing = true;
					mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
					if (mListViewListener != null) {
						mListViewListener.onRefresh();
					}
				}
				resetHeaderHeight();
			} else if (getLastVisiblePosition() == mTotalItemCount - 1) {
				// 加载更多
				if (mEnablePullLoad && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA && !mPullLoading) {
					startLoadMore();
				}
				resetFooterHeight();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			if (mScrollBack == SCROLLBACK_HEADER) {
				mHeaderView.setVisiableHeight(mScroller.getCurrY());
			} else {
				mFooterView.setBottomMargin(mScroller.getCurrY());
			}
			postInvalidate();
			invokeOnScrolling();
		}
		super.computeScroll();
	}

	@Override
	public void setOnScrollListener(OnScrollListener l) {
		mScrollListener = l;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (mScrollListener != null) {
			mScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// send to user's listener
		mTotalItemCount = totalItemCount;
		if (mScrollListener != null) {
			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}

	/**
	 * 设置XListView的监听
	 * 
	 * @param l
	 * @author 张静
	 * @Time 2015年12月4日上午11:03:13
	 */
	public void setXListViewListener(IXListViewListener l) {
		mListViewListener = l;
	}

	/**
	 * OnXScrollListener 接口，待实现onXScrolling方法 作者:张静 最后更新时间:2015年12月4日 上午11:04:07
	 * 版本:v1.0
	 */
	public interface OnXScrollListener extends OnScrollListener {

		public void onXScrolling(View view);
	}

	/**
	 * IXListViewListener 接口，待实现，刷新方法和加载更多的方法 作者:张静 最后更新时间:2015年12月4日 上午11:05:38
	 * 版本:v1.0
	 */
	public interface IXListViewListener {

		public void onRefresh();// 刷新方法

		public void onLoadMore();// 加载方法
	}
}
