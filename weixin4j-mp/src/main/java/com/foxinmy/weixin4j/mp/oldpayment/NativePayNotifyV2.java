package com.foxinmy.weixin4j.mp.oldpayment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.payment.JsPayNotify;

/**
 * V2 Native支付回调时POST的信息
 * 
 * @className PayNativeNotifyV2
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年10月28日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NativePayNotifyV2 extends JsPayNotify {

	private static final long serialVersionUID = 1868431159301749988L;

	/**
	 * 产品ID 可视为订单ID
	 */
	@JSONField(name = "ProductId")
	@XmlElement(name = "ProductId")
	private String productId;

	private NativePayNotifyV2() {
		// jaxb required
	}

	public String getProductId() {
		return productId;
	}

	@Override
	public String toString() {
		return "NativePayNotifyV2 [productId=" + productId + ", "
				+ super.toString() + "]";
	}
}