package com.beijing.beixin.ui.base;

import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.ShoppingCartBean;
import com.beijing.beixin.pojo.UserInfoBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.classify.ClassifyMoreProductActivity;
import com.beijing.beixin.ui.myself.login.LoginActivity;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.ui.shoppingcart.ShopActivity;
import com.beijing.beixin.ui.shoppingcart.SubmitOrderActivity;
import com.beijing.beixin.utils.LogUtil;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class WebViewFragment extends BaseFragment {

	private static final String URL = "url";
	private static final String TITLE = "title";
	private static final String IS_TOP = "is_top";
	private TextView mNavigationTitle;
	private ImageView mBackImgBtn;
	private ImageView mCloseImgBtn;
	private BridgeWebView mWebView;
	private String mUrl;
	private boolean mIsTop;

	public static WebViewFragment instance(String url, String title) {
		return instance(url, title, false);
	}

	public static WebViewFragment instance(String url, String title, boolean isTop) {
		WebViewFragment webViewFragment = new WebViewFragment();
		Bundle bundle = new Bundle();
		bundle.putString(URL, url);
		bundle.putString(TITLE, title);
		bundle.putBoolean(IS_TOP, isTop);
		webViewFragment.setArguments(bundle);
		return webViewFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		String title = getArguments().getString(TITLE);
		mUrl = getArguments().getString(URL);
		// mUrl =
		// "http://b2cmob.bxmedia.net/h5/marketing/bjtsds-49sale-dp-20160425.html";
		// mUrl =
		// "http://b2cmob.bxmedia.net/h5/marketing/wfjsd-qcmy-dp-20160425.html";
		// mUrl =
		// "http://b2cmob.bxmedia.net/h5/marketing/yycsd-qc59-dp-20160425.html";
		// mUrl =
		// "http://b2cmob.bxmedia.net/h5/marketing/tbts80sale-20160425.html";
		// mUrl =
		// "http://b2cmob.bxmedia.net/h5/marketing/kjzyts49sale-20160425.html";
		// mUrl = "http://b2cmob.bxmedia.net/h5/marketing/jpts-20160425.html";
		// mUrl = "http://b2cmob.bxmedia.net/h5/marketing/tsdsm-20160425.html";
		// mUrl = "http://b2cmob.bxmedia.net/h5/groupbuy/groupbuy.html";
		// mUrl =
		// "http://b2cmob.bxmedia.net/h5/marketing/bxwqmss-20160425.html";
		// mUrl = "http://b2cmob.bxmedia.net/h5/zhubiantuijian/recommen.html";

		// mUrl =
		// "http://192.168.1.126:8080/appPages/code/zhubiantuijian/recommen.html";
		// http://192.168.1.126:8080/appPages/code/marketing/wfjsd-qcmy-dp-20160425.html";
		// mUrl =
		// "http://192.168.1.126:8080/appPages/code/groupbuy/groupbuy.html";
		// mUrl =
		// "http://192.168.1.126:8080/appPages/code/marketing/bjtsds-49sale-dp-20160425.html";
		// mUrl =
		// "http://192.168.1.126:8080/appPages/code/marketing/bxwqmss-20160425.html";
		// mUrl =
		// "http://192.168.1.126:8080/appPages/code/marketing/jpts-20160425.html";
		// mUrl =
		// "http://192.168.1.126:8080/appPages/code/marketing/kjzyts49sale-20160425.html";
		// mUrl =
		// "http://192.168.1.126:8080/appPages/code/discovery/discovery.html";
		// mUrl =
		// "http://192.168.1.126:8080/appPages/code/marketing/tbts80sale-20160425.html";
		// mUrl =
		// "http://192.168.1.126:8080/appPages/code/marketing/tsdsm-20160425.html";
		// mUrl =
		// "http://192.168.1.126:8080/appPages/code/marketing/wfjsd-qcmy-dp-20160425.html";
		// mUrl =
		// "http://192.168.1.126:8080/appPages/code/marketing/yycsd-qc59-dp-20160425.html";
		// mUrl =
		// "http://192.168.1.126:8080/appPages/code/zhubiantuijian/recommen.html";

		mIsTop = getArguments().getBoolean(IS_TOP);

		View rootView = inflater.inflate(R.layout.fragment_webview, container, false);
		mNavigationTitle = (TextView) rootView.findViewById(R.id.navigation_title);
		if (TextUtils.isEmpty(title)) {
			mNavigationTitle.setVisibility(View.GONE);
		} else {
			mNavigationTitle.setVisibility(View.VISIBLE);
			mNavigationTitle.setText(title);
		}
		mWebView = (BridgeWebView) rootView.findViewById(R.id.webview);
		mBackImgBtn = (ImageView) rootView.findViewById(R.id.navigationLeftImageBtn);
		mBackImgBtn.setImageResource(R.drawable.title_bar_back);
		if (mBackImgBtn != null && !mIsTop) {
			mBackImgBtn.setVisibility(View.VISIBLE);
			mBackImgBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					onBackPressed();
				}
			});
		} else {
			mBackImgBtn.setVisibility(View.INVISIBLE);
		}
		mCloseImgBtn = (ImageView) rootView.findViewById(R.id.navigationRightImageBtn);
		mCloseImgBtn.setImageResource(R.drawable.comdemore);
		mCloseImgBtn.setVisibility(View.VISIBLE);
		mCloseImgBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showwindow(mCloseImgBtn);
			}
		});
		initWebView();
		return rootView;
	}

	public void onBackPressed() {
		if (mWebView.canGoBack()) {
			mWebView.goBack();
		} else if (!mIsTop) {
			getActivity().finish();
		}
	}

	private void initWebView() {
		WebSettings settings = mWebView.getSettings();
		settings.setSupportZoom(true);
		settings.setJavaScriptEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		settings.setBuiltInZoomControls(true);// support zoom
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		// 启动缓存
		mWebView.getSettings().setAppCacheEnabled(true);
		// 设置缓存模式
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		setCookie();
		// 在当前的浏览器中响应

		mWebView.setDefaultHandler(new DefaultHandler() {
		});
		mWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				mNavigationTitle.setVisibility(View.VISIBLE);
				mNavigationTitle.setText(title);
				if (mWebView.canGoBack()) {
					if (mIsTop) {
						mBackImgBtn.setVisibility(View.VISIBLE);
						mBackImgBtn.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								onBackPressed();
							}
						});
					} else {
						mCloseImgBtn.setVisibility(View.VISIBLE);
						mCloseImgBtn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								getActivity().finish();
							}
						});
					}
				} else {
					if (mIsTop) {
						mBackImgBtn.setVisibility(View.INVISIBLE);
						mBackImgBtn.setOnClickListener(null);
					} else {
						mCloseImgBtn.setVisibility(View.INVISIBLE);
						mCloseImgBtn.setOnClickListener(null);
					}

				}

			}

		});
		reSendUserInfo();
		reAddToShoppingCart();
		reBookdetai();
		reIsLogin();
		reMoreProduct();
		reShopInfo();
		reGroupBuy();
	}

	@Override
	public void onResume() {
		super.onResume();
		// 加载网页

	}

	private void setCookie() {
		CookieStore cookieStore = MyApplication.mapp.getCookieStore();
		if (cookieStore != null) {
			List<Cookie> list = cookieStore.getCookies();
			CookieManager cookieManager = CookieManager.getInstance();
			cookieManager = CookieManager.getInstance();
			cookieManager.setAcceptCookie(true);
			cookieManager.removeAllCookie();
			for (int i = 0; i < list.size(); i++) {
				Cookie cookie = list.get(i);
				cookieManager.setCookie(SystemConfig.getRequestServerUrl(), cookie.getName() + "=" + cookie.getValue());
			}
		}
		mWebView.loadUrl(mUrl);
	}

	/**
	 * 进入店铺
	 */
	private void reShopInfo() {
		mWebView.registerHandler("gotoShopInfoA", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				function.onCallBack(gotoShopInfo(data));
			}
		});
	}

	/**
	 * 团购
	 */
	private void reGroupBuy() {
		mWebView.registerHandler("groupBuyA", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				function.onCallBack(groupBuy(data));
			}
		});
	}

	/**
	 * 发送 用户信息
	 */
	private void reSendUserInfo() {
		mWebView.registerHandler("SendUserInfoA", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				function.onCallBack(getUserInfo());
			}
		});
	}

	/**
	 * 加入购物车
	 */
	private void reAddToShoppingCart() {
		mWebView.registerHandler("addShopCartA", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				function.onCallBack(addToShoppingCart(data));

			}
		});
	}

	/**
	 * 显示详情
	 */
	private void reBookdetai() {
		mWebView.registerHandler("showBookDetaiA", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				function.onCallBack(showBookDetail(data));

			}
		});
	}

	/**
	 * 是否登陆
	 */
	private void reIsLogin() {
		mWebView.registerHandler("isLoginA", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				function.onCallBack(goToLogin());
			}
		});
	}

	public String groupBuy(String date) {
		if (MyApplication.mapp.getCookieStore() == null) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			getActivity().startActivity(intent);
			return "";
		}
		String skuId = "";
		int num = 0;
		try {
			JSONObject jsonObject = new JSONObject(date);
			skuId = jsonObject.getString("skuId");
			num = jsonObject.getInt("num");
		} catch (Exception e) {
			showToast("团购失败");
			return "";
		}
		BaseTask task = new BaseTask(getActivity());
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", "groupBuy");
		params.addBodyParameter("handler", "groupBuy");
		params.addBodyParameter("objectId", skuId);
		params.addBodyParameter("quantity", num + "");
		showDialog("正在下订单");
		task.askCookieRequest(SystemConfig.GROUPBY_ADD, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				dismissDialog();
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result);
					ShoppingCartBean bean = JSON.parseObject(obj.get("result").toString(), ShoppingCartBean.class);
					// 给页面填充数据
					if (bean != null) {
						Intent intent = new Intent(getActivity(), SubmitOrderActivity.class);
						intent.putExtra("shoppingCartBean", bean);
						startActivity(intent);
					} else {
						showToast("团购失败");
					}
				} catch (JSONException e) {
					e.printStackTrace();
					showToast("团购失败");
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				showToast("团购失败");
			}
		});

		return "";
	}

	private String gotoShopInfo(String date) {
		if (TextUtils.isEmpty(date)) {
			return "";
		}
		Intent inShop = new Intent(getActivity(), ShopActivity.class);
		inShop.putExtra("shopId", date);
		getActivity().startActivity(inShop);
		return "";
	}

	private void reMoreProduct() {
		mWebView.registerHandler("moreProductA", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				function.onCallBack(moreProduct(data));
			}
		});

	}

	private String moreProduct(String data) {
		if (TextUtils.isEmpty(data)) {
			showToast("查看更多卖家失败");
			return "";
		}
		Intent intent = new Intent(getActivity(), ClassifyMoreProductActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("pid", data);
		bundle.putString("More", "More");
		intent.putExtras(bundle);
		startActivity(intent);
		return "";
	}

	private String goToLogin() {
		if (MyApplication.mapp.getCookieStore() == null) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			getActivity().startActivity(intent);
			return "";
		} else {
			return "YES";
		}

	}

	private String showBookDetail(String date) {
		LogUtil.e("date", date);
		String productId = "";
		boolean canAddCart = false;
		try {
			JSONObject jsonObject = new JSONObject(date);
			productId = jsonObject.getString("productId");
			if (jsonObject.has("canAddCart")) {
				canAddCart = !"false".equals(jsonObject.getString("canAddCart"));
			}
		} catch (Exception e) {
			showToast("查看详情失败");
			return "";
		}
		Intent intent = new Intent(getActivity(), BookDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("ProductId", productId);
		bundle.putBoolean("canAddCart", canAddCart);
		intent.putExtras(bundle);
		startActivity(intent);
		return "";
	}

	public String addToShoppingCart(String date) {
		LogUtil.e("date", date);
		String shopInfId = "";
		String skuId = "";
		int num = 0;
		try {
			JSONObject jsonObject = new JSONObject(date);
			shopInfId = jsonObject.getString("shopInfId");
			skuId = jsonObject.getString("skuId");
			num = jsonObject.getInt("num");
		} catch (Exception e) {
			showToast("加入购物车失败");
			return "";
		}
		BaseTask task = new BaseTask();
		task.addShoppingCard(shopInfId, skuId, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("BookDetailActivity", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					if (obj.getBoolean("success")) {
						showToast("加入购物车成功");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				showToast("加入购物车失败");
			}
		}, num);
		return "";
	}

	/**
	 * 获取用户信息
	 * 
	 * @return {"userId":"","phoneNumber":""}
	 */
	private String getUserInfo() {
		UserInfoBean userInfoBean = MyApplication.mapp.getUserInfoBean();
		if (userInfoBean == null) {
			return "";
		}
		int userId = userInfoBean.getSysUserId();
		String phoneNumber = userInfoBean.getUserMobile();
		String result = "{\"userId\":userIdvalue,\"phoneNumber\":\"phoneNumbervalue\"}";
		result = result.replace("userIdvalue", userId + "");
		result = result.replace("phoneNumbervalue", phoneNumber + "");
		return result;
	}
}
