package com.foxinmy.weixin4j.xml;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
	String[] value() default { "(_\\d)$" };
}
