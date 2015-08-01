package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 聊天会话信息
 * 
 * @className ChatInfo
 * @author jy
 * @date 2015年7月31日
 * @since JDK 1.7
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

	public ChatInfo(String chatId, String name, String owner) {
		this(chatId, name, owner, null);
	}

	@JSONCreator
	public ChatInfo(@JSONField(name = "chatId") String chatId,
			@JSONField(name = "name") String name,
			@JSONField(name = "owner") String owner,
			@JSONField(name = "userlist") List<String> members) {
		this.chatId = chatId;
		this.name = name;
		this.owner = owner;
		this.members = members;
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

	@Override
	public String toString() {
		return "ChatInfo [chatId=" + chatId + ", name=" + name + ", owner="
				+ owner + ", members=" + members + "]";
	}
}
