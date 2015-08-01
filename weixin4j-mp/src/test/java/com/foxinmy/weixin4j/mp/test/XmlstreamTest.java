package com.foxinmy.weixin4j.mp.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.payment.v2.RefundRecordV2;
import com.foxinmy.weixin4j.payment.mch.Order;
import com.foxinmy.weixin4j.xml.ListsuffixResultDeserializer;
import com.foxinmy.weixin4j.xml.XmlStream;

public class XmlstreamTest {

	public static void object2xmlWithRootElement() {
		Token token = new Token();
		token.setAccessToken("accessToken");
		token.setExpiresIn(12);
		token.setTime(13l);
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

	/*
	 * public static String errorXml() { StringBuffer xml = new StringBuffer();
	 * String url =
	 * "http://qydev.weixin.qq.com/wiki/index.php?title=%E5%85%A8%E5%B1%80%E8%BF%94%E5%9B%9E%E7%A0%81%E8%AF%B4%E6%98%8E"
	 * ; try { Document doc = Jsoup.parse(new URL(url), 5000); Elements eles =
	 * doc.getElementsByTag("tr"); for (Element ele : eles) {
	 * xml.append("<error>"); xml.append("<code>").append(ele.child(0).text())
	 * .append("</code>"); xml.append("<text>").append(ele.child(1).text())
	 * .append("</text>"); xml.append("</error>"); } } catch (Exception e) {
	 * e.printStackTrace(); } return xml.toString(); }
	 */

	public static void main(String[] args) throws Exception {
		// map2xml();
		// xml2map();
		// xml2order();
		// System.err.println(xml2refundRecordV2());
		// xml2refundRecordV3();
		// object2xmlWithoutRootElement();
		/*
		 * RefundRecord refundRecord = xml2refundRecordV2();
		 * System.err.println(refundRecord); String sign =
		 * refundRecord.getSign(); refundRecord.setSign(null); String validSign
		 * = PayUtil.paysignMd5(refundRecord, "paysignkey");
		 * System.err.println("sign=" + sign + ",validSign=" + validSign);
		 * System.err.println(ListsuffixResultSerializer
		 * .serializeToXML(refundRecord));
		 */
		// System.out.println(errorXml());
	}
}
