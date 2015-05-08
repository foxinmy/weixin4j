package com.foxinmy.weixin4j.request;

import java.io.Serializable;

import com.foxinmy.weixin4j.type.EncryptType;

/**
 * 微信请求
 * 
 * @className WeixinRequest
 * @author jy
 * @date 2015年3月29日
 * @since JDK 1.7
 * @see
 */
public class WeixinRequest implements Serializable, Cloneable {

	private static final long serialVersionUID = -9157395300510879866L;

	// 以下字段是加密方式为「安全模式」时的参数
	/**
	 * 加密后的内容
	 */
	private String encryptContent;
	/**
	 * 加密类型
	 * 
	 * @see com.foxinmy.weixin4j.type.EncryptType
	 */
	private EncryptType encryptType;

	// 以下字段每次被动消息时都会带上
	/**
	 * 随机字符串
	 */
	private String echoStr;
	/**
	 * 时间戳
	 */
	private String timeStamp;
	/**
	 * 随机数
	 */
	private String nonce;
	/**
	 * 参数签名
	 */
	private String signature;
	/**
	 * AES模式下消息签名
	 */
	private String msgSignature;
	/**
	 * xml消息明文主体
	 */
	private String originalContent;
	/**
	 * 请求的方式
	 */
	private String method;

	public String getEncryptContent() {
		return encryptContent;
	}

	public void setEncryptContent(String encryptContent) {
		this.encryptContent = encryptContent;
	}

	public EncryptType getEncryptType() {
		return encryptType;
	}

	public void setEncryptType(EncryptType encryptType) {
		this.encryptType = encryptType;
	}

	public String getEchoStr() {
		return echoStr;
	}

	public void setEchoStr(String echoStr) {
		this.echoStr = echoStr;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getMsgSignature() {
		return msgSignature;
	}

	public void setMsgSignature(String msgSignature) {
		this.msgSignature = msgSignature;
	}

	public String getOriginalContent() {
		return originalContent;
	}

	public void setOriginalContent(String originalContent) {
		this.originalContent = originalContent;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "WeixinRequest [encryptContent=" + encryptContent
				+ ", encryptType=" + encryptType + ", echoStr=" + echoStr
				+ ", timeStamp=" + timeStamp + ", nonce=" + nonce
				+ ", signature=" + signature + ", originalContent="
				+ originalContent + ", method=" + method + "]";
	}
}
