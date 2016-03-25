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
 * 支付订单详情
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
	 * 微信分配的子商户公众账号ID 非必须
	 */
	@XmlElement(name = "sub_id")
	@JSONField(name = "sub_id")
	private String subId;
	/**
	 * 微信支付分配的子商户号 非必须
	 */
	@XmlElement(name = "sub_mch_id")
	@JSONField(name = "sub_mch_id")
	private String subMchId;
	/**
	 * 用户子标识 非必须
	 */
	@XmlElement(name = "sub_openid")
	@JSONField(name = "sub_openid")
	private String subOpenId;
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

	/**
	 * 
	 * @param weixinAccount
	 *            商户信息 必填
	 * @param openId
	 *            用户唯一标识 JSAPI支付必填
	 * @param body
	 *            支付详情 必填
	 * @param outTradeNo
	 *            商户侧订单号 必填
	 * @param totalFee
	 *            支付金额(单位元) 必填
	 * @param notifyUrl
	 *            支付回调URL
	 * @param createIp
	 *            发起支付的IP地址
	 * @param tradeType
	 *            支付类型
	 */
	public MchPayPackage(WeixinPayAccount weixinAccount, String openId,
			String body, String outTradeNo, double totalFee, String notifyUrl,
			String createIp, TradeType tradeType) {
		this(weixinAccount, openId, body, outTradeNo, totalFee, notifyUrl,
				createIp, tradeType, null);
	}

	/**
	 * 
	 * @param weixinAccount
	 *            商户信息 必填
	 * @param openId
	 *            用户唯一标识 JSAPI支付必填
	 * @param body
	 *            支付详情 必填
	 * @param outTradeNo
	 *            商户侧订单号 必填
	 * @param totalFee
	 *            支付金额(单位元) 必填
	 * @param notifyUrl
	 *            支付回调URL
	 * @param createIp
	 *            发起支付的IP地址
	 * @param tradeType
	 *            支付类型
	 * @param attach
	 *            支付时附加信息
	 */
	public MchPayPackage(WeixinPayAccount weixinAccount, String openId,
			String body, String outTradeNo, double totalFee, String notifyUrl,
			String createIp, TradeType tradeType, String attach) {
		this(weixinAccount.getId(), weixinAccount.getMchId(), weixinAccount
				.getDeviceInfo(), weixinAccount.getSubId(), weixinAccount
				.getSubMchId(), null, body, outTradeNo, totalFee, notifyUrl,
				createIp, tradeType, openId, attach, null, null, null, null,
				null);
	}

	/**
	 * 完整参数
	 * 
	 * @param appId
	 *            公众号唯一标识 必填
	 * @param mchId
	 *            微信支付商户号 必填
	 * @param deviceInfo
	 *            微信支付设备号 非必填
	 * @param subId
	 *            子商户唯一标识 非必填
	 * @param subMchId
	 *            子商户商户号 非必填
	 * @param subOpenId
	 *            用户在子商户appid下的唯一标识 非必填
	 *            openid和sub_openid可以选传其中之一，如果选择传sub_openid ,则必须传sub_appid
	 * @param body
	 *            支付详情 必填
	 * @param outTradeNo
	 *            商户侧订单号 必填
	 * @param totalFee
	 *            支付金额(单位元) 必填
	 * @param notifyUrl
	 *            支付回调URL 必填
	 * @param createIp
	 *            发起支付的IP地址 必填
	 * @param tradeType
	 *            支付类型 必填
	 * @param openId
	 *            用户唯一标识 JSAPI支付必填
	 * @param attach
	 *            支付时附加信息 非必填
	 * @param timeStart
	 *            订单生成时间 非必填
	 * @param timeExpire
	 *            订单失效时间 非必填
	 * @param goodsTag
	 *            商品标记 非必填
	 * @param productId
	 *            商品ID native支付必填
	 * @param limitPay
	 *            指定支付方式 非必填
	 */
	public MchPayPackage(String appId, String mchId, String deviceInfo,
			String subId, String subMchId, String subOpenId, String body,
			String outTradeNo, double totalFee, String notifyUrl,
			String createIp, TradeType tradeType, String openId, String attach,
			Date timeStart, Date timeExpire, String goodsTag, String productId,
			String limitPay) {
		super(body, outTradeNo, totalFee, notifyUrl, createIp, attach,
				timeStart, timeExpire, goodsTag);
		this.appId = appId;
		this.mchId = mchId;
		this.deviceInfo = deviceInfo;
		this.subId = subId;
		this.subMchId = subMchId;
		this.subOpenId = subOpenId;
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
				+ ", deviceInfo=" + deviceInfo + ", subId=" + subId
				+ ", subMchId=" + subMchId + ", subOpenId=" + subOpenId
				+ ", nonceStr=" + nonceStr + ", sign=" + sign + ", tradeType="
				+ tradeType + ", openId=" + openId + ", productId=" + productId
				+ ", limitPay=" + limitPay + ", " + super.toString() + "]";
	}
}
