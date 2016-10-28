package com.foxinmy.weixin4j.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * 
 * @className RegexUtil
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年12月8日
 * @since JDK 1.7
 * @see
 */
public final class RegexUtil {
	/**
	 * Content-disposition 中的 filename提取正则
	 */
	private static final Pattern FILENAME_RGX = Pattern
			.compile("(?<=filename=\").*?(?=\")");

	/**
	 * 从 Content-disposition提取文件名
	 * 
	 * @param contentDisposition
	 * @return
	 */
	public static String regexFileNameFromContentDispositionHeader(
			String contentDisposition) {
		if (StringUtil.isBlank(contentDisposition)) {
			return null;
		}
		Matcher filenameMatcher = FILENAME_RGX.matcher(contentDisposition);
		return filenameMatcher.find() ? filenameMatcher.group() : null;
	}
}
