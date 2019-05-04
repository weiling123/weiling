package com.beijing.beixin.pojo;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA. User: jlmak Date: 11-4-20 Time: 下午3:31 状态
 */
public class ShopRatingAvgVo {
	/**
	 * 与卖家的描述一致
	 */
	private double productDescrSame = 0;
	/**
	 * 卖家服务态度
	 */
	private double sellerServiceAttitude = 0;
	/**
	 * 卖家发货速度
	 */
	private double sellerSendOutSpeed = 0;

	public double getProductDescrSame() {
		return productDescrSame;
	}

	public void setProductDescrSame(double productDescrSame) {
		this.productDescrSame = productDescrSame;
	}

	public double getSellerServiceAttitude() {
		return sellerServiceAttitude;
	}

	public void setSellerServiceAttitude(double sellerServiceAttitude) {
		this.sellerServiceAttitude = sellerServiceAttitude;
	}

	public double getSellerSendOutSpeed() {
		return sellerSendOutSpeed;
	}

	public void setSellerSendOutSpeed(double sellerSendOutSpeed) {
		this.sellerSendOutSpeed = sellerSendOutSpeed;
	}

	/**
	 * 卖家综合评价
	 */
	public double getSellerAverage() {
		return new BigDecimal((productDescrSame + sellerSendOutSpeed + sellerServiceAttitude) / 3.0)
				.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
