package com.beijing.beixin.ui.shoppingcart;

import com.beijing.beixin.R;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.fragment.ShoppingCartFragment;
import com.beijing.beixin.utils.ExitApplication;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class ShopcartActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopcart);

		ExitApplication.getInstance().addAllActivity(this);

		FragmentManager fragmentManager = getSupportFragmentManager();
		ShoppingCartFragment shoppingCartFragment = (ShoppingCartFragment) fragmentManager.getFragments().get(0);
		shoppingCartFragment.setPoistion(3);
		shoppingCartFragment.setContent(this);
	}

}
