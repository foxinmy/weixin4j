package com.foxinmy.weixin4j.mp.oldpayment;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.payment.PayRequest;

/**
 * V2 Native支付时的回调响应
 *
 * @className NativePayResponseV2
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年10月28日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NativePayResponseV2 extends PayRequest {

	private static final long serialVersionUID = 6119895998783333012L;
	/**
	 * 返回码
	 */
	@JSONField(name = "RetCode")
	@XmlElement(name = "RetCode")
	private String retCode;
	/**
	 * 返回消息
	 */
	@JSONField(name = "RetErrMsg")
	@XmlElement(name = "RetErrMsg")
	private String retMsg;

	protected NativePayResponseV2() {
		// jaxb required
	}

	/**
	 * 响应错误信息
	 *
	 * @param errorMsg
	 *            错误信息
	 */
	public NativePayResponseV2(String errorMsg) {
		this.retCode = "-1";
		this.retMsg = errorMsg;
	}

	/**
	 * 正确响应
	 *
	 * @param weixinAccount
	 * @param payPackage
	 *            订单信息
	 */
	public NativePayResponseV2(WeixinOldPayAccount weixinAccount,
			PayPackageV2 payPackage) {
		super(weixinAccount.getId(), null);
		this.retCode = "0";
		this.retMsg = "OK";
		WeixinOldPaymentSignature weixinSignature = new WeixinOldPaymentSignature(
				weixinAccount.getPaySignKey(), weixinAccount.getPartnerKey());
		setPackageInfo(weixinSignature.sign(payPackage));
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", weixinAccount.getId());
		map.put("appkey", weixinAccount.getPaySignKey());
		map.put("timestamp", getTimeStamp());
		map.put("noncestr", getNonceStr());
		map.put("package", getPackageInfo());
		map.put("retcode", getRetCode());
		map.put("reterrmsg", getRetMsg());
		this.setPaySign(weixinSignature.sign(map));
	}

	public String getRetCode() {
		return retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	@Override
	public String toString() {
		return "NativePayResponseV2 [retCode=" + retCode + ", retMsg=" + retMsg
				+ ", " + super.toString() + "]";
	}
}
