package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.card.FieldNameType;

/**
 * 会员卡自定义类型
 *
 * @auther: Feng Yapeng
 * @since: 2016/12/15 10:49
 */
public class MemCardCustomField {

    /**
     *会员信息类目半自定义名称，当开发者变更这类类目信息的value值时，可以选择触发系统模板消息通知用户。
     */
    @JSONField(name = "name_type")
    private FieldNameType nameType;

    /**
     * 会员信息类目自定义名称，当开发者变更这类类目信息的value值时, 不会触发系统模板消息通知用户
     */
    private String name;
    /**
     * 点击类目跳转外链url
     */
    private String url;
    /**
     * s
     */
    private String tips;

    public MemCardCustomField(FieldNameType fieldNameType, String url) {
        this.nameType = fieldNameType;
        this.url = url;
    }

    public MemCardCustomField(FieldNameType fieldNameType, String name, String url) {
        this.nameType = fieldNameType;
        this.name = name;
        this.url = url;
    }

    public MemCardCustomField(String name, String url, String tips) {
        this.name = name;
        this.url = url;
        this.tips = tips;
    }

    public FieldNameType getNameType() {
        return nameType;
    }

    public void setNameType(FieldNameType nameType) {
        this.nameType = nameType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
