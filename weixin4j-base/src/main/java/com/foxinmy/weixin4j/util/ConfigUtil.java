package com.foxinmy.weixin4j.util;

import java.io.File;
import java.util.ResourceBundle;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.model.WeixinMpAccount;
import com.foxinmy.weixin4j.model.WeixinQyAccount;

/**
 * 商户配置工具类
 * 
 * @className ConfigUtil
 * @author jy
 * @date 2014年10月31日
 * @since JDK 1.7
 * @see
 */
public class ConfigUtil {
	private final static String CLASSPATH_PREFIX = "classpath:";
	private final static String CLASSPATH_VALUE;
	private final static ResourceBundle weixinBundle;
	static {
		weixinBundle = ResourceBundle.getBundle("weixin");
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
	 * 获取weixin.properties文件中的key值
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		return weixinBundle.getString(key);
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

	public static <T extends WeixinAccount> T getWeixinAccount(Class<T> clazz) {
		String text = getValue("account");
		return JSON.parseObject(text, clazz);
	}

	public static WeixinMpAccount getWeixinMpAccount() {
		return getWeixinAccount(WeixinMpAccount.class);
	}

	public static WeixinQyAccount getWeixinQyAccount() {
		return getWeixinAccount(WeixinQyAccount.class);
	}
}
