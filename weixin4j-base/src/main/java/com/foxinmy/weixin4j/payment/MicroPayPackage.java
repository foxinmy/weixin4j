package com.foxinmy.weixin4j.payment;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.util.RandomUtil;

/**
 * 刷卡支付订单详情
 * 
 * @className MicroPayPackage
 * @author jy
 * @date 2014年11月17日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MicroPayPackage extends PayPackage {

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
	 * 随机字符串,不长于 32 位 必须
	 */
	@XmlElement(name = "nonce_str")
	@JSONField(name = "nonce_str")
	private String nonceStr;
	/**
	 * 签名 <font color="red">调用者不必关注</font>
	 */
	private String sign;
	/**
	 * 扫码支付授权码 ,设备读取用户微信中的条码或者二维码信息
	 */
	@XmlElement(name = "auth_code")
	@JSONField(name = "auth_code")
	private String authCode;
	/**
	 * 指定支付方式:no_credit--指定不能使用信用卡支付
	 */
	@XmlElement(name = "limit_pay")
	@JSONField(name = "limit_pay")
	private String limitPay;

	protected MicroPayPackage() {
		// jaxb required
	}

	/**
	 * 
	 * @param weixinAccount
	 *            商户信息
	 * @param authCode
	 *            授权码
	 * @param body
	 *            支付详情
	 * @param totalFee
	 *            支付金额(单位元) 必填
	 * @param createIp
	 *            发起支付的IP地址
	 */
	public MicroPayPackage(WeixinPayAccount weixinAccount, String authCode,
			String body, String outTradeNo, double totalFee, String createIp) {
		this(weixinAccount.getId(), weixinAccount.getMchId(), weixinAccount
				.getDeviceInfo(), weixinAccount.getSubId(), weixinAccount
				.getSubMchId(), authCode, body, outTradeNo, totalFee, createIp,
				null, null, null, null, null);
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
	 * @param authCode
	 *            授权码 必填
	 * @param body
	 *            支付详情 必填
	 * @param outTradeNo
	 *            商户侧订单号 必填
	 * @param totalFee
	 *            支付金额(单位元) 必填
	 * @param createIp
	 *            发起支付的IP地址 必填
	 * @param attach
	 *            支付时附加信息 非必填
	 * @param timeStart
	 *            订单生成时间 非必填
	 * @param timeExpire
	 *            订单失效时间 非必填
	 * @param goodsTag
	 *            商品标记 非必填
	 * @param limitPay
	 *            指定支付方式 非必填
	 */
	public MicroPayPackage(String appId, String mchId, String deviceInfo,
			String subId, String subMchId, String authCode, String body,
			String outTradeNo, double totalFee, String createIp, String attach,
			Date timeStart, Date timeExpire, String goodsTag, String limitPay) {
		super(body, outTradeNo, totalFee, null, createIp, null, timeStart,
				timeExpire, goodsTag);
		this.appId = appId;
		this.mchId = mchId;
		this.deviceInfo = deviceInfo;
		this.subId = subId;
		this.subMchId = subMchId;
		this.nonceStr = RandomUtil.generateString(16);
		this.authCode = authCode;
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

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getLimitPay() {
		return limitPay;
	}

	public void setLimitPay(String limitPay) {
		this.limitPay = limitPay;
	}

	@Override
	public String toString() {
		return "MicroPayPackage [appId=" + appId + ", mchId=" + mchId
				+ ", deviceInfo=" + deviceInfo + ", subId=" + subId
				+ ", subMchId=" + subMchId + ", nonceStr=" + nonceStr
				+ ", sign=" + sign + ", authCode=" + authCode + ", limitPay="
				+ limitPay + ", " + super.toString() + "]";
	}
}
