package com.foxinmy.weixin4j.http.weixin;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 调用接口返回xml格式
 * 
 * @className XmlResult
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月1日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlResult implements Serializable {

	private static final long serialVersionUID = -6185313616955051150L;
	/**
	 * 调用接口返回码，通信标识
	 */
	@XmlElement(name = "return_code")
	@JSONField(name = "return_code")
	private String returnCode;

	/**
	 * 调用接口返回消息,如非 空,为错误原因 可能为空
	 */
	@XmlElement(name = "return_msg")
	@JSONField(name = "return_msg")
	private String returnMsg;
	/**
	 * 业务结果SUCCESS/FAIL 非空
	 */
	@XmlElement(name = "result_code")
	@JSONField(name = "result_code")
	private String resultCode;
	/**
	 * 错误代码 可为空
	 */
	@XmlElement(name = "err_code")
	@JSONField(name = "err_code")
	private String errCode;
	/**
	 * 结果信息描述 可为空
	 */
	@XmlElement(name = "err_code_des")
	@JSONField(name = "err_code_des")
	private String errCodeDes;

	protected XmlResult() {
		// jaxb required
	}

	public XmlResult(String returnCode, String returnMsg) {
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
	}

	public String getResultCode() {
		return resultCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public String getErrCodeDes() {
		return errCodeDes;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}

	@Override
	public String toString() {
		return "returnCode=" + returnCode + ", returnMsg=" + returnMsg
				+ ", resultCode=" + resultCode + ", errCode=" + errCode
				+ ", errCodeDes=" + errCodeDes;
	}
}
