package com.foxinmy.weixin4j.pay.profitsharing;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.pay.type.profitsharing.ReceiverType;
import com.foxinmy.weixin4j.pay.type.profitsharing.RelationType;

/**
 * 分账接收方基础信息
 * 用于添加或删除分帐接收方时使用
 *
 * @author kit(kit_21cn@21cn.com)
 * @date 2020年05月20日
 * @since weixin4j-pay 1.1.0
 */
public class Receiver {

    public Receiver(ReceiverType type, String account, RelationType relationType) {
        this.type = type;
        this.account = account;
        this.relationType = relationType;
    }

    public Receiver(ReceiverType type, String account, String name, RelationType relationType, String customRelation) {
        this.type = type;
        this.account = account;
        this.name = name;
        this.relationType = relationType;
        this.customRelation = customRelation;
    }

    /**
     * 分账接收方类型
     */
    private ReceiverType type;
    /**
     * 分账接收方帐号
     * 类型是MERCHANT_ID时，是商户ID
     * 类型是PERSONAL_WECHATID时，是个人微信号
     * 类型是PERSONAL_OPENID时，是个人openid
     * 类型是PERSONAL_SUB_OPENID时，是个人sub_openid
     */
    private String account;
    /**
     * 分账接收方全称
     * 分账接收方类型是MERCHANT_ID时，是商户全称（必传）
     * 分账接收方类型是PERSONAL_WECHATID 时，是个人姓名（必传）
     * 分账接收方类型是PERSONAL_OPENID时，是个人姓名（选传，传则校验）
     * 分账接收方类型是PERSONAL_SUB_OPENID时，是个人姓名（选传，传则校验）
     */
    private String name;
    /**
     * 与分账方的关系类型
     */
    @JSONField(name = "relation_type")
    private RelationType relationType;
    /**
     * 自定义的分账关系
     * 子商户与接收方具体的关系，本字段最多10个字。
     * 当字段relation_type的值为CUSTOM时，本字段必填
     */
    @JSONField(name = "custom_relation")
    private String customRelation;

    public ReceiverType getType() {
        return type;
    }

    public void setType(ReceiverType type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public String getCustomRelation() {
        return customRelation;
    }

    public void setCustomRelation(String customRelation) {
        this.customRelation = customRelation;
    }
}
