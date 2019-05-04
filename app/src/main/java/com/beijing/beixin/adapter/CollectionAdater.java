package com.beijing.beixin.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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
import com.beijing.beixin.pojo.ProductCollectionBean;
import com.beijing.beixin.pojo.ProductlistBean;
import com.beijing.beixin.ui.myself.MyCollectionActivity;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.Utils;
import com.lidroid.xutils.BitmapUtils;

/**
 * 
 * @author ouyanghao
 * 
 */
public class CollectionAdater extends BaseAdapter {

	private Context context;// 上下文
	private List<ProductCollectionBean> list;
	private BitmapUtil bitmapUtil = null;
	private ListItemClickHelp callback;

	public CollectionAdater(Context context, ListItemClickHelp callback) {
		super();
		this.context = context;
		bitmapUtil = new BitmapUtil();
		this.callback = callback;
	}

	public void setData(List<ProductCollectionBean> list) {
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
			convertView = View.inflate(context, com.beijing.beixin.R.layout.item_product_collection, null);
			holder.iv_bookimage = (ImageView) convertView.findViewById(R.id.iv_bookimage);
			holder.tv_bookname = (TextView) convertView.findViewById(R.id.tv_bookname);
			holder.tv_bookprice = (TextView) convertView.findViewById(R.id.tv_bookprice);
			holder.tv_old_price = (TextView) convertView.findViewById(R.id.tv_old_price);
			holder.tv_flagshipstore = (TextView) convertView.findViewById(R.id.tv_flagshipstore);
			holder.tv_baoyou = (TextView) convertView.findViewById(R.id.tv_baoyou);
			holder.tv_songquan = (TextView) convertView.findViewById(R.id.tv_songquan);
			holder.tv_zengpin = (TextView) convertView.findViewById(R.id.tv_zengpin);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		bitmapUtil.displayImage(context, holder.iv_bookimage, list.get(position).getImage());
		holder.tv_bookname.setText(list.get(position).getProductNm());
		holder.tv_bookprice.setText("¥" + Utils.parseDecimalDouble2(list.get(position).getUnitPrice()));
		holder.tv_old_price.setText("¥" + Utils.parseDecimalDouble2(list.get(position).getMarketPrice()));
		holder.tv_old_price.getPaint().setAntiAlias(true);// 抗锯齿
		holder.tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
		holder.tv_flagshipstore.setText(list.get(position).getShop().getShopNm());

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
		return convertView;
	}

	class ViewHolder {

		TextView tv_bookname;
		ImageView iv_bookimage;
		TextView tv_bookprice;
		TextView tv_old_price;
		TextView tv_flagshipstore;
		TextView tv_baoyou;
		TextView tv_songquan;
		TextView tv_zengpin;
	}
}
