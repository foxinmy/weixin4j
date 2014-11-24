package com.foxinmy.weixin4j.action;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.dom4j.DocumentException;

import com.foxinmy.weixin4j.model.BaseMsg;
import com.foxinmy.weixin4j.response.ResponseMessage;
import com.foxinmy.weixin4j.util.MessageUtil;
import com.foxinmy.weixin4j.xml.XStream;

/**
 * 继承的类需实现execute(M inMessage)
 * 
 * @className AbstractAction
 * @author jy
 * @date 2014年10月12日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.action.WeixinAction
 */
@SuppressWarnings("unchecked")
public abstract class AbstractAction<M extends BaseMsg> implements
		WeixinAction {

	public abstract ResponseMessage execute(M inMessage);

	@Override
	public ResponseMessage execute(String msg) throws DocumentException {
		BaseMsg message = MessageUtil.xml2msg(msg);
		if (message == null) {
			Class<M> messageClass = getGenericType();
			XStream xstream = XStream.get();
			xstream.processAnnotations(messageClass);
			xstream.alias("xml", messageClass);
			return execute(xstream.fromXML(msg, messageClass));
		}
		return execute((M) message);
	}

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
