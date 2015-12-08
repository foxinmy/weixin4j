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
 * @author jy
 * @date 2014年11月1日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlResult implements Serializable {

	private static final long serialVersionUID = -6185313616955051150L;

	/**
	 * 此字段是通信标识,非交易 标识,交易是否成功需要查 看 result_code 来判断非空
	 */
	@XmlElement(name = "return_code")
	@JSONField(name = "return_code")
	private String returnCode;
	/**
	 * 返回信息,如非 空,为错误原因 可能为空
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

	public XmlResult() {
	}

	public XmlResult(String returnCode, String returnMsg) {
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
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

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
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
