package com.foxinmy.weixin4j.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 微信账号信息
 * 
 * @className WeixinAccount
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月18日
 * @since JDK 1.6
 * @see
 */
public class WeixinAccount implements Serializable {

	private static final long serialVersionUID = -6001008896414323534L;

	/**
	 * 唯一的身份标识
	 */
	private final String id;
	/**
	 * 调用接口的密钥
	 */
	private final String secret;

	@JSONCreator
	public WeixinAccount(@JSONField(name = "id") String id,
			@JSONField(name = "secret") String secret) {
		this.id = id;
		this.secret = secret;
	}

	public String getId() {
		return id;
	}

	public String getSecret() {
		return secret;
	}

	@Override
	public String toString() {
		return "id=" + id + ", secret=" + secret;
	}
}
