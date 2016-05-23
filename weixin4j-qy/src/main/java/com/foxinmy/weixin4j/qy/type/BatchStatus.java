package com.foxinmy.weixin4j.qy.type;

/**
 * 异步任务的状态
 * 
 * @className BatchStatus
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月31日
 * @since JDK 1.6
 * @see
 */
public enum BatchStatus {
	/**
	 * 开始
	 */
	START(1),
	/**
	 * 进行中
	 */
	PROCESS(2),
	/**
	 * 完成
	 */
	DONE(3);
	private int code;

	BatchStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
