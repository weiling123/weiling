package com.beijing.beixin.adapter;

import android.view.View;

/**
 * listview点击事件接口
 * 
 * @author ouyanghao
 *
 */
public interface ListItemClickHelp {
	void onItemClick(View item, View widget, int position, int which);
}
