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
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.util.ReflectionUtil;

/**
 * 对 后缀为_$n 的 xml节点序列化
 * 
 * @className ListsuffixResultSerializer
 * @author jy
 * @date 2015年3月24日
 * @since JDK 1.7
 * @see
 */
public class ListsuffixResultSerializer {

	private static final String LISTSUFFIX_RESULT_NAME = "$listsuffix_result$";

	/**
	 * 序列化为json
	 * 
	 * @param object
	 * @return json
	 */
	public static JSONObject serializeToJSON(Object object) {
		final JSONObject listsuffix = new JSONObject();
		String preJson = JSON.toJSONString(object, new PropertyFilter() {
			@Override
			public boolean apply(Object source, String name, Object value) {
				if (name.equalsIgnoreCase(LISTSUFFIX_RESULT_NAME)) {
					listsuffix.put(LISTSUFFIX_RESULT_NAME, JSON.toJSON(value));
					return false;
				}
				Field field = ReflectionUtil.getAccessibleField(source, name);
				if (field != null
						&& field.getAnnotation(ListsuffixResult.class) != null) {
					listsuffix.put(LISTSUFFIX_RESULT_NAME, JSON.toJSON(value));
					return false;
				}
				return true;
			}
		});
		JSONObject result = JSON.parseObject(preJson);
		try {
			Map<String, String> listMap = listsuffixConvertMap(listsuffix
					.getJSONArray(LISTSUFFIX_RESULT_NAME));
			result.putAll(listMap);
		} catch (JSONException e) {
			;//
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
