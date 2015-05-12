package com.foxinmy.weixin4j.mp.payment.v2;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.mp.type.SignType;
import com.foxinmy.weixin4j.util.StringUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 调用V2.x接口返回的公用字段
 * 
 * @className ApiResult
 * @author jy
 * @date 2014年12月30日
 * @since JDK 1.7
 * @see
 */
public class ApiResult implements Serializable {

	private static final long serialVersionUID = -2876899595643466203L;
	/**
	 * 是查询结果状态码,0 表明成功,其他表明错误;
	 */
	@JSONField(name = "ret_code")
	@XStreamAlias("retcode")
	private int retCode;
	/**
	 * 是查询结果出错信息;
	 */
	@JSONField(name = "ret_msg")
	@XStreamAlias("retmsg")
	private String retMsg;
	/**
	 * 是返回信息中的编码方式;
	 */
	@JSONField(name = "input_charset")
	@XStreamAlias("input_charset")
	private String inputCharset;
	/**
	 * 是财付通商户号,即前文的 partnerid;
	 */
	private String partner;
	/**
	 * 多密钥支持的密钥序号,默认 1
	 */
	@XStreamAlias("sign_key_index")
	@JSONField(name = "sign_key_index")
	private Integer signKeyIndex;
	/**
	 * 签名 <font color="red">调用者无需关注</font>
	 */
	private String sign;
	/**
	 * 签名类型,取值:MD5、RSA
	 */
	@JSONField(name = "sign_type")
	@XStreamAlias("sign_type")
	private SignType signType;

	public int getRetCode() {
		return retCode;
	}

	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return StringUtil.isNotBlank(retMsg) ? retMsg : null;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public SignType getSignType() {
		return signType;
	}

	public void setSignType(SignType signType) {
		this.signType = signType;
	}

	public Integer getSignKeyIndex() {
		return signKeyIndex;
	}

	public void setSignKeyIndex(Integer signKeyIndex) {
		this.signKeyIndex = signKeyIndex;
	}

	@Override
	public String toString() {
		return "retCode=" + retCode + ", retMsg=" + retMsg + ", inputCharset="
				+ inputCharset + ", partner=" + partner + ", sign=" + sign
				+ ", signType=" + signType + ", signKeyIndex=" + signKeyIndex;
	}
}
