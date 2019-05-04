package com.beijing.beixin.ui.shoppingcart.detail;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.BookMoreDetailBean;
import com.beijing.beixin.pojo.NameAndValue;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseFragment;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.utils.ExpandListView;
import com.beijing.beixin.utils.LogUtil;
import com.beijing.beixin.utils.ScreenUtil;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class BookDetailFragment extends BaseFragment {

	private NameAndValue[] mNameAndValues;
	private MyArrayAdapter mMyArrayAdapter;
	private View mTitleView;
	private WebView mWebView;

	private ListView mListView;

	private BookDetailActivity mBookDetailActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBookDetailActivity = (BookDetailActivity) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_book_detail, container, false);
		mTitleView = root.findViewById(R.id.webViewName);
		mWebView = (WebView) root.findViewById(R.id.webview);
		WebSettings settings = mWebView.getSettings();
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		LogUtil.e("onCreateView", mWebView + " :　" + mTitleView);
		mListView = (ListView) root.findViewById(R.id.listview);
		mMyArrayAdapter = new MyArrayAdapter(getActivity(), R.layout.item_book_detail_list);
		mListView.setAdapter(mMyArrayAdapter);
		initWebView();
		if (mBookDetailActivity.mDetailMore.size() == 0) {
			sendhttp();
		}
		return root;
	}

	public boolean isListViewReachTopEdge() {
		boolean result = false;
		if (mListView.getFirstVisiblePosition() == 0) {
			final View topChildView = mListView.getChildAt(0);
			if (topChildView == null) {
				return true;
			}
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
		params.addBodyParameter("type", "0");// 图文介绍
		task.askCookieRequest(SystemConfig.GET_PRODUCT_DETAIL, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("bookdetailMore", arg0.result.toString());
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(arg0.result.toString());
					List<BookMoreDetailBean> list = JSON.parseArray(jsonObject.getString("result"),
							BookMoreDetailBean.class);
					for (int i = 0; i < list.size(); i++) {
						if (!"".equals(list.get(i).getValueString())) {
							mBookDetailActivity.mDetailMore.add(list.get(i));
						}
					}
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

	private void initWebView() {
		String pluCode = ((BookDetailActivity) getActivity()).mProductDetail.getPluCode();
		String videoUrl = ((BookDetailActivity) getActivity()).mProductDetail.getVideoUrl();
		LogUtil.e("onCreateView", mWebView + "");
		if (TextUtils.isEmpty(pluCode) || TextUtils.isEmpty(videoUrl)) {
			mTitleView.setVisibility(View.GONE);
			mWebView.setVisibility(View.GONE);
			return;
		}
		mTitleView.setVisibility(View.VISIBLE);
		mWebView.setVisibility(View.VISIBLE);
		mWebView.getLayoutParams().height = 220 * ScreenUtil.getScreenWidth(getActivity()) / 400;
		mWebView.loadUrl(videoUrl);
	}

	@Override
	public void onDestroy() {
		if (mWebView != null) {
			mWebView.reload();
			mWebView.destroy();
		}
		getActivity().finish();
		super.onDestroy();
	}

	class MyArrayAdapter extends ArrayAdapter<NameAndValue> {

		public MyArrayAdapter(Context context, int resource) {
			super(context, resource);
		}

		@Override
		public int getCount() {
			if (mBookDetailActivity.mDetailMore == null) {
				return 0;
			}
			return mBookDetailActivity.mDetailMore.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null || convertView.getTag() == null) {
				viewHolder = new ViewHolder();
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_book_detail_list, null);
				viewHolder.mTitle = (TextView) convertView.findViewById(R.id.title);
				viewHolder.mContent = (TextView) convertView.findViewById(R.id.content);
				viewHolder.mWebView = (WebView) convertView.findViewById(R.id.content_webView);
				convertView.setTag(viewHolder);
				viewHolder.mWebView.getSettings().setDefaultTextEncodingName("UTF -8");// 设置默认为utf-8
				viewHolder.mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
				viewHolder.mWebView.getSettings().setLoadWithOverviewMode(true);
				viewHolder.mWebView.getSettings().setUseWideViewPort(true);
				viewHolder.mWebView.getSettings().setBuiltInZoomControls(false);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.mTitle.setText(Html.fromHtml(mBookDetailActivity.mDetailMore.get(position).getName()));
			String content = mBookDetailActivity.mDetailMore.get(position).getValueString();
			boolean isShowWebView = false;
			if (!TextUtils.isEmpty(content)) {
				String contentLow = content.toLowerCase();
				isShowWebView = contentLow.contains("http://") || contentLow.contains("https://");
			}
			showContent(isShowWebView, viewHolder.mContent, viewHolder.mWebView, content);
			return convertView;
		}
	}

	private void showContent(boolean isShowWebView, TextView textView, WebView webView, String content) {
		if (isShowWebView) {
			textView.setVisibility(View.GONE);
			webView.setVisibility(View.VISIBLE);
			webView.loadData(content, "text/html; charset=UTF-8", null);
		} else {
			textView.setVisibility(View.VISIBLE);
			webView.setVisibility(View.GONE);
			textView.setText(Html.fromHtml(content));
		}
	}

	static class ViewHolder {

		private TextView mTitle;
		private TextView mContent;
		WebView mWebView;
	}
}
