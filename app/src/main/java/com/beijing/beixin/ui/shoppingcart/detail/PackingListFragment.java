package com.beijing.beixin.ui.shoppingcart.detail;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.BookMoreDetailBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseFragment;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class PackingListFragment extends BaseFragment {
	private BookDetailActivity mBookDetailActivity;

	private MyArrayAdapter mMyArrayAdapter;

	private ListView mListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBookDetailActivity = (BookDetailActivity) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.package_list_detail, container, false);
		mListView = (ListView) root.findViewById(R.id.listview);
		mMyArrayAdapter = new MyArrayAdapter(getActivity(), R.layout.item_book_package_list);
		mListView.setAdapter(mMyArrayAdapter);
		if (mBookDetailActivity.mDetailMorePage.size() == 0) {
			sendhttp();
		}
		return root;
	}

	public boolean isListViewReachTopEdge() {
		boolean result = false;
		if (mListView.getFirstVisiblePosition() == 0) {
			final View topChildView = mListView.getChildAt(0);
			result = topChildView.getTop() == 0;
		}
		return result;
	}

	public void sendhttp() {
		showDialog("正在请求数据，请稍后...");
		BaseTask task = new BaseTask(getActivity());
		RequestParams params = new RequestParams();
		BookDetailActivity activity = (BookDetailActivity) getActivity();
		params.addBodyParameter("productId", activity.mProductId);
		params.addBodyParameter("type", "1");// 出版信息
		task.askCookieRequest(SystemConfig.GET_PRODUCT_DETAIL, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("bookdetailMore", arg0.result.toString());
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(arg0.result.toString());
					mBookDetailActivity.mDetailMorePage = JSON.parseArray(jsonObject.getString("result"),
							BookMoreDetailBean.class);
					mMyArrayAdapter.notifyDataSetChanged();
					dismissDialog();
				} catch (JSONException e) {
					dismissDialog();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
			}
		});
	}

	class MyArrayAdapter extends ArrayAdapter<BookMoreDetailBean> {

		public MyArrayAdapter(Context context, int resource) {
			super(context, resource);
		}

		@Override
		public int getCount() {
			if (mBookDetailActivity.mDetailMorePage == null) {
				return 0;
			}
			return mBookDetailActivity.mDetailMorePage.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null || convertView.getTag() == null) {
				viewHolder = new ViewHolder();
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_book_package_list, null);
				viewHolder.mTitle = (TextView) convertView.findViewById(R.id.title);
				viewHolder.mContent = (TextView) convertView.findViewById(R.id.content);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.mTitle.setText(Html.fromHtml(mBookDetailActivity.mDetailMorePage.get(position).getName()));
			viewHolder.mContent
					.setText(Html.fromHtml(mBookDetailActivity.mDetailMorePage.get(position).getValueString()));
			return convertView;
		}
	}

	static class ViewHolder {

		private TextView mTitle;
		private TextView mContent;
	}
}
