package com.foxinmy.weixin4j.qy.model;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.model.WeixinAccount;

/**
 * 微信企业号信息
 * 
 * @className WeixinQyAccount
 * @author jy
 * @date 2014年11月18日
 * @since JDK 1.6
 * @see <a href=
 *      "https://qy.weixin.qq.com/cgi-bin/home?lang=zh_CN&token=685923034#setting"
 *      >企业号设置</a>
 */
public class WeixinQyAccount extends WeixinAccount {

	private static final long serialVersionUID = 3689999353867189585L;
	/**
	 * 多个应用套件信息
	 */
	private List<WeixinAccount> suiteAccounts;
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
	 *            企业ID 必填
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
			@JSONField(name = "suites") List<WeixinAccount> suiteAccounts,
			@JSONField(name = "providerSecret") String providerSecret,
			@JSONField(name = "chatSecret") String chatSecret) {
		super(corpid, corpsecret);
		this.suiteAccounts = suiteAccounts;
		this.providerSecret = providerSecret;
		this.chatSecret = chatSecret;
	}

	public List<WeixinAccount> getSuiteAccounts() {
		return suiteAccounts;
	}

	public String getProviderSecret() {
		return providerSecret;
	}

	public String getChatSecret() {
		return chatSecret;
	}

	public WeixinAccount[] suiteAccountsToArray() {
		return suiteAccounts != null ? suiteAccounts
				.toArray(new WeixinAccount[suiteAccounts.size()]) : null;
	}

	@Override
	public String toString() {
		return "WeixinQyAccount [" + super.toString() + ", suiteAccounts="
				+ suiteAccounts + ", providerSecret=" + providerSecret
				+ ",  chatSecret=" + chatSecret + "]";
	}
}
