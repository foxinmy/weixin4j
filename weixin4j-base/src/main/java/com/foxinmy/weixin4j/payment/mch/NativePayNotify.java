package com.foxinmy.weixin4j.payment.mch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  Native支付回调时POST的信息
 * 
 * @className PayNativeNotify
 * @author jy
 * @date 2014年10月30日
 * @since JDK 1.7
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NativePayNotify extends ApiResult {

	private static final long serialVersionUID = 4515471400239795492L;

	/**
	 * 产品ID 可视为订单ID
	 */
	@XmlElement(name = "product_id")
	private String productId;

	protected NativePayNotify() {
		// jaxb required
	}
	
	public String getProductId() {
		return productId;
	}

	@Override
	public String toString() {
		return "NativePayNotify [productId=" + productId + ", "
				+ super.toString() + "]";
	}
}
