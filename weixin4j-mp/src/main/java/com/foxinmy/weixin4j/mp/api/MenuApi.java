package com.foxinmy.weixin4j.mp.api;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ParseProcess;
import com.alibaba.fastjson.serializer.NameFilter;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Button;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.model.Menu;
import com.foxinmy.weixin4j.mp.model.MenuMatchRule;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.type.ButtonType;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 菜单相关API
 *
 * @className MenuApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月25日
 * @since JDK 1.6
 */
public class MenuApi extends MpApi {

    private final TokenManager tokenManager;

    public MenuApi(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    /**
     * 自定义菜单
     *
     * @param buttons
     *            菜单列表
     * @throws WeixinException
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013&token=&lang=zh_CN">
     *      创建自定义菜单</a>
     * @see com.foxinmy.weixin4j.model.Button
     * @return 处理结果
     */
    public ApiResult createMenu(List<Button> buttons) throws WeixinException {
        String menu_create_uri = getRequestUri("menu_create_uri");
        JSONObject obj = new JSONObject();
        obj.put("button", buttons);
        return createMenu0(menu_create_uri, obj).getAsResult();
    }

    private WeixinResponse createMenu0(String url, JSONObject data) throws WeixinException {
        return weixinExecutor.post(String.format(url, tokenManager.getAccessToken()),
                JSON.toJSONString(data, new NameFilter() {
                    @Override
                    public String process(Object object, String name, Object value) {
                        if (object instanceof Button && name.equals("content")
                                && StringUtil.isNotBlank(((Button) object).getType())) {
                            ButtonType buttonType = ButtonType.valueOf(((Button) object).getType());
                            if (ButtonType.view == buttonType || ButtonType.miniprogram == buttonType) {
                                return "url";
                            } else if (ButtonType.media_id == buttonType || ButtonType.view_limited == buttonType) {
                                return "media_id";
                            } else {
                                return "key";
                            }
                        }
                        return name;
                    }
                }));

    }

    /**
     * 查询菜单
     *
     * @throws WeixinException
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141014&token=&lang=zh_CN">
     *      查询菜单</a>
     * @see com.foxinmy.weixin4j.model.Button
     * @return 菜单集合
     */
    public List<Button> getMenu() throws WeixinException {
        return buttonsConvertor(getMenu0().getJSONObject("menu"));
    }

    private JSONObject getMenu0() throws WeixinException {
        String menu_get_uri = getRequestUri("menu_get_uri");
        Token token = tokenManager.getCache();
        WeixinResponse response = weixinExecutor.get(String.format(menu_get_uri, token.getAccessToken()));
        return response.getAsJson();
    }

    /**
     * 查询全部菜单(包含个性化菜单)
     *
     * @throws WeixinException
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141014&token=&lang=zh_CN">
     *      普通菜单</a>
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN">
     *      个性化菜单</a>
     * @see com.foxinmy.weixin4j.model.Button
     * @see com.foxinmy.weixin4j.mp.model.Menu
     * @return 菜单集合
     */
    public List<Menu> getAllMenu() throws WeixinException {
        JSONObject response = getMenu0();
        List<Menu> menus = new ArrayList<Menu>();
        // 普通菜单
        JSONObject menuObj = response.getJSONObject("menu");
        menus.add(new Menu(menuObj.getString("menuid"), buttonsConvertor(menuObj), null));
        // 个性化菜单
        JSONArray menuObjs = response.getJSONArray("conditionalmenu");
        if (menuObjs != null && !menuObjs.isEmpty()) {
            for (int i = 0; i < menuObjs.size(); i++) {
                menuObj = menuObjs.getJSONObject(i);
                menus.add(new Menu(menuObj.getString("menuid"), buttonsConvertor(menuObj),
                        menuObj.getObject("matchrule", MenuMatchRule.class)));
            }
        }
        return menus;
    }

    /**
     * 删除菜单
     *
     * @return 处理结果
     * @throws WeixinException
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141015&token=&lang=zh_CN">
     *      删除菜单</a>
     * @return 处理结果
     */
    public ApiResult deleteMenu() throws WeixinException {
        String menu_delete_uri = getRequestUri("menu_delete_uri");
        Token token = tokenManager.getCache();
        WeixinResponse response = weixinExecutor.get(String.format(menu_delete_uri, token.getAccessToken()));

        return response.getAsResult();
    }

    /**
     * 创建个性化菜单
     *
     * @param buttons
     *            菜单列表
     * @param matchRule
     *            匹配规则 至少要有一个匹配信息是不为空
     * @throws WeixinException
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN">
     *      创建个性化菜单</a>
     * @see com.foxinmy.weixin4j.model.Button
     * @return 菜单ID
     */
    public String createCustomMenu(List<Button> buttons, MenuMatchRule matchRule) throws WeixinException {
        String menu_create_uri = getRequestUri("menu_custom_create_uri");
        JSONObject obj = new JSONObject();
        obj.put("button", buttons);
        obj.put("matchrule", matchRule.getRule());
        return createMenu0(menu_create_uri, obj).getAsJson().getString("menuid");
    }

    /**
     * 删除个性化菜单
     *
     * @throws WeixinException
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN">
     *      删除个性化菜单</a>
     * @return 处理结果
     */
    public ApiResult deleteCustomMenu(String menuId) throws WeixinException {
        String menu_delete_uri = getRequestUri("menu_delete_custom_uri");
        Token token = tokenManager.getCache();
        JSONObject obj = new JSONObject();
        obj.put("menuid", menuId);
        WeixinResponse response = weixinExecutor.post(String.format(menu_delete_uri, token.getAccessToken()),
                obj.toJSONString());

        return response.getAsResult();
    }

    /**
     * 测试个性化菜单匹配结果
     *
     * @param userId
     *            可以是粉丝的OpenID，也可以是粉丝的微信号。
     * @return 匹配到的菜单配置
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN">
     *      测试个性化菜单</a>
     * @see com.foxinmy.weixin4j.model.Button
     * @throws WeixinException
     */
    public List<Button> matchCustomMenu(String userId) throws WeixinException {
        String menu_trymatch_uri = getRequestUri("menu_trymatch_uri");
        Token token = tokenManager.getCache();
        JSONObject obj = new JSONObject();
        obj.put("user_id", userId);
        WeixinResponse response = weixinExecutor.post(String.format(menu_trymatch_uri, token.getAccessToken()),
                obj.toJSONString());

        return buttonsConvertor(response.getAsJson().getJSONObject("menu"));
    }

    private final ParseProcess buttonProcess = new ExtraProcessor() {
        @Override
        public void processExtra(Object object, String key, Object value) {
            ((Button) object).setContent(String.valueOf(value));
        }
    };

    private List<Button> buttonsConvertor(JSONObject menu) {
        JSONArray buttons = menu.getJSONArray("button");
        List<Button> buttonList = new ArrayList<Button>(buttons.size());
        for (int i = 0; i < buttons.size(); i++) {
            buttonList.add(JSON.parseObject(buttons.getString(i), Button.class, buttonProcess));
        }
        return buttonList;
    }
}
