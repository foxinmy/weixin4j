package com.foxinmy.weixin4j.mp.type;

/**
 * 自动匹配模式
 * 
 * @className AutomatchMode
 * @author jy
 * @date 2015年4月15日
 * @since JDK 1.7
 * @see
 */
public enum AutomatchMode {
	/**
	 * 代表消息中含有该关键词即可
	 */
	contain,
	/**
	 * 表示消息内容必须和关键词严格相同
	 */
	equal
}
