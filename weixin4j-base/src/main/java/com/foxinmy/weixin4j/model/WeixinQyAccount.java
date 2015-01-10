package com.foxinmy.weixin4j.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.AccountType;

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

	public WeixinQyAccount(String corpid, String corpsecret) {
		super(corpid, corpsecret);
	}

	@Override
	@JSONField(deserialize = false)
	public AccountType getAccountType() {
		return AccountType.QY;
	}

	@Override
	public String toString() {
		return "WeixinQyAccount [" + super.toString() + "]";
	}
}
