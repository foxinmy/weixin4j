package com.foxinmy.weixin4j.payment.mch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * authcode2openid
 * 
 * @className OpenIdResult
 * @author jy
 * @date 2015年7月23日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OpenIdResult extends ApiResult {

	private static final long serialVersionUID = 902743989722741814L;

	/**
	 * 用户在商户appid下的唯一标识
	 */
	@XmlElement(name = "openid")
	@JSONField(name = "openid")
	private String openId;

	public String getOpenId() {
		return openId;
	}

	@Override
	public String toString() {
		return "OpenIdResult [openId=" + openId + ", "
				+ super.toString() + "]";
	}
}
