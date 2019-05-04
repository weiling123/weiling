package com.beijing.beixin.ui.myself;

import com.beijing.beixin.R;
import com.beijing.beixin.R.drawable;
import com.beijing.beixin.R.id;
import com.beijing.beixin.R.layout;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

/**
 * 账户余额
 * 
 * @author ouyanghao
 * 
 */
@SuppressWarnings("unused")
public class AccountMoneyActivity extends BaseActivity {

	/**
	 * 可用余额
	 */
	private TextView available_money;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_money);
		init();
		initData();
	}

	private void init() {
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		setNavigationTitle("账户余额");
		available_money = (TextView) findViewById(R.id.available_money);
	}

	private void initData() {
		String money = getIntent().getStringExtra("money");
		if (money != null) {
			available_money.setText(money);
		}
	}
}
