package com.beijing.beixin.utils.uupay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.unionpay.UPPayAssistEx;

/**
 * 银联支付工具类
 * 
 * @author ouyanghao 时间：2016年1月7日
 * 
 */
public class UPPayUtils {
	/** 手机终端尚未安装支付控件 */
	public static final int PLUGIN_NOT_INSTALLED = -1;
	public static final int PLUGIN_NEED_UPGRADE = 2;
	/** mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境 */
	public final static String mMode = "01";
	/** 发送错误 **/
	public static final int UPPAY_RESULT_ERROR = 0;
	/** 成功 **/
	public static final int UPPAY_RESULT_SUCC = 1;
	/** 取消 **/
	public static final int UPPAY_RESULT_CLOSE = 2;
	/** 失败 **/
	public static final int UPPAY_RESULT_FAIL = 3;

	/**
	 * 调用银联支付(apk)
	 * 
	 * @Title: doStartUnionPayPluginapk @Description:
	 *         TODO(这里用一句话描述这个方法的作用) @param @param activity 上下文 @param @param tn
	 *         TN号 @param @param mMode 调用模式 @return void 返回类型 @throws
	 */
	public static void doStartUnionPayPluginapk(final Activity activity, String tn, String mode) {
		int ret = UPPayAssistEx.startPay(activity, null, null, tn, mode);
		if (ret == PLUGIN_NEED_UPGRADE || ret == PLUGIN_NOT_INSTALLED) {
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setTitle("提示");
			builder.setMessage("首次使用须安装银联支付安全控件，是否安装？");
			builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					// 返回值true——安装正常，false——安装失败
					UPPayAssistEx.installUPPayPlugin(activity);
				}
			});

			builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.show();
		}

	}

	/**
	 * 调用银联支付(jar)
	 * 
	 * @Title: doStartUnionPayPluginjar @Description:
	 *         TODO(这里用一句话描述这个方法的作用) @param @param activity 上下文 @param @param tn
	 *         TN号 @param @param mMode2 调用模式 @return void 返回类型 @throws
	 */
	// public static void doStartUnionPayPluginjar(Activity activity, String tn,
	// String mode) {
	// UPPayAssistEx.startPayByJAR(activity, PayActivity.class, null, null,
	// tn, mode);
	// }

	/**
	 * 获取银联支付结果
	 * 
	 * @Title: getUPPayResltDatas @Description: TODO(这里用一句话描述这个方法的作用) @param
	 *         context 上下文 @param data 返回值 @param @return int 返回类型 @throws
	 */
	public static int getUPPayResltDatas(Context context, Intent data) {
		if (data == null) {
			return UPPAY_RESULT_ERROR;
		}
		/**
		 * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
		 */
		String str = data.getExtras().getString("pay_result");
		if (str.equalsIgnoreCase("success")) {
			return UPPAY_RESULT_SUCC;
		} else if (str.equalsIgnoreCase("fail")) {
			Toast.makeText(context, "支付失败，请重新支付！", Toast.LENGTH_SHORT).show();
			return UPPAY_RESULT_FAIL;
		} else if (str.equalsIgnoreCase("cancel")) {
			// Toast.makeText(context, "您已取消支付！", Toast.LENGTH_SHORT).show();
			return UPPAY_RESULT_CLOSE;
		}
		return UPPAY_RESULT_ERROR;
	}

	/**
	 * 获取银联TN号
	 * 
	 * @param activity
	 *            上下文
	 * @param myhandler
	 *            Handler
	 * @param obj
	 *            类型参数
	 */
	public static void getUPPayTnNumber(final Activity activity, final Handler myhandler, String url, Object... obj) {
		// 服务端接口
	}

	/**
	 * 返回错误信息
	 * 
	 * @param str
	 *            错误信息
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Message getMsg(String str) {
		Bundle bundle = new Bundle();
		bundle.putString("message", str);
		Message msg = new Message();
		msg.setData(bundle);
		return msg;
	}
}
