package com.foxinmy.weixin4j.mp.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.foxinmy.weixin4j.mp.action.Action;
import com.foxinmy.weixin4j.mp.action.WeixinAction;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;
import com.foxinmy.weixin4j.util.ClassUtil;

public class WeixinActionMapping {
	private final Map<String, WeixinAction> actionMap;

	public WeixinActionMapping() {
		actionMap = new HashMap<String, WeixinAction>();
		Set<Class<?>> weixinActions = ClassUtil.getClasses(WeixinAction.class
				.getPackage());
		for (Class<?> clazz : weixinActions) {
			Action action = clazz.getAnnotation(Action.class);
			if (action == null) {
				continue;
			}
			WeixinAction weixinAction = null;
			try {
				weixinAction = (WeixinAction) clazz.newInstance();
			} catch (Exception e) {
				continue;
			}
			MessageType msgType = action.msgType();
			EventType[] eventTypes = action.eventType();
			if (eventTypes != null && eventTypes.length > 0) {
				for (EventType e : eventTypes) {
					actionMap.put(
							(msgType.name() + "_" + e.name()).toLowerCase(),
							weixinAction);
				}
				continue;
			}
			actionMap.put(msgType.name().toLowerCase(), weixinAction);
		}
	}

	public WeixinAction getAction(String key) {
		return actionMap.get(key.toLowerCase());
	}
}
