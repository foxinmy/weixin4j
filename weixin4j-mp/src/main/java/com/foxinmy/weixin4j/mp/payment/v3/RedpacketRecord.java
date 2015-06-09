package com.foxinmy.weixin4j.mp.payment.v3;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.http.weixin.XmlResult;
import com.foxinmy.weixin4j.mp.type.RedpacketSendType;
import com.foxinmy.weixin4j.mp.type.RedpacketStatus;
import com.foxinmy.weixin4j.mp.type.RedpacketType;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 红包记录
 * 
 * @className RedpacketRecord
 * @author jy
 * @date 2015年6月4日
 * @since JDK 1.7
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RedpacketRecord extends XmlResult {

	private static final long serialVersionUID = 929959747323918458L;

	/**
	 * 商户订单号（每个订单号必须唯一） 组成： mch_id+yyyymmdd+10位一天内不能重复的数字。
	 */
	@XmlElement(name = "mch_billno")
	@JSONField(name = "mch_billno")
	private String outTradeNo;
	/**
	 * 微信支付分配的商户号
	 */
	@XmlElement(name = "mch_id")
	@JSONField(name = "mch_id")
	private String mchId;

	/**
	 * 红包单号
	 */
	@XmlElement(name = "detail_id")
	@JSONField(name = "detail_id")
	private String repacketId;
	/**
	 * 红包状态
	 */
	@XmlElement(name = "status")
	private RedpacketStatus status;
	/**
	 * 发放类型
	 */
	@XmlElement(name = "send_type")
	@JSONField(name = "send_type")
	private RedpacketSendType sendType;
	/**
	 * 红包类型
	 */
	@XmlElement(name = "hb_type")
	@JSONField(name = "hb_type")
	private RedpacketType type;
	/**
	 * 红包个数
	 */
	@XmlElement(name = "total_num")
	@JSONField(name = "total_num")
	private int totalNum;
	/**
	 * 红包总金额（单位分）
	 */
	@XmlElement(name = "total_amount")
	@JSONField(name = "total_amount")
	private int totalAmount;
	/**
	 * 发送失败原因
	 */
	@XmlElement(name = "reason")
	private String reason;
	/**
	 * 发放时间
	 */
	@XmlElement(name = "send_time")
	@JSONField(name = "send_time")
	private String sendTime;
	/**
	 * 红包退款时间
	 */
	@XmlElement(name = "refund_time")
	@JSONField(name = "refund_time")
	private String refundTime;
	/**
	 * 红包退款金额
	 */
	@XmlElement(name = "refund_amount")
	@JSONField(name = "refund_amount")
	private Integer refundAmount;
	/**
	 * 祝福语
	 */
	@XmlElement(name = "wishing")
	private String wishing;
	/**
	 * 活动描述
	 */
	@XmlElement(name = "remark")
	private String remark;
	/**
	 * 活动名称
	 */
	@XmlElement(name = "act_name")
	@JSONField(name = "act_name")
	private String actName;
	/**
	 * 裂变红包领取列表
	 */
	@XmlElement(name = "hbinfo")
	@XmlElementWrapper(name = "hblist")
	@JSONField(name = "hblist")
	private List<RedpacketReceiver> receivers;

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public String getMchId() {
		return mchId;
	}

	public String getRepacketId() {
		return repacketId;
	}

	public RedpacketStatus getStatus() {
		return status;
	}

	public RedpacketSendType getSendType() {
		return sendType;
	}

	public RedpacketType getType() {
		return type;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false, deserialize = false)
	public double getFormatTotalAmount() {
		return totalAmount / 100d;
	}

	public String getReason() {
		return reason;
	}

	public String getSendTime() {
		return sendTime;
	}

	@JSONField(serialize = false, deserialize = false)
	public Date getFormatSendTime() {
		return DateUtil.parse2yyyyMMddHHmmss(sendTime);
	}

	public String getRefundTime() {
		return refundTime;
	}

	@JSONField(serialize = false, deserialize = false)
	public Date getFormatRefundTime() {
		if (StringUtil.isNotBlank(refundTime)) {
			return DateUtil.parse2yyyyMMddHHmmss(refundTime);
		}
		return null;
	}

	public Integer getRefundAmount() {
		return refundAmount;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false, deserialize = false)
	public double getFormatRefundAmount() {
		if (refundAmount != null) {
			return refundAmount.intValue() / 100d;
		}
		return 0d;
	}

	public String getWishing() {
		return wishing;
	}

	public String getRemark() {
		return remark;
	}

	public String getActName() {
		return actName;
	}

	public List<RedpacketReceiver> getReceivers() {
		return receivers;
	}

	@XmlRootElement
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class RedpacketReceiver {
		/**
		 * 领取红包的Openid
		 */
		@XmlElement(name = "openid")
		private String openid;
		/**
		 * 领取状态
		 */
		@XmlElement(name = "status")
		private RedpacketStatus status;
		/**
		 * 领取金额
		 */
		private int amount;
		/**
		 * 领取时间
		 */
		@XmlElement(name = "rcv_time")
		@JSONField(name = "rcv_time")
		private String receiveTime;

		public String getOpenid() {
			return openid;
		}

		public RedpacketStatus getStatus() {
			return status;
		}

		public int getAmount() {
			return amount;
		}

		public String getReceiveTime() {
			return receiveTime;
		}

		/**
		 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
		 * 
		 * @return 元单位
		 */
		@JSONField(serialize = false, deserialize = false)
		public double getFormatAmount() {
			return amount / 100d;
		}

		@JSONField(serialize = false, deserialize = false)
		public Date getFormatReceiveTime() {
			return DateUtil.parse2yyyyMMddHHmmss(receiveTime);
		}

		@Override
		public String toString() {
			return "RedpacketReceiver [openid=" + openid + ", status=" + status
					+ ", amount=" + getFormatAmount() + ", receiveTime="
					+ receiveTime + "]";
		}
	}

	@Override
	public String toString() {
		return "RedpacketRecord [outTradeNo=" + outTradeNo + ", mchId=" + mchId
				+ ", repacketId=" + repacketId + ", status=" + status
				+ ", sendType=" + sendType + ", type=" + type + ", totalNum="
				+ totalNum + ", totalAmount=" + getFormatTotalAmount()
				+ ", reason=" + reason + ", sendTime=" + sendTime
				+ ", refundTime=" + refundTime + ", refundAmount="
				+ getFormatRefundAmount() + ", wishing=" + wishing
				+ ", remark=" + remark + ", actName=" + actName
				+ ", receivers=" + receivers + "]";
	}
}
