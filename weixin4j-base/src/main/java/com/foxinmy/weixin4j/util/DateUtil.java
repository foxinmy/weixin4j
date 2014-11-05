package com.foxinmy.weixin4j.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @className DateUtil
 * @author jy
 * @date 2014年10月31日
 * @since JDK 1.7
 * @see
 */
public class DateUtil {
	private static final String yyyyMMdd = "yyyyMMdd";
	private static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

	public static String fortmat2yyyyMMdd(Date date) {
		return new SimpleDateFormat(yyyyMMdd).format(date);
	}

	public static String fortmat2yyyyMMddHHmmss(Date date) {
		return new SimpleDateFormat(yyyyMMddHHmmss).format(date);
	}

	public static String format2fee(double fee) {
		return new DecimalFormat("#").format(fee * 100);
	}
}
