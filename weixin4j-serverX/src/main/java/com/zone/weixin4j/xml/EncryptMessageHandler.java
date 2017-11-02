package com.zone.weixin4j.xml;

import com.zone.weixin4j.util.ServerToolkits;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 获取加密的密文内容
 * 
 * @className EncryptMessageHandler
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月17日
 * @since JDK 1.6
 * @see
 */
public class EncryptMessageHandler extends DefaultHandler {

	private String toUserName;
	private String encryptContent;
	private String content;

	@Override
	public void startDocument() throws SAXException {
		toUserName = null;
		encryptContent = null;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.equalsIgnoreCase("encrypt")) {
			encryptContent = content;
		} else if (localName.equalsIgnoreCase("tousername")) {
			toUserName = content;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		this.content = new String(ch, start, length);
	}

	public String getToUserName() {
		return toUserName;
	}

	public String getEncryptContent() {
		return encryptContent;
	}

	private final static EncryptMessageHandler global = new EncryptMessageHandler();

	public static EncryptMessageHandler parser(String xmlContent)
			throws RuntimeException {
		try {
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();
			xmlReader.setContentHandler(global);
			xmlReader.parse(new InputSource(new ByteArrayInputStream(xmlContent
					.getBytes(ServerToolkits.UTF_8))));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		}
		return global;
	}
}
