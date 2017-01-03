package com.foxinmy.weixin4j.util;

import com.foxinmy.weixin4j.http.weixin.WeixinResponse;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 接口调用错误获取
 *
 * @author fengyapeng
 * @className WeixinErrorUtil2
 * @date
 * @see
 * @since JDK 1.6
 */
public final class WeixinErrorUtil2 {

	private static       byte[]              errorXmlByteArray;
	private final static Map<String, String> errorCacheMap;

	static {
		errorCacheMap = new ConcurrentHashMap<String, String>();
		try {
			errorXmlByteArray = IOUtil.toByteArray(WeixinResponse.class.getResourceAsStream("error.xml"));
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();
			ContentHandler textHandler = new ErrorTextHandler(errorCacheMap);
			xmlReader.setContentHandler(textHandler);
			xmlReader.parse(new InputSource(new ByteArrayInputStream(errorXmlByteArray)));
		} catch (IOException e) {
			;
		} catch (SAXException e) {

		}
	}

	private static class ErrorTextHandler extends DefaultHandler {

		private Map<String, String> errorCacheMap;

		public ErrorTextHandler(Map<String, String> errorCacheMap) {
			this.errorCacheMap = errorCacheMap;
		}

		private String  code;
		private String  text;
		private boolean codeElement;
		private boolean textElement;
		private int isPair = 0;

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			codeElement = qName.equalsIgnoreCase("code");
			textElement = qName.equalsIgnoreCase("text");
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			String _text = new String(ch, start, length);
			if (codeElement) {
				isPair++;
				code = _text;
				codeElement = false;
			}
			if (textElement) {
				isPair++;
				text = _text;
				textElement = false;
			}
			if (isPair == 2) {
				// 配对成功
				errorCacheMap.put(code, text);
				isPair = 0;
				code = null;
				text = null;
			}
		}
	}

	public static String getText(String code) throws RuntimeException {
		return errorCacheMap.get(code);
	}

	public static String getText(String code, String defaultMsg) throws RuntimeException {
		String text = getText(code);
		if (StringUtil.isNotBlank(text)) {
			return text;
		}
		return defaultMsg;
	}


	public static void main(String[] args) {
		System.out.println(getText("40001"));
		System.out.println(getText("30002"));
		System.out.println(getText("1234"));
	}
}
