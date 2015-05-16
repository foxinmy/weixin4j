package com.foxinmy.weixin4j.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.foxinmy.weixin4j.type.MessageType;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.StringUtil;

public class CruxMessageHandler extends DefaultHandler {

	private String fromUserName;
	private String toUserName;
	private String msgType;
	private String eventType;
	private String agentId;

	private String content;

	@Override
	public void startDocument() throws SAXException {
		fromUserName = null;
		toUserName = null;
		msgType = null;
		eventType = null;
		agentId = null;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.equalsIgnoreCase("fromUserName")) {
			fromUserName = content;
		} else if (localName.equalsIgnoreCase("toUserName")) {
			toUserName = content;
		} else if (localName.equalsIgnoreCase("msgType")) {
			msgType = content.toLowerCase();
		} else if (localName.equalsIgnoreCase("event")) {
			eventType = content.toLowerCase();
		} else if (localName.equalsIgnoreCase("agentId")) {
			agentId = content;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		this.content = new String(ch, start, length);
	}

	public String getUniqueKey() {
		StringBuilder uniqueKey = new StringBuilder();
		uniqueKey.append(msgType);
		if (msgType.equals(MessageType.event.name())) {
			if (StringUtil.isBlank(agentId)) {
				uniqueKey.insert(0, "mp:");
			} else {
				uniqueKey.insert(0, "qy:");
			}
			uniqueKey.append(":").append(eventType);
		}
		return uniqueKey.toString();
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	private static CruxMessageHandler global = new CruxMessageHandler();

	public static CruxMessageHandler parser(String xmlContent)
			throws RuntimeException {
		try {
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();
			xmlReader.setContentHandler(global);
			xmlReader.parse(new InputSource(new ByteArrayInputStream(xmlContent
					.getBytes(Consts.UTF_8))));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		}
		return global;
	}
}
