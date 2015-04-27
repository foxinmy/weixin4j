package com.foxinmy.weixin4j.mp.payment.conver;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.foxinmy.weixin4j.mp.payment.coupon.CouponInfo;
import com.foxinmy.weixin4j.mp.payment.v3.Order;
import com.foxinmy.weixin4j.mp.payment.v3.RefundResult;
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
 * V3订单详情转换类
 * 
 * @className OrderConverter
 * @author jy
 * @date 2015年3月24日
 * @since JDK 1.7
 * @see
 */
public class CouponConverter {

	private final static XmlStream xStream = XmlStream.get();
	private final static Mapper mapper;
	private final static ReflectionProvider reflectionProvider;
	private final static Pattern pattern = Pattern.compile("(_\\d)$");
	private static Class<CouponInfo> COUPON_CLASS = CouponInfo.class;
	private static Class<?> clazz;

	static {
		xStream.processAnnotations(new Class[] { COUPON_CLASS });
		xStream.registerConverter(new $());
		mapper = xStream.getMapper();
		reflectionProvider = xStream.getReflectionProvider();
	}

	public static <T> T fromXML(String xml, Class<T> clazz) {
		CouponConverter.clazz = clazz;
		xStream.processAnnotations(clazz);
		return xStream.fromXML(xml, clazz);
	}

	private static class $ implements Converter {
		@Override
		public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
			return clazz.equals(Order.class)
					|| clazz.equals(RefundResult.class);
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
			Object object = null;
			try {
				object = clazz.newInstance();
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
					Object value = context.convertAnother(object,
							field.getType());
					reflectionProvider.writeField(object, fieldName, value,
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
			if (!outMap.isEmpty()) {
				StringBuilder couponXml = new StringBuilder();
				couponXml.append("<list>");
				for (Iterator<Entry<String, Map<String, String>>> outIt = outMap
						.entrySet().iterator(); outIt.hasNext();) {
					couponXml.append("<")
							.append(COUPON_CLASS.getCanonicalName())
							.append(">");
					for (Iterator<Entry<String, String>> innerIt = outIt.next()
							.getValue().entrySet().iterator(); innerIt
							.hasNext();) {
						Entry<String, String> entry = innerIt.next();
						couponXml.append("<").append(entry.getKey())
								.append(">");
						couponXml.append(entry.getValue());
						couponXml.append("</").append(entry.getKey())
								.append(">");
					}
					couponXml.append("</")
							.append(COUPON_CLASS.getCanonicalName())
							.append(">");
				}
				couponXml.append("</list>");
				reflectionProvider.writeField(object, "couponList",
						xStream.fromXML(couponXml.toString(), List.class),
						List.class.getDeclaringClass());
			}
			return object;
		}
	}
}
