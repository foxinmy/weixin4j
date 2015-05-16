package com.foxinmy.weixin4j.dispatcher;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foxinmy.weixin4j.message.ImageMessage;
import com.foxinmy.weixin4j.message.LocationMessage;
import com.foxinmy.weixin4j.message.TextMessage;
import com.foxinmy.weixin4j.message.VideoMessage;
import com.foxinmy.weixin4j.message.VoiceMessage;
import com.foxinmy.weixin4j.message.event.LocationEventMessage;
import com.foxinmy.weixin4j.mp.event.KfCloseEventMessage;
import com.foxinmy.weixin4j.mp.event.KfCreateEventMessage;
import com.foxinmy.weixin4j.mp.event.KfSwitchEventMessage;
import com.foxinmy.weixin4j.mp.event.MassEventMessage;
import com.foxinmy.weixin4j.mp.event.ScanEventMessage;
import com.foxinmy.weixin4j.mp.event.TemplatesendjobfinishMessage;
import com.foxinmy.weixin4j.qy.event.BatchjobresultMessage;
import com.foxinmy.weixin4j.qy.event.EnterAgentEventMessage;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

public class WeixinMessageMatcher {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final String MP_TAG = "mp";
	private final String QY_TAG = "qy";

	private final Map<String, Class<?>> key2ClassMap;
	private final Map<Class<?>, String> class2KeyMap;

	public WeixinMessageMatcher() {
		key2ClassMap = new HashMap<String, Class<?>>();
		class2KeyMap = new HashMap<Class<?>, String>();
		init0();
		init1();
		init2();
		init3();
		log.info("detected message for events: {}", key2ClassMap.keySet());
	}

	private void init0() {
		// /////////////////////////////////////////////////
		/******************** 普通消息 ********************/
		// /////////////////////////////////////////////////
		String uniqueKey = MessageType.text.name();
		Class<?> clazz = TextMessage.class;
		regist(uniqueKey, clazz);
		uniqueKey = MessageType.image.name();
		clazz = ImageMessage.class;
		regist(uniqueKey, clazz);
		uniqueKey = MessageType.voice.name();
		clazz = VoiceMessage.class;
		regist(uniqueKey, clazz);
		uniqueKey = MessageType.video.name();
		clazz = VideoMessage.class;
		regist(uniqueKey, clazz);
		uniqueKey = MessageType.shortvideo.name();
		regist(uniqueKey, clazz);
		uniqueKey = MessageType.location.name();
		clazz = LocationMessage.class;
		regist(uniqueKey, clazz);
	}

	private void init1() {
		// /////////////////////////////////////////////////
		/******************** 事件消息 ********************/
		// /////////////////////////////////////////////////
		String uniqueKey;
		Class<?> clazz;
		for (EventType eventType : new EventType[] { EventType.subscribe,
				EventType.unsubscribe }) {
			uniqueKey = String.format("%s:%s", MessageType.event.name(),
					eventType.name());
			clazz = com.foxinmy.weixin4j.mp.event.ScribeEventMessage.class;
			regist(String.format("%s:%s", MP_TAG, uniqueKey), clazz);
		}
		for (EventType eventType : new EventType[] { EventType.subscribe,
				EventType.unsubscribe }) {
			uniqueKey = String.format("%s:%s", MessageType.event.name(),
					eventType.name());
			clazz = com.foxinmy.weixin4j.qy.event.ScribeEventMessage.class;
			regist(String.format("%s:%s", QY_TAG, uniqueKey), clazz);
		}
		uniqueKey = String.format("%s:%s", MessageType.event.name(),
				EventType.location.name());
		clazz = LocationEventMessage.class;
		regist(uniqueKey, clazz);
		for (EventType eventType : new EventType[] { EventType.click,
				EventType.view }) {
			uniqueKey = String.format("%s:%s", MessageType.event.name(),
					eventType.name());
			clazz = com.foxinmy.weixin4j.message.event.MenuEventMessage.class;
			regist(uniqueKey, clazz);
		}
		for (EventType eventType : new EventType[] { EventType.scancode_push,
				EventType.scancode_waitmsg }) {
			uniqueKey = String.format("%s:%s", MessageType.event.name(),
					eventType.name());
			clazz = com.foxinmy.weixin4j.message.event.MenuScanEventMessage.class;
			regist(uniqueKey, clazz);
		}
		for (EventType eventType : new EventType[] { EventType.pic_sysphoto,
				EventType.pic_photo_or_album, EventType.pic_weixin }) {
			uniqueKey = String.format("%s:%s", MessageType.event.name(),
					eventType.name());
			clazz = com.foxinmy.weixin4j.message.event.MenuPhotoEventMessage.class;
			regist(uniqueKey, clazz);
		}
		uniqueKey = String.format("%s:%s", MessageType.event.name(),
				EventType.location_select.name());
		clazz = com.foxinmy.weixin4j.message.event.MenuLocationEventMessage.class;
		regist(uniqueKey, clazz);
	}

	private void init2() {
		// /////////////////////////////////////////////////
		/******************** 公众平台事件消息 ********************/
		// /////////////////////////////////////////////////
		String uniqueKey = String.format("%s:%s:%s", MP_TAG,
				MessageType.event.name(), EventType.scan.name());
		Class<?> clazz = ScanEventMessage.class;
		regist(uniqueKey, clazz);
		uniqueKey = String.format("%s:%s:%s", MP_TAG, MessageType.event.name(),
				EventType.masssendjobfinish.name());
		clazz = MassEventMessage.class;
		regist(uniqueKey, clazz);
		uniqueKey = String.format("%s:%s:%s", MP_TAG, MessageType.event.name(),
				EventType.templatesendjobfinish.name());
		clazz = TemplatesendjobfinishMessage.class;
		regist(uniqueKey, clazz);
		uniqueKey = String.format("%s:%s:%s", MP_TAG, MessageType.event.name(),
				EventType.kf_create_session.name());
		clazz = KfCreateEventMessage.class;
		regist(uniqueKey, clazz);
		uniqueKey = String.format("%s:%s:%s", MP_TAG, MessageType.event.name(),
				EventType.kf_close_session.name());
		clazz = KfCloseEventMessage.class;
		regist(uniqueKey, clazz);
		uniqueKey = String.format("%s:%s:%s", MP_TAG, MessageType.event.name(),
				EventType.kf_switch_session.name());
		clazz = KfSwitchEventMessage.class;
		regist(uniqueKey, clazz);
	}

	private void init3() {
		// /////////////////////////////////////////////////
		/******************** 企业号事件消息 ********************/
		// /////////////////////////////////////////////////
		String uniqueKey = String.format("%s:%s:%s", QY_TAG,
				MessageType.event.name(), EventType.batch_job_result.name());
		Class<?> clazz = BatchjobresultMessage.class;
		regist(uniqueKey, clazz);
		uniqueKey = String.format("%s:%s:%s", QY_TAG, MessageType.event.name(),
				EventType.enter_agent.name());
		clazz = EnterAgentEventMessage.class;
		regist(uniqueKey, clazz);
	}

	public void regist(String uniqueKey, Class<?> clazz) {
		key2ClassMap.put(uniqueKey, clazz);
		class2KeyMap.put(clazz, uniqueKey);
	}

	public boolean match(Object keyOrClass) {
		return key2ClassMap.containsKey(keyOrClass)
				|| class2KeyMap.containsKey(keyOrClass);
	}

	public Class<?> find(String uniqueKey) {
		return key2ClassMap.get(uniqueKey);
	}
}
