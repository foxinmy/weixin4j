package com.foxinmy.weixin4j.mp.payment.v3;

import com.foxinmy.weixin4j.mp.payment.ApiResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * V3 Native支付回调时POST的信息
 * 
 * @className PayNativeNotifyV3
 * @author jy
 * @date 2014年10月30日
 * @since JDK 1.7
 * @see
 */
@XStreamAlias("xml")
public class NativePayNotifyV3 extends ApiResult {

	private static final long serialVersionUID = 4515471400239795492L;

	@XStreamAlias("mch_id")
	private String mchId;
	@XStreamAlias("product_id")
	private String productId;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	@Override
	public String toString() {
		return "NativePayNotifyV3 [mchId=" + mchId + ", productId=" + productId
				+ ", getAppId()=" + getAppId() + ", getNonceStr()="
				+ getNonceStr() + ", getSign()=" + getSign()
				+ ", getDeviceInfo()=" + getDeviceInfo() + ", toString()="
				+ super.toString() + ", getReturnCode()=" + getReturnCode()
				+ ", getReturnMsg()=" + getReturnMsg() + ", getResultCode()="
				+ getResultCode() + ", getErrCode()=" + getErrCode()
				+ ", getErrCodeDes()=" + getErrCodeDes() + "]";
	}
}
