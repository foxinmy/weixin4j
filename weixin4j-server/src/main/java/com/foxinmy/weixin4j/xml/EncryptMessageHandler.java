package com.foxinmy.weixin4j.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.foxinmy.weixin4j.util.Consts;

public class EncryptMessageHandler extends DefaultHandler {

	private String encryptContent;

	private String currentQName;

	@Override
	public void startDocument() throws SAXException {
		encryptContent = null;
		currentQName = null;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		this.currentQName = qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		this.currentQName = null;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (currentQName.equalsIgnoreCase("encrypt")) {
			this.encryptContent = new String(ch, start, length);
		}
	}

	public String getEncryptContent() {
		return encryptContent;
	}

	public static String parser(String xmlContent) throws RuntimeException {
		EncryptMessageHandler messageHandler = new EncryptMessageHandler();
		try {
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();
			xmlReader.setContentHandler(messageHandler);
			xmlReader.parse(new InputSource(new ByteArrayInputStream(xmlContent
					.getBytes(Consts.UTF_8))));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		}
		return messageHandler.getEncryptContent();
	}
}
