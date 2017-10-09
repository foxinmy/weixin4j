package com.foxinmy.weixin4j.qy.model;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.model.WeixinAccount;

/**
 * 微信企业号信息
 *
 * @className WeixinQyAccount
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月18日
 * @since JDK 1.6
 */
public class WeixinQyAccount extends WeixinAccount {

	private static final long serialVersionUID = 3689999353867189585L;
	/**
	 * 多个应用套件信息
	 */
	private List<WeixinAccount> suites;
	/**
	 * 第三方提供商secret(企业号登陆)
	 */
	private String providerSecret;
	/**
	 * 消息服务secret(企业号聊天)
	 */
	private String chatSecret;

	/**
	 *
	 * @param corpid
	 *            企业ID 使用普通接口(WeixinProxy对象)必须填写
	 * @param corpsecret
	 *            管理组的凭证密钥 使用普通接口(WeixinProxy对象)必须填写
	 * @param suites
	 *            应用套件集合 使用套件接口(WeixinSuiteProxy#SuiteApi)必须填写
	 * @param providerSecret
	 *            第三方提供商secret(企业号登陆) 使用服务商接口(WeixinSuiteProxy#ProviderApi)必填项
	 * @param chatSecret
	 *            消息服务secret(企业号聊天) 暂无用途
	 */
	@JSONCreator
	public WeixinQyAccount(@JSONField(name = "id") String corpid,
			@JSONField(name = "secret") String corpsecret,
			@JSONField(name = "suites") List<WeixinAccount> suites,
			@JSONField(name = "providerSecret") String providerSecret,
			@JSONField(name = "chatSecret") String chatSecret) {
		super(corpid, corpsecret);
		this.suites = suites;
		this.providerSecret = providerSecret;
		this.chatSecret = chatSecret;
	}

	public List<WeixinAccount> getSuites() {
		return suites;
	}

	public String getProviderSecret() {
		return providerSecret;
	}

	public String getChatSecret() {
		return chatSecret;
	}

	@Override
	public String toString() {
		return "WeixinQyAccount [" + super.toString() + ", suites=" + suites
				+ ", providerSecret=" + providerSecret + ",  chatSecret="
				+ chatSecret + "]";
	}
}
