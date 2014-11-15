package com.foxinmy.weixin4j.mp.model;

import io.netty.handler.codec.http.HttpMethod;

import java.io.Serializable;

import com.foxinmy.weixin4j.mp.type.EncryptType;
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
	private String msgSignature;

	// 以下字段每次被动消息时都会带上
	private String echoStr;
	private String timeStamp;
	private String nonce;
	private String signature;

	private String token;

	// xml消息主体
	private String xmlContent;

	// request method
	private HttpMethod method;

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

	public String getMsgSignature() {
		return msgSignature;
	}

	public void setMsgSignature(String msgSignature) {
		this.msgSignature = msgSignature;
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

	public String getXmlContent() {
		return xmlContent;
	}

	public void setXmlContent(String xmlContent) {
		this.xmlContent = xmlContent;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "HttpMessage [toUserName=" + toUserName + ", encryptContent="
				+ encryptContent + ", encryptType=" + encryptType
				+ ", msgSignature=" + msgSignature + ", echoStr=" + echoStr
				+ ", timeStamp=" + timeStamp + ", nonce=" + nonce
				+ ", signature=" + signature + ", token=" + token
				+ ", xmlContent=" + xmlContent + ", method=" + method + "]";
	}
}
