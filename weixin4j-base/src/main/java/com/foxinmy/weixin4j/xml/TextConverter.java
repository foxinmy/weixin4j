package com.foxinmy.weixin4j.xml;

import com.foxinmy.weixin4j.tuple.Text;
import com.thoughtworks.xstream.converters.SingleValueConverter;

/**
 * Text回复消息转换
 * 
 * @className TextConverter
 * @author jy
 * @date 2014年11月22日
 * @since JDK 1.7
 */
public class TextConverter implements SingleValueConverter {

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
		return clazz.equals(Text.class);
	}

	@Override
	public String toString(Object obj) {
		return ((Text) obj).getContent();
	}

	@Override
	public Object fromString(String text) {
		return new Text(text);
	}
}
