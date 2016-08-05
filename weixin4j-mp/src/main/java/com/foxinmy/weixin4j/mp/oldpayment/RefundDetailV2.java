package com.foxinmy.weixin4j.mp.oldpayment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.mch.RefundChannel;
import com.foxinmy.weixin4j.type.mch.RefundStatus;

/**
 * V2退款详细
 * 
 * @className RefundDetailV2
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月6日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RefundDetailV2 extends ApiResultV2 {

	private static final long serialVersionUID = -3687863914168618620L;

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
	 * 退款渠道 0:退到财付通、1:退到银行;
	 */
	@XmlElement(name = "refund_channel")
	@JSONField(name = "refund_channel")
	private int refundChannel;
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
	private int refundStatus;
	/**
	 * 转账退款接收退款的财付通帐号
	 */
	@XmlElement(name = "recv_user_id")
	@JSONField(name = "recv_user_id")
	private String recvUserId;
	/**
	 * 转账退款接收退款的姓名(需与接收退款的财付通帐号绑定的姓名一致)
	 */
	@XmlElement(name = "reccv_user_name")
	@JSONField(name = "reccv_user_name")
	private String reccvUserName;

	protected RefundDetailV2() {
		// jaxb required
	}

	public String getOutRefundNo() {
		return outRefundNo;
	}

	public String getRefundId() {
		return refundId;
	}

	public int getRefundChannel() {
		return refundChannel;
	}

	@JSONField(serialize = false)
	public RefundChannel getFormatRefundChannel() {
		if (refundChannel == 0) {
			return RefundChannel.TENPAY;
		} else if (refundChannel == 1) {
			return RefundChannel.BANK;
		} else {
			return null;
		}
	}

	public int getRefundFee() {
		return refundFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatRefundFee() {
		return refundFee / 100d;
	}

	public int getRefundStatus() {
		return refundStatus;
	}

	@JSONField(serialize = false)
	public RefundStatus getFormatRefundStatus() {
		String refundStatus_ = String.format(",%d,", refundStatus);
		if (",4,10,".contains(refundStatus_)) {
			return RefundStatus.SUCCESS;
		} else if (",3,5,6,".contains(refundStatus_)) {
			return RefundStatus.FAIL;
		} else if (",8,9,11,".contains(refundStatus_)) {
			return RefundStatus.PROCESSING;
		} else if (",1,2,".contains(refundStatus_)) {
			return RefundStatus.NOTSURE;
		} else if (",7,".contains(refundStatus_)) {
			return RefundStatus.CHANGE;
		} else {
			return null;
		}
	}

	public String getRecvUserId() {
		return recvUserId;
	}

	public String getReccvUserName() {
		return reccvUserName;
	}

	@Override
	public String toString() {
		return "outRefundNo=" + outRefundNo + ", refundId=" + refundId
				+ ", refundChannel=" + refundChannel + ", refundFee="
				+ refundFee + ", refundStatus=" + refundStatus
				+ ", recvUserId=" + recvUserId + ", reccvUserName="
				+ reccvUserName + ", refundChannel=" + getFormatRefundChannel()
				+ ", refundFee=" + getFormatRefundFee() + ", refundStatus="
				+ getFormatRefundStatus();
	}
}
