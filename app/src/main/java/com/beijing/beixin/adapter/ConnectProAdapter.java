package com.beijing.beixin.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.beixin.R;
import com.beijing.beixin.adapter.AddresslistAdater.ViewHolder;
import com.beijing.beixin.pojo.detailBean;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.Utils;

public class ConnectProAdapter extends BaseAdapter {
	private Context context;// 上下文
	private List<detailBean> list;
	private boolean mIsClick;

	public ConnectProAdapter(Context con, boolean isClick) {
		context = con;
		mInflater = LayoutInflater.from(con);
		mIsClick = isClick;
	}

	public ConnectProAdapter(Context con) {
		this(con, true);
	}

	public void setData(List<detailBean> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		if (list == null) {
			return 0;
		}
		return list.size();
	}

	private LayoutInflater mInflater;

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (!mIsClick) {
			return getViewTag(position, convertView, parent);
		}

		if (convertView == null || convertView.getTag() == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.connect_product_item, null);
			holder.im = (ImageView) convertView.findViewById(R.id.iv_pic);
			holder.title = (TextView) convertView.findViewById(R.id.tv_name);
			holder.price = (TextView) convertView.findViewById(R.id.tv_price);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String picUrl = list.get(position).getImage();
		BitmapUtil util = new BitmapUtil();
		util.displayImage(context, holder.im, picUrl);
		holder.title.setText(list.get(position).getProductNm());
		holder.price.setText("¥" + Utils.parseDecimalDouble2(list.get(position).getUnitPrice()));

		// convertView.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// onShowDetail(position);
		// }
		// });

		return convertView;
	}

	public void onShowDetail(int position) {
		Intent intente = new Intent(context, BookDetailActivity.class);
		intente.putExtra("ProductId", list.get(position).getProductId() + "");
		context.startActivity(intente);
	}

	private static class ViewHolder {

		private TextView title;
		private ImageView im;
		private TextView price;
	}

	private static class ViewHolderTag {

		private TextView title;
		private ImageView im;
		private TextView price;
	}

	private View getViewTag(final int position, View convertView, ViewGroup parent) {
		ViewHolderTag viewHolderTag;
		if (convertView == null || convertView.getTag() == null) {
			convertView = mInflater.inflate(R.layout.horizontallistview_item, null);
			viewHolderTag = new ViewHolderTag();
			viewHolderTag.im = (ImageView) convertView.findViewById(R.id.iv_pic);
			viewHolderTag.title = (TextView) convertView.findViewById(R.id.tv_name);
			viewHolderTag.price = (TextView) convertView.findViewById(R.id.tv_price);
			convertView.setTag(viewHolderTag);
		} else {
			viewHolderTag = (ViewHolderTag) convertView.getTag();
		}
		String picUrl = list.get(position).getImage();
		BitmapUtil util = new BitmapUtil();
		util.displayImage(context, viewHolderTag.im, picUrl);
		viewHolderTag.title.setText(list.get(position).getProductNm());
		viewHolderTag.price.setText("¥" + Utils.parseDecimalDouble2(list.get(position).getMarketPrice()));

		return convertView;
	}
}