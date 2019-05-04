package com.beijing.beixin.adapter;

import org.json.JSONException;
import org.json.JSONObject;

import com.beijing.beixin.R;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.NewProductBean.product;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.myself.login.LoginActivity;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.Utils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class HomeSpecialListAdapter extends ArrayAdapter<product> implements OnItemClickListener {

	private product[] mProduct;
	private BaseActivity mActivity;
	private BitmapUtil mBitmapUtil;
	private boolean mIshowNew;

	public HomeSpecialListAdapter(BaseActivity context, int resource, product[] products, boolean isShow) {
		super(context, resource);
		mActivity = context;
		mProduct = products;
		mBitmapUtil = new BitmapUtil();
		mIshowNew = isShow;
	}

	public void OnRefresh(product[] products) {
		mProduct = products;
		notifyDataSetChanged();
	}

	public product[] getProduct() {
		return mProduct;
	}

	@Override
	public int getCount() {
		if (mProduct == null) {
			return 0;
		}
		return mProduct.length;
	}

	@Override
	public product getItem(int position) {
		return mProduct[position];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vHolder = new ViewHolder();
		if (convertView == null) {
			vHolder = new ViewHolder();
			convertView = mActivity.getLayoutInflater().inflate(R.layout.item_home_special_list, null);
			vHolder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
			vHolder.like_im = (ImageView) convertView.findViewById(R.id.iv_histroy);
			vHolder.name = (TextView) convertView.findViewById(R.id.name);
			vHolder.bookstore = (TextView) convertView.findViewById(R.id.bookstore);
			vHolder.be_money = (TextView) convertView.findViewById(R.id.tv_bookprice);
			vHolder.ds_money = (TextView) convertView.findViewById(R.id.tv_old_price);
			vHolder.tv_baoyou = (TextView) convertView.findViewById(R.id.tv_baoyou);
			vHolder.tv_songquan = (TextView) convertView.findViewById(R.id.tv_songquan);
			vHolder.tv_zengpin = (TextView) convertView.findViewById(R.id.tv_zengpin);
			vHolder.ds_money.getPaint().setAntiAlias(true);// 抗锯齿
			vHolder.ds_money.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 中划线
			MyOnClickListener myOnClickListener = new MyOnClickListener(vHolder);
			convertView.findViewById(R.id.iv_histroy).setOnClickListener(myOnClickListener);
			convertView.findViewById(R.id.iv_shopping).setOnClickListener(myOnClickListener);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}
		product product = getItem(position);
		mBitmapUtil.displayImage(mActivity.getApplicationContext(), vHolder.iv_pic, product.getImageLinks());
		vHolder.name.setText(product.getName());
		vHolder.bookstore.setText(product.getShopNm());
		if ("N".equals(product.getIsAttention())) {
			vHolder.like_im.setImageResource(R.drawable.grey_heart);
		} else {
			vHolder.like_im.setImageResource(R.drawable.red_heart);
		}
		vHolder.be_money.setText("¥" + Utils.parseDecimalDouble2(product.getPrice().getUnitPrice()));
		vHolder.ds_money.setText("¥" + Utils.parseDecimalDouble2(product.getMarketPrice()));
		vHolder.position = position;

		String name = "";
		for (int i = 0; i < product.getAppProductBusinessRule().size(); i++) {
			name = name + product.getAppProductBusinessRule().get(i);
		}
		if (name.contains("包邮")) {
			vHolder.tv_baoyou.setVisibility(View.VISIBLE);
		} else {
			vHolder.tv_baoyou.setVisibility(View.GONE);
		}
		if (name.contains("券")) {
			vHolder.tv_songquan.setVisibility(View.VISIBLE);
		} else {
			vHolder.tv_songquan.setVisibility(View.GONE);
		}
		if (name.contains("赠品")) {
			vHolder.tv_zengpin.setVisibility(View.VISIBLE);
		} else {
			vHolder.tv_zengpin.setVisibility(View.GONE);
		}
		return convertView;
	}

	private static class ViewHolder {

		ImageView iv_pic;
		ImageView like_im;
		TextView name;
		TextView bookstore;
		TextView be_money;
		TextView af_money;
		TextView ds_money;
		TextView tv_baoyou;
		TextView tv_songquan;
		TextView tv_zengpin;
		View newBook;
		int position;
	}

	/**
	 * 加入购物车
	 */
	public void addTocart(product prod) {
		BaseTask task = new BaseTask(mActivity);
		task.addShoppingCard(prod.getShopInfId(), prod.getSkuId(), new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				android.util.Log.e("加入购物车", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					if (obj.getBoolean("success")) {
						mActivity.showToast("加入购物车成功");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				android.util.Log.e("加入购物车异常", arg0.toString());
			}
		});
	}

	/**
	 * 收藏商品
	 */
	public void collectionProduct(String pid, final int position) {
		BaseTask task = new BaseTask(mActivity);
		RequestParams params = new RequestParams();
		params.addBodyParameter("productId", pid);
		task.askCookieRequest(SystemConfig.COLLECTION_PRODUCT, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// Log.e("collectionProduct", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					if (obj.getBoolean("success")) {
						mActivity.showToast("收藏商品成功");
						mProduct[position].setIsAttention("Y");
						notifyDataSetChanged();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				android.util.Log.e("收藏商品异常", arg0.toString());
			}
		});
	}

	/**
	 * 取消收藏商品
	 */
	public void cancelCollectionProduct(String pid, final int position) {
		BaseTask task = new BaseTask(mActivity);
		RequestParams params = new RequestParams();
		params.addBodyParameter("productId", pid);
		task.askCookieRequest(SystemConfig.CANCEL_COLLECTION_PRODUCT, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				android.util.Log.e("cancelcollectionProduct", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					if (obj.getBoolean("success")) {
						mActivity.showToast("取消收藏商品成功");
						mProduct[position].setIsAttention("N");
						notifyDataSetChanged();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
			}
		});
	}

	private class MyOnClickListener implements View.OnClickListener {

		ViewHolder mViewHolder;

		MyOnClickListener(ViewHolder viewHolder) {
			mViewHolder = viewHolder;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_histroy:
				if (MyApplication.mapp.getCookieStore() == null) {
					mActivity.startActivity(LoginActivity.class);
					return;
				}
				if ("N".equals(mProduct[mViewHolder.position].getIsAttention())) {
					collectionProduct(mProduct[mViewHolder.position].getProductId(), mViewHolder.position);
				} else {
					cancelCollectionProduct(mProduct[mViewHolder.position].getProductId(), mViewHolder.position);
				}
				break;
			case R.id.iv_shopping:
				addTocart(mProduct[mViewHolder.position]);
				break;
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(mActivity, BookDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("ProductId", mProduct[position].getProductId() + "");
		intent.putExtras(bundle);
		mActivity.startActivity(intent);
	}
}
