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
import android.widget.TextView;

import com.beijing.beixin.R;
import com.beijing.beixin.pojo.AdvertisingBean;
import com.beijing.beixin.pojo.NewProductBean;
import com.beijing.beixin.pojo.ProductlistBean;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.Utils;

/**
 * 新品上架
 * 
 * @author ouyanghao
 * 
 */
public class NewProductAdater extends BaseAdapter {

	private Context context;// 上下文
	private List<NewProductBean.product> list;
	private BitmapUtil bitmapUtil = null;
	private ListItemClickHelp callback;

	public NewProductAdater(Context context, ListItemClickHelp callback) {
		super();
		this.context = context;
		bitmapUtil = new BitmapUtil();
		this.callback = callback;
	}

	public void setData(List<NewProductBean.product> list) {
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
			convertView = View.inflate(context, com.beijing.beixin.R.layout.item_product_list, null);
			holder.iv_bookimage = (ImageView) convertView.findViewById(R.id.iv_bookimage);
			holder.tv_bookname = (TextView) convertView.findViewById(R.id.tv_bookname);
			holder.tv_bookprice = (TextView) convertView.findViewById(R.id.tv_bookprice);
			holder.tv_old_price = (TextView) convertView.findViewById(R.id.tv_old_price);
			holder.tv_morebyhome = (TextView) convertView.findViewById(R.id.tv_morebyhome);
			holder.iv_histroy = (ImageView) convertView.findViewById(R.id.iv_histroy);
			holder.iv_shopping = (ImageView) convertView.findViewById(R.id.iv_shopping);
			holder.tv_flagshipstore = (TextView) convertView.findViewById(R.id.tv_flagshipstore);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		bitmapUtil.displayImage(context, holder.iv_bookimage, list.get(position).getImageLinks());
		holder.tv_bookname.setText(list.get(position).getName());
		holder.tv_bookprice.setText("¥" + Utils.parseDecimalDouble2(list.get(position).getPrice().getUnitPrice()));
		holder.tv_old_price.setText("¥" + Utils.parseDecimalDouble2(list.get(position).getMarketPrice()));
		holder.tv_flagshipstore.setText(list.get(position).getShopNm());
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
		final int three = holder.tv_morebyhome.getId();
		holder.tv_morebyhome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				callback.onItemClick(view, arg2, pos, three);
			}
		});
		return convertView;
	}

	class ViewHolder {

		ImageView iv_bookimage;
		ImageView iv_histroy;
		ImageView iv_shopping;
		TextView tv_pid;
		TextView tv_bookname;
		TextView tv_bookprice;
		TextView tv_old_price;
		TextView tv_morebyhome;
		TextView tv_flagshipstore;
	}
}
