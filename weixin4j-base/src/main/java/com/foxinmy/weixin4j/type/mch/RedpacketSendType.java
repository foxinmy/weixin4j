package com.foxinmy.weixin4j.type.mch;

/**
 * 红包发放类型
 * 
 * @className RedpacketSendType
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月4日
 * @since JDK 1.6
 * @see
 */
public enum RedpacketSendType {
	/**
	 * 通过API接口发放
	 */
	API,
	/**
	 * 通过上传文件方式发放
	 */
	UPLOAD,
	/**
	 * 通过活动方式发放
	 */
	ACTIVITY;
}
