package com.foxinmy.weixin4j.payment.mch;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.PayPackage;
import com.foxinmy.weixin4j.type.TradeType;
import com.foxinmy.weixin4j.util.RandomUtil;

/**
 * JS支付订单详情
 * 
 * @className MchPayPackage
 * @author jy
 * @date 2014年10月21日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MchPayPackage extends PayPackage {

	private static final long serialVersionUID = 8944928173669656177L;

	/**
	 * 微信分配的公众账号 必须
	 */
	@XmlElement(name = "appid")
	@JSONField(name = "appid")
	private String appId;
	/**
	 * 微信支付分配的商户号 必须
	 */
	@XmlElement(name = "mch_id")
	@JSONField(name = "mch_id")
	private String mchId;
	/**
	 * 微信支付分配的终端设备号 非必须
	 */
	@XmlElement(name = "device_info")
	@JSONField(name = "device_info")
	private String deviceInfo;
	/**
	 * 随机字符串,不长于 32 位 必须
	 */
	@XmlElement(name = "nonce_str")
	@JSONField(name = "nonce_str")
	private String nonceStr;
	/**
	 * 签名 <font color="red">调用者无需关心</font>
	 */
	private String sign;
	/**
	 * 交易类型JSAPI、NATIVE、APP 必须
	 */
	@XmlElement(name = "trade_type")
	@JSONField(name = "trade_type")
	private String tradeType;
	/**
	 * 用户在商户 appid 下的唯一 标识, trade_type 为 JSAPI 时,此参数必传
	 */
	@XmlElement(name = "openid")
	@JSONField(name = "openid")
	private String openId;
	/**
	 * 只在 trade_type 为 NATIVE 且【模式一】 时需要填写 非必须
	 */
	@XmlElement(name = "product_id")
	@JSONField(name = "product_id")
	private String productId;
	/**
	 * 指定支付方式:no_credit--指定不能使用信用卡支付
	 */
	@XmlElement(name = "limit_pay")
	@JSONField(name = "limit_pay")
	private String limitPay;

	protected MchPayPackage() {
		// jaxb required
	}

	public MchPayPackage(WeixinPayAccount weixinAccount, String openId,
			String body, String outTradeNo, double totalFee, String notifyUrl,
			String createIp, TradeType tradeType) {
		this(weixinAccount, openId, body, outTradeNo, totalFee, notifyUrl,
				createIp, tradeType, null);
	}

	public MchPayPackage(WeixinPayAccount weixinAccount, String openId,
			String body, String outTradeNo, double totalFee, String notifyUrl,
			String createIp, TradeType tradeType, String attach) {
		this(weixinAccount.getId(), weixinAccount.getMchId(), weixinAccount
				.getDeviceInfo(), body, outTradeNo, totalFee, notifyUrl,
				createIp, tradeType, openId, attach, null, null, null, null,
				null);
	}

	public MchPayPackage(String appId, String mchId, String deviceInfo,
			String body, String outTradeNo, double totalFee, String notifyUrl,
			String createIp, TradeType tradeType, String openId, String attach,
			Date timeStart, Date timeExpire, String goodsTag, String productId,
			String limitPay) {
		super(body, outTradeNo, totalFee, notifyUrl, createIp, attach,
				timeStart, timeExpire, goodsTag);
		this.appId = appId;
		this.mchId = mchId;
		this.deviceInfo = deviceInfo;
		this.nonceStr = RandomUtil.generateString(16);
		this.tradeType = tradeType.name();
		this.openId = openId;
		this.productId = productId;
		this.limitPay = limitPay;
	}

	public String getAppId() {
		return appId;
	}

	public String getMchId() {
		return mchId;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTradeType() {
		return tradeType;
	}

	public String getOpenId() {
		return openId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getLimitPay() {
		return limitPay;
	}

	public void setLimitPay(String limitPay) {
		this.limitPay = limitPay;
	}

	@Override
	public String toString() {
		return "MchPayPackage [appId=" + appId + ", mchId=" + mchId
				+ ", deviceInfo=" + deviceInfo + ", nonceStr=" + nonceStr
				+ ", sign=" + sign + ", tradeType=" + tradeType + ", openId="
				+ openId + ", productId=" + productId + ", " + super.toString()
				+ "]";
	}
}
