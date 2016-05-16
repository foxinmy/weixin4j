package com.foxinmy.weixin4j.type;

/**
 * 海关
 * 
 * @className CustomsCity
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年3月27日
 * @since JDK 1.6
 * @see
 */
public enum CustomsCity {
	NO("无需上报海关"), GUANGZHOU("广州"), HANGZHOU("杭州"), NINGBO("宁波"), ZHENGZHOU_BS(
			"郑州（保税物流中心）"), CHONGQING("重庆"), XIAN("西安"), SHANGHAI("上海"), ZHENGZHOU_ZH(
			"郑州（综保区)");
	
	private String name;

	CustomsCity(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
