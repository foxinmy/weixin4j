package com.foxinmy.weixin4j.type;

/**
 * 星期
 * 
 * @className Week
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年8月5日
 * @since JDK 1.6
 */
public enum Week {
	MONDAY("周一"), TUESDAY("周二"), WEDNESDAY("周三"), THURSDAY("周四"), FRIDAY("周五"), SATURDAY(
			"周六"), SUNDAY("周日");
	private String desc;

	Week(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
