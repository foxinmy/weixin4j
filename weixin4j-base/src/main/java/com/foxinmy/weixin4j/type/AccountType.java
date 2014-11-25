package com.foxinmy.weixin4j.type;

import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.model.WeixinMpAccount;
import com.foxinmy.weixin4j.model.WeixinQyAccount;

/**
 * 账号类型
 * 
 * @className AccountType
 * @author jy
 * @date 2014年11月18日
 * @since JDK 1.7
 * @see
 */
public enum AccountType {
	MP(WeixinMpAccount.class), QY(WeixinQyAccount.class);
	private Class<? extends WeixinAccount> clazz;

	AccountType(Class<? extends WeixinAccount> clazz) {
		this.clazz = clazz;
	}

	public Class<? extends WeixinAccount> getClazz() {
		return clazz;
	}
}
