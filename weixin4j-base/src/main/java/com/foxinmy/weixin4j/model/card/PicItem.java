package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 礼品卡货架主题中的卡面结构体
 *
 * @author kit(kit.li@qq.com)
 * @date 2018年10月30日
 */
public class PicItem {
    /**
     * 卡面图片，须先将图片上传至CDN，大小控制在1000 x 600像素以下
     */
    @JSONField(name = "background_pic_url")
    private String backgroundPicUrl;
    /**
     * 自定义的卡面的标识, 非必填
     */
    @JSONField(name = "outer_img_id")
    private String outerImgId;
    /**
     * 该卡面对应的默认祝福语，当用户没有编辑内容时会随卡默认填写为用户祝福内容
     */
    @JSONField(name = "default_gifting_msg")
    private String defaultGiftingMsg;

    public PicItem(){

    }

    public PicItem(String backgroundPicUrl, String defaultGiftingMsg) {
        this.backgroundPicUrl = backgroundPicUrl;
        this.defaultGiftingMsg = defaultGiftingMsg;
    }

    public PicItem(String backgroundPicUrl, String outerImgId, String defaultGiftingMsg) {
        this.backgroundPicUrl = backgroundPicUrl;
        this.outerImgId = outerImgId;
        this.defaultGiftingMsg = defaultGiftingMsg;
    }

    public String getBackgroundPicUrl() {
        return backgroundPicUrl;
    }

    public String getOuterImgId() {
        return outerImgId;
    }

    public String getDefaultGiftingMsg() {
        return defaultGiftingMsg;
    }

    public void setBackgroundPicUrl(String backgroundPicUrl) {
        this.backgroundPicUrl = backgroundPicUrl;
    }

    public void setOuterImgId(String outerImgId) {
        this.outerImgId = outerImgId;
    }

    public void setDefaultGiftingMsg(String defaultGiftingMsg) {
        this.defaultGiftingMsg = defaultGiftingMsg;
    }
}
