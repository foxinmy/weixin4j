package com.foxinmy.weixin4j.mp.type;

/**
 * 国家地区语言版本
 * @className Lang
 * @author jy
 * @date 2014年11月5日
 * @since JDK 1.7
 * @see
 */
public enum Lang {
	zh_CN("简体"), zh_TW("繁体"), en("英语");

	private String desc;

	Lang(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
