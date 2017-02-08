package com.foxinmy.weixin4j.request;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

import com.foxinmy.weixin4j.type.EncryptType;
import com.foxinmy.weixin4j.util.AesToken;

/**
 * 微信请求
 * 
 * @className WeixinRequest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月29日
 * @since JDK 1.6
 * @see
 */
public class WeixinRequest implements HttpMessage {

	/**
	 * 请求的表头
	 */
	private HttpHeaders headers;
	/**
	 * 请求的方式
	 */
	private HttpMethod method;
	/**
	 * 请求的URI
	 */
	private String uri;

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
	/**
	 * aes & token
	 */
	private AesToken aesToken;
	/**
	 * url parameter
	 */
	private Map<String, List<String>> parameters;
	private DecoderResult decoderResult;
	private HttpVersion protocolVersion;

	public WeixinRequest(HttpHeaders headers, HttpMethod method, String uri,
			EncryptType encryptType, String echoStr, String timeStamp,
			String nonce, String signature, String msgSignature,
			String originalContent, String encryptContent, AesToken aesToken) {
		this.headers = headers;
		this.method = method;
		this.uri = uri;
		this.encryptType = encryptType;
		this.echoStr = echoStr;
		this.timeStamp = timeStamp;
		this.nonce = nonce;
		this.signature = signature;
		this.msgSignature = msgSignature;
		this.originalContent = originalContent;
		this.encryptContent = encryptContent;
		this.aesToken = aesToken;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getUri() {
		return uri;
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

	public AesToken getAesToken() {
		return aesToken;
	}

	public Map<String, List<String>> getParameters() {
		if (parameters == null) {
			this.parameters = new QueryStringDecoder(uri, true).parameters();
		}
		return parameters;
	}

	@Override
	public DecoderResult getDecoderResult() {
		return decoderResult;
	}

	@Override
	public void setDecoderResult(DecoderResult decoderResult) {
		this.decoderResult = decoderResult;
	}

	@Override
	public HttpVersion getProtocolVersion() {
		return protocolVersion;
	}

	@Override
	public HttpMessage setProtocolVersion(HttpVersion protocolVersion) {
		this.protocolVersion = protocolVersion;
		return this;
	}

	@Override
	public HttpHeaders headers() {
		return headers;
	}

	@Override
	public DecoderResult decoderResult() {
		return decoderResult;
	}

	@Override
	public HttpVersion protocolVersion() {
		return protocolVersion;
	}

	@Override
	public String toString() {
		return "WeixinRequest [headers=" + headers.entries() + ", method="
				+ method + ", uri=" + uri + ", echoStr=" + echoStr
				+ ", timeStamp=" + timeStamp + ", nonce=" + nonce
				+ ", signature=" + signature + ", msgSignature=" + msgSignature
				+ ", encryptType=" + encryptType + ", originalContent="
				+ originalContent + ", encryptContent=" + encryptContent
				+ ", aesToken=" + aesToken + ", decoderResult=" + decoderResult
				+ ", protocolVersion=" + protocolVersion + "]";
	}
}
