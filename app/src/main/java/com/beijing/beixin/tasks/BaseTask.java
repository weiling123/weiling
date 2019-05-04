package com.beijing.beixin.tasks;

import org.apache.http.HttpResponse;

import android.R.bool;
import android.R.integer;
import android.content.Context;

import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.DataShop;
import com.beijing.beixin.pojo.NewProductBean.product;
import com.beijing.beixin.utils.NetWorkUtils;
import com.beijing.beixin.utils.ToastUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class BaseTask extends HttpUtils {

	public Context mContext;

	public BaseTask() {
	}

	public BaseTask(Context mContext) {
		this.mContext = mContext;
		if (NetWorkUtils.getCurrentNetType(mContext) == null) {
			ToastUtil.show(mContext, "请连接网络!");
			return;
		}
	}

	public static HttpUtils utils = new HttpUtils(100 * 1000);

	/**
	 * 普通调用，不需要cookie/post
	 * 
	 * 
	 * @param url
	 * @param params
	 * @param requestCallBack
	 */
	public void askNormalRequest(String url, RequestParams params, RequestCallBack<String> requestCallBack) {
		utils.send(HttpMethod.POST, url, params, requestCallBack);
	}

	/**
	 * 普通调用，不需要cookie/get
	 * 
	 * 
	 * @param url
	 * @param params
	 * @param requestCallBack
	 */
	public void askNormalRequestGet(String url, RequestParams params, RequestCallBack<String> requestCallBack) {
		utils.send(HttpMethod.GET, url, params, requestCallBack);
	}

	/**
	 * 登录后调用，需要传入cookie/post
	 * 
	 * 
	 * @param url
	 * @param params
	 * @param requestCallBack
	 */
	public void askCookieRequest(String url, RequestParams params, RequestCallBack<String> requestCallBack) {
		// RequestParams params = new RequestParams();
		// params.addBodyParameter("token", "token");
		// params.addBodyParameter("page", 0 + "");
		// params.addBodyParameter("pagesize", 10 + "");
		// [[version: 0][name: JSESSIONID][value:
		// 1FCAD0CFAC56EDDC3984392B88D5AD2A][domain: 112.33.7.53][path:
		// /][expiry: null], [version: 0][name: route][value:
		// 816a086018bc7430f462ea5574642e73][domain: 112.33.7.53][path:
		// /][expiry: null]]
		utils.configCookieStore(MyApplication.mapp.getCookieStore());
		utils.send(HttpMethod.POST, url, params, requestCallBack);
	}

	public void addShoppingCard(String shopInfId, String skuid, RequestCallBack<String> requestCallBack) {
		addShoppingCard(shopInfId, skuid, requestCallBack, 1);
	}

	public void addShoppingCard(String shopInfId, String skuid, RequestCallBack<String> requestCallBack, int num) {
		if (MyApplication.mapp.getCookieStore() == null) {
			DataShop dataShop = new DataShop(shopInfId, skuid, num);
			int shopNum = MyApplication.mapp.addDataShop(dataShop);
			String result = "{\"share_current_request_time\":" + System.currentTimeMillis()
					+ ",\"ctx\":\"\",\"cartNum\":" + shopNum + ",\"success\":true}";
			ResponseInfo<String> rInfo = new ResponseInfo<String>(null, result, false);
			requestCallBack.onSuccess(rInfo);
			return;
		}
		utils.configCookieStore(MyApplication.mapp.getCookieStore());
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", "normal");
		params.addBodyParameter("handler", "sku");
		params.addBodyParameter("objectId", skuid);
		params.addBodyParameter("quantity", num + "");
		utils.send(HttpMethod.POST, SystemConfig.ADD_CART, params, requestCallBack);
	}

	/**
	 * 登录后调用，需要传入cookie/get
	 * 
	 * 
	 * @param url
	 * @param params
	 * @param requestCallBack
	 */
	public void askCookieRequestGet(String url, RequestParams params, RequestCallBack<String> requestCallBack) {
		// RequestParams params = new RequestParams();
		// params.addBodyParameter("token", "token");
		// params.addBodyParameter("page", 0 + "");
		// params.addBodyParameter("pagesize", 10 + "");
		utils.configCookieStore(MyApplication.mapp.getCookieStore());
		utils.send(HttpMethod.GET, url, params, requestCallBack);
	}
}
