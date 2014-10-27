package com.foxinmy.weixin4j.msg.model;

import java.io.Serializable;
import java.io.Writer;

import com.foxinmy.weixin4j.msg.notify.BaseNotify;
import com.foxinmy.weixin4j.type.MediaType;
import com.foxinmy.weixin4j.util.ClassUtil;
import com.foxinmy.weixin4j.xml.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public abstract class BaseMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	private final static XStream xstream = new XStream(
			new JsonHierarchicalStreamDriver() {
				public HierarchicalStreamWriter createWriter(Writer writer) {
					return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
				}
			});
	static {
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(ClassUtil.getClasses(
				BaseNotify.class.getPackage()).toArray(new Class[0]));
	}

	public abstract MediaType getMediaType();

	/**
	 * 客服消息json化,适用于客服消息接口
	 * 
	 * @return {"touser": "to","msgtype": "text","text": {"content": "123"}}
	 */
	public String toNotifyJson() {
		return xstream.toXML(this);
	}
}
