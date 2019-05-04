package com.beijing.beixin.utils.takeRoundPhotoSDK.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 类名称:RoundImage 类描述:重写的圆形的ImageView 作者:张静 最后更新时间:2015年12月3日 下午3:49:40 版本:v1.0
 */
public class RoundImage extends ImageView {

	/** view宽 */
	private int viewWidth;
	/** view高 */
	private int viewHeight;
	/** bitmap对象 */
	private Bitmap image;
	/** 画笔对象 */
	private Paint paint;
	/** 边界画笔 */
	private Paint paintBorder;
	/** BitmapShader对象 */
	private BitmapShader shader;

	public RoundImage(Context context) {
		super(context);
		setup();
	}

	public RoundImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		setup();
	}

	public RoundImage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setup();
	}

	/**
	 * 初始化设置
	 * 
	 * @author 张静
	 * @Time 2015年12月3日下午2:44:35
	 */
	private void setup() {
		paint = new Paint();
		paint.setAntiAlias(true);
		paintBorder = new Paint();
		paintBorder.setAntiAlias(true);
	}

	/**
	 * 设置边界宽度
	 * 
	 * @param borderWidth
	 * @author 张静
	 * @Time 2015年12月3日下午2:45:11
	 */
	public void setBorderWidth(int borderWidth) {
		this.invalidate();
	}

	/**
	 * 设置边界颜色
	 * 
	 * @param borderColor
	 * @author 张静
	 * @Time 2015年12月3日下午2:45:23
	 */
	public void setBorderColor(int borderColor) {
		if (paintBorder != null)
			paintBorder.setColor(borderColor);
		this.invalidate();
	}

	/**
	 * 放置bitmap
	 * 
	 * @author 张静
	 * @Time 2015年12月3日下午2:45:44
	 */
	private void loadBitmap() {
		BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getDrawable();
		if (bitmapDrawable != null)
			image = bitmapDrawable.getBitmap();
	}

	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas) {
		// load the bitmap
		loadBitmap();
		// init shader
		if (image != null) {
			shader = new BitmapShader(Bitmap.createScaledBitmap(image, canvas.getWidth(), canvas.getHeight(), false),
					Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
			paint.setShader(shader);
			int circleCenter = viewWidth / 2;
			canvas.drawCircle(circleCenter, circleCenter, circleCenter, paint);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = measureWidth(widthMeasureSpec);
		int height = measureHeight(heightMeasureSpec, widthMeasureSpec);
		viewWidth = width;
		viewHeight = height;
		setMeasuredDimension(width, height);
	}

	/**
	 * 测量宽度
	 * 
	 * @param measureSpec
	 * @return
	 * @author 张静
	 * @Time 2015年12月3日下午2:46:24
	 */
	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			// Measure the text
			result = viewWidth;
		}
		return result;
	}

	/**
	 * 测量高度
	 * 
	 * @param measureSpecHeight
	 * @param measureSpecWidth
	 * @return
	 * @author 张静
	 * @Time 2015年12月3日下午2:46:42
	 */
	private int measureHeight(int measureSpecHeight, int measureSpecWidth) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpecHeight);
		int specSize = MeasureSpec.getSize(measureSpecHeight);
		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			result = viewHeight;
		}
		return result;
	}
}
