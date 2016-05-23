package com.foxinmy.weixin4j.qy.model;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 通讯录
 * 
 * @className Contacts
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年1月25日
 * @since JDK 1.6
 * @see
 */
public class Contacts extends IdParameter {

	private static final long serialVersionUID = -1334319915595303647L;

	@JSONField(name = "userlist")
	private List<User> users;
	@JSONField(name = "partylist")
	private List<Party> partys;
	@JSONField(name = "taglist")
	private List<Tag> tags;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
		List<String> userIds = new ArrayList<String>();
		for (User user : users) {
			userIds.add(user.getUserId());
		}
		super.setUserIds(userIds);
	}

	public List<Party> getPartys() {
		return partys;
	}

	public void setPartys(List<Party> partys) {
		this.partys = partys;
		List<Integer> partyIds = new ArrayList<Integer>();
		for (Party party : partys) {
			partyIds.add(party.getId());
		}
		super.setPartyIds(partyIds);
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
		List<Integer> tagIds = new ArrayList<Integer>();
		for (Tag tag : tags) {
			tagIds.add(tag.getId());
		}
		super.setTagIds(tagIds);
	}

	@Override
	public String toString() {
		return "Contacts [users=" + users + ", partys=" + partys + ", tags="
				+ tags + ", " + super.toString() + "]";
	}
}
