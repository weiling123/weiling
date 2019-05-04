package com.beijing.beixin.ui.myself.order;

import org.json.JSONException;
import org.json.JSONObject;

import com.beijing.beixin.R;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.OrderListBean;
import com.beijing.beixin.pojo.OrderListBean.AppOrderItemVo;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 * 评价晒单
 * 
 * @author ouyanghao
 * 
 */
public class EvaluationSingleActivity extends BaseActivity {

	private OrderListBean mOrderListBean;

	private ListBean[] mListBeans;

	private BitmapUtil mBitmapUtil = new BitmapUtil();

	private boolean mIsEdit;

	private int[] mRatingShop = new int[] { 5, 5, 5 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evaluation_single);

		BitmapDrawable bd = (BitmapDrawable) getResources().getDrawable(R.drawable.star_normal);
		Bitmap bmp = bd.getBitmap();

		int ratingBarHeight = bmp.getHeight();
		setNavigationTitle("评价晒单");
		setNavigationLeftBtnImage(R.drawable.title_bar_back);

		mOrderListBean = (OrderListBean) getIntent().getSerializableExtra("OrderListBean");

		mIsEdit = ("待评价".equals(mOrderListBean.getOrderStat()));

		View[] shopCommentLayout = new View[3];
		shopCommentLayout[0] = findViewById(R.id.shop_comment_fit_layout);
		shopCommentLayout[1] = findViewById(R.id.shop_comment_fit_attitude_layout);
		shopCommentLayout[2] = findViewById(R.id.shop_comment_fit_speed_layout);

		View shopCommentTitle = findViewById(R.id.shop_comment);

		View submitButton = findViewById(R.id.submit);
		if (mIsEdit) {
			RatingBar[] ratingBars = new RatingBar[3];
			ratingBars[0] = (RatingBar) findViewById(R.id.shop_comment_fit);
			ratingBars[1] = (RatingBar) findViewById(R.id.shop_comment_fit_attitude);
			ratingBars[2] = (RatingBar) findViewById(R.id.shop_comment_fit_speed);

			submitButton.setVisibility(View.VISIBLE);
			shopCommentTitle.setVisibility(View.VISIBLE);
			for (int i = 0; i < shopCommentLayout.length; i++) {
				ratingBars[i].setOnRatingBarChangeListener(new MyOnRatingBarChangeListener(i));
				ratingBars[i].getLayoutParams().height = ratingBarHeight;
				shopCommentLayout[i].setVisibility(View.VISIBLE);
			}
		} else {
			shopCommentTitle.setVisibility(View.GONE);
			submitButton.setVisibility(View.GONE);
			for (int i = 0; i < shopCommentLayout.length; i++) {
				shopCommentLayout[i].setVisibility(View.GONE);
			}
		}

		if (mOrderListBean != null && mOrderListBean.getOrderItemList() != null) {
			mListBeans = new ListBean[mOrderListBean.getOrderItemList().size()];
			for (int i = 0; i < mListBeans.length; i++) {
				mListBeans[i] = new ListBean();
				AppOrderItemVo appOrderItemVo = mOrderListBean.getOrderItemList().get(i);
				if (!mIsEdit) {
					mListBeans[i].mContent = appOrderItemVo.getCommentCont();
					mListBeans[i].mRating = appOrderItemVo.getGradeLevel();
				}
			}
		}

		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(new MyArrayAdapter());
	}

	public void onSubmit(View view) {
		sendhttp();
	}

	private class MyArrayAdapter extends ArrayAdapter<OrderListBean> {

		public MyArrayAdapter() {
			super(EvaluationSingleActivity.this, R.layout.item_comment_order);
		}

		@Override
		public int getCount() {
			if (mOrderListBean == null) {
				return 0;
			}

			if (mOrderListBean.getOrderItemList() == null) {
				return 0;
			}

			return mOrderListBean.getOrderItemList().size();

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder viewHolder;
			if (convertView == null || convertView.getTag() == null) {
				viewHolder = new ViewHolder();
				convertView = getLayoutInflater().inflate(R.layout.item_comment_order, null);
				viewHolder.product_image = (ImageView) convertView.findViewById(R.id.product_image);
				viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);

				if (mIsEdit) {
					viewHolder.ratingBar.setIsIndicator(false);
					viewHolder.ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
						public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
							mListBeans[viewHolder.index].mRating = (int) rating;
						}
					});
				} else {
					viewHolder.ratingBar.setIsIndicator(true);
				}

				viewHolder.add_content = (EditText) convertView.findViewById(R.id.add_content);
				if (mIsEdit) {
					viewHolder.add_content.setEnabled(true);
					viewHolder.add_content.addTextChangedListener(new TextWatcher() {

						@Override
						public void onTextChanged(CharSequence s, int start, int before, int count) {

						}

						@Override
						public void beforeTextChanged(CharSequence s, int start, int count, int after) {
							mListBeans[viewHolder.index].mContent = s.toString();

						}

						@Override
						public void afterTextChanged(Editable s) {
							mListBeans[viewHolder.index].mContent = s.toString();
						}
					});

				} else {
					viewHolder.add_content.setEnabled(false);
				}

				convertView.setTag(viewHolder);

			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.index = position;
			mBitmapUtil.displayImage(getContext(), viewHolder.product_image,
					mOrderListBean.getOrderItemList().get(position).getImage());
			setText(viewHolder.add_content, mListBeans[position].mContent);
			viewHolder.ratingBar.setRating(mListBeans[position].mRating);

			return convertView;
		}
	}

	private void setText(EditText editText, String text) {
		if (TextUtils.isEmpty(text)) {
			if (mIsEdit) {
				editText.setHint("1-150个字内\n写下您的购买体会或者建议");
			} else {
				editText.setHint("未进行评价");
			}
		} else {
			editText.setText(text);
		}
	}

	private static class ViewHolder {
		ImageView product_image;
		RatingBar ratingBar;
		EditText add_content;
		int index;
	}

	private class ListBean {
		private int mRating = 5;
		private String mContent = "";
		private String orderItemId = "";
	}

	public void sendhttp() {
		showDialog("正在提交数据，请稍后...");
		BaseTask task = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("orderId", mOrderListBean.getOrderId() + "");
		params.addBodyParameter("productDescrSame", mRatingShop[0] + "");
		params.addBodyParameter("sellerServiceAttitude", mRatingShop[1] + "");
		params.addBodyParameter("sellerSendOutSpeed", mRatingShop[2] + "");
		for (int i = 0; i < mListBeans.length; i++) {
			AppOrderItemVo appOrderItemVo = mOrderListBean.getOrderItemList().get(i);
			params.addBodyParameter("sysCommentList[" + i + "].objectId", appOrderItemVo.getProductId() + "");
			params.addBodyParameter("sysCommentList[" + i + "].gradeLevel", mListBeans[i].mRating + "");
			params.addBodyParameter("sysCommentList[" + i + "].commentCont", mListBeans[i].mContent);
			params.addBodyParameter("sysCommentList[" + i + "].orderId", mOrderListBean.getOrderId() + "");
			params.addBodyParameter("sysCommentList[" + i + "].orderItemId",
					mOrderListBean.getOrderItemList().get(i).getOrderItemId() + "");
		}

		task.askCookieRequest(SystemConfig.COMMENT_ORLDER, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				dismissDialog();

				if (arg0 == null || TextUtils.isEmpty(arg0.result)) {
					showToast("评论提交失败");
				} else {
					try {
						JSONObject jsonObject = new JSONObject(arg0.result);
						if (jsonObject.has("success")) {
							if (jsonObject.getBoolean("success")) {
								dismissDialog();
								showToast("评论提交成功");
								setResult(RESULT_OK);
								finish();
								return;
							}
						}
					} catch (JSONException e) {
						showToast("评论提交失败");
						e.printStackTrace();
					}

				}
				showToast("评论提交失败");
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				showToast("评论提交失败");
			}
		});
	}

	private class MyOnRatingBarChangeListener implements OnRatingBarChangeListener {

		private int mIndex;

		MyOnRatingBarChangeListener(int index) {
			mIndex = index;
		}

		@Override
		public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
			mRatingShop[mIndex] = (int) rating;
		}

	}

}
