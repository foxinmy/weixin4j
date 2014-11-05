package com.foxinmy.weixin4j.mp.type;

/**
 * 头像大小
 * @className FaceSize
 * @author jy
 * @date 2014年11月5日
 * @since JDK 1.7
 * @see
 */
public enum FaceSize {
	small(46), middle1(64), middle2(96), big(132);
	private int size;

	FaceSize(int size) {
		this.size = size;
	}

	public int getInt() {
		return size;
	}
}
