package com.foxinmy.weixin4j.type;

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
	 */
	subscribe,
	/**
	 * 取消关注事件
	 * 
	 */
	unsubscribe,
	/**
	 * 上报地理位置事件
	 * 
	 * @see com.foxinmy.weixin4j.message.event.LocationEventMessage
	 */
	location,
	/**
	 * 菜单扫描事件
	 * 
	 * @see com.foxinmy.weixin4j.message.event.menu.MenuScanEventMessage
	 */
	scancode_push,
	/**
	 * 菜单点击关键字事件
	 * 
	 * @see com.foxinmy.weixin4j.message.event.menu.MenuEventMessage
	 */
	view,
	/**
	 * 菜单点击链接事件
	 * 
	 * @see com.foxinmy.weixin4j.message.event.menu.MenuEventMessage
	 */
	click,
	/**
	 * 菜单扫描并调出等待界面事件
	 * 
	 * @see com.foxinmy.weixin4j.message.event.menu.MenuScanEventMessage
	 */
	scancode_waitmsg,
	/**
	 * 菜单弹出拍照事件
	 * 
	 * @see com.foxinmy.weixin4j.message.event.menu.MenuPhotoEventMessage
	 */
	pic_sysphoto,
	/**
	 * 菜单弹出发图事件
	 * 
	 * @see com.foxinmy.weixin4j.message.event.menu.MenuPhotoEventMessage
	 */
	pic_photo_or_album,
	/**
	 * 菜单弹出发图事件
	 * 
	 * @see com.foxinmy.weixin4j.message.event.menu.MenuPhotoEventMessage
	 */
	pic_weixin,
	/**
	 * 菜单发送地理位置事件
	 * 
	 * @see com.foxinmy.weixin4j.message.event.menu.MenuLocationEventMessage
	 */
	location_select,

	// ------------------------------公众平台特有------------------------------
	//

	/**
	 * 二维码扫描事件
	 * 
	 * @see com.foxinmy.weixin4j.mp.event.ScanEventMessage
	 */
	scan,
	/**
	 * 群发消息事件
	 * 
	 * @see com.foxinmy.weixin4j.mp.event.MassEventMessage
	 */
	masssendjobfinish,
	/**
	 * 模板消息事件
	 * 
	 * @see com.foxinmy.weixin4j.mp.event.TemplatesendjobfinishMessage
	 */
	templatesendjobfinish,
	/**
	 * 客服接入会话事件
	 * 
	 * @see com.foxinmy.weixin4j.mp.event.KfCreateEventMessage
	 */
	kf_create_session,
	/**
	 * 客服关闭会话事件
	 * 
	 * @see com.foxinmy.weixin4j.mp.event.KfCloseEventMessage
	 */
	kf_close_session,
	/**
	 * 客服转接会话事件
	 * 
	 * @see com.foxinmy.weixin4j.mp.event.KfSwitchEventMessage
	 */
	kf_switch_session,
	/**
	 * 异步任务完成事件
	 * 
	 * @see com.foxinmy.weixin4j.qy.event.BatchjobresultMessage
	 */
	batch_job_result,
	/**
	 * 进入企业号应用事件
	 * 
	 * @see com.foxinmy.weixin4j.qy.event.EnterAgentEventMessage
	 */
	enter_agent;
}
