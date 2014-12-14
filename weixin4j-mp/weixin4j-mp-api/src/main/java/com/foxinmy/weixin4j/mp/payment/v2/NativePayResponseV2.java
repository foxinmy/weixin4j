package com.foxinmy.weixin4j.mp.payment.v2;

import com.foxinmy.weixin4j.model.WeixinMpAccount;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Native支付时的回调响应
 * 
 * @className NativePayResponseV2
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.7
 * @see
 */
@XStreamAlias("xml")
public class NativePayResponseV2 extends JsPayRequestV2 {

	private static final long serialVersionUID = 6119895998783333012L;
	@XStreamAlias("RetCode")
	private String retCode;
	@XStreamAlias("RetErrMsg")
	private String retMsg;

	public NativePayResponseV2(WeixinMpAccount weixinAccount,
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
