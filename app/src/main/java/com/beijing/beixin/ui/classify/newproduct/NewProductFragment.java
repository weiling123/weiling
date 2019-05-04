package com.beijing.beixin.ui.classify.newproduct;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.beijing.beixin.R;
import com.beijing.beixin.adapter.HomeSpecialListAdapter;
import com.beijing.beixin.pojo.NewProductBean.product;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.ui.base.BaseFragment;
import com.beijing.beixin.ui.classify.NewProductActivity;

public class NewProductFragment extends BaseFragment {

	private product[] mProduct;
	private NewProductActivity newProductActivity = null;
	private int index;
	private List<product> list = null;
	private HomeSpecialListAdapter myArrayAdapter = null;

	public static NewProductFragment instance(int index) {
		NewProductFragment groupFragment = new NewProductFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("index", index);
		groupFragment.setArguments(bundle);
		return groupFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		newProductActivity = (NewProductActivity) getActivity();
		index = getArguments().getInt("index");
		list = newProductActivity.getProduct(index);
		if (list != null && list.size() > 0) {
			mProduct = new product[list.size()];
			mProduct = list.toArray(mProduct);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_group_new_book, container, false);
		ListView listView = (ListView) rootView.findViewById(R.id.listview);
		View showNull = rootView.findViewById(R.id.tv_main_no_data2);
		if (mProduct == null || mProduct.length == 0) {
			showNull.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
		} else {
			myArrayAdapter = new HomeSpecialListAdapter((BaseActivity) getActivity(), R.layout.item_new_book_list,
					mProduct, true);
			listView.setAdapter(myArrayAdapter);
			showNull.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
			listView.setOnItemClickListener(myArrayAdapter);
		}
		return rootView;
	}

}
