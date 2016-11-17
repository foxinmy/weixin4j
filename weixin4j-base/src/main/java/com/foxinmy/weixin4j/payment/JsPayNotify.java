package com.foxinmy.weixin4j.payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * JSAPI支付回调时的POST信息
 * 
 * @className JsPayNotify
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年8月19日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JsPayNotify extends PayBaseInfo {

	private static final long serialVersionUID = -4659030958445259803L;

	/**
	 * 用户的openid
	 */
	@JSONField(name = "OpenId")
	@XmlElement(name = "OpenId")
	private String openId;
	/**
	 * 是否关注公众号
	 */
	@JSONField(name = "IsSubscribe")
	@XmlElement(name = "IsSubscribe")
	private int isSubscribe;

	public JsPayNotify() {

	}

	public String getOpenId() {
		return openId;
	}

	public int getIsSubscribe() {
		return isSubscribe;
	}

	@JSONField(serialize = false)
	public boolean getFormatIsSubscribe() {
		return isSubscribe == 1;
	}

	@Override
	public String toString() {
		return "openId=" + openId + ", isSubscribe=" + getFormatIsSubscribe()
				+ ", " + super.toString();
	}
}
