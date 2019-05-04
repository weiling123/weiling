package com.beijing.beixin.ui.myself.login;

import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import com.beijing.beixin.R;
import com.beijing.beixin.R.layout;
import com.beijing.beixin.ui.base.BaseActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class TermsActivity extends BaseActivity {

	String mType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terms);
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		mType = getIntent().getStringExtra("type");
		if ("T".equals(mType)) {
			setNavigationTitle("隐私条款");
			TextView tv_terms = (TextView) findViewById(R.id.tv_terms);
			tv_terms.setBackgroundDrawable(null);
			tv_terms.setText(readFromAsset("privacyPolicy.txt"));
		}
		if ("A".equals(mType)) {
			setNavigationTitle("关于北新网");
			TextView tv_terms = (TextView) findViewById(R.id.tv_terms);
			tv_terms.setText(null);
			tv_terms.setBackgroundDrawable(getResources().getDrawable(R.drawable.aboutbeixinwang));
			tv_terms.setGravity(Gravity.CENTER);
		}
	}

	/**
	 * 从asset中获取文件并读取数据（资源文件只能读不能写）
	 * 
	 * @param fileName
	 * @return
	 */
	public String readFromAsset(String fileName) {
		String res = "";
		try {
			InputStream in = getResources().getAssets().open(fileName);
			int length = in.available();
			byte[] buffer = new byte[length];
			in.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}
