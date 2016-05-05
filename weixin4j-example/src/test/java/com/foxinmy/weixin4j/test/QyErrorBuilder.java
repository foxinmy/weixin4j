package com.foxinmy.weixin4j.test;

import java.util.Map;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 收集微信企业号错误码
 * 
 * @className QyErrorBuilder
 * @author jy
 * @date 2016年5月5日
 * @since JDK 1.7
 * @see
 */
public class QyErrorBuilder {

	private static final String HOME = "http://qydev.weixin.qq.com/wiki/index.php?title=%E5%85%A8%E5%B1%80%E8%BF%94%E5%9B%9E%E7%A0%81%E8%AF%B4%E6%98%8E";

	private static Map<String, String> analyCode() throws Exception {
		Document root = Jsoup.connect(HOME).get();
		Elements trs = root.getElementsByTag("tr");
		Map<String, String> error = new TreeMap<String, String>();
		for (int i = 1; i < trs.size(); i++) {
			error.put(trs.get(i).child(0).text().trim(), trs.get(i).child(1)
					.text().trim());
		}
		return error;
	}

	public static Map<String, String> build() throws Exception {
		System.err.println("开始解析URI资源:" + HOME);
		Map<String, String> error = analyCode();
		System.err.println("共收集到状态码:" + error.size());
		return error;
	}
}
