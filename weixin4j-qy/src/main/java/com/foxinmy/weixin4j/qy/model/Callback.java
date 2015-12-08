package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 调用某些接口时填入的回调信息
 * 
 * @className Callback
 * @author jy
 * @date 2015年3月30日
 * @since JDK 1.6
 * @see
 */
public class Callback implements Serializable {

	private static final long serialVersionUID = 8575808461248605317L;

	/**
	 * 企业应用接收企业号推送请求的访问协议和地址，支持http或https协议
	 */
	private String url;
	/**
	 * 用于生成签名
	 */
	private String token;
	/**
	 * 用于消息体的加密，是AES密钥的Base64编码
	 */
	@JSONField(name = "encodingaeskey")
	private String aesKey;

	@JSONCreator
	public Callback(@JSONField(name = "url") String url,
			@JSONField(name = "token") String token,
			@JSONField(name = "aesKey") String aesKey) {
		this.url = url;
		this.token = token;
		this.aesKey = aesKey;
	}

	public String getUrl() {
		return url;
	}

	public String getToken() {
		return token;
	}

	public String getAesKey() {
		return aesKey;
	}

	@Override
	public String toString() {
		return "Callback [url=" + url + ", token=" + token + ", aesKey="
				+ aesKey + "]";
	}
}
