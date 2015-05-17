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

/**
 * 微信消息匹配
 * 
 * @className WeixinMessageMatcher
 * @author jy
 * @date 2015年5月17日
 * @since JDK 1.7
 * @see
 */
public class WeixinMessageMatcher {

	private final InternalLogger logger = InternalLoggerFactory
			.getInstance(getClass());

	public static final String MESSAGEKEY_MP_TAG = "mp";
	public static final String MESSAGEKEY_SEPARATOR = ":";
	public static final String MESSAGEKEY_MP_SEPARATOR = MESSAGEKEY_MP_TAG
			+ MESSAGEKEY_SEPARATOR;
	public static final String MESSAGEKEY_QY_TAG = "qy";
	public static final String MESSAGEKEY_QY_SEPARATOR = MESSAGEKEY_QY_TAG
			+ MESSAGEKEY_SEPARATOR;

	private final Map<String, Class<?>> key2ClassMap;
	private final Map<Class<?>, String> class2KeyMap;

	public WeixinMessageMatcher() {
		key2ClassMap = new HashMap<String, Class<?>>();
		class2KeyMap = new HashMap<Class<?>, String>();
		init0();
		init1();
		init2();
		init3();
		logger.info("detected message for events: {}", key2ClassMap.keySet());
	}

	private void init0() {
		// /////////////////////////////////////////////////
		/******************** 普通消息 ********************/
		// /////////////////////////////////////////////////
		String messageKey = MessageType.text.name();
		Class<?> clazz = TextMessage.class;
		regist(messageKey, clazz);
		messageKey = MessageType.image.name();
		clazz = ImageMessage.class;
		regist(messageKey, clazz);
		messageKey = MessageType.voice.name();
		clazz = VoiceMessage.class;
		regist(messageKey, clazz);
		messageKey = MessageType.video.name();
		clazz = VideoMessage.class;
		regist(messageKey, clazz);
		messageKey = MessageType.shortvideo.name();
		regist(messageKey, clazz);
		messageKey = MessageType.location.name();
		clazz = LocationMessage.class;
		regist(messageKey, clazz);
	}

	private void init1() {
		// /////////////////////////////////////////////////
		/******************** 事件消息 ********************/
		// /////////////////////////////////////////////////
		for (EventType eventType : new EventType[] { EventType.subscribe,
				EventType.unsubscribe }) {
			regist(mpEventMessagKey(eventType),
					com.foxinmy.weixin4j.mp.event.ScribeEventMessage.class);
		}
		for (EventType eventType : new EventType[] { EventType.subscribe,
				EventType.unsubscribe }) {
			regist(qyEventMessagKey(eventType),
					com.foxinmy.weixin4j.qy.event.ScribeEventMessage.class);
		}
		Class<?> clazz = LocationEventMessage.class;
		regist(mpEventMessagKey(EventType.location), clazz);
		regist(qyEventMessagKey(EventType.location), clazz);
		for (EventType eventType : new EventType[] { EventType.click,
				EventType.view }) {
			clazz = com.foxinmy.weixin4j.message.event.MenuEventMessage.class;
			regist(mpEventMessagKey(eventType), clazz);
			regist(qyEventMessagKey(eventType), clazz);
		}
		for (EventType eventType : new EventType[] { EventType.scancode_push,
				EventType.scancode_waitmsg }) {
			clazz = com.foxinmy.weixin4j.message.event.MenuScanEventMessage.class;
			regist(mpEventMessagKey(eventType), clazz);
			regist(qyEventMessagKey(eventType), clazz);
		}
		for (EventType eventType : new EventType[] { EventType.pic_sysphoto,
				EventType.pic_photo_or_album, EventType.pic_weixin }) {
			clazz = com.foxinmy.weixin4j.message.event.MenuPhotoEventMessage.class;
			regist(mpEventMessagKey(eventType), clazz);
			regist(qyEventMessagKey(eventType), clazz);
		}
		clazz = com.foxinmy.weixin4j.message.event.MenuLocationEventMessage.class;
		regist(mpEventMessagKey(EventType.location_select), clazz);
		regist(qyEventMessagKey(EventType.location_select), clazz);
	}

	private void init2() {
		// /////////////////////////////////////////////////
		/******************** 公众平台事件消息 ********************/
		// /////////////////////////////////////////////////
		regist(mpEventMessagKey(EventType.scan), ScanEventMessage.class);
		regist(mpEventMessagKey(EventType.masssendjobfinish),
				MassEventMessage.class);
		regist(mpEventMessagKey(EventType.templatesendjobfinish),
				TemplatesendjobfinishMessage.class);
		regist(mpEventMessagKey(EventType.kf_create_session),
				KfCreateEventMessage.class);
		regist(mpEventMessagKey(EventType.kf_close_session),
				KfCloseEventMessage.class);
		regist(mpEventMessagKey(EventType.kf_switch_session),
				KfSwitchEventMessage.class);
	}

	private void init3() {
		// /////////////////////////////////////////////////
		/******************** 企业号事件消息 ********************/
		// /////////////////////////////////////////////////
		regist(qyEventMessagKey(EventType.batch_job_result),
				BatchjobresultMessage.class);
		regist(qyEventMessagKey(EventType.enter_agent),
				EnterAgentEventMessage.class);
	}

	/**
	 * 公众平台事件消息的唯一messageKey
	 * 
	 * @param eventType
	 * @return
	 */
	protected String mpEventMessagKey(EventType eventType) {
		return String.format("%s%s%s%s", MESSAGEKEY_MP_SEPARATOR,
				MessageType.event.name(), MESSAGEKEY_SEPARATOR,
				eventType.name());
	}

	/**
	 * 企业号事件消息的唯一messageKey
	 * 
	 * @param eventType
	 * @return
	 */
	protected String qyEventMessagKey(EventType eventType) {
		return String.format("%s%s%s%s", MESSAGEKEY_QY_SEPARATOR,
				MessageType.event.name(), MESSAGEKEY_SEPARATOR,
				eventType.name());
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
