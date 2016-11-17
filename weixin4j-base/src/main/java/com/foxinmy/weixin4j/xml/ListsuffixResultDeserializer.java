package com.foxinmy.weixin4j.xml;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stream.StreamSource;

import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.ReflectionUtil;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 对 后缀为_$n 的 xml节点反序列化
 * 
 * @className ListsuffixResultDeserializer
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月24日
 * @since JDK 1.6
 * @see
 */
public class ListsuffixResultDeserializer {

	private static Pattern DEFAULT_PATTERN;
	static {
		String regex = null;
		try {
			Object value = ListsuffixResult.class.getMethod("value")
					.getDefaultValue();
			if (value instanceof String) {
				regex = (String) value;
			} else if (value instanceof String[]) {
				regex = ((String[]) value)[0];
			}
		} catch (NoSuchMethodException e) {
			;
		}
		if (StringUtil.isBlank(regex)) {
			regex = "(_\\d)$";
		}
		DEFAULT_PATTERN = Pattern.compile(regex);
	}

	/**
	 * 对包含$n节点的xml反序列化
	 * 
	 * @param content
	 *            xml内容
	 * @param clazz
	 * @return
	 */
	public static <T> T deserialize(String content, Class<T> clazz) {
		T t = XmlStream.fromXML(content, clazz);
		Map<Field, String[]> listsuffixFields = getListsuffixFields(clazz);
		if (!listsuffixFields.isEmpty()) {
			for (Entry<Field, String[]> entry : listsuffixFields.entrySet()) {
				Field field = entry.getKey();
				Type type = field.getGenericType();
				Class<?> wrapperClazz = null;
				if (type instanceof ParameterizedType) {
					wrapperClazz = (Class<?>) ((ParameterizedType) type)
							.getActualTypeArguments()[0];
				} else {
					continue;
				}
				ListWrapper<?> listWrapper = deserializeToListWrapper(content,
						wrapperClazz, entry.getValue());
				if (listWrapper != null) {
					try {
						field.setAccessible(true);
						field.set(t, listWrapper.getItems());
					} catch (Exception e) {
						;
					}
				}
			}
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	public static <T> ListWrapper<T> deserializeToListWrapper(String content,
			Class<T> clazz, String... matchPattern) {
		XMLStreamReader xr = null;
		XMLStreamWriter xw = null;
		try {
			xr = XMLInputFactory.newInstance().createXMLStreamReader(
					new StringReader(content));
			List<Pattern> patterns = new ArrayList<Pattern>();
			for (String pattern : matchPattern) {
				patterns.add(Pattern.compile(pattern));
			}
			Matcher matcher = null;
			Map<String, Map<String, String>> outMap = new HashMap<String, Map<String, String>>();
			while (true) {
				int event = xr.next();
				if (event == XMLStreamConstants.END_DOCUMENT) {
					break;
				} else if (event == XMLStreamConstants.START_ELEMENT) {
					String name = xr.getLocalName();
					for (Pattern pattern : patterns) {
						if ((matcher = pattern.matcher(name)).find()) {
							while (true) {
								event = xr.next();
								if (event == XMLStreamConstants.START_ELEMENT) {
									name = xr.getLocalName();
								} else if (event == XMLStreamConstants.END_ELEMENT) {
									break;
								} else if (event == XMLStreamConstants.CHARACTERS  || event == XMLStreamConstants.CDATA) {
									String key = matcher.group();
									if (!pattern.pattern().equals(
											DEFAULT_PATTERN.pattern())) {
										matcher = DEFAULT_PATTERN.matcher(name);
										matcher.find();
										key = matcher.group();
									}
									Map<String, String> innerMap = null;
									if ((innerMap = outMap.get(key)) == null) {
										innerMap = new HashMap<String, String>();
										outMap.put(key, innerMap);
									}
									innerMap.put(name.replace(key, ""),
											xr.getText());
								}
							}
							break;
						}
					}
				}
			}
			if (!outMap.isEmpty()) {
				StringWriter sw = new StringWriter();
				xw = XMLOutputFactory.newInstance().createXMLStreamWriter(sw);
				xw.writeStartDocument(Consts.UTF_8.name(), "1.0");
				xw.writeStartElement(clazz.getCanonicalName());
				String itemName = StringUtil
						.uncapitalize(clazz.getSimpleName());
				XmlRootElement rootElement = clazz
						.getAnnotation(XmlRootElement.class);
				if (rootElement != null
						&& StringUtil.isNotBlank(rootElement.name())) {
					try {
						if (!rootElement.name().equals(
								XmlRootElement.class.getMethod("name")
										.getDefaultValue().toString())) {
							itemName = rootElement.name();
						}
					} catch (NoSuchMethodException e) {
						;
					}
				}
				for (Entry<String, Map<String, String>> outE : outMap
						.entrySet()) {
					xw.writeStartElement(itemName);
					for (Entry<String, String> innerE : outE
							.getValue().entrySet()) {
						xw.writeStartElement(innerE.getKey());
						xw.writeCharacters(innerE.getValue());
						xw.writeEndElement();
					}
					xw.writeEndElement();
				}
				xw.writeEndElement();
				xw.writeEndDocument();
				JAXBContext ctx = JAXBContext.newInstance(ListWrapper.class,
						clazz);
				Unmarshaller u = ctx.createUnmarshaller();
				return u.unmarshal(
						new StreamSource(new StringReader(sw.getBuffer()
								.toString())), ListWrapper.class).getValue();
			}
			return null;
		} catch (XMLStreamException e) {
			throw new IllegalArgumentException(e);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (xw != null) {
					xw.close();
				}
				if (xr != null) {
					xr.close();
				}
			} catch (XMLStreamException e) {
				;
			}
		}
	}

	public static Map<Field, String[]> getListsuffixFields(Class<?> clazz) {
		Map<Field, String[]> listsuffixFields = new HashMap<Field, String[]>();
		Set<Field> allFields = ReflectionUtil.getAllField(clazz);
		ListsuffixResult listsuffixResult = null;
		for (Field field : allFields) {
			listsuffixResult = field.getAnnotation(ListsuffixResult.class);
			if (listsuffixResult != null) {
				listsuffixFields.put(field, listsuffixResult.value());
			}
		}
		return listsuffixFields;
	}
}
