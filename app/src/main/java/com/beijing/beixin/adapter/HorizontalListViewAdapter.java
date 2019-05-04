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
import com.beijing.beixin.pojo.AppClientModuleObjectVo;
import com.beijing.beixin.pojo.ProductlistBean;
import com.beijing.beixin.ui.StartActivity;
import com.beijing.beixin.ui.classify.ProductListActivity;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.utils.BitmapUtil;

public class HorizontalListViewAdapter extends BaseAdapter {

	private Context context;// 上下文
	private List<AppClientModuleObjectVo> list;
	// private ViewHolder vh = new ViewHolder();
	private boolean mIsClick;
	private String title;

	public HorizontalListViewAdapter(Context con, boolean isClick) {
		context = con;
		mInflater = LayoutInflater.from(con);
		mIsClick = isClick;
	}

	public HorizontalListViewAdapter(Context con) {
		this(con, true);
	}

	public void setData(List<AppClientModuleObjectVo> list, String title) {
		this.list = list;
		this.title = title;
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
		if (!mIsClick) {
			return getViewTag(position, convertView, parent);
		}
		ViewHolder vh = null;
		if (convertView == null || convertView.getTag() == null) {
			vh = new ViewHolder();
			convertView = mInflater.inflate(R.layout.horizontallistview_item, null);
			vh.im = (ImageView) convertView.findViewById(R.id.iv_pic);
			vh.title = (TextView) convertView.findViewById(R.id.tv_name);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		String picUrl = list.get(position).getPicUrl();
		BitmapUtil util = new BitmapUtil();
		util.displayImage(context, vh.im, picUrl);
		// vh.title.setText("一品芝麻狐");
		vh.title.setText(list.get(position).getProduct().getProductNm());
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onShowDetail(position);
			}
		});
		return convertView;
	}

	public void onShowDetail(int position) {
		Intent intente = new Intent(context, ProductListActivity.class);
		if (list.get(position).getProduct().getSkuList() != null) {
			intente.putExtra("skuId", list.get(position).getProduct().getSkuList().get(0).getSkuId() + "");
		}
		intente.putExtra("titleType", "0");
		intente.putExtra("title", title);
		context.startActivity(intente);
	}

	private static class ViewHolder {

		private TextView title;
		private ImageView im;
	}

	private View getViewTag(final int position, View convertView, ViewGroup parent) {
		ViewHolderTag viewHolderTag;
		if (convertView == null || convertView.getTag() == null) {
			convertView = mInflater.inflate(R.layout.horizontallistview_item, null);
			viewHolderTag = new ViewHolderTag();
			viewHolderTag.im = (ImageView) convertView.findViewById(R.id.iv_pic);
			viewHolderTag.title = (TextView) convertView.findViewById(R.id.tv_name);
			convertView.setTag(viewHolderTag);
		} else {
			viewHolderTag = (ViewHolderTag) convertView.getTag();
		}
		String picUrl = list.get(position).getPicUrl();
		BitmapUtil util = new BitmapUtil();
		util.displayImage(context, viewHolderTag.im, picUrl);
		viewHolderTag.title.setText(list.get(position).getProduct().getProductNm());
		return convertView;
	}

	private static class ViewHolderTag {

		private TextView title;
		private ImageView im;
	}
}