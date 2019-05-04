package com.beijing.beixin.adapter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.SaveSendWayBean;
import com.beijing.beixin.pojo.SendWayBean;
import com.beijing.beixin.pojo.ShoppingCartBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.utils.HorizontalListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class SendWayAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	Activity context;
	private List<SaveSendWayBean> list;
	/**
	 * 配送规则id
	 */
	private int deliveryRuleId = 0;
	/**
	 * 配送方式
	 */
	public String sendWay = "";
	/**
	 * 配送方式
	 */
	private String payway = "";
	// private int sendWayPosition = 0;

	public SendWayAdapter(Activity context) {
		this.mInflater = LayoutInflater.from(context);
		this.context = context;
	}

	public void setData(List<SaveSendWayBean> list, String payway) {
		this.list = list;
		this.payway = payway;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		historyCommentHolder myHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_send_way, null);
			myHolder = new historyCommentHolder();
			myHolder.textview_shop_name = (TextView) convertView.findViewById(R.id.textview_shop_name);
			myHolder.HorizontalListView = (HorizontalListView) convertView.findViewById(R.id.layout_send_way);
			myHolder.gridView_send_way = (GridView) convertView.findViewById(R.id.gridView_send_way);
			convertView.setTag(myHolder);
		} else {
			myHolder = (historyCommentHolder) convertView.getTag();
		}
		myHolder.textview_shop_name.setText(list.get(position).getShopName());
		CommonAdapter<String> adapterImage = new CommonAdapter<String>(context, list.get(position).getProImageList(),
				R.layout.item_orders) {

			@Override
			public void convert(ViewHolder helper, String item) {
				BitmapUtils util = new BitmapUtils(context);
				util.display(helper.getView(R.id.images), item);
			}
		};
		myHolder.HorizontalListView.setAdapter(adapterImage);
		CommonAdapter<SendWayBean> adapter = new CommonAdapter<SendWayBean>(context,
				list.get(position).getSendWayList(), R.layout.item_send_way_gridview) {

			@Override
			public void convert(ViewHolder helper, SendWayBean item) {
				helper.setText(R.id.textview_pay_way, item.getDeliveryRuleNm());
			}
		};
		myHolder.gridView_send_way.setAdapter(adapter);
		myHolder.gridView_send_way.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int positionItem, long id) {
				for (int i = 0; i < list.get(position).getSendWayList().size(); i++) {
					View v = parent.getChildAt(i);
					TextView textview_pay_way = (TextView) v.findViewById(R.id.textview_pay_way);
					if (i == positionItem) {
						deliveryRuleId = list.get(position).getSendWayList().get(i).getDeliveryRule()
								.getDeliveryRuleId();
						// Toast.makeText(context,
						// "deliveryRuleId="+deliveryRuleId,
						// Toast.LENGTH_SHORT).show();
						if ("".equals(sendWay)) {
							sendWay = list.get(position).getSendWayList().get(i).getDeliveryRuleNm();
						} else if (!sendWay.contains(list.get(position).getSendWayList().get(i).getDeliveryRuleNm())) {
							sendWay = sendWay + "+" + list.get(position).getSendWayList().get(i).getDeliveryRuleNm();
						}
						textview_pay_way.setBackgroundResource(R.drawable.white_rounded_shape);
						textview_pay_way.setTextColor(context.getResources().getColor(R.color.common_red_bg));
						saveSendWay(list.get(position).getOrgId());
					} else {
						textview_pay_way.setBackgroundResource(R.drawable.image_rounded_shape);
						textview_pay_way.setTextColor(context.getResources().getColor(R.color.common_tv_black));
					}
				}
			}
		});
		return convertView;
	}

	class historyCommentHolder {

		TextView textview_shop_name;
		HorizontalListView HorizontalListView;
		GridView gridView_send_way;
	}

	/**
	 * 配送方式选择
	 */
	public void saveSendWay(Integer orgId) {
		BaseTask baseTask = new BaseTask(context);
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", "normal");
		params.addBodyParameter("orgId", orgId + "");
		if (deliveryRuleId == 0) {
			Toast.makeText(context, "您还没有选择配送方式哦!", Toast.LENGTH_SHORT).show();
			return;
		}
		params.addBodyParameter("deliveryRuleId", deliveryRuleId + "");
		baseTask.askCookieRequest(SystemConfig.SAVE_SEND_WAY, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("submitOrder", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result.toString());
					ShoppingCartBean bean = JSON.parseObject(obj.get("result").toString(), ShoppingCartBean.class);
					if (bean != null && bean.getShoppingCartVos() != null
							&& bean.getShoppingCartVos().get(0).getItems() != null) {
						Toast.makeText(context, "配送方式选择成功", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(context, "配送方式选择失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
