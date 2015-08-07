package com.foxinmy.weixin4j.payment.mch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.exception.PayException;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.payment.PayUtil;
import com.foxinmy.weixin4j.util.RandomUtil;

/**
 * Native支付时的回调响应
 * 
 * @className NativePayResponseV3
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.7
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NativePayResponse extends ApiResult {

	private static final long serialVersionUID = 6119895998783333012L;

	@XmlTransient
	@JSONField(serialize = false)
	private PrePay prePay;

	@JSONField(name = "prepay_id")
	private String prepayId;

	protected NativePayResponse() {
		// jaxb required
	}

	/**
	 * 一般作为校验失败时返回
	 * 
	 * @param returnMsg
	 *            失败消息
	 * @param resultMsg
	 *            结果消息
	 * @throws PayException
	 */
	public NativePayResponse(String returnMsg, String resultMsg) {
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
	public NativePayResponse(MchPayPackage payPackage, String paysignKey)
			throws PayException {
		super.setReturnCode(Consts.SUCCESS);
		this.setResultCode(Consts.SUCCESS);
		this.setMchId(payPackage.getMchId());
		this.setAppId(payPackage.getAppId());
		this.setNonceStr(RandomUtil.generateString(16));
		this.prePay = PayUtil.createPrePay(payPackage, paysignKey);
		this.prepayId = prePay.getPrepayId();
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	@Override
	public String toString() {
		return "NativePayResponseV3 [prePay=" + prePay + ", prepayId="
				+ prepayId + ", " + super.toString() + "]";
	}

}
