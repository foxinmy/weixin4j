package com.foxinmy.weixin4j.type;

/**
 * 币种
 * 
 * @className CurrencyType
 * @author jy
 * @date 2014年11月2日
 * @since JDK 1.6
 * @see
 */
public enum CurrencyType {
	CNY("人民币"), HKD("港元"), TWD("台币"), EUR("欧元"), USD("美元"), GBP("英镑"), JPY("日元");
	private String desc;

	CurrencyType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
