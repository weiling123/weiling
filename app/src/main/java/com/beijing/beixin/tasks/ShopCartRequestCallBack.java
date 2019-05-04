package com.beijing.beixin.tasks;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.DataShop;
import com.beijing.beixin.pojo.ShoppingCartBean;
import com.beijing.beixin.pojo.ShoppingCartProBean;
import com.beijing.beixin.pojo.ShoppingCartShopBean;
import com.beijing.beixin.ui.MainActivity;
import com.beijing.beixin.ui.fragment.ShoppingCartFragment;
import com.beijing.beixin.utils.LogUtil;
import com.beijing.beixin.utils.Utils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class ShopCartRequestCallBack extends RequestCallBack<String> {

	private ShoppingCartFragment mShoppingCartFragment;
	private String mToast;

	public ShopCartRequestCallBack(ShoppingCartFragment shoppingCartFragment, String toast) {
		mShoppingCartFragment = shoppingCartFragment;
		mToast = toast;
	}

	@Override
	public void onSuccess(ResponseInfo<String> arg0) {
		JSONObject obj;
		try {
			obj = new JSONObject(arg0.result);
			LogUtil.e("arg0.result", arg0.result);
			mShoppingCartFragment.dismissDialog();
			ShoppingCartBean bean = JSON.parseObject(obj.get("result").toString(), ShoppingCartBean.class);
			ShoppingCartBean bean2 = JSON.parseObject(obj.get("result").toString(), ShoppingCartBean.class);
			// 给页面填充数据
			if (bean != null) {
				if (bean.getShoppingCartVos().size() != 0) {
					// mShoppingCartFragment.layout_nodata.setVisibility(View.GONE);
					// mShoppingCartFragment.navigationLeftBtn.setVisibility(View.VISIBLE);
					mShoppingCartFragment.showView();
					mShoppingCartFragment.setCartCount("" + bean.getAllCartNum());
				} else {
					mShoppingCartFragment.adapter.setData(bean);
					mShoppingCartFragment.adapter.notifyDataSetChanged();
					mShoppingCartFragment.hideView();
					// mShoppingCartFragment.navigationLeftBtn.setVisibility(View.GONE);
					// mShoppingCartFragment.layout_nodata.setVisibility(View.VISIBLE);
					mShoppingCartFragment.setCartCount("0");
					MyApplication.mapp.emptyDataShop();
					return;
				}
				mShoppingCartFragment.listshoppingcart = bean;
				mShoppingCartFragment.editshoppingcart = bean2;
				if (mShoppingCartFragment.getActivity() instanceof MainActivity) {
					((MainActivity) mShoppingCartFragment.getActivity())
							.setCartCount(Utils.isInteger(bean.getAllCartNum()) + "", true);
				}
				mShoppingCartFragment.button_to_pay.setText("去结算(" + Utils.isInteger(bean.getSelectCartNum()) + ")");
				mShoppingCartFragment.textview_allprice
						.setText("¥" + Utils.parseDecimalDouble2(bean.getAllOrderTotalAmount()));
				if (bean.getIsSelectAll() == true) {
					mShoppingCartFragment.checkbox_selectall.setChecked(true);
				} else {
					mShoppingCartFragment.checkbox_selectall.setChecked(false);
				}
				mShoppingCartFragment.adapter.setData(bean);
				mShoppingCartFragment.adapter.notifyDataSetChanged();
			}
			if (MyApplication.mapp.getCookieStore() == null) {
				ArrayList<DataShop> arrayList = new ArrayList<DataShop>();
				List<ShoppingCartShopBean> shoppingCartVos = bean.getShoppingCartVos();
				if (shoppingCartVos != null) {
					for (ShoppingCartShopBean shoppingCartShopBean : shoppingCartVos) {
						if (shoppingCartShopBean != null) {
							List<ShoppingCartProBean> items = shoppingCartShopBean.getItems();
							for (ShoppingCartProBean shoppingCartProBean : items) {
								int checkState = shoppingCartProBean.getItemSelected() ? 1 : 0;
								DataShop dataShop = new DataShop(shoppingCartShopBean.getShopInfId() + "",
										shoppingCartProBean.getSkuId() + "", shoppingCartProBean.getQuantity(),
										checkState);
								arrayList.add(dataShop);
							}
						}
					}
				}
				DataShop[] dataShops = new DataShop[arrayList.size()];
				dataShops = arrayList.toArray(dataShops);
				MyApplication.mapp.setDateShop(dataShops);
			}
			mShoppingCartFragment.view_top.setFocusable(true);
			mShoppingCartFragment.view_top.setFocusableInTouchMode(true);
			mShoppingCartFragment.view_top.requestFocus();

		} catch (JSONException e) {
			mShoppingCartFragment.dismissDialog();
			if (!TextUtils.isEmpty(mToast)) {
				mShoppingCartFragment.showToast(mToast);
			}
			e.printStackTrace();
		}
	}

	@Override
	public void onFailure(HttpException arg0, String arg1) {
		mShoppingCartFragment.dismissDialog();
		if (!TextUtils.isEmpty(mToast)) {
			mShoppingCartFragment.showToast(mToast);
		}
	}
}
