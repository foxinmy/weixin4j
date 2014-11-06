package com.foxinmy.weixin4j.util;

import java.io.File;
import java.util.ResourceBundle;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.model.WeixinAccount;

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
	private static ResourceBundle weixin;
	static {
		weixin = ResourceBundle.getBundle("weixin");
		Set<String> keySet = weixin.keySet();
		for (String key : keySet) {
			if (!key.endsWith("_path")) {
				continue;
			}
			new File(getValue(key)).mkdirs();
		}
	}

	public static String getValue(String key) {
		return weixin.getString(key);
	}

	public static WeixinAccount getWeixinAccount() {
		String text = getValue("account");
		return JSON.parseObject(text, WeixinAccount.class);
	}
}
