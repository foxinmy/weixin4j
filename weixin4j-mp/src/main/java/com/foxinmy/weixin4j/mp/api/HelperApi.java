package com.foxinmy.weixin4j.mp.api;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Button;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.model.AutoReplySetting;
import com.foxinmy.weixin4j.mp.model.MenuSetting;
import com.foxinmy.weixin4j.mp.model.SemQuery;
import com.foxinmy.weixin4j.mp.model.SemResult;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.tuple.MpArticle;

/**
 * 辅助相关API
 *
 * @className HelperApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月26日
 * @since JDK 1.6
 * @see
 */
public class HelperApi extends MpApi {

    private final TokenManager tokenManager;

    public HelperApi(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    /**
     * 长链接转短链接
     *
     * @param url
     *            待转换的链接
     * @return 短链接
     * @throws WeixinException
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433600&token=&lang=zh_CN">长链接转短链接</a>
     */
    public String getShorturl(String url) throws WeixinException {
        String shorturl_uri = getRequestUri("shorturl_uri");
        Token token = tokenManager.getCache();
        JSONObject obj = new JSONObject();
        obj.put("action", "long2short");
        obj.put("long_url", url);
        WeixinResponse response = weixinExecutor.post(String.format(shorturl_uri, token.getAccessToken()),
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
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141241&token=&lang=zh_CN">语义理解</a>
     * @throws WeixinException
     */
    public SemResult semantic(SemQuery semQuery) throws WeixinException {
        String semantic_uri = getRequestUri("semantic_uri");
        Token token = tokenManager.getCache();
        WeixinResponse response = weixinExecutor.post(String.format(semantic_uri, token.getAccessToken()),
                semQuery.toJson());
        return response.getAsObject(new TypeReference<SemResult>() {
        });
    }

    /**
     * 获取微信服务器IP地址
     *
     * @return IP地址
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140187&token=&lang=zh_CN">获取IP地址</a>
     * @throws WeixinException
     */
    public List<String> getWechatServerIp() throws WeixinException {
        String getcallbackip_uri = getRequestUri("getcallbackip_uri");
        Token token = tokenManager.getCache();
        WeixinResponse response = weixinExecutor.get(String.format(getcallbackip_uri, token.getAccessToken()));
        return JSON.parseArray(response.getAsJson().getString("ip_list"), String.class);
    }

    /**
     * 获取公众号当前使用的自定义菜单的配置，如果公众号是通过API调用设置的菜单，则返回菜单的开发配置，
     * 而如果公众号是在公众平台官网通过网站功能发布菜单，则本接口返回运营者设置的菜单配置。
     *
     * @return 菜单配置信息
     * @see {@link MenuApi#getMenu()}
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1434698695&token=&lang=zh_CN">获取自定义菜单配置</a>
     * @see com.foxinmy.weixin4j.model.Button
     * @see com.foxinmy.weixin4j.mp.model.MenuSetting
     * @see com.foxinmy.weixin4j.tuple.MpArticle
     * @throws WeixinException
     */
    public MenuSetting getMenuSetting() throws WeixinException {
        String menu_get_selfmenu_uri = getRequestUri("menu_get_selfmenu_uri");
        Token token = tokenManager.getCache();
        WeixinResponse response = weixinExecutor.get(String.format(menu_get_selfmenu_uri, token.getAccessToken()));
        JSONObject result = response.getAsJson();
        JSONArray buttons = result.getJSONObject("selfmenu_info").getJSONArray("button");
        List<Button> buttonList = new ArrayList<Button>(buttons.size());
        JSONObject buttonObj = null;
        for (int i = 0; i < buttons.size(); i++) {
            buttonObj = buttons.getJSONObject(i);
            if (buttonObj.containsKey("sub_button")) {
                buttonObj.put("sub_button", buttonObj.getJSONObject("sub_button").getJSONArray("list"));
                buttonObj.put("type", "popups");
            }
            buttonList.add(JSON.parseObject(buttonObj.toJSONString(), Button.class, ButtonExtraProcessor.global));
        }
        return new MenuSetting(result.getBooleanValue("is_menu_open"), buttonList);
    }

    private static final class ButtonExtraProcessor implements ExtraProcessor {
        private static ButtonExtraProcessor global = new ButtonExtraProcessor();
        private static final String KEY = "news_info";

        private ButtonExtraProcessor() {
        }

        @Override
        public void processExtra(Object object, String key, Object value) {
            if (KEY.equalsIgnoreCase(key)) {
                JSONArray news = ((JSONObject) value).getJSONArray("list");
                List<MpArticle> newsList = new ArrayList<MpArticle>(news.size());
                JSONObject article = null;
                for (int i = 0; i < news.size(); i++) {
                    article = news.getJSONObject(i);
                    article.put("show_cover_pic", article.remove("show_cover"));
                    article.put("thumb_url", article.remove("cover_url"));
                    article.put("url", article.remove("content_url"));
                    article.put("content_source_url", article.remove("source_url"));
                    newsList.add(JSON.toJavaObject(article, MpArticle.class));
                }
                ((Button) object).setExtra(newsList);
            } else {
                ((Button) object).setContent(String.valueOf(value));
            }
        }
    };

    /**
     * 获取公众号当前使用的自动回复规则，包括关注后自动回复、消息自动回复（60分钟内触发一次）、关键词自动回复。
     *
     * @see com.foxinmy.weixin4j.mp.model.AutoReplySetting
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751299&token=&lang=zh_CN">获取自动回复规则</a>
     * @return 自定义回复配置信息
     * @throws WeixinException
     */
    public AutoReplySetting getAutoReplySetting() throws WeixinException {
        String autoreply_setting_get_uri = getRequestUri("autoreply_setting_get_uri");
        Token token = tokenManager.getCache();
        WeixinResponse response = weixinExecutor.get(String.format(autoreply_setting_get_uri, token.getAccessToken()));

        JSONObject result = response.getAsJson();

        AutoReplySetting replySetting = JSON.toJavaObject(result, AutoReplySetting.class);
        List<AutoReplySetting.Rule> ruleList = null;
        if (result.containsKey("keyword_autoreply_info")) {
            JSONArray keywordList = result.getJSONObject("keyword_autoreply_info").getJSONArray("list");
            ruleList = new ArrayList<AutoReplySetting.Rule>(keywordList.size());
            JSONObject keywordObj = null;
            JSONArray replyList = null;
            JSONObject replyObj = null;
            for (int i = 0; i < keywordList.size(); i++) {
                keywordObj = keywordList.getJSONObject(i);
                AutoReplySetting.Rule rule = JSON.toJavaObject(keywordObj, AutoReplySetting.Rule.class);
                replyList = keywordObj.getJSONArray("reply_list_info");
                List<AutoReplySetting.Entry> entryList = new ArrayList<AutoReplySetting.Entry>(replyList.size());
                for (int j = 0; j < replyList.size(); j++) {
                    replyObj = replyList.getJSONObject(j);
                    if (replyObj.getString("type").equals("news")) {
                        entryList.add(JSON.parseObject(replyObj.toJSONString(), AutoReplySetting.Entry.class,
                                ButtonExtraProcessor.global));
                    } else {
                        entryList.add(JSON.toJavaObject(replyObj, AutoReplySetting.Entry.class));
                    }
                }
                rule.setReplyList(entryList);
                ruleList.add(rule);
            }
        }
        replySetting.setKeywordReplyList(ruleList);
        return replySetting;
    }

    /**
     * 接口调用次数调用清零：公众号调用接口并不是无限制的。为了防止公众号的程序错误而引发微信服务器负载异常，默认情况下，
     * 每个公众号调用接口都不能超过一定限制 ，当超过一定限制时，调用对应接口会收到{"errcode":45009,"errmsg":"api freq
     * out of limit" }错误返回码。
     *
     * @param appId
     *            公众号ID
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433744592&token=&lang=zh_CN">接口清零</a>
     * @return 操作结果
     * @throws WeixinException
     */
    public ApiResult clearQuota(String appId) throws WeixinException {
        String clearquota_uri = getRequestUri("clearquota_uri");
        String body = String.format("{\"appid\":\"%s\"}", appId);
        WeixinResponse response = weixinExecutor.post(String.format(clearquota_uri, tokenManager.getAccessToken()),
                body);
        return response.getAsResult();
    }
}