package com.foxinmy.weixin4j.payment.mch;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.payment.coupon.RefundCouponInfo;
import com.foxinmy.weixin4j.type.mch.CouponType;
import com.foxinmy.weixin4j.type.mch.RefundChannel;
import com.foxinmy.weixin4j.type.mch.RefundStatus;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.xml.ListsuffixResult;

/**
 * 退款详细
 * 
 * @className RefundDetail
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月21日
 * @since JDK 1.6
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RefundDetail implements Serializable {
	private static final long serialVersionUID = 1402738803019986864L;

	protected RefundDetail() {
		// jaxb required
	}

	/**
	 * 商户退款单号
	 */
	@XmlElement(name = "out_refund_no")
	@JSONField(name = "out_refund_no")
	private String outRefundNo;
	/**
	 * 微信退款单号
	 */
	@XmlElement(name = "refund_id")
	@JSONField(name = "refund_id")
	private String refundId;
	/**
	 * 退款渠道:ORIGINAL—原路退款,默认 BALANCE—退回到余额
	 */
	@XmlElement(name = "refund_channel")
	@JSONField(name = "refund_channel")
	private String refundChannel;
	/**
	 * 退款总金额,单位为分,可以做部分退款
	 */
	@XmlElement(name = "refund_fee")
	@JSONField(name = "refund_fee")
	private int refundFee;
	/**
	 * 退款状态
	 */
	@XmlElement(name = "refund_status")
	@JSONField(name = "refund_status")
	private String refundStatus;
	/**
	 * 退款金额：退款金额=申请退款金额-非充值代金券退款金额，退款金额<=申请退款金额
	 */
	@XmlElement(name = "settlement_refund_fee")
	@JSONField(name = "settlement_refund_fee")
	private Integer settlementRefundFee;
	/**
	 * 代金券退款金额：代金券退款金额<=退款金额，退款金额-代金券或立减优惠退款金额为现金，
	 */
	@XmlElement(name = "coupon_refund_fee")
	@JSONField(name = "coupon_refund_fee")
	private Integer couponRefundFee;
	/**
	 * 代金券或立减优惠使用数量
	 */
	@XmlElement(name = "coupon_refund_count")
	@JSONField(name = "coupon_refund_count")
	private Integer couponRefundCount;
	/**
	 * 代金券类型
	 * 
	 * @see com.foxinmy.weixin4j.type.mch.CouponType
	 */
	@XmlElement(name = "coupon_type")
	@JSONField(name = "coupon_type")
	private String couponType;
	/**
	 * 退款入账账户：取当前退款单的退款入账方 1）退回银行卡： {银行名称}{卡类型}{卡尾号} 2）退回支付用户零钱: 支付用户零钱
	 */
	@XmlElement(name = "refund_recv_accout")
	@JSONField(name = "refund_recv_accout")
	private String refundRecvAccout;
	/**
	 * 退款成功时间，当退款状态为退款成功时有返回
	 */
	@XmlElement(name = "refund_success_time")
	@JSONField(name = "refund_success_time")
	private String refundSuccessTime;
	/**
	 * 退款代金券信息
	 * 
	 * @see com.foxinmy.weixin4j.payment.coupon.RefundCouponInfo
	 */
	@ListsuffixResult
	private List<RefundCouponInfo> couponList;

	public String getOutRefundNo() {
		return outRefundNo;
	}

	public String getRefundId() {
		return refundId;
	}

	public String getRefundChannel() {
		return refundChannel;
	}

	@JSONField(serialize = false)
	public RefundChannel getFormatRefundChannel() {
		return refundChannel != null ? RefundChannel.valueOf(refundChannel
				.toUpperCase()) : null;
	}

	public int getRefundFee() {
		return refundFee;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	@JSONField(serialize = false)
	public RefundStatus getFormatRefundStatus() {
		return refundStatus != null ? RefundStatus.valueOf(refundStatus
				.toUpperCase()) : null;
	}

	public List<RefundCouponInfo> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<RefundCouponInfo> couponList) {
		this.couponList = couponList;
	}

	public Integer getSettlementRefundFee() {
		return settlementRefundFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatSettlementRefundFee() {
		return settlementRefundFee != null ? settlementRefundFee / 100d : 0d;
	}

	public Integer getCouponRefundFee() {
		return couponRefundFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatCouponRefundFee() {
		return couponRefundFee != null ? couponRefundFee / 100d : 0d;
	}

	public Integer getCouponRefundCount() {
		return couponRefundCount;
	}

	public String getCouponType() {
		return couponType;
	}

	@JSONField(serialize = false)
	public CouponType getFormatCouponType() {
		return couponType != null ? CouponType
				.valueOf(couponType.toUpperCase()) : null;
	}

	public String getRefundRecvAccout() {
		return refundRecvAccout;
	}

	public String getRefundSuccessTime() {
		return refundSuccessTime;
	}
	
	@JSONField(serialize = false)
	public Date getFormatRefundSuccessTime() {
		return refundSuccessTime != null ? DateUtil.parse2yyyyMMddHHmmss(refundSuccessTime) : null;
	}

	@Override
	public String toString() {
		return "RefundDetail [outRefundNo=" + outRefundNo + ", refundId="
				+ refundId + ", refundChannel=" + refundChannel
				+ ", refundFee=" + refundFee + ", refundStatus=" + refundStatus
				+ ", settlementRefundFee=" + settlementRefundFee
				+ ", couponRefundFee=" + couponRefundFee
				+ ", couponRefundCount=" + couponRefundCount + ", couponType="
				+ couponType + ", refundRecvAccout=" + refundRecvAccout
				+ ", couponList=" + couponList + "]";
	}
}
