package com.foxinmy.weixin4j.wxa.model.custommessage;

import java.io.Serializable;

import com.foxinmy.weixin4j.tuple.NotifyTuple;

/**
 * 客服消息。
 *
 * @since 1.8
 */
public class CustomMessage implements Serializable {

	private static final long serialVersionUID = 2018052901L;

	private String toUser;

	private NotifyTuple tuple;

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public NotifyTuple getTuple() {
		return tuple;
	}

	/**
	 * The {@link NotifyTuple}.
	 *
	 * @param tuple the tuple.
	 * @see Text
	 * @see Image
	 * @see Link
	 * @see MiniProgramPage
	 */
	public void setTuple(NotifyTuple tuple) {
		this.tuple = tuple;
	}

}
