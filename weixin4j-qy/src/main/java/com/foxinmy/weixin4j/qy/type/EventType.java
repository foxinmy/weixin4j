package com.foxinmy.weixin4j.qy.type;

/**
 * 企业号事件类型
 * 
 * @className EventType
 * @author jy
 * @date 2014年9月30日
 * @since JDK 1.7
 * @see
 */
public enum EventType {
	/**
	 * 进入企业号应用事件
	 * 
	 * @see com.foxinmy.weixin4j.qy.event.EnterAgentEventMessage
	 */
	enter_agent,
	/**
	 * 异步任务完成事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.BatchjobresultMessage
	 */
	batch_job_result;
}
