package com.beijing.beixin.ui.fragment;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.ViewHolder;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.AppProductBaseVo;
import com.beijing.beixin.pojo.PrestoreTransactionLogBean;
import com.beijing.beixin.pojo.UseredInfoBean;
import com.beijing.beixin.tasks.BaseTask;
import com.beijing.beixin.ui.base.BaseFragment;
import com.beijing.beixin.ui.myself.AccountMoneyActivity;
import com.beijing.beixin.ui.myself.AssetCenterActivity;
import com.beijing.beixin.ui.myself.BrowseTheFootprintActivity;
import com.beijing.beixin.ui.myself.MyAccountActivity;
import com.beijing.beixin.ui.myself.MyCollectionActivity;
import com.beijing.beixin.ui.myself.MyCouponActivity;
import com.beijing.beixin.ui.myself.MyTicketActivity;
import com.beijing.beixin.ui.myself.SettingActivity;
import com.beijing.beixin.ui.myself.login.LoginActivity;
import com.beijing.beixin.ui.myself.order.OrderActivity;
import com.beijing.beixin.ui.myself.qrcode.QrcodeActivity;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.IgnitedDiagnosticsUtils;
import com.beijing.beixin.utils.MyGridView;
import com.beijing.beixin.utils.NetWorkUtils;
import com.beijing.beixin.utils.RoundImageView;
import com.beijing.beixin.utils.ToastUtil;
import com.beijing.beixin.utils.Utils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 我的
 * 
 * @author ouyanghao
 * 
 */
@SuppressLint("ValidFragment")
public class MySelfFragment extends BaseFragment implements OnClickListener {

	/******************************************************************************/
	/**
	 * 头像
	 */
	private RoundImageView iv_personHeader;
	/**
	 * 会员名称
	 */
	private TextView tv_myselfvipname;
	/**
	 * 二维码
	 */
	private ImageView iv_myselfvipZxing;
	/**
	 * 会员等级图标
	 */
	private ImageView iv_myselflevel_image;
	/**
	 * 会员等级名称
	 */
	private TextView tv_myselflevel_name;
	/**
	 * 账户管理，收货地址
	 */
	private LinearLayout ll_myselfAddress;
	/**
	 * 收藏的宝贝数量
	 */
	private TextView baby_count;
	/**
	 * 收藏的店铺数量
	 */
	private TextView shop_count;
	/**
	 * 浏览足迹数量
	 */
	private TextView footprint_count;
	/**
	 * 查看订单
	 */
	private LinearLayout ll_myselfOrder;
	/**
	 * 待付款
	 */
	private LinearLayout tv_obligation;
	/**
	 * 待收货
	 */
	private LinearLayout tv_geting;
	/**
	 * 待自提
	 */
	private LinearLayout tv_myselfgeting;
	/**
	 * 待评价
	 */
	private LinearLayout tv_contenting;
	/**
	 * 返修/换货
	 */
	private LinearLayout tv_backorreturn;
	/**
	 * 资产中心
	 */
	private LinearLayout ll_myselfAssets;
	/**
	 * 账户余额
	 */
	private TextView tv_account;
	private LinearLayout ll_account;
	/**
	 * 优惠券
	 */
	private TextView tv_coupons;
	private LinearLayout ll_coupon;
	/**
	 * 浏览足迹
	 */
	private LinearLayout ll_footprint;
	private LinearLayout ll_userinfo;
	private LinearLayout ll_myselfTicket;
	private TextView loginOrRegister;
	private TextView tv_nubercount;
	BitmapUtil bitmapUtil;
	UseredInfoBean infoBean;
	List<PrestoreTransactionLogBean> mLogBeans;
	// 标志位，标志已经初始化完成。
	private boolean isPrepared;
	/**
	 * 订单状态count obligationcount 待付款 myselfgetingcount 待发货 getingcount 待收货
	 * contentingcount 待评价
	 */
	private TextView obligationcount;
	private TextView myselfgetingcount;
	private TextView getingcount;
	private TextView contentingcount;
	private MyGridView gridview_recommend;

	/**
	 * 构造
	 * 
	 * @param context
	 */
	public MySelfFragment() {
	}

	private int cartPosition;
	private List<AppProductBaseVo> listRecommend;
	private TextView title;
	private ImageView right;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.myself_member_fragment, container, false);
		init(view);
		isPrepared = true;
		return view;
	}

	public void setPoistion(int position) {
		cartPosition = position;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (infoBean == null && MyApplication.mapp.getCookieStore() != null && cartPosition == 4) {
			sendhttp();
		}
		if (MyApplication.mapp.getCookieStore() == null) {
			infoBean = null;
			ll_userinfo.setVisibility(View.GONE);
			loginOrRegister.setVisibility(View.VISIBLE);
			obligationcount.setVisibility(View.INVISIBLE);
			myselfgetingcount.setVisibility(View.INVISIBLE);
			getingcount.setVisibility(View.INVISIBLE);
			contentingcount.setVisibility(View.INVISIBLE);
			baby_count.setText("0");
			shop_count.setText("0");
			footprint_count.setText("0");
			tv_account.setText("0.00");
			tv_coupons.setText("0");
			tv_nubercount.setText("0");
			footprint_count.setText("0");
			iv_personHeader.setImageResource(R.drawable.mine_defaulticon);
		}
		if (!"".equals(MyApplication.mapp.getCertUserName())) {
			if (MyApplication.mServer.findAllFootInfo(MyApplication.mapp.getCertUserName()).size() == 0) {
				footprint_count.setText(0 + "");
			} else {
				footprint_count.setText(
						MyApplication.mServer.findAllFootInfo(MyApplication.mapp.getCertUserName()).size() + "");
			}
		}
	}

	/**
	 * 绑定控件ID及监听
	 * 
	 * @param v
	 */
	public void init(View v) {
		title = (TextView) v.findViewById(R.id.navigation_title);
		ImageView left = (ImageView) v.findViewById(R.id.navigationLeftImageBtn);
		right = (ImageView) v.findViewById(R.id.navigationRightImageBtn);
		right.setImageResource(R.drawable.comdemore);
		title.setText("会员中心");
		left.setImageResource(R.drawable.setup);
		iv_personHeader = (RoundImageView) v.findViewById(R.id.iv_personHeader);
		tv_myselfvipname = (TextView) v.findViewById(R.id.tv_myselfvipname);
		obligationcount = (TextView) v.findViewById(R.id.obligationcount);
		myselfgetingcount = (TextView) v.findViewById(R.id.myselfgetingcount);
		getingcount = (TextView) v.findViewById(R.id.getingcount);
		contentingcount = (TextView) v.findViewById(R.id.contentingcount);
		iv_myselfvipZxing = (ImageView) v.findViewById(R.id.iv_myselfvipZxing);
		iv_myselflevel_image = (ImageView) v.findViewById(R.id.iv_myselflevel_image);
		tv_myselflevel_name = (TextView) v.findViewById(R.id.tv_myselflevel_name);
		ll_myselfAddress = (LinearLayout) v.findViewById(R.id.ll_myselfAddress);
		ll_userinfo = (LinearLayout) v.findViewById(R.id.ll_userinfo);
		ll_myselfTicket = (LinearLayout) v.findViewById(R.id.ll_myselfTicket);
		baby_count = (TextView) v.findViewById(R.id.baby_count);
		shop_count = (TextView) v.findViewById(R.id.shop_count);
		tv_nubercount = (TextView) v.findViewById(R.id.tv_nubercount);
		footprint_count = (TextView) v.findViewById(R.id.footprint_count);
		ll_myselfOrder = (LinearLayout) v.findViewById(R.id.ll_myselfOrder);
		ll_account = (LinearLayout) v.findViewById(R.id.ll_account);
		SetDrawable(v);
		ll_myselfAssets = (LinearLayout) v.findViewById(R.id.ll_myselfAssets);
		tv_account = (TextView) v.findViewById(R.id.tv_account);
		loginOrRegister = (TextView) v.findViewById(R.id.loginOrRegister);
		tv_account.setOnClickListener(this);
		tv_coupons = (TextView) v.findViewById(R.id.tv_coupons);
		ll_coupon = (LinearLayout) v.findViewById(R.id.ll_coupon);
		ll_coupon.setOnClickListener(this);
		ll_footprint = (LinearLayout) v.findViewById(R.id.ll_footprint);
		gridview_recommend = (MyGridView) v.findViewById(R.id.gridview_recommend);
		LinearLayout layout_my_collection_pro = (LinearLayout) v.findViewById(R.id.layout_my_collection_pro);
		layout_my_collection_pro.setOnClickListener(this);
		LinearLayout layout_my_collection_shop = (LinearLayout) v.findViewById(R.id.layout_my_collection_shop);
		layout_my_collection_shop.setOnClickListener(this);
		left.setOnClickListener(this);
		ll_footprint.setOnClickListener(this);
		loginOrRegister.setOnClickListener(this);
		iv_personHeader.setOnClickListener(this);
		iv_myselfvipZxing.setOnClickListener(this);
		ll_myselfAddress.setOnClickListener(this);
		ll_myselfOrder.setOnClickListener(this);
		tv_obligation.setOnClickListener(this);
		tv_geting.setOnClickListener(this);
		tv_myselfgeting.setOnClickListener(this);
		tv_backorreturn.setOnClickListener(this);
		tv_contenting.setOnClickListener(this);
		ll_myselfAssets.setOnClickListener(this);
		iv_personHeader.setOnClickListener(this);
		ll_account.setOnClickListener(this);
		ll_myselfTicket.setOnClickListener(this);
		bitmapUtil = new BitmapUtil();
		ll_userinfo.setVisibility(View.GONE);
		loginOrRegister.setVisibility(View.VISIBLE);
		gridview_recommend.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), BookDetailActivity.class);
				intent.putExtra("ProductId", listRecommend.get(position).getProductId() + "");
				getActivity().startActivity(intent);
			}
		});
		right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showwindow(right);
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
			title.setFocusable(true);
			title.setFocusableInTouchMode(true);
			title.requestFocus();
		}
	}

	/**
	 * 设置图片大小 b km
	 * 
	 * @param v
	 */
	public void SetDrawable(View v) {
		// 待付款
		tv_obligation = (LinearLayout) v.findViewById(R.id.tv_obligation);
		// 待收货
		tv_geting = (LinearLayout) v.findViewById(R.id.tv_geting);
		// 待自提
		tv_myselfgeting = (LinearLayout) v.findViewById(R.id.tv_myselfgeting);
		// 待评价
		tv_contenting = (LinearLayout) v.findViewById(R.id.tv_contenting);
		// 返修/退款
		tv_backorreturn = (LinearLayout) v.findViewById(R.id.tv_backorreturn);
	}

	/**
	 * 获取用户信息
	 */
	public void sendhttp() {
		showDialog("正在请求用户信息。。。");
		BaseTask task = new BaseTask(getActivity());
		task.askCookieRequest(SystemConfig.USERINFO, null, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				JSONObject object;
				try {
					object = new JSONObject(arg0.result);
					Log.e("用户信息", object.getString("result").toString());
					infoBean = JSON.parseObject(object.getString("result").toString(), UseredInfoBean.class);
					tv_coupons.setText(infoBean.getCouponTotalCount() + "");
					baby_count.setText(infoBean.getCollectionTotalCount() + "");
					shop_count.setText(infoBean.getCollectionShopCount() + "");
					MyApplication.mapp.setUserSexCode(infoBean.getUserSexCode());
					tv_myselflevel_name.setText(infoBean.getUserLevelNm());
					BitmapUtils util = new BitmapUtils(getActivity());
					util.configDefaultLoadingImage(R.drawable.mine_defaulticon);
					util.configDefaultLoadFailedImage(R.drawable.mine_defaulticon);
					util.configDefaultConnectTimeout(10 * 1000);
					util.configDefaultReadTimeout(10 * 1000);
					util.display(iv_personHeader, infoBean.getUserIcon());
					MyApplication.mapp.getUserInfoBean().setUserEmail(infoBean.getUserEmile());
					tv_myselfvipname.setText(infoBean.getLoginId());
					if (infoBean.getIntegral() != null) {
						tv_nubercount.setText(infoBean.getIntegral() + "");
					}
					if (infoBean.getToPayTotalCount() > 0) {
						obligationcount.setVisibility(View.VISIBLE);
						if (infoBean.getToPayTotalCount() > 100) {
							obligationcount.setText("99+");
						} else {
							obligationcount.setText(infoBean.getToPayTotalCount() + "");
						}
					} else {
						obligationcount.setVisibility(View.INVISIBLE);
					}
					if (infoBean.getToSendTotalCount() > 0) {
						myselfgetingcount.setVisibility(View.VISIBLE);
						if (infoBean.getToSendTotalCount() > 100) {
							myselfgetingcount.setText("99+");
						} else {
							myselfgetingcount.setText(infoBean.getToSendTotalCount() + "");
						}
					} else {
						myselfgetingcount.setVisibility(View.INVISIBLE);
					}
					if (infoBean.getToReceiveTotalCount() > 0) {
						getingcount.setVisibility(View.VISIBLE);
						if (infoBean.getToReceiveTotalCount() > 100) {
							getingcount.setText("99+");
						} else {
							getingcount.setText(infoBean.getToReceiveTotalCount() + "");
						}
					} else {
						getingcount.setVisibility(View.INVISIBLE);
					}
					if (infoBean.getFinishedTotalCount() > 0) {
						contentingcount.setVisibility(View.VISIBLE);
						if (infoBean.getFinishedTotalCount() > 100) {
							contentingcount.setText("99+");
						} else {
							contentingcount.setText(infoBean.getFinishedTotalCount() + "");
						}
					} else {
						contentingcount.setVisibility(View.INVISIBLE);
					}
					ll_userinfo.setVisibility(View.VISIBLE);
					loginOrRegister.setVisibility(View.GONE);
					getProductIdByPlucode("1");
				} catch (JSONException e) {
					e.printStackTrace();
					dismissDialog();
				}
				dismissDialog();
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dismissDialog();
				Log.e("用户信息异常", arg0.toString());
			}
		});
	}

	/**
	 * 查询用户账户余额或积分type 0为积分 1为余额
	 * 
	 * @param type
	 */
	private void getProductIdByPlucode(String type) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("type", type);
		params.addBodyParameter("userId", MyApplication.mapp.getUserInfoBean().getSysUserId() + "");
		BaseTask task = new BaseTask(getActivity());
		task.askCookieRequest(SystemConfig.GET_USER_PRESTORE, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.e("test", "积分或余额获取失败");
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					Log.e("积分或余额", arg0.result);
					JSONObject obj = new JSONObject(arg0.result);
					if (obj.getBoolean("success")) {
						tv_account.setText("¥" + Utils.parseDecimalDouble2(obj.getString("result").toString()));

					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
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
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// ToastUtil.show(getActivity(), "为你推荐加载失败");
				Log.e("为你推荐", "为你推荐加载失败");
			}
		});
	}

	public static final int HEADER_CODE = 101;
	public static final int NAME_HEADER_CODE = 102;
	private static final int RESULT_OK = 0;

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		if (MyApplication.mapp.getCookieStore() == null) {
			startActivity(LoginActivity.class);
			return;
		}
		switch (v.getId()) {
		case R.id.iv_personHeader:
			if (infoBean != null) {
				Intent intentheader = new Intent(getActivity(), MyAccountActivity.class);
				intentheader.putExtra("headimage", infoBean.getUserIcon());
				intentheader.putExtra("name", infoBean.getUserName());
				intentheader.putExtra("sex", MyApplication.mapp.getUserSexCode() + "");
				intentheader.putExtra("username", infoBean.getLoginId() + "");
				intentheader.putExtra("userLevelNm", infoBean.getUserLevelNm());
				startActivityForResult(intentheader, HEADER_CODE);
			} else {
				startActivity(LoginActivity.class);
			}
			break;
		case R.id.iv_myselfvipZxing:
			if (infoBean != null) {
				Intent intent5 = new Intent(getActivity(), QrcodeActivity.class);
				intent5.putExtra("headimage", infoBean.getUserIcon());
				intent5.putExtra("cardNo", infoBean.getCardNo());
				startActivity(intent5);
			} else {
				startActivity(LoginActivity.class);
			}
			break;
		case R.id.ll_myselfAddress:
			if (infoBean != null) {
				Intent intentheaders = new Intent(getActivity(), SettingActivity.class);
				intentheaders.putExtra("headimage", infoBean.getUserIcon());
				intentheaders.putExtra("name", infoBean.getUserName());
				intentheaders.putExtra("sex", MyApplication.mapp.getUserSexCode() + "");
				intentheaders.putExtra("username", infoBean.getLoginId() + "");
				intentheaders.putExtra("userLevelNm", infoBean.getUserLevelNm());
				startActivity(intentheaders);
			} else {
				startActivity(LoginActivity.class);
			}
			break;
		case R.id.ll_myselfOrder:
			startActivityForResult(new Intent(getActivity(), OrderActivity.class), 1003);
			break;
		case R.id.tv_obligation:
			Intent intent = new Intent(getActivity(), OrderActivity.class);
			intent.putExtra("state", "1");
			startActivityForResult(intent, 1003);
			break;
		case R.id.tv_geting:// 收货
			Intent intent1 = new Intent(getActivity(), OrderActivity.class);
			intent1.putExtra("state", "3");
			startActivityForResult(intent1, 1003);
			break;
		case R.id.tv_myselfgeting:// 发货
			Intent intent2 = new Intent(getActivity(), OrderActivity.class);
			intent2.putExtra("state", "2");
			startActivityForResult(intent2, 1003);
			break;
		case R.id.tv_contenting:
			Intent intent3 = new Intent(getActivity(), OrderActivity.class);
			intent3.putExtra("state", "4");
			startActivityForResult(intent3, 1003);
			break;
		case R.id.tv_backorreturn:
			ToastUtil.show(getActivity(), "此功能暂未开放");
			break;
		case R.id.ll_myselfAssets:
			Intent intentAsset = new Intent(getActivity(), AssetCenterActivity.class);
			intentAsset.putExtra("money", tv_account.getText().toString().trim());
			intentAsset.putExtra("coupon", tv_coupons.getText().toString().trim());
			startActivity(intentAsset);
			break;
		case R.id.ll_coupon:
			startActivity(MyCouponActivity.class);
			break;
		case R.id.ll_footprint:
			startActivity(BrowseTheFootprintActivity.class);
			break;
		case R.id.layout_my_collection_pro:
			Intent intent4 = new Intent(getActivity(), MyCollectionActivity.class);
			intent4.putExtra("flag", "pro");
			startActivity(intent4);
			break;
		case R.id.layout_my_collection_shop:
			Intent intent6 = new Intent(getActivity(), MyCollectionActivity.class);
			intent6.putExtra("flag", "shop");
			startActivity(intent6);
			break;
		case R.id.ll_account:
			Intent intentAccount = new Intent(getActivity(), AccountMoneyActivity.class);
			intentAccount.putExtra("money", tv_account.getText().toString().trim());
			startActivity(intentAccount);
			break;
		case R.id.ll_myselfTicket:
			Intent intentTicker = new Intent(getActivity(), MyTicketActivity.class);
			startActivity(intentTicker);
			break;
		case R.id.loginOrRegister:
			startActivity(LoginActivity.class);
		case R.id.navigationLeftImageBtn:
			startActivity(SettingActivity.class);
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == HEADER_CODE && resultCode == 0) {
			if (data == null) {
				return;
			}
			if ("heade".equals(data.getExtras().getString("heade"))) {
				sendhttp();
			}
		}
		if (requestCode == 1003 && resultCode == 1004) {
			sendhttp();
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			findRecommend();
			cartPosition = 4;
			if (MyApplication.mapp.getCookieStore() != null && isPrepared == true) {
				sendhttp();
			} else if (MyApplication.mapp.getCookieStore() == null && isPrepared == true) {
				infoBean = null;
				ll_userinfo.setVisibility(View.GONE);
				loginOrRegister.setVisibility(View.VISIBLE);
				baby_count.setText("0");
				shop_count.setText("0");
				footprint_count.setText("0");
				tv_account.setText("0.00");
				tv_coupons.setText("0");
				tv_nubercount.setText("0");
				iv_personHeader.setImageResource(R.drawable.usericon);
			}
		} else {
			// 相当于Fragment的onPause
		}
	}
}
