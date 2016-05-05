package com.foxinmy.weixin4j.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 手机微信商户平台错误码
 * 
 * @className PayErrorBuilder
 * @author jy
 * @date 2016年5月5日
 * @since JDK 1.7
 * @see
 */
public class PayErrorBuilder {

	private static final String HOME = "https://pay.weixin.qq.com/wiki/doc/api/index.html";

	private static final List<String> EXTRA_RESOURCES;
	static {
		EXTRA_RESOURCES = new ArrayList<String>();
		EXTRA_RESOURCES
				.add("https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_3#");
		EXTRA_RESOURCES
				.add("https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_4");
		EXTRA_RESOURCES
				.add("https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_5");
		EXTRA_RESOURCES
				.add("https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_5");
		EXTRA_RESOURCES
				.add("https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_7&index=6");
		EXTRA_RESOURCES
				.add("https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=16_5");
		EXTRA_RESOURCES
				.add("https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=16_6");
		EXTRA_RESOURCES
				.add("https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2");
		EXTRA_RESOURCES
				.add("https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_3");
		EXTRA_RESOURCES
				.add("https://pay.weixin.qq.com/wiki/doc/api/external/declarecustom.php?chapter=18_1");
		EXTRA_RESOURCES
				.add("https://pay.weixin.qq.com/wiki/doc/api/external/declarecustom.php?chapter=18_2");
	}

	private static List<String> collectUrl() throws Exception {
		Document root = Jsoup.connect(HOME).get();
		List<String> resources = new ArrayList<String>();
		Elements eles = root.getElementsByClass("guide-main");
		for (Element ele : eles) {
			for (Element li : ele.children()) {
				System.err.println(li.child(0).child(1).text() + "资源列表:");
				root = Jsoup.connect(li.child(0).absUrl("href")).get();
				Elements dls = root.getElementsByClass("menu").first()
						.getElementsByTag("dl");
				for (Element dl : dls) {
					if (dl.child(0).text().equalsIgnoreCase("api列表")) {
						for (int i = 1; i < dl.children().size(); i++) {
							System.err.println(dl.children().get(i).child(0)
									.text());
							resources.add(dl.children().get(i).child(0)
									.absUrl("href"));
						}
						break;
					}
				}
			}
		}
		resources.addAll(EXTRA_RESOURCES);
		return resources;
	}

	private static Map<String, String> analyCode(String resource)
			throws Exception {
		Document root = Jsoup.connect(resource).get();
		Elements eles = root.getElementsByClass("data-box");
		Map<String, String> error = new HashMap<String, String>();
		String text = "";
		StringBuilder desc = new StringBuilder();
		for (Element box : eles) {
			Elements trs = box.getElementsByTag("tr");
			if (trs.isEmpty()) {
				continue;
			}
			text = box.child(0).text().trim();
			boolean b1 = text.equals("错误码") || text.equals("返回码");
			text = trs.first().text().trim();
			boolean b2 = text.equals("错误码") || text.equals("返回码");
			if (b1 || b2) {
				for (int i = 1; i < trs.size(); i++) {
					desc.append(trs.get(i).child(1).text().trim());
					for (int j = 2; j < trs.get(i).children().size(); j++) {
						desc.append(",").append(
								trs.get(i).child(j).text().trim());
					}
					error.put(trs.get(i).child(0).text().trim(),
							desc.toString());
					desc.delete(0, desc.length());
				}
			}
		}
		return error;
	}

	public static Map<String, String> builder() throws Exception {
		System.err.println("0.开始收集URI资源...");
		List<String> resources = collectUrl();
		System.err.println("共收集到URI资源:" + resources.size());
		System.err.println("1.开始解析URI资源...");
		Map<String, String> error = new TreeMap<String, String>();
		for (int i = 0; i < resources.size(); i++) {
			System.err
					.println("开始解析第" + (i + 1) + "个URI资源:" + resources.get(i));
			Map<String, String> result = analyCode(resources.get(i));
			System.err.println(resources.get(i) + ":" + result.size());
			error.putAll(result);
		}
		System.err.println("共收集到状态码:" + error.size());
		return error;
	}
}
