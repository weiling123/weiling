package com.beijing.beixin.ui.activity.yindaoye;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

/**
 * vatty *
 * 
 * 
 * 
 */
public class MainFragment_Adapter extends FragmentPagerAdapter {
	private List<Fragment> fragmentsList;
	FragmentManager fm;

	public MainFragment_Adapter(FragmentManager fm) {
		super(fm);

	}

	public MainFragment_Adapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragmentsList = fragments;
		this.fm = fm;
	}

	@Override
	public int getCount() {
		return fragmentsList.size();
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragmentsList.get(arg0);
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
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
		fragment = fragmentsList.get(position);
		// 添加新fragment时必须用前面获得的tag，这点很重要
		ft.add(container.getId(), fragment, fragmentTag);
		ft.attach(fragment);
		ft.commit();
		return fragment;
	}

}
