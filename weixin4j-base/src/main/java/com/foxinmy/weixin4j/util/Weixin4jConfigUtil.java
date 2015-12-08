package com.foxinmy.weixin4j.util;

import java.io.File;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

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
	private final static ResourceBundle weixinBundle;
	static {
		weixinBundle = ResourceBundle.getBundle("weixin4j");
		Set<String> keySet = weixinBundle.keySet();
		File file = null;
		CLASSPATH_VALUE = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();
		for (String key : keySet) {
			if (!key.endsWith("_path")) {
				continue;
			}
			file = new File(getValue(key).replaceFirst(CLASSPATH_PREFIX,
					CLASSPATH_VALUE));
			if (!file.exists() && !file.mkdirs()) {
				System.err.append(String.format("%s create fail.%n",
						file.getAbsolutePath()));
			}
		}
	}

	/**
	 * 获取weixin4j.properties文件中的key值
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		return weixinBundle.getString(key);
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
			value = weixinBundle.getString(key);
		} catch (MissingResourceException e) {
			System.err.println("'" + key
					+ "' key not found in weixin4j.properties file.");
			; // error
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
		return new File(getValue(key).replaceFirst(CLASSPATH_PREFIX,
				CLASSPATH_VALUE)).getPath();
	}

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getClassPathValue(String key, String defaultValue) {
		String value = defaultValue;
		try {
			value = getClassPathValue(key);
		} catch (MissingResourceException e) {
			System.err.println("'" + key
					+ "' key not found in weixin4j.properties file.");
			; // error
		}
		return value;
	}

	public static WeixinAccount getWeixinAccount() {
		WeixinAccount account = null;
		try {
			account = JSON.parseObject(getValue("account"), WeixinAccount.class);
		} catch (MissingResourceException e) {
			System.err
					.println("'account' key not found in weixin4j.properties file.");
			; // error
		}
		return account;
	}
}
