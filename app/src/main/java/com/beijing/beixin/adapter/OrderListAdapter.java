package com.beijing.beixin.adapter;

import java.util.List;

import com.beijing.beixin.R;
import com.beijing.beixin.pojo.OrderListBean.AppOrderItemVo;
import com.beijing.beixin.pojo.OrderListBean;
import com.beijing.beixin.ui.myself.order.OrderDetailActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.HorizontalListView;
import com.beijing.beixin.utils.Utils;
import com.lidroid.xutils.BitmapUtils;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OrderListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	Activity context;
	private List<OrderListBean> list;
	BitmapUtil bitmapUtil;
	private ListItemClickHelp callback;

	public OrderListAdapter(Activity context, ListItemClickHelp callback) {
		this.mInflater = LayoutInflater.from(context);
		this.context = context;
		this.callback = callback;
		bitmapUtil = new BitmapUtil();
	}

	public void setData(List<OrderListBean> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		historyCommentHolder holder;
		if (convertView == null) {
			holder = new historyCommentHolder();
			convertView = mInflater.inflate(R.layout.item_order_list, null);
			holder.rl_info = (RelativeLayout) convertView.findViewById(R.id.rl_info);
			holder.book_info = (RelativeLayout) convertView.findViewById(R.id.book_info);
			holder.images_info = (HorizontalListView) convertView.findViewById(R.id.images_info);
			holder.imageview_shop_icon = (ImageView) convertView.findViewById(R.id.imageview_shop_icon);
			holder.imageview_commodity_icon = (ImageView) convertView.findViewById(R.id.imageview_commodity_icon);
			holder.textview_shop_name = (TextView) convertView.findViewById(R.id.textview_shop_name);
			holder.textview_vocher_name = (TextView) convertView.findViewById(R.id.textview_vocher_name);
			holder.textview_commodity_name = (TextView) convertView.findViewById(R.id.textview_commodity_name);
			holder.textview_commodity_detail = (TextView) convertView.findViewById(R.id.textview_commodity_detail);
			holder.textview_commodity_price = (TextView) convertView.findViewById(R.id.textview_commodity_price);
			holder.old_price = (TextView) convertView.findViewById(R.id.old_price);
			holder.tv_pay = (TextView) convertView.findViewById(R.id.tv_pay);
			holder.tv_gopay = (TextView) convertView.findViewById(R.id.tv_gopay);
			holder.tv_return = (TextView) convertView.findViewById(R.id.tv_return);
			holder.tv_suried = (TextView) convertView.findViewById(R.id.tv_suried);
			holder.tv_single = (TextView) convertView.findViewById(R.id.tv_single);
			holder.tv_buify = (TextView) convertView.findViewById(R.id.tv_buify);
			convertView.setTag(holder);
		} else {
			holder = (historyCommentHolder) convertView.getTag();
		}
		if (list.get(position).getOrderItemList().size() > 1) {
			holder.rl_info.setVisibility(View.GONE);
			holder.images_info.setVisibility(View.VISIBLE);
			CommonAdapter<OrderListBean.AppOrderItemVo> adapter = new CommonAdapter<OrderListBean.AppOrderItemVo>(
					context, list.get(position).getOrderItemList(), R.layout.item_orders) {

				@Override
				public void convert(ViewHolder helper, AppOrderItemVo item) {
					BitmapUtils util = new BitmapUtils(context);
					util.display(helper.getView(R.id.images), item.getImage());
					helper.getConvertView().setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							Intent intent = new Intent(context, OrderDetailActivity.class);
							intent.putExtra("orderid", list.get(position).getOrderId() + "");
							context.startActivity(intent);
						}
					});
				}
			};
			holder.images_info.setAdapter(adapter);
		}
		if (list.get(position).getOrderItemList().size() == 1) {
			holder.images_info.setVisibility(View.GONE);
			holder.rl_info.setVisibility(View.VISIBLE);
			bitmapUtil.displayImage(context, holder.imageview_commodity_icon,
					list.get(position).getOrderItemList().get(0).getImage());
			holder.textview_commodity_price.setText(
					"¥" + Utils.parseDecimalDouble2(list.get(position).getOrderItemList().get(0).getUnitPrice()));
			holder.old_price.setText("¥" + list.get(position).getOrderItemList().get(0).getUnitPrice());
			holder.textview_commodity_name.setText(list.get(position).getOrderItemList().get(0).getProductNm());
		}
		if ("已取消".equals(list.get(position).getOrderStat())) {
			holder.tv_return.setVisibility(View.GONE);
			holder.tv_gopay.setVisibility(View.GONE);
			holder.tv_buify.setVisibility(View.GONE);
			holder.tv_single.setVisibility(View.GONE);
			holder.tv_suried.setVisibility(View.GONE);
		}
		if ("待付款".equals(list.get(position).getOrderStat())) {
			holder.tv_return.setVisibility(View.VISIBLE);
			holder.tv_gopay.setVisibility(View.VISIBLE);
			holder.tv_buify.setVisibility(View.GONE);
			holder.tv_single.setVisibility(View.GONE);
			holder.tv_suried.setVisibility(View.GONE);
		}
		if ("待发货".equals(list.get(position).getOrderStat())) {
			holder.tv_return.setVisibility(View.GONE);
			holder.tv_gopay.setVisibility(View.GONE);
			holder.tv_buify.setVisibility(View.GONE);
			holder.tv_single.setVisibility(View.GONE);
			holder.tv_suried.setVisibility(View.GONE);
		}
		if ("待收货".equals(list.get(position).getOrderStat())) {
			holder.tv_suried.setVisibility(View.VISIBLE);
			holder.tv_return.setVisibility(View.GONE);
			holder.tv_gopay.setVisibility(View.GONE);
			holder.tv_buify.setVisibility(View.GONE);
			holder.tv_single.setVisibility(View.GONE);
		}
		if ("待评价".equals(list.get(position).getOrderStat())) {
			holder.tv_buify.setVisibility(View.VISIBLE);
			holder.tv_single.setVisibility(View.VISIBLE);
			holder.tv_single.setText("评价晒单");
			holder.tv_return.setVisibility(View.GONE);
			holder.tv_gopay.setVisibility(View.GONE);
			holder.tv_suried.setVisibility(View.GONE);
		}
		if ("已完成".equals(list.get(position).getOrderStat())) {
			holder.tv_buify.setVisibility(View.VISIBLE);
			holder.tv_single.setVisibility(View.VISIBLE);
			holder.tv_single.setText("查看评价");
			holder.tv_return.setVisibility(View.GONE);
			holder.tv_gopay.setVisibility(View.GONE);
			holder.tv_suried.setVisibility(View.GONE);
		}
		if (list.get(position).getShopNm().length() > 12) {
			holder.textview_shop_name.setText(list.get(position).getShopNm().substring(0, 12) + "...");
		} else {
			holder.textview_shop_name.setText(list.get(position).getShopNm());
		}
		holder.textview_vocher_name.setText(list.get(position).getOrderStat());
		holder.textview_commodity_detail.setText(list.get(position).getOrderItemList().get(0).getSpec());
		holder.tv_pay.setText("¥" + Utils.parseDecimalDouble2(list.get(position).getOrderTotalAmount()));
		final View view = convertView;
		final int pos = position;
		/**
		 * 去付款
		 */
		final int tv_gopay = holder.tv_gopay.getId();
		holder.tv_gopay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				callback.onItemClick(view, parent, pos, tv_gopay);
			}
		});
		/**
		 * 取消订单
		 */
		final int tv_return = holder.tv_return.getId();
		holder.tv_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				callback.onItemClick(view, parent, pos, tv_return);
			}
		});
		/**
		 * 确认收货
		 */
		final int tv_suried = holder.tv_suried.getId();
		holder.tv_suried.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				callback.onItemClick(view, parent, pos, tv_suried);
			}
		});
		/**
		 * 晒单评价
		 */
		final int tv_single = holder.tv_single.getId();
		holder.tv_single.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				callback.onItemClick(view, parent, pos, tv_single);
			}
		});
		/**
		 * 再次购买
		 */
		final int tv_buify = holder.tv_buify.getId();
		holder.tv_buify.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				callback.onItemClick(view, parent, pos, tv_buify);
			}
		});
		/**
		 * 订单详情
		 */
		holder.rl_info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, OrderDetailActivity.class);
				intent.putExtra("orderid", list.get(position).getOrderId() + "");
				context.startActivity(intent);
			}
		});
		/**
		 * 订单详情
		 */
		holder.book_info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, OrderDetailActivity.class);
				intent.putExtra("orderid", list.get(position).getOrderId() + "");
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	class historyCommentHolder {

		ImageView imageview_shop_icon;
		ImageView imageview_commodity_icon;
		TextView textview_shop_name;
		TextView textview_vocher_name;
		TextView textview_commodity_name;
		TextView textview_commodity_detail;
		TextView textview_commodity_price;
		TextView old_price;
		TextView tv_pay;
		TextView tv_gopay;
		TextView tv_return;
		TextView tv_suried;
		TextView tv_single;
		TextView tv_buify;
		RelativeLayout rl_info;
		RelativeLayout book_info;
		HorizontalListView images_info;
	}
}
