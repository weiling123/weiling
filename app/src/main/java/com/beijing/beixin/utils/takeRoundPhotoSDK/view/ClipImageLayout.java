package com.beijing.beixin.utils.takeRoundPhotoSDK.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

/**
 * 类名称:ClipImageLayout 类描述:裁剪界面重写的布局 作者:张静 最后更新时间:2015年12月3日 下午3:48:46 版本:v1.0
 */
public class ClipImageLayout extends RelativeLayout {

	/** 需要裁剪的移动缩放的imageView */
	private ClipZoomImageView mZoomImageView;
	/** 裁剪边框 */
	private ClipImageBorderView mClipImageView;
	/** 横向边距 */
	private int mHorizontalPadding = 60;

	/**
	 * 自定义view的构造方法
	 */
	public ClipImageLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mZoomImageView = new ClipZoomImageView(context);
		mClipImageView = new ClipImageBorderView(context);
		android.view.ViewGroup.LayoutParams lp = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		this.addView(mZoomImageView, lp);
		this.addView(mClipImageView, lp);
		// 计算padding的px
		mHorizontalPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding,
				getResources().getDisplayMetrics());
		mZoomImageView.setHorizontalPadding(mHorizontalPadding);
		mClipImageView.setHorizontalPadding(mHorizontalPadding);
	}

	/**
	 * 对外公布设置边距的方法,单位为dp
	 * 
	 * @param mHorizontalPadding
	 *            (边距)
	 * @author 张静
	 * @Time 2015年12月3日下午2:32:33
	 */
	public void setHorizontalPadding(int mHorizontalPadding) {
		this.mHorizontalPadding = mHorizontalPadding;
	}

	/**
	 * 裁剪图片
	 * 
	 * @return
	 * @author 张静
	 * @Time 2015年12月3日下午2:31:58
	 */
	public Bitmap clip() {
		return mZoomImageView.clip();
	}

	/**
	 * 设置bitmap
	 * 
	 * @param bitmap
	 *            (bitmap对象)
	 * @author 张静
	 * @Time 2015年12月3日下午2:32:12
	 */
	public void setBitmap(Bitmap bitmap) {
		mZoomImageView.setImageBitmap(bitmap);
	}
}
