package com.beijing.beixin.ui.myself;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.beixin.R;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.homepage.WebViewActivity;
import com.beijing.beixin.utils.zxing.CreateErWeiMaImage;
import com.google.zxing.WriterException;

public class QrcodeTicketActivity extends BaseActivity {

	private ImageView iv_qrcode;
	private TextView textview_qrCode;
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qrcode_ticket);
		init();
	}

	public void init() {
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		setNavigationTitle("二维码");
		iv_qrcode = (ImageView) findViewById(R.id.iv_qrcode);
		mWebView = (WebView) findViewById(R.id.webview);
		WebSettings settings = mWebView.getSettings();
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		mWebView.loadUrl(getIntent().getStringExtra("url"));
		CreateQrCode(getIntent().getStringExtra("qrcode"));
	}

	/**
	 * 生成二维码
	 */
	public void CreateQrCode(String code) {
		Bitmap qrcode;
		try {
			qrcode = CreateErWeiMaImage.createQRCode(code, 600);
			iv_qrcode.setImageBitmap(qrcode);
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}

}
