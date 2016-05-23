package com.foxinmy.weixin4j.mp.type;

/**
 * 国家地区语言版本
 * 
 * @className Lang
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月5日
 * @since JDK 1.6
 * @see
 */
public enum Lang {
	zh_CN("简体中文"), zh_TW("繁体中文"), zh_HK("繁体中文"), en("英语"), id("印尼"), ms("马来"), es(
			"西班牙"), ko("韩国"), it("意大利"), ja("日本"), pl("波兰"), pt("葡萄牙"), ru("俄国"), th(
			"泰文"), vi("越南"), ar("阿拉伯语"), hi("北印度"), he("希伯来"), tr("土耳其"), de(
			"德语"), fr("法语");
	private String desc;

	Lang(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
