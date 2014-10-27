package com.foxinmy.weixin4j.mp.action;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.dom4j.DocumentException;

import com.foxinmy.weixin4j.mp.response.BaseResponse;
import com.foxinmy.weixin4j.msg.BaseMessage;
import com.foxinmy.weixin4j.util.MessageUtil;
import com.foxinmy.weixin4j.xml.XStream;

/**
 * 继承的类需实现execute(M inMessage)
 * 
 * @className AbstractAction
 * @author jy
 * @date 2014年10月12日
 * @since JDK 1.7
 * @see
 */
public abstract class AbstractAction<M extends BaseMessage> implements
		WeixinAction {

	public abstract BaseResponse execute(M inMessage);

	@SuppressWarnings("unchecked")
	@Override
	public String execute(String msg) throws DocumentException {
		BaseMessage message = MessageUtil.xml2msg(msg);
		if (message == null) {
			Class<M> messageClass = getGenericType();
			XStream xstream = new XStream();
			xstream.ignoreUnknownElements();
			xstream.autodetectAnnotations(true);
			xstream.processAnnotations(messageClass);
			xstream.alias("xml", messageClass);
			return execute(xstream.fromXML(msg, messageClass)).toXml();
		}
		return execute((M) message).toXml();
	}

	@SuppressWarnings("unchecked")
	private Class<M> getGenericType() {
		Class<M> clazz = null;
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType ptype = ((ParameterizedType) type);
			Type[] args = ptype.getActualTypeArguments();
			clazz = (Class<M>) args[0];
		}
		return clazz;
	}
}
