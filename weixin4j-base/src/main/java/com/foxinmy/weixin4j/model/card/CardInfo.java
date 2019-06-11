package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 已领取的礼品卡信息
 * 用于查询用户的礼品卡信息时，作为参数或返回对象
 *
 * @author kit (kit@muses.cc)
 * @date 2018-11-23
 */
public class CardInfo {
    /**
     * 礼品卡的code
     */
    private String code;
    /**
     * 礼品卡的card_id
     */
    @JSONField(name = "card_id")
    private String cardId;
    /**
     * 生效时间
     */
    @JSONField(name = "begin_time")
    private long beginTime;
    /**
     * 结束时间
     */
    @JSONField(name = "end_time")
    private long endTime;
    /**
     * 当前的余额额度
     */
    private int balance;
    /**
     * 礼品卡卡面显示的卡号，若没设置则与code相同
     */
    @JSONField(name = "card_number")
    private String cardNumber;
    /**
     * 用于支持商家激活时针对单个礼品卡分配自定义的礼品卡背景。
     */
    @JSONField(name = "background_pic_url")
    private String backgroundPicUrl;
    /**
     * 自定义金额消耗记录，不超过14个汉字。
     */
    @JSONField(name = "record_balance")
    private String recordBalance;
    /**
     * 创建时字段custom_field1定义类型的最新数值，限制为4个汉字，12字节。
     */
    @JSONField(name = "custom_field_value1")
    private String customFieldValue1;
    /**
     * 创建时字段custom_field2定义类型的最新数值，限制为4个汉字，12字节。
     */
    @JSONField(name = "custom_field_value2")
    private String customFieldValue2;
    /**
     * 创建时字段custom_field3定义类型的最新数值，限制为4个汉字，12字节。
     */
    @JSONField(name = "custom_field_value3")
    private String customFieldValue3;
    /**
     * 控制本次积分变动后转赠入口是否出现
     */
    @JSONField(name = "can_give_friend")
    private Boolean canGiveFriend;
    /**
     * 该卡的价格
     */
    private int price;
    /**
     * 祝福语    
     */
    @JSONField(name = "default_gifting_msg")
    private String defaultGiftingMsg;

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

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBackgroundPicUrl() {
        return backgroundPicUrl;
    }

    public void setBackgroundPicUrl(String backgroundPicUrl) {
        this.backgroundPicUrl = backgroundPicUrl;
    }

    public String getRecordBalance() {
        return recordBalance;
    }

    public void setRecordBalance(String recordBalance) {
        this.recordBalance = recordBalance;
    }

    public String getCustomFieldValue1() {
        return customFieldValue1;
    }

    public void setCustomFieldValue1(String customFieldValue1) {
        this.customFieldValue1 = customFieldValue1;
    }

    public String getCustomFieldValue2() {
        return customFieldValue2;
    }

    public void setCustomFieldValue2(String customFieldValue2) {
        this.customFieldValue2 = customFieldValue2;
    }

    public String getCustomFieldValue3() {
        return customFieldValue3;
    }

    public void setCustomFieldValue3(String customFieldValue3) {
        this.customFieldValue3 = customFieldValue3;
    }

    public Boolean getCanGiveFriend() {
        return canGiveFriend;
    }

    public void setCanGiveFriend(Boolean canGiveFriend) {
        this.canGiveFriend = canGiveFriend;
    }

    public void CardItem(){}

    public void CardItem(String code, String cardId){
        this.code = code;
        this.cardId = cardId;
    }
}
