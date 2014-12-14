package com.foxinmy.weixin4j.mp.payment;

import org.apache.commons.lang3.StringUtils;

import com.foxinmy.weixin4j.mp.type.RefundChannel;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 退款申请结果
 * 
 * @className RefundResult
 * @author jy
 * @date 2014年11月6日
 * @since JDK 1.7
 * @see
 */
@XStreamAlias("xml")
public class RefundResult extends ApiResult {

	private static final long serialVersionUID = -3687863914168618620L;

	// 微信订单号
	@XStreamAlias("transaction_id")
	private String transactionId;
	// 商户系统内部的订单号
	@XStreamAlias("out_trade_no")
	private String outTradeNo;
	// 商户退款单号
	@XStreamAlias("out_refund_no")
	private String outRefundNo;
	// 微信退款单号
	@XStreamAlias("refund_id")
	private String refundId;
	// 退款渠道 ORIGINAL—原路退款,默认 BALANCE—退回到余额
	@XStreamAlias("refund_channel")
	private String refundChannel;
	// 退款总金额,单位为元,可以做部分退款
	@XStreamAlias("refund_fee")
	private int refundFee;
	// 现金券退款金额<=退款金 额,退款金额-现金券退款金 额为现金
	@XStreamAlias("coupon_refund_fee")
	private int couponRefundFee;
	@XStreamAlias("recv_user_id")
	private String recvUserId;// 转账退款接收退款的财付通帐号
	@XStreamAlias("reccv_user_name")
	private String reccvUserName;// 转账退款接收退款的姓名(需与接收退款的财 付通帐号绑定的姓名一致)
	@XStreamAlias("sign_key_index")
	private String signKeyIndex;// 多密钥支持的密钥序号,默认 1
	@XStreamAlias("sign_type")
	private String signType;// 签名类型,取值:MD5、RSA,默认:MD5

	public String getTransactionId() {
		return transactionId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public String getOutRefundNo() {
		return outRefundNo;
	}

	public String getRefundId() {
		return refundId;
	}

	public String getRefundChannel() {
		if (StringUtils.isBlank(refundChannel)) {
			return RefundChannel.BALANCE.name();
		}
		// V2
		if (refundChannel.equals("0")) {
			return RefundChannel.TENPAY.name();
		} else if (refundChannel.equals("1")) {
			return RefundChannel.BALANCE.name();
		}
		return refundChannel;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	public double getRefundFee() {
		return refundFee / 100d;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	public double getCouponRefundFee() {
		return couponRefundFee / 100d;
	}

	public String getSignKeyIndex() {
		return signKeyIndex;
	}

	public String getSignType() {
		return signType;
	}

	@Override
	public String toString() {
		return "RefundResult [transactionId=" + transactionId + ", outTradeNo="
				+ outTradeNo + ", outRefundNo=" + outRefundNo + ", refundId="
				+ refundId + ", refundChannel=" + refundChannel
				+ ", refundFee=" + refundFee + ", couponRefundFee="
				+ couponRefundFee + ", recvUserId=" + recvUserId
				+ ", reccvUserName=" + reccvUserName + ", signKeyIndex="
				+ signKeyIndex + ", signType=" + signType + ", "
				+ super.toString() + "]";
	}
}
