package com.foxinmy.weixin4j.dispatcher;

import java.io.ByteArrayInputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.util.Consts;

public abstract class WeixinMessageAdapter<M> {
	/**
	 * 消息类集合
	 */
	private static final Map<Class<?>, Unmarshaller> unmarshallerMap;

	static {
		unmarshallerMap = new HashMap<Class<?>, Unmarshaller>();
	}

	@SuppressWarnings("unchecked")
	protected M messageRead(String message) throws WeixinException {
		try {
			Class<?> clazz = resolveMessageClass();
			Source source = new StreamSource(new ByteArrayInputStream(
					message.getBytes(Consts.UTF_8)));
			JAXBElement<?> jaxbElement = getUnmarshaller().unmarshal(source,
					clazz);
			return (M) jaxbElement.getValue();
		} catch (JAXBException e) {
			throw new WeixinException(e);
		}
	}

	protected Unmarshaller getUnmarshaller() throws WeixinException {
		Class<?> mineClass = resolveMessageClass();
		Unmarshaller unmarshaller = unmarshallerMap.get(mineClass);
		if (unmarshaller == null) {
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(mineClass);
				unmarshaller = jaxbContext.createUnmarshaller();
				unmarshallerMap.put(mineClass, unmarshaller);
			} catch (JAXBException e) {
				throw new WeixinException(e);
			}
		}
		return unmarshaller;
	}

	protected Class<?> resolveMessageClass() {
		Class<?> clazz = WeixinMessage.class;
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType ptype = ((ParameterizedType) type);
			Type[] args = ptype.getActualTypeArguments();
			clazz = (Class<?>) args[0];
		}
		return clazz;
	}
}
