package com.foxinmy.weixin4j.mp.response;

import com.foxinmy.weixin4j.model.BaseMsg;
import com.foxinmy.weixin4j.mp.type.ResponseType;
import com.foxinmy.weixin4j.msg.BaseMessage;
import com.foxinmy.weixin4j.util.ClassUtil;
import com.foxinmy.weixin4j.xml.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 响应消息基类
 * <p>
 * <font color="red">回复图片等多媒体消息时需要预先上传多媒体文件到微信服务器,
 * 假如服务器无法保证在五秒内处理并回复，可以直接回复空串，微信服务器不会对此作任何处理，并且不会发起重试</font>
 * </p>
 * 
 * @className BaseResponse
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 */
public class BaseResponse extends BaseMsg {

	private static final long serialVersionUID = 7761192742840031607L;
	protected final static XStream xmlStream = XStream.get();
	static {
		Class<?>[] classes = ClassUtil.getClasses(
				BaseResponse.class.getPackage()).toArray(new Class[0]);

		xmlStream.autodetectAnnotations(true);
		xmlStream.processAnnotations(classes);
	}
	@XStreamAlias("MsgType")
	private ResponseType msgType; // 消息类型

	public BaseResponse(ResponseType msgType) {
		this.msgType = msgType;
	}

	public BaseResponse(ResponseType msgType, BaseMessage inMessage) {
		this(msgType, inMessage.getFromUserName(), inMessage.getToUserName());
	}

	public BaseResponse(ResponseType msgType, String toUserName,
			String fromUserName) {
		super(toUserName, fromUserName);
		this.msgType = msgType;

	}

	public ResponseType getMsgType() {
		return msgType;
	}

	public void setMsgType(ResponseType msgType) {
		this.msgType = msgType;
	}

	/**
	 * 消息对象转换为微信服务器接受的xml格式消息
	 * 
	 * @return xml字符串
	 */
	public String toXml() {
		return xmlStream.toXML(this);
	}
}
