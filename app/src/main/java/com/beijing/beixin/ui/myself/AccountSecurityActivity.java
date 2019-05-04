package com.beijing.beixin.ui.myself;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.beixin.R;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.myself.login.ResertPwdActivity;
import com.beijing.beixin.ui.pay.PayPwdActivity;

public class AccountSecurityActivity extends BaseActivity implements OnClickListener {

	private TextView mobile;
	private TextView tv_mail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_security);
		initView();
		init();
	}

	@Override
	protected void onResume() {
		initData();
		super.onResume();
	}

	public void init() {
		setNavigationTitle("账户安全");
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
	}

	private void initView() {
		LinearLayout layout_login_pwd = (LinearLayout) findViewById(R.id.layout_login_pwd);
		LinearLayout layout_pay_pwd = (LinearLayout) findViewById(R.id.layout_pay_pwd);
		LinearLayout layout_mobile = (LinearLayout) findViewById(R.id.layout_mobile);
		LinearLayout layout_email = (LinearLayout) findViewById(R.id.layout_email);
		mobile = (TextView) findViewById(R.id.tv_mobile);
		tv_mail = (TextView) findViewById(R.id.tv_mail);
		layout_login_pwd.setOnClickListener(this);
		layout_pay_pwd.setOnClickListener(this);
		layout_email.setOnClickListener(this);
		layout_mobile.setOnClickListener(this);
		initData();
	}

	public void initData() {
		if (MyApplication.mapp.getCookieStore() != null) {
			if (MyApplication.mapp.getUserInfoBean().getUserMobile() != null) {
				mobile.setText(MyApplication.mapp.getUserInfoBean().getUserMobile().substring(0, 3) + "****"
						+ MyApplication.mapp.getUserInfoBean().getUserMobile().substring(7,
								MyApplication.mapp.getUserInfoBean().getUserMobile().length()));
			}
			if (MyApplication.mapp.getUserInfoBean().getUserEmail() != null) {
				tv_mail.setText(MyApplication.mapp.getUserInfoBean().getUserEmail()
						.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4"));
			}
		}
	}

	public static final int PAYPWD_CODE = 2001;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_login_pwd:
			startActivity(ResertPwdActivity.class);
			break;
		case R.id.layout_pay_pwd:
			Intent intent = new Intent(AccountSecurityActivity.this, PayPwdActivity.class);
			startActivityForResult(intent, PAYPWD_CODE);
			break;
		case R.id.right_save:
			break;
		case R.id.left_back:
			this.finish();
			break;
		case R.id.layout_mobile:
			startActivity(UpdateMobileActivity.class);
			break;
		case R.id.layout_email:
			startActivity(UpdateEmailActivity.class);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// if (requestCode == PAYPWD_CODE) {
		// switch (resultCode) {
		// case RESULT_OK:
		// break;
		// }
		// }
		super.onActivityResult(requestCode, resultCode, data);
	}
}
