package com.zone.weixin4j.dispatcher;

import com.zone.weixin4j.message.*;
import com.zone.weixin4j.message.event.*;
import com.zone.weixin4j.mp.event.*;
import com.zone.weixin4j.qy.event.BatchjobresultMessage;
import com.zone.weixin4j.qy.event.EnterAgentEventMessage;
import com.zone.weixin4j.request.WeixinMessage;
import com.zone.weixin4j.type.AccountType;
import com.zone.weixin4j.type.EventType;
import com.zone.weixin4j.type.MessageType;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认MessageMatcher实现(可以改进)
 * 
 * @className DefaultMessageMatcher
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月10日
 * @since JDK 1.6
 * @see
 */
public class DefaultMessageMatcher implements WeixinMessageMatcher {

	private final Map<WeixinMessageKey, Class<? extends WeixinMessage>> messageClassMap;

	public DefaultMessageMatcher() {
		messageClassMap = new HashMap<WeixinMessageKey, Class<? extends WeixinMessage>>();
		initMessageClass();
	}

	private void initMessageClass() {
		// /////////////////////////////////////////////////
		/******************** 普通消息 ********************/
		// /////////////////////////////////////////////////
		initGeneralMessageClass();
		// /////////////////////////////////////////////////
		/******************** 事件消息 ********************/
		// /////////////////////////////////////////////////
		initEventMessageClass();
		// /////////////////////////////////////////////////
		/***************** 公众平台事件消息 *****************/
		// /////////////////////////////////////////////////
		initMpEventMessageClass();
		// /////////////////////////////////////////////////
		/****************** 企业号事件消息 ******************/
		// /////////////////////////////////////////////////
		initQyEventMessageClass();
	}

	private void initGeneralMessageClass() {
		for (AccountType accountType : AccountType.values()) {
			messageClassMap.put(new WeixinMessageKey(MessageType.text.name(),
					null, accountType), TextMessage.class);
			messageClassMap.put(new WeixinMessageKey(MessageType.image.name(),
					null, accountType), ImageMessage.class);
			messageClassMap.put(new WeixinMessageKey(MessageType.voice.name(),
					null, accountType), VoiceMessage.class);
			messageClassMap.put(new WeixinMessageKey(MessageType.video.name(),
					null, accountType), VideoMessage.class);
			messageClassMap.put(
					new WeixinMessageKey(MessageType.shortvideo.name(), null,
							accountType), VideoMessage.class);
			messageClassMap.put(
					new WeixinMessageKey(MessageType.location.name(), null,
							accountType), LocationMessage.class);
			messageClassMap.put(new WeixinMessageKey(MessageType.link.name(),
					null, accountType), LinkMessage.class);
		}
	}

	private void initEventMessageClass() {
		String messageType = MessageType.event.name();
		EventType[] eventTypes = new EventType[] { EventType.subscribe,
				EventType.unsubscribe };
		for (EventType eventType : eventTypes) {
			messageClassMap.put(
					new WeixinMessageKey(messageType, eventType.name(),
							AccountType.MP),
					ScribeEventMessage.class);
		}
		for (EventType eventType : eventTypes) {
			messageClassMap.put(
					new WeixinMessageKey(messageType, eventType.name(),
							AccountType.QY),
					ScribeEventMessage.class);
		}
		for (AccountType accountType : AccountType.values()) {
			messageClassMap.put(new WeixinMessageKey(messageType,
					EventType.location.name(), accountType),
					LocationEventMessage.class);
			messageClassMap.put(new WeixinMessageKey(messageType,
					EventType.location_select.name(), accountType),
					MenuLocationEventMessage.class);
			for (EventType eventType : new EventType[] { EventType.click,
					EventType.view }) {
				messageClassMap.put(
						new WeixinMessageKey(messageType, eventType.name(),
								accountType), MenuEventMessage.class);
			}
			for (EventType eventType : new EventType[] {
					EventType.scancode_push, EventType.scancode_waitmsg }) {
				messageClassMap.put(
						new WeixinMessageKey(messageType, eventType.name(),
								accountType), MenuScanEventMessage.class);
			}
			for (EventType eventType : new EventType[] {
					EventType.pic_sysphoto, EventType.pic_photo_or_album,
					EventType.pic_weixin }) {
				messageClassMap.put(
						new WeixinMessageKey(messageType, eventType.name(),
								accountType), MenuPhotoEventMessage.class);
			}
		}
	}

	private void initMpEventMessageClass() {
		String messageType = MessageType.event.name();
		AccountType accountType = AccountType.MP;
		messageClassMap.put(
				new WeixinMessageKey(messageType, EventType.scan.name(),
						accountType),
				ScanEventMessage.class);
		messageClassMap.put(new WeixinMessageKey(messageType,
				EventType.masssendjobfinish.name(), accountType),
				MassEventMessage.class);
		messageClassMap.put(new WeixinMessageKey(messageType,
				EventType.templatesendjobfinish.name(), accountType),
				TemplatesendjobfinishMessage.class);
		messageClassMap.put(new WeixinMessageKey(messageType,
				EventType.kf_create_session.name(), accountType),
				KfCreateEventMessage.class);
		messageClassMap.put(new WeixinMessageKey(messageType,
				EventType.kf_close_session.name(), accountType),
				KfCloseEventMessage.class);
		messageClassMap.put(new WeixinMessageKey(messageType,
				EventType.kf_switch_session.name(), accountType),
				KfSwitchEventMessage.class);
		EventType[] eventTypes = new EventType[] {
				EventType.qualification_verify_success,
				EventType.naming_verify_success, EventType.annual_renew,
				EventType.verify_expired };
		for (EventType eventType : eventTypes) {
			messageClassMap.put(
					new WeixinMessageKey(messageType, eventType.name(),
							accountType), VerifyExpireEventMessage.class);
		}
		eventTypes = new EventType[] { EventType.qualification_verify_success,
				EventType.naming_verify_fail };
		for (EventType eventType : eventTypes) {
			messageClassMap.put(
					new WeixinMessageKey(messageType, eventType.name(),
							accountType), VerifyFailEventMessage.class);
		}
	}

	private void initQyEventMessageClass() {
		String messageType = MessageType.event.name();
		messageClassMap.put(new WeixinMessageKey(messageType,
				EventType.batch_job_result.name(), AccountType.QY),
				BatchjobresultMessage.class);
		messageClassMap.put(new WeixinMessageKey(messageType,
				EventType.enter_agent.name(), AccountType.QY),
				EnterAgentEventMessage.class);
		//messageClassMap.put(new WeixinMessageKey(messageType,
			//	EventType.suite.name(), AccountType.QY),
				//SuiteMessage.class);
	}

	@Override
	public Class<? extends WeixinMessage> match(WeixinMessageKey messageKey) {
		return messageClassMap.get(messageKey);
	}

	@Override
	public void regist(WeixinMessageKey messageKey,
			Class<? extends WeixinMessage> messageClass) {
		Class<?> clazz = match(messageKey);
		if (clazz != null) {
			throw new IllegalArgumentException("duplicate messagekey '"
					+ messageKey + "' define for " + clazz);
		}
		messageClassMap.put(messageKey, messageClass);
	}
}