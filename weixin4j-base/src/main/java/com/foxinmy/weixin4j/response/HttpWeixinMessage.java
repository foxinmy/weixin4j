package com.foxinmy.weixin4j.response;

import java.io.Serializable;

import com.foxinmy.weixin4j.type.EncryptType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class HttpWeixinMessage implements Serializable {
	private static final long serialVersionUID = -9157395300510879866L;

	// 以下字段是加密方式为「安全模式」时的参数
	@XStreamAlias("ToUserName")
	private String toUserName;
	@XStreamAlias("Encrypt")
	private String encryptContent;
	private EncryptType encryptType;

	// 以下字段每次被动消息时都会带上
	private String echoStr;
	private String timeStamp;
	private String nonce;
	private String signature;

	// 冗余字段
	private String token;

	// xml消息明文主体
	private String originalContent;

	// request method
	private String method;

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
		return "HttpWeixinMessage [toUserName=" + toUserName + ", encryptContent="
				+ encryptContent + ", encryptType=" + encryptType
				+ ", echoStr=" + echoStr + ", timeStamp=" + timeStamp
				+ ", nonce=" + nonce + ", signature=" + signature + ", token="
				+ token + ", originalContent=" + originalContent + ", method="
				+ method + "]";
	}
}
