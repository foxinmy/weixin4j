package com.foxinmy.weixin4j.qy.type;

/**
 * 异步任务的类型
 * 
 * @className BatchType
 * @author jy
 * @date 2015年3月31日
 * @since JDK 1.7
 * @see
 */
public enum BatchType {
	/**
	 * 增量更新成员
	 */
	SYNC_USER(1),
	/**
	 * 全量覆盖成员
	 */
	REPLACE_USER(2),
	/**
	 * 邀请成员关注
	 */
	INVITE_USER(3),
	/**
	 * 全量覆盖部门
	 */
	REPLACE_PARTY(4);

	private int code;

	BatchType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
