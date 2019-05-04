package com.beijing.beixin.ui.pay;

import com.beijing.beixin.R;
import com.beijing.beixin.R.layout;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.ui.MainActivity;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.myself.order.OrderActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 订单支付失败
 * 
 * @author ouyanghao
 * 
 */
public class PayOrderFailureActivity extends BaseActivity implements OnClickListener {

	/**
	 * 订单金额
	 */
	private TextView tv_ordermomey;
	/**
	 * 查看订单
	 */
	private TextView tv_seeorder;
	/**
	 * 回首页
	 */
	private TextView tv_backhome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_order_success);
		init();
	}

	/**
	 * 绑定控件ID及监听
	 */
	public void init() {
		setNavigationTitle("订单支付失败");
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		String money = getIntent().getStringExtra("money");
		tv_ordermomey = (TextView) findViewById(R.id.tv_ordermomey);
		tv_ordermomey.setText(money);
		tv_seeorder = (TextView) findViewById(R.id.tv_seeorder);
		tv_backhome = (TextView) findViewById(R.id.tv_backhome);
		tv_seeorder.setOnClickListener(this);
		tv_backhome.setOnClickListener(this);
		TextView textview_pay_message = (TextView) findViewById(R.id.textview_pay_message);
		textview_pay_message.setText("订单支付失败");
		TextView textview_price_name = (TextView) findViewById(R.id.textview_price_name);
		textview_price_name.setVisibility(View.INVISIBLE);
		ImageView imageview_order = (ImageView) findViewById(R.id.imageview_order);
		imageview_order.setImageResource(R.drawable.failure);
		// ((MyApplication)
		// getApplication()).setTotalMoney("success");//将总金额存入sp中
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_seeorder:
			finish();
			startActivity(OrderActivity.class);
			break;
		case R.id.tv_backhome:
			finish();
			startActivity(MainActivity.class);
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ((MyApplication) getApplication()).setTotalMoney("");//将总金额存入sp中
	}

}
