package com.foxinmy.weixin4j.mp.payment.v3;

import org.apache.commons.lang3.StringUtils;

import com.foxinmy.weixin4j.exception.PayException;
import com.foxinmy.weixin4j.mp.payment.ApiResult;
import com.foxinmy.weixin4j.mp.payment.PayUtil;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Native支付时的回调响应
 * 
 * @className NativePayResponseV3
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.7
 * @see
 */
@XStreamAlias("xml")
public class NativePayResponseV3 extends ApiResult {

	private static final long serialVersionUID = 6119895998783333012L;

	private String prepay_id;

	public NativePayResponseV3(PayPackageV3 payPackage, String returnMsg,
			String resultMsg) throws PayException {
		super.setReturnMsg(returnMsg);
		super.setReturnCode(StringUtils.isNotBlank(returnMsg) ? FAIL : SUCCESS);
		this.setErrCodeDes(resultMsg);
		this.setResultCode(StringUtils.isNotBlank(resultMsg) ? FAIL : SUCCESS);
		this.setMchId(payPackage.getMch_id());
		this.setAppId(payPackage.getAppid());
		this.setNonceStr(RandomUtil.generateString(16));
		this.prepay_id = PayUtil.createPrePay(payPackage).getPrepayId();
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	@Override
	public String toString() {
		return "NativePayResponseV3 [prepay_id=" + prepay_id + ", getAppId()="
				+ getAppId() + ", getMchId()=" + getMchId()
				+ ", getNonceStr()=" + getNonceStr() + ", getSign()="
				+ getSign() + ", getDeviceInfo()=" + getDeviceInfo()
				+ ", getReturnCode()=" + getReturnCode() + ", getReturnMsg()="
				+ getReturnMsg() + ", getResultCode()=" + getResultCode()
				+ ", getErrCode()=" + getErrCode() + ", getErrCodeDes()="
				+ getErrCodeDes() + "]";
	}
}
