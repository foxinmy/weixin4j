package com.zone.weixin4j.mp.component;

/**
 * 应用组件回调事件
 * 
 * @className ComponentEventType
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月5日
 * @since JDK 1.6
 */
public enum ComponentEventType {
	/**
	 * 推送ticket
	 */
	component_verify_ticket,
	/**
	 * 取消授权
	 */
	unauthorized,
	/**
	 * 授权成功
	 */
	authorized,
	/**
	 * 授权更新
	 */
	updateauthorized
}
