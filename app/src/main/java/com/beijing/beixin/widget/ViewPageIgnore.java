package com.beijing.beixin.widget;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ViewPageIgnore extends ViewPager{
	
	private boolean mIsIgnore;
	
	public ViewPageIgnore(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ViewPageIgnore(Context context) {
		super(context);
	}
	
	public void setIsIgnore(boolean isgnore){
		mIsIgnore = isgnore;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Detect when the user lifts their finger off the screen after a touch
		if (getParent() != null && mIsIgnore) {
			getParent().requestDisallowInterceptTouchEvent(true);
		}

		return super.onTouchEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (getParent() != null && mIsIgnore) {
			getParent().requestDisallowInterceptTouchEvent(true);
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (getParent() != null && mIsIgnore) {
			getParent().requestDisallowInterceptTouchEvent(true);
		}
		return super.onInterceptTouchEvent(arg0);
	}
	
}
