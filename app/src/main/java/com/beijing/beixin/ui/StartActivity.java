package com.beijing.beixin.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.UserInfoBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.activity.yindaoye.GuideFragment;
import com.beijing.beixin.ui.activity.yindaoye.MainFragment_Adapter;
import com.beijing.beixin.ui.activity.yindaoye.MyViewPager;
import com.beijing.beixin.ui.fragment.HomeFragment.MyAdapter;
import com.beijing.beixin.ui.myself.login.LoginActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.ExitApplication;
import com.beijing.beixin.utils.MD5;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class StartActivity extends FragmentActivity {

	private Handler handler = new Handler();
	private ImageView logopic;// 图片
	private MyViewPager myViewPager;
	List<Fragment> mFragments = new ArrayList<Fragment>();
	MainFragment_Adapter mainFragment_Adapter;
	private String username, password;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		// 加入全局退出队列
		ExitApplication.getInstance().addAllActivity(this);
		if (((MyApplication) getApplication()).getIsopen().equals("true")) {
			initStart();
			SharedPreferences settings = getSharedPreferences(LoginActivity.SETTING_INFOS, 0);
			username = settings.getString("name", "");
			password = settings.getString("pwd", "");
			if (!"".equals(username)) {
				logins();
			}
		} else {
			initGuide();
			initViewPager();
		}
	}

	public void logins() {
		final BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("account", username);
		params.addBodyParameter("password", MD5.MD5Encode(password));
		task.askCookieRequest(SystemConfig.LOGIN, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				JSONObject obj;
				Log.e("login", arg0.result.toString());
				try {
					obj = new JSONObject(arg0.result);
					UserInfoBean userBean = JSON.parseObject(obj.getString("result"), UserInfoBean.class);
					// http为HttpUtils实例
					DefaultHttpClient httpClient = (DefaultHttpClient) task.utils.getHttpClient();
					// 将用户cookie保存到application中
					MyApplication.mapp.setCookieStore(httpClient.getCookieStore());
					MyApplication.mapp.setUserInfoBean(userBean);
					MyApplication.mapp.setCertUserName(username);
					MyApplication.mapp.setCertPassword(password);
					Log.e("getCookieStore", httpClient.getCookieStore() + "");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("登录异常", arg0.toString());
			}
		});
	}

	private void initStart() {
		logopic = (ImageView) findViewById(R.id.logopic);// 找到需要进行动画的图片id
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(StartActivity.this, R.drawable.logo_show);// 载入动画
		logopic.startAnimation(hyperspaceJumpAnimation);// 开始动画
		// 关闭通知栏的提示
		NotificationManager mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// 关闭通知栏
		if (mManager != null) {
			mManager.cancel(0);
		}
		handler.postDelayed(new Runnable() {// 主屏幕跳动同时执行线程

			public void run() {
				startBPSActivity();// 屏幕跳转
			}
		}, 2000L);// 定义跳转屏持续时间
	}

	private void initGuide() {
		myViewPager = (MyViewPager) findViewById(R.id.content);
		mFragments.add(GuideFragment.getGuideFragment(1));
		mFragments.add(GuideFragment.getGuideFragment(2));
		mFragments.add(GuideFragment.getGuideFragment(3));
		mainFragment_Adapter = new MainFragment_Adapter(getSupportFragmentManager(), mFragments);
		myViewPager.setOffscreenPageLimit(mFragments.size());
		myViewPager.setVisibility(View.VISIBLE);
		myViewPager.setAdapter(mainFragment_Adapter);
		myViewPager.setCurrentItem(0);
	}

	/**
	 * 初始化首页的大图轮播
	 */
	private void initViewPager() {
		final ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		group.removeAllViews();
		// 载入图片资源ID
		// imgIdArray = new String[picList.size()];
		// for (int i = 0; i < picList.size(); i++) {
		// imgIdArray[i] = picList.get(i);
		// }
		// 将点点加入到ViewGroup中
		tips = new ImageView[mFragments.size()];
		for (int i = 0; i < tips.length; i++) {
			ImageView imageView = new ImageView(this);
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
		// mImageViews = new ImageView[imgIdArray.length];
		// for (int i = 0; i < mImageViews.length; i++) {
		// ImageView imageView = new ImageView(this);
		// imageView.setScaleType(ScaleType.FIT_XY);
		// mImageViews[i] = imageView;
		// String url;
		// if (!imgIdArray[i].equals("")) {
		// url = imgIdArray[i];
		// }
		// else {
		// url = "http://asfgsf";
		// }
		// BitmapUtil util = new BitmapUtil();
		// util.displayImage(this, imageView, url);
		// }
		// 设置Adapter
		// viewPager.setAdapter(new MyAdapter());
		// viewPager.setFocusable(true);
		// 设置监听，主要是设置点点的背景
		myViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// if ( position < 1) { //首位之前，跳转到末尾（N）
				// position = mImageViews.length;
				// viewPager.setCurrentItem(position,false);
				// } else if ( position > mImageViews.length) { //末位之后，跳转到首位（1）
				// viewPager.setCurrentItem(1,false); //false:不显示跳转过程的动画
				// position = 1;
				// }
				setImageBackground(position % mFragments.size());
				if (position == 2) {
					group.setVisibility(View.GONE);
				} else {
					group.setVisibility(View.VISIBLE);
				}
				// currentItem = position;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
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

	/**
	 * 屏幕跳转
	 */
	private void startBPSActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		// 打开新的Activity
		startActivity(intent);
		this.finish();
	}
}
