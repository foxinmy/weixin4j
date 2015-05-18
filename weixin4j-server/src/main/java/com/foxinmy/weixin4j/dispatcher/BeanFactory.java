package com.foxinmy.weixin4j.dispatcher;

import com.foxinmy.weixin4j.exception.WeixinException;

/**
 * Bean构造
 * 
 * @className BeanFactory
 * @author jy
 * @date 2015年5月7日
 * @since JDK 1.7
 * @see
 */
public interface BeanFactory {
	Object getBean(String name) throws WeixinException;

	<T> T getBean(Class<T> clazz) throws WeixinException;

	<T> T getBean(String name, Class<T> clazz) throws WeixinException;
}
