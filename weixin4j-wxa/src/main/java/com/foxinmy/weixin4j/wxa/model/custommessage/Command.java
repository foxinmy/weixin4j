package com.foxinmy.weixin4j.wxa.model.custommessage;

import com.foxinmy.weixin4j.wxa.api.CustomMessageApi;

/**
 * 客服输入状态命令。
 *
 * @see CustomMessageApi#typingCustomMessage(String, Command)
 * @since 1.8
 */
public enum Command {

	/**
	 * 对用户下发“正在输入"状态。
	 */
	TYPING("Typing"),

	/**
	 * 取消对用户的”正在输入"状态。
	 */
	CANCEL_TYPING("CancelTyping");

	private final String value;

	Command(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

}
