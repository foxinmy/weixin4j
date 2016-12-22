package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 会员初始化的信息
 *
 * @auther: Feng Yapeng
 * @since: 2016/12/20 15:00
 */
public class MemberInitInfo {

    /**
     * 会员卡编号，由开发者填入，作为序列号显示在用户的卡包里。可与Code码保持等值。
     */
    @JSONField(name = "membership_number")
    private String membershipNumber;
    /**
     * 领取会员卡用户获得的code
     */
    private String code;
    /**
     * 卡券ID【自定义code卡券必填】
     */
    @JSONField(name = "card_id")
    private String cardId;
    /**
     * 商家自定义会员卡背景图，须 先调用上传图片接口将背景图上传至CDN，否则报错，
     * 卡面设计请遵循微信会员卡自定义背景设计规范
     */
    @JSONField(name = "background_pic_url")
    private String backgroundPicUrl;

    /**
     * 激活后的有效起始时间。若不填写默认以创建时的 date_info 为准。Unix时间戳格式
     */
    @JSONField(name = "activate_begin_time")
    private long     activateBeginTime;
    /**
     * 激活后的有效截至时间。
     */
    @JSONField(name = "activate_end_time")
    private long     activateEndTime;
    /**
     * 初始积分，不填为0。
     */
    @JSONField(name = "init_bonus")
    private Integer initBonus;
    /**
     * 积分同步说明。
     */
    @JSONField(name = "init_bonus_record")
    private String  initBonusRecord;


    /**
     * 初始余额，不填为0。
     */
    @JSONField(name = "init_balance")
    private Integer initBalance;
    /**
     * 创建时字段custom_field1定义类型的初始值，限制为4个汉字，12字节。
     */
    private String  init_custom_field_value1;
    /**
     * 创建时字段custom_field2定义类型的初始值，限制为4个汉字，12字节。
     */
    private String  init_custom_field_value2;
    /**
     * 创建时字段custom_field3定义类型的初始值，限制为4个汉字，12字节。
     */
    private String  init_custom_field_value3;

    public String getMembershipNumber() {
        return membershipNumber;
    }

    public void setMembershipNumber(String membershipNumber) {
        this.membershipNumber = membershipNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getBackgroundPicUrl() {
        return backgroundPicUrl;
    }

    public void setBackgroundPicUrl(String backgroundPicUrl) {
        this.backgroundPicUrl = backgroundPicUrl;
    }

    public long getActivateBeginTime() {
        return activateBeginTime;
    }

    public void setActivateBeginTime(Date activateBeginTime) {
        this.activateBeginTime = activateBeginTime.getTime() / 1000;
    }

    public long getActivateEndTime() {
        return activateEndTime;
    }

    public void setActivateEndTime(Date activateEndTime) {
        this.activateEndTime = activateEndTime.getTime() / 1000;
    }

    public Integer getInitBonus() {
        return initBonus;
    }

    public void setInitBonus(Integer initBonus) {
        this.initBonus = initBonus;
    }

    public String getInitBonusRecord() {
        return initBonusRecord;
    }

    public void setInitBonusRecord(String initBonusRecord) {
        this.initBonusRecord = initBonusRecord;
    }

    public Integer getInitBalance() {
        return initBalance;
    }

    public void setInitBalance(Integer initBalance) {
        this.initBalance = initBalance;
    }

    public String getInit_custom_field_value1() {
        return init_custom_field_value1;
    }

    public void setInit_custom_field_value1(String init_custom_field_value1) {
        this.init_custom_field_value1 = init_custom_field_value1;
    }

    public String getInit_custom_field_value2() {
        return init_custom_field_value2;
    }

    public void setInit_custom_field_value2(String init_custom_field_value2) {
        this.init_custom_field_value2 = init_custom_field_value2;
    }

    public String getInit_custom_field_value3() {
        return init_custom_field_value3;
    }

    public void setInit_custom_field_value3(String init_custom_field_value3) {
        this.init_custom_field_value3 = init_custom_field_value3;
    }
}
