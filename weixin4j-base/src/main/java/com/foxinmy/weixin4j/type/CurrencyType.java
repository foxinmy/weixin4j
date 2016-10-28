package com.foxinmy.weixin4j.type;

/**
 * 币种
 * 
 * @className CurrencyType
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月2日
 * @since JDK 1.6
 * @see
 */
public enum CurrencyType {
	CNY("人民币"), HKD("港元"), TWD("台币"), EUR("欧元"), USD("美元"), GBP("英镑"), JPY("日元"), CAD(
			"加拿大元"), AUD("澳大利亚元"), NZD("新西兰元"), KRW("韩元"), THB("泰铢");

	private String desc;

	CurrencyType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
