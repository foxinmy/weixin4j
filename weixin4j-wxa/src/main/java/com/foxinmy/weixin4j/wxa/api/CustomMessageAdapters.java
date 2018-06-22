package com.foxinmy.weixin4j.wxa.api;

import java.util.HashMap;
import java.util.Map;

import com.foxinmy.weixin4j.tuple.NotifyTuple;
import com.foxinmy.weixin4j.wxa.model.custommessage.Command;
import com.foxinmy.weixin4j.wxa.model.custommessage.CustomMessage;

/**
 * Adapters for {@link CustomMessageApi}.
 *
 * @since 1.8
 */
final class CustomMessageAdapters {

	private CustomMessageAdapters() {
	}

	public static Map<String, Object> toMap(CustomMessage customMessage) {
		final NotifyTuple tuple = customMessage.getTuple();
		final String msgType = tuple.getMessageType();

		final Map<String, Object> params = new HashMap<String, Object>(3);
		params.put("touser", customMessage.getToUser());
		params.put("msgtype", msgType);
		params.put(msgType, tuple);

		return params;
	}

	public static Map<String, String> toMap(String toUser, Command command) {
		final Map<String, String> params = new HashMap<String, String>(2);
		params.put("touser", toUser);
		params.put("command", command.toString());
		return params;
	}
}
