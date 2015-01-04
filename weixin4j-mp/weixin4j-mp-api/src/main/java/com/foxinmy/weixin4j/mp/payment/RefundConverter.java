package com.foxinmy.weixin4j.mp.payment;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.foxinmy.weixin4j.xml.XmlStream;
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
 * @see com.foxinmy.weixin4j.mp.payment.v2.RefundRecord
 * @see com.foxinmy.weixin4j.mp.payment.v2.RefundDetail
 * @see com.foxinmy.weixin4j.mp.payment.v3.RefundRecord
 * @see com.foxinmy.weixin4j.mp.payment.v3.RefundDetail
 */
public class RefundConverter {
	private final static XmlStream xStream = XmlStream.get();
	private final static Mapper mapper;
	private final static ReflectionProvider reflectionProvider;
	private final static Pattern pattern = Pattern.compile("(_\\d)$");
	private static Class<?> clazz;
	private final static Class<com.foxinmy.weixin4j.mp.payment.v2.RefundRecord> REFUNDRECORD2 = com.foxinmy.weixin4j.mp.payment.v2.RefundRecord.class;
	private final static Class<com.foxinmy.weixin4j.mp.payment.v3.RefundRecord> REFUNDRECORD3 = com.foxinmy.weixin4j.mp.payment.v3.RefundRecord.class;

	static {
		xStream.processAnnotations(new Class[] { REFUNDRECORD2, REFUNDRECORD3,
				com.foxinmy.weixin4j.mp.payment.v2.RefundDetail.class,
				com.foxinmy.weixin4j.mp.payment.v3.RefundDetail.class });
		xStream.aliasField("refund_state", com.foxinmy.weixin4j.mp.payment.v2.RefundDetail.class, "refundStatus");
		xStream.registerConverter(new $());
		mapper = xStream.getMapper();
		reflectionProvider = xStream.getReflectionProvider();
	}

	public static <T> T fromXML(String xml, Class<T> clazz) {
		RefundConverter.clazz = clazz;
		return xStream.fromXML(xml, clazz);
	}

	private static class $ implements Converter {
		@Override
		public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
			return clazz.equals(REFUNDRECORD2) || clazz.equals(REFUNDRECORD3);
		}

		@Override
		public void marshal(Object source, HierarchicalStreamWriter writer,
				MarshallingContext context) {
			new ReflectionConverter(mapper, reflectionProvider).marshal(source,
					writer, context);
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader,
				UnmarshallingContext context) {
			Object refund = null;
			try {
				refund = clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			Matcher matcher = null;
			Map<String, Map<String, String>> outMap = new HashMap<String, Map<String, String>>();
			while (reader.hasMoreChildren()) {
				reader.moveDown();
				String nodeName = reader.getNodeName();
				String fieldName = mapper.realMember(clazz, nodeName);
				Field field = reflectionProvider.getFieldOrNull(clazz,
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
			String detailCanonicalName = clazz.getCanonicalName().replaceFirst(
					"RefundRecord", "RefundDetail");
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
			reflectionProvider.writeField(refund, "details",
					xStream.fromXML(detailXml.toString(), List.class),
					List.class.getDeclaringClass());
			return refund;
		}
	}
}