package com.foxinmy.weixin4j.qy.model;

import java.io.Serializable;

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

	private String userid;
	private int status;

	/**
	 * 默认关闭免打扰
	 * 
	 * @param userid
	 */
	public ChatMute(String userid) {
		this.userid = userid;
	}

	/**
	 * 传入true时开启免打扰
	 * 
	 * @param userid
	 *            成员userid
	 * @param status
	 *            是否开启免打扰
	 */
	public ChatMute(String userid, boolean status) {
		this.userid = userid;
		this.status = status ? 1 : 0;
	}

	public String getUserid() {
		return userid;
	}

	public int getStatus() {
		return status;
	}

	@JSONField(deserialize = false)
	public boolean getFormatStatus() {
		return status == 1;
	}

	@Override
	public String toString() {
		return "ChatMute [userid=" + userid + ", status=" + getFormatStatus()
				+ "]";
	}
}
