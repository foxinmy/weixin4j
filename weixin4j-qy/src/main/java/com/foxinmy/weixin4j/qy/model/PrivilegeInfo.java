package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 权限信息
 * 
 * @className PrivilegeInfo
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年3月28日
 * @since JDK 1.6
 * @see
 */
public class PrivilegeInfo implements Serializable {

	private static final long serialVersionUID = 2689295767648714897L;
	/**
	 * 权限级别
	 */
	private int level;
	/**
	 * 	应用可见范围（成员）
	 */
	@JSONField(name = "allow_user")
	private List<String> allowUserIds;
	/**
	 * 应用可见范围（部门）
	 */
	@JSONField(name = "allow_party")
	private List<Integer> allowPartyIds;
	/**
	 * 	应用可见范围（标签）
	 */
	@JSONField(name = "allow_tag")
	private List<Integer> allowTagIds;
	/**
	 * 额外通讯录（成员）
	 */
	@JSONField(name = "extra_user")
	private List<String> extraUserIds;
	/**
	 * 额外通讯录（部门）
	 */
	@JSONField(name = "extra_party")
	private List<Integer> extraPartyIds;
	/**
	 * 额外通讯录（标签）
	 */
	@JSONField(name = "extra_tag")
	private List<Integer> extraTagIds;

	public int getLevel() {
		return level;
	}

	@JSONField(serialize = false)
	public PrivilegeLevel getFormatLevel() {
		return PrivilegeLevel.values()[level - 1];
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<String> getAllowUserIds() {
		return allowUserIds;
	}

	public void setAllowUserIds(List<String> allowUserIds) {
		this.allowUserIds = allowUserIds;
	}

	public List<Integer> getAllowPartyIds() {
		return allowPartyIds;
	}

	public void setAllowPartyIds(List<Integer> allowPartyIds) {
		this.allowPartyIds = allowPartyIds;
	}

	public List<Integer> getAllowTagIds() {
		return allowTagIds;
	}

	public void setAllowTagIds(List<Integer> allowTagIds) {
		this.allowTagIds = allowTagIds;
	}

	public List<String> getExtraUserIds() {
		return extraUserIds;
	}

	public void setExtraUserIds(List<String> extraUserIds) {
		this.extraUserIds = extraUserIds;
	}

	public List<Integer> getExtraPartyIds() {
		return extraPartyIds;
	}

	public void setExtraPartyIds(List<Integer> extraPartyIds) {
		this.extraPartyIds = extraPartyIds;
	}

	public List<Integer> getExtraTagIds() {
		return extraTagIds;
	}

	public void setExtraTagIds(List<Integer> extraTagIds) {
		this.extraTagIds = extraTagIds;
	}

	@Override
	public String toString() {
		return "PrivilegeInfo [level=" + level + ", allowUserIds=" + allowUserIds + ", allowPartyIds=" + allowPartyIds
				+ ", allowTagIds=" + allowTagIds + ", extraUserIds=" + extraUserIds + ", extraPartyIds=" + extraPartyIds
				+ ", extraTagIds=" + extraTagIds + "]";
	}
}