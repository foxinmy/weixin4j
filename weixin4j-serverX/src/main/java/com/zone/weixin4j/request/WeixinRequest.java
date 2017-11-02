package com.zone.weixin4j.request;

import com.zone.weixin4j.type.EncryptType;
import com.zone.weixin4j.util.AesToken;

import javax.servlet.http.HttpServletRequest;


/**
 * 微信请求
 *
 * @author jinyu(foxinmy@gmail.com)
 * @className WeixinRequest
 * @date 2015年3月29日
 * @see
 * @since JDK 1.6
 */
public class WeixinRequest {

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
     * @see com.zone.weixin4j.type.EncryptType
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

    private HttpServletRequest request;

    public WeixinRequest(String uri,
                         EncryptType encryptType, String echoStr, String timeStamp,
                         String nonce, String signature, String msgSignature,
                         String originalContent, String encryptContent, AesToken aesToken) {
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

    public WeixinRequest(String uri,
                         EncryptType encryptType, String echoStr, String timeStamp,
                         String nonce, String signature, String msgSignature,
                         String originalContent, String encryptContent, AesToken aesToken, HttpServletRequest request) {
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
        this.request = request;
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

    public HttpServletRequest getHttpServletRequest() {
        return request;
    }

    public WeixinRequest setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.request = httpServletRequest;
        return this;
    }

    @Override
    public String toString() {
        return "WeixinRequest [uri=" + uri + ", echoStr=" + echoStr
                + ", timeStamp=" + timeStamp + ", nonce=" + nonce
                + ", signature=" + signature + ", msgSignature=" + msgSignature
                + ", encryptType=" + encryptType + ", originalContent="
                + originalContent + ", encryptContent=" + encryptContent
                + ", aesToken=" + aesToken + "]";
    }
}
