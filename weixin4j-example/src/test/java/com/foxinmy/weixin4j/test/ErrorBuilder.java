package com.foxinmy.weixin4j.test;

import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class ErrorBuilder {

	public static void main(String[] args) throws Exception {
		Map<String, String> error = new TreeMap<String, String>();
		error.putAll(QyErrorBuilder.build());
		error.putAll(MpErrorBuilder.build());
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<!-- 公众平台错误码:https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433747234&token=&lang=zh_CN -->");
		xml.append("<!-- 企业号错误码:http://qydev.weixin.qq.com/wiki/index.php?title=%E5%85%A8%E5%B1%80%E8%BF%94%E5%9B%9E%E7%A0%81%E8%AF%B4%E6%98%8E -->");
		xml.append("<errors>");
		for (Entry<String, String> entry : error.entrySet()) {
			xml.append("<error>");
			xml.append("<code>").append(entry.getKey()).append("</code>");
			xml.append("<text>").append(entry.getValue()).append("</text>");
			xml.append("</error>");
		}
		xml.append("<!-- 商户平台错误码 -->");
		error = PayErrorBuilder.builder();
		for (Entry<String, String> entry : error.entrySet()) {
			xml.append("<error>");
			xml.append("<code>").append(entry.getKey()).append("</code>");
			xml.append("<text>").append(entry.getValue()).append("</text>");
			xml.append("</error>");
		}
		xml.append("</errors>");
		System.err.println("\n");
		System.err.println(xml.toString());
	}
}
