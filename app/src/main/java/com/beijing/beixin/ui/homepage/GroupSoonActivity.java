package com.beijing.beixin.ui.homepage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.beijing.beixin.R;
import com.beijing.beixin.ui.PageTabActivity;
import com.beijing.beixin.ui.homepage.groupby.GroupSoonFragment;
import com.beijing.beixin.utils.MResource;

public class GroupSoonActivity extends PageTabActivity {

	private static final int TAB_NUM = 4;

	private TextView mTabsTView[] = new TextView[TAB_NUM];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_soon);
		setNavigationTitle("即将开团");
		setNavigationLeftBtnImage(R.drawable.title_bar_back);

		for (int i = 0; i < TAB_NUM; i++) {
			mTabsTView[i] = (TextView) findViewById(MResource.getIdByName(this, "tv_tab_" + i));
		}

		init(mTabsTView, -1);
	}

	@Override
	public Fragment[] getFragment() {
		Fragment[] fragments = new Fragment[TAB_NUM];
		fragments[0] = GroupSoonFragment.instance(null);
		fragments[1] = GroupSoonFragment.instance("normal");
		fragments[2] = GroupSoonFragment.instance("good ");
		fragments[3] = GroupSoonFragment.instance("bad");
		return fragments;
	}

	@Override
	public int getTabNum() {
		return TAB_NUM;
	}

	@Override
	public void initPageSelected(int currIndex, int nextIndex) {
		mTabsTView[currIndex].setTextColor(getResources().getColor(R.color.tv_gray_prodetail));
		mTabsTView[nextIndex].setTextColor(getResources().getColor(R.color.tv_tips_prodetail));
	}

}
