package com.foxinmy.weixin4j.mp.payment.coupon;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.mp.payment.v3.ApiResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 代金券发放结果
 * 
 * @className CouponResult
 * @author jy
 * @date 2015年3月25日
 * @since JDK 1.7
 * @see
 */
public class CouponResult extends ApiResult {

	private static final long serialVersionUID = -1996967923720149124L;

	/**
	 * 代金券批次id
	 */
	@XStreamAlias("coupon_stock_id")
	@JSONField(name = "coupon_stock_id")
	private String couponStockId;
	/**
	 * 返回记录数
	 */
	@XStreamAlias("resp_count")
	@JSONField(name = "resp_count")
	private int responseCount;
	/**
	 * 成功记录数
	 */
	@XStreamAlias("success_count")
	@JSONField(name = "success_count")
	private int successCount;
	/**
	 * 失败记录数
	 */
	@XStreamAlias("failed_count")
	@JSONField(name = "failed_count")
	private int failedCount;
	/**
	 * 用户在商户appid下的唯一标识
	 */
	@XStreamAlias("openid")
	@JSONField(name = "openid")
	private String openId;
	/**
	 * 返回码 SUCCESS或者FAILED
	 */
	@XStreamAlias("ret_code")
	@JSONField(name = "ret_code")
	private String retCode;
	/**
	 * 代金券id
	 */
	@XStreamAlias("coupon_id")
	@JSONField(name = "coupon_id")
	private String couponId;
	/**
	 * 失败描述信息，例如：“用户已达领用上限”
	 */
	@XStreamAlias("ret_msg")
	@JSONField(name = "ret_msg")
	private String retMsg;
	
	public String getCouponStockId() {
		return couponStockId;
	}


	public int getResponseCount() {
		return responseCount;
	}


	public int getSuccessCount() {
		return successCount;
	}


	public int getFailedCount() {
		return failedCount;
	}


	public String getOpenId() {
		return openId;
	}


	public String getRetCode() {
		return retCode;
	}


	public String getCouponId() {
		return couponId;
	}


	public String getRetMsg() {
		return retMsg;
	}


	@Override
	public String toString() {
		return "CouponResult [couponStockId=" + couponStockId
				+ ", responseCount=" + responseCount + ", successCount="
				+ successCount + ", failedCount=" + failedCount + ", openId="
				+ openId + ", retCode=" + retCode + ", couponId=" + couponId
				+ ", retMsg=" + retMsg + "]";
	}
}
