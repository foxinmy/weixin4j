package com.foxinmy.weixin4j.http.message;

import java.io.IOException;
import java.io.InputStream;

import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.MimeType;
import com.foxinmy.weixin4j.util.FileUtil;
import com.foxinmy.weixin4j.util.RegexUtil;
import com.foxinmy.weixin4j.xml.XmlStream;

/**
 * XML 转换
 * 
 * @className XmlMessageConverter
 * @author jinyu
 * @date Jul 20, 2016
 * @since JDK 1.6
 */
public class XmlMessageConverter extends AbstractMessageConverter {

	public static final XmlMessageConverter GLOBAL = new XmlMessageConverter();

	private static final String XML = "xml";
	private static final int BRACKET = '<';

	public XmlMessageConverter() {
		super(MimeType.APPLICATION_XML, MimeType.TEXT_XML, new MimeType("application", "*+xml"));
	}
	
	@Override
	public boolean canConvert(Class<?> clazz, HttpResponse response) {
		if (!super.canConvert(clazz, response)) {
			String disposition = response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION);
			String fileName = RegexUtil.regexFileNameFromContentDispositionHeader(disposition);
			return (fileName != null && FileUtil.getFileExtension(fileName).equalsIgnoreCase(XML));
		}
		return true;
	}

	@Override
	protected boolean supports(Class<?> clazz, byte[] content) {
		return BRACKET == content[0];
	}

	@Override
	protected <T> T convertInternal(Class<? extends T> clazz, InputStream body) throws IOException {
		return XmlStream.fromXML(body, clazz);
	}
}
