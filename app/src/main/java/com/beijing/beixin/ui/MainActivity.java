package com.beijing.beixin.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.VersionBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.activity.yindaoye.MainFragment_Adapter;
import com.beijing.beixin.ui.activity.yindaoye.MyViewPager;
import com.beijing.beixin.ui.base.WebViewFragment;
import com.beijing.beixin.ui.fragment.ClassifyFragment;
import com.beijing.beixin.ui.fragment.HomeFragment;
import com.beijing.beixin.ui.fragment.MySelfFragment;
import com.beijing.beixin.ui.fragment.ShoppingCartFragment;
import com.beijing.beixin.utils.CommonAlertDialog;
import com.beijing.beixin.utils.ExitApplication;
import com.beijing.beixin.utils.IgnitedDiagnosticsUtils;
import com.beijing.beixin.utils.UpdateHelper;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

@SuppressLint("CutPasteId")
public class MainActivity extends FragmentActivity implements OnClickListener {

	public long exitTime;// 储存点击退出时间
	private String version;
	private VersionBean versionBean = null;
	/**
	 * 四个图片
	 */
	private ImageView iv_home, iv_classify, iv_find, iv_cart, iv_myself;
	/**
	 * 四个文字
	 */
	private TextView tv_home, tv_classify, tv_find, tv_cart, tv_myself;
	/**
	 * 购物车数量
	 */
	private TextView cartcount;
	/**
	 * 用于展示首页的Fragment
	 */
	private HomeFragment homeFragment;
	/**
	 * 用于展示分类的Fragment
	 */
	private ClassifyFragment classifyFragment;
	/**
	 * 用于展示发现的Fragment
	 */
	private WebViewFragment findFragment;
	/**
	 * 用于展示购物车的Fragment
	 */
	private ShoppingCartFragment shoppingcartFragment;
	/**
	 * 用于展示我的的Fragment
	 */
	private MySelfFragment myselfFragment;
	/**
	 * 首页界面布局
	 */
	private RelativeLayout home_layout;
	/**
	 * 分类界面布局
	 */
	private RelativeLayout classify_layout;
	/**
	 * 发现界面布局
	 */
	private RelativeLayout find_layout;
	/**
	 * 购物车界面布局
	 */
	private RelativeLayout cart_layout;
	/**
	 * 我的界面布局
	 */
	private RelativeLayout myself_layout;
	List<Fragment> mFragments = new ArrayList<Fragment>();
	public MyViewPager mContent;
	FragmentManager mFragmentManager;
	FragmentTransaction mFragmentTransaction;
	MainFragment_Adapter mainFragment_Adapter;

	// private List<AppClientModuleObjectVo> listRecommend;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getversion();
		initView();
		initFragment();
		SetButtonOnclick();
		ExitApplication.getInstance().addAllActivity(this);// 加入全局退出队列
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (MyApplication.mapp.getCookieStore() != null) {
			getCartNum();
		} else {
			if (MyApplication.mapp.getShopNum() == 0) {
				cartcount.setVisibility(View.INVISIBLE);
				if ((MyApplication.mapp.getShopNum() >= 100)) {
					cartcount.setText("99+");
				} else {
					cartcount.setText(MyApplication.mapp.getShopNum() + "");
				}
			} else {
				cartcount.setVisibility(View.VISIBLE);
				if ((MyApplication.mapp.getShopNum() >= 100)) {
					cartcount.setText("99+");
				} else {
					cartcount.setText(MyApplication.mapp.getShopNum() + "");
				}
			}
		}
	}

	/**
	 * 初始化主界面的控件
	 */
	private void initView() {
		iv_home = (ImageView) findViewById(R.id.iv_home);
		iv_classify = (ImageView) findViewById(R.id.iv_classify);
		iv_find = (ImageView) findViewById(R.id.iv_find);
		iv_cart = (ImageView) findViewById(R.id.iv_cart);
		iv_myself = (ImageView) findViewById(R.id.iv_myself);
		tv_home = (TextView) findViewById(R.id.tv_home);
		tv_classify = (TextView) findViewById(R.id.tv_classify);
		tv_find = (TextView) findViewById(R.id.tv_find);
		tv_cart = (TextView) findViewById(R.id.tv_cart);
		tv_myself = (TextView) findViewById(R.id.tv_myself);
		cartcount = (TextView) findViewById(R.id.cartcount);
		home_layout = (RelativeLayout) findViewById(R.id.home_layout);
		classify_layout = (RelativeLayout) findViewById(R.id.classify_layout);
		find_layout = (RelativeLayout) findViewById(R.id.find_layout);
		cart_layout = (RelativeLayout) findViewById(R.id.cart_layout);
		myself_layout = (RelativeLayout) findViewById(R.id.myself_layout);
	}

	/**
	 * 初始化主页面五个fragment的方法
	 */
	public void initFragment() {
		mContent = (MyViewPager) findViewById(R.id.content);
		homeFragment = new HomeFragment();
		classifyFragment = new ClassifyFragment();
		if (SystemConfig.IS_UAT) {
			findFragment = WebViewFragment.instance("http://112.33.7.55:81/h5/discovery/discovery.html", "发现", true);
		} else {
			findFragment = WebViewFragment.instance("http://b2cmob.bxmedia.net/h5/discovery/discovery.html", "发现",
					true);
		}
		// findFragment = WebViewFragment.instance("http://m.sohu.com/", "发现",
		// true);
		shoppingcartFragment = new ShoppingCartFragment(this);
		myselfFragment = new MySelfFragment();
		mFragments.add(homeFragment);
		mFragments.add(classifyFragment);
		mFragments.add(findFragment);
		mFragments.add(shoppingcartFragment);
		mFragments.add(myselfFragment);
		mainFragment_Adapter = new MainFragment_Adapter(getSupportFragmentManager(), mFragments);
		mContent.setOffscreenPageLimit(mFragments.size());
		mContent.setAdapter(mainFragment_Adapter);
		mContent.setCurrentItem(0);
		String str = getIntent().getStringExtra("cart");
		String home_exit = getIntent().getStringExtra("home_exit");
		if ("productinfo".equals(str)) {
			shoppingcartFragment.setPoistion(3);
			classifyFragment.setPoistion(3);
			homeFragment.setPoistion(3);
			mContent.setCurrentItem(3);
			setMenuButtonBj(3);
		}
		if ("home_exit".equals(home_exit)) {
			mContent.setCurrentItem(4);
			setMenuButtonBj(4);
		}
		if ("myself".equals(str)) {
			shoppingcartFragment.setPoistion(4);
			myselfFragment.setPoistion(4);
			homeFragment.setPoistion(4);
			mContent.setCurrentItem(4);
			setMenuButtonBj(4);
		}
		mContent.setNoScroll(true);
		mContent.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * 设置点击事件
	 */
	private void SetButtonOnclick() {
		home_layout.setOnClickListener(new MyOnClickListener(0));
		classify_layout.setOnClickListener(new MyOnClickListener(1));
		find_layout.setOnClickListener(new MyOnClickListener(2));
		cart_layout.setOnClickListener(new MyOnClickListener(3));
		myself_layout.setOnClickListener(new MyOnClickListener(4));
	}

	/**
	 * 设置按钮的颜色和字体
	 * 
	 * @param arg0
	 */
	public void setMenuButtonBj(int arg0) {
		switch (arg0) {
		case 0:
			iv_home.setImageResource(R.drawable.home_red);
			iv_classify.setImageResource(R.drawable.classify_white);
			iv_find.setImageResource(R.drawable.find_white);
			iv_cart.setImageResource(R.drawable.main_cart_white);
			iv_myself.setImageResource(R.drawable.member_white);
			tv_home.setTextColor(getResources().getColor(R.color.common_dark_red));
			tv_classify.setTextColor(getResources().getColor(R.color.common_tv_black));
			tv_find.setTextColor(getResources().getColor(R.color.common_tv_black));
			tv_cart.setTextColor(getResources().getColor(R.color.common_tv_black));
			tv_myself.setTextColor(getResources().getColor(R.color.common_tv_black));
			break;
		case 1:
			iv_home.setImageResource(R.drawable.home_white);
			iv_classify.setImageResource(R.drawable.classify_red);
			iv_find.setImageResource(R.drawable.find_white);
			iv_cart.setImageResource(R.drawable.main_cart_white);
			iv_myself.setImageResource(R.drawable.member_white);
			tv_home.setTextColor(getResources().getColor(R.color.common_tv_black));
			tv_classify.setTextColor(getResources().getColor(R.color.common_dark_red));
			tv_find.setTextColor(getResources().getColor(R.color.common_tv_black));
			tv_cart.setTextColor(getResources().getColor(R.color.common_tv_black));
			tv_myself.setTextColor(getResources().getColor(R.color.common_tv_black));
			break;
		case 2:
			iv_home.setImageResource(R.drawable.home_white);
			iv_classify.setImageResource(R.drawable.classify_white);
			iv_find.setImageResource(R.drawable.find_red);
			iv_cart.setImageResource(R.drawable.main_cart_white);
			iv_myself.setImageResource(R.drawable.member_white);
			tv_home.setTextColor(getResources().getColor(R.color.common_tv_black));
			tv_classify.setTextColor(getResources().getColor(R.color.common_tv_black));
			tv_find.setTextColor(getResources().getColor(R.color.common_dark_red));
			tv_cart.setTextColor(getResources().getColor(R.color.common_tv_black));
			tv_myself.setTextColor(getResources().getColor(R.color.common_tv_black));
			break;
		case 3:
			iv_home.setImageResource(R.drawable.home_white);
			iv_classify.setImageResource(R.drawable.classify_white);
			iv_find.setImageResource(R.drawable.find_white);
			iv_cart.setImageResource(R.drawable.main_cart_red);
			iv_myself.setImageResource(R.drawable.member_white);
			tv_home.setTextColor(getResources().getColor(R.color.common_tv_black));
			tv_classify.setTextColor(getResources().getColor(R.color.common_tv_black));
			tv_find.setTextColor(getResources().getColor(R.color.common_tv_black));
			tv_cart.setTextColor(getResources().getColor(R.color.common_dark_red));
			tv_myself.setTextColor(getResources().getColor(R.color.common_tv_black));
			break;
		case 4:
			iv_home.setImageResource(R.drawable.home_white);
			iv_classify.setImageResource(R.drawable.classify_white);
			iv_find.setImageResource(R.drawable.find_white);
			iv_cart.setImageResource(R.drawable.main_cart_white);
			iv_myself.setImageResource(R.drawable.member_red);
			tv_home.setTextColor(getResources().getColor(R.color.common_tv_black));
			tv_classify.setTextColor(getResources().getColor(R.color.common_tv_black));
			tv_find.setTextColor(getResources().getColor(R.color.common_tv_black));
			tv_cart.setTextColor(getResources().getColor(R.color.common_tv_black));
			tv_myself.setTextColor(getResources().getColor(R.color.common_dark_red));
			break;
		}
	}

	private class MyOnPageChangeListener implements OnPageChangeListener {

		public void onPageSelected(int arg0) {
			Animation animation = null;
			mContent.setNoScroll(true);
			setMenuButtonBj(arg0);
			shoppingcartFragment.setPoistion(arg0);
			myselfFragment.setPoistion(arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	private class MyOnClickListener implements View.OnClickListener {

		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			mContent.setCurrentItem(index);
			if (index == 3 && MyApplication.mapp.getCookieStore() != null) {
				shoppingcartFragment.setPoistion(3);
				myselfFragment.setPoistion(3);
			}
			if (index == 4 && MyApplication.mapp.getCookieStore() != null) {
				shoppingcartFragment.setPoistion(4);
				myselfFragment.setPoistion(4);
			}
		}
	};

	/**
	 * 设置未登录时小红点的状态
	 */
	public void setVorG() {
		if (MyApplication.mapp.getCookieStore() == null) {
			if (MyApplication.mapp.getShopNum() == 0) {
				cartcount.setVisibility(View.INVISIBLE);
				if ((MyApplication.mapp.getShopNum() >= 100)) {
					cartcount.setText("99+");
				} else {
					cartcount.setText(MyApplication.mapp.getShopNum() + "");
				}
			} else {
				cartcount.setVisibility(View.VISIBLE);
				if ((MyApplication.mapp.getShopNum() >= 100)) {
					cartcount.setText("99+");
				} else {
					cartcount.setText(MyApplication.mapp.getShopNum() + "");
				}
			}
		} else if (MyApplication.mapp.getCookieStore() != null) {
			cartcount.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 获取购物车数量
	 */
	public void getCartNum() {
		BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", "normal");
		task.askCookieRequest(SystemConfig.GET_SELECTCART_NUM, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("获取购物车数量", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					if (obj.getBoolean("success") && obj.getInt("cartNum") != 0) {
						cartcount.setVisibility(View.VISIBLE);
						if (obj.getInt("cartNum") >= 100) {
							cartcount.setText("99+");
						} else {
							cartcount.setText(obj.getInt("cartNum") + "");
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("获取购物车数量异常", arg0.toString());
			}
		});
	}

	public void setCartCount(String carnum, Boolean visible) {
		if (cartcount != null) {
			if (visible == true) {
				if ("".equals(carnum) && "0".equals(carnum)) {
					cartcount.setVisibility(View.INVISIBLE);
				} else {
					cartcount.setVisibility(View.VISIBLE);
					if (Integer.parseInt(carnum) >= 100) {
						cartcount.setText("99+");
					} else {
						cartcount.setText(carnum);
					}
				}
			} else {
				cartcount.setVisibility(View.INVISIBLE);
			}
		}
	}

	@Override
	public void onClick(View v) {
	}

	/**
	 * 版本升级
	 */
	@SuppressWarnings("static-access")
	public void getversion() {
		IgnitedDiagnosticsUtils util = new IgnitedDiagnosticsUtils();
		version = util.getApplicationVersionString(this);
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("platformDeviceTypeCode", "aos-hand");
		params.addBodyParameter("versionNumber", version.replace("v", ""));
		baseTask.askCookieRequest(SystemConfig.GETSYSTEMPARAMS, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					if (TextUtils.isEmpty(arg0.result)) {
						return;
					}
					Log.e("版本更新", arg0.result.toString());
					JSONObject obj = new JSONObject(arg0.result);
					versionBean = JSON.parseObject(obj.getString("result"), VersionBean.class);
					if (versionBean != null) {
						if ("Y".equals(versionBean.getIsForcedUpdate())
								&& !version.equals("v" + versionBean.getVersionNumber())) {
							final CommonAlertDialog dialog = new CommonAlertDialog(MainActivity.this);
							dialog.showYesDialog("您的版本过低\n强制更新到最新版本！", new OnClickListener() {

								@Override
								public void onClick(View v) {
									UpdateHelper uh = new UpdateHelper(MainActivity.this);
									uh.init(versionBean.getDownLoadUrl());
									dialog.dismiss();
								}
							});
							return;
						}
						if (!version.equals("v" + versionBean.getVersionNumber())) {
							final CommonAlertDialog dialog = new CommonAlertDialog(MainActivity.this);
							dialog.showYesOrNoDialog("您的版本过低\n是否更新到最新版本？", new OnClickListener() {

								@Override
								public void onClick(View v) {
									UpdateHelper uh = new UpdateHelper(MainActivity.this);
									uh.init(versionBean.getDownLoadUrl());
									dialog.dismiss();
								}
							}, new OnClickListener() {

								@Override
								public void onClick(View v) {
									dialog.dismiss();
								}
							});
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("版本更新异常", arg0.toString());
			}
		});
	}

	/**
	 * 再按一次退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				// 全局推出
				ExitApplication.getInstance().exitAll();
				if (MyApplication.mapp.getCookieStore() != null) {
					MyApplication.mapp.clears();
				}
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void setCurrent(int index) {
		mContent.setCurrentItem(index);
		setMenuButtonBj(index);
	}
	// public void initGridView() {
	// shoppingcartFragment.initGridView();
	// myselfFragment.initGridView();
	// }
	// public List<AppClientModuleObjectVo> getListRecommend() {
	// return listRecommend;
	// }
	// public void setListRecommend(List<AppClientModuleObjectVo> listRecommend)
	// {
	// this.listRecommend = listRecommend;
	// }
}
