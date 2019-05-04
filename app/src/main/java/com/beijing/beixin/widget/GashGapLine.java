package com.beijing.beixin.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GashGapLine extends View {

	private Paint mPaint;

	public GashGapLine(Context context) {
		super(context);
		initView();
	}

	@SuppressWarnings("deprecation")
	public GashGapLine(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	private void initView() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.parseColor("#888888"));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = getWidth();
		int x = width / 2;

		int step = width * 2 + 3;

		int index = (getHeight() / (step)) - 2;

		for (int i = 0; index < 10; i++) {
			canvas.drawCircle(x, x + step * i + 2, x, mPaint);
		}

	}
}
