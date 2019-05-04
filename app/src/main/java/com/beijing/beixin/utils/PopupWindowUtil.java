package com.beijing.beixin.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * popwindow设置
 * 
 * @author ouyanghao
 */
@SuppressLint("RtlHardcoded")
public class PopupWindowUtil {
	private String PopupWindowUtil = "PopupWindowUtil ";
	protected final View anchor;
	private final PopupWindow popWindow;
	private View root;
	private Drawable background = null;
	private final WindowManager windowManager;
	@SuppressWarnings("unused")
	private Context context;
	protected Activity activity;

	private DisplayMetrics mMetric = null;

	public PopupWindowUtil(View anchor, Activity activity, Object object) {
		this.anchor = anchor;
		this.activity = activity;
		this.popWindow = new PopupWindow(anchor.getContext());

		this.popWindow.setTouchInterceptor(new OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					// Common.setNormal((Activity)
					// PopupWindowUtil.this.anchor.getContext());
					PopupWindowUtil.this.popWindow.dismiss();
					return true;
				}
				return false;
			}
		});

		this.windowManager = (WindowManager) this.anchor.getContext().getSystemService(Context.WINDOW_SERVICE);
	}

	public PopupWindowUtil(View anchor, Activity activity) {
		this.anchor = anchor;
		this.activity = activity;
		this.popWindow = new PopupWindow(anchor.getContext());

		this.popWindow.setTouchInterceptor(new OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					// Common.setNormal((Activity)
					// PopupWindowUtil.this.anchor.getContext());
					PopupWindowUtil.this.popWindow.dismiss();
					return true;
				}
				return false;
			}
		});

		this.windowManager = (WindowManager) this.anchor.getContext().getSystemService(Context.WINDOW_SERVICE);
		onCreate();
	}

	/**
	 * Anything you want to have happen when created. Probably should create a
	 * view and setup the event listeners on child views.
	 */
	protected void onCreate() {
	}

	/**
	 * In case there is stuff to do right before displaying.
	 */
	protected void onShow() {
	}

	@SuppressWarnings("deprecation")
	private void preShow() {
		if (this.root == null) {
			throw new IllegalStateException("setContentView was not called with a view to display.");
		}
		onShow();

		if (this.background == null) {
			this.popWindow.setBackgroundDrawable(new BitmapDrawable());
		} else {
			this.popWindow.setBackgroundDrawable(this.background);
		}

		// int popWidth =
		// context.getResources().getDimensionPixelOffset(R.dimen.popupWindow_width);
		// LogHelper.i(PopupWindowUtil, "popWidth: " + popWidth);

		mMetric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(mMetric);
		int width = mMetric.widthPixels / 2; // 屏幕宽度（像素）
		// LogHelper.e(PopupWindowUtil, "metric.widthPixels:" +
		// mMetric.widthPixels + " width: " + width);
		this.popWindow.setWidth(width);

		// this.popWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		this.popWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		this.popWindow.setTouchable(true);
		this.popWindow.setFocusable(true);
		this.popWindow.setOutsideTouchable(true);

		this.popWindow.setContentView(this.root);
		// Common.setGray((Activity) anchor.getContext());
	}

	/**
	 * 登录界面pop设置
	 */
	@SuppressWarnings("deprecation")
	private void preLoginInfoListShow(View view) {
		if (this.root == null) {
			throw new IllegalStateException("setContentView was not called with a view to display.");
		}
		onShow();

		if (this.background == null) {
			this.popWindow.setBackgroundDrawable(new BitmapDrawable());
		} else {
			this.popWindow.setBackgroundDrawable(this.background);
		}

		// int popWidth =
		// context.getResources().getDimensionPixelOffset(R.dimen.popupWindow_width);
		// LogHelper.i(PopupWindowUtil, "popWidth: " + popWidth);

		mMetric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(mMetric);
		// 屏幕宽度（像素）
		int width = view.getWidth();
		LogUtil.e(PopupWindowUtil, "metric.widthPixels:" + mMetric.widthPixels + " width: " + width);
		this.popWindow.setWidth(width);

		// this.popWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		this.popWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		this.popWindow.setTouchable(true);
		this.popWindow.setFocusable(true);
		this.popWindow.setOutsideTouchable(true);

		this.popWindow.setContentView(this.root);
		// Common.setGray((Activity) anchor.getContext());
	}

	/**
	 * 分园
	 */
	@SuppressWarnings("deprecation")
	private void preChildGardenShow() {
		if (this.root == null) {
			throw new IllegalStateException("setContentView was not called with a view to display.");
		}
		onShow();

		if (this.background == null) {
			this.popWindow.setBackgroundDrawable(new BitmapDrawable());
		} else {
			this.popWindow.setBackgroundDrawable(this.background);
		}

		// int popWidth =
		// context.getResources().getDimensionPixelOffset(R.dimen.popupWindow_width);
		// LogHelper.i(PopupWindowUtil, "popWidth: " + popWidth);

		mMetric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(mMetric);
		int width = mMetric.widthPixels; // 屏幕宽度（像素）
		// LogHelper.e(PopupWindowUtil, "metric.widthPixels:" +
		// mMetric.widthPixels + " width: " + width);
		this.popWindow.setWidth(width);

		// this.popWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		this.popWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		this.popWindow.setTouchable(true);
		this.popWindow.setFocusable(true);
		this.popWindow.setOutsideTouchable(true);

		this.popWindow.setContentView(this.root);
		// Common.setGray((Activity) anchor.getContext());
	}

	@SuppressWarnings("deprecation")
	private void preShowHorizontal() {
		if (this.root == null) {
			throw new IllegalStateException("setContentView was not called with a view to display.");
		}
		onShow();

		if (this.background == null) {
			this.popWindow.setBackgroundDrawable(new BitmapDrawable());
		} else {
			this.popWindow.setBackgroundDrawable(this.background);
		}

		// int popWidth =
		// context.getResources().getDimensionPixelOffset(R.dimen.popupWindow_width);
		// LogHelper.i(PopupWindowUtil, "popWidth: " + popWidth);

		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels * 2 / 3; // 屏幕宽度（像素）
		LogUtil.i(PopupWindowUtil, "width: " + width);
		this.popWindow.setWidth(width);

		// this.popWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		this.popWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		this.popWindow.setTouchable(true);
		this.popWindow.setFocusable(true);
		this.popWindow.setOutsideTouchable(true);

		this.popWindow.setContentView(this.root);
		// Common.setGray((Activity) anchor.getContext());
	}

	@SuppressWarnings("deprecation")
	private void preShowSecond(int viewHeight) {
		if (this.root == null) {
			throw new IllegalStateException("setContentView was not called with a view to display.");
		}
		onShow();

		if (this.background == null) {
			this.popWindow.setBackgroundDrawable(new BitmapDrawable());
		} else {
			this.popWindow.setBackgroundDrawable(this.background);
		}

		// int popWidth =
		// context.getResources().getDimensionPixelOffset(R.dimen.popupWindow_width);
		// LogHelper.i(PopupWindowUtil, "popWidth: " + popWidth);

		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; // 屏幕宽度（像素）
		LogUtil.i(PopupWindowUtil, "width: " + width);
		this.popWindow.setWidth(width);
		@SuppressWarnings("unused")
		int height = metric.heightPixels;

		// this.popWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);

		this.popWindow.setHeight(viewHeight);
		// this.popWindow.setHeight(height * 3 / 4);

		this.popWindow.setTouchable(true);
		this.popWindow.setFocusable(true);
		this.popWindow.setOutsideTouchable(true);

		this.popWindow.setContentView(this.root);
		// Common.setGray((Activity) anchor.getContext());
	}

	public void setBackgroundDrawable(Drawable background) {
		this.background = background;
	}

	/**
	 * Sets the content view. Probably should be called from {@link onCreate}
	 * 
	 * @param root
	 *            the view the popup will display
	 */
	public void setContentView(View root) {
		this.root = root;
		this.popWindow.setContentView(root);
	}

	/**
	 * Will inflate and set the view from a resource id
	 * 
	 * @param layoutResID
	 */
	public void setContentView(int layoutResID) {
		LayoutInflater inflator = (LayoutInflater) this.anchor.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.setContentView(inflator.inflate(layoutResID, null));
	}

	/**
	 * If you want to do anything when {@link dismiss} is called
	 * 
	 * @param listener
	 */
	public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
		this.popWindow.setOnDismissListener(listener);
	}

	/**
	 * Displays like a popdown menu from the anchor view
	 */
	public void showLikePopDownMenu() {
		this.showLikePopDownMenu(0, 0);
	}

	/**
	 * 显示登录过的用户信息列表
	 */
	public void showLoginUserInfoListMenu(View view) {
		this.showLoginInfoListPopDownMenu(view, 0, 0);
	}

	/**
	 * Displays like a popdown menu from the anchor view.
	 * 
	 * @param xOffset
	 *            offset in X direction
	 * @param yOffset
	 *            offset in Y direction
	 */
	public void showLikePopDownMenu(int xOffset, int yOffset) {
		this.preShow();

		if (mMetric == null) {
			mMetric = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(mMetric);
		}

		int[] location = new int[2];
		anchor.getLocationOnScreen(location);
		int x = location[0];
		@SuppressWarnings("unused")
		int y = location[1];
		int start = (mMetric.widthPixels / 2) - 10 - x;

		// this.popWindow.setAnimationStyle(R.style.PopDownMenu);
		// this.popWindow.showAsDropDown(this.anchor, xOffset, yOffset);
		this.popWindow.showAsDropDown(this.anchor, start, yOffset);
	}

	/**
	 * 登陆界面用户本地数据库中用户信息列表对话框
	 * 
	 * @param view
	 * @param xOffset
	 * @param yOffset
	 */
	public void showLoginInfoListPopDownMenu(View view, int xOffset, int yOffset) {
		this.preLoginInfoListShow(view);

		if (mMetric == null) {
			mMetric = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(mMetric);
		}

		int[] location = new int[2];
		anchor.getLocationOnScreen(location);
		int x = location[0];
		@SuppressWarnings("unused")
		int y = location[1];
		@SuppressWarnings("unused")
		int start = (mMetric.widthPixels / 2) - 10 - x;

		// this.popWindow.setAnimationStyle(R.style.PopDownMenu);
		this.popWindow.showAsDropDown(this.anchor, xOffset, yOffset);
	}

	/**
	 * 显示分园 Displays like a popdown menu from the anchor view
	 */
	public void showLikeChildPopDownMenu() {
		this.showLikeChikdPopDownMenu(0, 0);
	}

	/**
	 * 显示分园对话框 Displays like a popdown menu from the anchor view.
	 * 
	 * @param xOffset
	 *            offset in X direction
	 * @param yOffset
	 *            offset in Y direction
	 */
	public void showLikeChikdPopDownMenu(int xOffset, int yOffset) {
		this.preChildGardenShow();

		if (mMetric == null) {
			mMetric = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(mMetric);
		}

		int[] location = new int[2];
		anchor.getLocationOnScreen(location);
		int x = location[0];
		@SuppressWarnings("unused")
		int y = location[1];
		int start = 0 - x;

		// this.popWindow.setAnimationStyle(R.style.GrowFromTop);
		// this.popWindow.showAsDropDown(this.anchor, xOffset, yOffset);
		this.popWindow.showAsDropDown(this.anchor, start, yOffset);
	}

	/**
	 * 成长档案目录 Displays like a popdown menu from the anchor view
	 */
	public void showLikePopDownMenuHorizontal() {
		this.showLikePopDownMenuHorizontal(0, 0);
	}

	public void showLikePopDownMenuHorizontal(int xOffset, int yOffset) {
		preShowHorizontal();
		// this.popWindow.setAnimationStyle(R.style.PopDownMenu);
		this.popWindow.showAsDropDown(this.anchor, xOffset, yOffset);
	}

	/**
	 * 正下方显示
	 */
	public void showLikePopDownMenuDown(int xOffset, int yOffset) {
		this.preShow();
		// this.popWindow.setAnimationStyle(R.style.PopDownMenu);
		this.popWindow.showAtLocation(this.anchor, Gravity.BOTTOM, xOffset, yOffset);
	}

	/**
	 * 中间显示
	 */
	public void showLikePopDownMenuCenter(int xOffset, int yOffset) {
		this.preShow();
		// this.popWindow.setAnimationStyle(R.style.PopDownMenu);
		this.popWindow.showAtLocation(this.anchor, Gravity.CENTER, xOffset, yOffset);
	}

	/**
	 * 上面显示
	 */
	public void showLikePopDownMenuTop(int xOffset, int yOffset) {
		this.preShow();
		// this.popWindow.setAnimationStyle(R.style.PopDownMenu);
		this.popWindow.showAtLocation(this.anchor, Gravity.TOP, xOffset, yOffset);
	}

	/**
	 * 上面显示
	 */
	@SuppressLint("RtlHardcoded")
	public void showLikePopDownMenuTopAndLeft(int xOffset, int yOffset) {
		this.preShow();
		// this.popWindow.setAnimationStyle(R.style.PopDownMenu);
		this.popWindow.showAtLocation(this.anchor, Gravity.TOP | Gravity.LEFT, xOffset, yOffset);
	}

	/**
	 * 上面显示
	 */
	public void showLikePopDownMenuNo(int xOffset, int yOffset) {
		this.preShow();
		// this.popWindow.setAnimationStyle(R.style.PopDownMenu);
		this.popWindow.showAtLocation(this.anchor, Gravity.NO_GRAVITY, xOffset, yOffset);
	}

	/**
	 * 右上角显示
	 */
	public void showLikePopDownMenuTopAndRight(int xOffset, int yOffset) {
		this.preShow();
		// this.popWindow.setAnimationStyle(R.style.PopDownMenu);
		this.popWindow.showAtLocation(this.anchor, Gravity.TOP | Gravity.RIGHT, xOffset, yOffset);
	}

	/**
	 * 在控件上面显示 Displays like a popdown menu from the anchor view
	 */
	public void showLikePopUpMenu(View view, int gridviewHeight) {
		preShowSecond(gridviewHeight);

		int[] location = new int[2];
		// this.anchor.getLocationOnScreen(location);
		view.getLocationOnScreen(location);

		// this.popWindow.setAnimationStyle(R.style.GrowFromBottom);
		// this.popWindow.showAtLocation(this.anchor, Gravity.CENTER, 0, 0);
		// this.popWindow.showAtLocation(this.anchor, Gravity.NO_GRAVITY,
		// location[0], location[1] - this.popWindow.getHeight());
		this.popWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1] - this.popWindow.getHeight());

	}

	/**
	 * Displays like a QuickAction from the anchor view.
	 */
	public void showLikeQuickAction() {
		this.showLikeQuickAction(0, 0);
	}

	/**
	 * Displays like a QuickAction from the anchor view.
	 * 
	 * @param xOffset
	 *            offset in the X direction
	 * @param yOffset
	 *            offset in the Y direction
	 */
	public void showLikeQuickAction(int xOffset, int yOffset) {
		this.preShow();

		// this.popWindow.setAnimationStyle(R.style.GrowFromBottom);

		int[] location = new int[2];
		this.anchor.getLocationOnScreen(location);

		Rect anchorRect = new Rect(location[0], location[1], location[0] + this.anchor.getWidth(),
				location[1] + this.anchor.getHeight());

		this.root.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		int rootWidth = this.root.getMeasuredWidth();
		int rootHeight = this.root.getMeasuredHeight();

		@SuppressWarnings("deprecation")
		int screenWidth = this.windowManager.getDefaultDisplay().getWidth();

		int xPos = ((screenWidth - rootWidth) / 2) + xOffset;
		int yPos = anchorRect.top - rootHeight + yOffset;

		// display on bottom
		if (rootHeight > anchorRect.top) {
			yPos = anchorRect.bottom + yOffset;
			// this.popWindow.setAnimationStyle(R.style.GrowFromTop);
		}

		this.popWindow.showAtLocation(this.anchor, Gravity.NO_GRAVITY, xPos, yPos);
	}

	public void showCenter() {
		this.preShow();
		// this.popWindow.setAnimationStyle(R.style.GrowFromBottom);
		this.popWindow.showAtLocation(this.anchor, Gravity.CENTER, 0, 0);

	}

	public void dismiss() {
		this.popWindow.dismiss();
	}
}
