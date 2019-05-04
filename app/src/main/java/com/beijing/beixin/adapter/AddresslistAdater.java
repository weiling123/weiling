package com.beijing.beixin.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.beixin.R;
import com.beijing.beixin.pojo.AddressBean;
import com.beijing.beixin.utils.BitmapUtil;

/**
 * 地址列表
 * 
 * @author ouyanghao
 * 
 */
@SuppressWarnings("unused")
public class AddresslistAdater extends BaseAdapter {

	private Context context;// 上下文
	private List<AddressBean> list;
	private BitmapUtil bitmapUtil = null;
	private ListItemClickHelp callback;

	public AddresslistAdater(Context context, ListItemClickHelp callback) {
		super();
		this.context = context;
		bitmapUtil = new BitmapUtil();
		this.callback = callback;
	}

	public void setData(List<AddressBean> list) {
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
	public View getView(int position, View convertView, final ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_address, null);
			holder.iv_default_addess = (ImageView) convertView.findViewById(R.id.iv_default_address);
			holder.addess_name = (TextView) convertView.findViewById(R.id.address_name);
			holder.addess_tel = (TextView) convertView.findViewById(R.id.address_tel);
			holder.addess = (TextView) convertView.findViewById(R.id.address);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if ("Y".equals(list.get(position).getIsDefault().toString())) {
			holder.iv_default_addess.setImageResource(R.drawable.select_r);
		} else {
			holder.iv_default_addess.setImageResource(R.drawable.select_g);
		}
		holder.addess_name.setText(list.get(position).getName());
		holder.addess_tel.setText(list.get(position).getMobile());
		holder.addess.setText(list.get(position).getAddr());
		final View view = convertView;
		final int pos = position;
		final int one = holder.iv_default_addess.getId();
		holder.iv_default_addess.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				callback.onItemClick(view, arg2, pos, one);
			}
		});
		return convertView;
	}

	class ViewHolder {
		ImageView iv_default_addess;
		TextView addess_name;
		TextView addess_tel;
		TextView addess;
	}
}
