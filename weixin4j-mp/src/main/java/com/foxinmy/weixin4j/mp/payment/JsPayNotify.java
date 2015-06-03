package com.foxinmy.weixin4j.mp.payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * JSAPI支付回调时的POST信息
 * 
 * @className JsPayNotify
 * @author jy
 * @date 2014年8月19日
 * @since JDK 1.7
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JsPayNotify extends PayBaseInfo {

	private static final long serialVersionUID = -4659030958445259803L;

	/**
	 * 用户的openid
	 */
	@XmlElement(name = "OpenId")
	private String openid;
	/**
	 * 是否关注公众号
	 */
	@XmlElement(name = "IsSubscribe")
	private int issubscribe;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public int getIssubscribe() {
		return issubscribe;
	}

	public void setIssubscribe(int issubscribe) {
		this.issubscribe = issubscribe;
	}

	@JSONField(serialize = false, deserialize = false)
	public boolean getFormatIssubscribe() {
		return issubscribe == 1;
	}

	@Override
	public String toString() {
		return "openid=" + openid + ", issubscribe=" + getFormatIssubscribe()
				+ ", " + super.toString();
	}
}
