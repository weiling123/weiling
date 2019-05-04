package com.beijing.beixin.utils.alipay;

/**
 * 类描述：支付回调接口
 * 
 * @author ouyanghao 时间：2015年12月8日 版本号：v1.1
 * 
 */
public interface AliPayInterface {
	/**
	 * 支付成功的回调 @Title: paySuccess @Description: TODO(这里用一句话描述这个方法的作用) @param
	 * 设定文件 @return void 返回类型 @throws
	 */
	void paySuccess();

	/**
	 * 支付失败的回调 @Title: payError @Description: TODO(这里用一句话描述这个方法的作用) @param
	 * 设定文件 @return void 返回类型 @throws
	 */
	void payError();

	/**
	 * 支付过程中的回调 @Title: payProgress @Description: TODO(这里用一句话描述这个方法的作用) @param
	 * 设定文件 @return void 返回类型 @throws
	 */
	void payProgress();

	/**
	 * 支付取消中的回调 @Title: payCancel @Description: TODO(这里用一句话描述这个方法的作用) @param
	 * 设定文件 @return void 返回类型 @throws
	 */
	void payCancel();
}