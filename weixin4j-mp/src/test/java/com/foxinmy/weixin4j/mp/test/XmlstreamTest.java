package com.foxinmy.weixin4j.mp.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.oldpayment.RefundRecordV2;
import com.foxinmy.weixin4j.payment.mch.Order;
import com.foxinmy.weixin4j.xml.ListsuffixResultDeserializer;
import com.foxinmy.weixin4j.xml.XmlStream;

public class XmlstreamTest {

	public static void object2xmlWithRootElement() {
		Token token = new Token("accessToken", 12l, 13l);
		String content = XmlStream.toXML(token);
		System.err.println(content);
	}

	public static void object2xmlWithoutRootElement() {
		String content = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><xml><accessToken>accessToken</accessToken><expiresIn>12</expiresIn><time>13</time></xml>";
		System.err.println(XmlStream.fromXML(content, Token.class));
	}

	public static void xml2objectWithRootElement() {

	}

	public static void xml2objectWithoutRootElement() {

	}

	public static void map2xml() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "weixin4j");
		map.put("year", "2015");
		System.err.println(XmlStream.map2xml(map));
	}

	public static void xml2map() {
		String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xml><name><![CDATA[weixin4j]]></name><year><![CDATA[2015]]></year></xml>";
		System.err.println(XmlStream.xml2map(content));
	}

	public static void xml2order() throws Exception {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					"/Users/jy/Downloads/order.xml"));
			String data = null;// 一次读入一行，直到读入null为文件结束
			while ((data = br.readLine()) != null) {
				sb.append(data);
			}
			br.close();
		} catch (Exception e) {

		}
		System.err.println(ListsuffixResultDeserializer.deserialize(
				sb.toString(), Order.class));
	}

	public static RefundRecordV2 xml2refundRecordV2() throws Exception {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					"/Users/jy/Downloads/refund_record2.xml"));
			String data = null;// 一次读入一行，直到读入null为文件结束
			while ((data = br.readLine()) != null) {
				sb.append(data);
			}
			br.close();
		} catch (Exception e) {

		}
		return ListsuffixResultDeserializer.deserialize(sb.toString(),
				RefundRecordV2.class);
	}

	public static void xml2refundRecordV3() throws Exception {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					"/Users/jy/Downloads/refund_record3.xml"));
			String data = null;// 一次读入一行，直到读入null为文件结束
			while ((data = br.readLine()) != null) {
				sb.append(data);
			}
			br.close();
		} catch (Exception e) {

		}
		System.err.println(ListsuffixResultDeserializer.deserialize(
				sb.toString(),
				com.foxinmy.weixin4j.payment.mch.RefundRecord.class));
	}
}
