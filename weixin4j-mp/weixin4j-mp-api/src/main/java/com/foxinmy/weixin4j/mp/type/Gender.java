package com.foxinmy.weixin4j.mp.type;

/**
 * 用户性别
 * @className Gender
 * @author jy
 * @date 2014年11月5日
 * @since JDK 1.7
 * @see
 */
public enum Gender {
	male(1), female(2), unknown(0);

	private int sex;

	Gender(int sex) {
		this.sex = sex;
	}

	public int getInt() {
		return sex;
	}
}
