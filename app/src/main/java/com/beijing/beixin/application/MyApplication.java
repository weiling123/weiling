package com.beijing.beixin.application;

import java.util.ArrayList;

import org.apache.http.client.CookieStore;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.WindowManager;

import com.beijing.beixin.pojo.DataShop;
import com.beijing.beixin.pojo.UserInfoBean;
import com.beijing.beixin.pojo.VersionBean;
import com.beijing.beixin.ui.myself.login.LoginActivity;
import com.beijing.beixin.utils.sqlite.DBServer;
import com.lidroid.xutils.http.RequestParams;

/**
 * 主application 存放一些全局变量
 * 
 * @author liangshibin 2015-12-25
 */
public class MyApplication extends Application {

	private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
	private DataShop[] mDataShop;

	public WindowManager.LayoutParams getMywmParams() {
		return wmParams;
	}

	public static MyApplication mapp;
	public static DBServer mServer;
	public static String className;
	private String certUserName;// 账号
	private String certPassword;// 密码
	private CookieStore cookieStore;// 存放登录cookie
	private UserInfoBean userInfoBean;// 用户信息
	private String isopen;// 判断是不是第一次登录
	private String version;// 版本号
	private VersionBean versionBean = null;
	private String totalMoney = "";
	private Context mContext;
	private String mobile;

	private int mUserSexCode = -1;

	public UserInfoBean getUserInfoBean() {
		return userInfoBean;
	}

	public void setUserInfoBean(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}

	public String getCertUserName() {
		return certUserName;
	}

	public void setCertUserName(String certUserName) {
		this.certUserName = certUserName;
	}

	public String getCertPassword() {
		return certPassword;
	}

	public void setCertPassword(String certPassword) {
		this.certPassword = certPassword;
	}

	public CookieStore getCookieStore() {
		return cookieStore;
	}

	public void setCookieStore(CookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}

	public String getUsermobile() {
		if (mobile == null && !"".equals(mobile)) {
			mobile = getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("UserMobile", "");
		}
		return mobile;
	}

	public void setUsermobile(String mobile) {
		this.mobile = mobile;
		getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit().putString("UserMobile", mobile).commit();
	}

	public int getUserSexCode() {
		if (mUserSexCode == -1) {
			mUserSexCode = getSharedPreferences("userInfo", Context.MODE_PRIVATE).getInt("UserSexCode", 0);
		}
		return mUserSexCode;
	}

	public void setUserSexCode(int userSexCode) {
		mUserSexCode = userSexCode;
		getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit().putInt("UserSexCode", userSexCode).commit();
	}

	public String getTotalMoney() {
		totalMoney = getSharedPreferences("pay", Context.MODE_PRIVATE).getString("totalMoney", "false");
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = isopen;
		getSharedPreferences("pay", Context.MODE_PRIVATE).edit().putString("totalMoney", totalMoney).commit();
	}

	public String getIsopen() {
		isopen = getSharedPreferences("isopen", Context.MODE_PRIVATE).getString("isopen", "false");
		return isopen;
	}

	public void setIsopen(String isopen) {
		this.isopen = isopen;
		getSharedPreferences("isopen", Context.MODE_PRIVATE).edit().putString("isopen", isopen).commit();
	}

	public void clears() {
		this.certUserName = "";
		this.certPassword = "";
		this.userInfoBean = null;
		this.cookieStore = null;
	}

	public void clear() {
		SharedPreferences settings = getSharedPreferences(LoginActivity.SETTING_INFOS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("pwd", "");
		editor.commit();
		this.certUserName = "";
		this.certPassword = "";
		this.userInfoBean = null;
		this.cookieStore = null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		mapp = this;
		mServer = new DBServer(this);
		// CrashHandler crashHandler = CrashHandler.getInstance();
		// // 注册crashHandler
		// crashHandler.init(getApplicationContext());
	}

	public DataShop[] getDataShop() {
		if (mDataShop == null) {
			mDataShop = mServer.getDataShopCart();
		}
		return mDataShop;
	}

	public int getShopNum() {
		mDataShop = getDataShop();
		if (mDataShop == null) {
			return 0;
		}
		int resultNum = 0;
		for (int i = 0; i < mDataShop.length; i++) {
			resultNum += mDataShop[i].getNumber();
		}
		return resultNum;
	}

	public int addDataShop(DataShop dataShop) {
		return changeDataShop(dataShop);
	}

	public void setDateShop(DataShop[] dataShop) {
		mDataShop = dataShop;
		mServer.updateDataShopCart(mDataShop);
	}

	private synchronized int changeDataShop(DataShop dataShop) {
		mDataShop = getDataShop();
		if (mDataShop == null) {
			mDataShop = new DataShop[] { dataShop };
			mServer.updateDataShopCart(mDataShop);
			return dataShop.getNumber();
		}

		boolean isAddSame = false;
		for (int i = 0; i < mDataShop.length; i++) {
			if ((dataShop.equals(mDataShop[i]))) {
				isAddSame = true;
				mDataShop[i].addNumber(dataShop.getNumber());
				break;
			}
		}
		if (!isAddSame) {
			DataShop[] temp = mDataShop;
			mDataShop = new DataShop[mDataShop.length + 1];
			System.arraycopy(temp, 0, mDataShop, 0, temp.length);
			mDataShop[temp.length] = dataShop;
		}
		mServer.updateDataShopCart(mDataShop);
		return getShopNum();
	}

	public static final int ST_NOCHANGE = 0;

	public static final int ST_SELECT = 1;

	public static final int ST_NO_SELECT = 2;

	public static final int ST_ADD_NUM = 3;

	public static final int ST_CHANGE_NUM = 4;

	public RequestParams getShopcartParam(int state, DataShop[] dataShopDel, String shopInfId, int shopCheckState,
			String skuId, int skuIdCheckState, int num) {

		int isSelectAll = 1;
		DataShop[][] dataShopss = getDataShop(dataShopDel);
		if (dataShopss == null || dataShopss.length == 0) {
			return null;
		}

		StringBuffer shopParam = new StringBuffer();
		for (DataShop[] dataShopItems : dataShopss) {
			shopParam.append(dataShopItems[0].getShopInfId());
			shopParam.append("s");
			for (DataShop dataShop : dataShopItems) {
				shopParam.append(dataShop.getSkuId());
				shopParam.append(",");
				if (num != 0) {
					if (state == ST_ADD_NUM && dataShop.getShopInfId().equals(shopInfId)
							&& dataShop.getSkuId().equals(skuId)) {
						int numResult = dataShop.getNumber() + num;
						shopParam.append(numResult);
					} else if (dataShop.getShopInfId().equals(shopInfId) && dataShop.getSkuId().equals(skuId)) {
						shopParam.append(num);
					} else {
						shopParam.append(dataShop.getNumber());
					}
				} else {
					shopParam.append(dataShop.getNumber());
				}
				shopParam.append(",");
				switch (state) {
				case ST_NOCHANGE:
					if (dataShop.getShopInfId().equals(shopInfId) && TextUtils.isEmpty(skuId)) {
						shopParam.append(shopCheckState);
						if (shopCheckState == 0) {
							isSelectAll = 0;
						}
					} else if (dataShop.getShopInfId().equals(shopInfId) && dataShop.getSkuId().equals(skuId)) {
						shopParam.append(skuIdCheckState);
						if (skuIdCheckState == 0) {
							isSelectAll = 0;
						}
					} else {
						shopParam.append(dataShop.getCheckState());
						if (dataShop.getCheckState() == 0) {
							isSelectAll = 0;
						}
					}

					break;
				case ST_SELECT:
					shopParam.append(1);
					break;
				case ST_NO_SELECT:
					isSelectAll = 0;
					shopParam.append(0);
					break;
				default:
					shopParam.append(dataShop.getCheckState());
					if (dataShop.getCheckState() == 0) {
						isSelectAll = 0;
					}
					break;

				}
				shopParam.append(":");
			}
			shopParam.delete(shopParam.length() - 1, shopParam.length());
			shopParam.append("|");
		}
		shopParam.delete(shopParam.length() - 1, shopParam.length());
		RequestParams params = new RequestParams();
		params.addBodyParameter("infoStr", shopParam.toString());
		params.addBodyParameter("isSelectAll", isSelectAll + "");
		return params;
	}

	public void emptyDataShop() {
		mDataShop = null;
		mServer.emptyDataShopCart();
	}

	private DataShop[][] getDataShop(DataShop[] dataShopDel) {
		mDataShop = getDataShop();
		if (mDataShop == null) {
			return null;
		}

		DataShop[] dataShopNets;

		if (dataShopDel != null) {
			ArrayList<DataShop> arrayListNet = new ArrayList<DataShop>();
			for (DataShop dataShop : mDataShop) {
				out: for (DataShop itemDel : dataShopDel) {
					if (dataShop.equals(itemDel)) {
						continue out;
					}
				}
				arrayListNet.add(dataShop);
			}
			dataShopNets = new DataShop[arrayListNet.size()];
			dataShopNets = arrayListNet.toArray(dataShopNets);
		} else {
			dataShopNets = mDataShop;
		}

		if (dataShopNets.length == 0) {
			return null;
		}
		ArrayList<ArrayList<DataShop>> arrayList = new ArrayList<ArrayList<DataShop>>();

		out: for (DataShop dataShopNetItem : dataShopNets) {
			for (int i = 0; i < arrayList.size(); i++) {
				if (dataShopNetItem.getShopInfId().equals(arrayList.get(i).get(0).getShopInfId())) {
					arrayList.get(i).add(dataShopNetItem);
					continue out;
				}
			}

			ArrayList<DataShop> itemList = new ArrayList<DataShop>();
			itemList.add(dataShopNetItem);
			arrayList.add(itemList);
		}

		DataShop[][] dataShops = new DataShop[arrayList.size()][];
		for (int i = 0; i < dataShops.length; i++) {
			dataShops[i] = new DataShop[arrayList.get(i).size()];
			dataShops[i] = arrayList.get(i).toArray(dataShops[i]);
		}
		return dataShops;
	}

	public void delDateShop(DataShop[] dataShopDels) {
		mDataShop = getDataShop();
		ArrayList<DataShop> arrayList = new ArrayList<DataShop>();
		out: for (DataShop dataShop : mDataShop) {
			for (DataShop dataShopDel : dataShopDels) {
				if (dataShop.equals(dataShopDel)) {
					continue out;
				}
			}
			arrayList.add(dataShop);
		}

		mDataShop = new DataShop[arrayList.size()];
		mDataShop = arrayList.toArray(mDataShop);
		mServer.updateDataShopCart(mDataShop);
	}
}
