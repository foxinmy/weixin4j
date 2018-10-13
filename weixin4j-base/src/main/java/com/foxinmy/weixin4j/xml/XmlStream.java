package com.foxinmy.weixin4j.xml;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.StringUtil;
import org.xml.sax.InputSource;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.*;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * XML 处理
 * 
 * @className XmlStream
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月2日
 * @since JDK 1.6
 * @see
 */
public final class XmlStream {
	private final static String ROOT_ELEMENT_XML = "xml";
	private final static String XML_VERSION = "1.0";
	private final static ConcurrentHashMap<Class<?>, JAXBContext> jaxbContexts = new ConcurrentHashMap<Class<?>, JAXBContext>();
	private final static SAXParserFactory spf = SAXParserFactory.newInstance();
	private final static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	static {
		try {
			String FEATURE = null;

			spf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
			spf.setFeature("http://xml.org/sax/features/external-general-entities", false);
			spf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			spf.setXIncludeAware(false);
		//	spf.setExpandEntityReferences(false);


			FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
			dbf.setFeature(FEATURE, true);

			// If you can't completely disable DTDs, then at least do the following:
			// Xerces 1 - http://xerces.apache.org/xerces-j/features.html#external-general-entities
			// Xerces 2 - http://xerces.apache.org/xerces2-j/features.html#external-general-entities
			// JDK7+ - http://xml.org/sax/features/external-general-entities
			FEATURE = "http://xml.org/sax/features/external-general-entities";
			dbf.setFeature(FEATURE, false);

			// Xerces 1 - http://xerces.apache.org/xerces-j/features.html#external-parameter-entities
			// Xerces 2 - http://xerces.apache.org/xerces2-j/features.html#external-parameter-entities
			// JDK7+ - http://xml.org/sax/features/external-parameter-entities
			FEATURE = "http://xml.org/sax/features/external-parameter-entities";
			dbf.setFeature(FEATURE, false);

			// Disable external DTDs as well
			FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
			dbf.setFeature(FEATURE, false);

			// and these as well, per Timothy Morgan's 2014 paper: "XML Schema, DTD, and Entity Attacks"
			dbf.setXIncludeAware(false);
			dbf.setExpandEntityReferences(false);


		} catch (Exception e) {
			;
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
	@SuppressWarnings("unchecked")
	public static <T> T fromXML(InputStream content, Class<T> clazz) {
		JAXBContext jaxbContext = getJaxbContext(clazz);
		try {
			DocumentBuilder safebuilder = dbf.newDocumentBuilder();

			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Source source = new SAXSource(spf.newSAXParser().getXMLReader(), new InputSource(content));
			XmlRootElement rootElement = clazz.getAnnotation(XmlRootElement.class);
			if (rootElement == null
					|| rootElement.name().equals(XmlRootElement.class.getMethod("name").getDefaultValue().toString())) {
				JAXBElement<T> jaxbElement = unmarshaller.unmarshal(source, clazz);
				return jaxbElement.getValue();
			} else {
				return (T) unmarshaller.unmarshal(source);
			}
		} catch (Exception ex) {
			throw new RuntimeException("Could not unmarshaller class [" + clazz + "]", ex);
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
		return fromXML(new ByteArrayInputStream(content.getBytes(Consts.UTF_8)), clazz);
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
			XMLStreamWriter xw = XMLOutputFactory.newInstance().createXMLStreamWriter(sw);
			xw.writeStartDocument(Consts.UTF_8.name(), XML_VERSION);
			xw.writeStartElement(ROOT_ELEMENT_XML);
			for (Entry<String, String> entry : map.entrySet()) {
				if (StringUtil.isBlank(entry.getValue())) {
					continue;
				}
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

	/**
	 * map2xml
	 * 
	 * @param json
	 *            value无嵌套的json
	 * @return xml内容
	 */
	public static String map2xml(JSONObject json) {
		StringWriter sw = new StringWriter();
		try {
			XMLStreamWriter xw = XMLOutputFactory.newInstance().createXMLStreamWriter(sw);
			xw.writeStartDocument(Consts.UTF_8.name(), XML_VERSION);
			xw.writeStartElement(ROOT_ELEMENT_XML);
			for (Entry<String, Object> entry : json.entrySet()) {
				if (StringUtil.isBlank(json.getString(entry.getKey()))) {
					continue;
				}
				xw.writeStartElement(entry.getKey());
				xw.writeCData(json.getString(entry.getKey()));
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
			XMLStreamReader xr = XMLInputFactory.newInstance().createXMLStreamReader(sr);
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
		JAXBContext jaxbContext = getJaxbContext(clazz);
		try {
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, Consts.UTF_8.name());
			XmlRootElement rootElement = clazz.getAnnotation(XmlRootElement.class);
			if (rootElement == null
					|| rootElement.name().equals(XmlRootElement.class.getMethod("name").getDefaultValue().toString())) {
				marshaller.marshal(new JAXBElement<T>(new QName(ROOT_ELEMENT_XML), clazz, t), os);
			} else {
				marshaller.marshal(t, os);
			}
		} catch (Exception ex) {
			throw new RuntimeException("Could not marshal class [" + clazz + "] ", ex);
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

	private static JAXBContext getJaxbContext(Class<?> clazz) {
		JAXBContext jaxbContext = jaxbContexts.get(clazz);
		if (jaxbContext == null) {
			try {
				jaxbContext = JAXBContext.newInstance(clazz);
				jaxbContexts.putIfAbsent(clazz, jaxbContext);
			} catch (JAXBException ex) {
				throw new RuntimeException("Could not instantiate JAXBContext for class [" + clazz + "] ", ex);
			}
		}
		return jaxbContext;
	}
}