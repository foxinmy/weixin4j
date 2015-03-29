package com.foxinmy.weixin4j.qy.type;

/**
 * 成员状态
 * 
 * @className UserStatus
 * @author jy
 * @date 2014年11月19日
 * @since JDK 1.7
 * @see
 */
public enum UserStatus {
	/**
	 * 0=全部
	 */
	BOTH(0),
	/**
	 * 1=已关注
	 */
	FOLLOW(1),
	/**
	 * 2=已冻结
	 */
	FROZEN(2),
	/**
	 * 4=未关注
	 */
	UNFOLLOW(4);
	private int val;

	UserStatus(int val) {
		this.val = val;
	}

	public int getVal() {
		return val;
	}
}
