package com.beijing.beixin.adapter;

import java.util.List;

import com.beijing.beixin.R;
import com.beijing.beixin.adapter.ClassifyRightListViewAdapter.historyCommentHolder;
import com.beijing.beixin.pojo.ClassifyRightBean;
import com.beijing.beixin.pojo.ShopBean;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.Utils;
import com.lidroid.xutils.BitmapUtils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopAdater extends BaseAdapter {

	Activity context;
	private List<ShopBean.shopproduct> list;
	private BitmapUtil bitmapUtil = null;
	private ListItemClickHelp callback;

	public ShopAdater(Activity context, ListItemClickHelp callback) {
		super();
		this.context = context;
		bitmapUtil = new BitmapUtil();
		this.callback = callback;
	}

	public void setData(List<ShopBean.shopproduct> list) {
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
	public View getView(int position, View convertView, final ViewGroup parent) {
		HolderView myHolder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_shop, null);
			myHolder = new HolderView();
			myHolder.iv_bookimage = (ImageView) convertView.findViewById(R.id.iv_bookimage);
			myHolder.tv_bookname = (TextView) convertView.findViewById(R.id.tv_bookname);
			myHolder.tv_bookprice = (TextView) convertView.findViewById(R.id.tv_bookprice);
			myHolder.tv_old_price = (TextView) convertView.findViewById(R.id.tv_old_price);
			myHolder.iv_histroy = (ImageView) convertView.findViewById(R.id.iv_histroy);
			myHolder.iv_shopping = (ImageView) convertView.findViewById(R.id.iv_shopping);
			myHolder.tv_baoyou = (TextView) convertView.findViewById(R.id.tv_baoyou);
			myHolder.tv_songquan = (TextView) convertView.findViewById(R.id.tv_songquan);
			myHolder.tv_zengpin = (TextView) convertView.findViewById(R.id.tv_zengpin);
			convertView.setTag(myHolder);
		} else {
			myHolder = (HolderView) convertView.getTag();
		}
		bitmapUtil.displayImage(context, myHolder.iv_bookimage, list.get(position).getProductImgUrl());
		myHolder.tv_bookname.setText(list.get(position).getProductNm());
		myHolder.tv_bookprice.setText("¥" + Utils.parseDecimalDouble2(list.get(position).getUnitPrice()));
		myHolder.tv_old_price.setText("¥" + Utils.parseDecimalDouble2(list.get(position).getUnitPrice()));

		String name = "";
		// for(int
		// i=0;i<list.get(position).getAppProductBusinessRule().size();i++){
		// name=name+list.get(position).getAppProductBusinessRule().get(i);
		// }
		if (name.contains("包邮")) {
			myHolder.tv_baoyou.setVisibility(View.VISIBLE);
		} else {
			myHolder.tv_baoyou.setVisibility(View.GONE);
		}
		if (name.contains("券")) {
			myHolder.tv_songquan.setVisibility(View.VISIBLE);
		} else {
			myHolder.tv_songquan.setVisibility(View.GONE);
		}
		if (name.contains("赠品")) {
			myHolder.tv_zengpin.setVisibility(View.VISIBLE);
		} else {
			myHolder.tv_zengpin.setVisibility(View.GONE);
		}

		final View view = convertView;
		final int pos = position;
		final int one = myHolder.iv_histroy.getId();
		myHolder.iv_histroy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				callback.onItemClick(view, parent, pos, one);
			}
		});
		final int two = myHolder.iv_shopping.getId();
		myHolder.iv_shopping.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				callback.onItemClick(view, parent, pos, two);
			}
		});
		return convertView;
	}

	class HolderView {

		ImageView iv_bookimage;
		TextView tv_bookname;
		TextView tv_bookprice;
		TextView tv_old_price;
		ImageView iv_histroy;
		ImageView iv_shopping;
		TextView tv_baoyou;
		TextView tv_songquan;
		TextView tv_zengpin;
	}
}
