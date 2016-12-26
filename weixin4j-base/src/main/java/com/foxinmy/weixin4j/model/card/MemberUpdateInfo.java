package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 会员更新的信息
 *
 * @auther: Feng Yapeng
 * @since: 2016/12/20 15:01
 */
public class MemberUpdateInfo {

    /**
     * 卡券Code码。
     */
    private String  code;
    /**
     * 卡券ID。
     */
    @JSONField(name = "card_id")
    private String  cardId;
    /**
     * 支持商家激活时针对单个会员卡分配自定义的会员卡背景。
     */
    @JSONField(name = "background_pic_url")
    private String  backgroundPicUrl;
    /**
     * 需要设置的积分全量值，传入的数值会直接显示
     */
    private Integer bonus;
    /**
     * 本次积分变动值，传负数代表减少
     */
    @JSONField(name = "add_bonus")
    private Integer addBonus;
    /**
     * 商家自定义积分消耗记录，不超过14个汉字
     */
    @JSONField(name = "record_bonus")
    private String  recordBonus;
    /*
     * 需要设置的余额全量值，传入的数值会直接显示在卡面
     */
    private Integer balance;

    /**
     * 本次余额变动值，传负数代表减少
     */
    @JSONField(name = "add_balance")
    private Integer addBalance;
    /**
     * 商家自定义金额消耗记录，不超过14个汉字。
     */
    @JSONField(name = "record_balance")
    private String  recordBalance;
    /**
     * 创建时字段custom_field1定义类型的最新数值，限制为4个汉字，12字节。
     */
    @JSONField(name = "custom_field_value1")
    private String  customFieldValue1;
    /**
     * 同上
     */
    @JSONField(name = "custom_field_value2")
    private String  customFieldValue2;
    /**
     * 同上
     */
    @JSONField(name = "custom_field_value3")
    private String  customFieldValue3;

    /**
     *
     */
    @JSONField(name = "notifyOptional")
    private JSONObject notifyOptional;


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

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public Integer getAddBonus() {
        return addBonus;
    }

    public void setAddBonus(Integer addBonus) {
        this.addBonus = addBonus;
    }

    public String getRecordBonus() {
        return recordBonus;
    }

    public void setRecordBonus(String recordBonus) {
        this.recordBonus = recordBonus;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getAddBalance() {
        return addBalance;
    }

    public void setAddBalance(Integer addBalance) {
        this.addBalance = addBalance;
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

    public void setCustomFieldValue1(String customFieldValue1, boolean notify) {
        this.customFieldValue1 = customFieldValue1;
        if (notifyOptional == null) {
            notifyOptional = new JSONObject();
        }
        notifyOptional.put("is_notify_custom_field1", notify);
    }


    public String getCustomFieldValue2() {
        return customFieldValue2;
    }

    public void setCustomFieldValue2(String customFieldValue2) {
        this.customFieldValue2 = customFieldValue2;

    }

    public void setCustomFieldValue2(String customFieldValue2, boolean notify) {
        this.customFieldValue2 = customFieldValue2;
        if (notifyOptional == null) {
            notifyOptional = new JSONObject();
        }
        notifyOptional.put("is_notify_custom_field2", notify);

    }

    public String getCustomFieldValue3() {
        return customFieldValue3;
    }

    public void setCustomFieldValue3(String customFieldValue3) {
        this.customFieldValue3 = customFieldValue3;
    }

    public void setCustomFieldValue3(String customFieldValue3, boolean notify) {
        this.customFieldValue3 = customFieldValue3;
        if (notifyOptional == null) {
            notifyOptional = new JSONObject();
        }
        notifyOptional.put("is_notify_custom_field3", notify);
    }

    public void setNOtify(boolean notifyBonus, boolean notifyBalance) {
        if (notifyOptional == null) {
            notifyOptional = new JSONObject();
        }
        notifyOptional.put("is_notify_bonus", notifyBonus);
        notifyOptional.put("is_notify_balance", notifyBalance);
    }
}
