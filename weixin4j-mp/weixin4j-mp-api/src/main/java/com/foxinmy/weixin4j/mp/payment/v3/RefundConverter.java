package com.foxinmy.weixin4j.mp.payment.v3;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.foxinmy.weixin4j.xml.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * 退款查询接口调用结果转换类
 * 
 * @className RefundConverter
 * @author jy
 * @date 2014年11月2日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.mp.payment.v3.Refund
 * @see com.foxinmy.weixin4j.mp.payment.v3.RefundDetail
 */
public class RefundConverter {
	private final static XStream xStream = XStream.get();
	private final ReflectionConverter reflectionConverter;

	public RefundConverter() {
		xStream.processAnnotations(Refund.class);
		xStream.registerConverter(new RefundConverter.$());
		reflectionConverter = new ReflectionConverter(xStream.getMapper(),
				xStream.getReflectionProvider());
	}

	public String toXML(Refund refund) {
		return xStream.toXML(refund);
	}

	public Refund fromXML(String xml) {
		return xStream.fromXML(xml, Refund.class);
	}

	private class $ implements Converter {
		@Override
		public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
			return clazz.equals(Refund.class);
		}

		@Override
		public void marshal(Object source, HierarchicalStreamWriter writer,
				MarshallingContext context) {
			reflectionConverter.marshal(source, writer, context);
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object unmarshal(HierarchicalStreamReader reader,
				UnmarshallingContext context) {
			Refund refund = new Refund();
			Mapper mapper = xStream.getMapper();
			ReflectionProvider reflectionProvider = xStream
					.getReflectionProvider();
			Pattern pattern = Pattern.compile("(_\\d)$");
			Matcher matcher = null;
			Map<String, Map<String, String>> outMap = new HashMap<String, Map<String, String>>();
			while (reader.hasMoreChildren()) {
				reader.moveDown();
				String nodeName = reader.getNodeName();
				String fieldName = mapper.realMember(Refund.class, nodeName);
				Field field = reflectionProvider.getFieldOrNull(Refund.class,
						fieldName);
				if (field != null) {
					Object value = context.convertAnother(refund,
							field.getType());
					reflectionProvider.writeField(refund, fieldName, value,
							field.getDeclaringClass());
				} else if ((matcher = pattern.matcher(nodeName)).find()) {
					String key = matcher.group();
					Map<String, String> innerMap = null;
					if ((innerMap = outMap.get(key)) == null) {
						innerMap = new HashMap<String, String>();
						outMap.put(key, innerMap);
					}
					innerMap.put(nodeName.replace(key, ""), reader.getValue());
				}
				reader.moveUp();
			}
			StringBuilder detailXml = new StringBuilder();
			detailXml.append("<list>");
			String detailCanonicalName = RefundDetail.class.getCanonicalName();
			for (Iterator<Entry<String, Map<String, String>>> outIt = outMap
					.entrySet().iterator(); outIt.hasNext();) {
				detailXml.append("<").append(detailCanonicalName).append(">");
				for (Iterator<Entry<String, String>> innerIt = outIt.next()
						.getValue().entrySet().iterator(); innerIt.hasNext();) {
					Entry<String, String> entry = innerIt.next();
					detailXml.append("<").append(entry.getKey()).append(">");
					detailXml.append(entry.getValue());
					detailXml.append("</").append(entry.getKey()).append(">");
				}
				detailXml.append("</").append(detailCanonicalName).append(">");
			}
			detailXml.append("</list>");
			xStream.processAnnotations(RefundDetail.class);
			refund.setDetails(xStream.fromXML(detailXml.toString(), List.class));
			return refund;
		}
	}
}
