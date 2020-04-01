package com.foxinmy.weixin4j.pay.payment.coupon;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.pay.payment.mch.MerchantResult;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 代金券发放结果
 * 
 * @className CouponResult
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月25日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CouponResult extends MerchantResult {

	private static final long serialVersionUID = -1996967923720149124L;

	/**
	 * 代金券批次id
	 */
	@XmlElement(name = "coupon_stock_id")
	@JSONField(name = "coupon_stock_id")
	private String couponStockId;
	/**
	 * 返回记录数
	 */
	@XmlElement(name = "resp_count")
	@JSONField(name = "resp_count")
	private int responseCount;
	/**
	 * 成功记录数
	 */
	@XmlElement(name = "success_count")
	@JSONField(name = "success_count")
	private int successCount;
	/**
	 * 失败记录数
	 */
	@XmlElement(name = "failed_count")
	@JSONField(name = "failed_count")
	private int failedCount;
	/**
	 * 用户在商户appid下的唯一标识
	 */
	@XmlElement(name = "openid")
	@JSONField(name = "openid")
	private String openId;
	/**
	 * 返回码 SUCCESS或者FAILED
	 */
	@XmlElement(name = "ret_code")
	@JSONField(name = "ret_code")
	private String retCode;
	/**
	 * 代金券id
	 */
	@XmlElement(name = "coupon_id")
	@JSONField(name = "coupon_id")
	private String couponId;
	/**
	 * 失败描述信息，例如：“用户已达领用上限”
	 */
	@XmlElement(name = "ret_msg")
	@JSONField(name = "ret_msg")
	private String retMsg;

	public CouponResult() {

	}

	public String getCouponStockId() {
		return couponStockId;
	}

	public void setCouponStockId(String couponStockId) {
		this.couponStockId = couponStockId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getResponseCount() {
		return responseCount;
	}

	public void setResponseCount(int responseCount) {
		this.responseCount = responseCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}

	public String getOpenId() {
		return openId;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
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
