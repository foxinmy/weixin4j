package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 成员新消息免打扰
 * 
 * @className ChatMute
 * @author jy
 * @date 2015年8月1日
 * @since JDK 1.7
 * @see
 */
public class ChatMute implements Serializable {

	private static final long serialVersionUID = 6734443056426236273L;

	@JSONField(name = "userid")
	private String userId;
	private int status;

	/**
	 * 默认关闭免打扰
	 * 
	 * @param userid
	 */
	public ChatMute(String userId) {
		this.userId = userId;
	}

	/**
	 * 传入true时开启免打扰
	 * 
	 * @param userid
	 *            成员userid
	 * @param status
	 *            是否开启免打扰
	 */
	@JSONCreator
	public ChatMute(@JSONField(name = "userId") String userId,
			@JSONField(name = "status") boolean status) {
		this.userId = userId;
		this.status = status ? 1 : 0;
	}

	public String getUserId() {
		return userId;
	}

	public int getStatus() {
		return status;
	}

	@JSONField(serialize = false)
	public boolean getFormatStatus() {
		return status == 1;
	}

	@Override
	public String toString() {
		return "ChatMute [userId=" + userId + ", status=" + status + "]";
	}
}
