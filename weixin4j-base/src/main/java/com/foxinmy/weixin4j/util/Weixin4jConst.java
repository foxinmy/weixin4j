package com.foxinmy.weixin4j.util;

/**
 * 常量配置
 * 
 * @className Weixin4jConst
 * @author jy
 * @date 2015年7月1日
 * @since JDK 1.7
 * @see
 */
public final class Weixin4jConst {
	/**
	 * 使用FileTokenStorager时token的存放路径
	 */
	public static final String DEFAULT_TOKEN_PATH = "/tmp/weixin4j/token";
	/**
	 * 二维码保存路径
	 */
	public static final String DEFAULT_QRCODE_PATH = "/tmp/weixin4j/qrcode";
	/**
	 * 媒体文件保存路径
	 */
	public static final String DEFAULT_MEDIA_PATH = "/tmp/weixin4j/media";
	/**
	 * 对账单保存路径
	 */
	public static final String DEFAULT_BILL_PATH = "/tmp/weixin4j/bill";
	/**
	 * ca证书存放的完整路径 (V2版本后缀为*.pfx,V3版本后缀为*.p12)
	 */
	public static final String DEFAULT_CAFILE_PATH = "classpath:ca.p12";
}
