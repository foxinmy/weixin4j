package com.foxinmy.weixin4j.mp.payment.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.model.WeixinPayAccount;

/**
 * V2 Native支付时的回调响应
 * 
 * @className NativePayResponseV2
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.7
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NativePayResponseV2 extends JsPayRequestV2 {

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

	public NativePayResponseV2(WeixinPayAccount weixinAccount,
			PayPackageV2 payPackage) {
		super(weixinAccount, payPackage);
		this.retCode = "0";
		this.retMsg = "OK";
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	@Override
	public String toString() {
		return "NativePayResponseV2 [retCode=" + retCode + ", retMsg=" + retMsg
				+ ", " + super.toString() + "]";
	}
}
