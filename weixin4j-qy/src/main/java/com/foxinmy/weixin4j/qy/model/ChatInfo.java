package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 聊天会话信息
 * 
 * @className ChatInfo
 * @author jy
 * @date 2015年7月31日
 * @since JDK 1.6
 * @see
 */
public class ChatInfo implements Serializable {

	private static final long serialVersionUID = 1899784347096501375L;
	/**
	 * 会话id
	 */
	@JSONField(name = "chatid")
	private String chatId;
	/**
	 * 会话标题
	 */
	private String name;
	/**
	 * 管理员userid
	 */
	private String owner;
	/**
	 * 会话成员列表
	 */
	@JSONField(name = "userlist")
	private List<String> members;

	protected ChatInfo() {

	}

	public ChatInfo(String chatId) {
		this.chatId = chatId;
	}

	public ChatInfo(String name, String owner, String... members) {
		this.name = name;
		this.owner = owner;
		this.members = Arrays.asList(members);
	}

	public String getChatId() {
		return chatId;
	}

	public String getName() {
		return name;
	}

	public String getOwner() {
		return owner;
	}

	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

	public void setMembers(String... members) {
		this.members = Arrays.asList(members);
	}

	// ---------- setter 应该全部去掉

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "ChatInfo [chatId=" + chatId + ", name=" + name + ", owner="
				+ owner + ", members=" + members + "]";
	}
}
