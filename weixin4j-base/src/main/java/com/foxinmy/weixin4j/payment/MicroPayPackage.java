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

	public MicroPayPackage(WeixinPayAccount weixinAccount, String authCode,
			String body, String outTradeNo, double totalFee, String createIp) {
		this(weixinAccount.getId(), weixinAccount.getMchId(), weixinAccount
				.getDeviceInfo(), authCode, body, outTradeNo, totalFee,
				createIp, null, null, null, null, null);
	}

	public MicroPayPackage(String appId, String mchId, String deviceInfo,
			String authCode, String body, String outTradeNo, double totalFee,
			String createIp, String attach, Date timeStart, Date timeExpire,
			String goodsTag, String limitPay) {
		super(body, outTradeNo, totalFee, null, createIp, null, timeStart,
				timeExpire, goodsTag);
		this.appId = appId;
		this.mchId = mchId;
		this.deviceInfo = deviceInfo;
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
				+ ", deviceInfo=" + deviceInfo + ", nonceStr=" + nonceStr
				+ ", sign=" + sign + ", authCode=" + authCode + ", "
				+ super.toString() + "]";
	}
}
