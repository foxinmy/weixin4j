package com.foxinmy.weixin4j.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期工具类
 * 
 * @className DateUtil
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年10月31日
 * @since JDK 1.6
 * @see
 */
public final class DateUtil {
	private static final String yyyyMMdd = "yyyyMMdd";
	private static final String yyyy_MM_dd = "yyyy-MM-dd";
	private static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

	/**
	 * 日期对象转换为yyyymmdd的字符串形式
	 * 
	 * @param date
	 *            日期对象
	 * @return
	 */
	public static String fortmat2yyyyMMdd(Date date) {
		return formatDate(date, yyyyMMdd);
	}

	/**
	 * 日期对象转换为yyyy_mm_dd的字符串形式
	 * 
	 * @param date
	 *            日期对象
	 * @return
	 */
	public static String fortmat2yyyy_MM_dd(Date date) {
		return formatDate(date, yyyy_MM_dd);
	}

	/**
	 * 日期对象转换为yyyymmddhhmmss的字符串形式
	 * 
	 * @param date
	 *            日期对象
	 * @return
	 */
	public static String fortmat2yyyyMMddHHmmss(Date date) {
		return formatDate(date, yyyyMMddHHmmss);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            日期对象
	 * @param pattern
	 *            格式表达式
	 * @return 日期字符串
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		return df.format(date);
	}

	/**
	 * yyyymmddhhmmss形式的字符串转换为日期对象
	 * 
	 * @param date
	 *            日期字符串
	 * @return
	 */
	public static Date parse2yyyyMMddHHmmss(String date) {
		return parseDate(date, yyyyMMddHHmmss);
	}

	/**
	 * 转换日期
	 * 
	 * @param date
	 *            日期字符串
	 * @param pattern
	 *            格式表达式
	 * @return 日期对象
	 */
	public static Date parseDate(String date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		try {
			return df.parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 当前时间戳转换为秒的字符串形式
	 * 
	 * @return
	 */
	public static String timestamp2string() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	/**
	 * 单位为元的金额格式化为分
	 * 
	 * @param fee
	 *            金额 单位为元
	 * @return 四舍六入五成双的整型金额
	 */
	public static int formatYuan2Fen(double fee) {
		BigDecimal _fee = new BigDecimal(Double.toString(fee));
		return _fee.multiply(new BigDecimal("100"))
				.setScale(0, BigDecimal.ROUND_HALF_EVEN).intValue();
	}

	/**
	 * 单位为分的金额格式化为元
	 * 
	 * @param fee
	 *            金额 单位为分
	 * @return 四舍六入五成双的金额
	 */
	public static double formatFee2Yuan(int fee) {
		BigDecimal _fee = new BigDecimal(Integer.toString(fee));
		return _fee.divide(new BigDecimal("100"))
				.setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}
}
