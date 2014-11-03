package com.foxinmy.weixin4j.util;

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
	private static final String YYYYMMDD = "yyyyMMdd";

	public static String fortmatYYYYMMDD(Date date) {
		return new SimpleDateFormat(YYYYMMDD).format(date);
	}
}
