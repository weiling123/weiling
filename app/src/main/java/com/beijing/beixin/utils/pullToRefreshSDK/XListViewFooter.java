package com.beijing.beixin.utils.pullToRefreshSDK;

import com.beijing.beixin.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 类名称:XListViewFooter 类描述:XListView底部布局 作者:张静 最后更新时间:2015年12月4日 上午9:59:30
 * 版本:v1.0
 */
public class XListViewFooter extends LinearLayout {

	public final static int STATE_NORMAL = 0;// 正常状态
	public final static int STATE_READY = 1;// 准备加载状态
	public final static int STATE_LOADING = 2;// 正在加载状态
	private Context mContext;// 上下文
	private View mContentView;// 底部内容的布局
	private ImageView mProgressBar;// 进度条
	private TextView mHintView;// 说明文字

	public XListViewFooter(Context context) {
		super(context);
		initView(context);
	}

	public XListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * 设置状态
	 * 
	 * @param state
	 *            (状态标记)
	 * @author 张静
	 * @Time 2015年12月4日上午10:07:06
	 */
	public void setState(int state) {
		mHintView.setVisibility(View.INVISIBLE);
		mProgressBar.setVisibility(View.GONE);
		mHintView.setVisibility(View.INVISIBLE);
		if (state == STATE_READY) {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText("松开刷新数据");
		} else if (state == STATE_LOADING) {
			mProgressBar.setVisibility(View.VISIBLE);
			AnimationSet animationSet = new AnimationSet(true);
			RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			rotateAnimation.setDuration(1000);
			animationSet.addAnimation(rotateAnimation);
			mProgressBar.startAnimation(animationSet);
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText("正在加载");
		} else {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText("更多...");
		}
	}

	/**
	 * 设置底部高度
	 * 
	 * @param height
	 *            (高度)
	 * @author 张静
	 * @Time 2015年12月4日上午10:20:12
	 */
	public void setBottomMargin(int height) {
		if (height < 0)
			return;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}

	/**
	 * 获得底部高度
	 * 
	 * @return
	 * @author 张静
	 * @Time 2015年12月4日上午10:21:25
	 */
	public int getBottomMargin() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
		return lp.bottomMargin;
	}

	/**
	 * 普通状态
	 * 
	 * @author 张静
	 * @Time 2015年12月4日上午10:12:39
	 */
	public void normal() {
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
	}

	/**
	 * 正在加载
	 * 
	 * @author 张静
	 * @Time 2015年12月4日上午10:12:11
	 */
	public void loading() {
		mHintView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
	}

	/**
	 * 隐藏底部的footer
	 * 
	 * @author 张静
	 * @Time 2015年12月4日上午10:11:32
	 */
	public void hide() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
		lp.height = 0;
		mContentView.setLayoutParams(lp);
	}

	/**
	 * 显示底部的footer
	 * 
	 * @author 张静
	 * @Time 2015年12月4日上午10:11:14
	 */
	public void show() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);
	}

	/**
	 * 初始化界面
	 * 
	 * @param context
	 *            (上下文)
	 * @author 张静
	 * @Time 2015年12月4日上午10:08:56
	 */
	@SuppressLint("InflateParams")
	@SuppressWarnings("deprecation")
	private void initView(Context context) {
		mContext = context;
		LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext)
				.inflate(R.layout.pulltorefresh_xlistview_footer, null);
		addView(moreView);// 加入布局文件
		moreView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		mContentView = moreView.findViewById(R.id.xlistview_footer_content);// 内容布局
		mProgressBar = (ImageView) moreView.findViewById(R.id.xlistview_footer_progressbar);
		mProgressBar.setImageResource(PullToRefreshConfig.mIconLoading);
		mHintView = (TextView) moreView.findViewById(R.id.xlistview_footer_hint_textview);
	}
}
