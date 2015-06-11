package com.foxinmy.weixin4j.qy.model;

import com.foxinmy.weixin4j.model.WeixinAccount;

/**
 * 微信企业号信息
 * 
 * @className WeixinQyAccount
 * @author jy
 * @date 2014年11月18日
 * @since JDK 1.7
 * @see <a href=
 *      "https://qy.weixin.qq.com/cgi-bin/home?lang=zh_CN&token=685923034#setting"
 *      >企业号设置</a>
 */
public class WeixinQyAccount extends WeixinAccount {
	private static final long serialVersionUID = 3689999353867189585L;

	public WeixinQyAccount() {
	}

	/**
	 * 
	 * @param corpid
	 *            企业ID
	 * @param corpsecret
	 *            管理组的凭证密钥
	 */
	public WeixinQyAccount(String corpid, String corpsecret) {
		super(corpid, corpsecret);
	}

	/**
	 * 提供商的secret
	 */
	private String providerSecret;

	public String getProviderSecret() {
		return providerSecret;
	}

	public void setProviderSecret(String providerSecret) {
		this.providerSecret = providerSecret;
	}

	@Override
	public String toString() {
		return "WeixinQyAccount [" + super.toString() + ", providerSecret="
				+ providerSecret + "]";
	}
}
