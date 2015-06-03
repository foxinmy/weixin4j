package com.foxinmy.weixin4j.mp.payment.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.foxinmy.weixin4j.mp.payment.JsPayNotify;

/**
 * V2 Native支付回调时POST的信息
 * 
 * @className PayNativeNotifyV2
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.7
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NativePayNotifyV2 extends JsPayNotify {

	private static final long serialVersionUID = 1868431159301749988L;
	
	/**
	 * 产品ID 可视为订单ID
	 */
	@XmlElement(name = "ProductId")
	private String productId;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "NativePayNotifyV2 [productId=" + productId + ", "
				+ super.toString() + "]";
	}
}