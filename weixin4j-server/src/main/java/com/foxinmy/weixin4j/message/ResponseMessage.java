package com.foxinmy.weixin4j.message;

import java.io.Serializable;

import com.foxinmy.weixin4j.response.WeixinResponse;

public class ResponseMessage implements Serializable {

	private static final long serialVersionUID = -2822272237544040042L;

	private WeixinMessage message;
	private WeixinResponse response;

	public ResponseMessage(WeixinMessage message, WeixinResponse response) {
		this.message = message;
		this.response = response;
	}

	public WeixinMessage getMessage() {
		return message;
	}

	public WeixinResponse getResponse() {
		return response;
	}

	public String toXml() {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<xml>");
		xmlContent.append(String.format(
				"<ToUserName><![CDATA[%s]]></ToUserName>",
				message.getFromUserName()));
		xmlContent.append(String.format(
				"<FromUserName><![CDATA[%s]]></FromUserName>",
				message.getToUserName()));
		xmlContent.append(String.format(
				"<CreateTime><![CDATA[%d]]></CreateTime>",
				System.currentTimeMillis() / 1000l));
		xmlContent.append(String.format("<MsgType><![CDATA[%s]]></MsgType>",
				response.getMsgType()));
		xmlContent.append(response.toContent());
		xmlContent.append("</xml>");
		return xmlContent.toString();
	}

	@Override
	public String toString() {
		return "ResponseMessage [message=" + message + ", response=" + response
				+ "]";
	}
}
