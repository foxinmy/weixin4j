package com.foxinmy.weixin4j.mp.oldpayment;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.SignType;

/**
 * 调用V2.x接口返回的公用字段
 * 
 * @className ApiResultV2
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年12月30日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ApiResultV2 implements Serializable {

	private static final long serialVersionUID = -2876899595643466203L;
	/**
	 * 是查询结果状态码,0 表明成功,其他表明错误;
	 */
	@JSONField(name = "retcode")
	@XmlElement(name = "retcode")
	private Integer retCode;
	/**
	 * 是查询结果出错信息;
	 */
	@JSONField(name = "retmsg")
	@XmlElement(name = "retmsg")
	private String retMsg;
	/**
	 * 是返回信息中的编码方式;
	 */
	@JSONField(name = "input_charset")
	@XmlElement(name = "input_charset")
	private String inputCharset;
	/**
	 * 是财付通商户号,即前文的 partnerid;
	 */
	private String partner;
	/**
	 * 多密钥支持的密钥序号,默认 1
	 */
	@XmlElement(name = "sign_key_index")
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
	@XmlElement(name = "sign_type")
	private String signType;

	protected ApiResultV2() {
		// jaxb required
	}

	public Integer getRetCode() {
		return retCode;
	}

	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return this.retMsg;
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

	public String getSignType() {
		return signType;
	}

	@JSONField(serialize = false)
	public SignType getFormatSignType() {
		return signType != null ? SignType.valueOf(signType) : null;
	}

	public void setSignType(String signType) {
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
