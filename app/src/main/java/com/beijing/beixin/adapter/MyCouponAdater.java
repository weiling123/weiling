package com.beijing.beixin.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.beixin.R;
import com.beijing.beixin.pojo.MyCouponBean;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.Utils;

/**
 * 优惠券列表
 * 
 * @author ouyanghao
 * 
 */
public class MyCouponAdater extends BaseAdapter {

	private Context context;// 上下文
	private List<MyCouponBean.coupon> list;
	private BitmapUtil bitmapUtil = null;
	private ListItemClickHelp callback;
	private String mCouponId;

	public MyCouponAdater(Context context) {
		super();
		this.context = context;
		bitmapUtil = new BitmapUtil();
	}

	public void setData(List<MyCouponBean.coupon> list, String couponId) {
		this.list = list;
		this.mCouponId = couponId;
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
			convertView = View.inflate(context, com.beijing.beixin.R.layout.item_coupon, null);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.layout_coupon = (LinearLayout) convertView.findViewById(R.id.layout_coupon);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (!"".equals(mCouponId)) {
			holder.layout_coupon.setBackgroundResource(R.drawable.coupon_selected);
			holder.tv_name.setText("取消使用    " + list.get(position).getBatchNm());
		} else {
			holder.layout_coupon.setBackgroundResource(R.drawable.coupon);
			holder.tv_name.setText(list.get(position).getBatchNm());
		}
		holder.tv_count.setText("¥" + Utils.parseDecimalDouble2(list.get(position).getAmount()));
		if (list.get(position).getDateStr() == null) {
			holder.tv_time.setText("使用期限" + list.get(position).getEndTimeString());
		} else {
			holder.tv_time.setText(list.get(position).getDateStr());
		}
		return convertView;
	}

	class ViewHolder {
		LinearLayout layout_coupon;
		TextView tv_name;
		TextView tv_time;
		TextView tv_count;
	}
}
