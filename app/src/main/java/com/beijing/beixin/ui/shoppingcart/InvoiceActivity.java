package com.beijing.beixin.ui.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.beijing.beixin.R;
import com.beijing.beixin.ui.base.BaseActivity;

public class InvoiceActivity extends BaseActivity {

	private RadioButton radiobutton_need_invoice;
	private RadioButton radiobutton_inneed_invoice;
	private EditText edittext_invoice;
	private RadioGroup group;
	private Button button_sure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invoice);
		setNavigationTitle("选择发票");
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		initView();
		setListener();
	}

	private void initView() {
		group = (RadioGroup) findViewById(R.id.radiogroup_invoice);
		radiobutton_need_invoice = (RadioButton) findViewById(R.id.radiobutton_need_invoice);
		radiobutton_inneed_invoice = (RadioButton) findViewById(R.id.radiobutton_inneed_invoice);
		edittext_invoice = (EditText) findViewById(R.id.edittext_invoice);
		button_sure = (Button) findViewById(R.id.button_sure);
	}

	private void setListener() {
		// 绑定一个匿名监听器
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkedId) {
				if (checkedId == R.id.radiobutton_need_invoice) {
					edittext_invoice.setEnabled(true);
				} else if (checkedId == R.id.radiobutton_inneed_invoice) {
					edittext_invoice.setEnabled(false);
				}
			}
		});
		button_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (edittext_invoice.isEnabled() == true && "".equals(edittext_invoice.getText().toString())) {
					showToast("请输入发票抬头内容");
					return;
				}
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("invoice", edittext_invoice.getText().toString());
				intent.putExtras(bundle);
				setResult(1, intent);
				finish();
			}
		});
	}
}
