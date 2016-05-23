package com.foxinmy.weixin4j.payment.mch;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.util.DateUtil;

/**
 * 发送红包结果
 * 
 * @className RedpacketSendResult
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年4月1日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RedpacketSendResult extends MerchantResult {

	private static final long serialVersionUID = 5611847899634131711L;
	/**
	 * 商户订单号（每个订单号必须唯一） 组成： mch_id+yyyymmdd+10位一天内不能重复的数字。
	 */
	@XmlElement(name = "mch_billno")
	@JSONField(name = "mch_billno")
	private String outTradeNo;
	/**
	 * 接收红包的用户的openid
	 */
	@XmlElement(name = "re_openid")
	@JSONField(name = "re_openid")
	private String openId;
	/**
	 * 付款金额 单位为分
	 */
	@XmlElement(name = "total_amount")
	@JSONField(name = "total_amount")
	private int totalAmount;
	/**
	 * 发放成功时间
	 */
	@XmlElement(name = "send_time")
	@JSONField(name = "send_time")
	private String sendTime;
	/**
	 * 微信单号
	 */
	@XmlElement(name = "send_listid")
	@JSONField(name = "send_listid")
	private String sendListid;

	protected RedpacketSendResult() {
		// jaxb required
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public String getOpenId() {
		return openId;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatTotalAmount() {
		return totalAmount / 100d;
	}

	public String getSendTime() {
		return sendTime;
	}

	@JSONField(serialize = false)
	public Date getFormatSendTime() {
		return DateUtil.parse2yyyyMMddHHmmss(sendTime);
	}

	public String getSendListid() {
		return sendListid;
	}

	@Override
	public String toString() {
		return "RedpacketSendResult [outTradeNo=" + outTradeNo + ", openId="
				+ openId + ", totalAmount=" + totalAmount + ", "
				+ super.toString() + "]";
	}
}