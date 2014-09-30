package com.foxinmy.weixin4j.type;

import com.foxinmy.weixin4j.msg.event.EventMessage;
import com.foxinmy.weixin4j.msg.event.LocationEventMessage;
import com.foxinmy.weixin4j.msg.event.MassEventMessage;
import com.foxinmy.weixin4j.msg.event.ScanEventMessage;
import com.foxinmy.weixin4j.msg.event.ScribeEventMessage;
import com.foxinmy.weixin4j.msg.event.TemplatesendjobfinishMessage;
import com.foxinmy.weixin4j.msg.event.menu.MenuEventMessage;
import com.foxinmy.weixin4j.msg.event.menu.MenuLocationEventMessage;
import com.foxinmy.weixin4j.msg.event.menu.MenuPhotoEventMessage;
import com.foxinmy.weixin4j.msg.event.menu.MenuScanEventMessage;

/**
 * 事件类型
 * 
 * @className EventType
 * @author jy
 * @date 2014年9月30日
 * @since JDK 1.7
 * @see
 */
public enum EventType {
	subscribe(ScribeEventMessage.class), unsubscribe(ScribeEventMessage.class), scan(
			ScanEventMessage.class), scancode_push(MenuScanEventMessage.class), view(
			MenuEventMessage.class), scancode_waitmsg(
			MenuScanEventMessage.class), pic_sysphoto(
			MenuPhotoEventMessage.class), pic_photo_or_album(
			MenuPhotoEventMessage.class), pic_weixin(
			MenuPhotoEventMessage.class), location_select(
			MenuLocationEventMessage.class), click(MenuEventMessage.class), location(
			LocationEventMessage.class), massendjobfinish(
			MassEventMessage.class), templatesendjobfinish(
			TemplatesendjobfinishMessage.class);

	private Class<? extends EventMessage> eventClass;

	EventType(Class<? extends EventMessage> eventClass) {
		this.eventClass = eventClass;
	}

	public Class<? extends EventMessage> getEventClass() {
		return eventClass;
	}
}
