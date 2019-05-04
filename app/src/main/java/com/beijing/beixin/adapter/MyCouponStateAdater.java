package com.beijing.beixin.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.beixin.R;
import com.beijing.beixin.pojo.MoreShopBean;
import com.beijing.beixin.pojo.MyCouponBean;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.Utils;

/**
 * 优惠券列表
 * 
 * @author ouyanghao
 * 
 */
public class MyCouponStateAdater extends BaseAdapter {

	private Context context;// 上下文
	private List<MyCouponBean.coupon> list;
	private BitmapUtil bitmapUtil = null;
	private ListItemClickHelp callback;

	public MyCouponStateAdater(Context context) {
		super();
		this.context = context;
		bitmapUtil = new BitmapUtil();
	}

	public void setData(List<MyCouponBean.coupon> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup arg2) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, com.beijing.beixin.R.layout.item_coupon, null);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_name.setText(list.get(position).getBatchNm());
		holder.tv_count.setText("¥" + Utils.parseDecimalDouble2(list.get(position).getAmount()));
		if (list.get(position).getDateStr() == null) {
			holder.tv_time.setText("使用期限" + list.get(position).getEndTimeString());
		} else {
			holder.tv_time.setText(list.get(position).getDateStr());
		}
		return convertView;
	}

	class ViewHolder {

		TextView tv_name;
		TextView tv_time;
		TextView tv_count;
	}
}
