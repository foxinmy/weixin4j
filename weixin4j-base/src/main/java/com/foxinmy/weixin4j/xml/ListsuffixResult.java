package com.foxinmy.weixin4j.xml;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Pattern;

/**
 * 对$n结尾的节点注解
 * 
 * @className ListsuffixResult
 * @author jy
 * @date 2015年6月15日
 * @since JDK 1.7
 * @see
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListsuffixResult {
	String[] value() default { DEFAULT_REGEX };

	public final static String DEFAULT_REGEX = "(_\\d)$";
	public final static Pattern DEFAULT_PATTERN = Pattern
			.compile(DEFAULT_REGEX);
}
