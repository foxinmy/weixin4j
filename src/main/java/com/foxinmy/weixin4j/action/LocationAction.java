package com.foxinmy.weixin4j.action;

import com.foxinmy.weixin4j.msg.in.LocationMessage;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 地理位置响应
 * 
 * @className LocationAction
 * @author jy
 * @date 2014年10月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.in.LocationMessage
 */
@Action(msgType = MessageType.location)
public class LocationAction extends DebugAction<LocationMessage> {

}
