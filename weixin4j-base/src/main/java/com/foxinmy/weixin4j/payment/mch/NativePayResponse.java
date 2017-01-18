package com.foxinmy.weixin4j.payment.mch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.sign.WeixinPaymentSignature;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.RandomUtil;

/**
 * Native支付时的回调响应
 * 
 * @className NativePayResponse
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年10月28日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NativePayResponse extends MerchantResult {

	private static final long serialVersionUID = 6119895998783333012L;

	@XmlElement(name = "prepay_id")
	@JSONField(name = "prepay_id")
	private String prepayId;

	protected NativePayResponse() {
		// jaxb required
	}

	/**
	 * 作为return_code 为 FAIL 的时候返回
	 * 
	 * @param returnMsg
	 *            失败消息
	 * @param resultMsg
	 *            结果消息
	 * @throws WeixinPayException
	 */
	public NativePayResponse(String returnMsg, String resultMsg) {
		super(Consts.FAIL, returnMsg);
		super.setErrCodeDes(resultMsg);
		super.setResultCode(Consts.FAIL);
	}

	/**
	 * 作为return_code 为 SUCCESS 的时候返回
	 * 
	 * @param weixinAccount
	 *            商户信息
	 * @param prepayId
	 *            调用统一下单接口生成的预支付ID
	 * @throws WeixinPayException
	 */
	public NativePayResponse(WeixinPayAccount weixinAccount, String prepayId) {
		super(Consts.SUCCESS, "OK");
		this.setResultCode(Consts.SUCCESS);
		this.setMchId(weixinAccount.getMchId());
		this.setAppId(weixinAccount.getId());
		this.setNonceStr(RandomUtil.generateString(16));
		this.prepayId = prepayId;
		this.setSign(new WeixinPaymentSignature(weixinAccount.getPaySignKey())
				.sign(this));
	}

	public String getPrepayId() {
		return prepayId;
	}

	@Override
	public String toString() {
		return "NativePayResponse [prepayId=" + prepayId + ", "
				+ super.toString() + "]";
	}
}
