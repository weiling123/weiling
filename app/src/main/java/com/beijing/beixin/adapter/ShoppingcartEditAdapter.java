package com.beijing.beixin.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.ShoppingCartBean;
import com.beijing.beixin.pojo.ShoppingCartProBean;
import com.beijing.beixin.pojo.ShoppingCartShopBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.tasks.InitShopcart;
import com.beijing.beixin.ui.MainActivity;
import com.beijing.beixin.ui.fragment.ShoppingCartFragment;
import com.beijing.beixin.utils.ExpandListView;
import com.beijing.beixin.utils.MyDialog;
import com.beijing.beixin.utils.ToastUtil;
import com.beijing.beixin.utils.Utils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class ShoppingcartEditAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private EditText dialogtextview_account;
	Activity context;

	private ShoppingCartFragment mShoppingCartFragment;
	/**
	 * 判断是购物车状态还是编辑状态
	 */
	private String flag = "pay";
	public List<ShoppingCartShopBean> listDelete = new ArrayList<ShoppingCartShopBean>();
	private ShoppingCartBean cartBean;
	public String deleteItemKeys;

	public ShoppingcartEditAdapter(ShoppingCartFragment shoppingCartFragment) {
		this.mInflater = LayoutInflater.from(shoppingCartFragment.getActivity());
		this.context = shoppingCartFragment.getActivity();
		mShoppingCartFragment = shoppingCartFragment;
	}

	public void setData(ShoppingCartBean bean) {
		cartBean = bean;
		if (cartBean.getShoppingCartVos() != null) {
			listDelete = cartBean.getShoppingCartVos();
		}
	}

	public void clearDate() {
		if (listDelete != null) {
			listDelete.clear();
			notifyDataSetChanged();
		}
	}

	public void setDelete() {
		for (int i = 0; i < listDelete.size(); i++) {
			listDelete.get(i).setIsSelectAll(false);
			for (int j = 0; j < listDelete.get(i).getItems().size(); j++) {
				listDelete.get(i).getItems().get(j).setItemSelected(false);
			}
		}
	}

	public void setUnDelete() {
		for (int i = 0; i < listDelete.size(); i++) {
			listDelete.get(i).setIsSelectAll(true);
			for (int j = 0; j < listDelete.get(i).getItems().size(); j++) {
				listDelete.get(i).getItems().get(j).setItemSelected(true);
			}
		}
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public int getCount() {
		return listDelete.size();
	}

	@Override
	public Object getItem(int position) {
		return listDelete.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final historyCommentHolder myHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_shoppingcart_list, null);
			myHolder = new historyCommentHolder();
			myHolder.checkbox_item_check = (CheckBox) convertView.findViewById(R.id.checkbox_item_check);
			myHolder.textview_shop_name = (TextView) convertView.findViewById(R.id.textview_shop_name);
			myHolder.textview_vocher_name = (TextView) convertView.findViewById(R.id.textview_vocher_name);
			myHolder.listview_shoppingcart_commodity = (ExpandListView) convertView
					.findViewById(R.id.listview_shoppingcart_commodity);
			convertView.setTag(myHolder);
		} else {
			myHolder = (historyCommentHolder) convertView.getTag();
		}
		myHolder.textview_shop_name.setText(listDelete.get(position).getShopNm());
		if (listDelete.get(position).getIsSelectAll() == true) {
			myHolder.checkbox_item_check.setChecked(true);
		} else {
			myHolder.checkbox_item_check.setChecked(false);
		}
		myHolder.checkbox_item_check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (myHolder.checkbox_item_check.isChecked()) {
					listDelete.get(position).setIsSelectAll(true);
					for (int i = 0; i < listDelete.get(position).getItems().size(); i++) {
						listDelete.get(position).getItems().get(i).setItemSelected(true);
					}
					cartBean.setShoppingCartVos(listDelete);
					ShoppingcartEditAdapter.this.setData(cartBean);
					ShoppingcartEditAdapter.this.notifyDataSetChanged();
					for (int i = 0; i < listDelete.size(); i++) {
						if (listDelete.get(i).getIsSelectAll() == false) {
							cartBean.setIsSelectAll(false);
							break;
						}
						cartBean.setIsSelectAll(true);
					}
					mShoppingCartFragment.updateEdit(cartBean);
				} else {
					listDelete.get(position).setIsSelectAll(false);
					for (int i = 0; i < listDelete.get(position).getItems().size(); i++) {
						listDelete.get(position).getItems().get(i).setItemSelected(false);
					}
					cartBean.setShoppingCartVos(listDelete);
					ShoppingcartEditAdapter.this.setData(cartBean);
					ShoppingcartEditAdapter.this.notifyDataSetChanged();
					for (int i = 0; i < listDelete.size(); i++) {
						if (listDelete.get(i).getIsSelectAll() == false) {
							cartBean.setIsSelectAll(false);
							break;
						}
						cartBean.setIsSelectAll(true);
					}
					mShoppingCartFragment.updateEdit(cartBean);
				}
			}
		});
		CommonPositionAdapter<ShoppingCartProBean> adapterProduct = new CommonPositionAdapter<ShoppingCartProBean>(
				context, listDelete.get(position).getItems(), R.layout.item_shoppingcart_second_list) {

			@Override
			public void convert(final ViewHolder helper, final ShoppingCartProBean item, final int itemPosition) {
				// 商品是否有赠品
				if (item.getPresents() != null && item.getPresents().size() != 0) {
					String present = "";
					for (int i = 0; i < item.getPresents().size(); i++) {
						present = present + "赠品：" + item.getPresents().get(i).getName() + "    x"
								+ item.getPresents().get(i).getQuantity();
					}
					helper.setText(R.id.textview_present_name, present);
				}
				// 商品超过库存
				if (item.getQuantity() > item.getStock()) {
					helper.setText(R.id.textview_stock_tip, "最多只能购买" + item.getStock() + "件");
					helper.setEnable(R.id.textview_plus, false);
				} else {
					helper.setText(R.id.textview_stock_tip, "");
					helper.setEnable(R.id.textview_plus, true);
				}
				// 商品是赠品或者下架的话不能勾选，不能修改数量
				if (item.getIsPresent() || "N".equals(item.getIsOnSale())) {
					helper.setEnable(R.id.checkbox_item_check_layout, false);
					helper.setEnable(R.id.textview_account, false);
					helper.setEnable(R.id.textview_plus, false);
					helper.setEnable(R.id.textview_minus, false);

				} else {
					helper.setEnable(R.id.checkbox_item_check_layout, true);
					helper.setEnable(R.id.textview_account, true);
					helper.setEnable(R.id.textview_plus, true);
					helper.setEnable(R.id.textview_minus, true);
				}
				// 商品是否是赠品
				if (item.getIsPresent()) {
					helper.setVisiblity(R.id.textview_mobile_acount, true);

				} else {
					helper.setVisiblity(R.id.textview_mobile_acount, false);
				}
				// 商品是否失效
				if ("Y".equals(item.getIsOnSale())) {
					helper.setVisiblity(R.id.textview_tag2, false);
				} else {
					helper.setVisiblity(R.id.textview_tag2, true);
				}
				helper.setText(R.id.textview_commodity_name, item.getName());
				helper.setText(R.id.textview_commodity_price,
						"¥" + Utils.parseDecimalDouble2(item.getProductUnitPrice()));
				helper.setText(R.id.textview_account, item.getQuantity() + "");
				helper.setText(R.id.textview_commodity_sum_price,
						"小计:¥" + Utils.parseDecimalDouble2(item.getProductTotalAmount()));
				helper.displayImage(R.id.imageview_commodity_icon, item.getImage());
				if (item.getItemSelected() == true) {
					helper.setChecked(R.id.checkbox_item_check, true);
				} else {
					helper.setChecked(R.id.checkbox_item_check, false);
				}
				helper.getView(R.id.textview_account).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						final MyDialog dialog;
						View v = LayoutInflater.from(context).inflate(R.layout.item_cartshop_edit, null);
						dialog = new MyDialog(context, v, R.style.dialog_base);
						dialog.setContentView(v);
						TextView dialogtextview_minus = (TextView) v.findViewById(R.id.dialogtextview_minus);
						dialogtextview_account = (EditText) v.findViewById(R.id.dialogtextview_account);
						TextView dialogtextview_plus = (TextView) v.findViewById(R.id.dialogtextview_plus);
						TextView dialog_N = (TextView) v.findViewById(R.id.dialog_N);
						TextView dialog_Y = (TextView) v.findViewById(R.id.dialog_Y);
						dialogtextview_account.setText(item.getQuantity() + "");
						dialogtextview_account.setSelection(dialogtextview_account.getText().toString().length());
						// 减少
						dialogtextview_minus.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								if (Integer.parseInt(dialogtextview_account.getText().toString().trim()) > 1) {
									dialogtextview_account.setText(
											(Integer.parseInt(dialogtextview_account.getText().toString().trim()) - 1)
													+ "");
									dialogtextview_account
											.setSelection(dialogtextview_account.getText().toString().length());
								}
							}
						});
						// 添加
						dialogtextview_plus.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								dialogtextview_account.setText(
										(Integer.parseInt(dialogtextview_account.getText().toString().trim()) + 1)
												+ "");
								dialogtextview_account
										.setSelection(dialogtextview_account.getText().toString().length());
							}
						});
						// 取消
						dialog_N.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								dialog.dismiss();
							}
						});
						// 确定
						dialog_Y.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								int quality = Integer.valueOf(dialogtextview_account.getText().toString());
								if (quality > item.getStock()) {
									Toast.makeText(mContext, "商品购买数量不能超过" + item.getStock() + "件", Toast.LENGTH_SHORT)
											.show();
									dialogtextview_account.setText(item.getStock() + "");
									dialogtextview_account
											.setSelection(dialogtextview_account.getText().toString().length());
									return;
								}

								if ("".equals(Utils.checkStr(
										dialogtextview_account.getText().toString().trim().replace("0", "")))) {
									ToastUtil.show(context, "请填写商品数量");
								} else {
									int num = Integer.parseInt(dialogtextview_account.getText().toString().trim());
									String shopInfId = listDelete.get(position).getShopInfId() + "";
									String skuId = item.getSkuId() + "";

									updateQuantity(num, item.getItemKey(), listDelete.get(position).getOrgId(),
											shopInfId, skuId, num, MyApplication.ST_CHANGE_NUM);
									dialog.dismiss();
								}
							}
						});
						dialog.show();
					}
				});
				helper.getView(R.id.textview_plus).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String shopInfId = listDelete.get(position).getShopInfId() + "";
						String skuId = item.getSkuId() + "";
						updateQuantity(item.getQuantity() + 1, item.getItemKey(), listDelete.get(position).getOrgId(),
								shopInfId, skuId, 1, MyApplication.ST_ADD_NUM);
					}
				});
				helper.getView(R.id.textview_minus).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (item.getQuantity() > 1) {
							String shopInfId = listDelete.get(position).getShopInfId() + "";
							String skuId = item.getSkuId() + "";
							updateQuantity(item.getQuantity() - 1, item.getItemKey(),
									listDelete.get(position).getOrgId(), shopInfId, skuId, -1,
									MyApplication.ST_ADD_NUM);
						}
					}
				});
				helper.getView(R.id.checkbox_item_check).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (((CheckBox) helper.getView(R.id.checkbox_item_check)).isChecked()) {
							listDelete.get(position).getItems().get(itemPosition).setItemSelected(true);
							for (int i = 0; i < listDelete.get(position).getItems().size(); i++) {
								if (listDelete.get(position).getItems().get(i).getItemSelected() == false) {
									listDelete.get(position).setIsSelectAll(false);
									break;
								}
								listDelete.get(position).setIsSelectAll(true);
							}
							cartBean.setShoppingCartVos(listDelete);
							ShoppingcartEditAdapter.this.setData(cartBean);
							ShoppingcartEditAdapter.this.notifyDataSetChanged();
							for (int i = 0; i < listDelete.size(); i++) {
								if (listDelete.get(i).getIsSelectAll() == false) {
									cartBean.setIsSelectAll(false);
									break;
								}
								cartBean.setIsSelectAll(true);
							}
							mShoppingCartFragment.updateEdit(cartBean);
						} else {
							listDelete.get(position).getItems().get(itemPosition).setItemSelected(false);
							for (int i = 0; i < listDelete.get(position).getItems().size(); i++) {
								if (listDelete.get(position).getItems().get(i).getItemSelected() == false) {
									listDelete.get(position).setIsSelectAll(false);
									break;
								}
								listDelete.get(position).setIsSelectAll(true);
							}
							cartBean.setShoppingCartVos(listDelete);
							ShoppingcartEditAdapter.this.setData(cartBean);
							ShoppingcartEditAdapter.this.notifyDataSetChanged();
							for (int i = 0; i < listDelete.size(); i++) {
								if (listDelete.get(i).getIsSelectAll() == false) {
									cartBean.setIsSelectAll(false);
									break;
								}
								cartBean.setIsSelectAll(true);
							}
							mShoppingCartFragment.updateEdit(cartBean);
						}
					}
				});
			}
		};
		myHolder.listview_shoppingcart_commodity.setAdapter(adapterProduct);
		return convertView;
	}

	class historyCommentHolder {

		CheckBox checkbox_item_check;
		TextView textview_shop_name;
		TextView textview_vocher_name;
		ExpandListView listview_shoppingcart_commodity;
	}

	/**
	 * 改变数量
	 * 
	 * @param select
	 */
	private void updateQuantity(int quantity, String itemKey, int orgId, String shopInfId, String skuId, int num,
			int state) {
		if (dialogtextview_account != null && "0".equals(dialogtextview_account.getText().toString().trim())) {
			ToastUtil.show(context, "商品数量不能为0");
			return;
		}

		if (MyApplication.mapp.getCookieStore() == null) {
			new InitShopcart(mShoppingCartFragment, state, null, "改变商品数量失败", "正在请求数据，请稍后...", shopInfId, 0, skuId, 0,
					num);
			return;
		}

		BaseTask baseTask = new BaseTask(context);
		RequestParams params = new RequestParams();
		showDialog("正在请求数据，请稍后...");
		params.addBodyParameter("type", "normal");
		params.addBodyParameter("handler", "sku");
		params.addBodyParameter("itemKey", itemKey);
		params.addBodyParameter("quantity", quantity + "");
		params.addBodyParameter("orgId", orgId + "");
		baseTask.askCookieRequest(SystemConfig.UPDATE_QUANTITY, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("shoppingcart", arg0.result.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(arg0.result);
					ShoppingCartBean bean = JSON.parseObject(obj.get("result").toString(), ShoppingCartBean.class);
					ShoppingCartBean bean1 = JSON.parseObject(obj.get("result").toString(), ShoppingCartBean.class);
					// 给页面填充数据
					if (bean != null) {
						try {
							mShoppingCartFragment.update(bean1);
						} catch (CloneNotSupportedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						for (int i = 0; i < listDelete.size(); i++) {
							ShoppingCartShopBean shopBean = listDelete.get(i);
							if (shopBean.getIsSelectAll() == true) {
								bean.getShoppingCartVos().get(i).setIsSelectAll(true);
							} else {
								bean.getShoppingCartVos().get(i).setIsSelectAll(false);
								for (int j = 0; j < shopBean.getItems().size(); j++) {
									if (shopBean.getItems().get(j).getItemSelected() == true) {
										bean.getShoppingCartVos().get(i).getItems().get(j).setItemSelected(true);
									} else {
										bean.getShoppingCartVos().get(i).getItems().get(j).setItemSelected(false);
									}
								}
							}
						}
						ShoppingcartEditAdapter.this.setData(bean);
						ShoppingcartEditAdapter.this.notifyDataSetChanged();
						if (context instanceof MainActivity) {
							((MainActivity) context).getCartNum();
						}
						dismissDialog();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				ToastUtil.show(context, "改变商品数量失败");
				dismissDialog();
			}
		});
	}

	private Dialog dialog;

	/**
	 * 公共类加载数据效果
	 * 
	 * @param message
	 */
	@SuppressLint("InflateParams")
	public void showDialog(String message) {
		dialog = new Dialog(context, R.style.dialog_progress);
		View view = context.getLayoutInflater().inflate(R.layout.base_load_dialog, null);
		TextView textView = (TextView) view.findViewById(R.id.progressbar_message);
		textView.setText(message);
		dialog.setContentView(view);
		dialog.setCancelable(false);
		dialog.show();
	}

	/*** 关闭Dialog */
	public void dismissDialog() {
		if (dialog != null) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	}
}
