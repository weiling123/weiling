package com.beijing.beixin.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 倒计时的工具类
 * 
 * @author 张鑫
 *
 */
public class DaoCounter extends CountDownTimer {

	/**
	 * 上下文对象
	 */
	private Context context;
	/**
	 * 时间
	 */
	public static final int TIME_COUNT = 121000;
	/**
	 * 组件，用来显示倒计时字体
	 */
	private TextView tv;
	/**
	 * 字体颜色
	 */
	@SuppressWarnings("unused")
	private int tickTVColor, tickBGColor, finishBGColor;
	/**
	 * 字符串
	 */
	private String finishText;
	/**
	 * 颜色
	 */
	@SuppressWarnings("unused")
	private int finishTVColor;

	/**
	 * 构造函数，传参，赋值
	 */
	// super(millisInFuture, countDownInterval);
	public DaoCounter(Context context, long millisInFuture, long countDownInterval, TextView btn, int tvColor,
			int tickBGColor, int finishBGColor, String finishText) {
		super(millisInFuture, countDownInterval);
		this.context = context;
		btn.setTextColor(context.getResources().getColor(tvColor));
		this.tv = btn;
		this.tickBGColor = tickBGColor;
		this.finishBGColor = finishBGColor;
		this.finishText = finishText;
	}

	// 倒计时结束
	@Override
	public void onFinish() {
		System.out.println("onFinish");
		tv.setClickable(true);
		tv.setText(finishText);
		tv.setBackgroundColor(context.getResources().getColor(finishBGColor));// ???
	}

	// 计时中
	@Override
	public void onTick(long millisUntilFinished) {
		System.out.println("onTick");
		tv.setClickable(false);
		tv.setText(millisUntilFinished / 1000 + "秒后重发");
		tv.setBackgroundColor(context.getResources().getColor(tickBGColor));// ???
	}
}
