package com.beijing.beixin.dialog;

import android.R.color;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.bean.ProductSpec;
import com.beijing.beixin.bean.Result;
import com.beijing.beixin.bean.Sku;
import com.beijing.beixin.bean.SpecValue;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.Utils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class DialogBookDetail extends Dialog implements android.view.View.OnClickListener {

	private Sku mSku;

	private ProductSpec mProductSpec;

	private BookDetailActivity mBookDetailActivity;
	/**
	 * 数量
	 */
	private EditText textview_account;

	/**
	 * 记录商品的数量
	 */
	private int beforeCount;

	private GridViewListener mGridViewListener;

	private TextView mStockNum;

	private TextView mTextVPrice;

	private TextView mProductNum;

	private MyListAdapter mMyListAdapter;

	private Button add_cart;

	public DialogBookDetail(BookDetailActivity bookDetailActivity, ProductSpec productSpec, Sku sku, String produceUrl,
			GridViewListener cridViewListener) {
		super(bookDetailActivity);

		initDialog(bookDetailActivity, cridViewListener, productSpec, sku, produceUrl);
	}

	private void initDialog(BookDetailActivity bookDetailActivity, GridViewListener cridViewListener,
			ProductSpec productSpec, Sku sku, String produceUrl) {
		mProductSpec = productSpec;
		mSku = sku;
		mGridViewListener = cridViewListener;
		mBookDetailActivity = bookDetailActivity;
		Window dialogWindow = getWindow();
		dialogWindow.requestFeature(Window.FEATURE_NO_TITLE);
		dialogWindow.setBackgroundDrawable(bookDetailActivity.getResources().getDrawable(color.transparent));
		setContentView(R.layout.dialog_post_select);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.BOTTOM);
		dialogWindow.setWindowAnimations(R.style.book_detail_dailog);
		int height = getScreenHeight(bookDetailActivity) * 3 / 4;

		dialogWindow.setLayout(getScreenWidth(bookDetailActivity), height);
		dialogWindow.setAttributes(lp);

		ListView listView = (ListView) findViewById(R.id.content_list);
		mMyListAdapter = new MyListAdapter();
		listView.setAdapter(mMyListAdapter);

		findViewById(R.id.textview_minus).setOnClickListener(this);
		findViewById(R.id.textview_plus).setOnClickListener(this);
		findViewById(R.id.close_icon).setOnClickListener(this);

		textview_account = (EditText) findViewById(R.id.textview_account);
		textview_account.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				if (!"".equals(s.toString())) {
					beforeCount = Integer.parseInt(s.toString());
				} else {
					beforeCount = 1;
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!"".equals(s.toString()) && Integer.parseInt(s.toString()) > mSku.getCurrentNum()
						&& textview_account != null) {
					textview_account.setText(beforeCount + "");
					textview_account.setSelection(textview_account.getText().toString().length());
					return;
				} else {
				}
				if ("".equals(s.toString())) {
					textview_account.setText("1");

				}
				textview_account.setSelection(textview_account.getText().toString().length());
				setBuyNum();
			}
		});

		ImageView imageView = (ImageView) findViewById(R.id.image_icon);
		new BitmapUtil().displayImage(mBookDetailActivity, imageView, produceUrl);

		mStockNum = (TextView) findViewById(R.id.stock_num);
		add_cart = (Button) findViewById(R.id.add_cart);
		add_cart.setOnClickListener(this);
		setCanAddCart();

		mTextVPrice = (TextView) findViewById(R.id.price);
		mProductNum = (TextView) findViewById(R.id.product_num);
		updateSku();
		show();
	}

	public void setCanAddCart() {
		if (mBookDetailActivity.mProductDetail.getSku().getCurrentNum() <= 0 || !mBookDetailActivity.mIScanAddCart
				|| "N".equals(mBookDetailActivity.mProductDetail.getIsOnSale())) {
			add_cart.setEnabled(false);
			add_cart.setBackgroundResource(R.color.dark_gray);
		} else {
			add_cart.setEnabled(true);
			add_cart.setBackgroundResource(R.color.common_red_bg);
		}
	}
	

	private void updateSku() {
		int currentNum = mSku.getCurrentNum();
		String storNum = currentNum == 0 ? "0" : "1";
		mStockNum.setText(currentNum + "");
		textview_account.setText(storNum);
		mTextVPrice.setText("¥" + Utils.parseDecimalDouble2(mSku.getSalePrice()));
		mProductNum.setText("商品编号：" + mSku.getStockNo());
	}

	public static int getScreenWidth(Activity activity) {
		WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();

		return display.getWidth();
	}

	public static int getScreenHeight(Activity activity) {
		WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();

		return display.getHeight();
	}

	@Override
	public void onBackPressed() {
		dismiss();
	}

	public class MyListAdapter extends ArrayAdapter<Result> {

		public MyListAdapter() {
			super(mBookDetailActivity, R.layout.row_book_detail_dialog_item);
		}

		@Override
		public int getCount() {
			if (mProductSpec == null) {
				return 0;
			}

			if (mProductSpec.getResult() == null) {
				return 0;
			}
			return mProductSpec.getResult().size();
		}

		@Override
		public Result getItem(int position) {
			return mProductSpec.getResult().get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			Result result = getItem(position);
			if (convertView == null || convertView.getTag() == null) {
				LayoutInflater inflater = getLayoutInflater();
				convertView = (LinearLayout) inflater.inflate(R.layout.row_book_detail_dialog_item, null);
				viewHolder = new ViewHolder();
				viewHolder.gridView = (GridView) convertView.findViewById(R.id.gridview);
				viewHolder.title = (TextView) convertView.findViewById(R.id.title);
				convertView.setTag(viewHolder);

			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.title.setText(result.getSpecNm());
			viewHolder.gridView.setAdapter(new MyGridAdapter(position));
			return convertView;
		}

	}

	static class ViewHolder {
		GridView gridView;
		TextView title;
	}

	public class MyGridAdapter extends ArrayAdapter<SpecValue> {

		private int mPosition;

		public MyGridAdapter(int position) {
			super(mBookDetailActivity, R.layout.row_book_detail_dialog_item);
			mPosition = position;
		}

		@Override
		public int getCount() {

			if (mProductSpec.getResult().get(mPosition) == null) {
				return 0;
			}
			if (mProductSpec.getResult().get(mPosition).getSpecValues() == null) {
				return 0;
			}

			return mProductSpec.getResult().get(mPosition).getSpecValues().size();
		}

		@Override
		public SpecValue getItem(int position) {
			return mProductSpec.getResult().get(mPosition).getSpecValues().get(position);
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolderGrid viewHolder = null;

			SpecValue specValue = mProductSpec.getResult().get(mPosition).getSpecValues().get(position);

			if (convertView == null || convertView.getTag() == null) {
				LayoutInflater inflater = getLayoutInflater();
				convertView = inflater.inflate(R.layout.row_book_detail_dialog_grid, null);
				viewHolder = new ViewHolderGrid();
				viewHolder.content = (CheckBox) convertView.findViewById(R.id.content);
				viewHolder.root_view = convertView.findViewById(R.id.root_view);
				convertView.setTag(viewHolder);

			} else {
				viewHolder = (ViewHolderGrid) convertView.getTag();
			}
			viewHolder.content.setText(specValue.getSpecValueNm());
			viewHolder.content.setChecked(specValue.getChecked());
			if (specValue.getChecked()) {
				viewHolder.root_view.setOnClickListener(null);
			} else {
				viewHolder.root_view.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						StringBuffer specvals = new StringBuffer();
						for (int i = 0; i < mProductSpec.getResult().size(); i++) {
							Result result = mProductSpec.getResult().get(i);
							List<SpecValue> specValues = result.getSpecValues();
							SpecValue specValue = null;
							if (i == mPosition) {
								specValue = specValues.get(position);
							} else {
								for (int j = 0; j < specValues.size(); j++) {
									SpecValue specValueTemp = specValues.get(j);
									if (specValueTemp.getChecked()) {
										specValue = specValueTemp;
										break;
									}
								}
							}

							if (specValue != null) {
								specvals.append(specValue.getSpecId());
								specvals.append(",");
								specvals.append(specValue.getSpecValueId());
								specvals.append(";");
							}
						}

						if (specvals.length() > 0) {
							specvals = specvals.deleteCharAt(specvals.length() - 1);
							getSkuBySpec(specvals.toString());
						}
					}
				});
			}
			return convertView;
		}

	}

	static class ViewHolderGrid {
		CheckBox content;
		View root_view;
	}

	public interface GridViewListener {
		public void onGridSelect(Sku sku, int buyNum);

	}

	public static int getColor(Context context, int colorId) {
		Resources resources = context.getResources();
		return resources.getColor(colorId);
	}

	/**
	 * 点击取消按钮
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.textview_plus:
			if (!Utils.checkStr(textview_account.getText().toString()).equals("")) {
				int a = Integer.parseInt(Utils.checkStr(textview_account.getText().toString()));
				if (a + 1 > mSku.getCurrentNum()) {
					(mBookDetailActivity).showToast("您选择的数量已超过库存，请重新选择");
					return;
				} else {
					textview_account.setText((a + 1) + "");
					textview_account.setSelection(textview_account.getText().toString().length());
				}
			}
			setBuyNum();
			break;
		case R.id.textview_minus:
			if (!Utils.checkStr(textview_account.getText().toString()).equals("")) {
				int b = Integer.parseInt(Utils.checkStr(textview_account.getText().toString()));
				if (b > 1) {
					textview_account.setText((b - 1) + "");
					textview_account.setSelection(textview_account.getText().toString().length());
				}
			}
			setBuyNum();
			break;
		case R.id.add_cart:
			(mBookDetailActivity).addTocart();
			dismiss();
			break;
		case R.id.close_icon:
			dismiss();
			break;
		}
	}

	private void setBuyNum() {
		setBuyNum(null);
	}

	private void setBuyNum(Sku sku) {
		String numSrc = textview_account.getText().toString();
		int num = Integer.parseInt(numSrc.trim());
		mGridViewListener.onGridSelect(sku, num);
	}

	/**
	 * 点击规格获取不同的颜色
	 */
	private void getSkuBySpec(String specvals) {
		BaseTask task = new BaseTask(mBookDetailActivity);
		RequestParams params = new RequestParams();
		params.addBodyParameter("productId", mBookDetailActivity.mProductId);
		params.addBodyParameter("specvals", specvals);
		mBookDetailActivity.showDialog("选择商品规格中");
		task.askNormalRequest(SystemConfig.GET_PRODUCT_SKU, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					mBookDetailActivity.dismissDialog();
					Log.e("商品规格", arg0.result.toString());
					mProductSpec = JSON.parseObject(arg0.result, ProductSpec.class);
					Sku sku = mProductSpec.getSku();
					if (sku != null) {
						mSku = sku;
						updateSku();
						setBuyNum(mSku);
						mMyListAdapter.notifyDataSetChanged();
						setCanAddCart();
					} else {
						mBookDetailActivity.showToast("选择商品规格失败");
					}

				} catch (Exception e) {
					mBookDetailActivity.dismissDialog();
					mBookDetailActivity.showToast("选择商品规格失败");
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				mBookDetailActivity.dismissDialog();
				mBookDetailActivity.showToast("选择商品规格失败");
			}

		});
	}
}
