package com.foxinmy.weixin4j.handler;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.WeixinResponse;

/**
 * 微信消息处理器
 * 
 * @className WeixinMessageHandler
 * @author jy
 * @date 2015年5月7日
 * @since JDK 1.7
 * @see
 */
public interface WeixinMessageHandler {

	/**
	 * 能否处理请求
	 * 
	 * @param request
	 *            微信请求
	 * @param message
	 *            微信消息
	 * @return
	 */
	public boolean canHandle(WeixinRequest request, String message)
			throws WeixinException;

	/**
	 * 处理请求
	 * 
	 * @param request
	 *            微信请求
	 * @param message
	 *            微信消息
	 * @return
	 */
	public WeixinResponse doHandle(WeixinRequest request, String message)
			throws WeixinException;
}
