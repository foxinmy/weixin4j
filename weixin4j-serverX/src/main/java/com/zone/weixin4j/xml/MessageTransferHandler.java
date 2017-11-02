package com.zone.weixin4j.xml;

import com.zone.weixin4j.request.WeixinRequest;
import com.zone.weixin4j.socket.WeixinMessageTransfer;
import com.zone.weixin4j.type.AccountType;
import com.zone.weixin4j.util.ServerToolkits;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 微信消息
 *
 * @className MessageTransferHandler
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月17日
 * @since JDK 1.6
 * @see
 */
public class MessageTransferHandler extends DefaultHandler {

	private String fromUserName;
	private String toUserName;
	private String msgType;
	private String eventType;
	private boolean isQY;
	private Set<String> nodeNames;

	private String content;

	@Override
	public void startDocument() throws SAXException {
		fromUserName = null;
		toUserName = null;
		msgType = null;
		eventType = null;
		isQY = false;
		nodeNames = new HashSet<String>();
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		nodeNames.add(localName);
		localName = localName.toLowerCase();
		if (localName.equals("fromusername")) {
			fromUserName = content;
		} else if (localName.equals("tousername")) {
			toUserName = content;
		} else if (localName.equals("msgtype")) {
			msgType = content.toLowerCase();
		} else if (localName.equals("event")) {
			eventType = content.toLowerCase();
		} else if (localName.startsWith("agent") // 应用信息
				|| localName.startsWith("suite") // 套件信息
				|| localName.equals("batchjob")) { // 批量任务
			isQY = true;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		this.content = new String(ch, start, length);
	}

	private AccountType getAccountType() {
		if (isQY) {
			return AccountType.QY;
		}
		if (ServerToolkits.isBlank(msgType)
				&& ServerToolkits.isBlank(eventType)) {
			return null;
		}
		return AccountType.MP;
	}

	private static MessageTransferHandler global = new MessageTransferHandler();

	public static WeixinMessageTransfer parser(WeixinRequest request)
			throws RuntimeException {
		try {
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();
			xmlReader.setContentHandler(global);
			xmlReader.parse(new InputSource(new ByteArrayInputStream(request
					.getOriginalContent().getBytes(ServerToolkits.UTF_8))));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		}
		return new WeixinMessageTransfer(request.getAesToken(),
				request.getEncryptType(), global.toUserName,
				global.fromUserName, global.getAccountType(), global.msgType,
				global.eventType, global.nodeNames);
	}
}
