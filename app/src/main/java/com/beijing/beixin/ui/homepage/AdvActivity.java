package com.beijing.beixin.ui.homepage;

import android.os.Bundle;
import android.widget.ImageView;

import com.beijing.beixin.R;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.utils.BitmapUtil;

public class AdvActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adv);
		initView();
	}

	private void initView() {
		String url = getIntent().getStringExtra("openUrl");
		ImageView imageview_adv = (ImageView) findViewById(R.id.imageview_adv);
		BitmapUtil util = new BitmapUtil();
		util.displayImage(AdvActivity.this, imageview_adv, url);
	}
}
