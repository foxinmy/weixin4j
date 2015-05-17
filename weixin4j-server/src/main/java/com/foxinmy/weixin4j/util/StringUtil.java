package com.foxinmy.weixin4j.util;

import java.nio.charset.Charset;

/**
 * 字符串工具类
 * 
 * @className StringUtil
 * @author jy
 * @date 2015年5月17日
 * @since JDK 1.7
 * @see
 */
public final class StringUtil {

	public static final String EMPTY = "";
	public static final int INDEX_NOT_FOUND = -1;

	private static byte[] getBytes(final String content, final Charset charset) {
		if (content == null) {
			return null;
		}
		return content.getBytes(charset);
	}

	private static String newString(final byte[] bytes, final Charset charset) {
		return bytes == null ? null : new String(bytes, charset);
	}

	public static byte[] getBytesUtf8(final String content) {
		return getBytes(content, Consts.UTF_8);
	}

	public static String newStringUtf8(final byte[] bytes) {
		return newString(bytes, Consts.UTF_8);
	}

	public static boolean isEmpty(final CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static String capitalize(final String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}

		char firstChar = str.charAt(0);
		if (Character.isTitleCase(firstChar)) {
			// already capitalized
			return str;
		}
		return new StringBuilder(strLen)
				.append(Character.toTitleCase(firstChar))
				.append(str.substring(1)).toString();
	}

	public static String substringBefore(final String str,
			final String separator) {
		if (isEmpty(str) || separator == null) {
			return str;
		}
		if (separator.isEmpty()) {
			return EMPTY;
		}
		final int pos = str.indexOf(separator);
		if (pos == INDEX_NOT_FOUND) {
			return str;
		}
		return str.substring(0, pos);
	}

	public static String substringAfter(final String str, final String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (separator == null) {
			return EMPTY;
		}
		final int pos = str.indexOf(separator);
		if (pos == INDEX_NOT_FOUND) {
			return EMPTY;
		}
		return str.substring(pos + separator.length());
	}
}
