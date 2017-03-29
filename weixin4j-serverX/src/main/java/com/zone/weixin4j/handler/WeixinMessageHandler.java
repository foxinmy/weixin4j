package com.zone.weixin4j.handler;

import com.zone.weixin4j.exception.WeixinException;
import com.zone.weixin4j.request.WeixinMessage;
import com.zone.weixin4j.request.WeixinRequest;
import com.zone.weixin4j.response.WeixinResponse;

import java.util.Set;

/**
 * 微信消息处理器
 * 
 * @className WeixinMessageHandler
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月7日
 * @since JDK 1.6
 * @see MessageHandlerAdapter
 * @see MultipleMessageHandlerAdapter
 */
public interface WeixinMessageHandler {

	/**
	 * 能否处理请求
	 * 
	 * @param request
	 *            微信请求
	 * @param message
	 *            微信消息
	 * @param nodeNames
	 *            节点名称集合
	 * @return true则执行doHandle
	 */
	public boolean canHandle(WeixinRequest request, WeixinMessage message,
                             Set<String> nodeNames) throws WeixinException;

	/**
	 * 处理请求
	 * 
	 * @param request
	 *            微信请求
	 * @param message
	 *            微信消息
	 * @param nodeNames
	 *            节点名称集合
	 * @return 回复内容
	 */
	public WeixinResponse doHandle(WeixinRequest request, WeixinMessage message,
                                   Set<String> nodeNames) throws WeixinException;

	/**
	 * 用于匹配到多个MessageHandler时权重降序排列,数字越大优先级越高
	 * 
	 * @return 权重
	 */
	public int weight();
}
