package com.foxinmy.weixin4j.mp.payment.v2;

import com.foxinmy.weixin4j.mp.payment.JsPayNotify;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Native支付回调时POST的信息
 * 
 * @className PayNativeNotifyV2
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.7
 * @see
 */
public class NativePayNotifyV2 extends JsPayNotify {

	private static final long serialVersionUID = 1868431159301749988L;
	@XStreamAlias("ProductId")
	private String productId;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "PayNativeNotifyV2 [productId=" + productId + ", getAppid()="
				+ getAppid() + ", getTimestamp()=" + getTimestamp()
				+ ", getNoncestr()=" + getNoncestr() + ", getOpenid()="
				+ getOpenid() + ", getAppsignature()=" + getAppsignature()
				+ ", getIssubscribe()=" + getIssubscribe()
				+ ", getSignmethod()=" + getSignmethod() + "]";
	}
}
