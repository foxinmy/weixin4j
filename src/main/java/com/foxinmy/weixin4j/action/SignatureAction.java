package com.foxinmy.weixin4j.action;

import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import com.foxinmy.weixin4j.type.MessageType;
import com.foxinmy.weixin4j.util.ConfigUtil;
import com.foxinmy.weixin4j.util.MessageUtil;

/**
 * 用于校验消息安全性
 * 
 * @className SignatureAction
 * @author jy
 * @date 2014年10月24日
 * @since JDK 1.7
 * @see
 */
@Action(msgType = MessageType.signature)
public class SignatureAction implements WeixinAction {

	@Override
	public String execute(String uri) throws DocumentException {
		String[] paths = uri.split("\\?");
		if (paths == null || paths.length < 2) {
			return "";
		}
		QueryStringDecoder queryDecoder = new QueryStringDecoder(paths[1],
				false);
		Map<String, List<String>> parameters = queryDecoder.parameters();
		String echostr = parameters.containsKey("echostr") ? parameters.get(
				"echostr").get(0) : null;
		String timestamp = parameters.containsKey("timestamp") ? parameters
				.get("timestamp").get(0) : null;
		String nonce = parameters.containsKey("nonce") ? parameters
				.get("nonce").get(0) : null;
		String signature = parameters.containsKey("signature") ? parameters
				.get("signature").get(0) : null;
		String token = ConfigUtil.getValue("app_token");
		return MessageUtil.signature(token, echostr, timestamp, nonce,
				signature);
	}
}
