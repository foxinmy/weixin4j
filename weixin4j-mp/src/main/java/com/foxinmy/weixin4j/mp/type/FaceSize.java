package com.foxinmy.weixin4j.mp.type;

/**
 * 头像大小
 * 
 * @className FaceSize
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月5日
 * @since JDK 1.6
 * @see
 */
public enum FaceSize {
	/**
	 * 46x46
	 */
	small(46),
	/**
	 * 64x64
	 */
	middle1(64),
	/**
	 * 96x96
	 */
	middle2(96),
	/**
	 * 132x132
	 */
	big(132);
	private int size;

	FaceSize(int size) {
		this.size = size;
	}

	public int getInt() {
		return size;
	}
}
