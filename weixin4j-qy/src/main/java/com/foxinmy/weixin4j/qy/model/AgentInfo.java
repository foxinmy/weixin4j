package com.foxinmy.weixin4j.qy.model;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 企业号应用的设置信息
 * 
 * @className AgentInfo
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月17日
 * @since JDK 1.6
 * @see
 */
public class AgentInfo extends AgentSetter {

	private static final long serialVersionUID = -8975132919768696174L;

	/**
	 * 企业应用方形头像
	 */
	@JSONField(name = "square_logo_url")
	private String squareLogoUrl;
	/**
	 * 企业应用圆形头像
	 */
	@JSONField(name = "round_logo_url")
	private String roundLogoUrl;
	/**
	 * 企业应用可见范围（人员），其中包括userid和关注状态state
	 */
	@JSONField(deserialize = false)
	private List<User> allowUsers;
	/**
	 * 企业应用可见范围（部门）
	 */
	@JSONField(deserialize = false)
	private List<Integer> allowPartys;
	/**
	 * 企业应用可见范围（标签）
	 */
	@JSONField(deserialize = false)
	private List<Integer> allowTags;
	/**
	 * 企业应用是否被禁用
	 */
	private boolean close;
	/**
	 * 应用类型。1：消息型；2：主页型
	 */
	private int type;

	public AgentInfo() {
		super(0);
	}

	public String getSquareLogoUrl() {
		return squareLogoUrl;
	}

	public String getRoundLogoUrl() {
		return roundLogoUrl;
	}

	public List<User> getAllowUsers() {
		return allowUsers;
	}

	public void setAllowUsers(List<User> allowUsers) {
		this.allowUsers = allowUsers;
	}

	public List<Integer> getAllowPartys() {
		return allowPartys;
	}

	public void setAllowPartys(List<Integer> allowPartys) {
		this.allowPartys = allowPartys;
	}

	public List<Integer> getAllowTags() {
		return allowTags;
	}

	public void setAllowTags(List<Integer> allowTags) {
		this.allowTags = allowTags;
	}

	public boolean isClose() {
		return close;
	}

	// ---------- setter 应该全部去掉

	public void setSquareLogoUrl(String squareLogoUrl) {
		this.squareLogoUrl = squareLogoUrl;
	}

	public void setRoundLogoUrl(String roundLogoUrl) {
		this.roundLogoUrl = roundLogoUrl;
	}

	public void setClose(boolean close) {
		this.close = close;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "AgentInfo [type=" + type + ", squareLogoUrl=" + squareLogoUrl + ", roundLogoUrl=" + roundLogoUrl
				+ ", allowUsers=" + allowUsers + ", allowPartys=" + allowPartys + ", allowTags=" + allowTags
				+ ", close=" + close + ", " + super.toString() + "]";
	}
}
