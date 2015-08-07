package com.foxinmy.weixin4j.payment.mch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.http.weixin.XmlResult;

/**
 * 发送红包结果
 * 
 * @className RedpacketSendResult
 * @author jy
 * @date 2015年4月1日
 * @since JDK 1.7
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RedpacketSendResult extends XmlResult {

	private static final long serialVersionUID = 5611847899634131711L;
	/**
	 * 微信分配的公众账号
	 */
	@XmlElement(name = "wxappid")
	@JSONField(name = "wxappid")
	private String appId;
	/**
	 * 微信支付分配的商户号
	 */
	@XmlElement(name = "mch_id")
	@JSONField(name = "mch_id")
	private String mchId;
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

	protected RedpacketSendResult() {
		// jaxb required
	}

	public String getAppId() {
		return appId;
	}

	public String getMchId() {
		return mchId;
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

	@Override
	public String toString() {
		return "RedpacketSendResult [appId=" + appId + ", mchId=" + mchId
				+ ", outTradeNo=" + outTradeNo + ", openId=" + openId
				+ ", totalAmount=" + totalAmount + ", " + super.toString()
				+ "]";
	}
}
