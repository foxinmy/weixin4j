package com.foxinmy.weixin4j.qy.chat;

/**
 * 会话事件
 * 
 * @className ChatEventType
 * @author jy
 * @date 2015年8月1日
 * @since JDK 1.7
 * @see
 */
public enum ChatEventType {
	/**
	 * 创建会话
	 */
	create_chat,
	/**
	 * 修改会话
	 */
	update_chat,
	/**
	 * 退出会话
	 */
	quit_chat,
	/**
	 * 订阅事件
	 */
	subscribe,
	/**
	 * 取消订阅事件
	 */
	unsubscribe;
}
