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
	private final static ResourceBundle weixinBundle;
	static {
		weixinBundle = ResourceBundle.getBundle("weixin");
		Set<String> keySet = weixinBundle.keySet();
		for (String key : keySet) {
			if (!key.endsWith("_path")) {
				continue;
			}
			new File(getValue(key)).mkdirs();
		}
	}

	public static String getValue(String key) {
		return weixinBundle.getString(key);
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
