package com.foxinmy.weixin4j.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * XML 处理
 * 
 * @className XmlStream
 * @author jy
 * @date 2015年6月2日
 * @since JDK 1.7
 * @see
 */
public final class XmlStream {
	private final static String ROOT_ELEMENT_XML = "xml";
	private final static String XML_VERSION = "1.0";
	private final static Map<Class<?>, Unmarshaller> messageUnmarshaller;
	private final static Map<Class<?>, Marshaller> messageMarshaller;
	static {
		messageUnmarshaller = new HashMap<Class<?>, Unmarshaller>();
		messageMarshaller = new HashMap<Class<?>, Marshaller>();
	}

	/**
	 * Xml2Bean
	 * 
	 * @param content
	 *            xml内容
	 * @param clazz
	 *            bean类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromXML(InputStream content, Class<T> clazz) {
		Unmarshaller unmarshaller = messageUnmarshaller.get(clazz);
		if (unmarshaller == null) {
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
				unmarshaller = jaxbContext.createUnmarshaller();
				messageUnmarshaller.put(clazz, unmarshaller);
			} catch (JAXBException e) {
				throw new IllegalArgumentException(e);
			}
		}
		try {
			Source source = new StreamSource(content);
			XmlRootElement rootElement = clazz
					.getAnnotation(XmlRootElement.class);
			if (rootElement == null || rootElement.name().equals(
					XmlRootElement.class.getMethod("name")
					.getDefaultValue().toString())) {
				JAXBElement<T> jaxbElement = unmarshaller.unmarshal(source,
						clazz);
				return jaxbElement.getValue();
			} else {
				return (T) unmarshaller.unmarshal(source);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} finally {
			if (content != null) {
				try {
					content.close();
				} catch (IOException e) {
					;
				}
			}
		}
	}

	/**
	 * Xml2Bean
	 * 
	 * @param content
	 *            xml内容
	 * @param clazz
	 *            bean类型
	 * @return
	 */
	public static <T> T fromXML(String content, Class<T> clazz) {
		return fromXML(
				new ByteArrayInputStream(content.getBytes(Consts.UTF_8)), clazz);
	}

	/**
	 * map2xml
	 * 
	 * @param map
	 *            value无嵌套的map
	 * @return xml内容
	 */
	public static String map2xml(Map<String, String> map) {
		StringWriter sw = new StringWriter();
		try {
			XMLStreamWriter xw = XMLOutputFactory.newInstance()
					.createXMLStreamWriter(sw);
			xw.writeStartDocument(Consts.UTF_8.name(), XML_VERSION);
			xw.writeStartElement(ROOT_ELEMENT_XML);
			for (Iterator<Entry<String, String>> it = map.entrySet().iterator(); it
					.hasNext();) {
				Entry<String, String> entry = it.next();
				xw.writeStartElement(entry.getKey());
				xw.writeCData(entry.getValue());
				xw.writeEndElement();
			}
			xw.writeEndDocument();
			xw.flush();
			xw.close();
		} catch (XMLStreamException e) {
			throw new IllegalArgumentException(e);
		} finally {
			try {
				sw.close();
			} catch (IOException e) {
				;
			}
		}
		return sw.getBuffer().toString();
	}

	public static String map2xml(JSONObject json) {
		StringWriter sw = new StringWriter();
		try {
			XMLStreamWriter xw = XMLOutputFactory.newInstance()
					.createXMLStreamWriter(sw);
			xw.writeStartDocument(Consts.UTF_8.name(), XML_VERSION);
			xw.writeStartElement(ROOT_ELEMENT_XML);
			Set<String> keys = json.keySet();
			for (String key : keys) {
				xw.writeStartElement(key);
				xw.writeCData(json.getString(key));
				xw.writeEndElement();
			}
			xw.writeEndDocument();
			xw.flush();
			xw.close();
		} catch (XMLStreamException e) {
			throw new IllegalArgumentException(e);
		} finally {
			try {
				sw.close();
			} catch (IOException e) {
				;
			}
		}
		return sw.getBuffer().toString();
	}

	/**
	 * xml2map
	 * 
	 * @param content
	 *            无嵌套节点的xml内容
	 * @return map对象
	 */
	public static Map<String, String> xml2map(String content) {
		Map<String, String> map = new HashMap<String, String>();
		StringReader sr = new StringReader(content);
		try {
			XMLStreamReader xr = XMLInputFactory.newInstance()
					.createXMLStreamReader(sr);
			while (true) {
				int event = xr.next();
				if (event == XMLStreamConstants.END_DOCUMENT) {
					xr.close();
					break;
				} else if (event == XMLStreamConstants.START_ELEMENT) {
					String name = xr.getLocalName();
					while (true) {
						event = xr.next();
						if (event == XMLStreamConstants.START_ELEMENT) {
							name = xr.getLocalName();
						} else if (event == XMLStreamConstants.END_ELEMENT) {
							break;
						} else if (event == XMLStreamConstants.CHARACTERS) {
							String value = xr.getText();
							map.put(name, value);
						}
					}
				}
			}
		} catch (XMLStreamException e) {
			throw new IllegalArgumentException(e);
		} finally {
			sr.close();
		}
		return map;
	}

	/**
	 * Bean2Xml
	 * 
	 * @param object
	 *            bean对象
	 * @return xml内容
	 */
	public static String toXML(Object object) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		toXML(object, os);
		return StringUtil.newStringUtf8(os.toByteArray());
	}

	/**
	 * Bean2Xml
	 * 
	 * @param t
	 *            bean对象
	 * @param os
	 *            输出流
	 */
	@SuppressWarnings("unchecked")
	public static <T> void toXML(T t, OutputStream os) {
		Class<T> clazz = (Class<T>) t.getClass();
		Marshaller marshaller = messageMarshaller.get(clazz);
		if (marshaller == null) {
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
				marshaller = jaxbContext.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_ENCODING, Consts.UTF_8.name()); 
				messageMarshaller.put(clazz, marshaller);
			} catch (JAXBException e) {
				throw new IllegalArgumentException(e);
			}
		}
		try {
			XmlRootElement rootElement = clazz
					.getAnnotation(XmlRootElement.class);
			if (rootElement == null
					|| rootElement.name().equals(
							XmlRootElement.class.getMethod("name")
									.getDefaultValue().toString())) {
				marshaller.marshal(new JAXBElement<T>(new QName(
						ROOT_ELEMENT_XML), clazz, t), os);
			} else {
				marshaller.marshal(t, os);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					;
				}
			}
		}
	}
}
