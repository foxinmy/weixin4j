package com.foxinmy.weixin4j.mp.type;

/**
 * 用户渠道来源
 * 
 * @className UserSourceType
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年1月25日
 * @since JDK 1.6
 * @see
 */
public enum UserSourceType {
	OTHER("其它（包括带参数二维码）"), QRCODE("扫二维码"), CARDSHARE("名片分享"), SONUMBER("搜号码（即微信添加朋友页的搜索）"), SOMPACCOUNT(
			"查询微信公众帐号"), ARTICLEMENU("图文页右上角菜单");
	private String desc;

	UserSourceType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
