package com.foxinmy.weixin4j.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.ButtonType;

/**
 * 菜单按钮
 * <p>
 * 目前自定义菜单最多包括3个一级菜单,每个一级菜单最多包含5个二级菜单,一级菜单最多4个汉字,二级菜单最多7个汉字,多出来的部分将会以"..."代替
 * 请注意,创建自定义菜单后,由于微信客户端缓存,需要24小时微信客户端才会展现出来,建议测试时可以尝试取消关注公众账号后再次关注,则可以看到创建后的效果
 * </p>
 *
 * @className Button
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月5日
 * @since JDK 1.6
 */
public class Button implements Serializable {

    private static final long serialVersionUID = -6422234732203854866L;

    /**
     * 菜单标题，不超过16个字节，子菜单不超过40个字节
     */
    private String name;
    /**
     * 菜单类型 </br>
     * <font color="red"> 公众平台官网上能够设置的菜单类型有view、text、img、photo、video、voice
     * </font>
     *
     * @see com.foxinmy.weixin4j.type.ButtonType
     */
    private String type;
    /**
     * 菜单KEY值,根据type的类型而定
     * </p>
     * 通过公众平台设置的自定义菜单：</br>
     * <li>text:保存文字；
     * <li>img、voice：保存媒体ID；
     * <li>video：保存视频URL；
     * <li>news：保存图文消息媒体ID
     * <li>view：保存链接URL；
     * <p>
     * 使用API设置的自定义菜单：
     * </p>
     * <li>click、scancode_push、scancode_waitmsg、pic_sysphoto、pic_photo_or_album、
     * pic_weixin、location_select：保存key；
     * <li>view：保存链接URL;
     * <li>media_id、view_limited：保存媒体ID
     */
    private String content;
    /**
     * 扩展属性，比如在公众平台设置菜单时的图文列表
     */
    @JSONField(serialize = false, deserialize = false)
    private Object extra;
    /**
     * miniprogram类型必须 小程序的appid（仅认证公众号可配置）
     */
    private String appid;
    /**
     * miniprogram类型必须 小程序的页面路径
     */
    private String pagepath;

    /**
     * 二级菜单数组，个数应为1~5个
     */
    @JSONField(name = "sub_button")
    private List<Button> subs;

    protected Button() {
        this.subs = new ArrayList<Button>();
    }

    /**
     * 创建一个具有子菜单的菜单
     *
     * @param name
     *            菜单名
     * @param subButtons
     *            二级菜单列表
     */
    public Button(String name, Button... subButtons) {
        this.name = name;
        this.subs = new ArrayList<Button>(Arrays.asList(subButtons));
    }

    /**
     * 创建一个普通菜单
     *
     * @param name
     *            菜单名
     * @param content
     *            菜单内容
     * @param type
     *            菜单类型
     */
    public Button(String name, String content, ButtonType type) {
        this.name = name;
        this.content = content;
        this.type = type.name();
        this.subs = new ArrayList<Button>();
    }

    /**
     * 小程序菜单
     *
     * @param name
     *            菜单名
     * @param url
     *            小程序的url页面
     * @param appid
     *            小程序的appid
     * @param pagepath
     *            小程序员的页面路径
     */
    public Button(String name, String url, String appid, String pagepath) {
        this.name = name;
        this.content = url;
        this.appid = appid;
        this.pagepath = pagepath;
        this.type = ButtonType.miniprogram.name();
        this.subs = new ArrayList<Button>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setType(ButtonType type) {
        this.type = type.name();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getExtra() {
        return extra;
    }

    /**
     * 扩展只读属性，设置无效
     *
     * @param extra
     */
    public void setExtra(Object extra) {
        this.extra = extra;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPagepath() {
        return pagepath;
    }

    public void setPagepath(String pagepath) {
        this.pagepath = pagepath;
    }

    public List<Button> getSubs() {
        return subs;
    }

    public void setSubs(List<Button> subs) {
        this.subs = subs;
    }

    public Button pushSub(Button btn) {
        this.subs.add(btn);
        return this;
    }

    @Override
    public String toString() {
        return "Button [name=" + name + ", type=" + type + ", content=" + content + ", extra=" + extra + ", appid="
                + appid + ", pagepath=" + pagepath + ", subs=" + subs + "]";
    }
}
