package com.beijing.beixin.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.TextView;

public class TriangleTextview extends TextView {

	private Paint mPaint;

	private static final int SHORT_LINE = 4;

	public TriangleTextview(Context context) {
		super(context);
		initPaint();
	}

	public TriangleTextview(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	public TriangleTextview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPaint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();
		int leftWidth = getPaddingLeft() - SHORT_LINE;
		int rightWidth = getPaddingRight() - SHORT_LINE;
		int midY = height >> 1;

		Path path = new Path();

		int y0 = midY + SHORT_LINE;
		path.moveTo(0, y0);

		int y1 = midY - SHORT_LINE;
		path.lineTo(0, y1);

		path.lineTo(leftWidth, 0);

		int x3 = width - rightWidth;
		path.lineTo(x3, 0);

		path.lineTo(width, y1);

		path.lineTo(width, y0);

		path.lineTo(x3, height);

		path.lineTo(leftWidth, height);

		path.close();

		canvas.drawPath(path, mPaint);
		super.onDraw(canvas);
	}

	private void initPaint() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.parseColor("#F0F0F0"));

	}

}
