package com.foxinmy.weixin4j.mp.payment;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * JSAPI支付回调时的POST信息
 * 
 * @className JsPayNotify
 * @author jy
 * @date 2014年8月19日
 * @since JDK 1.7
 * @see
 */
public class JsPayNotify implements Serializable {

	private static final long serialVersionUID = -4659030958445259803L;

	@XStreamAlias("AppId")
	private String appid; // 公众号ID

	@XStreamAlias("TimeStamp")
	private String timestamp; // 时间戳

	@XStreamAlias("NonceStr")
	private String noncestr; // 随机字符串

	@XStreamAlias("OpenId")
	private String openid; // 用户ID

	@XStreamAlias("AppSignature")
	private String appsignature; // 签名结果

	@XStreamAlias("IsSubscribe")
	private int issubscribe;

	@XStreamAlias("SignMethod")
	private String signmethod; // 签名方式

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getAppsignature() {
		return appsignature;
	}

	public void setAppsignature(String appsignature) {
		this.appsignature = appsignature;
	}

	public int getIssubscribe() {
		return issubscribe;
	}

	public void setIssubscribe(int issubscribe) {
		this.issubscribe = issubscribe;
	}

	public String getSignmethod() {
		return signmethod;
	}

	public void setSignmethod(String signmethod) {
		this.signmethod = signmethod;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[JsPayNotify appid=").append(appid);
		sb.append(", timestamp=").append(timestamp);
		sb.append(", noncestr=").append(noncestr);
		sb.append(", openid=").append(openid);
		sb.append(", appsignature=").append(appsignature);
		sb.append(", issubscribe=").append(issubscribe);
		sb.append(", signmethod=").append(signmethod).append("]");
		return sb.toString();
	}
}
