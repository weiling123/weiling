package com.beijing.beixin.ui.shoppingcart;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.beijing.beixin.R;
import com.beijing.beixin.ui.PageTabActivity;
import com.beijing.beixin.ui.shoppingcart.comment.CommentFragment;
import com.beijing.beixin.ui.shoppingcart.detail.AfterSaleServiceFragment;
import com.beijing.beixin.utils.MResource;

public class GoodCommentsActivity extends PageTabActivity {

	private View[] mTabsView;

	private TextView[][] mTabsTView;

	private Resources mResources;

	private static final int TAB_NUM = 5;

	public String mProductId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_good_comment);

		setNavigationTitle("商品评价");
		setNavigationLeftBtnImage(R.drawable.title_bar_back);

		String[] keys = new String[] { "commentTotalCount", "goodCommentTotalCount", "normalCommentTotalCount",
				"badCommentTotalCount" };
		Intent intent = getIntent();
		mProductId = intent.getStringExtra("ProductId");

		mResources = getResources();

		mTabsView = new View[TAB_NUM];
		mTabsTView = new TextView[TAB_NUM][2];
		for (int i = 0; i < mTabsView.length; i++) {
			mTabsView[i] = findViewById(MResource.getIdByName(this, "tv_tab_" + i));
			mTabsTView[i][0] = (TextView) findViewById(MResource.getIdByName(this, "tv_tab_name" + i));
			mTabsTView[i][1] = (TextView) findViewById(MResource.getIdByName(this, "tv_tab_num" + i));
			if (i < keys.length) {
				String num = intent.getStringExtra(keys[i]);
				mTabsTView[i][1].setText(num);
			}
		}

		init(mTabsView, R.id.cursor);
	}

	/**
	 * 点击返回导航的操作
	 */
	public void onBack(View view) {
		onBackPressed();
	}

	@Override
	public Fragment[] getFragment() {
		Fragment[] fragments = new Fragment[TAB_NUM];
		fragments[0] = CommentFragment.instance(null);
		fragments[1] = CommentFragment.instance("good");
		fragments[2] = CommentFragment.instance("normal");
		fragments[3] = CommentFragment.instance("bad");
		fragments[4] = new AfterSaleServiceFragment();
		return fragments;
	}

	@Override
	public int getTabNum() {
		return TAB_NUM;
	}

	public void initPageSelected(int currIndex, int nextIndex) {
		mTabsTView[currIndex][0].setTextColor(mResources.getColor(R.color.tv_gray_prodetail));
		mTabsTView[currIndex][1].setTextColor(mResources.getColor(R.color.tv_gray_prodetail));
		mTabsTView[nextIndex][0].setTextColor(mResources.getColor(R.color.tv_tips_prodetail));
		mTabsTView[nextIndex][1].setTextColor(mResources.getColor(R.color.tv_tips_prodetail));
	}

	private void setNum(TextView textView, int num) {

	}

}
