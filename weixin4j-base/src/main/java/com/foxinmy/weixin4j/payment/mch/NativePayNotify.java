package com.foxinmy.weixin4j.payment.mch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Native支付回调时POST的信息
 * 
 * @className PayNativeNotify
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年10月30日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NativePayNotify extends OpenIdResult {

	private static final long serialVersionUID = 4515471400239795492L;
	/**
	 * 用户是否关注公众账号,Y- 关注,N-未关注,仅在公众 账号类型支付有效
	 */
	@XmlElement(name = "is_subscribe")
	@JSONField(name = "is_subscribe")
	private String isSubscribe;
	/**
	 * 产品ID 可视为订单ID
	 */
	@XmlElement(name = "product_id")
	@JSONField(name = "product_id")
	private String productId;

	protected NativePayNotify() {
		// jaxb required
	}

	public String getProductId() {
		return productId;
	}

	public String getIsSubscribe() {
		return isSubscribe;
	}

	@Override
	public String toString() {
		return "NativePayNotify [productId=" + productId + ", isSubscribe="
				+ isSubscribe + ", " + super.toString() + "]";
	}
}
