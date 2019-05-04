package com.beijing.beixin.ui.shoppingcart.bookdetail;

import com.beijing.beixin.R;
import com.beijing.beixin.ui.PageTabFragment;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity.DetailInterface;
import com.beijing.beixin.ui.shoppingcart.comment.CommentFragment;
import com.beijing.beixin.utils.MResource;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CommentsFragement extends PageTabFragment implements DetailInterface {

	private static final int TAB_NUM = 4;

	private BookDetailActivity mBookDetailActivity;

	private View[] mTabsView;

	private TextView[][] mTabsTView;

	private View mRootView;

	private Resources mResources;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBookDetailActivity = (BookDetailActivity) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_book_detail_comments, container, false);
		return mRootView;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser && mFragments == null) {
			updateUI();
		}
	}

	public void updateUI() {
		mResources = getResources();

		int[] nums = new int[] { mBookDetailActivity.mProductDetail.getCommentTotalCount(),
				mBookDetailActivity.mProductDetail.getGoodCommentTotalCount(),
				mBookDetailActivity.mProductDetail.getNormalCommentTotalCount(),
				mBookDetailActivity.mProductDetail.getBadCommentTotalCount(), };

		mTabsView = new View[TAB_NUM];
		mTabsTView = new TextView[TAB_NUM][2];
		for (int i = 0; i < mTabsView.length; i++) {
			mTabsView[i] = mRootView.findViewById(MResource.getIdByName(getActivity(), "tv_tab_" + i));
			mTabsTView[i][0] = (TextView) mRootView
					.findViewById(MResource.getIdByName(getActivity(), "tv_tab_name" + i));
			mTabsTView[i][1] = (TextView) mRootView
					.findViewById(MResource.getIdByName(getActivity(), "tv_tab_num" + i));
			if (i < nums.length) {
				mTabsTView[i][1].setText(nums[i] + "");
			}
		}

		init(mRootView, mTabsView, -1);
	}

	@Override
	public Fragment[] getFragment() {
		Fragment[] fragments = new Fragment[TAB_NUM];
		fragments[0] = CommentFragment.instance(null);
		fragments[1] = CommentFragment.instance("good");
		fragments[2] = CommentFragment.instance("normal");
		fragments[3] = CommentFragment.instance("bad");
		return fragments;
	}

	@Override
	public int getTabNum() {
		return TAB_NUM;
	}

	public void initPageSelected(int currIndex, int nextIndex) {
		mTabsTView[currIndex][0].setTextColor(mResources.getColor(R.color.tv_gray_prodetail_top_comment));
		mTabsTView[currIndex][1].setTextColor(mResources.getColor(R.color.tv_gray_prodetail_top_comment));
		mTabsTView[nextIndex][0].setTextColor(mResources.getColor(R.color.tv_tips_prodetail_top));
		mTabsTView[nextIndex][1].setTextColor(mResources.getColor(R.color.tv_tips_prodetail_top));
	}

	@Override
	public int getProductNum() {
		return 1;
	}

}
