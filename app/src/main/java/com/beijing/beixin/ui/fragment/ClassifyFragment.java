package com.beijing.beixin.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.CommonPositionAdapter;
import com.beijing.beixin.adapter.ViewHolder;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.AppProductBaseVo;
import com.beijing.beixin.pojo.ClassifyRightBean;
import com.beijing.beixin.pojo.DataShop;
import com.beijing.beixin.pojo.ClassifyRightBean.AppCateGoryVo;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.MainActivity;
import com.beijing.beixin.ui.base.BaseFragment;
import com.beijing.beixin.ui.classify.ClassifyProductActivity;
import com.beijing.beixin.ui.homepage.SearchActivity;
import com.beijing.beixin.ui.myself.login.LoginActivity;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.utils.CommonAlertDialog;
import com.beijing.beixin.utils.IgnitedDiagnosticsUtils;
import com.beijing.beixin.utils.MyGridView;
import com.beijing.beixin.utils.NetWorkUtils;
import com.beijing.beixin.utils.ToastUtil;
import com.beijing.beixin.utils.zxing.CaptureActivity;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 分类
 * 
 * @author ouyanghao
 * 
 */
public class ClassifyFragment extends BaseFragment implements OnClickListener {

	private ListView listview_classify_left;
	private EditText et_component_search;
	private MyGridView gridview_classify_listview;
	private List<ClassifyRightBean> listBean;
	private CommonPositionAdapter<ClassifyRightBean> mAdapter;
	private int cartPosition = 0;
	private int selectIndex = 0;
	/**
	 * 二维码扫描的按钮
	 */
	private ImageView imageview_scancode;
	private ImageView iv_loginorexit;
	private List<AppProductBaseVo> listRecommend;
	private GridView gridview_recommend;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.classify_fragment, container, false);
		init(v);
		setListener();
		return v;
	}

	public void setPoistion(int position) {
		cartPosition = position;
	}

	private void init(View v) {
		gridview_recommend = (GridView) v.findViewById(R.id.gridview_recommend);
		imageview_scancode = (ImageView) v.findViewById(R.id.imageview_scancode);
		iv_loginorexit = (ImageView) v.findViewById(R.id.iv_loginorexit);
		gridview_classify_listview = (MyGridView) v.findViewById(R.id.gridview_classify_listview);
		et_component_search = (EditText) v.findViewById(R.id.et_component_search);
		et_component_search.setOnClickListener(this);
		listview_classify_left = (ListView) v.findViewById(R.id.listview_classify_left);
	}

	private void setListener() {
		gridview_recommend.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), BookDetailActivity.class);
				intent.putExtra("ProductId", listRecommend.get(position).getProductId() + "");
				getActivity().startActivity(intent);
			}
		});
		iv_loginorexit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (MyApplication.mapp.getCookieStore() != null) {
					final CommonAlertDialog dialog = new CommonAlertDialog(getActivity());
					dialog.showYesOrNoDialog("是否退出登录？", new OnClickListener() {

						@Override
						public void onClick(View v) {
							loginout();
							dialog.dismiss();

						}
					}, new OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});

				} else {
					startActivity(LoginActivity.class);
				}
			}
		});
		imageview_scancode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 二维码扫描
				startActivity(new Intent(getActivity(), CaptureActivity.class));
			}
		});
		listview_classify_left.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
				// 选中时改变颜色
				selectIndex = position;
				mAdapter.notifyDataSetChanged();
				showDialog("正在请求数据...");
				findRecommend();
				List<AppCateGoryVo> list = listBean.get(position).getSubCategory();
				AppCateGoryVo vo = new AppCateGoryVo();
				if (list.size() % 3 == 2) {
					list.add(vo);
				} else if (list.size() % 3 == 1) {
					list.add(vo);
					list.add(vo);
				}
				CommonAdapter<ClassifyRightBean.AppCateGoryVo> adapter = new CommonAdapter<ClassifyRightBean.AppCateGoryVo>(
						getActivity(), list, R.layout.item_classify_right_textview) {

					@Override
					public void convert(ViewHolder helper, final AppCateGoryVo item) {
						helper.setText(R.id.textview_item_classify_right, item.getCategoryNm());
						helper.getConvertView().setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								Intent intent = new Intent(getActivity(), ClassifyProductActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("type", "type");
								bundle.putString("categoryId", item.getSysObjectId() + "");
								bundle.putString("categoryIds", item.getCategoryId() + "");
								intent.putExtras(bundle);
								getActivity().startActivity(intent);
							}
						});
					}
				};
				gridview_classify_listview.setAdapter(adapter);
			}
		});
	}

	public void initGridView() {
		if (listRecommend != null && listRecommend.size() != 0) {
			CommonAdapter<AppProductBaseVo> adapter = new CommonAdapter<AppProductBaseVo>(getActivity(), listRecommend,
					R.layout.item_recommend_gridview) {

				@Override
				public void convert(ViewHolder helper, AppProductBaseVo item) {
					helper.setText(R.id.textview_recommend1, item.getProductNm());
					helper.displayImage(R.id.imageview_recommend1, item.getImage());
				}
			};
			gridview_recommend.setAdapter(adapter);
		}
	}

	/**
	 * 为你推荐
	 * 
	 * @param productIds
	 */
	private void findRecommend() {
		IgnitedDiagnosticsUtils util = new IgnitedDiagnosticsUtils();
		@SuppressWarnings("static-access")
		String version = util.getApplicationVersionString(getActivity());
		BaseTask task = new BaseTask(getActivity());
		RequestParams params = new RequestParams();
		params.addBodyParameter("networktype", NetWorkUtils.getCurrentNetType(getActivity()));
		params.addBodyParameter("platformDeviceTypeCode", "aos-hand");
		params.addBodyParameter("appversion", version.replace("v", ""));
		params.addBodyParameter("limit", "20");
		task.askCookieRequest(SystemConfig.FIND_RECOMMEND, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("recommend", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result);
					listRecommend = JSON.parseArray(obj.getString("recommendProducts").toString(),
							AppProductBaseVo.class);
					initGridView();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				dismissDialog();
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				// ToastUtil.show(getActivity(), "为你推荐加载失败");
				Log.e("为你推荐", "为你推荐加载失败");
			}
		});
	}

	/**
	 * 退出登录
	 */
	public void loginout() {
		BaseTask baseTask = new BaseTask(getActivity());
		baseTask.askCookieRequest(SystemConfig.LOGOUT, null, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("退出成功", arg0.result.toString());
				try {
					org.json.JSONObject jsonObject = new org.json.JSONObject(arg0.result);
					if (jsonObject.getBoolean("success")) {
						MyApplication.mapp.clear();
						showToast("退出成功");
						LoginOrRegister();
						((MainActivity) getActivity()).setCurrent(4);
						((MainActivity) getActivity()).setCartCount("0", false);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("退出成功异常", arg0.toString());
				showToast("退出失败");
			}
		});
	}

	public void LoginOrRegister() {
		if (MyApplication.mapp.getCookieStore() != null) {
			iv_loginorexit.setImageResource(R.drawable.home_exit_black);
		} else {
			iv_loginorexit.setImageResource(R.drawable.home_login_black);
		}
	}

	/**
	 * 获取商品分类
	 */
	private void sendhttp() {
		showDialog("正在请求分类数据，请稍后...");
		BaseTask task = new BaseTask(getActivity());
		task.askNormalRequest(SystemConfig.PRODUCT_TYPE, null, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				listBean = new ArrayList<ClassifyRightBean>();
				JSONObject object;
				try {
					object = new JSONObject(arg0.result);
					JSONArray array = object.getJSONArray("result");
					listBean = JSON.parseArray(array.toString(), ClassifyRightBean.class);
					InitLeftListView(listBean);
					AppCateGoryVo vo = new AppCateGoryVo();
					List<AppCateGoryVo> list = listBean.get(0).getSubCategory();
					if (list.size() % 3 == 2) {
						list.add(vo);
					} else if (list.size() % 3 == 1) {
						list.add(vo);
						list.add(vo);
					}
					CommonAdapter<ClassifyRightBean.AppCateGoryVo> adapter = new CommonAdapter<ClassifyRightBean.AppCateGoryVo>(
							getActivity(), list, R.layout.item_classify_right_textview) {

						@Override
						public void convert(ViewHolder helper, final AppCateGoryVo item) {
							helper.setText(R.id.textview_item_classify_right, item.getCategoryNm());
							helper.getConvertView().setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									Intent intent = new Intent(getActivity(), ClassifyProductActivity.class);
									Bundle bundle = new Bundle();
									bundle.putString("type", "type");
									bundle.putString("categoryId", item.getSysObjectId() + "");
									bundle.putString("categoryIds", item.getCategoryId() + "");
									intent.putExtras(bundle);
									getActivity().startActivity(intent);
								}
							});
						}
					};
					gridview_classify_listview.setAdapter(adapter);
					findRecommend();
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("分类异常", arg0.toString());
				dismissDialog();
			}
		});
	}

	/**
	 * 左边分类适配器
	 * 
	 * @param mlList
	 */
	private void InitLeftListView(List<ClassifyRightBean> mlList) {
		listview_classify_left.setAdapter(mAdapter = new CommonPositionAdapter<ClassifyRightBean>(getActivity(), mlList,
				R.layout.item_classify_left) {

			@Override
			public void convert(final ViewHolder helper, final ClassifyRightBean item, int position) {
				helper.setText(R.id.tv_classify_left_name, item.getCategoryNm());
				if (position == selectIndex) {
					((TextView) (helper.getView(R.id.tv_classify_left_name)))
							.setTextColor(getResources().getColor(R.color.white));
					((TextView) (helper.getView(R.id.tv_classify_left_name)))
							.setBackgroundResource(R.color.common_dark_red);
				} else {
					((TextView) (helper.getView(R.id.tv_classify_left_name)))
							.setTextColor(getResources().getColor(R.color.common_tv_black));
					((TextView) (helper.getView(R.id.tv_classify_left_name)))
							.setBackgroundResource(R.color.bg_prodetail);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.et_component_search:
			Intent intsearch = new Intent(getActivity(), SearchActivity.class);
			getActivity().startActivity(intsearch);
			break;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		LoginOrRegister();

	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			LoginOrRegister();
			if (listBean == null) {
				sendhttp();
			} else {
				showDialog("正在请求数据...");
				findRecommend();
			}
		} else {
			// 相当于Fragment的onPause
		}
	}
}
