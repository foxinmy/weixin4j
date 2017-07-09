package com.foxinmy.weixin4j.mp.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.util.NameValue;

/**
 * 模板消息
 *
 * @className TemplateMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月29日
 * @since JDK 1.6
 * @see <a href=
 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN">模板消息</a>
 */
public class TemplateMessage implements Serializable {

    private static final long serialVersionUID = 7950608393821661436L;

    /**
     * 用户的openid
     */
    @JSONField(name = "touser")
    private String toUser;
    /**
     * 模板ID
     */
    @JSONField(name = "template_id")
    private String templateId;
    /**
     * 点击消息跳转的url
     */
    private String url;
    /**
     * 头部信息(first第一行)
     */
    @JSONField(serialize = false)
    private NameValue head;
    /**
     * 尾部信息(remark最后行)
     */
    @JSONField(serialize = false)
    private NameValue tail;

    /**
     * 跳小程序所需数据，不需跳小程序可不用传该数据
     */
    private String miniprogram;
    /**
     * 所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系）
     */
    private String appid;
    /**
     * 所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar）
     */
    private String pagepath;

    /**
     * 数据项
     */
    @JSONField(name = "data")
    private Map<String, NameValue> content;

    private final static String HEAD_KEY = "first";
    private final static String TAIL_KEY = "remark";
    private final static String DEFAULT_COLOR = "#173177";

    @JSONCreator
    public TemplateMessage(@JSONField(name = "toUser") String toUser, @JSONField(name = "templateId") String templateId,
            @JSONField(name = "url") String url) {
        this.toUser = toUser;
        this.templateId = templateId;
        this.url = url;
        this.content = new HashMap<String, NameValue>();
    }

    public String getToUser() {
        return toUser;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getUrl() {
        return url;
    }

    public NameValue getHead() {
        return head == null ? content.get(HEAD_KEY) : head;
    }

    public NameValue getTail() {
        return tail == null ? content.get(TAIL_KEY) : tail;
    }

    public Map<String, NameValue> getContent() {
        return content;
    }

    /**
     * 新增头部字段(默认颜色为#FF0000)
     *
     * @param text
     *            字段文本
     * @return
     */
    public TemplateMessage pushHead(String text) {
        return pushHead("#FF0000", text);
    }

    /**
     * 新增头部字段
     *
     * @param color
     *            文字颜色
     * @param text
     *            字段文本
     * @return
     */
    public TemplateMessage pushHead(String color, String text) {
        head = new NameValue(color, text);
        content.put(HEAD_KEY, head);
        return this;
    }

    /**
     * 新增尾部字段(默认颜色为#173177)
     *
     * @param text
     *            字段文本
     * @return
     */
    public TemplateMessage pushTail(String text) {
        return pushTail(DEFAULT_COLOR, text);
    }

    /**
     * 新增尾部字段
     *
     * @param color
     *            文字颜色
     * @param text
     *            字段文本
     * @return
     */
    public TemplateMessage pushTail(String color, String text) {
        tail = new NameValue(color, text);
        content.put(TAIL_KEY, tail);
        return this;
    }

    /**
     * 新增字段项(默认颜色为#173177)
     *
     * @param key
     *            预留的字段名
     * @param text
     *            字段文本
     * @return
     */
    public TemplateMessage pushItem(String key, String text) {
        return pushItem(key, DEFAULT_COLOR, text);
    }

    /**
     * 新增字段项
     *
     * @param key
     *            预留的字段名
     * @param color
     *            文字颜色
     * @param text
     *            字段文本
     * @return
     */
    public TemplateMessage pushItem(String key, String color, String text) {
        content.put(key, new NameValue(color, text));
        return this;
    }

    /**
     * 设置所有字段项
     *
     * @param items
     */
    public void setItems(Map<String, NameValue> items) {
        this.content = items;
    }

    public String getMiniprogram() {
        return miniprogram;
    }

    public void setMiniprogram(String miniprogram) {
        this.miniprogram = miniprogram;
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

    @Override
    public String toString() {
        return "TemplateMessage [toUser=" + toUser + ", templateId=" + templateId + ", url=" + url + ", head="
                + getHead() + ", tail=" + getTail() + ", content=" + content + "]";
    }
}
