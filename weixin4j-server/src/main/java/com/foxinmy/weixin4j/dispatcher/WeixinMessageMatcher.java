package com.foxinmy.weixin4j.dispatcher;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.HashMap;
import java.util.Map;

import com.foxinmy.weixin4j.message.ImageMessage;
import com.foxinmy.weixin4j.message.LocationMessage;
import com.foxinmy.weixin4j.message.TextMessage;
import com.foxinmy.weixin4j.message.VideoMessage;
import com.foxinmy.weixin4j.message.VoiceMessage;
import com.foxinmy.weixin4j.message.event.LocationEventMessage;
import com.foxinmy.weixin4j.messagekey.WeixinMessageKeyDefiner;
import com.foxinmy.weixin4j.mp.event.KfCloseEventMessage;
import com.foxinmy.weixin4j.mp.event.KfCreateEventMessage;
import com.foxinmy.weixin4j.mp.event.KfSwitchEventMessage;
import com.foxinmy.weixin4j.mp.event.MassEventMessage;
import com.foxinmy.weixin4j.mp.event.ScanEventMessage;
import com.foxinmy.weixin4j.mp.event.TemplatesendjobfinishMessage;
import com.foxinmy.weixin4j.qy.event.BatchjobresultMessage;
import com.foxinmy.weixin4j.qy.event.EnterAgentEventMessage;
import com.foxinmy.weixin4j.type.AccountType;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 微信消息匹配(不够优雅,待改进)
 * 
 * @className WeixinMessageMatcher
 * @author jy
 * @date 2015年5月17日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.request.WeixinMessage
 * @see com.foxinmy.weixin4j.messagekey.WeixinMessageKeyDefiner
 */
public class WeixinMessageMatcher {

	private final InternalLogger logger = InternalLoggerFactory
			.getInstance(getClass());

	private final Map<String, Class<?>> key2ClassMap;
	private final Map<Class<?>, String> class2KeyMap;

	private final WeixinMessageKeyDefiner messageKeyDefiner;

	public WeixinMessageMatcher(WeixinMessageKeyDefiner messageKeyDefiner) {
		this.messageKeyDefiner = messageKeyDefiner;
		key2ClassMap = new HashMap<String, Class<?>>();
		class2KeyMap = new HashMap<Class<?>, String>();
		init0();
		init1();
		init2();
		init3();
		logger.info("detected message for events: {}", key2ClassMap.keySet());
	}

	public WeixinMessageKeyDefiner getMessageKeyDefiner() {
		return messageKeyDefiner;
	}

	private String messageKey(MessageType messageType) {
		return messageKeyDefiner.defineMessageKey(messageType.name(), null,
				null);
	}

	private String mpEventMessageKey(EventType eventType) {
		return messageKeyDefiner.defineMessageKey(MessageType.event.name(),
				eventType.name(), AccountType.MP);
	}

	private String qyEventMessageKey(EventType eventType) {
		return messageKeyDefiner.defineMessageKey(MessageType.event.name(),
				eventType.name(), AccountType.QY);
	}

	private void init0() {
		// /////////////////////////////////////////////////
		/******************** 普通消息 ********************/
		// /////////////////////////////////////////////////
		regist(messageKey(MessageType.text), TextMessage.class);
		regist(messageKey(MessageType.image), ImageMessage.class);
		regist(messageKey(MessageType.voice), VoiceMessage.class);
		regist(messageKey(MessageType.video), VideoMessage.class);
		regist(messageKey(MessageType.shortvideo), VideoMessage.class);
		regist(messageKey(MessageType.location), LocationMessage.class);
	}

	private void init1() {
		// /////////////////////////////////////////////////
		/******************** 事件消息 ********************/
		// /////////////////////////////////////////////////
		for (EventType eventType : new EventType[] { EventType.subscribe,
				EventType.unsubscribe }) {
			regist(mpEventMessageKey(eventType),
					com.foxinmy.weixin4j.mp.event.ScribeEventMessage.class);
		}
		for (EventType eventType : new EventType[] { EventType.subscribe,
				EventType.unsubscribe }) {
			regist(qyEventMessageKey(eventType),
					com.foxinmy.weixin4j.qy.event.ScribeEventMessage.class);
		}
		Class<?> clazz = LocationEventMessage.class;
		regist(mpEventMessageKey(EventType.location), clazz);
		regist(qyEventMessageKey(EventType.location), clazz);
		for (EventType eventType : new EventType[] { EventType.click,
				EventType.view }) {
			clazz = com.foxinmy.weixin4j.message.event.MenuEventMessage.class;
			regist(mpEventMessageKey(eventType), clazz);
			regist(qyEventMessageKey(eventType), clazz);
		}
		for (EventType eventType : new EventType[] { EventType.scancode_push,
				EventType.scancode_waitmsg }) {
			clazz = com.foxinmy.weixin4j.message.event.MenuScanEventMessage.class;
			regist(mpEventMessageKey(eventType), clazz);
			regist(qyEventMessageKey(eventType), clazz);
		}
		for (EventType eventType : new EventType[] { EventType.pic_sysphoto,
				EventType.pic_photo_or_album, EventType.pic_weixin }) {
			clazz = com.foxinmy.weixin4j.message.event.MenuPhotoEventMessage.class;
			regist(mpEventMessageKey(eventType), clazz);
			regist(qyEventMessageKey(eventType), clazz);
		}
		clazz = com.foxinmy.weixin4j.message.event.MenuLocationEventMessage.class;
		regist(mpEventMessageKey(EventType.location_select), clazz);
		regist(qyEventMessageKey(EventType.location_select), clazz);
	}

	private void init2() {
		// /////////////////////////////////////////////////
		/******************** 公众平台事件消息 ********************/
		// /////////////////////////////////////////////////
		regist(mpEventMessageKey(EventType.scan), ScanEventMessage.class);
		regist(mpEventMessageKey(EventType.masssendjobfinish),
				MassEventMessage.class);
		regist(mpEventMessageKey(EventType.templatesendjobfinish),
				TemplatesendjobfinishMessage.class);
		regist(mpEventMessageKey(EventType.kf_create_session),
				KfCreateEventMessage.class);
		regist(mpEventMessageKey(EventType.kf_close_session),
				KfCloseEventMessage.class);
		regist(mpEventMessageKey(EventType.kf_switch_session),
				KfSwitchEventMessage.class);
	}

	private void init3() {
		// /////////////////////////////////////////////////
		/******************** 企业号事件消息 ********************/
		// /////////////////////////////////////////////////
		regist(qyEventMessageKey(EventType.batch_job_result),
				BatchjobresultMessage.class);
		regist(qyEventMessageKey(EventType.enter_agent),
				EnterAgentEventMessage.class);
	}

	/**
	 * 注册一个消息类型
	 * 
	 * @param messageKey
	 *            消息的key
	 * @param clazz
	 *            消息类型
	 */
	public void regist(String messageKey, Class<?> clazz) {
		key2ClassMap.put(messageKey, clazz);
		class2KeyMap.put(clazz, messageKey);
	}

	/**
	 * 匹配到消息
	 * 
	 * @param keyOrClass
	 *            消息key或者消息类型
	 * @return 匹配结果
	 */
	public boolean match(Object keyOrClass) {
		return key2ClassMap.containsKey(keyOrClass)
				|| class2KeyMap.containsKey(keyOrClass);
	}

	/**
	 * 消息key找到消息类型
	 * 
	 * @param messageKey
	 *            消息key
	 * @return 消息类型
	 */
	public Class<?> find(String messageKey) {
		return key2ClassMap.get(messageKey);
	}
}
