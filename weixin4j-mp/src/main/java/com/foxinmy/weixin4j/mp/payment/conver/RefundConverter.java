package com.foxinmy.weixin4j.mp.payment.conver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.foxinmy.weixin4j.mp.payment.coupon.CouponInfo;
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
	private final static Pattern REFUND_PATTERN = Pattern.compile("_\\d{1,}$");
	private final static Pattern COUPON_PATTERN = Pattern
			.compile("_\\d{1,}_\\d{1,}$");
	private static Class<?> clazz;
	private final static Class<CouponInfo> COUPON_CLASS = CouponInfo.class;
	private final static Class<com.foxinmy.weixin4j.mp.payment.v2.RefundRecord> REFUNDRECORD2 = com.foxinmy.weixin4j.mp.payment.v2.RefundRecord.class;
	private final static Class<com.foxinmy.weixin4j.mp.payment.v3.RefundRecord> REFUNDRECORD3 = com.foxinmy.weixin4j.mp.payment.v3.RefundRecord.class;

	static {
		xStream.processAnnotations(new Class[] { REFUNDRECORD2, REFUNDRECORD3 });
		xStream.aliasField("refund_state",
				com.foxinmy.weixin4j.mp.payment.v2.RefundDetail.class,
				"refundStatus");
		xStream.registerConverter(new $());
		mapper = xStream.getMapper();
		reflectionProvider = xStream.getReflectionProvider();
	}

	public static <T> T fromXML(String xml, Class<T> clazz) {
		RefundConverter.clazz = clazz;
		xStream.processAnnotations(clazz);
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
			Map<String, Map<String, String>> refundMap = new HashMap<String, Map<String, String>>();
			Map<String, Map<String, String>> couponMap = new HashMap<String, Map<String, String>>();
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
				} else if ((matcher = REFUND_PATTERN.matcher(nodeName)).find()) {
					String key = matcher.group();
					Map<String, String> innerMap = null;
					if ((matcher = COUPON_PATTERN.matcher(nodeName)).find()) {
						key = matcher.group();
						if ((innerMap = couponMap.get(key)) == null) {
							innerMap = new HashMap<String, String>();
							couponMap.put(key, innerMap);
						}
					} else {
						if ((innerMap = refundMap
								.get(String.format("%s_", key))) == null) {
							innerMap = new HashMap<String, String>();
							refundMap.put(String.format("%s_", key), innerMap);
						}
					}
					innerMap.put(nodeName.replaceFirst(key, ""),
							reader.getValue());
				}
				reader.moveUp();
			}
			if (!refundMap.isEmpty()) {
				StringBuilder detailXml = new StringBuilder();
				detailXml.append("<list>");
				String detailCanonicalName = clazz.getCanonicalName()
						.replaceFirst("RefundRecord", "RefundDetail");
				for (Iterator<Entry<String, Map<String, String>>> refundIT = refundMap
						.entrySet().iterator(); refundIT.hasNext();) {
					detailXml.append("<").append(detailCanonicalName)
							.append(">");
					Entry<String, Map<String, String>> refundEntry = refundIT
							.next();
					for (Iterator<Entry<String, String>> innerIt = refundEntry
							.getValue().entrySet().iterator(); innerIt
							.hasNext();) {
						Entry<String, String> entry = innerIt.next();
						detailXml.append("<").append(entry.getKey())
								.append(">");
						detailXml.append(entry.getValue());
						detailXml.append("</").append(entry.getKey())
								.append(">");
					}
					if (!couponMap.isEmpty()) {
						detailXml.append("<couponList class=\"")
								.append(ArrayList.class.getCanonicalName())
								.append("\">");
						Iterator<Entry<String, Map<String, String>>> couponIT = couponMap
								.entrySet().iterator();
						while (couponIT.hasNext()) {
							Entry<String, Map<String, String>> couponEntry = couponIT
									.next();
							if (couponEntry.getKey().startsWith(
									refundEntry.getKey())) {
								detailXml
										.append("<")
										.append(COUPON_CLASS.getCanonicalName())
										.append(">");
								for (Iterator<Entry<String, String>> innerIt = couponEntry
										.getValue().entrySet().iterator(); innerIt
										.hasNext();) {
									Entry<String, String> entry = innerIt
											.next();
									detailXml.append("<")
											.append(entry.getKey()).append(">");
									detailXml.append(entry.getValue());
									detailXml.append("</")
											.append(entry.getKey()).append(">");
								}
								detailXml
										.append("</")
										.append(COUPON_CLASS.getCanonicalName())
										.append(">");
								couponIT.remove();
							}
						}
						detailXml.append("</couponList>");
					}
					detailXml.append("</").append(detailCanonicalName)
							.append(">");
				}
				detailXml.append("</list>");
				reflectionProvider.writeField(refund, "details",
						xStream.fromXML(detailXml.toString(), List.class),
						List.class.getDeclaringClass());
			}
			return refund;
		}
	}
}