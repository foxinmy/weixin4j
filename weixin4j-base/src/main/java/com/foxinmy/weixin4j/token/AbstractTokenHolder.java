package com.foxinmy.weixin4j.token;

import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.type.AccountType;
import com.foxinmy.weixin4j.util.ConfigUtil;

/**
 * 获取weixin.properties中的id&secret信息
 * 
 * @className AbstractTokenHolder
 * @author jy
 * @date 2014年10月6日
 * @since JDK 1.7
 * @see
 */
public abstract class AbstractTokenHolder implements TokenHolder {

	protected final HttpRequest request = new HttpRequest();
	protected final WeixinAccount weixinAccount;

	public AbstractTokenHolder(AccountType accountType) {
		if (accountType == AccountType.MP) {
			this.weixinAccount = ConfigUtil.getWeixinMpAccount();
		} else if (accountType == AccountType.QY) {
			this.weixinAccount = ConfigUtil.getWeixinQyAccount();
		} else {
			this.weixinAccount = null;
		}
	}

	public AbstractTokenHolder(WeixinAccount weixinAccount) {
		this.weixinAccount = weixinAccount;
	}

	public WeixinAccount getAccount() {
		return weixinAccount;
	}
}
