package com.beijing.beixin.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class BottomListenerScrollView extends ScrollView {

	private UpSlideListener mUpSlideListener;

	private boolean mIsBottom;

	private boolean mUpSlide;

	private int mX;

	private int mY;

	private boolean mIsUpdate;

	private int mLastBottom;

	public BottomListenerScrollView(Context context) {
		super(context);

	}

	public BottomListenerScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public BottomListenerScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);

		if (mLastBottom == 0) {
			View view = (View) getChildAt(getChildCount() - 1);
			mLastBottom = view.getBottom();
		}

		if (mLastBottom == (getHeight() + getScrollY())) {
			if (!mIsBottom) {
				mIsBottom = true;
			}
		} else if (mIsBottom) {
			mIsBottom = false;
			mUpSlide = false;
		}

	}

	public void setIsUpdate(boolean isUpdate) {
		isUpdate = false;
	}

	public void setUpSlideLister(UpSlideListener upSlideLister) {
		mUpSlideListener = upSlideLister;
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (mIsBottom && !mUpSlide) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mUpSlide = true;
				break;
			}
		}

		if (mUpSlide) {
			return onMyTouchEvent(event);
		}

		return super.onTouchEvent(event);
	}

	private boolean onMyTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			if (mX != -1) {
				int XDistance = getDistance(event.getX() - mX);
				int YDisTance = getDistance(event.getY() - mY);
				if (YDisTance > XDistance && YDisTance > 50 && event.getY() < mY) {
					LogUtil.e("onMyTouchEvent",
							"mUpSlideListener != null : " + (mUpSlideListener != null) + " : " + (!mIsUpdate));
					if (mUpSlideListener != null && (!mIsUpdate)) {
						mIsUpdate = true;
						mUpSlideListener.doUpSlide();
						return true;
					}
				}
			}
			mX = (int) event.getX();
			mY = (int) event.getY();
			break;
		case MotionEvent.ACTION_UP:
			mX = -1;
			mY = -1;
			break;
		case MotionEvent.ACTION_DOWN:
			mIsUpdate = false;
			mX = (int) event.getX();
			mY = (int) event.getY();
			break;
		}
		return super.onTouchEvent(event);
	}

	private int getDistance(float distance) {
		if (distance < 0) {
			return (int) -distance;
		}
		return (int) distance;
	}

	public interface UpSlideListener {
		public void doUpSlide();
	}
}
