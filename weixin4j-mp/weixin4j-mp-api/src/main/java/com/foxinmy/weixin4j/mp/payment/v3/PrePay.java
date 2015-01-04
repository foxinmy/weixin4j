package com.foxinmy.weixin4j.mp.payment.v3;

import com.foxinmy.weixin4j.mp.type.TradeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * V3预订单信息
 * 
 * @className PrePay
 * @author jy
 * @date 2014年10月21日
 * @since JDK 1.7
 * @see
 */
@XStreamAlias("xml")
public class PrePay extends ApiResult {

	private static final long serialVersionUID = -8430005768959715444L;

	@XStreamAlias("trade_type")
	private TradeType tradeType;// 交易类型JSAPI、NATIVE、APP 非空
	@XStreamAlias("prepay_id")
	private String prepayId;// 微信生成的预支付 ID,用于后续接口调用中使用二维码链接 非空
	@XStreamAlias("code_url")
	private String codeUrl;// trade_type 为 NATIVE 是有 返回,此参数可直接生成二 维码展示出来进行扫码支付
							// 可能为空

	public PrePay() {

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
