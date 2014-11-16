package com.foxinmy.weixin4j.mp.util;

import java.util.HashMap;
import java.util.Map;

import com.foxinmy.weixin4j.mp.type.CustomRecordOperCode;

/**
 * 实体帮助类
 * 
 * @className EntityUtil
 * @author jy
 * @date 2014年11月16日
 * @since JDK 1.7
 * @see
 */
public class EntityUtil {
	private static Map<Integer, CustomRecordOperCode> customRecordOperCodeMap;
	static {
		customRecordOperCodeMap = new HashMap<Integer, CustomRecordOperCode>();
		for (CustomRecordOperCode operCode : CustomRecordOperCode.values()) {
			customRecordOperCodeMap.put(operCode.getCode(), operCode);
		}
	}

	public static CustomRecordOperCode getCustomRecordOperCode(int code) {
		return customRecordOperCodeMap.get(code);
	}
}
