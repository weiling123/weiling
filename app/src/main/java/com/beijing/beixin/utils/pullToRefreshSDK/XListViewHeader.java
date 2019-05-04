package com.beijing.beixin.utils.pullToRefreshSDK;

import com.beijing.beixin.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 类名称:XListViewHeader 类描述:XListView头部布局 作者:张静 最后更新时间:2015年12月4日 上午9:30:23
 * 版本:v1.0
 */
public class XListViewHeader extends LinearLayout {

	private LinearLayout mContainer;// 头部布局
	private ImageView mArrowImageView;// 显示剪头图片的ImageView
	private ImageView mProgressBar;// 进度条
	private TextView mHintTextView;// 说明文本
	private int mState = STATE_NORMAL;// 用于记录状态,初始是正常状态
	/** 用于改变箭头方向的动画 */
	private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;
	private final int ROTATE_ANIM_DURATION = 180;// 动画持续时间
	public final static int STATE_NORMAL = 0;// 正常状态
	public final static int STATE_READY = 1;// 准备刷新状态,箭头方向发生改变之后的状态
	public final static int STATE_REFRESHING = 2;// 正在刷新状态,箭头变成了progressbar

	public XListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	public XListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * 初始化界面
	 * 
	 * @param context
	 *            (上下文)
	 * @author 张静
	 * @Time 2015年12月4日上午9:44:29
	 */
	@SuppressLint("InflateParams")
	private void initView(Context context) {
		// 初始情况，设置下拉刷新view高度为0,是为了隐藏header
		@SuppressWarnings("deprecation")
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0);
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.pulltorefresh_xlistview_header, null);
		addView(mContainer, lp);
		setGravity(Gravity.BOTTOM);// 设置居于底部
		mArrowImageView = (ImageView) findViewById(R.id.xlistview_header_arrow);// 箭头
		mArrowImageView.setImageResource(PullToRefreshConfig.mIconUpdate);
		mHintTextView = (TextView) findViewById(R.id.xlistview_header_hint_textview);// 指示文字
		mProgressBar = (ImageView) findViewById(R.id.xlistview_header_progressbar);// 进度条
		mProgressBar.setImageResource(PullToRefreshConfig.mIconLoading);
		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);// 设置动画效果
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);// 设置动画时长
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
	}

	/**
	 * 设置状态
	 * 
	 * @param state
	 *            (状态标记)
	 * @author 张静
	 * @Time 2015年12月4日上午9:46:07
	 */
	public void setState(int state) {
		if (state == mState)
			return;// 如果没有做任何操作（即普通状态）就返回空
		if (state == STATE_REFRESHING) { // 状态为 正在刷新的状态，显示进度
			mArrowImageView.clearAnimation();// 清除动画
			mArrowImageView.setVisibility(View.INVISIBLE);// 箭头图片设置为隐藏
			mProgressBar.setVisibility(View.VISIBLE);// 进度栏progressBar显示
			AnimationSet animationSet = new AnimationSet(true);
			RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			rotateAnimation.setDuration(1000);
			animationSet.addAnimation(rotateAnimation);
			mProgressBar.startAnimation(animationSet);
		} else { // 显示箭头图片,把进度条设为隐藏
			mArrowImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.INVISIBLE);
		}
		switch (state) {
		case STATE_NORMAL:// 普通状态
			if (mState == STATE_READY) {
				// 开始准备状态，箭头图片开启动画
				mArrowImageView.startAnimation(mRotateDownAnim);
			}
			if (mState == STATE_REFRESHING) {
				// 正在刷新状态，箭头图片清除动画
				mArrowImageView.clearAnimation();
			}
			mHintTextView.setText("下拉刷新");// 文字说明修改为"下拉刷新"
			break;
		case STATE_READY:// 准备状态
			if (mState != STATE_READY) {
				mArrowImageView.clearAnimation();
				mArrowImageView.startAnimation(mRotateUpAnim);
				mHintTextView.setText("松开刷新数据");// "松开刷新数据"
			}
			break;
		case STATE_REFRESHING:// 正在刷新状态
			mHintTextView.setText("正在加载");// "正在加载"
			break;
		default:
		}
		mState = state;
	}

	/**
	 * 设置header布局的高度
	 * 
	 * @param height
	 *            (高度)
	 * @author 张静
	 * @Time 2015年12月4日上午9:53:52
	 */
	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContainer.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	/**
	 * 获得heaer布局的高度
	 * 
	 * @return
	 * @author 张静
	 * @Time 2015年12月4日上午9:56:02
	 */
	public int getVisiableHeight() {
		return mContainer.getHeight();
	}
}
