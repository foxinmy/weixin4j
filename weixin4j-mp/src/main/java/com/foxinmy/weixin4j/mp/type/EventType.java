package com.foxinmy.weixin4j.mp.type;

/**
 * 公众号事件类型
 * 
 * @className EventType
 * @author jy
 * @date 2014年9月30日
 * @since JDK 1.7
 * @see
 */
public enum EventType {
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
	 * @see com.foxinmy.weixin4j.mp.event.BatchjobresultMessage
	 */
	batch_job_result;
}
