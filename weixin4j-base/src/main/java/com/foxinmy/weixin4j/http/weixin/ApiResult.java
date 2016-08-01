package com.foxinmy.weixin4j.http.weixin;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 调用接口的返回
 * 
 * @className ApiResult
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月24日
 * @since JDK 1.6
 * @see <a href=
 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433747234&token=&lang=zh_CN">公众平台全局返回码说明</a>
 * @see <a href=
 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E5%85%A8%E5%B1%80%E8%BF%94%E5%9B%9E%E7%A0%81%E8%AF%B4%E6%98%8E">企业号全局返回码说明</a>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ApiResult implements Serializable {

	private static final long serialVersionUID = -6185313616955051150L;

	/**
	 * 调用接口返回码，通信标识
	 */
	@XmlElement(name = "return_code")
	@JSONField(name = "errcode")
	private String returnCode;

	/**
	 * 调用接口返回消息,如非 空,为错误原因 可能为空
	 */
	@XmlElement(name = "return_msg")
	@JSONField(name = "errmsg")
	private String returnMsg;

	public ApiResult() {
		this.returnCode = "0";
		this.returnMsg = "OK";
	}

	public ApiResult(String returnCode, String returnMsg) {
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	@Override
	public String toString() {
		return "returnCode=" + returnCode + ", returnMsg=" + returnMsg;
	}
}
