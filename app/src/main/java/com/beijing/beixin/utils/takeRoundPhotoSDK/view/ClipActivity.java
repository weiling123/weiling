package com.beijing.beixin.utils.takeRoundPhotoSDK.view;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.beixin.R;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.utils.takeRoundPhotoSDK.utils.ImageTools;

/**
 * 类名称:ClipActivity 类描述:实现裁剪功能的界面 作者:张静 最后更新时间:2015年12月3日 下午3:49:59 版本:v1.0
 */
public class ClipActivity extends BaseActivity implements OnClickListener {

	private ClipImageLayout mClipImageLayout;// 裁剪的布局
	private Dialog loadingDialog;// 进度条
	private String path;// 得到的路径
	private Button clipButton, cancelButton;// 裁剪按钮和取消按钮
	TextView btn_back;// 返回按钮
	@SuppressLint("HandlerLeak")
	/** Handler 对象 */
	public Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				Toast.makeText(getApplicationContext(), "图片加载失败", Toast.LENGTH_SHORT).show();
			} else if (msg.what == 1) {
				ClipActivity.this.finish();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置NO title
		setContentView(R.layout.activity_takeroundphoto_clipimage);
		initView();// 绑定控件
		initData();// 绑定数据
	}

	/**
	 * 绑定数据
	 * 
	 * @author 张静
	 * @Time 2015年12月3日下午2:49:29
	 */
	private void initData() {
		path = getIntent().getStringExtra("path");// 得到图片的路径
		if (TextUtils.isEmpty(path) || !(new File(path).exists())) {
			Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
			return;
		}
		Bitmap bitmap = ImageTools.convertToBitmap(path, 600, 600);
		if (bitmap == null) {
			Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
			return;
		}
		int degree = readPictureDegree((new File(path)).getAbsolutePath());
		bitmap = rotaingImageView(degree, bitmap);
		mClipImageLayout.setBitmap(bitmap);
	}

	/**
	 * 绑定控件
	 * 
	 * @author 张静
	 * @Time 2015年12月3日下午2:49:58
	 */
	private void initView() {
		setNavigationTitle("移动和缩放");
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		btn_back = (TextView) this.findViewById(R.id.button_back);// 返回键
		btn_back.setOnClickListener(this);
		initDialog();// 初始化自定义loadding dialog
		mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);
		clipButton = (Button) this.findViewById(R.id.id_action_clip);
		cancelButton = (Button) this.findViewById(R.id.id_action_cancel);
		clipButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
	}

	/**
	 * 初始化自定义的dialog
	 * 
	 * @author 张静
	 * @Time 2015年12月3日下午2:50:30
	 */
	@SuppressLint("InflateParams")
	@SuppressWarnings("deprecation")
	private void initDialog() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View v = inflater.inflate(R.layout.take_round_photo_clipactivity_loading, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		tipTextView.setText("正在保存,请稍候");
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.take_round_photo_load_animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		loadingDialog = new Dialog(this, R.style.loading_dialog);// 创建自定义样式dialog
		loadingDialog.setCancelable(false);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
	}

	/**
	 * 回退按钮
	 */
	@Override
	public void onBackPressed() {
		Intent intent5 = new Intent();
		setResult(-2, intent5);
		this.finish();
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_action_clip:// 裁剪
			loadingDialog.show();
			clipImg();
			break;
		case R.id.id_action_cancel:// 取消
			this.finish();
			break;
		case R.id.button_back:// 返回
			this.finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 裁剪图片
	 * 
	 * @author 张静
	 * @Time 2015年12月3日下午2:51:55
	 */
	private void clipImg() {
		Bitmap bitmap = mClipImageLayout.clip();
		String photoSavePath = Environment.getExternalStorageDirectory() + "/IconDemo/";
		File imgDir = new File(photoSavePath);
		if (!imgDir.exists()) {
			imgDir.mkdir();
		}
		// 获得路径
		String path = photoSavePath + System.currentTimeMillis() + ".png";
		// 保存到sdcard
		ImageTools.savePhotoToSDCard(bitmap, path);
		// 把path传到imageActivity中去
		// url = path;
		Intent intentRound = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("roundpath", path);
		intentRound.putExtras(bundle);
		setResult(-1, intentRound);
		ClipActivity.this.finish();
	}

	/**
	 * 旋转ImageView
	 * 
	 * @param angle
	 *            (角度)
	 * @param bitmap
	 *            (Bitmap对象)
	 * @return
	 * @author 张静
	 * @Time 2015年12月3日下午2:56:57
	 */
	Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/**
	 * 读取照片旋转的角度
	 * 
	 * @param path
	 *            (路径)
	 * @return
	 * @author 张静
	 * @Time 2015年12月3日下午2:56:25
	 */
	public int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
}
