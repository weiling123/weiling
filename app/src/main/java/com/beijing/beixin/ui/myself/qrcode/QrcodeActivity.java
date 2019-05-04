package com.beijing.beixin.ui.myself.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.beixin.R;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.RoundImageView;
import com.beijing.beixin.utils.zxing.CreateErWeiMaImage;
import com.google.zxing.WriterException;

/**
 * 二维码
 * 
 * @author ouyanghao
 * 
 */
public class QrcodeActivity extends BaseActivity {

	/**
	 * 头像
	 */
	private RoundImageView iv_personHeader;
	/**
	 * 用户名
	 */
	private TextView tv_name;
	/**
	 * 卡号
	 */
	private TextView tv_cardNo;
	/**
	 * 二维码容器
	 */
	private ImageView iv_qrcode;
	/**
	 * 条形码容器
	 */
	private ImageView iv_ticketcode;
	private BitmapUtil bitmapUtil;
	private String cardNo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qrcode);
		init();
	}

	public void init() {
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		setNavigationTitle("我的二维码");
		Intent intent = getIntent();
		String headimage = intent.getStringExtra("headimage");
		cardNo = intent.getStringExtra("cardNo");
		iv_personHeader = (RoundImageView) findViewById(R.id.iv_personHeader);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_cardNo = (TextView) findViewById(R.id.tv_cardNo);
		iv_qrcode = (ImageView) findViewById(R.id.iv_qrcode);
		iv_ticketcode = (ImageView) findViewById(R.id.iv_erweicode);
		bitmapUtil = new BitmapUtil();
		bitmapUtil.displayImage(QrcodeActivity.this, iv_personHeader, headimage);
		tv_name.setText(MyApplication.mapp.getCertUserName());
		tv_cardNo.setText("卡号:" + cardNo);
		CreateQrCode();
		CreateErWeiMaCode();
	}

	/**
	 * 生成二维码
	 */
	public void CreateQrCode() {
		Bitmap qrcode;
		try {
			qrcode = CreateErWeiMaImage.createQRCode(MyApplication.mapp.getCertUserName(), 600);
			iv_qrcode.setImageBitmap(qrcode);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 生成条形码
	 */
	public void CreateErWeiMaCode() {
		Bitmap qrcode;
		try {
			if (cardNo != null) {
				qrcode = CreateErWeiMaImage.creatBarcode(this, cardNo + "", 500, 200, false);
				iv_ticketcode.setImageBitmap(qrcode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
