package com.foxinmy.weixin4j.xml;

import java.io.InputStream;
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
}
