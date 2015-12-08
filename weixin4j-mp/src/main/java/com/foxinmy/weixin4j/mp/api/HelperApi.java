package com.foxinmy.weixin4j.mp.api;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Button;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.model.AutoReplySetting;
import com.foxinmy.weixin4j.mp.model.MenuSetting;
import com.foxinmy.weixin4j.mp.model.SemQuery;
import com.foxinmy.weixin4j.mp.model.SemResult;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.tuple.MpArticle;

/**
 * 辅助相关API
 * 
 * @className HelperApi
 * @author jy.hu
 * @date 2014年9月26日
 * @since JDK 1.6
 * @see
 */
public class HelperApi extends MpApi {

	private final TokenHolder tokenHolder;

	public HelperApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 长链接转短链接
	 * 
	 * @param url
	 *            待转换的链接
	 * @return 短链接
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/10/165c9b15eddcfbd8699ac12b0bd89ae6.html">长链接转短链接</a>
	 */
	public String getShorturl(String url) throws WeixinException {
		String shorturl_uri = getRequestUri("shorturl_uri");
		Token token = tokenHolder.getToken();
		JSONObject obj = new JSONObject();
		obj.put("action", "long2short");
		obj.put("long_url", url);
		WeixinResponse response = weixinExecutor.post(
				String.format(shorturl_uri, token.getAccessToken()),
				obj.toJSONString());

		return response.getAsJson().getString("short_url");
	}

	/**
	 * 语义理解
	 * 
	 * @param semQuery
	 *            语义理解协议
	 * @return 语义理解结果
	 * @see com.foxinmy.weixin4j.mp.model.SemQuery
	 * @see com.foxinmy.weixin4j.mp.model.SemResult
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/0/0ce78b3c9524811fee34aba3e33f3448.html">语义理解</a>
	 * @throws WeixinException
	 */
	public SemResult semantic(SemQuery semQuery) throws WeixinException {
		String semantic_uri = getRequestUri("semantic_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.post(
				String.format(semantic_uri, token.getAccessToken()),
				semQuery.toJson());
		return response.getAsObject(new TypeReference<SemResult>() {
		});
	}

	/**
	 * 获取微信服务器IP地址
	 * 
	 * @return IP地址
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/0/2ad4b6bfd29f30f71d39616c2a0fcedc.html">获取IP地址</a>
	 * @throws WeixinException
	 */
	public List<String> getCallbackip() throws WeixinException {
		String getcallbackip_uri = getRequestUri("getcallbackip_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.post(String.format(getcallbackip_uri,
				token.getAccessToken()));
		return JSON.parseArray(response.getAsJson().getString("ip_list"),
				String.class);
	}

	/**
	 * 获取公众号当前使用的自定义菜单的配置，如果公众号是通过API调用设置的菜单，则返回菜单的开发配置，
	 * 而如果公众号是在公众平台官网通过网站功能发布菜单，则本接口返回运营者设置的菜单配置。
	 * 
	 * @return 菜单集合
	 * @see {@link MenuApi#getMenu()}
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/17/4dc4b0514fdad7a5fbbd477aa9aab5ed.html">获取自定义菜单配置</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 * @see com.foxinmy.weixin4j.mp.model.MenuSetting
	 * @see com.foxinmy.weixin4j.tuple.MpArticle
	 * @throws WeixinException
	 */
	public MenuSetting getMenuSetting() throws WeixinException {
		String menu_get_selfmenu_uri = getRequestUri("menu_get_selfmenu_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.get(String.format(menu_get_selfmenu_uri,
				token.getAccessToken()));
		JSONObject result = response.getAsJson();

		JSONArray buttons = result.getJSONObject("selfmenu_info").getJSONArray(
				"button");
		List<Button> buttonList = new ArrayList<Button>(buttons.size());
		JSONObject buttonObj = null;
		for (int i = 0; i < buttons.size(); i++) {
			buttonObj = buttons.getJSONObject(i);
			if (buttonObj.containsKey("sub_button")) {
				JSONPath.set(buttonObj, "$.sub_button", buttonObj
						.getJSONObject("sub_button").getJSONArray("list"));
			}
			buttonList.add(JSON.parseObject(buttonObj.toJSONString(),
					Button.class, ButtonExtraProcessor.global));
		}
		return new MenuSetting(result.getBooleanValue("is_menu_open"),
				buttonList);
	}

	private static final class ButtonExtraProcessor implements ExtraProcessor {
		private static ButtonExtraProcessor global = new ButtonExtraProcessor();

		private ButtonExtraProcessor() {
		}

		@Override
		public void processExtra(Object object, String key, Object value) {
			if (key.equals("news_info")) {
				JSONArray news = ((JSONObject) value).getJSONArray("list");
				List<MpArticle> newsList = new ArrayList<MpArticle>(news.size());
				for (int i = 0; i < news.size(); i++) {
					newsList.add(JSON.parseObject(news.getString(i),
							MpArticle.class, ArticleExtraProcessor.global));
				}
				JSONPath.set(object, "$.content", newsList);
			} else {
				JSONPath.set(object, "$.content", value);
			}
		}
	};

	private static final class ArticleExtraProcessor implements ExtraProcessor {
		private static final ArticleExtraProcessor global = new ArticleExtraProcessor();

		private ArticleExtraProcessor() {

		}

		@Override
		public void processExtra(Object object, String key, Object value) {
			MpArticle mpArticle = (MpArticle) object;
			if (key.equals("show_cover")) {
				mpArticle.setShowCoverPic(value.equals("1"));
			}
			if (key.equals("source_url")) {
				mpArticle.setSourceUrl(value.toString());
			}
		}
	}

	/**
	 * 获取公众号当前使用的自动回复规则，包括关注后自动回复、消息自动回复（60分钟内触发一次）、关键词自动回复。
	 * 
	 * @see com.foxinmy.weixin4j.mp.model.AutoReplySetting
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/7/7b5789bb1262fb866d01b4b40b0efecb.html">获取自动回复规则</a>
	 * @throws WeixinException
	 */
	public AutoReplySetting getAutoReplySetting() throws WeixinException {
		String autoreply_setting_get_uri = getRequestUri("autoreply_setting_get_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.get(String.format(
				autoreply_setting_get_uri, token.getAccessToken()));

		JSONObject result = response.getAsJson();
		AutoReplySetting replySetting = JSON.toJavaObject(result,
				AutoReplySetting.class);
		List<AutoReplySetting.Rule> ruleList = null;
		if (result.containsKey("keyword_autoreply_info")) {
			JSONArray keywordList = result.getJSONObject(
					"keyword_autoreply_info").getJSONArray("list");
			ruleList = new ArrayList<AutoReplySetting.Rule>(keywordList.size());
			JSONObject keywordObj = null;
			JSONArray replyList = null;
			JSONObject replyObj = null;
			for (int i = 0; i < keywordList.size(); i++) {
				keywordObj = keywordList.getJSONObject(i);
				AutoReplySetting.Rule rule = JSON.toJavaObject(keywordObj,
						AutoReplySetting.Rule.class);
				replyList = keywordObj.getJSONArray("reply_list_info");
				List<AutoReplySetting.Entry> entryList = new ArrayList<AutoReplySetting.Entry>(
						replyList.size());
				for (int j = 0; j < replyList.size(); j++) {
					replyObj = replyList.getJSONObject(j);
					if (replyObj.getString("type").equals("news")) {
						entryList.add(JSON.parseObject(replyObj.toJSONString(),
								AutoReplySetting.Entry.class,
								ButtonExtraProcessor.global));
					} else {
						entryList.add(JSON.toJavaObject(replyObj,
								AutoReplySetting.Entry.class));
					}
				}
				rule.setReplyList(entryList);
				ruleList.add(rule);
			}
		}
		replySetting.setKeywordReplyList(ruleList);
		return replySetting;
	}
}