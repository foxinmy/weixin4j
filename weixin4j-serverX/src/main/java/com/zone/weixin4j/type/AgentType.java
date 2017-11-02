package com.zone.weixin4j.type;

/**
 * 应用类型
 * 
 * @className AgentType
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年8月1日
 * @since JDK 1.6
 * @see
 */
public enum AgentType {
	/**
	 * 聊天应用
	 */
	chat,
	// 企业客服回调
	/**
	 * 企业号内部客服，客户为企业号通讯录成员
	 */
	kf_internal,
	/**
	 * 企业号外部客服，客户为服务号openid
	 */
	kf_external
}
