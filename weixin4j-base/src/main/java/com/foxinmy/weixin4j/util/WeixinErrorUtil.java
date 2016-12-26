package com.foxinmy.weixin4j.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.foxinmy.weixin4j.http.weixin.WeixinResponse;

/**
 * 接口调用错误获取
 *
 * @author jy
 * @className WeixinErrorUtil
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月12日
 * @see
 * @since JDK 1.6
 */
public final class WeixinErrorUtil {

	private static       byte[]              errorXmlByteArray;
	private final static Map<String, String> errorCacheMap;

	static {
		errorCacheMap = new ConcurrentHashMap<String, String>();
		try {
			errorXmlByteArray = IOUtil.toByteArray(WeixinResponse.class.getResourceAsStream("error.xml"));
		} catch (IOException e) {
			;
		}
	}

	private static class ErrorTextHandler extends DefaultHandler {

		private final String code;

		public ErrorTextHandler(String code) {
			this.code = code;
		}

		private String  text;
		private boolean codeElement;
		private boolean textElement;
		private boolean findElement;

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
			if (codeElement && _text.equalsIgnoreCase(code)) {
				findElement = true;
			} else if (textElement && findElement) {
				text = _text;
				throw new SAXException("ENOUGH");
			}
		}

		public String getText() {
			return StringUtil.isBlank(text) ? "" : text;
		}
	}

	public static String getText(String code) throws RuntimeException {
		if (StringUtil.isBlank(code)) {
			return "";
		}
		String text = errorCacheMap.get(code);
		if (StringUtil.isBlank(text)) {
			ErrorTextHandler textHandler = new ErrorTextHandler(code);
			try {
				XMLReader xmlReader = XMLReaderFactory.createXMLReader();
				xmlReader.setContentHandler(textHandler);
				xmlReader.parse(new InputSource(new ByteArrayInputStream(errorXmlByteArray)));
				text = textHandler.getText();
				errorCacheMap.put(code, text);
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (SAXException e) {
				text = textHandler.getText();
				errorCacheMap.put(code, text);
			}
		}
		return text;
	}


	public static void main(String[] args) {
		System.out.println(getText("30002"));
		System.out.println(getText("30002"));
		System.out.println(getText("1234"));
	}
}
