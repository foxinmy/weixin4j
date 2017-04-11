package com.foxinmy.weixin4j.payment.mch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.foxinmy.weixin4j.type.TradeType;

/**
 * V3预订单信息
 * 
 * @className PrePay
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年10月21日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PrePay extends MerchantResult {

	private static final long serialVersionUID = -8430005768959715444L;

	/**
	 * 调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP，
	 * 
	 * @see com.foxinmy.weixin4j.mp.type.TradeType
	 */
	@XmlElement(name = "trade_type")
	private TradeType tradeType;
	/**
	 * 微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
	 */
	@XmlElement(name = "prepay_id")
	private String prepayId;
	/**
	 * 对于trade_type 为 NATIVE 或者 MWEB 是有 返回</br> NATVIE支付：可直接生成二维码展示出来进行扫码支付可能为空</br>
	 * MWEB支付：可直接作为跳转支付的URL
	 */
	@XmlElements({ @XmlElement(name = "code_url"),
			@XmlElement(name = "mweb_url") })
	private String payUrl;

	protected PrePay() {
		// jaxb required
	}

	public PrePay(String returnCode, String returnMsg) {
		super(returnCode, returnMsg);
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

	@Override
	public String toString() {
		return "PrePay [tradeType=" + tradeType + ", prepayId=" + prepayId
				+ ", payUrl=" + payUrl + ", " + super.toString() + "]";
	}
}
