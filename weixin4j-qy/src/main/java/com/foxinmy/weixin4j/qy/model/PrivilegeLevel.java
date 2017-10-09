package com.foxinmy.weixin4j.qy.model;

/**
 * 权限级别
 * 
 * @className PrivilegeLevel
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年3月28日
 * @since JDK 1.6
 * @see
 */
public enum PrivilegeLevel {
	/**
	 * 标识只读
	 */
	IDENTIFYING_READABLE(1),
	/**
	 * 信息只读
	 */
	INFORMATION_READABLE(2),
	/**
	 * 信息读写
	 */
	INFORMATION_READABLE$WRITABLE(3);

	private int level;

	PrivilegeLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return this.level;
	}
}
