package com.beijing.beixin.ui.shoppingcart.bookdetail;

import com.beijing.beixin.R;
import com.beijing.beixin.ui.PageTabFragment;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity.DetailInterface;
import com.beijing.beixin.ui.shoppingcart.detail.BookDetailFragment;
import com.beijing.beixin.ui.shoppingcart.detail.PackingListFragment;
import com.beijing.beixin.widget.ScrollViewContainer.BottomViewListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MoreInfoFragment extends PageTabFragment implements DetailInterface, BottomViewListener {

	private BookDetailActivity mBookDetailActivity;

	private TextView[] mTabsTView;

	private View mRootView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBookDetailActivity = (BookDetailActivity) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_book_detail_more_info, container, false);
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
		View linearLayout_title = mRootView.findViewById(R.id.linearLayout_title);

		String attrGroupNm = mBookDetailActivity.mProductDetail.getStandardAttrGroup();

		mTabsTView = new TextView[2];
		mTabsTView[0] = (TextView) mRootView.findViewById(R.id.tv_notab_0);
		mTabsTView[1] = (TextView) mRootView.findViewById(R.id.tv_notab_1);

		if ("N".equals(attrGroupNm)) {
			linearLayout_title.setVisibility(View.GONE);
		} else {
			linearLayout_title.setVisibility(View.VISIBLE);

		}

		init(mRootView, mTabsTView, -1);
	}

	@Override
	public int getProductNum() {
		return 1;
	}

	@Override
	public Fragment[] getFragment() {
		String attrGroupNm = mBookDetailActivity.mProductDetail.getStandardAttrGroup();
		if ("N".equals(attrGroupNm)) {
			Fragment[] fragments = new Fragment[1];
			fragments[0] = new BookDetailFragment();
			return fragments;

		} else {
			Fragment[] fragments = new Fragment[2];
			fragments[0] = new BookDetailFragment();
			fragments[1] = new PackingListFragment();
			return fragments;
		}
	}

	@Override
	public int getTabNum() {
		String attrGroupNm = mBookDetailActivity.mProductDetail.getStandardAttrGroup();
		return "N".equals(attrGroupNm) ? 1 : 2;
	}

	public void initPageSelected(int currIndex, int nextIndex) {
		mTabsTView[currIndex].setTextColor(getResources().getColor(R.color.tv_gray_prodetail_top));
		mTabsTView[nextIndex].setTextColor(getResources().getColor(R.color.tv_tips_prodetail_top));
	}

	@Override
	public boolean isCanSocroll() {
		if (mFragments == null) {
			return false;
		}
		if (mBookDetailActivity.mMoreInfoFragment.mCurrIndex == 0) {
			if (mFragments[0] == null) {
				return false;
			}
			BookDetailFragment bookDetailFragment = (BookDetailFragment) mFragments[0];
			return bookDetailFragment.isListViewReachTopEdge();
		} else {
			if (mFragments[1] == null) {
				return false;
			}
			PackingListFragment packingListFragment = (PackingListFragment) mFragments[1];
			return packingListFragment.isListViewReachTopEdge();
		}
	}

}
