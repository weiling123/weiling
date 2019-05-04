package com.beijing.beixin.ui.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.VoicemailContract;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.HorizontalListViewAdapter;
import com.beijing.beixin.adapter.HorizontalListViewAdapter1;
import com.beijing.beixin.adapter.ViewHolder;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.AppClientModuleObjectVo;
import com.beijing.beixin.pojo.AppClientModuleVo;
import com.beijing.beixin.pojo.AppProductBaseVo;
import com.beijing.beixin.pojo.CategoryInfoBean;
import com.beijing.beixin.pojo.DataShop;
import com.beijing.beixin.pojo.RecommendBean;
import com.beijing.beixin.pojo.HotProduct;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.MainActivity;
import com.beijing.beixin.ui.base.BaseFragment;
import com.beijing.beixin.ui.classify.HotProductActivity;
import com.beijing.beixin.ui.classify.EightAreasActivity;
import com.beijing.beixin.ui.classify.NewProductActivity;
import com.beijing.beixin.ui.classify.ProductListActivity;
import com.beijing.beixin.ui.homepage.GroupBuyActivity;
import com.beijing.beixin.ui.homepage.SearchActivity;
import com.beijing.beixin.ui.homepage.WebViewActivity;
import com.beijing.beixin.ui.myself.login.LoginActivity;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.ui.shoppingcart.ShopActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.CommonAlertDialog;
import com.beijing.beixin.utils.DensityUtil;
import com.beijing.beixin.utils.HorizontalListView;
import com.beijing.beixin.utils.IgnitedDiagnosticsUtils;
import com.beijing.beixin.utils.LogUtil;
import com.beijing.beixin.utils.MyGridView;
import com.beijing.beixin.utils.NetWorkUtils;
import com.beijing.beixin.utils.ToastUtil;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase.Mode;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.beijing.beixin.utils.zxing.CaptureActivity;
import com.beijing.beixin.widget.PullToRefreshHorizontalScrollView;
import com.beijing.beixin.widget.PullToRefreshScrollView;
import com.beijing.beixin.widget.PullToRefreshScrollView.onScroll;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 首页
 * 
 * @author ouyanghao
 * 
 */
@SuppressWarnings("unused")
public class HomeFragment extends BaseFragment implements OnClickListener {

	private static final int TEXTVIEW_ID = 10000;
	/**
	 * ViewPager
	 */
	private ViewPager viewPager;
	/**
	 * 装点点的ImageView数组
	 */
	private ImageView[] tips;
	/**
	 * 装ImageView数组
	 */
	private ImageView[] mImageViews;
	/**
	 * 图片资源id
	 */
	private String[] imgIdArray;
	private List<String> picList = new ArrayList<String>();
	/**
	 * 二维码扫描的按钮
	 */
	private ImageView imageview_scancode;
	/**
	 * 北新头条logo
	 */
	private ImageView imageview_news;
	/**
	 * 首页团购
	 */
	private ImageView imageview_group;
	/**
	 * 首页新品上架
	 */
	private ImageView imageview_newpro;
	/**
	 * 首页精品热销
	 */
	private ImageView imageview_hotsold;
	/**
	 * 首页主编圈子
	 */
	private ImageView imageview_editor;
	/**
	 * 新闻
	 */
	private ViewFlipper textview_news;
	/**
	 * 首页面的gridview
	 */
	private MyGridView gridview_home;
	/**
	 * 为你推荐的gridview
	 */
	private MyGridView gridview_recommend;
	/**
	 * 精品热销
	 */
	private RelativeLayout Rl_hot;
	/**
	 * 新品上架
	 */
	private RelativeLayout Rl_new;
	/**
	 * 布局中的TabWidget
	 */
	private ScheduledExecutorService scheduledExecutorService;
	private TabHost tabHost;
	private TabWidget tabWidget;
	private LinearLayout layout_home_timebuy;
	private int currentItem; // 当前页面
	private EditText et_component_search;
	private View v;
	private LinearLayout mLinearLayoutViewRush;
	private PullToRefreshHorizontalScrollView mHorizontalListViewRush;
	private LinearLayout mLinearLayoutViewGood;
	private PullToRefreshHorizontalScrollView mHorizontalListViewGood;
	private List<CategoryInfoBean> CategoryInfoList;
	private List<AppClientModuleVo> homeList;
	private TextView textview_group_title;
	private TextView textview_home_tip1;
	private TextView textview_home_time;
	private TextView textview_new_book;
	private TextView textview_home_tip2;
	private TextView textview_best_sold;
	private TextView textview_best_detail;
	private TextView textview_editor;
	private TextView textview_time_buy;
	private ImageView imageview_adv1;
	private TextView textview_hot_sale;
	private TextView textview_good_shop;
	private TextView textview_shopname1;
	private TextView textview_shopname1_msg;
	private TextView textview_shopname2;
	private TextView textview_shopname2_msg;
	private TextView textview_shopname3;
	private TextView textview_shopname3_msg;
	private TextView textview_shopname4;
	private TextView textview_shopname4_msg;
	private TextView textview_publisher;
	private TextView textview_publisher1_msg;
	private TextView textview_publisher22_tip;
	private TextView textview_home_tip3;
	private TextView textview_best_detail2;
	private TextView textview_recommend;
	private TextView textview_recommend2;
	private TextView textview_recommend3;
	private TextView textview_recommend4;
	private ImageView imageview_disccount1;
	private ImageView imageview_disccount2;
	private ImageView imageview_disccount3;
	private ImageView imageview_disccount4;
	private ImageView imageview_shop_left;
	private ImageView imageview_shop1;
	private ImageView imageview_shop2;
	private ImageView imageview_shop3;
	private ImageView imageview_shop4;
	private ImageView imageview_adv2;
	private ImageView imageview_publisher1;
	private ImageView imageview_publisher1_content;
	private ImageView imageview_time_best2;
	private ImageView textview_new_book2;
	private ImageView textview_best_sold2;
	private ImageView imageview_publisher21;
	private ImageView imageview_publisher22_logo;
	private ImageView imageview_publisher22_content;
	private ImageView imageview_adv3;
	private LinearLayout ll_editor;
	private LinearLayout ll_shop1;
	private LinearLayout ll_shop2;
	private LinearLayout ll_shop3;
	private LinearLayout ll_shop4;
	private LinearLayout ll_publisher1;
	private RelativeLayout rl_publisher2;
	private RelativeLayout rl_publisher3;
	private LinearLayout ll_publisher4;
	private LinearLayout ll_product1;
	private LinearLayout ll_product2;
	private LinearLayout ll_product3;
	private LinearLayout ll_product4;
	private LinearLayout ll_lookmore;
	private RelativeLayout rl_group;
	private RelativeLayout rl_viewGroup;
	private String pid1;
	private String pid2;
	private String pid3;
	private String pid4;
	private String shop1;
	private String shop2;
	private String shop3;
	private String shop4;
	private String shop5;
	private String p1;
	private String p2;
	private String p3;
	private String p4;
	private String openUrl;
	private List<AppClientModuleObjectVo>[] mHomeBooks;
	private AppClientModuleVo mPageAppClientModuleVo;
	private AppClientModuleVo[] mAdAppClientModuleVo = new AppClientModuleVo[3];
	private AppClientModuleVo[] mDisAppClientModuleVo = new AppClientModuleVo[4];
	private AppClientModuleVo mGroupModuleVo;
	private AppClientModuleVo mNewModuleVo;
	private AppClientModuleVo[] mPulisherModuleVo = new AppClientModuleVo[5];
	// 判断是否自动滚动的标志
	private boolean isrunning = true;
	private int cartPosition = 0;
	private CountDownTimer mCountDownTimer;
	private ImageView iv_loginorexit;
	private LinearLayout ll_loginorexit;
	private LinearLayout layout_title;
	private Context context;
	private List<AppClientModuleObjectVo> listRecommend;
	private List<AppClientModuleObjectVo> listAreas;
	private PullToRefreshScrollView pullToRefreshScrollView;
	private HotProduct[] mSellWells;
	/**
	 * 
	 */
	private int mSellWellIndex;
	/**
	 * 自动滑动的Handler 利用 Handler.sendEmptyMessageDelayed方法，实现定时滚动
	 * 
	 */
	private Handler handler2 = new Handler() {

		public void handleMessage(android.os.Message msg) {
			// 让ViewPager滑到下一页
			if (picList != null && picList.size() != 0) {
				currentItem = (currentItem + 1) % picList.size();
				viewPager.setCurrentItem(currentItem);
				// 延时，循环调用handler
				if (isrunning) {
					handler2.sendEmptyMessageDelayed(0, 5000);
				}
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.home_fragment, container, false);
		init(v);
		setListener();
		context = getActivity();
		sendhttps();
		handler2.sendEmptyMessageDelayed(0, 5000);
		return v;
	}

	public void setPoistion(int position) {
		cartPosition = position;
	}

	private void init(View v) {
		initGridView(v);
		mLinearLayoutViewRush = (LinearLayout) v.findViewById(R.id.linearLayout_push);
		mHorizontalListViewRush = (PullToRefreshHorizontalScrollView) v.findViewById(R.id.horizontallistview_push);
		initBestSold(v);
		layout_title = (LinearLayout) v.findViewById(R.id.layout_title);
		iv_loginorexit = (ImageView) v.findViewById(R.id.iv_loginorexit);
		ll_loginorexit = (LinearLayout) v.findViewById(R.id.ll_loginorexit);
		imageview_scancode = (ImageView) v.findViewById(R.id.imageview_scancode);
		imageview_news = (ImageView) v.findViewById(R.id.imageview_news);
		imageview_group = (ImageView) v.findViewById(R.id.imageview_group);
		imageview_newpro = (ImageView) v.findViewById(R.id.imageview_newpro);
		imageview_hotsold = (ImageView) v.findViewById(R.id.imageview_hotsold);
		imageview_editor = (ImageView) v.findViewById(R.id.imageview_editor);
		imageview_adv1 = (ImageView) v.findViewById(R.id.imageview_adv1);
		imageview_disccount1 = (ImageView) v.findViewById(R.id.imageview_disccount1);
		imageview_disccount2 = (ImageView) v.findViewById(R.id.imageview_disccount2);
		imageview_disccount3 = (ImageView) v.findViewById(R.id.imageview_disccount3);
		imageview_disccount4 = (ImageView) v.findViewById(R.id.imageview_disccount4);
		imageview_shop_left = (ImageView) v.findViewById(R.id.imageview_shop_left);
		imageview_shop1 = (ImageView) v.findViewById(R.id.imageview_shop1);
		imageview_shop2 = (ImageView) v.findViewById(R.id.imageview_shop2);
		imageview_shop3 = (ImageView) v.findViewById(R.id.imageview_shop3);
		imageview_shop4 = (ImageView) v.findViewById(R.id.imageview_shop4);
		imageview_adv2 = (ImageView) v.findViewById(R.id.imageview_adv2);
		imageview_publisher1 = (ImageView) v.findViewById(R.id.imageview_publisher1_logo);
		imageview_publisher1_content = (ImageView) v.findViewById(R.id.imageview_publisher1_content);
		imageview_time_best2 = (ImageView) v.findViewById(R.id.imageview_time_best2);
		textview_new_book2 = (ImageView) v.findViewById(R.id.textview_new_book2);
		textview_best_sold2 = (ImageView) v.findViewById(R.id.textview_best_sold2);
		imageview_publisher21 = (ImageView) v.findViewById(R.id.imageview_publisher21);
		imageview_publisher22_logo = (ImageView) v.findViewById(R.id.imageview_publisher22_logo);
		imageview_publisher22_content = (ImageView) v.findViewById(R.id.imageview_publisher22_content);
		imageview_adv3 = (ImageView) v.findViewById(R.id.imageview_adv3);
		textview_news = (ViewFlipper) v.findViewById(R.id.textview_news);
		textview_group_title = (TextView) v.findViewById(R.id.textview_group_title);
		textview_home_tip1 = (TextView) v.findViewById(R.id.textview_home_tip1);
		textview_home_time = (TextView) v.findViewById(R.id.textview_home_time);
		textview_new_book = (TextView) v.findViewById(R.id.textview_new_book);
		textview_home_tip2 = (TextView) v.findViewById(R.id.textview_home_tip2);
		textview_best_sold = (TextView) v.findViewById(R.id.textview_best_sold);
		textview_best_detail = (TextView) v.findViewById(R.id.textview_best_detail);
		textview_editor = (TextView) v.findViewById(R.id.textview_editor);
		textview_time_buy = (TextView) v.findViewById(R.id.textview_time_buy);
		textview_hot_sale = (TextView) v.findViewById(R.id.textview_hot_sale);
		textview_good_shop = (TextView) v.findViewById(R.id.textview_good_shop);
		textview_shopname1 = (TextView) v.findViewById(R.id.textview_shopname1);
		textview_shopname1_msg = (TextView) v.findViewById(R.id.textview_shopname1_msg);
		textview_shopname2 = (TextView) v.findViewById(R.id.textview_shopname2);
		textview_shopname2_msg = (TextView) v.findViewById(R.id.textview_shopname2_msg);
		textview_shopname3 = (TextView) v.findViewById(R.id.textview_shopname3);
		textview_shopname3_msg = (TextView) v.findViewById(R.id.textview_shopname3_msg);
		textview_shopname4 = (TextView) v.findViewById(R.id.textview_shopname4);
		textview_shopname4_msg = (TextView) v.findViewById(R.id.textview_shopname4_msg);
		textview_publisher = (TextView) v.findViewById(R.id.textview_publisher);
		textview_publisher1_msg = (TextView) v.findViewById(R.id.textview_publisher1_msg);
		textview_publisher22_tip = (TextView) v.findViewById(R.id.textview_publisher22_tip);
		textview_home_tip3 = (TextView) v.findViewById(R.id.textview_home_tip3);
		textview_best_detail2 = (TextView) v.findViewById(R.id.textview_best_detail2);
		textview_recommend = (TextView) v.findViewById(R.id.textview_recommend);
		Rl_hot = (RelativeLayout) v.findViewById(R.id.Rl_hot);
		Rl_new = (RelativeLayout) v.findViewById(R.id.Rl_new);
		et_component_search = (EditText) v.findViewById(R.id.et_component_search);
		ll_editor = (LinearLayout) v.findViewById(R.id.ll_editor);
		ll_shop1 = (LinearLayout) v.findViewById(R.id.ll_shop1);
		ll_shop2 = (LinearLayout) v.findViewById(R.id.ll_shop2);
		ll_shop3 = (LinearLayout) v.findViewById(R.id.ll_shop3);
		ll_shop4 = (LinearLayout) v.findViewById(R.id.ll_shop4);
		ll_lookmore = (LinearLayout) v.findViewById(R.id.ll_lookmore);
		View sell_lookmore = v.findViewById(R.id.sell_lookmore);
		ll_publisher1 = (LinearLayout) v.findViewById(R.id.ll_publisher1);
		rl_publisher2 = (RelativeLayout) v.findViewById(R.id.rl_publisher2);
		rl_publisher3 = (RelativeLayout) v.findViewById(R.id.rl_publisher3);
		ll_publisher4 = (LinearLayout) v.findViewById(R.id.ll_publisher4);
		rl_group = (RelativeLayout) v.findViewById(R.id.rl_group);
		rl_group.setOnClickListener(this);
		ll_loginorexit.setOnClickListener(this);
		Rl_hot.setOnClickListener(this);
		Rl_new.setOnClickListener(this);
		ll_lookmore.setOnClickListener(this);
		sell_lookmore.setOnClickListener(this);
		ll_editor.setOnClickListener(this);
		imageview_disccount1.setOnClickListener(this);
		imageview_disccount2.setOnClickListener(this);
		imageview_disccount3.setOnClickListener(this);
		imageview_disccount4.setOnClickListener(this);
		imageview_shop_left.setOnClickListener(this);
		ll_shop1.setOnClickListener(this);
		ll_shop2.setOnClickListener(this);
		ll_shop3.setOnClickListener(this);
		ll_shop4.setOnClickListener(this);
		ll_publisher1.setOnClickListener(this);
		rl_publisher2.setOnClickListener(this);
		rl_publisher3.setOnClickListener(this);
		ll_publisher4.setOnClickListener(this);
		imageview_adv1.setOnClickListener(this);
		imageview_adv2.setOnClickListener(this);
		imageview_adv3.setOnClickListener(this);
		pullToRefreshScrollView = (PullToRefreshScrollView) v.findViewById(R.id.pull_scrollView);
		pullToRefreshScrollView.setAnimation(false);
		pullToRefreshScrollView.setOnScroll(new onScroll() {

			@Override
			public void onScrollListener(int scrollY) {
				float yPositon = DensityUtil.dp2px(context, 200);
				if (scrollY < 0) {
					// layout_title.setVisibility(View.GONE);
				} else {
					// layout_title.setVisibility(View.VISIBLE);
					if (scrollY > yPositon) {
						layout_title.setBackgroundResource(R.color.tv_red_home);
						imageview_scancode.setImageResource(R.drawable.home_scan_white);
						et_component_search.setTextColor(getResources().getColor(R.color.white));
						if (MyApplication.mapp.getCookieStore() != null) {
							iv_loginorexit.setImageResource(R.drawable.home_exit_white);
						} else {
							iv_loginorexit.setImageResource(R.drawable.home_login_white);
						}
					} else {
						// 设置透明度渐变
						layout_title.setBackgroundResource(R.color.tv_red_home);
						int alpha = (int) (scrollY / yPositon * 255);
						layout_title.getBackground().setAlpha(alpha);
						imageview_scancode.setImageResource(R.drawable.home_scan_black);
						et_component_search.setTextColor(getResources().getColor(R.color.common_light_gray));
						if (MyApplication.mapp.getCookieStore() != null) {
							iv_loginorexit.setImageResource(R.drawable.home_exit_black);
						} else {
							iv_loginorexit.setImageResource(R.drawable.home_login_black);
						}
					}
				}
			}
		});
		pullToRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				// getEightArea();
				// layout_title.setVisibility(View.GONE);
				sendhttps();
				pullToRefreshScrollView.onRefreshComplete();
			}
		});
		LoginOrRegister();
	}

	public void LoginOrRegister() {
		if (MyApplication.mapp.getCookieStore() != null) {
			iv_loginorexit.setImageResource(R.drawable.home_exit_black);
		} else {
			iv_loginorexit.setImageResource(R.drawable.home_login_black);
		}
	}

	@Override
	public void onResume() {
		LoginOrRegister();
		super.onResume();
	}

	private void initBestSold(View v) {
		// 获取TabHost对象
		tabHost = (TabHost) v.findViewById(R.id.tabhost);
		// 如果没有继承TabActivity时，通过该种方法加载启动tabHost
		tabHost.setup();
		mLinearLayoutViewGood = (LinearLayout) v.findViewById(R.id.linearLayout_good);
		mHorizontalListViewGood = (PullToRefreshHorizontalScrollView) v.findViewById(R.id.horizontallistview_good);
		// 获取TabHost对象
		tabHost = (TabHost) v.findViewById(R.id.tabhost);
		// 如果没有继承TabActivity时，通过该种方法加载启动tabHost
		tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				int index = Integer.parseInt(tabId);
				List<AppClientModuleObjectVo> list = mHomeBooks[index];
				addView(mHorizontalListViewGood, mLinearLayoutViewGood, list, null);
				mHorizontalListViewGood.scrollTo(0, 0);
				mSellWellIndex = index;
			}
		});
	}

	public void goToActivity(AppClientModuleVo appClientModuleVo, int moduleIndex) {
		if (appClientModuleVo == null) {
			return;
		}
		List<AppClientModuleObjectVo> moduleObjects = appClientModuleVo.getModuleObjects();
		if (moduleObjects == null || moduleObjects.size() <= moduleIndex) {
			return;
		}
		AppClientModuleObjectVo objectVo = moduleObjects.get(moduleIndex);
		String action = objectVo.getActionType();
		if (TextUtils.isEmpty(action)) {
			return;
		}
		int code = 0;
		try {
			code = Integer.parseInt(action.trim());
		} catch (Exception e) {
			return;
		}
		switch (code) {
		/*
		 * //0 - 无效果 case 0: Intent intent = new Intent(getActivity(),
		 * AdvActivity.class); intent.putExtra("openUrl",
		 * appClientModuleVo.getModuleObjects().get(moduleIndex).getOpenUrl());
		 * getActivity().startActivity(intent); break;
		 */
		// 1 - 打开商品
		case 1:
			Intent intent = new Intent(getActivity(), BookDetailActivity.class);
			intent.putExtra("ProductId", objectVo.getProduct().getProductId() + "");
			getActivity().startActivity(intent);
			break;
		// 2 - 打开主题
		case 2:
			break;
		// 3 - 打开链接
		case 3:
			intent = new Intent(getActivity(), WebViewActivity.class);
			intent.putExtra("openUrl", objectVo.getOpenUrl());
			getActivity().startActivity(intent);
			break;
		// 4 - 抢购
		case 4:
			break;
		// 5 - 团购
		case 5:
			break;
		// 6 - 订单列表
		case 6:
			break;
		// 7 - 积分商品
		case 7:
			break;
		// 8 - 店铺
		case 8:
			intent = new Intent(getActivity(), ShopActivity.class);
			intent.putExtra("shopId", objectVo.getTargetObjectId() + "");
			getActivity().startActivity(intent);
			break;
		}
	}

	private static final int DAY = 86400;
	private static final String TIME_DONE = "00:00:00";
	private static final int HOUR = 3600;
	private static final int MINUTE = 60;

	private static int getTime(String src) {
		try {
			return Integer.parseInt(src);
		} catch (Exception e) {
			return 0;
		}
	}

	private static String getTime(int time) {
		time = time / 1000;
		if (time <= 0) {
			return TIME_DONE;
		}
		StringBuffer sBuffer = new StringBuffer();
		int day = time / DAY;
		if (day > 0) {
			sBuffer.append(day + "天");
		}
		int hour = (time % DAY) / HOUR;
		int min = (time % HOUR) / MINUTE;
		int second = time % MINUTE;
		sBuffer.append(getTimeSrc(hour));
		sBuffer.append(":");
		sBuffer.append(getTimeSrc(min));
		sBuffer.append(":");
		sBuffer.append(getTimeSrc(second));
		return sBuffer.toString();
	}

	private static String getTimeSrc(int time) {
		if (time < 10) {
			return "0" + time;
		}
		return time + "";
	}

	private void changeDefaultTab() {
		try {
			Field field = TabHost.class.getDeclaredField("mCurrentTab");
			field.setAccessible(true);
			field.setInt(tabHost, -1);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private void initGridView(View v) {
		gridview_recommend = (MyGridView) v.findViewById(R.id.gridview_recommend);
		gridview_home = (MyGridView) v.findViewById(R.id.gridview_home);
	}

	private void setListener() {
		et_component_search.setOnClickListener(this);
		imageview_scancode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 二维码扫描
				startActivity(new Intent(getActivity(), CaptureActivity.class));
			}
		});
	}

	/**
	 * 初始化首页的大图轮播
	 */
	private void initViewPager(View v) {
		ViewGroup group = (ViewGroup) v.findViewById(R.id.viewGroup);
		viewPager = (ViewPager) v.findViewById(R.id.viewPager);
		group.removeAllViews();
		// 载入图片资源ID
		imgIdArray = new String[picList.size()];
		for (int i = 0; i < picList.size(); i++) {
			imgIdArray[i] = picList.get(i);
		}
		// 将点点加入到ViewGroup中
		tips = new ImageView[imgIdArray.length];
		for (int i = 0; i < tips.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			imageView.setLayoutParams(new LayoutParams(10, 10));
			tips[i] = imageView;
			if (i == 0) {
				tips[i].setBackgroundResource(R.drawable.common_tips_selected_icon);
			} else {
				tips[i].setBackgroundResource(R.drawable.common_tips_unselect_icon);
			}
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			layoutParams.leftMargin = 5;
			layoutParams.rightMargin = 5;
			group.addView(imageView, layoutParams);
		}
		// 将图片装载到数组中
		mImageViews = new ImageView[imgIdArray.length];
		for (int i = 0; i < mImageViews.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			imageView.setScaleType(ScaleType.FIT_XY);
			mImageViews[i] = imageView;
			String url;
			if (!imgIdArray[i].equals("")) {
				url = imgIdArray[i];
			} else {
				url = "http://asfgsf";
			}
			BitmapUtil util = new BitmapUtil();
			util.displayImage(getActivity(), imageView, url);
		}
		// 设置Adapter
		viewPager.setAdapter(new MyAdapter());
		viewPager.setFocusable(true);
		// 设置监听，主要是设置点点的背景
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// if ( position < 1) { //首位之前，跳转到末尾（N）
				// position = mImageViews.length;
				// viewPager.setCurrentItem(position,false);
				// } else if ( position > mImageViews.length) { //末位之后，跳转到首位（1）
				// viewPager.setCurrentItem(1,false); //false:不显示跳转过程的动画
				// position = 1;
				// }
				setImageBackground(position % mImageViews.length);
				currentItem = position;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	public class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mImageViews.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(mImageViews[position % mImageViews.length]);
		}

		/**
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
		 */
		@Override
		public Object instantiateItem(View container, final int position) {
			try {
				((ViewPager) container).addView(mImageViews[position], 0);
				mImageViews[position].getDrawable();
				mImageViews[position].setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						goToActivity(mPageAppClientModuleVo, position);
					}
				});
			} catch (Exception e) {
				// handler something
			}
			return mImageViews[position];
		}
	}

	/**
	 * 设置选中的tip的背景
	 * 
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems) {
				tips[i].setBackgroundResource(R.drawable.common_tips_selected_icon);
			} else {
				tips[i].setBackgroundResource(R.drawable.common_tips_unselect_icon);
			}
		}
	}

	public void onShowDetail(AppClientModuleObjectVo appClientModuleObjectVo, String titleSrc) {
		Intent intente = new Intent(context, ProductListActivity.class);
		if (appClientModuleObjectVo.getProduct().getSkuList() != null) {
			intente.putExtra("skuId", appClientModuleObjectVo.getProduct().getSkuList().get(0).getSkuId() + "");
		}
		intente.putExtra("titleType", "0");
		intente.putExtra("title", titleSrc);
		context.startActivity(intente);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isrunning = false;
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Rl_hot:
			Intent inthot = new Intent(getActivity(), ProductListActivity.class);
			inthot.putExtra("title", "精品热销");
			inthot.putExtra("titleType", "2");
			getActivity().startActivity(inthot);
			break;
		case R.id.ll_lookmore:
			Intent intmore = new Intent(getActivity(), ProductListActivity.class);
			intmore.putExtra("title", textview_time_buy.getText().toString());
			intmore.putExtra("titleType", "0");
			getActivity().startActivity(intmore);
			break;
		case R.id.sell_lookmore:
			intmore = new Intent(getActivity(), HotProductActivity.class);
			intmore.putExtra("sellWells", mSellWells);
			intmore.putExtra("sellWellIndex", mSellWellIndex);
			getActivity().startActivity(intmore);
			break;
		case R.id.Rl_new:
			Intent intnew = new Intent(getActivity(), NewProductActivity.class);
			intnew.putExtra("type", "1");
			getActivity().startActivity(intnew);
			break;
		case R.id.rl_group:
			goToActivity(mGroupModuleVo, 0);
			/*
			 * Intent intgroup = new Intent(getActivity(),
			 * GroupBuyActivity.class); intgroup.putExtra("type", "group");
			 * getActivity().startActivity(intgroup);
			 */
			break;
		case R.id.et_component_search:
			Intent intsearch = new Intent(getActivity(), SearchActivity.class);
			intsearch.putExtra("search", et_component_search.getText().toString());
			getActivity().startActivity(intsearch);
			break;
		case R.id.imageview_disccount1:
			goToActivity(mDisAppClientModuleVo[0], 0);
			break;
		case R.id.imageview_disccount2:
			goToActivity(mDisAppClientModuleVo[1], 0);
			break;
		case R.id.imageview_disccount3:
			goToActivity(mDisAppClientModuleVo[2], 0);
			break;
		case R.id.imageview_disccount4:
			goToActivity(mDisAppClientModuleVo[3], 0);
			break;
		case R.id.imageview_shop_left:
			Intent shops1 = new Intent(getActivity(), ShopActivity.class);
			shops1.putExtra("shopId", shop1);
			getActivity().startActivity(shops1);
			break;
		case R.id.ll_shop1:
			Intent shops2 = new Intent(getActivity(), ShopActivity.class);
			shops2.putExtra("shopId", shop2);
			getActivity().startActivity(shops2);
			break;
		case R.id.ll_shop2:
			Intent shops3 = new Intent(getActivity(), ShopActivity.class);
			shops3.putExtra("shopId", shop3);
			getActivity().startActivity(shops3);
			break;
		case R.id.ll_shop3:
			Intent shops4 = new Intent(getActivity(), ShopActivity.class);
			shops4.putExtra("shopId", shop4);
			getActivity().startActivity(shops4);
			break;
		case R.id.ll_shop4:
			Intent shops5 = new Intent(getActivity(), ShopActivity.class);
			shops5.putExtra("shopId", shop5);
			getActivity().startActivity(shops5);
			break;
		case R.id.ll_editor:
			goToActivity(mPulisherModuleVo[0], 0);
			break;
		case R.id.ll_publisher1:
			goToActivity(mPulisherModuleVo[1], 0);
			break;
		case R.id.rl_publisher2:
			goToActivity(mPulisherModuleVo[2], 0);
			break;
		case R.id.rl_publisher3:
			goToActivity(mPulisherModuleVo[3], 0);
			break;
		case R.id.ll_publisher4:
			goToActivity(mPulisherModuleVo[4], 0);
			break;
		// mPulisherModuleVo
		// Intent shopOpenUrl = new Intent(getActivity(),
		// AdvActivity.class);
		// shopOpenUrl.putExtra("openUrl", openUrl);
		// getActivity().startActivity(shopOpenUrl);
		// ToastUtil.show(getActivity(), "正在维护中。。");
		// case R.id.ll_product1:
		// Intent in1 = new Intent(getActivity(), BookDetailActivity.class);
		// in1.putExtra("ProductId", p1);
		// getActivity().startActivity(in1);
		// break;
		// case R.id.ll_product2:
		// // 137342
		// Intent in2 = new Intent(getActivity(), BookDetailActivity.class);
		// in2.putExtra("ProductId", p2);
		// getActivity().startActivity(in2);
		// break;
		// case R.id.ll_product3:// 137664
		// Intent in3 = new Intent(getActivity(), BookDetailActivity.class);
		// in3.putExtra("ProductId", p3);
		// getActivity().startActivity(in3);
		// break;
		// case R.id.ll_product4:// 137541
		// Intent in4 = new Intent(getActivity(), BookDetailActivity.class);
		// in4.putExtra("ProductId", p4);
		// getActivity().startActivity(in4);
		// break;
		// case R.id.rl_viewGroup:
		// startActivity(AdvertisingActivity.class);
		// break;
		case R.id.imageview_adv1:
			goToActivity(mAdAppClientModuleVo[0], 0);
			break;
		case R.id.imageview_adv2:
			goToActivity(mAdAppClientModuleVo[1], 0);
			break;
		case R.id.imageview_adv3:
			goToActivity(mAdAppClientModuleVo[2], 0);
			break;
		case R.id.ll_loginorexit:
			if (MyApplication.mapp.getCookieStore() != null) {
				final CommonAlertDialog dialog = new CommonAlertDialog(getActivity());
				dialog.showYesOrNoDialog("是否退出登录？", new OnClickListener() {

					@Override
					public void onClick(View v) {
						loginout();
						dialog.dismiss();
					}
				}, new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
			} else {
				startActivity(LoginActivity.class);
			}
			break;
		}
	}

	/**
	 * 退出登录
	 */
	public void loginout() {
		BaseTask baseTask = new BaseTask(getActivity());
		baseTask.askCookieRequest(SystemConfig.LOGOUT, null, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("退出成功", arg0.result.toString());
				try {
					org.json.JSONObject jsonObject = new org.json.JSONObject(arg0.result);
					if (jsonObject.getBoolean("success")) {
						MyApplication.mapp.clear();
						showToast("退出成功");
						LoginOrRegister();
						((MainActivity) getActivity()).setCurrent(4);
						((MainActivity) getActivity()).setCartCount("0", false);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("退出成功异常", arg0.toString());
				showToast("退出失败");
			}
		});
	}

	private void sendhttps() {
		IgnitedDiagnosticsUtils util = new IgnitedDiagnosticsUtils();
		@SuppressWarnings("static-access")
		String version = util.getApplicationVersionString(getActivity());
		showDialog("正在请求首页数据，请稍后...");
		BaseTask task = new BaseTask(getActivity());
		RequestParams params = new RequestParams();
		params.addBodyParameter("networktype", NetWorkUtils.getCurrentNetType(getActivity()));
		params.addBodyParameter("platformDeviceTypeCode", "aos-hand");
		params.addBodyParameter("appversion", version.replace("v", ""));
		task.askNormalRequest(SystemConfig.INDEX, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				JSONObject object;
				try {
					object = new JSONObject(arg0.result);
					doHomeBookTab(object);
					homeList = JSON.parseArray(object.getString("result").toString(), AppClientModuleVo.class);
					if (homeList != null && homeList.size() != 0) {
						for (int i = 0; i < homeList.size(); i++) {
							if ("bxw_logo".equals(homeList.get(i).getInnerCode())) {// 北新头条logo
								if (homeList.get(i).getModuleObjects().size() != 0) {
									// String url =
									// homeList.get(i).getModuleObjects().get(0).getPicUrl();
									// BitmapUtil util = new
									// BitmapUtil();
									// util.displayImage(getActivity(),
									// imageview_news, url);
								}
							} else if ("bxw_search".equals(homeList.get(i).getInnerCode())) {// 北新搜索
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									et_component_search.setText(group);
								}
							} else if ("bxw_msg_content".equals(homeList.get(i).getInnerCode())) {// 北新新闻
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String[] texts = new String[homeList.get(i).getModuleObjects().size()];
									for (int j = 0; j < texts.length; j++) {
										texts[j] = homeList.get(i).getModuleObjects().get(j).getTitle();
									}
									mNewModuleVo = homeList.get(i);
									initViewFlipper(texts);
								}
							} else if ("bxw_banner".equals(homeList.get(i).getInnerCode())) {// 轮播大图
								mPageAppClientModuleVo = homeList.get(i);
								List<AppClientModuleObjectVo> module = homeList.get(i).getModuleObjects();
								picList.clear();
								for (int j = 0; j < module.size(); j++) {
									picList.add(module.get(j).getPicUrl());
								}
								if (picList != null && picList.size() != 0) {
									initViewPager(v);
								}
							} else if ("bxw_floor0_channel".equals(homeList.get(i).getInnerCode())) {
								listAreas = homeList.get(i).getModuleObjects();
								CommonAdapter<AppClientModuleObjectVo> adapter = new CommonAdapter<AppClientModuleObjectVo>(
										getActivity(), listAreas, R.layout.item_home_gridview) {

									@Override
									public void convert(ViewHolder helper, AppClientModuleObjectVo item) {
										helper.setText(R.id.textview_home_gridview,
												item.getTitle().substring(0, item.getTitle().indexOf("-")));
										helper.displayImage(R.id.imageview_home_gridview, item.getPicUrl());
									}
								};
								gridview_home.setAdapter(adapter);
								gridview_home.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
										String title = "";
										if (listAreas != null && listAreas.get(position) != null
												&& "0".equals(listAreas.get(position).getActionType())) {
											title = listAreas.get(position).getTitle();
										}
										Intent intent = new Intent(getActivity(), EightAreasActivity.class);
										Bundle bundle = new Bundle();
										bundle.putString("name", title);
										bundle.putString("categoryId",
												title.substring(title.indexOf("-") + 1, title.length()));
										intent.putExtras(bundle);
										getActivity().startActivity(intent);
									}
								});
							} else if ("bxw_floor1_groupon_title".equals(homeList.get(i).getInnerCode())) {// 北新团购
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_group_title.setText(group);
								}
							} else if ("bxw_floor1_groupon_name".equals(homeList.get(i).getInnerCode())) {// 北新团购
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_home_tip1.setText(group);
								}
							} else if ("bxw_floor1_groupon_time".equals(homeList.get(i).getInnerCode())) {// 北新团购
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									int time = getTime(group);
									textview_home_time.setText(getTime(time));
									if (mCountDownTimer != null) {
										mCountDownTimer.cancel();
										mCountDownTimer = null;
									}
									mCountDownTimer = new CountDownTimer(time, 1000) {

										public void onTick(long millisUntilFinished) {
											textview_home_time.setText(getTime((int) millisUntilFinished));
										}

										public void onFinish() {
											textview_home_time.setText(TIME_DONE);
										}
									}.start();
								}
							} else if ("bxw_floor1_groupon_content".equals(homeList.get(i).getInnerCode())) {// 北新团购
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_group, url);
									mGroupModuleVo = homeList.get(i);
								}
							} else if ("bxw_floor1_newbook_title".equals(homeList.get(i).getInnerCode())) {// 新品上架
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_new_book.setText(group);
								}
							} else if ("bxw_floor1_newbook_name".equals(homeList.get(i).getInnerCode())) {// 新品上架
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_home_tip2.setText(group);
								}
							} else if ("bxw_floor1_newbook_content".equals(homeList.get(i).getInnerCode())) {// 新品上架
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_newpro, url);
								}
							} else if ("bxw_floor1_hotsale_title".equals(homeList.get(i).getInnerCode())) {// 精品热销
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_best_sold.setText(group);
								}
							} else if ("bxw_floor1_hotsale_name".equals(homeList.get(i).getInnerCode())) {// 精品热销
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_best_detail.setText(group);
								}
							} else if ("bxw_floor1_hotsale_content".equals(homeList.get(i).getInnerCode())) {// 精品热销
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_hotsold, url);
								}
							} else if ("bxw_floor1_editors_title".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_editor.setText(group);
								}
							} else if ("bxw_floor1_editors_content".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									mPulisherModuleVo[0] = homeList.get(i);
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_editor, url);
									openUrl = homeList.get(i).getModuleObjects().get(0).getOpenUrl();
								}
							} else if ("bxw_floor2_flash_title".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_time_buy.setText(group);
								}
							} else if ("bxw_floor2_flash_content".equals(homeList.get(i).getInnerCode())) {// 限时抢购
								List<AppClientModuleObjectVo> module = homeList.get(i).getModuleObjects();
								if (module != null) {
									addView(mHorizontalListViewRush, mLinearLayoutViewRush, module,
											textview_time_buy.getText().toString());
								}
							} else if ("bxw_floor2_adv".equals(homeList.get(i).getInnerCode())) {// 广告一
								if (homeList.get(i).getModuleObjects().size() != 0) {
									mAdAppClientModuleVo[0] = homeList.get(i);
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_adv1, url);
								}
							} else if ("bxw_floor3_hotsales_title".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_hot_sale.setText(group);
								}
							} else if ("bxw_floor3_hotsales_content".equals(homeList.get(i).getInnerCode())) {// 限时抢购
								/*
								 * List<AppClientModuleObjectVo> module =
								 * homeList.get(i).getModuleObjects(); if
								 * (module != null) { HorizontalListView hlv2 =
								 * (HorizontalListView) v.findViewById(R.
								 * id.horizontallistview2); hlva2 = new
								 * HorizontalListViewAdapter (getActivity());
								 * hlva2.setData(module);
								 * hlv2.setAdapter(hlva2); }
								 */
							} else if ("bxw_floor3_adv1".equals(homeList.get(i).getInnerCode())) {// 广告一
								if (homeList.get(i).getModuleObjects().size() != 0) {
									mDisAppClientModuleVo[0] = homeList.get(i);
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_disccount1, url);
									if (homeList.get(i).getModuleObjects() != null) {
										if ("1".equals(homeList.get(i).getModuleObjects().get(0).getActionType())) {
											if (homeList.get(i).getModuleObjects().get(0).getTargetObjectId() != null) {
												pid1 = homeList.get(i).getModuleObjects().get(0).getTargetObjectId()
														.toString();
											}
										}
									}
								}
							} else if ("bxw_floor3_adv2".equals(homeList.get(i).getInnerCode())) {// 广告一
								if (homeList.get(i).getModuleObjects().size() != 0) {
									mDisAppClientModuleVo[1] = homeList.get(i);
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_disccount2, url);
									if (homeList.get(i).getModuleObjects() != null) {
										if ("1".equals(homeList.get(i).getModuleObjects().get(0).getActionType())) {
											if (homeList.get(i).getModuleObjects().get(0).getTargetObjectId() != null) {
												pid2 = homeList.get(i).getModuleObjects().get(0).getTargetObjectId()
														.toString();
											}
										}
									}
								}
							} else if ("bxw_floor3_adv3".equals(homeList.get(i).getInnerCode())) {// 广告一
								if (homeList.get(i).getModuleObjects().size() != 0) {
									mDisAppClientModuleVo[2] = homeList.get(i);
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_disccount3, url);
									if (homeList.get(i).getModuleObjects() != null) {
										if ("1".equals(homeList.get(i).getModuleObjects().get(0).getActionType())) {
											if (homeList.get(i).getModuleObjects().get(0).getTargetObjectId() != null) {
												pid3 = homeList.get(i).getModuleObjects().get(0).getTargetObjectId()
														.toString();
											}
										}
									}
								}
							} else if ("bxw_floor3_adv4".equals(homeList.get(i).getInnerCode())) {// 广告一
								if (homeList.get(i).getModuleObjects().size() != 0) {
									mDisAppClientModuleVo[3] = homeList.get(i);
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_disccount4, url);
									if (homeList.get(i).getModuleObjects() != null) {
										if ("1".equals(homeList.get(i).getModuleObjects().get(0).getActionType())) {
											if (homeList.get(i).getModuleObjects().get(0).getTargetObjectId() != null) {
												pid4 = homeList.get(i).getModuleObjects().get(0).getTargetObjectId()
														.toString();
											}
										}
									}
								}
							} else if ("bxw_floor4_shop_title".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_good_shop.setText(group);
								}
							} else if ("bxw_floor4_shopleft".equals(homeList.get(i).getInnerCode())) {// 广告一
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_shop_left, url);
									if (homeList.get(i).getModuleObjects() != null) {
										if ("8".equals(homeList.get(i).getModuleObjects().get(0).getActionType())) {
											if (homeList.get(i).getModuleObjects().get(0).getTargetObjectId() != null) {
												shop1 = homeList.get(i).getModuleObjects().get(0).getTargetObjectId()
														.toString();
											}
										}
									}
								}
							} else if ("bxw_floor4_shopright1_shopName".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_shopname1.setText(group);
								}
							} else if ("bxw_floor4_shopright1_msg".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_shopname1_msg.setText(group);
								}
							} else if ("bxw_floor4_shopright1_content".equals(homeList.get(i).getInnerCode())) {// 广告一
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_shop1, url);
									if (homeList.get(i).getModuleObjects() != null) {
										if ("8".equals(homeList.get(i).getModuleObjects().get(0).getActionType())) {
											if (homeList.get(i).getModuleObjects().get(0).getTargetObjectId() != null) {
												shop2 = homeList.get(i).getModuleObjects().get(0).getTargetObjectId()
														.toString();
											}
										}
									}
								}
							} else if ("bxw_floor4_shopright2_shopName".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_shopname2.setText(group);
								}
							} else if ("bxw_floor4_shopright2_msg".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_shopname2_msg.setText(group);
								}
							} else if ("bxw_floor4_shopright2_content".equals(homeList.get(i).getInnerCode())) {// 广告一
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_shop2, url);
									if (homeList.get(i).getModuleObjects() != null) {
										if ("8".equals(homeList.get(i).getModuleObjects().get(0).getActionType())) {
											if (homeList.get(i).getModuleObjects().get(0).getTargetObjectId() != null) {
												shop3 = homeList.get(i).getModuleObjects().get(0).getTargetObjectId()
														.toString();
											}
										}
									}
								}
							} else if ("bxw_floor4_shopright3_shopName".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_shopname3.setText(group);
								}
							} else if ("bxw_floor4_shopright3_msg".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_shopname3_msg.setText(group);
								}
							} else if ("bxw_floor4_shopright3_content".equals(homeList.get(i).getInnerCode())) {// 广告一
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_shop3, url);
									if (homeList.get(i).getModuleObjects() != null) {
										if ("8".equals(homeList.get(i).getModuleObjects().get(0).getActionType())) {
											if (homeList.get(i).getModuleObjects().get(0).getTargetObjectId() != null) {
												shop4 = homeList.get(i).getModuleObjects().get(0).getTargetObjectId()
														.toString();
											}
										}
									}
								}
							} else if ("bxw_floor4_shopright4_shopName".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_shopname4.setText(group);
								}
							} else if ("bxw_floor4_shopright4_msg".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_shopname4_msg.setText(group);
								}
							} else if ("bxw_floor4_shopright4_content".equals(homeList.get(i).getInnerCode())) {// 广告一
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_shop4, url);
									if (homeList.get(i).getModuleObjects() != null) {
										if ("8".equals(homeList.get(i).getModuleObjects().get(0).getActionType())) {
											if (homeList.get(i).getModuleObjects().get(0).getTargetObjectId() != null) {
												shop5 = homeList.get(i).getModuleObjects().get(0).getTargetObjectId()
														.toString();
											}
										}
									}
								}
							} else if ("bxw_floor4_adv".equals(homeList.get(i).getInnerCode())) {// 广告一
								if (homeList.get(i).getModuleObjects().size() != 0) {
									mAdAppClientModuleVo[1] = homeList.get(i);
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_adv2, url);
								}
							} else if ("bxw_floor5_publisher_title".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_publisher.setText(group);
								}
							} else if ("bxw_floor5_publisherleft_logo".equals(homeList.get(i).getInnerCode())) {// 广告一
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_publisher1, url);
								}
							} else if ("bxw_floor5_publisherleft_name".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_publisher1_msg.setText(group);
								}
							} else if ("bxw_floor5_publisherleft_content".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									mPulisherModuleVo[1] = homeList.get(i);
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_publisher1_content, url);
								}
							} else if ("bxw_floor5_publisherright1_logo".equals(homeList.get(i).getInnerCode())) {// 广告一
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), textview_new_book2, url);
								}
							} else if ("bxw_floor5_publisherright1_name".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_home_tip3.setText(group);
								}
							} else if ("bxw_floor5_publisherright1_content".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									mPulisherModuleVo[2] = homeList.get(i);
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_time_best2, url);
								}
							} else if ("bxw_floor5_publisherright21_logo".equals(homeList.get(i).getInnerCode())) {// 广告一
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), textview_best_sold2, url);
								}
							} else if ("bxw_floor5_publisherright21_name".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_best_detail2.setText(group);
								}
							} else if ("bxw_floor5_publisherright21_content".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									mPulisherModuleVo[3] = homeList.get(i);
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_publisher21, url);
								}
							} else if ("bxw_floor5_publisherright22_logo".equals(homeList.get(i).getInnerCode())) {// 广告一
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_publisher22_logo, url);
								}
							} else if ("bxw_floor5_publisherright22_name".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_publisher22_tip.setText(group);
								}
							} else if ("bxw_floor5_publisherright22_content".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									mPulisherModuleVo[4] = homeList.get(i);
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_publisher22_content, url);
								}
							} else if ("bxw_floor5_adv".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									mAdAppClientModuleVo[2] = homeList.get(i);
									String url = homeList.get(i).getModuleObjects().get(0).getPicUrl();
									BitmapUtil util = new BitmapUtil();
									util.displayImage(getActivity(), imageview_adv3, url);
								}
							} else if ("bxw_floor6_recommend_title".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									String group = homeList.get(i).getModuleObjects().get(0).getTitle();
									textview_recommend.setText(group);
								}
							} else if ("bxw_floor6_recommend".equals(homeList.get(i).getInnerCode())) {// 主编圈子
								if (homeList.get(i).getModuleObjects().size() != 0) {
									listRecommend = homeList.get(i).getModuleObjects();
									CommonAdapter<AppClientModuleObjectVo> adapter = new CommonAdapter<AppClientModuleObjectVo>(
											getActivity(), listRecommend, R.layout.item_recommend_gridview) {

										@Override
										public void convert(ViewHolder helper, AppClientModuleObjectVo item) {
											helper.setText(R.id.textview_recommend1, item.getProduct().getProductNm());
											helper.displayImage(R.id.imageview_recommend1,
													item.getProduct().getImage());
										}
									};
									gridview_recommend.setAdapter(adapter);
									gridview_recommend.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(AdapterView<?> parent, View view, int position,
												long id) {
											Intent intent = new Intent(getActivity(), BookDetailActivity.class);
											intent.putExtra("ProductId",
													listRecommend.get(position).getProduct().getProductId() + "");
											getActivity().startActivity(intent);
										}
									});
								}
							}
						}
					}
					viewPager.setFocusable(true);
					viewPager.setFocusableInTouchMode(true);
					viewPager.requestFocus();
					dismissDialog();
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(getActivity(), "网络异常，请查看网络连接");
			}
		});
	}

	private void initViewFlipper(final String[] texts) {
		for (int i = 0; i < texts.length; i++) {
			TextView textView = new TextView(getActivity());
			textView.setTextColor(Color.parseColor("#252525"));
			textView.setEllipsize(TruncateAt.END);
			textView.setSingleLine();
			textView.setText(texts[i]);
			final int index = i;
			textView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					goToActivity(mNewModuleVo, index);
				}
			});
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			textview_news.addView(textView, layoutParams);
		}
		textview_news.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.view_push_up_in));
		textview_news.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.view_push_up_out));
		if (texts.length > 1) {
			textview_news.startFlipping();
		}
	}

	private void doHomeBookTab(JSONObject object) throws JSONException {
		org.json.JSONArray list = object.getJSONArray("hots_products");
		tabHost.setup();

		int size = list.length();

		int count = tabHost.getChildCount();
		TabWidget tabWidget = tabHost.getTabWidget();
		tabWidget.removeAllViews();

		mHomeBooks = new ArrayList[size];
		mSellWells = new HotProduct[size];
		for (int i = 0; i < list.length(); i++) {
			JSONObject jsonObjectPro = list.getJSONObject(i);
			String title = jsonObjectPro.getString("title");
			String pageModuleId = jsonObjectPro.getString("pageModuleId");
			mSellWells[i] = new HotProduct(title, pageModuleId);
			tabHost.addTab(tabHost.newTabSpec("" + i).setIndicator(title).setContent(R.id.view_test));
			org.json.JSONArray productslist = jsonObjectPro.getJSONArray("products");
			mHomeBooks[i] = new ArrayList<AppClientModuleObjectVo>();
			for (int j = 0; j < productslist.length(); j++) {
				JSONObject productObject = productslist.getJSONObject(j);
				AppClientModuleObjectVo aModuleObjectVo = new AppClientModuleObjectVo();
				AppProductBaseVo appProductBaseVo = new AppProductBaseVo();
				appProductBaseVo.setProductNm(productObject.getString("name"));
				appProductBaseVo.setProductId(productObject.getInt("productId"));
				// if(productObject.has("ImageLinks")){
				aModuleObjectVo.setPicUrl(productObject.getString("imageLinks"));
				/*
				 * }else{ aModuleObjectVo.setPicUrl(
				 * "http://112.33.7.44:801/upload/1/2016/2/26/9506e3df-e762-4a38-9223-c53eb60b338b.png"
				 * ); }
				 */
				if (productObject.has("imageLinks")) {
					aModuleObjectVo.setPicUrl(productObject.getString("imageLinks"));
				}
				aModuleObjectVo.setProduct(appProductBaseVo);
				mHomeBooks[i].add(aModuleObjectVo);
			}
		}
		// 获取tab页
		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			// tabWidget.getChildAt(i).getLayoutParams().height = 120;
			TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
			tv.setTextSize(15);
			tv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
			tv.setTextColor(this.getResources().getColorStateList(R.color.tv_tab_home));
		}
		changeDefaultTab();
		// 获取手机长宽
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		Log.i("test", "screenWidth=" + screenWidth);
		// 当tab页的个数大于 3时 设置tab页的长度
		if (count > 3) {
			for (int i = 0; i < count; i++) {
				final int j = i;
				// 设置单个tab的长度
				tabWidget.getChildTabViewAt(i).setMinimumWidth((int) (screenWidth / 3.5));
				// 给每个tab页添加一个点击事件
				// 不用OnClickListener 避免与HorizontalScrollView发生冲突
				tabWidget.getChildTabViewAt(i).setOnTouchListener(new OnTouchListener() {

					@SuppressLint("ClickableViewAccessibility")
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						switch (event.getAction()) {
						case MotionEvent.ACTION_UP:
							// Toast.makeText(getActivity(), j + "",
							// Toast.LENGTH_LONG).show();
							break;
						default:
							break;
						}
						return false;
					}
				});
			}
		}
		addView(mHorizontalListViewGood, mLinearLayoutViewGood, mHomeBooks[0], null);

	}

	public void addView(PullToRefreshHorizontalScrollView pullToRefreshHorizontalScrollView, ViewGroup viewGroup,
			List<AppClientModuleObjectVo> module, final String titleSrc) {
		viewGroup.removeAllViews();
		if (module == null) {
			pullToRefreshHorizontalScrollView.setMode(Mode.DISABLED);
			return;
		}

		for (AppClientModuleObjectVo appClientModuleObjectVo : module) {
			View view = getView(appClientModuleObjectVo, titleSrc);
			viewGroup.addView(view);
		}

		if (module.size() > 0) {
			pullToRefreshHorizontalScrollView.setMode(Mode.PULL_FROM_END);
			pullToRefreshHorizontalScrollView
					.setOnRefreshListener(new OnRefreshListener<android.widget.HorizontalScrollView>() {

						@Override
						public void onRefresh(PullToRefreshBase<android.widget.HorizontalScrollView> refreshView) {
							refreshView.onRefreshComplete();
							if (titleSrc == null) {
								Intent intmore = new Intent(getActivity(), HotProductActivity.class);
								intmore.putExtra("sellWells", mSellWells);
								intmore.putExtra("sellWellIndex", mSellWellIndex);
								getActivity().startActivity(intmore);
							} else {
								Intent intmore = new Intent(getActivity(), ProductListActivity.class);
								intmore.putExtra("title", textview_time_buy.getText().toString());
								intmore.putExtra("titleType", "0");
								getActivity().startActivity(intmore);
							}

						}
					});
		} else {
			pullToRefreshHorizontalScrollView.setMode(Mode.DISABLED);
		}
	}

	public View getView(final AppClientModuleObjectVo appClientModuleObjectVo, final String titleSrc) {
		View convertView = getActivity().getLayoutInflater().inflate(R.layout.horizontallistview_item, null);
		ImageView im = (ImageView) convertView.findViewById(R.id.iv_pic);
		TextView title = (TextView) convertView.findViewById(R.id.tv_name);
		String picUrl = appClientModuleObjectVo.getPicUrl();
		BitmapUtil util = new BitmapUtil();
		util.displayImage(context, im, picUrl);
		title.setText(appClientModuleObjectVo.getProduct().getProductNm());
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (titleSrc == null) {
					Intent intente = new Intent(context, BookDetailActivity.class);
					if (appClientModuleObjectVo.getProduct().getProductId() != null) {
						intente.putExtra("ProductId", appClientModuleObjectVo.getProduct().getProductId() + "");
					}
					context.startActivity(intente);
				} else {
					onShowDetail(appClientModuleObjectVo, titleSrc);
				}
			}
		});
		return convertView;
	}

}
