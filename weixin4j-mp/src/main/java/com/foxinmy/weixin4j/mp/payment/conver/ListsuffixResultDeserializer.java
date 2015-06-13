package com.foxinmy.weixin4j.mp.payment.conver;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.mp.payment.coupon.CouponInfo;
import com.foxinmy.weixin4j.util.ReflectionUtil;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.xml.ListWrapper;
import com.foxinmy.weixin4j.xml.XmlStream;

/**
 * 对 后缀为_$n 的 xml节点反序列化
 * 
 * @className ListsuffixResultDeserializer
 * @author jy
 * @date 2015年3月24日
 * @since JDK 1.7
 * @see
 */
public class ListsuffixResultDeserializer {

	private final static Pattern SUFFIX_PATTERN = Pattern.compile("(_\\d)$");

	private final static Pattern TWO_SUFFIX_PATTERN = Pattern
			.compile("_\\d{1,}_\\d{1,}$");

	/**
	 * 对包含 coupon_id_$n 节点的转换 如V3订单查询接口
	 * 
	 * @param content
	 * @param clazz
	 * @return
	 */
	public static <T> T containCouponDeserialize(String content, Class<T> clazz) {
		return deserialize(content, clazz, "couponList");
	}

	/**
	 * 对包含 refund_id_$n 节点的转换 如V2退款查询接口
	 * 
	 * @param content
	 * @param clazz
	 * @return
	 */
	public static <T> T containRefundDeserialize(String content, Class<T> clazz) {
		return deserialize(content, clazz, "refundList");
	}

	/**
	 * 对同时包含 refund_id_$n 和 coupon_refund_id_$n_$m 节点的转换 如V3退款查询接口
	 * 
	 * @param content
	 * @param clazz
	 * @return
	 */
	public static <T> T containRefundDetailDeserialize(String content,
			Class<T> clazz) {
		T t = XmlStream.fromXML(content, clazz);
		Class<?> wrapperClazz = ReflectionUtil.getFieldGenericType(t,
				"refundList");
		XMLStreamReader xr = null;
		try {
			xr = XMLInputFactory.newInstance().createXMLStreamReader(
					new StringReader(content));
			Matcher matcher = null;
			Map<String, Map<String, String>> refundMap = new HashMap<String, Map<String, String>>();
			Map<String, StringBuilder> couponMap = new HashMap<String, StringBuilder>();
			while (true) {
				int event = xr.next();
				if (event == XMLStreamConstants.END_DOCUMENT) {
					break;
				} else if (event == XMLStreamConstants.START_ELEMENT) {
					String name = xr.getLocalName();
					if ((matcher = SUFFIX_PATTERN.matcher(name)).find()) {
						while (true) {
							event = xr.next();
							if (event == XMLStreamConstants.START_ELEMENT) {
								name = xr.getLocalName();
							} else if (event == XMLStreamConstants.END_ELEMENT) {
								break;
							} else if (event == XMLStreamConstants.CHARACTERS) {
								String key = matcher.group();
								if ((matcher = TWO_SUFFIX_PATTERN.matcher(name))
										.find()) {
									key = matcher.group().replaceFirst(
											SUFFIX_PATTERN.pattern(), "");
									StringBuilder sb = null;
									if ((sb = couponMap.get(key)) == null) {
										sb = new StringBuilder();
										couponMap.put(key, sb);
									}
									String reverserName = new StringBuffer(
											new StringBuilder(name)
													.reverse()
													.toString()
													.replaceFirst("^(\\d_)", ""))
											.reverse().toString();
									sb.append("<").append(reverserName)
											.append(">");
									sb.append(xr.getText());
									sb.append("</").append(reverserName)
											.append(">");
								} else {
									Map<String, String> innerMap = null;
									if ((innerMap = refundMap.get(key)) == null) {
										innerMap = new HashMap<String, String>();
										refundMap.put(key, innerMap);
									}
									innerMap.put(name.replace(key, ""),
											xr.getText());
								}
							}
						}
					}
				}
			}
			if (!refundMap.isEmpty()) {
				String itemName = StringUtil.uncapitalize(wrapperClazz
						.getSimpleName());
				XmlRootElement rootElement = wrapperClazz
						.getAnnotation(XmlRootElement.class);
				if (rootElement != null
						&& StringUtil.isNotBlank(rootElement.name())) {
					try {
						if (!rootElement.name().equals(
								XmlRootElement.class.getMethod("name")
										.getDefaultValue().toString())) {
							itemName = rootElement.name();
						}
					} catch (Exception e) {
						;
					}
				}
				List<Object> refundList = new ArrayList<Object>();
				StringBuilder xmlBuilder = new StringBuilder();
				for (Iterator<Entry<String, Map<String, String>>> refundIt = refundMap
						.entrySet().iterator(); refundIt.hasNext();) {
					xmlBuilder.delete(0, xmlBuilder.length());
					xmlBuilder.append("<").append(itemName).append(">");
					Entry<String, Map<String, String>> refundEntry = refundIt
							.next();
					for (Iterator<Entry<String, String>> refundInnerIt = refundEntry
							.getValue().entrySet().iterator(); refundInnerIt
							.hasNext();) {
						Entry<String, String> entry = refundInnerIt.next();
						xmlBuilder.append("<").append(entry.getKey())
								.append(">");
						xmlBuilder.append(entry.getValue());
						xmlBuilder.append("</").append(entry.getKey())
								.append(">");
					}
					xmlBuilder.append("</").append(itemName).append(">");
					Object refund = XmlStream.fromXML(xmlBuilder.toString(),
							wrapperClazz);
					StringBuilder couponXml = couponMap.get(refundEntry
							.getKey());
					if (couponXml != null) {
						ListWrapper<?> listWrapper = toListWrapper(
								String.format("<xml>%s</xml>",
										couponXml.toString()), CouponInfo.class);
						if (listWrapper != null) {
							ReflectionUtil.invokeSetterMethod(refund,
									"couponList", listWrapper.getItems(),
									List.class);
						}
					}
					refundList.add(refund);
				}
				ReflectionUtil.invokeSetterMethod(t, "refundList", refundList,
						List.class);
			}
		} catch (XMLStreamException e) {
			throw new IllegalArgumentException(e);
		} finally {
			try {
				if (xr != null) {
					xr.close();
				}
			} catch (XMLStreamException e) {
				;
			}
		}
		return t;
	}

	public static <T> T deserialize(String content, Class<T> clazz,
			String listPropertyName) {
		T t = XmlStream.fromXML(content, clazz);
		Class<?> wrapperClazz = ReflectionUtil.getFieldGenericType(t,
				listPropertyName);
		ListWrapper<?> listWrapper = toListWrapper(content, wrapperClazz);
		if (listWrapper != null) {
			ReflectionUtil.invokeSetterMethod(t, listPropertyName,
					listWrapper.getItems(), List.class);
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	public static <T> ListWrapper<T> toListWrapper(String content,
			Class<T> clazz) {
		XMLStreamReader xr = null;
		XMLStreamWriter xw = null;
		try {
			xr = XMLInputFactory.newInstance().createXMLStreamReader(
					new StringReader(content));
			Matcher matcher = null;
			Map<String, Map<String, String>> outMap = new HashMap<String, Map<String, String>>();
			while (true) {
				int event = xr.next();
				if (event == XMLStreamConstants.END_DOCUMENT) {
					break;
				} else if (event == XMLStreamConstants.START_ELEMENT) {
					String name = xr.getLocalName();
					if ((matcher = SUFFIX_PATTERN.matcher(name)).find()) {
						while (true) {
							event = xr.next();
							if (event == XMLStreamConstants.START_ELEMENT) {
								name = xr.getLocalName();
							} else if (event == XMLStreamConstants.END_ELEMENT) {
								break;
							} else if (event == XMLStreamConstants.CHARACTERS) {
								String key = matcher.group();
								Map<String, String> innerMap = null;
								if ((innerMap = outMap.get(key)) == null) {
									innerMap = new HashMap<String, String>();
									outMap.put(key, innerMap);
								}
								innerMap.put(name.replace(key, ""),
										xr.getText());
							}
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
					} catch (Exception e) {
						;
					}
				}
				for (Iterator<Entry<String, Map<String, String>>> outIt = outMap
						.entrySet().iterator(); outIt.hasNext();) {
					xw.writeStartElement(itemName);
					for (Iterator<Entry<String, String>> innerIt = outIt.next()
							.getValue().entrySet().iterator(); innerIt
							.hasNext();) {
						Entry<String, String> entry = innerIt.next();
						xw.writeStartElement(entry.getKey());
						xw.writeCharacters(entry.getValue());
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
}
