package com.beijing.beixin.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.drm.DrmStore.RightsStatus;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.beijing.beixin.R;
import com.beijing.beixin.pojo.ClassifyRightBean;
import com.beijing.beixin.pojo.ClassifyRightBean.AppCateGoryVo;
import com.beijing.beixin.ui.StartActivity;
import com.beijing.beixin.ui.classify.ClassifyProductActivity;

public class ClassifyRightListViewAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	Activity context;
	private List<ClassifyRightBean> list;

	public ClassifyRightListViewAdapter(Activity context) {
		this.mInflater = LayoutInflater.from(context);
		this.context = context;
	}

	public void setData(List<ClassifyRightBean> list) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		historyCommentHolder myHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_classify_right, null);
			myHolder = new historyCommentHolder();
			myHolder.textview_classify_listview = (TextView) convertView.findViewById(R.id.textview_classify_listview);
			myHolder.gridview_classify_listview = (GridView) convertView.findViewById(R.id.gridview_classify_listview);
			convertView.setTag(myHolder);
		} else {
			myHolder = (historyCommentHolder) convertView.getTag();
		}
		myHolder.textview_classify_listview.setText(list.get(position).getSubCateStr());
		AppCateGoryVo vo = new AppCateGoryVo();
		vo.setCategoryNm("");
		if (list.get(position).getSubCategory().size() % 3 == 2) {
			list.get(position).getSubCategory().add(vo);
		} else if (list.get(position).getSubCategory().size() % 3 == 1) {
			list.get(position).getSubCategory().add(vo);
			list.get(position).getSubCategory().add(vo);
		}
		CommonAdapter<ClassifyRightBean.AppCateGoryVo> adapter = new CommonAdapter<ClassifyRightBean.AppCateGoryVo>(
				context, list.get(position).getSubCategory(), R.layout.item_classify_right_textview) {

			@Override
			public void convert(ViewHolder helper, final AppCateGoryVo item) {
				helper.setText(R.id.textview_item_classify_right, item.getCategoryNm());
				helper.getConvertView().setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(context, ClassifyProductActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("type", "type");
						bundle.putString("categoryId", item.getSysObjectId() + "");
						intent.putExtras(bundle);
						context.startActivity(intent);
					}
				});
			}
		};
		myHolder.gridview_classify_listview.setAdapter(adapter);
		return convertView;
	}

	class historyCommentHolder {

		TextView textview_classify_listview;
		GridView gridview_classify_listview;
	}
}
