package com.foxinmy.weixin4j.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {
	private static Properties props = new Properties();
	static {
		try {
			props.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("weixin.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getValue(String key) {
		return props.getProperty(key);
	}

	public static void main(String[] args) {
		System.out.println(getValue("api_token_uri"));
	}
}
