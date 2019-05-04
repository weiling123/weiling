package com.beijing.beixin.ui.classify.newproduct;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.HomeSpecialListAdapter;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.NewProductBean.product;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.base.BaseFragment;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.PullListTask;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase;
import com.lidroid.xutils.http.RequestParams;

public class HotProductFragment extends BaseFragment {

	private String mType;
	private MyPullListTask mMyPullListTask;
	private BitmapUtil mBitmapUtil = new BitmapUtil();
	private String mPageModuleId;

	public static HotProductFragment instance(String pageModuleId) {
		HotProductFragment groupFragment = new HotProductFragment();
		Bundle bundle = new Bundle();
		bundle.putString("pageModuleId", pageModuleId);
		groupFragment.setArguments(bundle);
		return groupFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageModuleId = getArguments().getString("pageModuleId");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.pulllist_fragment, container, false);
		product[] arraList = new product[0];
		View loadingView = rootView.findViewById(R.id.progressBar);
		View loadFaileView = rootView.findViewById(R.id.load_faile);
		mMyPullListTask = new MyPullListTask(rootView);
		rootView.findViewById(R.id.load_faile_button).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mMyPullListTask.startNet();
			}
		});
		mMyPullListTask.setArraList(arraList, R.layout.item_new_book_list, loadingView, loadFaileView);
		return rootView;
	}

	class MyPullListTask extends PullListTask<product> {

		@Override
		public void setArraList(product[] arraList, int id) {
			mArrayList = new ArrayList();
			for (product t : arraList) {
				mArrayList.add(t);
			}
			mMyArrayAdapter = new HomeSpecialListAdapter((BaseActivity) getActivity(), R.layout.item_new_book_list,
					arraList, false);
			mPullList.setAdapter(mMyArrayAdapter);
			mPullList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent = new Intent(getActivity(), BookDetailActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("ProductId", mArrayList.get(position - 1).getProductId() + "");
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
		}

		public MyPullListTask(View view) {
			super(getActivity(), view, PullToRefreshBase.Mode.BOTH, true, false);
		}

		@Override
		public View getPullListView(product t, int position, View convertView, ViewGroup parent) {
			return null;
		}

		public String getPageUrl() {
			return SystemConfig.HOTS_PRODUCTS;
		}

		public RequestParams getRequestParams() {
			RequestParams requestParams = super.getRequestParams();
			requestParams.addBodyParameter("pageModuleId", mPageModuleId);
			requestParams.addBodyParameter("pageSize", "10");
			return requestParams;
		}

		public product[] getList(String response) {
			if (TextUtils.isEmpty(response)) {
				return null;
			}
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(response);
				ArrayList<product> arrayList = (ArrayList<product>) JSON
						.parseArray(jsonObject.getString("page_hots_products").toString(), product.class);
				product[] products = new product[arrayList.size()];
				return arrayList.toArray(products);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		public void onRefresh() {
			product[] arraList = new product[mArrayList.size()];
			((HomeSpecialListAdapter) mMyArrayAdapter).OnRefresh(mArrayList.toArray(arraList));
		}
	}

}