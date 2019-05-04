package com.beijing.beixin.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class ExpandViewPage extends ViewPager {

	public ExpandViewPage(Context context) {
		super(context);
	}

	public ExpandViewPage(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
