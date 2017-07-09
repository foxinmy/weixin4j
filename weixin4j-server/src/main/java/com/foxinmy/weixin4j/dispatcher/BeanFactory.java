package com.foxinmy.weixin4j.dispatcher;

import java.util.Map;

/**
 * Bean构造
 *
 * @className BeanFactory
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月7日
 * @since JDK 1.6
 * @see
 */
public interface BeanFactory {
	Object getBean(String name);

	<T> T getBean(Class<T> clazz);

	<T> T getBean(String name, Class<T> clazz);

	<T> Map<String, T> getBeans(Class<T> clazz);
}
