package com.beijing.beixin.ui.pay;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.beixin.R;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.ui.MainActivity;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.myself.order.OrderActivity;
import com.beijing.beixin.utils.ExitApplication;

/**
 * 订单支付成功
 * 
 * @author ouyanghao
 * 
 */
public class PayOrderSuccessActivity extends BaseActivity implements OnClickListener {

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
		setNavigationTitle("订单支付成功");
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		String money = getIntent().getStringExtra("money");
		tv_ordermomey = (TextView) findViewById(R.id.tv_ordermomey);
		tv_ordermomey.setText(money);
		tv_seeorder = (TextView) findViewById(R.id.tv_seeorder);
		tv_backhome = (TextView) findViewById(R.id.tv_backhome);
		ImageView imageview_order = (ImageView) findViewById(R.id.imageview_order);
		imageview_order.setImageResource(R.drawable.success);
		tv_seeorder.setOnClickListener(this);
		tv_backhome.setOnClickListener(this);

		((MyApplication) getApplication()).setTotalMoney("success");// 将总金额存入sp中
	}

	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		((MyApplication) getApplication()).setTotalMoney("");// 将总金额存入sp中
		finish();
		startActivity(OrderActivity.class);
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_seeorder:
			((MyApplication) getApplication()).setTotalMoney("");// 将总金额存入sp中
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
		((MyApplication) getApplication()).setTotalMoney("");// 将总金额存入sp中
	}

	/**
	 * 再按一次退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			((MyApplication) getApplication()).setTotalMoney("");// 将总金额存入sp中
			finish();
			startActivity(OrderActivity.class);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
