package com.foxinmy.weixin4j.xml;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.NameFilter;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 对 后缀为_$n 的 xml节点序列化
 * 
 * @className ListsuffixResultSerializer
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月24日
 * @since JDK 1.6
 * @see
 */
public class ListsuffixResultSerializer {

	/**
	 * 序列化为json
	 * 
	 * @param object
	 * @return json
	 */
	public static JSONObject serializeToJSON(Object object) {
		JSONObject result = (JSONObject) JSON.toJSON(object);
		Map<Field, String[]> listsuffixFields = ListsuffixResultDeserializer
				.getListsuffixFields(object.getClass());
		if (!listsuffixFields.isEmpty()) {
			JSONField jsonField = null;
			Object value = null;
			for (Field field : listsuffixFields.keySet()) {
				jsonField = field.getAnnotation(JSONField.class);
				if (jsonField != null
						&& StringUtil.isNotBlank(jsonField.name())) {
					result.remove(jsonField.name());
				} else {
					result.remove(field.getName());
				}
				try {
					field.setAccessible(true);
					value = field.get(object);
				} catch (Exception e) {
					;//
				}
				if (value != null && value instanceof List) {
					result.putAll(listsuffixConvertMap((List<?>) value));
				}
			}
		}
		return result;
	}

	/**
	 * list对象转换为map的$n形式
	 * 
	 * @param listsuffix
	 * @return
	 */
	public static Map<String, String> listsuffixConvertMap(List<?> listsuffix) {
		Map<String, String> listMap = new HashMap<String, String>();
		if (listsuffix != null && !listsuffix.isEmpty()) {
			for (int i = 0; i < listsuffix.size(); i++) {
				listMap.putAll(JSON.parseObject(JSON.toJSONString(
						listsuffix.get(i), new ListsuffixEndNameFilter(i)),
						new TypeReference<Map<String, String>>() {
						}));
			}
		}
		return listMap;
	}

	private static class ListsuffixEndNameFilter implements NameFilter {
		private final int index;

		public ListsuffixEndNameFilter(int index) {
			this.index = index;
		}

		@Override
		public String process(Object object, String name, Object value) {
			return String.format("%s_%d", name, index);
		}
	}

	/**
	 * 序列化为xml
	 * 
	 * @param object
	 * @return xml
	 */
	public static String serializeToXML(Object object) {
		JSONObject obj = serializeToJSON(object);
		StringWriter sw = new StringWriter();
		XMLStreamWriter xw = null;
		try {
			xw = XMLOutputFactory.newInstance().createXMLStreamWriter(sw);
			xw.writeStartDocument(Consts.UTF_8.name(), "1.0");
			xw.writeStartElement("xml");
			for (String key : obj.keySet()) {
				if (StringUtil.isBlank(obj.getString(key))) {
					continue;
				}
				xw.writeStartElement(key);
				xw.writeCData(obj.getString(key));
				xw.writeEndElement();
			}
			xw.writeEndElement();
			xw.writeEndDocument();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} finally {
			if (xw != null) {
				try {
					xw.close();
				} catch (XMLStreamException e) {
					;
				}
			}
			try {
				sw.close();
			} catch (IOException e) {
				;
			}
		}
		return sw.getBuffer().toString();
	}
}
