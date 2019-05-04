package com.beijing.beixin.widget;

import com.beijing.beixin.utils.LogUtil;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

public class CircleTextView extends TextView {

	private Paint mPaint;

	private Context mContext;

	public CircleTextView(Context context) {
		super(context, null);
		initPaint(context);
	}

	public CircleTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		initPaint(context);
	}

	public CircleTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPaint(context);
	}

	private void initPaint(Context context) {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(getTextColors().getDefaultColor());
		mPaint.setStrokeWidth(2);
		mContext = context;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		RectF rect = new RectF(0, 0, getWidth(), getHeight());
		canvas.drawArc(rect, 0, 360, false, mPaint);
		super.onDraw(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		String text = (String) getText();
		Rect rect = new Rect();
		getPaint().getTextBounds(text, 0, text.length(), rect);

		int width = rect.left + rect.width();
		int height = rect.bottom + rect.height();

		LogUtil.e("rect.left + rect.width()", rect.left + " : " + rect.width());

		int size = width > height ? width : height;
		size = (int) (Math.sqrt(size * size * 2) + 0.5);
		setMeasuredDimension(size, size);
	}
}