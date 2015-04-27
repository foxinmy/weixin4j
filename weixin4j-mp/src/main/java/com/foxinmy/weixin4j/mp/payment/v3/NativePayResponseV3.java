package com.foxinmy.weixin4j.mp.payment.v3;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.exception.PayException;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.mp.payment.PayUtil;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * V3 Native支付时的回调响应
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

	@XStreamOmitField
	@JSONField(serialize = false)
	private PrePay prePay;

	private String prepay_id;

	/**
	 * 一般作为校验失败时返回
	 * 
	 * @param returnMsg
	 *            失败消息
	 * @param resultMsg
	 *            结果消息
	 * @throws PayException
	 */
	public NativePayResponseV3(String returnMsg, String resultMsg) {
		super.setReturnMsg(returnMsg);
		super.setReturnCode(Consts.FAIL);
		super.setErrCodeDes(resultMsg);
		super.setResultCode(Consts.FAIL);
	}

	/**
	 * 作为return_code 为 SUCCESS 的时候返回
	 * 
	 * @param payPackage
	 *            订单信息
	 * @throws PayException
	 */
	public NativePayResponseV3(PayPackageV3 payPackage, String paysignKey)
			throws PayException {
		super.setReturnCode(Consts.SUCCESS);
		this.setResultCode(Consts.SUCCESS);
		this.setMchId(payPackage.getMchId());
		this.setAppId(payPackage.getAppid());
		this.setNonceStr(RandomUtil.generateString(16));
		this.prePay = PayUtil.createPrePay(payPackage, paysignKey);
		this.prepay_id = prePay.getPrepayId();
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	@Override
	public String toString() {
		return "NativePayResponseV3 [prePay=" + prePay + ", prepay_id="
				+ prepay_id + ", " + super.toString() + "]";
	}

}
