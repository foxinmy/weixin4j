package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.card.CardType;
import com.foxinmy.weixin4j.type.card.SubCardType;

/**
 * 单品类型礼品卡
 * 指该礼品卡用于兑换指定单品，如汉堡礼品卡
 *
 * @className VoucherCardCoupon
 * @author kit(kit.li@qq.com)
 * @date 2018年10月23日
 */
public class VoucherCard extends CardCoupon {

    /**
     * 子卡券类型
     */
    @JSONField(name = "sub_card_type")
    private final String subCardType;
    /**
     * 礼品卡背景图片,非必需
     */
    @JSONField(name = "background_pic_url")
    private String backgroundPicUrl;
    /**
     * 是否支持积分，填写true或者false。默认为false
     */
    @JSONField(name = "supply_bonus")
    private boolean supplyBonus;
    /**
     * 是否支持余额，填写true或者false。默认为false
     */
    @JSONField(name = "supply_balance")
    private boolean supplyBalance;
    /**
     * 自定义会员信息类目，会员卡激活后显示,包含name和url字段
     */
    @JSONField(name = "custom_field1")
    private MemCardCustomField customField1;
    /**
     * 自定义会员信息类目，会员卡激活后显示,包含name和url字段
     */
    @JSONField(name = "custom_field2")
    private MemCardCustomField customField2;
    /**
     * 自定义会员信息类目，会员卡激活后显示,包含name和url字段
     */
    @JSONField(name = "custom_field3")
    private MemCardCustomField customField3;
    /**
     * 是否自动激活，若开发者不需要额外激活流程则填写true。
     */
    @JSONField(name = "auto_activate")
    private boolean autoActivate;

    public String getSubCardType() {
        return subCardType;
    }

    public String getBackgroundPicUrl() {
        return backgroundPicUrl;
    }

    public boolean isSupplyBonus() {
        return supplyBonus;
    }

    public boolean isSupplyBalance() {
        return supplyBalance;
    }

    public MemCardCustomField getCustomField1() {
        return customField1;
    }

    public MemCardCustomField getCustomField2() {
        return customField2;
    }

    public MemCardCustomField getCustomField3() {
        return customField3;
    }

    public boolean isAutoActivate() {
        return autoActivate;
    }

    public VoucherCard(CouponBaseInfo baseInfo, GiftCard.Builder builder){
        super(baseInfo);
        this.subCardType = SubCardType.VOUCHER.name();
        this.autoActivate = builder.isAutoActivate();
        this.backgroundPicUrl = builder.getBackgroundPicUrl();
        this.customField1 = builder.getCustomField1();
        this.customField2 = builder.getCustomField2();
        this.customField3 = builder.getCustomField3();
        this.supplyBalance = false;
        this.supplyBonus = builder.isSupplyBonus();;
    }

    @JSONField(serialize = false)
    @Override
    public CardType getCardType() {
        return CardType.GENERAL_CARD;
    }

    @Override
    public String toString() {
        return "VoucherCardCoupon [" +
                "subCardType='" + subCardType + '\'' +
                ", backgroundPicUrl='" + backgroundPicUrl + '\'' +
                ", supplyBonus=" + supplyBonus +
                ", supplyBalance=" + supplyBalance +
                ", customField1=" + customField1 +
                ", customField2=" + customField2 +
                ", customField3=" + customField3 +
                ", autoActivate=" + autoActivate +
                ", " + super.toString() +
                ']';
    }
}
