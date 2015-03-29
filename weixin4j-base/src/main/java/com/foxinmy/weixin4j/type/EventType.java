package com.foxinmy.weixin4j.type;

import com.foxinmy.weixin4j.msg.event.EnterAgentEventMessage;
import com.foxinmy.weixin4j.msg.event.EventMessage;
import com.foxinmy.weixin4j.msg.event.KfCloseEventMessage;
import com.foxinmy.weixin4j.msg.event.KfCreateEventMessage;
import com.foxinmy.weixin4j.msg.event.KfSwitchEventMessage;
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
	/**
	 * 关注事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.ScribeEventMessage
	 */
	subscribe(ScribeEventMessage.class),
	/**
	 * 取消关注事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.ScribeEventMessage
	 */
	unsubscribe(ScribeEventMessage.class),
	/**
	 * 二维码扫描事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.ScanEventMessage
	 */
	scan(ScanEventMessage.class),
	/**
	 * 上报地理位置事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.LocationEventMessage
	 */
	location(LocationEventMessage.class),
	/**
	 * 菜单扫描事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.menu.MenuScanEventMessage
	 */
	scancode_push(MenuScanEventMessage.class),
	/**
	 * 菜单点击关键字事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.menu.MenuEventMessage
	 */
	view(MenuEventMessage.class),
	/**
	 * 菜单点击链接事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.menu.MenuEventMessage
	 */
	click(MenuEventMessage.class),
	/**
	 * 菜单扫描并调出等待界面事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.menu.MenuScanEventMessage
	 */
	scancode_waitmsg(MenuScanEventMessage.class),
	/**
	 * 菜单弹出拍照事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.menu.MenuPhotoEventMessage
	 */
	pic_sysphoto(MenuPhotoEventMessage.class),
	/**
	 * 菜单弹出发图事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.menu.MenuPhotoEventMessage
	 */
	pic_photo_or_album(MenuPhotoEventMessage.class),
	/**
	 * 菜单弹出发图事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.menu.MenuPhotoEventMessage
	 */
	pic_weixin(MenuPhotoEventMessage.class),
	/**
	 * 菜单发送地理位置事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.menu.MenuLocationEventMessage
	 */
	location_select(MenuLocationEventMessage.class),
	/**
	 * 群发消息事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.MassEventMessage
	 */
	masssendjobfinish(MassEventMessage.class),
	/**
	 * 模板消息事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.TemplatesendjobfinishMessage
	 */
	templatesendjobfinish(TemplatesendjobfinishMessage.class),
	/**
	 * 进入企业号应用事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.EnterAgentEventMessage
	 */
	enter_agent(EnterAgentEventMessage.class),
	/**
	 * 客服接入会话事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.KfCreateEventMessage
	 */
	kf_create_session(KfCreateEventMessage.class),
	/**
	 * 客服关闭会话事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.KfCloseEventMessage
	 */
	kf_close_session(KfCloseEventMessage.class),
	/**
	 * 客服转接会话事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.KfSwitchEventMessage
	 */
	kf_switch_session(KfSwitchEventMessage.class);

	private Class<? extends EventMessage> eventClass;

	EventType(Class<? extends EventMessage> eventClass) {
		this.eventClass = eventClass;
	}

	public Class<? extends EventMessage> getEventClass() {
		return eventClass;
	}
}
