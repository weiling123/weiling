package com.beijing.beixin.ui.myself;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.AppTicketVo;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseActivity;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class MyTicketActivity extends BaseActivity implements OnItemClickListener {

	private ListView listView;
	private Context mContext;
	/**
	 * 数据源
	 */
	private ArrayList<AppTicketVo> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_ticket);
		setNavigationTitle("我的票");
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		mContext = this;
		listView = (ListView) findViewById(R.id.listview);
		listView.setOnItemClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getTicketList();
	}

	public void getTicketList() {
		showDialog("正在获取票据...");
		BaseTask baseTask = new BaseTask(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("sysUserId", MyApplication.mapp.getUserInfoBean().getSysUserId() + "");
		baseTask.askCookieRequest(SystemConfig.TICKET_LIST, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					Log.e("我的票", arg0.result);
					JSONObject object = new JSONObject(arg0.result);
					dismissDialog();
					list = (ArrayList<AppTicketVo>) JSON.parseArray(object.getString("result").toString(),
							AppTicketVo.class);
					if (list == null || list.size() == 0) {
						showToast("暂无数据");
						return;
					}
					CommonAdapter<AppTicketVo> adapter = new CommonAdapter<AppTicketVo>(mContext, list,
							R.layout.item_ticket) {

						@Override
						public void convert(com.beijing.beixin.adapter.ViewHolder helper, AppTicketVo item) {
							helper.setText(R.id.name, item.getActivityName());
							// helper.setText(R.id.time, "使用期限至 :
							// "+item.getValidTimeToStrig());
							helper.setText(R.id.time,
									"使用期限 :\n" + item.getValidTimeFromString() + " - " + item.getValidTimeToStrig());
							if (item.getTicketStatus() == 1) {
								helper.setImageBackground(R.id.layout_bg, R.drawable.ticket_get);
							} else if (item.getTicketStatus() == 2) {
								helper.setImageBackground(R.id.layout_bg, R.drawable.ticket_used);
							} else {
								helper.setImageBackground(R.id.layout_bg, R.drawable.ticket_timeout);
							}
						}
					};
					listView.setAdapter(adapter);
				} catch (JSONException e) {
					showToast("票据获取失败");
					e.printStackTrace();
				}
				dismissDialog();
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("我的票", arg0.toString());
				showToast("票据获取失败");
				dismissDialog();
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		startActivity(QrcodeTicketActivity.class, new String[] { "qrcode", "url" },
				new String[] { list.get(position).getTicketCodeNm(), list.get(position).getActivityUrl() });
	}
}
