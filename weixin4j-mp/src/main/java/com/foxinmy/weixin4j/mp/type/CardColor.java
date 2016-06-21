package com.foxinmy.weixin4j.mp.type;

/**
 * 卡券颜色
 *
 * @className CardColor
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年4月4日
 * @since JDK 1.6
 * @see
 */
public enum CardColor {
	COLOR010(99, 179, 89, "#63b359"), COLOR020(44, 159, 103, "#2c9f67"), COLOR030(
			80, 159, 201, "#509fc9"), COLOR040(88, 133, 207, "#5885cf"), COLOR050(
			144, 98, 192, "#9062c0"), COLOR060(208, 154, 69, "#d09a45"), COLOR070(
			228, 117, 56, "#e4b138"), COLOR080(238, 144, 60, "#ee903c"), COLOR081(
			240, 133, 0, "#f08500"), COLOR082(169, 217, 45, "#a9d92d"), COLOR090(
			221, 101, 73, "#dd6549"), COLOR0100(204, 70, 61, "#cc463d"), COLOR0101(
			207, 62, 54, "#cf3e36"), COLOR0102(94, 102, 113, "#5E6671");
	private int r;
	private int g;
	private int b;
	private String hex;

	CardColor(int r, int g, int b, String hex) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.hex = hex;
	}

	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}

	public String getHex() {
		return hex;
	}
}
