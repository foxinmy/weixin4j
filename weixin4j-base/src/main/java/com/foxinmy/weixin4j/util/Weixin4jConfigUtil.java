package com.foxinmy.weixin4j.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.model.WeixinAccount;

/**
 * 公众号配置信息 class路径下weixin4j.properties文件
 * 
 * @className Weixin4jConfigUtil
 * @author jy
 * @date 2014年10月31日
 * @since JDK 1.6
 * @see
 */
public class Weixin4jConfigUtil {
	private final static String CLASSPATH_PREFIX = "classpath:";
	private final static String CLASSPATH_VALUE;
	private static ResourceBundle weixinBundle;
	static {
		CLASSPATH_VALUE = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();
		try {
			weixinBundle = ResourceBundle.getBundle("weixin4j");
		} catch (MissingResourceException e) {
			;
		}
	}

	private final static String WEIXIN4J_PREFIX = "weixin4j";

	private static String wrapKeyName(String key) {
		if (!key.startsWith(WEIXIN4J_PREFIX)) {
			return String.format("%s.%s", WEIXIN4J_PREFIX, key);
		}
		return key;
	}

	/**
	 * 获取weixin4j.properties文件中的key值
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		String wrapKey = wrapKeyName(key);
		return System.getProperty(wrapKey, weixinBundle.getString(wrapKey));
	}

	/**
	 * key不存在时则返回传入的默认值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getValue(String key, String defaultValue) {
		String value = defaultValue;
		try {
			value = getValue(key);
			if (StringUtil.isBlank(value)) {
				value = defaultValue;
			}
		} catch (MissingResourceException e) {
			;
		} catch (NullPointerException e) {
			;
		}
		return value;
	}

	/**
	 * 判断属性是否存在[classpath:]如果存在则拼接项目路径后返回 一般用于文件的绝对路径获取
	 * 
	 * @param key
	 * @return
	 */
	public static String getClassPathValue(String key) {
		return getValue(key).replaceFirst(CLASSPATH_PREFIX, CLASSPATH_VALUE);
	}

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getClassPathValue(String key, String defaultValue) {
		return getValue(key, defaultValue).replaceFirst(CLASSPATH_PREFIX,
				CLASSPATH_VALUE);
	}

	public static WeixinAccount getWeixinAccount() {
		WeixinAccount account = null;
		try {
			account = JSON
					.parseObject(getValue("account"), WeixinAccount.class);
		} catch (NullPointerException e) {
			System.err
					.println("'weixin4j.account' key not found in weixin4j.properties.");
		} catch (MissingResourceException e) {
			System.err
					.println("'weixin4j.account' key not found in weixin4j.properties.");
		}
		return account;
	}
}
