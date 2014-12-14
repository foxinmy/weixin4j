package com.foxinmy.weixin4j.model;

/**
 * 微信企业号信息
 * 
 * @className WeixinQyAccount
 * @author jy
 * @date 2014年11月18日
 * @since JDK 1.7
 * @see <a href=
 *        "https://qy.weixin.qq.com/cgi-bin/home?lang=zh_CN&token=685923034#setting"
 *        >企业号设置</a>
 */
public class WeixinQyAccount extends WeixinAccount {
	private static final long serialVersionUID = 3689999353867189585L;

	public WeixinQyAccount() {
	}

	public WeixinQyAccount(String corpid, String corpsecret) {
		super(corpid, corpsecret);
	}

	@Override
	public String getTokenUrl() {
		return String.format(Consts.QY_ASSESS_TOKEN_URL, getId(), getSecret());
	}

	@Override
	public String toString() {
		return "WeixinQyAccount [" + super.toString() + "]";
	}
}
