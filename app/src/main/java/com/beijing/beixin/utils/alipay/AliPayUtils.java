package com.beijing.beixin.utils.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.alipay.sdk.app.PayTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 类描述：支付宝工具类 时间：2016年1月6日
 * 
 * @author ouyanghao
 */
@SuppressWarnings("unused")
public class AliPayUtils {
	// 商户ID
	public static String PARTNER = "2088221249936330";
	// 商户收款账号
	public static String SELLER = "3021159461_bj@sina.com";
	// 商户私钥，pkcs8格式
	public static String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANffSDtpChBGO/tikQLPqjkNCgYvPSzP14/J+YrTKJETAIz8MsGhp9jV/YfxiTcROXd1iFYDNmQj44OFgZL00chdt2sShgnET5g8RBgd/NuUht8A4Hrf7U9aPRLG3VOK9bVTFiNaT7EFgHpYp24T4KAvia5Z/4WA8EKY5itQPDRzAgMBAAECgYBUm2ieEF8nXb+omUsohOe8kIW7QsyBQbzE2G57oKMnqQDwQPBLv+YfME+79kjHUnLphSE4RO4OdZp4M91DEb0eSBYZkeSQ5AWB02Z66xuVqh5dVUB7TpkIjDyQwZybSOLciYwAp6pZ1YacoPs4Bj3xi1NENDicOR3sMbMh274mgQJBAPvjX5fofwFEs5YhLn2/TSMja0qej2uWJseDBUv/JCQ3RqmyHHaMvmgXkTxXioh4h3lruZo1QV9fI0yGSxpzqeECQQDbZWZkxHakwqMuCWpCCCsFF41t7B9UUxnFKot5lB//h3ZzkO8c5kYnzOnApDxFmfibR3D/NXtExGWbHeHGrTDTAkEA0CVX/SO2r8mhzVGmJWtLxtmh84uLYJA3g26VlfNEXlj/H4N7Wct8HHA9J+v/Ij7XVFLckL2pQ7JObRIcRcOGgQJAd6mRtIpXb7vdRsHJ2+Qamrdi4z5zRAnjN1/iHNjGrxeSaqv1TxzlzNV99R1e5lN5fi/0GK5x4msARB+DQlq6EwJBALDlniSoDQpp3x3Qm3dNoT1FNFehiSeIKKN2X9lgDNBErCqvdy/8dCmQcyShe3ZWMHVlrkOrpQQQ+d6qtET3pgE=";
	// 支付宝公钥
	public static String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	// 标示
	public static final int SDK_PAY_FLAG = 1;
	// 调用者
	private Activity context;
	// 回调
	private AliPayInterface payInterface;

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文
	 */
	public AliPayUtils(Activity context) {
		super();
		this.context = context;
	}

	/**
	 * 支付状态（成功，失败，进行中）
	 * 
	 * @param payInterface
	 *            支付回调接口
	 */
	public void setPayInterface(AliPayInterface payInterface) {
		this.payInterface = payInterface;
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					if (payInterface != null) {
						payInterface.paySuccess();
					}
					Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
				} else if (TextUtils.equals(resultStatus, "6001")) {
					if (payInterface != null) {
						payInterface.payCancel();
					}
					Toast.makeText(context, "用户取消支付", Toast.LENGTH_SHORT).show();
				} else if (TextUtils.equals(resultStatus, "6002")) {
					if (payInterface != null) {
						payInterface.payCancel();
					}
					Toast.makeText(context, "网络连接出错", Toast.LENGTH_SHORT).show();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						if (payInterface != null) {
							payInterface.payProgress();
						}
						Toast.makeText(context, "支付结果确认中", Toast.LENGTH_SHORT).show();

					} else {
						if (payInterface != null) {
							payInterface.payError();
						}
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						// Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT)
						// .show();
					}
				}
				break;
			}
			}
		};
	};

	/**
	 * 支付
	 * 
	 * @param subject
	 *            商品
	 * @param body
	 *            商品详情
	 * @param price
	 *            商品
	 */
	public void GoPay(String subject, String body, String price) {
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(context).setTitle("警告").setMessage("需要配置:商户PID | 商户私钥，pkcs8格式| 商户收款账号")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
						}
					}).show();
			return;
		}
		// 订单
		String orderInfo = getOrderInfo("订单编码:0160323203038978", "赵氏孤儿", price);

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(context);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * 支付
	 * 
	 * @param sign
	 *            签名
	 * @param orderInfo
	 *            订单信息
	 */
	public void GoPay(String sign, String orderInfo) {

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(context);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * 创建订单信息
	 * 
	 * @param subject
	 *            商品
	 * @param body
	 *            商品详情
	 * @param price
	 *            商品
	 */
	public String getOrderInfo(String subject, String body, String price) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + "0160323203038978" + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://112.33.7.55:81/app/payment/alipay/handleResponse.json" + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		// orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

	/**
	 * 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(context);
		String version = payTask.getVersion();
		Toast.makeText(context, version, Toast.LENGTH_SHORT).show();
	}

}
