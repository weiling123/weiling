package com.beijing.beixin.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.beixin.R;
import com.beijing.beixin.pojo.MoreShopBean;
import com.beijing.beixin.pojo.ProductMoreBean;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.Utils;

/**
 * 商品列表适配器
 * 
 * @author ouyanghao
 * 
 */
public class MoreShopAdater extends BaseAdapter {

	private Context context;// 上下文
	private List<ProductMoreBean> list;
	private BitmapUtil bitmapUtil = null;
	private ListItemClickHelp callback;

	public MoreShopAdater(Context context, ListItemClickHelp callback) {
		super();
		this.context = context;
		bitmapUtil = new BitmapUtil();
		this.callback = callback;
	}

	public void setData(List<ProductMoreBean> list) {
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
			convertView = View.inflate(context, com.beijing.beixin.R.layout.item_more_product, null);
			holder.iv_bookimage = (ImageView) convertView.findViewById(R.id.iv_bookimage);
			holder.iv_histroy = (ImageView) convertView.findViewById(R.id.iv_histroy);
			holder.iv_shopping = (ImageView) convertView.findViewById(R.id.iv_shopping);
			holder.tv_bookname = (TextView) convertView.findViewById(R.id.tv_bookname);
			holder.tv_shopname = (TextView) convertView.findViewById(R.id.tv_shopname);
			holder.tv_old_price = (TextView) convertView.findViewById(R.id.tv_old_price);
			holder.be_money = (TextView) convertView.findViewById(R.id.tv_bookprice);
			holder.sales = (TextView) convertView.findViewById(R.id.sales);
			holder.tv_baoyou = (TextView) convertView.findViewById(R.id.tv_baoyou);
			holder.tv_songquan = (TextView) convertView.findViewById(R.id.tv_songquan);
			holder.tv_zengpin = (TextView) convertView.findViewById(R.id.tv_zengpin);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		bitmapUtil.displayImage(context, holder.iv_bookimage, list.get(position).getImage());
		holder.tv_shopname.setText(list.get(position).getShop().getShopNm());
		holder.tv_bookname.setText(list.get(position).getProductNm());
		holder.sales.setText(list.get(position).getSalesVolume());

		holder.tv_old_price.setText("¥" + Utils.parseDecimalDouble2(list.get(position).getMarketPrice()));
		holder.tv_old_price.getPaint().setAntiAlias(true);// 抗锯齿
		holder.tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰

		if ("N".equals(list.get(position).getIsAttention())) {
			holder.iv_histroy.setImageResource(R.drawable.grey_heart);
		} else {
			holder.iv_histroy.setImageResource(R.drawable.red_heart);
		}
		String name = "";
		for (int i = 0; i < list.get(position).getAppProductBusinessRule().size(); i++) {
			name = name + list.get(position).getAppProductBusinessRule().get(i);
		}
		if (name.contains("包邮")) {
			holder.tv_baoyou.setVisibility(View.VISIBLE);
		} else {
			holder.tv_baoyou.setVisibility(View.GONE);
		}
		if (name.contains("券")) {
			holder.tv_songquan.setVisibility(View.VISIBLE);
		} else {
			holder.tv_songquan.setVisibility(View.GONE);
		}
		if (name.contains("赠品")) {
			holder.tv_zengpin.setVisibility(View.VISIBLE);
		} else {
			holder.tv_zengpin.setVisibility(View.GONE);
		}
		holder.be_money.setText("¥" + Utils.parseDecimalDouble2(list.get(position).getUnitPrice()));
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, BookDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("ProductId", list.get(position).getProductId() + "");
				intent.putExtras(bundle);
				context.startActivity(intent);
			}
		});
		final View view = convertView;
		final int pos = position;
		final int one = holder.iv_histroy.getId();
		holder.iv_histroy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				callback.onItemClick(view, arg2, pos, one);
			}
		});
		final int two = holder.iv_shopping.getId();
		holder.iv_shopping.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				callback.onItemClick(view, arg2, pos, two);
			}
		});
		return convertView;
	}

	class ViewHolder {

		ImageView iv_bookimage;
		ImageView iv_histroy;
		ImageView iv_shopping;
		TextView tv_bookname;
		TextView tv_shopname;
		TextView tv_old_price;
		TextView be_money;
		TextView af_money;
		TextView sales;
		TextView tv_baoyou;
		TextView tv_songquan;
		TextView tv_zengpin;
	}
}
