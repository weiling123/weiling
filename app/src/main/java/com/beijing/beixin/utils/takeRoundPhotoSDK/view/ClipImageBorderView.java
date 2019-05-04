package com.beijing.beixin.utils.takeRoundPhotoSDK.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 类名称:ClipImageBorderView 类描述:裁剪边框自定义View 作者:张静 最后更新时间:2015年12月3日 下午3:48:31
 * 版本:v1.0
 */
public class ClipImageBorderView extends View {

	/** 水平方向与View的边距 */
	private int mHorizontalPadding;
	/** 边框的宽度 单位dp */
	private int mBorderWidth = 2;
	/** 画笔 */
	private Paint mPaint;

	public ClipImageBorderView(Context context) {
		this(context, null);
	}

	public ClipImageBorderView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ClipImageBorderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mBorderWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mBorderWidth,
				getResources().getDisplayMetrics());
		mPaint = new Paint();// 实例化paint对象
		mPaint.setAntiAlias(true);
	}

	/**
	 * 绘制裁剪的边框
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 绘制边框
		mPaint.setColor(Color.parseColor("#FFFFFF"));
		mPaint.setStrokeWidth(mBorderWidth);
		mPaint.setStyle(Style.STROKE);
		// 圆形边框
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - mHorizontalPadding, mPaint);
	}

	public void setHorizontalPadding(int mHorizontalPadding) {
		this.mHorizontalPadding = mHorizontalPadding;
	}
}
