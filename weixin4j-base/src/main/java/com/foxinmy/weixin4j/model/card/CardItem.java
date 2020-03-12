package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 礼品卡货架主题内的礼品卡描述项
 *
 * @author kit(kit.li@qq.com)
 * @date 2018年10月30日
 */
public class CardItem {
    /**
     * 待上架的card_id
     */
    @JSONField(name = "card_id")
    private String cardId;
    /**
     * 商品名，不填写默认为卡名称
     */
    private String title;
    /**
     * 商品缩略图，1000像素*600像素以下
     */
    @JSONField(name = "pic_url")
    private String picUrl;
    /**
     * 商品简介
     */
    private String desc;

    public CardItem(){

    }

    public CardItem(String cardId) {
        this.cardId = cardId;
    }

    public CardItem(String cardId, String title, String picUrl, String desc) {
        this.cardId = cardId;
        this.title = title;
        this.picUrl = picUrl;
        this.desc = desc;
    }

    public String getCardId() {
        return cardId;
    }

    public String getTitle() {
        return title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
