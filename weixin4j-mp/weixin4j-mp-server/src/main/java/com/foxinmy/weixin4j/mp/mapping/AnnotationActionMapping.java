package com.foxinmy.weixin4j.mp.mapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.dom4j.DocumentException;

import com.foxinmy.weixin4j.mp.action.WeixinAction;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;
import com.foxinmy.weixin4j.util.ClassUtil;

/**
 * 注解实现的Mapping
 * 
 * @className AnnotationActionMapping
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.mp.mapping.Action
 */
public class AnnotationActionMapping extends AbstractActionMapping {
	private final Map<String, WeixinAction> actionMap;

	public AnnotationActionMapping() {
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

	public WeixinAction getAction(String xmlMsg) throws DocumentException {
		String key = getMappingKey(xmlMsg);
		return actionMap.get(key);
	}
}
