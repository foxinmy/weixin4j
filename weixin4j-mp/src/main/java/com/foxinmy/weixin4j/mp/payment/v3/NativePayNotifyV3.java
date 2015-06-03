package com.foxinmy.weixin4j.mp.payment.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * V3 Native支付回调时POST的信息
 * 
 * @className PayNativeNotifyV3
 * @author jy
 * @date 2014年10月30日
 * @since JDK 1.7
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NativePayNotifyV3 extends ApiResult {

	private static final long serialVersionUID = 4515471400239795492L;

	/**
	 * 产品ID 可视为订单ID
	 */
	@XmlElement(name = "product_id")
	private String productId;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "NativePayNotifyV3 [productId=" + productId + ", "
				+ super.toString() + "]";
	}
}
