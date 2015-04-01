package com.foxinmy.weixin4j.qy.action.event;

import com.foxinmy.weixin4j.action.DebugAction;
import com.foxinmy.weixin4j.action.mapping.ActionAnnotation;
import com.foxinmy.weixin4j.msg.event.BatchjobresultMessage;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 异步任务完成事件
 * 
 * @className BatchJobAction
 * @author jy
 * @date 2015年3月31日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.event.BatchjobresultMessage
 */
@ActionAnnotation(msgType = MessageType.event, eventType = { EventType.batch_job_result })
public class BatchJobAction extends DebugAction<BatchjobresultMessage> {

}
