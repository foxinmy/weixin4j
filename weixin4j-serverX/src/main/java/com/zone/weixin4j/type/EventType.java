package com.zone.weixin4j.type;

/**
 * 事件类型
 * 
 * @className EventType
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月30日
 * @since JDK 1.6
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
	 * 菜单点击关键字事件
	 * 
	 * @see com.foxinmy.weixin4j.message.event.MenuEventMessage
	 */
	view,
	/**
	 * 菜单点击链接事件
	 * 
	 * @see com.foxinmy.weixin4j.message.event.MenuEventMessage
	 */
	click,
	/**
	 * 菜单扫描事件
	 * 
	 * @see com.foxinmy.weixin4j.message.event.MenuScanEventMessage
	 */
	scancode_push,
	/**
	 * 菜单扫描并调出等待界面事件
	 * 
	 * @see com.foxinmy.weixin4j.message.event.MenuScanEventMessage
	 */
	scancode_waitmsg,
	/**
	 * 菜单弹出拍照事件
	 * 
	 * @see com.foxinmy.weixin4j.message.event.MenuPhotoEventMessage
	 */
	pic_sysphoto,
	/**
	 * 菜单弹出发图事件
	 * 
	 * @see com.foxinmy.weixin4j.message.event.MenuPhotoEventMessage
	 */
	pic_photo_or_album,
	/**
	 * 菜单弹出发图事件
	 * 
	 * @see com.foxinmy.weixin4j.message.event.MenuPhotoEventMessage
	 */
	pic_weixin,
	/**
	 * 菜单发送地理位置事件
	 * 
	 * @see com.foxinmy.weixin4j.message.event.MenuLocationEventMessage
	 */
	location_select,

	// ------------------------------公众平台特有------------------------------

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
	 * 资质认证成功事件
	 */
	qualification_verify_success,
	/**
	 * 资质认证失败事件
	 */
	qualification_verify_fail,
	/**
	 * 名称认证成功事件
	 */
	naming_verify_success,
	/**
	 * 名称认证失败事件
	 */
	naming_verify_fail,
	/**
	 * 年审通知事件
	 */
	annual_renew,
	/**
	 * 认证过期失效通知
	 */
	verify_expired,

	// ------------------------------企业号特有------------------------------
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
	enter_agent,
	/**
	 * 第三方应用套件消息
	 * @see com.foxinmy.weixin4j.qy.suite.WeixinSuiteMessage
	 */
	suite;
}
