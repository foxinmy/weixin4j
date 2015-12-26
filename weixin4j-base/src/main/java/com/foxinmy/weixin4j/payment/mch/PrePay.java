package com.foxinmy.weixin4j.payment.mch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.foxinmy.weixin4j.type.TradeType;

/**
 * V3预订单信息
 * 
 * @className PrePay
 * @author jy
 * @date 2014年10月21日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PrePay extends ApiResult {

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
	 * trade_type 为 NATIVE 是有 返回,此参数可直接生成二 维码展示出来进行扫码支付 可能为空
	 */
	@XmlElement(name = "code_url")
	private String codeUrl;

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

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	@Override
	public String toString() {
		return "PrePay [tradeType=" + tradeType + ", prepayId=" + prepayId
				+ ", codeUrl=" + codeUrl + ", " + super.toString() + "]";
	}
}
