package com.beijing.beixin.tasks;

import android.text.TextUtils;

import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.DataShop;
import com.beijing.beixin.ui.fragment.ShoppingCartFragment;
import com.lidroid.xutils.http.RequestParams;

public class InitShopcart {
	public InitShopcart(ShoppingCartFragment shoppingCartFragment, int state, DataShop[] dataShop, String toast,
			String dialogText) {

		this(shoppingCartFragment, state, dataShop, toast, dialogText, null, 0, null, 0, 0);
	}

	public InitShopcart(ShoppingCartFragment shoppingCartFragment, int state, DataShop[] dataShop, String toast,
			String dialogText, String shopInfId, int shopCheckState, String skuId, int skuIdCheckState, int num) {

		RequestParams params = MyApplication.mapp.getShopcartParam(state, dataShop, shopInfId, shopCheckState, skuId,
				skuIdCheckState, num);
		if (params == null) {
			shoppingCartFragment.cleartShopCart();
			return;
		}
		if (!TextUtils.isEmpty(dialogText)) {
			shoppingCartFragment.showDialog(dialogText);
		}
		BaseTask baseTask = new BaseTask();
		baseTask.askCookieRequest(SystemConfig.CART_LOCATION, params,
				new ShopCartRequestCallBack(shoppingCartFragment, toast));

	}

}
