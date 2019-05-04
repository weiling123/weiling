package com.beijing.beixin.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.beijing.beixin.R;
import com.beijing.beixin.pojo.AppShopBean;
import com.beijing.beixin.ui.shoppingcart.ShopActivity;
import com.beijing.beixin.utils.BitmapUtil;

/**
 * 
 * @author ouyanghao
 * 
 */
public class CollectionShopAdater extends BaseAdapter {

	private Context context;// 上下文
	private List<AppShopBean> list;
	private BitmapUtil bitmapUtil = null;
	private ListItemClickHelp callback;

	public CollectionShopAdater(Context context, ListItemClickHelp callback) {
		super();
		this.context = context;
		bitmapUtil = new BitmapUtil();
		this.callback = callback;
	}

	public void setData(List<AppShopBean> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup arg2) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, com.beijing.beixin.R.layout.item_shop_collection, null);
			holder.iv_shop_image = (ImageView) convertView.findViewById(R.id.iv_shop_image);
			holder.tv_shop_name = (TextView) convertView.findViewById(R.id.tv_shop_name);
			holder.tv_promotion = (TextView) convertView.findViewById(R.id.tv_promotion);
			holder.tv_new = (TextView) convertView.findViewById(R.id.tv_new);
			holder.sellerAverage = (TextView) convertView.findViewById(R.id.sellerAverage);
			holder.attentionShopCount = (TextView) convertView.findViewById(R.id.attentionShopCount);
			holder.app_ratingbar = (RatingBar) convertView.findViewById(R.id.app_ratingbar);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		bitmapUtil.displayImage(context, holder.iv_shop_image, list.get(position).getLogoUrl());
		holder.tv_shop_name.setText(list.get(position).getShopNm());
		if (list.get(position).getAttentionShopCount() != null) {
			holder.attentionShopCount.setText(list.get(position).getAttentionShopCount() + "人关注");
		}
		if (list.get(position).getShopRatingAvgVo().getSellerAverage() != null) {
			holder.sellerAverage.setText(list.get(position).getShopRatingAvgVo().getSellerAverage() + "分");
			holder.app_ratingbar
					.setRating(Float.parseFloat(list.get(position).getShopRatingAvgVo().getSellerAverage()));
		}
		if (list.get(position).getPromotionProductCount() > 0) {
			holder.tv_promotion.setVisibility(View.VISIBLE);
		} else {
			holder.tv_promotion.setVisibility(View.GONE);
		}
		if (list.get(position).getNewProductCount() > 0) {
			holder.tv_new.setVisibility(View.VISIBLE);
		} else {
			holder.tv_new.setVisibility(View.GONE);
		}
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, ShopActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("shopId", list.get(position).getShopInfId() + "");
				intent.putExtras(bundle);
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	class ViewHolder {

		ImageView iv_shop_image;
		TextView tv_shop_name;
		TextView tv_promotion;
		TextView tv_new;
		TextView sellerAverage;
		TextView attentionShopCount;
		RatingBar app_ratingbar;
	}
}
