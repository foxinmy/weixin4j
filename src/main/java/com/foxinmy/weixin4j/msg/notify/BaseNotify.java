package com.foxinmy.weixin4j.msg.notify;

import java.io.Serializable;
import java.io.Writer;

import com.foxinmy.weixin4j.type.MessageType;
import com.foxinmy.weixin4j.xml.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * 客服消息基类(48小时内不限制发送次数)
 * @author jy.hu
 * @date 2014年4月4日
 * @since JDK 1.7
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF">发送客服消息</a>
 */
public class BaseNotify implements Serializable {

	private static final long serialVersionUID = 7190233634431087729L;

	private String touser;
	private MessageType msgtype;

	public BaseNotify(MessageType msgtype) {
		this.msgtype = msgtype;
	}

	public BaseNotify(String touser, MessageType msgtype) {
		this.touser = touser;
		this.msgtype = msgtype;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public MessageType getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(MessageType msgtype) {
		this.msgtype = msgtype;
	}

	/**
	 * 客服消息json化
	 * @return {"touser": "to","msgtype": "text","text": {"content": "123"}}
	 */
	public String toJson() {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
			public HierarchicalStreamWriter createWriter(Writer writer) {
				return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
			}
		});
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(this.getClass());
		return xstream.toXML(this);
	}

	@Override
	public String toString() {
		return String.format("[BaseNotify touser=%s ,msgtype=%s]", touser, msgtype.name());
	}
}
