package com.foxinmy.weixin4j.type;

/**
 * 证件类型
 * 
 * @className CredentialType
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年3月27日
 * @since JDK 1.6
 * @see
 */
public enum CredentialType {
	IDCARD("身份证");
	CredentialType(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return this.name;
	}
}
