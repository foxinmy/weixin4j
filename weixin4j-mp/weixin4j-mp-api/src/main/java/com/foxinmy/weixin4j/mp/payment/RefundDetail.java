package com.foxinmy.weixin4j.mp.payment;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.foxinmy.weixin4j.mp.type.RefundChannel;
import com.foxinmy.weixin4j.mp.type.RefundStatus;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 退款详细
 * 
 * @className RefundDetail
 * @author jy
 * @date 2014年11月2日
 * @since JDK 1.7
 * @see
 */
public class RefundDetail implements Serializable {

	private static final long serialVersionUID = 2828640496307351988L;

	@XStreamAlias("out_refund_no")
	private String outRefundNo; // 商户退款单号
	@XStreamAlias("refund_id")
	private String refundId; // 微信退款单号
	@XStreamAlias("refund_channel")
	private String refundChannel; // 退款渠道 ORIGINAL—原路退款 BALANCE—退回到余额
	@XStreamAlias("refund_fee")
	private int refundFee; // 退款总金额,单位为分,可以做部分退款
	@XStreamAlias("coupon_refund_fee")
	private int couponRefundFee; // 现金券退款金额<=退款金额,退款金额-现金券退款金额为现金
	@XStreamAlias("refund_status")
	private String refundStatus; // 退款状态
	@XStreamAlias("recv_user_id")
	private String recvUserId;// 转账退款接收退款的财付通帐号
	@XStreamAlias("reccv_user_name")
	private String reccvUserName;// 转账退款接收退款的姓名(需与接收退款的财 付通帐号绑定的姓名一致)
	@XStreamAlias("sign_key_index")
	private String signKeyIndex;// 多密钥支持的密钥序号,默认 1
	@XStreamAlias("sign_type")
	private String signType;// 签名类型,取值:MD5、RSA,默认:MD5

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

	public RefundStatus getRefundStatus() {
		// V2
		if ("4,10,".contains(refundStatus + ",")) {
			return RefundStatus.SUCCES;
		} else if ("3,5,6,".contains(refundStatus + ",")) {
			return RefundStatus.FAIL;
		} else if ("8,9,10,".contains(refundStatus + ",")) {
			return RefundStatus.PROCESSING;
		} else if ("1,2,".contains(refundStatus + ",")) {
			return RefundStatus.NOTSURE;
		} else if ("7,".contains(refundStatus + ",")) {
			return RefundStatus.CHANGE;
		} else {
			return RefundStatus.valueOf(refundStatus);
		}
	}

	public String getRecvUserId() {
		return recvUserId;
	}

	public String getReccvUserName() {
		return reccvUserName;
	}

	public String getSignKeyIndex() {
		return signKeyIndex;
	}

	public String getSignType() {
		return signType;
	}

	@Override
	public String toString() {
		return "RefundDetail [outRefundNo=" + outRefundNo + ", refundId="
				+ refundId + ", refundChannel=" + refundChannel
				+ ", refundFee=" + refundFee + ", couponRefundFee="
				+ couponRefundFee + ", refundStatus=" + refundStatus
				+ ", recvUserId=" + recvUserId + ", reccvUserName="
				+ reccvUserName + ", signKeyIndex=" + signKeyIndex
				+ ", signType=" + signType + "]";
	}
}
