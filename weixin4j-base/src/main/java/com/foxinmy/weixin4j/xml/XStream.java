package com.foxinmy.weixin4j.xml;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;

public class XStream extends com.thoughtworks.xstream.XStream {

	public XStream() {

		super(new Xpp3Driver() {

			@Override
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out) {

					@Override
					protected void writeText(QuickWriter writer, String text) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					}
				};
			}
		});
	}

	public XStream(HierarchicalStreamDriver hierarchicalStreamDriver) {
		super(hierarchicalStreamDriver);
	}

	@SuppressWarnings("unchecked")
	public <T> T fromXML(String xml, Class<T> t) {
		return (T) super.fromXML(xml);
	}

	@SuppressWarnings("unchecked")
	public <T> T fromXML(InputStream inputStream, Class<T> t) {
		return (T) super.fromXML(inputStream);
	}

	public static XStream get() {
		XStream xstream = new XStream();
		xstream.ignoreUnknownElements();
		xstream.autodetectAnnotations(true);
		return xstream;
	}

	public static <T> T get(InputStream inputStream, Class<T> clazz) {
		XStream xStream = get();
		xStream.alias("xml", clazz);
		xStream.processAnnotations(clazz);
		return xStream.fromXML(inputStream, clazz);
	}

	public static <T> T get(String xml, Class<T> clazz) {
		XStream xStream = get();
		xStream.alias("xml", clazz);
		xStream.processAnnotations(clazz);
		return xStream.fromXML(xml, clazz);
	}

	public static String to(Object obj) {
		XStream xStream = get();
		Class<?> clazz = obj.getClass();
		xStream.alias("xml", clazz);
		xStream.processAnnotations(clazz);
		return xStream.toXML(obj);
	}

	public static void to(Object obj, OutputStream out) {
		XStream xStream = get();
		Class<?> clazz = obj.getClass();
		xStream.alias("xml", clazz);
		xStream.processAnnotations(clazz);
		xStream.toXML(obj, out);
	}
}
