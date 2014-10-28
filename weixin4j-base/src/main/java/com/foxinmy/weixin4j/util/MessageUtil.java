package com.foxinmy.weixin4j.util;

import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foxinmy.weixin4j.msg.BaseMessage;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;
import com.foxinmy.weixin4j.xml.XStream;

public class MessageUtil {

	private final static Logger log = LoggerFactory
			.getLogger(MessageUtil.class);

	/**
	 * 验证微信签名
	 * 
	 * @param token
	 *            开发者填写的token
	 * @param echostr
	 *            随机字符串
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 *            随机数
	 * @param signature
	 *            微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
	 * @return 开发者通过检验signature对请求进行相关校验。若确认此次GET请求来自微信服务器
	 *         请原样返回echostr参数内容，则接入生效 成为开发者成功，否则接入失败
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E5%85%A5%E6%8C%87%E5%8D%97">接入指南</a>
	 */
	public static String signature(String token, String echostr,
			String timestamp, String nonce, String signature) {
		if (StringUtils.isBlank(token)) {
			log.error("signature fail : token is null!");
			return null;
		}
		if (StringUtils.isBlank(echostr) || StringUtils.isBlank(timestamp)
				|| StringUtils.isBlank(nonce)) {
			log.error("signature fail : invalid parameter!");
			return null;
		}
		String _signature = null;
		try {
			String[] a = { token, timestamp, nonce };
			Arrays.sort(a);
			StringBuilder sb = new StringBuilder(3);
			for (String str : a) {
				sb.append(str);
			}
			_signature = DigestUtils.sha1Hex(sb.toString());
		} catch (Exception e) {
			log.error("signature error", e);
		}
		if (signature.equals(_signature)) {
			return echostr;
		} else {
			log.error("signature fail : invalid signature!");
			return null;
		}
	}

	/**
	 * xml消息转换为消息对象
	 * 
	 * @param xmlMsg
	 *            消息字符串
	 * @return 消息对象
	 * @throws DocumentException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%AA%8C%E8%AF%81%E6%B6%88%E6%81%AF%E7%9C%9F%E5%AE%9E%E6%80%A7">验证消息的合法性</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E6%99%AE%E9%80%9A%E6%B6%88%E6%81%AF">普通消息</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E4%BA%8B%E4%BB%B6%E6%8E%A8%E9%80%81">事件触发</a>
	 * @see com.foxinmy.weixin4j.type.MessageType
	 * @see com.feican.weixin.msg.BaeMessage
	 * @see com.foxinmy.weixin4j.msg.TextMessage
	 * @see com.foxinmy.weixin4j.msg.ImageMessage
	 * @see com.foxinmy.weixin4j.msg.VoiceMessage
	 * @see com.foxinmy.weixin4j.msg.VideoMessage
	 * @see com.foxinmy.weixin4j.msg.LocationMessage
	 * @see com.foxinmy.weixin4j.msg.LinkMessage
	 * @see com.foxinmy.weixin4j.msg.event.ScribeEventMessage
	 * @see com.foxinmy.weixin4j.msg.event.ScanEventMessage
	 * @see com.foxinmy.weixin4j.msg.event.LocationEventMessage
	 * @see com.foxinmy.weixin4j.msg.event.menu.MenuEventMessage
	 */
	public static BaseMessage xml2msg(String xmlMsg) throws DocumentException {
		Document doc = DocumentHelper.parseText(xmlMsg);
		String type = doc.selectSingleNode("/xml/MsgType").getStringValue();
		if (StringUtils.isBlank(type)) {
			return null;
		}
		MessageType messageType = MessageType.valueOf(type.toLowerCase());
		Class<? extends BaseMessage> messageClass = messageType
				.getMessageClass();
		if (messageType == MessageType.event) {
			type = doc.selectSingleNode("/xml/Event").getStringValue();
			messageClass = EventType.valueOf(type.toLowerCase())
					.getEventClass();
		}
		XStream xstream = new XStream();
		xstream.ignoreUnknownElements();
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(messageClass);
		xstream.alias("xml", messageClass);
		return xstream.fromXML(xmlMsg, messageClass);
	}

	/**
	 * xml消息转换为消息对象
	 * 
	 * @param inputStream
	 *            带消息字符串的输入流
	 * @return 消息对象
	 * @throws DocumentException
	 * @see {@link com.foxinmy.weixin4j.util.MessageUtil#xml2msg(String)}
	 */
	public static BaseMessage xml2msg(InputStream inputStream)
			throws DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(inputStream);
		return xml2msg(doc.asXML());
	}
}
