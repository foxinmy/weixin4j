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

	/**
	 * 请求的方式
	 */
	private String method;

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
	 * 加密类型(POST时存在)
	 * 
	 * @see com.foxinmy.weixin4j.type.EncryptType
	 */
	private EncryptType encryptType;

	/**
	 * xml消息明文主体
	 */
	private String originalContent;

	/**
	 * xml消息密文主体(AES时存在)
	 */
	private String encryptContent;

	public WeixinRequest(String method, EncryptType encryptType,
			String echoStr, String timeStamp, String nonce, String signature,
			String msgSignature, String originalContent, String encryptContent) {
		this.method = method;
		this.encryptType = encryptType;
		this.echoStr = echoStr;
		this.timeStamp = timeStamp;
		this.nonce = nonce;
		this.signature = signature;
		this.msgSignature = msgSignature;
		this.originalContent = originalContent;
		this.encryptContent = encryptContent;
	}

	public String getMethod() {
		return method;
	}

	public String getEchoStr() {
		return echoStr;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public String getNonce() {
		return nonce;
	}

	public String getSignature() {
		return signature;
	}

	public String getMsgSignature() {
		return msgSignature;
	}

	public EncryptType getEncryptType() {
		return encryptType;
	}

	public String getOriginalContent() {
		return originalContent;
	}

	public String getEncryptContent() {
		return encryptContent;
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
