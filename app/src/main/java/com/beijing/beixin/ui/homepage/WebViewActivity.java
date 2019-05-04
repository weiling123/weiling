package com.beijing.beixin.ui.homepage;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.beijing.beixin.ui.base.WebViewFragment;

public class WebViewActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WebViewFragment webViewFragment = WebViewFragment.instance(getIntent().getStringExtra("openUrl"), "");
		getSupportFragmentManager().beginTransaction().add(android.R.id.content, webViewFragment).commit();
	}

	@Override
	public void onBackPressed() {
		finish();
	}

}
