package com.beijing.beixin.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.ShoppingcartEditAdapter;
import com.beijing.beixin.adapter.ShoppingcartFirstAdapter;
import com.beijing.beixin.adapter.ViewHolder;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.AppProductBaseVo;
import com.beijing.beixin.pojo.DataShop;
import com.beijing.beixin.pojo.ShoppingCartBean;
import com.beijing.beixin.pojo.ShoppingCartShopBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.tasks.InitShopcart;
import com.beijing.beixin.tasks.ShopCartRequestCallBack;
import com.beijing.beixin.ui.MainActivity;
import com.beijing.beixin.ui.base.BaseFragment;
import com.beijing.beixin.ui.myself.login.LoginActivity;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.ui.shoppingcart.ShopcartActivity;
import com.beijing.beixin.ui.shoppingcart.SubmitOrderActivity;
import com.beijing.beixin.utils.CommonAlertDialog;
import com.beijing.beixin.utils.DensityUtil;
import com.beijing.beixin.utils.IgnitedDiagnosticsUtils;
import com.beijing.beixin.utils.MyGridView;
import com.beijing.beixin.utils.NetWorkUtils;
import com.beijing.beixin.utils.ToastUtil;
import com.beijing.beixin.utils.UpdateHelper;
import com.beijing.beixin.utils.Utils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 购物车
 * 
 * @author ouyanghao
 * 
 */
@SuppressWarnings("unused")
public class ShoppingCartFragment extends BaseFragment implements OnClickListener {

	/** 获取application */
	MyApplication mobileApplication = null;
	/**
	 * 购物车列表数据
	 */
	public ShoppingCartBean listshoppingcart = new ShoppingCartBean();
	public ShoppingCartBean editshoppingcart = new ShoppingCartBean();
	public ListView listview_shoppingcart;
	public ImageView imageview_item_check;
	// public ImageView imageview_nodata;
	public CheckBox checkbox_selectall;
	public CheckBox checkbox_selectall_edit;
	/**
	 * 去结算
	 */
	public TextView button_to_pay;
	/**
	 * 合计
	 */
	public TextView textview_allprice;
	public ShoppingcartFirstAdapter adapter;
	public ShoppingcartEditAdapter editAdapter;
	public LinearLayout layout_topay;
	public LinearLayout layout_delete;
	public LinearLayout layout_nodata;
	private ScrollView scrollview_cart;
	public TextView navigationRightBtn;
	// 标志位，标志已经初始化完成。
	public boolean isPrepared;
	public int cartPosition;
	public Context mContext;
	private MyGridView gridview_recommend;
	private List<AppProductBaseVo> listRecommend;
	public View view_top;

	public ShoppingCartFragment() {
	}

	public ShoppingCartFragment(Context context) {
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.shoppingcart_fragment, container, false);
		mobileApplication = (MyApplication) getActivity().getApplication();
		isPrepared = true;
		init(v);
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		findRecommend();
		if (isPrepared == true && cartPosition == 3) {
			initData();
		}
	}

	public void setPoistion(int position) {
		cartPosition = position;
	}

	public void setContent(Context context) {
		mContext = context;
	}

	private void init(View v) {
		scrollview_cart = (ScrollView) v.findViewById(R.id.scrollview_cart);
		initView(v);
		initListView(v);
		initLinearLayout(v);
	}

	private void initLinearLayout(View v) {
		layout_topay = (LinearLayout) v.findViewById(R.id.layout_topay);
		layout_delete = (LinearLayout) v.findViewById(R.id.layout_delete);
		layout_nodata = (LinearLayout) v.findViewById(R.id.layout_nodata);
	}

	private void initView(View v) {
		view_top = (View) v.findViewById(R.id.view_top);
		TextView navigation_title = (TextView) v.findViewById(R.id.navigation_title);
		navigation_title.setText("购物车");
		navigationRightBtn = (TextView) v.findViewById(R.id.navigationRightBtn);
		if (getActivity() instanceof MainActivity) {
			v.findViewById(R.id.navigationLeftImageBtn).setVisibility(View.INVISIBLE);
		} else {
			ImageView imageView = (ImageView) v.findViewById(R.id.navigationLeftImageBtn);
			imageView.setImageResource(R.drawable.title_bar_back);
			imageView.setVisibility(View.VISIBLE);
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					getActivity().onBackPressed();
				}
			});
		}
		// ImageView navigationLeftImageBtn = (ImageView)
		// v.findViewById(R.id.navigationLeftImageBtn);
		ImageView navigationrightImageBtn = (ImageView) v.findViewById(R.id.navigationRightImageBtn);
		navigationrightImageBtn.setImageResource(R.drawable.comdemore);
		navigationrightImageBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showwindow(arg0);
			}
		});
		// navigationLeftImageBtn.setVisibility(View.GONE);
		navigationRightBtn.setText("编辑");
		navigationRightBtn.setVisibility(View.VISIBLE);
		gridview_recommend = (MyGridView) v.findViewById(R.id.gridview_recommend);
		navigationRightBtn.setOnClickListener(this);
		checkbox_selectall = (CheckBox) v.findViewById(R.id.checkbox_selectall);
		checkbox_selectall.setOnClickListener(this);
		TextView textview_selectall = (TextView) v.findViewById(R.id.textview_selectall);
		checkbox_selectall_edit = (CheckBox) v.findViewById(R.id.checkbox_selectall_edit);
		checkbox_selectall_edit.setOnClickListener(this);
		TextView textview_selectall_edit = (TextView) v.findViewById(R.id.textview_selectall_edit);
		TextView textview_delete = (TextView) v.findViewById(R.id.textview_delete);
		textview_delete.setOnClickListener(this);
		TextView goto_buy = (TextView) v.findViewById(R.id.goto_buy);
		goto_buy.setOnClickListener(this);
		TextView textview_collection = (TextView) v.findViewById(R.id.textview_collection);
		textview_collection.setOnClickListener(this);
		button_to_pay = (TextView) v.findViewById(R.id.button_to_pay);
		textview_allprice = (TextView) v.findViewById(R.id.textview_allprice);
		button_to_pay.setOnClickListener(this);
		gridview_recommend.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), BookDetailActivity.class);
				intent.putExtra("ProductId", listRecommend.get(position).getProductId() + "");
				getActivity().startActivity(intent);
			}
		});
	}

	private void initListView(View v) {
		listview_shoppingcart = (ListView) v.findViewById(R.id.listview_shoppingcart);
		adapter = new ShoppingcartFirstAdapter(this);
		adapter.setData(new ShoppingCartBean());
		listview_shoppingcart.setAdapter(adapter);
	}

	public void initGridView() {
		layout_nodata.setFocusable(true);
		layout_nodata.setFocusableInTouchMode(true);
		layout_nodata.requestFocus();
		if (listRecommend != null && listRecommend.size() != 0) {
			CommonAdapter<AppProductBaseVo> adapter = new CommonAdapter<AppProductBaseVo>(mContext, listRecommend,
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

	private void switchImageview(CheckBox checkbox) {
		if (checkbox.isChecked()) {
			allselect("true");
		} else {
			allselect("false");
		}
	}

	private void switchImageviewEdit(CheckBox checkbox) {
		if (checkbox.isChecked()) {
			editAdapter.setData(editshoppingcart);
			editAdapter.setUnDelete();
			editAdapter.notifyDataSetChanged();
		} else {
			editAdapter.setData(editshoppingcart);
			editAdapter.setDelete();
			editAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.goto_buy:
			if (getActivity() instanceof ShopcartActivity) {
				startActivity(MainActivity.class);
				return;
			}
			setCurrent(0);
			break;
		case R.id.checkbox_selectall:
			switchImageview(checkbox_selectall);
			break;
		case R.id.checkbox_selectall_edit:
			switchImageviewEdit(checkbox_selectall_edit);
			break;
		case R.id.button_to_pay:
			if (!"去结算(0)".equals(button_to_pay.getText().toString())) {
				if (MyApplication.mapp.getCookieStore() == null) {
					Intent intent = new Intent(getActivity(), LoginActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(getActivity(), SubmitOrderActivity.class);
					startActivity(intent);
				}
			} else {
				showToast("您还没有选择任何商品哦！");
			}
			break;
		case R.id.navigationRightBtn:
			if (layout_topay.getVisibility() == View.VISIBLE) {
				checkbox_selectall_edit.setChecked(false);
				navigationRightBtn.setText("完成");
				layout_delete.setVisibility(View.VISIBLE);
				layout_topay.setVisibility(View.GONE);
				editAdapter = new ShoppingcartEditAdapter(this);
				editAdapter.setData(editshoppingcart);
				editAdapter.setDelete();
				listview_shoppingcart.setAdapter(editAdapter);
			} else {
				navigationRightBtn.setText("编辑");
				layout_delete.setVisibility(View.GONE);
				layout_topay.setVisibility(View.VISIBLE);
				adapter.setData(listshoppingcart);
				listview_shoppingcart.setAdapter(adapter);
			}
			break;
		case R.id.textview_delete:
			ArrayList<DataShop> aList = new ArrayList<DataShop>();
			final String keys = getItemKeys(aList);
			if ("".equals(keys)) {
				showToast("您还没有选择任何商品哦！");
				return;
			}
			// final CommonAlertDialog dialog=new
			// CommonAlertDialog(getActivity());
			// dialog.showYesOrNoDialog("是否删除？", new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// ArrayList<DataShop> aList = new ArrayList<DataShop>();
			// DataShop[] dataShop = new DataShop[aList.size()];
			// dataShop = aList.toArray(dataShop);
			// batchDeletePro(keys, dataShop);
			// dialog.dismiss();
			//
			// }
			// }, new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// dialog.dismiss();
			// }
			// });

			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("提示");
			builder.setMessage("是否删除？");
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					ArrayList<DataShop> aList = new ArrayList<DataShop>();
					DataShop[] dataShop = new DataShop[aList.size()];
					dataShop = aList.toArray(dataShop);
					batchDeletePro(keys, dataShop);
				}
			});
			builder.create().show();
			break;
		case R.id.textview_collection:
			collectionProducts(getProductIds());
			break;
		default:
			break;
		}
	}

	/**
	 * 获取购物车列表
	 */
	public void initData() {
		if (MyApplication.mapp.getCookieStore() != null) {
			showDialog("正在请求购物车数据，请稍后...");
			BaseTask baseTask = new BaseTask(getActivity());
			RequestParams params = new RequestParams();
			params.addBodyParameter("type", "normal");
			baseTask.askCookieRequest(SystemConfig.CART, params, new ShopCartRequestCallBack(this, "请求购物车数据失败"));
		} else {
			new InitShopcart(this, MyApplication.ST_NOCHANGE, null, "请求购物车数据失败", "正在请求购物车数据，请稍后...");
		}
	}

	/**
	 * 全选
	 * 
	 * @param select
	 */
	private void allselect(String select) {
		if (MyApplication.mapp.getCookieStore() == null) {
			new InitShopcart(this, "true".equals(select) ? MyApplication.ST_SELECT : MyApplication.ST_NO_SELECT, null,
					"全选失败", "正在全选，请稍后...");
			return;
		}
		showDialog("正在全选，请稍后...");
		BaseTask baseTask = new BaseTask();
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", "normal");
		params.addBodyParameter("isSelected", select);
		baseTask.askCookieRequest(SystemConfig.SELECTALL, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("shoppingcart", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result);
					ShoppingCartBean bean = JSON.parseObject(obj.get("result").toString(), ShoppingCartBean.class);
					ShoppingCartBean bean2 = JSON.parseObject(obj.get("result").toString(), ShoppingCartBean.class);
					// 给页面填充数据
					if (bean != null) {
						listshoppingcart = bean;
						editshoppingcart = bean2;
						button_to_pay.setText("去结算(" + Utils.isInteger(bean.getSelectCartNum()) + ")");
						textview_allprice.setText("¥" + Utils.parseDecimalDouble2(bean.getAllOrderTotalAmount()));
						if (bean.getIsSelectAll() == true) {
							checkbox_selectall.setChecked(true);
						} else {
							checkbox_selectall.setChecked(false);
						}
						adapter.setData(bean);
						adapter.notifyDataSetChanged();
						dismissDialog();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				ToastUtil.show(getActivity(), "全选");
				dismissDialog();
			}
		});
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			if (isPrepared == true) {
				initData();
				findRecommend();
				navigationRightBtn.setText("编辑");
				layout_delete.setVisibility(View.GONE);
				layout_topay.setVisibility(View.VISIBLE);
				adapter.setData(listshoppingcart);
				listview_shoppingcart.setAdapter(adapter);
			}
		} else {
			// 相当于Fragment的onPause
		}
	}

	private void setCurrent(int index) {
		if (getActivity() instanceof MainActivity) {
			((MainActivity) getActivity()).setCurrent(index);
		}
	}

	public void setCartCount(String carnum) {
		if (getActivity() instanceof MainActivity) {
			((MainActivity) getActivity()).setCartCount(carnum, true);
		}
	}

	private void setVorG() {
		if (getActivity() instanceof MainActivity) {
			((MainActivity) getActivity()).setVorG();
		}
	}

	public void cleartShopCart() {
		if (editAdapter != null) {
			editAdapter.clearDate();
		}
		if (adapter != null) {
			adapter.clearDate();
		}
		if (button_to_pay != null) {
			button_to_pay.setText("去结算(0)");
		}
		if (textview_allprice != null) {
			textview_allprice.setText("¥0.00");
		}
		setVorG();
		if (checkbox_selectall_edit != null) {
			checkbox_selectall_edit.setChecked(false);
		}
		hideView();
	}

	/**
	 * 购物车没有商品时隐藏一些控件
	 */
	public void hideView() {
		navigationRightBtn.setVisibility(View.GONE);
		layout_nodata.setVisibility(View.VISIBLE);
		layout_topay.setVisibility(View.GONE);
		layout_delete.setVisibility(View.GONE);
		ScrollView.LayoutParams lp = new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,
				ScrollView.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 0, 0);
		scrollview_cart.setLayoutParams(lp);
	}

	/**
	 * 购物车有商品时显示一些控件
	 */
	public void showView() {
		int marginBottom = DensityUtil.dp2px(getActivity(), 60);
		navigationRightBtn.setVisibility(View.VISIBLE);
		layout_nodata.setVisibility(View.GONE);
		layout_topay.setVisibility(View.VISIBLE);
		ScrollView.LayoutParams lp = new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,
				ScrollView.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 0, marginBottom);
		scrollview_cart.setLayoutParams(lp);
	}

	/**
	 * 删除购物车商品
	 */
	private void batchDeletePro(String itemKeys, DataShop[] dataShop) {
		if (MyApplication.mapp.getCookieStore() == null) {
			MyApplication.mapp.emptyDataShop();
			cleartShopCart();
			return;
		}
		// showDialog("正在删除商品，请稍等");
		BaseTask baseTask = new BaseTask(getActivity());
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", "normal");
		params.addBodyParameter("handler", "sku");
		params.addBodyParameter("itemKeys", itemKeys);
		baseTask.askCookieRequest(SystemConfig.BATCHDELETE_PRODUCT, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("shoppingcart", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result);
					ShoppingCartBean bean = JSON.parseObject(obj.get("result").toString(), ShoppingCartBean.class);
					ShoppingCartBean bean2 = JSON.parseObject(obj.get("result").toString(), ShoppingCartBean.class);
					listshoppingcart = bean;
					// 给页面填充数据
					if (bean != null) {
						dismissDialog();
						showToast("删除商品成功");
						if (bean.getShoppingCartVos().size() == 0) {
							setCartCount("0");
							hideView();
							// navigationRightBtn.setVisibility(View.VISIBLE);
							editAdapter.setData(bean);
							editAdapter.notifyDataSetChanged();
							return;
						}
						setCartCount(Utils.isInteger(bean.getAllCartNum()) + "");
						listshoppingcart = bean;
						editshoppingcart = bean2;
						button_to_pay.setText("去结算(" + Utils.isInteger(bean.getSelectCartNum()) + ")");
						textview_allprice.setText("¥" + Utils.parseDecimalDouble2(bean.getAllOrderTotalAmount()));
						if (bean.getIsSelectAll() == true) {
							checkbox_selectall.setChecked(true);
						} else {
							checkbox_selectall.setChecked(false);
						}
						// adapter.setData(bean);
						// adapter.notifyDataSetChanged();
						for (int i = 0; i < editshoppingcart.getShoppingCartVos().size(); i++) {
							if (editshoppingcart.getShoppingCartVos().get(i).getIsSelectAll() == true) {
								editshoppingcart.getShoppingCartVos().get(i).setIsSelectAll(false);
							}
							for (int j = 0; j < editshoppingcart.getShoppingCartVos().get(i).getItems().size(); j++) {
								if (editshoppingcart.getShoppingCartVos().get(i).getItems().get(j)
										.getItemSelected() == true) {
									editshoppingcart.getShoppingCartVos().get(i).getItems().get(j)
											.setItemSelected(false);
								}
							}
						}
						editAdapter.setData(editshoppingcart);
						editAdapter.notifyDataSetChanged();
					}
				} catch (JSONException e) {
					dismissDialog();
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				ToastUtil.show(getActivity(), "删除商品失败");
			}
		});
	}

	/**
	 * 批量将商品收藏
	 * 
	 * @param itemKeys
	 */
	private void collectionProducts(String productIds) {
		if (MyApplication.mapp.getCookieStore() == null) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			return;
		}
		BaseTask baseTask = new BaseTask(getActivity());
		RequestParams params = new RequestParams();
		params.addBodyParameter("productIds", productIds);
		baseTask.askCookieRequest(SystemConfig.COLLECTION_PRODUCTS, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("shoppingcart", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result);
					if (obj.getBoolean("success")) {
						showToast("批量收藏成功");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				ToastUtil.show(getActivity(), "批量收藏失败");
			}
		});
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
				Log.e("shoppingcart", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result);
					listRecommend = JSON.parseArray(obj.getString("recommendProducts").toString(),
							AppProductBaseVo.class);
					initGridView();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// ToastUtil.show(getActivity(), "为你推荐加载失败");
				Log.e("为你推荐", "为你推荐加载失败");
			}
		});
	}

	private String getItemKeys(ArrayList<DataShop> aList) {
		String itemKeys = "";
		for (int i = 0; i < editAdapter.listDelete.size(); i++) {
			ShoppingCartShopBean shopBean = editAdapter.listDelete.get(i);
			for (int j = 0; j < shopBean.getItems().size(); j++) {
				if (true == shopBean.getItems().get(j).getItemSelected()) {
					itemKeys = itemKeys + shopBean.getItems().get(j).getItemKey() + ":" + shopBean.getOrgId() + ",";
					DataShop dataShop = new DataShop(shopBean.getShopInfId() + "",
							shopBean.getItems().get(j).getSkuId() + "");
					aList.add(dataShop);
				}
			}
		}
		if (itemKeys.length() > 0) {
			String a = itemKeys.substring(0, itemKeys.length() - 1);
			return a;
		}
		return "";
	}

	private String getProductIds() {
		String productIds = "";
		for (int i = 0; i < editAdapter.listDelete.size(); i++) {
			ShoppingCartShopBean shopBean = editAdapter.listDelete.get(i);
			for (int j = 0; j < shopBean.getItems().size(); j++) {
				if (true == shopBean.getItems().get(j).getItemSelected()) {
					productIds = productIds + shopBean.getItems().get(j).getProductId() + ",";
				}
			}
		}
		if (productIds.length() > 0) {
			String a = productIds.substring(0, productIds.length() - 1);
			return a;
		}
		return "";
	}

	public void update(ShoppingCartBean bean) throws CloneNotSupportedException {
		listshoppingcart = bean;
		editshoppingcart = (ShoppingCartBean) bean.clone();
		button_to_pay.setText("去结算(" + Utils.isInteger(bean.getSelectCartNum()) + ")");
		textview_allprice.setText("¥" + Utils.parseDecimalDouble2(bean.getAllOrderTotalAmount()));
		if (bean.getIsSelectAll() == true) {
			checkbox_selectall.setChecked(true);
		} else {
			checkbox_selectall.setChecked(false);
		}
	}

	public void updateEdit(ShoppingCartBean editStauts) {
		if (editStauts.getIsSelectAll() == true) {
			checkbox_selectall_edit.setChecked(true);
		} else {
			checkbox_selectall_edit.setChecked(false);
		}
	}
}
