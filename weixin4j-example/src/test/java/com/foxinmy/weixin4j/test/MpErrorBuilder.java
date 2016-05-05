package com.foxinmy.weixin4j.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sun.org.mozilla.javascript.internal.NativeArray;
import sun.org.mozilla.javascript.internal.NativeObject;

/**
 * 收集微信公众平台错误码
 * 
 * @className MpErrorBuilder
 * @author jy
 * @date 2016年5月5日
 * @since JDK 1.7
 * @see
 */
public class MpErrorBuilder {

	private static final String HOME = "https://mp.weixin.qq.com/wiki";

	private static final String RESOURCE = "https://mp.weixin.qq.com/wiki?action=doc&id=%s";

	private static List<String> collectUrl() throws Exception {
		Document root = Jsoup.connect(HOME).get();
		List<String> resources = null;
		Elements eles = root.getElementById("resMenu").getElementsByTag("a");
		if (eles.isEmpty()) {
			Element ele = root.getElementsByTag("script").last();
			StringBuilder script = new StringBuilder();
			script.append("window = {};");
			script.append("seajs = {};");
			script.append("seajs.use = function(arg1,arg2){return window.cgiData.list};");
			script.append("wx_main = {};");
			script.append(ele.html());
			ScriptEngine engine = new ScriptEngineManager()
					.getEngineByName("javascript");
			NativeArray na = (NativeArray) ((NativeObject) engine.eval(script
					.toString())).get("list");
			resources = new ArrayList<String>();
			for (int i = 0; i < na.getLength(); i++) {
				recurrenceMenu(resources, (NativeObject) na.get(i));
			}
		} else {
			resources = new ArrayList<String>(eles.size());
			for (Element ele : eles) {
				resources.add(String.format(RESOURCE,
						ele.getElementsByAttribute("data-id")));
			}
		}
		return resources;
	}

	private static void recurrenceMenu(List<String> resources,
			NativeObject rootObject) {
		NativeArray children = (NativeArray) rootObject.get("children");
		if (children.getLength() == 0l) {
			resources.add(String.format(RESOURCE, rootObject.get("id")
					.toString()));
			return;
		}
		for (int i = 0; i < children.getLength(); i++) {
			recurrenceMenu(resources, (NativeObject) children.get(i));
		}
	}

	private static Map<String, String> analyCode(String resource)
			throws Exception {
		Document root = Jsoup.connect(resource).get();
		Elements tables = root.getElementsByTag("table");
		Map<String, String> error = new HashMap<String, String>();
		for (Element table : tables) {
			Elements trs = table.getElementsByTag("tr");
			String text = trs.first().child(0).text().trim();
			if (text.equals("返回码") || text.equals("错误码")) {
				for (int i = 1; i < trs.size(); i++) {
					error.put(trs.get(i).child(0).text().trim(), trs.get(i)
							.child(1).text().trim());
				}
			}
		}
		return error;
	}

	public static Map<String, String> build() throws Exception {
		System.err.println("0.开始收集URI资源...");
		List<String> resources = collectUrl();
		System.err.println("共收集到URI资源:" + resources.size());
		System.err.println("1.开始解析URI资源...");
		Map<String, String> error = new HashMap<String, String>();
		for (int i = 0; i < resources.size(); i++) {
			System.err
					.println("开始解析第" + (i + 1) + "个URI资源:" + resources.get(i));
			Map<String, String> result = analyCode(resources.get(i));
			System.err.println(resources.get(i) + ":" + result.size());
			error.putAll(result);
		}
		return error;
	}
}
