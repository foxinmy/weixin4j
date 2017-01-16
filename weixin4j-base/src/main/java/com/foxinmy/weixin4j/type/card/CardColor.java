package com.foxinmy.weixin4j.type.card;

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
	/**
	 * <font Color="#63b359">Color010</font>
	 */
	Color010(99, 179, 89, "#63b359"),
	/**
	 * <font Color="#2c9f67">Color020</font>
	 */
	Color020(44, 159, 103, "#2c9f67"),
	/**
	 * <font Color="#509fc9">Color030</font>
	 */
	Color030(80, 159, 201, "#509fc9"),
	/**
	 * <font Color="#5885cf">Color040</font>
	 */
	Color040(88, 133, 207, "#5885cf"),
	/**
	 * <font Color="#9062c0">Color050</font>
	 */
	Color050(144, 98, 192, "#9062c0"),
	/**
	 * <font Color="#d09a45">Color060</font>
	 */
	Color060(208, 154, 69, "#d09a45"),
	/**
	 * <font Color="#e4b138">Color070</font>
	 */
	Color070(228, 117, 56, "#e4b138"),
	/**
	 * <font Color="#ee903c">Color080</font>
	 */
	Color080(238, 144, 60, "#ee903c"),
	/**
	 * <font Color="#f08500">Color081</font>
	 */
	Color081(240, 133, 0, "#f08500"),
	/**
	 * <font Color="#a9d92d">Color082</font>
	 */
	Color082(169, 217, 45, "#a9d92d"),
	/**
	 * <font Color="#dd6549">Color090</font>
	 */
	Color090(221, 101, 73, "#dd6549"),
	/**
	 * <font Color="#cc463d">Color0100</font>
	 */
	Color100(204, 70, 61, "#cc463d"),
	/**
	 * <font Color="#cf3e36">Color0101</font>
	 */
	Color101(207, 62, 54, "#cf3e36"),
	/**
	 * <font Color="#5E6671">Color0102</font>
	 */
	Color102(94, 102, 113, "#5E6671");
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
