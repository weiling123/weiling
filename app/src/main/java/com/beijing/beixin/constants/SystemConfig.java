package com.beijing.beixin.constants;

import android.annotation.SuppressLint;

/**
 * 接口请求的地址等一些基本信息
 * 
 * @author liangshibin 4j
 */
@SuppressLint("SdCardPath")
public class SystemConfig {

	public static boolean IS_UAT = true;
	/**
	 * 数据库开发测试模式切换 true-debug模式 ;false-生产模式
	 */
	public static boolean DB_DEBUG_MODEL = false;
	// /////////////////////////////////////////////////////////////////////////////
	/**
	 * 当前数据库DB的版本 findRecommend 初始是2
	 */
	public static int DB_VERSION = 6;
	/**
	 * 数据库名称
	 */
	// public static final String DATABASE_NAME = "dev_db";//开发
	public static final String DATABASE_NAME = "test_db1";// 测试
	// public static final String DATABASE_NAME = "db";//生产
	/**
	 * 检查APK新版本的url
	 */
	// public static String APK_CHECK_URL="http://...";//开发
	public static String APK_CHECK_URL = "http://...";// 测试
	// public static String APK_CHECK_URL="http://...";//生产
	/**
	 * 新版本apk下载url
	 */
	// public static String APK_DOWNLOAD_URL="http://...";//开发
	public static String APK_DOWNLOAD_URL = "http://...";// 测试
	// public static String APK_DOWNLOAD_URL="http://...";//生产
	/**
	 * 请求服务的IP地址
	 */
	public static String SERVER_IP_UAT = "112.33.7.55";// UAT地址（云端测试）
	// public static String SERVER_IP = "112.33.4.24";// POC地址（云端测试）
	// public static String SERVER_IP = "10.20.220.205";// 开发测试地址1111
	// public static String SERVER_IP = "192.168.1.115";// PC（生产）
	public static String SERVER_IP = "b2cmob.bxmedia.net";// PC（生产）
	// public static String SERVER_IP_UAT="192.168.0.112";//PC（生产）
	// public static String SERVER_IP = "192.168.1.114";// PC（生产）
	/**
	 * 请求服务的端口号
	 */
	// public static String SERVER_PORT = "8080";// 服务器端口（开发）
	public static String SERVER_PORT = "81";// 服务器端口（测试）/
	// /////////////////////////////////////////////////////////////////////////////
	/**
	 * 请求服务的前缀
	 */
	public static String SERVER_PRE = "http://";
	/**
	 * 请求服务的服务名
	 */
	public static String SERVER_NAME = "app";// 服务器地址

	/**
	 * 请求服务的服务名
	 */
	// public static String SERVER_NAME = "beifa/app";// 服务器地址/

	/**
	 * 生产拼接请求服务器地址的方法
	 * 
	 * @return
	 */
	public static String getRequestServerUrl() {
		// return "http://192.168.1.101:8080/beifa/app/";
		if (IS_UAT) {
			StringBuffer buffer = new StringBuffer("");
			buffer.append(SERVER_PRE);
			buffer.append(SERVER_IP_UAT);
			buffer.append(":");
			buffer.append(SERVER_PORT);
			buffer.append("/");
			buffer.append(SERVER_NAME);
			buffer.append("/");
			return buffer.toString();
		}
		StringBuffer buffer = new StringBuffer("");
		buffer.append(SERVER_PRE);
		buffer.append(SERVER_IP);
		buffer.append("/");
		buffer.append(SERVER_NAME);
		buffer.append("/");
		return buffer.toString();

	}

	/**
	 * 拼接请求服务器地址的方法
	 */
	/**
	 * @return
	 */
	public static String getRequestServeralipayUrl() {
		StringBuffer buffer = new StringBuffer("");
		buffer.append(SERVER_PRE);
		buffer.append(SERVER_IP);
		buffer.append(":");
		buffer.append(SERVER_PORT);
		buffer.append("/");
		// buffer.append("payment");
		buffer.append(SERVER_NAME);
		buffer.append("/");
		return buffer.toString();
	}

	/**
	 * sap账号密码登录
	 */
	public static String LOGIN_ACTION = "login";
	/**
	 * sap密码修改
	 */
	public static String PASSWORD_ACTION = "passwordmodify";
	/******************************* 请求服务的模块名以及方法名 ******************************/
	/**
	 * 登录
	 */
	public static String INDEX = getRequestServerUrl() + "index.json";
	/**
	 * 登录
	 */
	public static String LOGIN = getRequestServerUrl() + "user/login.json";
	/**
	 * 用户重置密码，发送短信验证码
	 */
	public static String REGISTER_SENDSMSVALIDATEMESSAGE = getRequestServerUrl() + "user/appSendMobileMessage.json";
	/**
	 * 该用户是否存在
	 */
	public static String EXIST_USER = getRequestServerUrl() + "user/existUser.json";
	/**
	 * 用户注册，发送短信验证码
	 */
	public static String REGISTER_SENDSMSVALIDATECODE = getRequestServerUrl() + "user/appSendMobileCode.json";
	/**
	 * 验证短信验证码
	 */
	public static String REGISTER_VERIFYVALIDATE = getRequestServerUrl() + "user/getAccountTransactionLogs.json";
	/**
	 * 重置密码
	 */
	public static String UPDATE_USER_PSW = getRequestServerUrl() + "user/updateUserPsw.json";
	/**
	 * 重置密码
	 */
	public static String UPDATE_LOGINUSER_PSW = getRequestServerUrl() + "user/updateLoginUserPsw.json";
	/**
	 * 2.1.6.用户注册，完善资料
	 */
	public static String REGISTER = getRequestServerUrl() + "user/appRegister.json";
	/**
	 * 2.1.7.查询用户详情信息
	 */
	public static String USERINFO = getRequestServerUrl() + "user/getUser.json";
	/**
	 * 2.1.12.查询用户账户
	 */
	public static String PRESTORETRANSACTIONLOGS = getRequestServerUrl() + "user/getPrestoreTransactionLogs.json";
	/**
	 * 修改支付密码 发送验证码
	 */
	public static String SEND_VALIDATE_CODE = getRequestServerUrl() + "user/sendValidateCode.json";
	/**
	 * 修改支付密码 验证验证码
	 */
	public static String VERIFY_VALIDATE = getRequestServerUrl() + "user/verifyValidate.json";
	/**
	 * 修改支付密码 修改密码
	 */
	public static String MOD_PASSWORD = getRequestServerUrl() + "user/modPassword.json";
	/**
	 * 修改支付密码 设置新密码
	 */
	public static String SET_NEWPSW = getRequestServerUrl() + "user/setNewPayPsw.json";
	/**
	 * 2.1.12.查询门票
	 */
	public static String TICKET_LIST = getRequestServerUrl() + "ticket/ticketList.json";
	/**
	 * 商品分类
	 */
	public static String PRODUCT_TYPE = getRequestServerUrl() + "category/listCate.json";
	/**
	 * 查询商品列表
	 */
	public static String PRODUCT_LIST = getRequestServerUrl() + "category/productlist.json";
	/**
	 * 商品详情
	 */
	public static String PRODUCT_DETAIL = getRequestServerUrl() + "product/productDetail.json";
	/**
	 * 非图书类商品规格
	 */
	public static String PRODUCT_SPEC = getRequestServerUrl() + "product/getProductSpecList.json";
	/**
	 * 非图书类商品点击不同规格获取不同价格和图片
	 */
	public static String GET_PRODUCT_SKU = getRequestServerUrl() + "product/getProductSkuBySpec.json";
	/**
	 * 获取商品图文详情和出版信息 0是图文详情 1是出版信息
	 */
	public static String GET_PRODUCT_DETAIL = getRequestServerUrl() + "product/getProductDetail.json";
	/**
	 * 商品评论
	 */
	public static String PRODUCT_COMMENT = getRequestServerUrl() + "product/productComments.json";
	/**
	 * 订单列表
	 */
	public static String ORDER_LIST = getRequestServerUrl() + "order/orderList.json";
	/**
	 * 评价晒单
	 */
	public static String COMMENT_ORLDER = getRequestServerUrl() + "order/addOrderCommentAndShopRating.json";
	/**
	 * 2.1.43.用户取消订单
	 */
	public static String CANCEL_ORDER = getRequestServerUrl() + "order/cancelOrder.json";
	/**
	 * 2.1.42.用户确认收货
	 */
	public static String BUYERSIGNED = getRequestServerUrl() + "order/buyerSigned.json";
	/**
	 * 订单详情
	 */
	public static String ORDER_DETAIL = getRequestServerUrl() + "order/orderDetail.json";
	/**
	 * 物流信息
	 */
	public static String QUERYLOGISTICS = getRequestServerUrl() + "order/queryLogistics.json";
	/**
	 * 收货地址
	 */
	public static String ADDRESS_LIST = getRequestServerUrl() + "user/address.json";
	/**
	 * 2.1.22.新增或更新收货地址
	 */
	public static String ADDRESS_SAVEORUPDATE = getRequestServerUrl() + "user/address/saveOrUpdate.json";
	/**
	 * 加入购物车
	 */
	public static String ADD_CART = getRequestServerUrl() + "cart/add.json";
	/**
	 * 商品收藏
	 */
	public static String COLLECTION_PRODUCT = getRequestServerUrl() + "product/collectionProduct.json";
	/**
	 * 取消商品收藏
	 */
	public static String CANCEL_COLLECTION_PRODUCT = getRequestServerUrl() + "product/cancelCollectionProduct.json";
	/**
	 * 获取购物车
	 */
	public static String CART = getRequestServerUrl() + "cart.json";
	/**
	 * 购物车全选
	 */
	public static String SELECTALL = getRequestServerUrl() + "cart/toggleSelectAll.json";
	/**
	 * 修改购物车商品数量
	 */
	public static String UPDATE_QUANTITY = getRequestServerUrl() + "cart/update.json";
	/**
	 * 修改购物车商品数量
	 */
	public static String SELECT_SHOP = getRequestServerUrl() + "cart/toggleSelectShopItem.json";
	/**
	 * 修改购物车商品数量
	 */
	public static String SELECT_PRODUCT = getRequestServerUrl() + "cart/updateSelectItems.json";
	/**
	 * 删除购物车商品
	 */
	public static String BATCHDELETE_PRODUCT = getRequestServerUrl() + "cart/batchRemove.json";
	/**
	 * 支付方式列表
	 */
	public static String PAYWAY_LIST = getRequestServerUrl() + "cart/payWayList.json";
	/**
	 * 配送方式列表
	 */
	public static String SEND_LIST = getRequestServerUrl() + "cart/deliveryRuleList.json";
	/**
	 * 团购生成订单
	 */
	public static String GROUPBY_ADD = getRequestServerUrl() + "cart/appTuanAdd.json";
	/**
	 * web优惠券式列表
	 */
	public static String WEB_COUPON_LIST = getRequestServerUrl() + "cart/getCoupons.json";
	/**
	 * L 提交订单
	 */
	public static String ADD_ORDER = getRequestServerUrl() + "cart/addOrder.json";
	/**
	 * 保存支付方式
	 */
	public static String SAVE_PAY_WAY = getRequestServerUrl() + "cart/savePayWay.json";
	/**
	 * 保存配送方式
	 */
	public static String SAVE_SEND_WAY = getRequestServerUrl() + "cart/saveDeliveryRuleId.json";
	/**
	 * 获取地址列表
	 */
	public static String RECEIVE_ADDRESS = getRequestServerUrl() + "cart/receiverAddress.json";
	/**
	 * 保存地址列表
	 */
	public static String UPDATE_RECEIVER = getRequestServerUrl() + "cart/updateReceiver.json";
	/**
	 * 2.1.23.删除收货地址
	 */
	public static String ADDESS_DEL = getRequestServerUrl() + "user/address/deleteUserAddress.json";
	/**
	 * 2.1.21.地址AJAX联动查询
	 */
	public static String ZONE = getRequestServerUrl() + "user/zone.json";
	/**
	 * 收藏商品
	 */
	public static String PRODUCT_COLLECT = getRequestServerUrl() + "user/productCollect.json";
	/**
	 * 收藏商品
	 */
	public static String SHOP_COLLECT = getRequestServerUrl() + "user/shopCollect.json";
	/**
	 * 进入收银台页面，调用接口查询收银台支付信息
	 */
	public static String GET_CAHRGE = getRequestServerUrl() + "cart/getAppCharge.json";
	/**
	 * 调用接口，创建流水账单
	 */
	public static String GET_PAYMENT = getRequestServerUrl() + "cart/getPaymentDocumentId.json";
	/**
	 * 进入收银台页面，调用接口查询收银台支付信息
	 */
	public static String GET_CHARGE = getRequestServerUrl() + "cart/getAppCharge.json";
	/**
	 * 调用接口，使用预存款
	 */
	public static String PRESTORE_PAY = getRequestServerUrl() + "cart/prestorePay.json";
	/**
	 * 确认使用预付款
	 */
	public static String PRESTOREFULLPAY = getRequestServerUrl() + "cart/prestoreFullyPay.json";
	/**
	 * 微信支付接口
	 */
	public static String WEIXIN_PAY = getRequestServerUrl() + "weixin/payPackage.json";
	/**
	 * 支付宝支付接口
	 */
	public static String ALIPAY = getRequestServerUrl() + "payment/alipay/buildData.json";
	/**
	 * 店铺商品分类
	 */
	public static String SHOPCATEGORY = getRequestServerUrl() + "shop/shopCategory.json";
	/**
	 * 店铺商品列表
	 */
	public static String SHOPPRODUCTLIST = getRequestServerUrl() + "shop/shopProductList.json";
	/**
	 * 店铺收藏取消
	 */
	public static String ADDDELSHOP = getRequestServerUrl() + "shop/addDelShop.json";
	/**
	 * 店铺信息
	 */
	public static String SHOPINF = getRequestServerUrl() + "shop/shopInf.json";
	/**
	 * 使用plucode生成productid
	 */
	public static String PRODUCTID_BY_PLUCODE = getRequestServerUrl() + "user/getProductIdByPlucode.json";
	/**
	 * 通过userid 得到会员的积分或余额
	 */
	public static String GET_USER_PRESTORE = getRequestServerUrl() + "user/getUserIntegralPrestore.json";
	/**
	 * 使用优惠券
	 */
	public static String USER_COUPON = getRequestServerUrl() + "cart/useCoupons.json";
	/**
	 * 取消使用优惠券
	 */
	public static String CANCEL_USER_COUPON = getRequestServerUrl() + "cart/cancelUseCoupon.json";
	/**
	 * 批量收藏商品
	 */
	public static String COLLECTION_PRODUCTS = getRequestServerUrl() + "cart/collectionProducts.json";
	/**
	 * 搜索商品
	 */
	public static String SEARCH_PRODUCT = getRequestServerUrl() + "category/productInfoProxylist.json";
	/**
	 * 搜索商品
	 */
	public static String HOTKEYWORY = getRequestServerUrl() + "product/getHotKeyword.json";
	// public static String SEARCH_PRODUCT = getRequestServerUrl() +
	// "category/filterSearchProduct.json";
	/**
	 * 更多卖家
	 */
	public static String MORE_SHOP = getRequestServerUrl() + "product/productDetailInfoProxylist.json";
	/**
	 * 扫一扫
	 */
	public static String FINDPRODUCT = getRequestServerUrl() + "product/findProductDetailInfoProxylist.json";
	/**
	 * 我的优惠券
	 */
	public static String GETPASTCOUPONPAGE = getRequestServerUrl() + "cart/getPastCouponPage.json";
	/**
	 * 我的优惠券count
	 */
	public static String COUPONCOUNT = getRequestServerUrl() + "cart/getCouponCount.json";
	/**
	 * 版本更新
	 */
	public static String GETSYSTEMPARAMS = getRequestServerUrl() + "system/getSystemParams.json";
	/**
	 * 首页八个领域
	 */
	public static String CATEGORY_PROXY = getRequestServerUrl() + "category/categoryProxyList.json";
	/**
	 * 广告活动
	 */
	public static String ACTIVITYDETAIL = getRequestServerUrl() + "marketingActivity/activityDetail.json";
	/**
	 * 保存备注
	 */
	public static String SAVE_REMARK = getRequestServerUrl() + "cart/saveRemark.json";
	/**
	 * 出版社
	 */
	public static String ALLBRAND = getRequestServerUrl() + "category/allBrand.json";
	/**
	 * 筛选
	 */
	public static String FILTERSEARCHPRODUCT = getRequestServerUrl() + "category/filterSearchProduct.json";
	/**
	 * 设置支付密码
	 */
	public static String SETNEWPAYPSW = getRequestServerUrl() + "user/setNewPayPsw.json";
	/**
	 * 获取购物车数量
	 */
	// public static String GET_CART_NUM = getRequestServerUrl() +
	// "cart/getUserCartNum.json";
	/**
	 * 获取离线购物车
	 */
	public static String CART_LOCATION = getRequestServerUrl() + "cart/getShoppingCartNologin.json";
	/**
	 * 同步离线购物车
	 */
	public static String SYNC_CART_LOCATION = getRequestServerUrl() + "cart/addBatchItem.json";
	/**
	 * 再次购买
	 */
	public static String BUY_AGAIN = getRequestServerUrl() + "cart/addBatch.json";
	/**
	 * 获取购物车数量
	 */
	public static String GET_SELECTCART_NUM = getRequestServerUrl() + "cart/getUserCartNum.json";
	/**
	 * 获取全部区域的数量
	 */
	public static String ZONE_LIST = getRequestServerUrl() + "user/zoneList.json";
	/**
	 * 新品上架/精品热销
	 */
	public static String INDEX_ALL_PRODUCTS = getRequestServerUrl() + "product/indexAllProducts.json";
	/**
	 * 单取商品
	 */
	public static String INDEX_PRODUCTS = getRequestServerUrl() + "product/indexAllProduct.json";
	/**
	 * 热本商品
	 */
	public static String HOTS_PRODUCTS = getRequestServerUrl() + "indexPageHotProducts.json";
	/**
	 * 修改头像
	 */
	public static String SAVEUSERICON = getRequestServerUrl() + "user/saveUserIcon.json";
	/**
	 * 退出
	 */
	public static String LOGOUT = getRequestServerUrl() + "user/logout.json";
	/**
	 * 八个馆图片
	 */
	public static String FINDMODELBYCHANNELID = getRequestServerUrl() + "findModelByChannelName.json";
	/**
	 * 修改性别
	 */
	public static String UPDATEUSERSEXCODE = getRequestServerUrl() + "user/updateUserSexCode.json";
	/**
	 * 修改昵称
	 */
	public static String UPDATEUSERNAME = getRequestServerUrl() + "user/updateUserName.json";
	/**
	 * 修改邮箱
	 */
	public static String UPDATEUSEREMAIL = getRequestServerUrl() + "user/updateUserEmail.json";
	/**
	 * 修改电话
	 */
	public static String UPDATEUSERMOVILE = getRequestServerUrl() + "user/updateUserMobile.json";
	/**
	 * 为你推荐
	 */
	public static String FIND_RECOMMEND = getRequestServerUrl() + "findRecommendProducts.json";
	/**
	 * 为你推荐
	 */
	public static String MULTI_STANDARD = getRequestServerUrl() + "multiStandard.json";

	/**
	 * 八个馆图片
	 */
	// public static String UPDATEUSERSEXCODE = getRequestServerUrl() +
	// "user/updateUserSexCode.json";
	/******************************* 一些常量信息 ******************************/
	/**
	 * 请求成功
	 */
	public static String REQUEST_SUCCESS = "S";
	/**
	 * 请求失败
	 */
	public static String REQUEST_FAILED = "E";
	/******************************* 记录日志类 *************************************/
	/**
	 * 消息查询 日志
	 */
	public static boolean FLAG_MESSAGE_EXCEPTION_LOG = false;
}
