package com.foxinmy.weixin4j.http;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 调用接口返回xml格式
 * 
 * @className XmlResult
 * @author jy
 * @date 2014年11月1日
 * @since JDK 1.7
 * @see
 */
public class XmlResult implements Serializable {

	private static final long serialVersionUID = -6185313616955051150L;

	@XStreamAlias("return_code")
	@JSONField(name = "return_code")
	private String returnCode;// 此字段是通信标识,非交易 标识,交易是否成功需要查 看 result_code 来判断 非空
	@XStreamAlias("return_msg")
	@JSONField(name = "return_msg")
	private String returnMsg;// 返回信息,如非 空,为错误原因 可能为空
	@XStreamAlias("result_code")
	@JSONField(name = "result_code")
	private String resultCode;// 业务结果SUCCESS/FAIL 非空
	@XStreamAlias("err_code")
	@JSONField(name = "err_code")
	private String errCode;// 错误代码 可为空
	@XStreamAlias("err_code_des")
	@JSONField(name = "err_code_des")
	private String errCodeDes;// 结果信息描述 可为空

	public XmlResult() {
	}

	public XmlResult(String returnCode, String returnMsg) {
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return StringUtils.isNotBlank(returnMsg) ? returnMsg : null;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrCodeDes() {
		return StringUtils.isNotBlank(errCodeDes) ? errCodeDes : null;
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
