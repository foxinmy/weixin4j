package com.foxinmy.weixin4j.qy.type;

/**
 * 成员登录类型
 * 
 * @className LoginUserType
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年11月4日
 * @since JDK 1.6
 */
public enum LoginUserType {
	/**
	 * 企业号创建者
	 */
	CREATOR,
	/**
	 * 企业号内部系统管理员
	 */
	INNERADMIN,
	/**
	 * 企业号外部系统管理员
	 */
	OUTTERADMIN,
	/**
	 * 企业号分级管理员
	 */
	LEVELADMIN,
	/**
	 * 企业号成员
	 */
	MEMBER;
}
