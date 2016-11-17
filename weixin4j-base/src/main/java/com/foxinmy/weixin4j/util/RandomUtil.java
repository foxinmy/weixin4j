package com.foxinmy.weixin4j.util;

import java.util.Random;
import java.util.UUID;

/**
 * 随机码工具类
 * 
 * @className RandomUtil
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年10月22日
 * @since JDK 1.6
 * @see
 */
public class RandomUtil {

	private static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LETTERCHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUMBERCHAR = "0123456789";

	/**
	 * 返回一个定长的随机字符串(包含数字和大小写字母)
	 * 
	 * @param length
	 *            随机数的长度
	 * @return
	 */
	public static String generateString(int length) {
		StringBuilder sb = new StringBuilder(length);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯数字字符串(只包含数字)
	 * 
	 * @param length
	 *            随机数的长度
	 * @return
	 */
	public static String generateStringByNumberChar(int length) {
		StringBuilder sb = new StringBuilder(length);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(NUMBERCHAR.charAt(random.nextInt(NUMBERCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机数的长度
	 * @return
	 */
	public static String generateStringByLetterCharr(int length) {
		StringBuilder sb = new StringBuilder(length);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(LETTERCHAR.charAt(random.nextInt(LETTERCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机数的长度
	 * @return
	 */
	public static String generateLowerString(int length) {
		return generateStringByLetterCharr(length).toLowerCase();
	}

	/**
	 * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机数的长度
	 * @return
	 */
	public static String generateUpperString(int length) {
		return generateStringByLetterCharr(length).toUpperCase();
	}

	/**
	 * 随机获取UUID字符串(无中划线)
	 * 
	 * @return UUID字符串
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8) + uuid.substring(9, 13)
				+ uuid.substring(14, 18) + uuid.substring(19, 23)
				+ uuid.substring(24);
	}

	public static void main(String[] args) {
		System.out.println(System.nanoTime());
		System.out.println(System.currentTimeMillis());
	}
}
