package com.beijing.beixin.ui.myself;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.beixin.R;
import com.beijing.beixin.ui.base.BaseActivity;

/**
 * 资产中心
 * 
 * @author ouyanghao
 * 
 */
public class AssetCenterActivity extends BaseActivity implements OnClickListener {

	/**
	 * 账户余额
	 */
	private LinearLayout ll_account_money;
	private TextView account_money;
	/**
	 * 优惠券
	 */
	private LinearLayout ll_coupons;
	private TextView coupons;
	/**
	 * 快捷支付
	 */
	private LinearLayout ll_unbundlinguupay;
	private TextView unbundlinguupay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asset_center);
		init();
		initData();
	}

	private void init() {
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		setNavigationTitle("资产中心");
		ll_account_money = (LinearLayout) findViewById(R.id.ll_account_money);
		account_money = (TextView) findViewById(R.id.account_money);
		ll_coupons = (LinearLayout) findViewById(R.id.ll_coupons);
		coupons = (TextView) findViewById(R.id.coupons);
		ll_unbundlinguupay = (LinearLayout) findViewById(R.id.ll_unbundlinguupay);
		unbundlinguupay = (TextView) findViewById(R.id.unbundlinguupay);
		ll_account_money.setOnClickListener(this);
		ll_coupons.setOnClickListener(this);
		ll_unbundlinguupay.setOnClickListener(this);
	}

	private void initData() {
		String money = getIntent().getStringExtra("money");
		String coupon = getIntent().getStringExtra("coupon");
		if (money != null) {
			account_money.setText(money);
		}
		if (coupon != null) {
			coupons.setText(coupon);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_account_money:
			Intent intentAsset = new Intent(AssetCenterActivity.this, AccountMoneyActivity.class);
			intentAsset.putExtra("money", account_money.getText().toString().trim());
			startActivity(intentAsset);
			break;
		case R.id.ll_coupons:
			startActivity(MyCouponActivity.class);
			break;
		case R.id.ll_unbundlinguupay:
			startActivity(AccountMoneyActivity.class);
			break;
		}
	}
}
